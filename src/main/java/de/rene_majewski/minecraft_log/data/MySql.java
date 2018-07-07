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

public class MySql {

  private String _host;
  private int _port;
  private String _user;
  private String _password;
  private String _database;
  private String _prefix;

  private Config _config;

  private Connection _conn;

  public static final String[] CREATE_TABLE_FILES = {
    "/sql/player.sql",
    "/sql/log_command.sql"
  };

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

  private void createTables() {
    for (String name : CREATE_TABLE_FILES) {
      String sql = loadSql(name);
      this.queryUpdate(sql);
    }
  }

  private String loadSql(String name) {
    String result = new String();
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

  public Connection getConnection() {
    return this._conn;
  }

  public boolean hasConnection() {
    try {
      return this._conn != null || this._conn.isValid(1);
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
  }

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

  public void closeConnection() {
    try {
      this._conn.close();
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      this._conn = null;
    }
  }

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

  public String getTableName(String path) {
    return this._prefix + this._config.getString(path);
  }
}