package de.rene_majewski.minecraft_log.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import de.rene_majewski.minecraft_log.config.Config;

public class MySql {

  private String _host;
  private int _port;
  private String _user;
  private String _password;
  private String _database;
  private String _prefix;

  private Connection _conn;

  public MySql(Config config) {
    this._host = config.getString(Config.DB_CONFIG_HOST);
    this._port = config.getInteger(Config.DB_CONFIG_PORT);
    this._user = config.getString(Config.DB_CONFIG_USER);
    this._password = config.getString(Config.DB_CONFIG_PASSWORD);
    this._database = config.getString(Config.DB_CONFIG_DATABASE);
    this._prefix = config.getString(Config.DB_CONFIG_PREFIX);

    openConnection();
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
}