package de.rene_majewski.rm_plugin.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import de.rene_majewski.rm_plugin.config.Config;

/**
 * Stellt eine Verbindung zum MySQL-Server her.
 * 
 * @author René Majewski
 * @since 0.1
 */
public class MySql {
  /**
   * Speichert den Hostnamen des MySQL-Server.
   * 
   * @since 0.1
   */
  private String _host;

  /**
   * Speichert den Port des MySQL-Servers.
   * 
   * @since 0.1
   */
  private int _port;

  /**
   * Speichert den Benutzernamen des MySQL-Servers.
   * 
   * @since 0.1
   */
  private String _user;

  /**
   * Speichert das Benutzer-Passwort des MySQL-Servers.
   * 
   * @since 0.1
   */
  private String _password;

  /**
   * Speichert den Namen der Datenbank des MySQL-Servers.
   * 
   * @since 0.1
   */
  private String _database;

  /**
   * Speichert den Prefix für die Datenbank-Tabellen.
   * 
   * @since 0.1
   */
  private String _prefix;

  /**
   * Speichert die Konfiguration dieses Plugins.
   * 
   * @since 0.1
   */
  private Config _config;

  /**
   * Speichert die Verbindung zum Server.
   * 
   * @since 0.1
   */
  private Connection _conn;

  /**
   * Speichert die SQL-Dateinamen, in denen die einzelnen Datenbank-Tabellen
   * erstellt werden.
   * 
   * @since 0.1
   */
  public static final String[] CREATE_TABLE_FILES = {
    "/sql/player.sql",
    "/sql/log_command.sql",
    "/sql/log_chat.sql",
    "/sql/log_loggin.sql",
    "/sql/world.sql",
    "/sql/log_world_change.sql",
    "/sql/block.sql",
    "/sql/log_block.sql",
    "/sql/group.sql",
    "/sql/group_parent.sql",
    "/sql/group_permissions.sql",
    "/sql/permission.sql",
    "/sql/balance.sql",
    "/sql/balance_statement.sql",
    "/sql/home.sql"
  };

  /**
   * Initialisiert die Verbindung zur Datenbank.
   * 
   * Zuerst wird aus der Konfiguration die einzelnen Angaben geladen, die
   * gebraucht werden, um die Verbindung zum Datenbank-Server herzustellen.
   * Danach wird versucht eine Verbindung aufzubauen. Wenn die Verbindung
   * erfolgreich aufgebaut wurde, werden die SQL-Dateien geladen, die die
   * einzelnen Datenbank-Tabellen erstellen.
   * 
   * @param config Die Konfiguration dieses Plugins.
   * 
   * @since 0.1
   */
  public MySql(Config config) {
    this._config = config;

    this._host = config.getString(Config.DB_CONFIG_HOST);
    this._port = config.getInteger(Config.DB_CONFIG_PORT);
    this._user = config.getString(Config.DB_CONFIG_USER);
    this._password = config.getString(Config.DB_CONFIG_PASSWORD);
    this._database = config.getString(Config.DB_CONFIG_DATABASE);
    this._prefix = config.getString(Config.DB_CONFIG_PREFIX);

    openConnection();

    if (hasConnection()) {
      createTables();
      allBlocksToDatabase();
    }
  }

  /**
   * Erstellt die einzelnen Datenbank-Tabellen.
   * 
   * @since 0.1
   */
  private void createTables() {
    for (String name : CREATE_TABLE_FILES) {
      String sql = loadSql(name);
      this.queryUpdate(sql);
    }
  }

