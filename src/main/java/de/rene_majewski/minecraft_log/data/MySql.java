package de.rene_majewski.minecraft_log.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.entity.Player;

import de.rene_majewski.minecraft_log.config.Config;

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
    "/sql/log_chat.sql"
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
        ps = this.getConnection().prepareStatement("INSERT INTO ? (uuid, name) VALUES (?, ?)");
        ps.setString(1, this.getTableName(Config.DB_TABLE_PLAYER));
        ps.setString(2, player.getUniqueId().toString());
        ps.setString(3, player.getDisplayName());
        ps.executeUpdate();
        this.closeRessources(rs, ps);
      }

      ps = this.getConnection().prepareStatement("SELECT id FROM ? WHERE uuid = ?");
      ps.setString(1, this.getTableName(Config.DB_TABLE_PLAYER));
      ps.setString(2, player.getUniqueId().toString());
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
      ps = this.getConnection().prepareStatement("SELECT id FROM ? WHERE uuid = ?");
      ps.setString(1, this.getTableName(Config.DB_TABLE_PLAYER));
      ps.setString(2, player.getUniqueId().toString());
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
}