  /**
   * Lädt die SQL-Datei, deren Namen übergeben wurde und gibt deren Inhalt
   * zurück.
   * 
   * Nachdem die SQL-Datei geladen wurde, wird dem Tabellen-Namen der Präfix
   * vorangestellt. Danach wird die überarbeitete Zeichenkette zurückgegeben.
   * 
   * @param name Name der SQL-Datei, die geladen werden soll.
   * 
   * @return Der überarbeitete Inhalt der SQL-Datei. Sollte ein Fehler
   * aufgetreten wird eine leere Zeichenkette zurückgegeben.
   * 
   * @since 0.1
   */
  private String loadSql(String name) {
    String result = "";
    BufferedReader br = null;
    InputStream is = null;

    try {
      is = getClass().getResourceAsStream(name);
      br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
      String rl = new String();
      while((rl = br.readLine()) != null) {
        result += rl.replaceFirst("`&", "`" + this._config.getString(Config.DB_CONFIG_PREFIX)).trim();
      }
    } catch (Exception e) {
      System.err.println("The file '" + name + "' not loaded.");
      e.printStackTrace();
      result = "";
    } finally {
      if (br != null) {
        try {
          br.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }

      if (is != null) {
        try {
          is.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }

    return result;
  }

  /**
   * Ermittelt alle Materialien und schreibt die Daten in Datenbank, fall sie noch nicht existieren.
   */
  private void allBlocksToDatabase() {
    for (Material material : Material.class.getEnumConstants()) {
      this.saveMaterialToDatabase(material);
    }
  }

  /**
   * Speichert das angegebene Material in der Datenbank.
   * 
   * @param material Material, dass in der Datenbank gespeichert werden soll.
   */
  private void saveMaterialToDatabase(Material material) {
    PreparedStatement ps = null;

    try {
      if (!this.isBlockExists(material) && this.hasConnection()) {
        ps = this.getConnection().prepareStatement("INSERT INTO " + this.getTableName(Config.DB_TABLE_BLOCK) + " (name, max_stack_size, is_block, is_edible, is_record, is_solid, is_transparent, is_flammable, is_burnable, is_occluding, has_gravity, minecraft_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        ps.setString(1, material.name().toLowerCase());
        ps.setInt(2, material.getMaxStackSize());
        ps.setBoolean(3, material.isBlock());
        ps.setBoolean(4, material.isEdible());
        ps.setBoolean(5, material.isRecord());
        ps.setBoolean(6, material.isSolid());
        ps.setBoolean(7, material.isTransparent());
        ps.setBoolean(8, material.isFlammable());
        ps.setBoolean(9, material.isBurnable());
        ps.setBoolean(10, material.isOccluding());
        ps.setBoolean(11, material.hasGravity());
        ps.setInt(12, material.getId());
        ps.executeUpdate();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      this.closeRessources(null, ps);
    }
  }

  /**
   * Baut die Verbindung zum Datenbank-Server auf.
   * 
   * @return Aufgebaute Verbindung zum Datenbank-Server. Sollte ein Fehler beim
   * Aufbau aufgetreten sein, so wird <code>null</b> zurückgegeben.
   * 
   * @since 0.1
   */
  public Connection openConnection() {
    try {
      Class.forName("com.mysql.jdbc.Driver");
      this._conn = DriverManager.getConnection(
        "jdbc:mysql://" + this._host + ":" + this._port + "/" + this._database,
        this._user, this._password);
      return this._conn;
    } catch (SQLException e) {
      System.err.println("No connect to MySQL server");
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    
    return null;
  }

  /**
   * Gibt die geöffnete Verbindung zum Datenbank-Server zurück.
   * 
   * @return Geöffnete Verbindung zum Datenbank-Server
   * 
   * @since 0.1
   */
  public Connection getConnection() {
    return this._conn;
  }

  /**
   * Überprüft, ob eine Verbindung zum Datenbank-Server besteht.
   * 
   * @return <code>true</code>, wenn die Verbindung zum Datenbank-Server
   * erfolgreich hergestellt wurde. <code>false</code>, wenn keine
   * Verbindung zum Datenbank-Server besteht.
   * 
   * @since 0.1
   */
  public boolean hasConnection() {
    try {
      return this._conn != null || this._conn.isValid(1);
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
  }

  /**
   * Schließt die Ressourcen.
   * 
   * @param rs ResultSet, dass geschlossen werden soll.
   * 
   * @param ps PreparedStatement, dass geschlossen werden soll.
   * 
   * @since 0.1
   */
  public void closeRessources(ResultSet rs, PreparedStatement ps) {
    if (rs != null) {
      try {
        rs.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }

    if (ps != null) {
      try {
        ps.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * Beendet die Verbindung zum Datenbank-Server.
   * 
   * @since 0.1
   */
  public void closeConnection() {
    try {
      this._conn.close();
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      this._conn = null;
    }
  }

  /**
   * Upadte-Abfrage an den Datenbank-Server übertragen.
   * 
   * @param query SQL-Abfrage, die an den Datenbank-Server übertragen werden
   * soll.
   * 
   * @since 0.1
   */
  public void queryUpdate(String query) {
    PreparedStatement ps = null;
    try {
      ps = this._conn.prepareStatement(query);
      ps.executeUpdate();
    } catch (SQLException e) {
      System.err.println("Failed to send update '" + query + "'.");
      e.printStackTrace();
    } finally {
      this.closeRessources(null, ps);
    }
  }

  /**
   * Ermittelt die Datensatz-ID des übergeben Spielers. Wenn der Datensatz zum
   * Spieler noch nicht existiert, wird er angelegt.
   * 
   * @param player Spieler dessen Datensatz-ID ermittelt werden soll.
   * 
   * @return Die Datensatz-ID des Spielers.
   * 
   * @since 0.1
   */
  public int getPlayerId(Player player) {
    int result = -1;

    PreparedStatement ps = null;
    ResultSet rs = null;
    try {
      if (!this.isPlayerExists(player)) {
        ps = this.getConnection().prepareStatement("INSERT INTO " + this.getTableName(Config.DB_TABLE_PLAYER) + " (uuid, name) VALUES (?, ?)");
        ps.setString(1, player.getUniqueId().toString());
        ps.setString(2, player.getDisplayName());
        ps.executeUpdate();
        this.closeRessources(rs, ps);
      }

      ps = this.getConnection().prepareStatement("SELECT id FROM " + this.getTableName(Config.DB_TABLE_PLAYER) + " WHERE uuid = ?");
      ps.setString(1, player.getUniqueId().toString());
      rs = ps.executeQuery();
      if (rs.next()) {
        result = rs.getInt("id");
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      this.closeRessources(rs, ps);
    }

    return result;
  }

  /**
   * Ermittelt ob ein Datensatz für den Spieler existiert oder nicht.
   * 
   * @param player Spieler, bei dem geschaut werden soll ob schon ein Datensatz
   * in der Datenbank existiert.
   * 
   * @return <code>true</code>, wenn ein Datensatz zum Spieler in der
   * Datenbank existiert. <code>false</code>, wenn noch kein Datensatz
   * existiert.
   * 
   * @since 0.1
   */
  public boolean isPlayerExists(Player player) {
    boolean result = false;

    PreparedStatement ps = null;
    ResultSet rs = null;
    try {
      ps = this.getConnection().prepareStatement("SELECT id,uuid FROM " + this.getTableName(Config.DB_TABLE_PLAYER) + " WHERE uuid = ?;");
      ps.setString(1, player.getUniqueId().toString());
      rs = ps.executeQuery();
      if (rs.next()) {
        result = true;
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      this.closeRessources(rs, ps);
    }

    return result;
  }

  /**
   * Lädt den angegebenen Tabellen-Namen aus der Konfiguration und stellt den
   * Präfix voran.
   * 
   * @param path Path wo der Tabellen-Name innerhalb der Konfiguration steht.
   * 
   * @return Angegebenen Tabellen-Namen mit vorangestellten Präfix.
   * 
   * @since 0.1
   */
  public String getTableName(String path) {
    return this._prefix + this._config.getString(path);
  }

  /**
   * Ermittelt die Datensatz-ID der übergebenen Welt. Wenn der Datensatz zur
   * Welt noch nicht existiert, wird er angelegt.
   * 
   * @param world Welt, deren Datensatz-ID ermittelt werden soll.
   * 
   * @return Die Datensatz-ID der Welt. Konnte kein Datensatz ermittelt werden,
   * so wird <b>0</b> zurückgegeben.
   */
  public int getWorldId(World world) {
    int result = -1;

    PreparedStatement ps = null;
    ResultSet rs = null;

    try {
      if (!this.isWorldExists(world)) {
        ps = this.getConnection().prepareStatement("INSERT INTO " + this.getTableName(Config.DB_TABLE_WORLD) + " (name) VALUES (?)");
        ps.setString(1, world.getName());
        ps.executeUpdate();
        this.closeRessources(rs, ps);
      }

      ps = this.getConnection().prepareStatement("SELECT id FROM " + this.getTableName(Config.DB_TABLE_WORLD) + " WHERE name=?");
      ps.setString(1, world.getName());
      rs = ps.executeQuery();
      if (rs.next()) {
        result = rs.getInt("id");
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      this.closeRessources(rs, ps);
    }

    return result;
  }

  /**
   * Ermittelt ob ein Datensatz für die Welt existiert oder nicht.
   * 
   * @param world Welt, bei der geschaut werden soll, ob schon ein Datensatz
   * in der Datenbank existiert.
   * 
   * @return <code>true</code>, wenn ein Datensatz zur Welt in der Datenbank
   * existiert. <code>false</code>, wenn noch kein Datensatz exitiert.
   */
  public boolean isWorldExists(World world) {
    boolean result = false;

    PreparedStatement ps = null;
    ResultSet rs = null;
    try {
      ps = this.getConnection().prepareStatement("SELECT id, name FROM " + this.getTableName(Config.DB_TABLE_WORLD) + " WHERE name = ?");
      ps.setString(1, world.getName());
      rs = ps.executeQuery();
      if (rs.next()) {
        result = true;
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      this.closeRessources(rs, ps);
    }

    return result;
  }

  /**
   * Ermittelt die Datensatz-ID des übergebenen Blocks. Wenn der Datensatz zum
   * Block noch nicht existiert, wird er angelegt.
   * 
   * @param block Block, dessen Datensatz-ID ermittelt werden soll.
   * 
   * @return Die Datensatz-ID des Block. Konnte kein Datensatz ermittelt werden,
   * so wird <b>-1</b> zurückgegeben.
   */
  public int getBlockdId(Block block) {
    return getBlockdId(block.getType());
  }

  /**
   * Ermittelt die Datensatz-ID des übergebenen Materials. Wenn der Datensatz
   * zum Material noch nicht existiert, wird er angelegt.
   * 
   * @param block Material, deren Datensatz-ID ermittelt werden soll.
   * 
   * @return Die Datensatz-ID des Materials. Konnte kein Datensatz ermittelt
   * werden, so wird <b>-1</b> zurückgegeben.
   */
  public int getBlockdId(Material material) {
    int result = -1;

    PreparedStatement ps = null;
    ResultSet rs = null;

    try {
      saveMaterialToDatabase(material);

      ps = this.getConnection().prepareStatement("SELECT id FROM " + this.getTableName(Config.DB_TABLE_BLOCK) + " WHERE name=?");
      ps.setString(1, material.name().toLowerCase());
      rs = ps.executeQuery();
      if (rs.next()) {
        result = rs.getInt("id");
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      this.closeRessources(rs, ps);
    }

    return result;
  }

  /**
   * Ermittelt ob ein Datensatz für den Block existiert oder nicht.
   * 
   * @param block Block, bei dem geschaut werden soll, ob schon ein Datensatz
   * in der Datenbank existiert.
   * 
   * @return <code>true</code>, wenn ein Datensatz zum Block in der Datenbank
   * existiert. <code>false</code>, wenn noch kein Datensatz exitiert.
   */
  public boolean isBlockExists(Block block) {
    return isBlockExists(block.getType());
  }

  /**
   * Ermittelt ob ein Datensatz für das Material existiert oder nicht.
   * 
   * @param material Material, bei dem geschaut werden soll, ob schon ein
   * Datensatz in der Datenbank existiert.
   * 
   * @return <code>true</code>, wenn ein Datensatz zum Material in der
   * Datenbank existiert. <code>false</code>, wenn noch ken Datensatz
   * existiert.
   */
  public boolean isBlockExists(Material material) {
    boolean result = false;

    PreparedStatement ps = null;
    ResultSet rs = null;
    try {
      ps = this.getConnection().prepareStatement("SELECT id FROM " + this.getTableName(Config.DB_TABLE_BLOCK) + " WHERE name = ?");
      ps.setString(1, material.name().toLowerCase());
      rs = ps.executeQuery();
      if (rs.next()) {
        result = true;
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      this.closeRessources(rs, ps);
    }

    return result;
  }

  /**
   * Ermittelt die ID der angegeben Gruppe.
   * 
   * @param name Name der Gruppe, deren ID ermittelt werden soll.
   * 
   * @return Konnte die Gruppe ermittelt, dann deren ID. Wenn nicht {@code -1}.
   * 
   * @since 0.2
   */
  public int getGroupId(String name) {
    int result = -1;

    PreparedStatement ps = null;
    ResultSet rs = null;
    try {
      ps = this.getConnection().prepareStatement("SELECT id FROM " + this.getTableName(Config.DB_TABLE_PERMISSION_GROUP) + " WHERE name = ?");
      ps.setString(1, name);
      rs = ps.executeQuery();
      if (rs.next()) {
        result = rs.getInt(1);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      this.closeRessources(rs, ps);
    }

    return result;
  }

  /**
   * Ermittelt die ID der angegeben Permission. Wurde keine ID in der Datenbank
   * gefunden, so wird die Permission angelegt.
   * 
   * @param permission Name der Permission, deren ID ermittelt werden soll.
   * 
   * @return ID der Permission.
   */
  public int getPermissionId(String permission) {
    int result = -1;

    PreparedStatement ps = null;
    ResultSet rs = null;

    String tmp = permission;
    if (tmp.startsWith("-")) {
      tmp = tmp.substring(1);
    }

    try {
      if (!this.isPermissionExists(tmp)) {
        ps = this.getConnection().prepareStatement("INSERT INTO " + this.getTableName(Config.DB_TABLE_PERMISSION) + " (name) VALUES (?)");
        ps.setString(1, tmp);
        ps.executeUpdate();
        this.closeRessources(rs, ps);
      }

      ps = this.getConnection().prepareStatement("SELECT id FROM " + this.getTableName(Config.DB_TABLE_PERMISSION) + " WHERE name=?");
      ps.setString(1, tmp);
      rs = ps.executeQuery();
      if (rs.next()) {
        result = rs.getInt("id");
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      this.closeRessources(rs, ps);
    }

    return result;
  }

  /**
   * Überprüft, ob die angegebene Permission existiert.
   * 
   * @param name Permission, wo geschaut werden soll, ob sie in der Datenbank
   * schon existiert.
   * 
   * @return <code>true</code>, wenn die Permission in der Datenbank existiert.
   * <code>false</code>, wenn noch ken Datensatz existiert.
   * 
   * @since 0.2
   */
  public boolean isPermissionExists(String name) {
    boolean result = false;

    String tmp = name;
    if (tmp.startsWith("-")) {
      tmp = tmp.substring(1);
    }

    PreparedStatement ps = null;
    ResultSet rs = null;
    try {
      ps = this.getConnection().prepareStatement("SELECT id FROM " + this.getTableName(Config.DB_TABLE_PERMISSION) + " WHERE name = ?");
      ps.setString(1, name);
      rs = ps.executeQuery();
      if (rs.next()) {
        result = true;
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      this.closeRessources(rs, ps);
    }

    return result;
  }
}