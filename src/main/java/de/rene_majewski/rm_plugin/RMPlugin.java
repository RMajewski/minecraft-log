package de.rene_majewski.rm_plugin;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.plugin.java.JavaPlugin;

import de.rene_majewski.rm_plugin.commands.RMPluginCommand;
import de.rene_majewski.rm_plugin.config.Config;
import de.rene_majewski.rm_plugin.data.MySql;
import de.rene_majewski.rm_plugin.listener.BlockListener;
import de.rene_majewski.rm_plugin.listener.CommandListener;
import de.rene_majewski.rm_plugin.listener.PlayerListener;

/**
 * Die Hauptklasse des RM-Plugins.
 * 
 * @since 0.1
 * @author René Majewski
 */
public final class RMPlugin extends JavaPlugin 
{
  /**
   * Speichert die Konfiguration des Plugins.
   * 
   * @since 0.1
   */
  private Config _config;

  /**
   * Speichert den Zugriff zu dem MySQL-Server.
   * 
   * @since 0.1
   */
  private MySql _mysql;

  private boolean _enablePermission;

  /**
   * Wird aufgerufen, wenn das Plugin initialisiert wird.
   * 
   * @since 0.1
   */
  @Override
  public void onEnable() {
    super.onEnable();

    this._enablePermission = false;

    _config = new Config(this);

    this._mysql = new MySql(_config);

    this.registerCommands();
    this.registerEvents();
  }

  /**
   * Registriert alle Events des RM-Plugin
   * 
   * @since 0.1
   */
  private void registerEvents() {
    new BlockListener(this);
    new PlayerListener(this);
    new CommandListener(this);
  }

  /**
   * Registriert alle Befehle des RM-Plugin.
   * 
   * @since 0.1
   */
  private void registerCommands() {
    this.getCommand("rmplugin").setExecutor(new RMPluginCommand(this));
  }

  /**
   * Wird aufgerufen, wenn das RM-Plugin wieder aus dem Speicher gelöscht wird.
   * 
   * @since 0.1
   */
  @Override
  public void onDisable() {
    if (this._mysql.hasConnection()) {
        PreparedStatement ps = null;
        ResultSet rs = null;

      try {
        ps = this._mysql.getConnection().prepareStatement("SELECT b.id AS player_id FROM(SELECT a.player_id AS id, count(a.player_id) AS cnt FROM minecraft.minecraft_log_log_loggin AS a WHERE a.time > current_date() GROUP BY a.player_id) AS b WHERE MOD(b.cnt,2) <> 0");
        rs = ps.executeQuery();
        while (rs.next()) {
          PlayerListener.logout(rs.getInt("player_id"), this._mysql);
        }
      } catch (SQLException e) {
        e.printStackTrace();
      } finally {
        this._mysql.closeRessources(rs, ps);
      }

      this._mysql.closeConnection();
    }
  }

  /**
   * Gibt den Zugriff zum MySQL-Server zurück.
   * 
   * @since 0.1
   */
  public MySql getMySql() {
    return this._mysql;
  }

  /**
   * Gibt den Zugriff zur Konfiguration zurück.
   * 
   * @since 0.1
   */
  public Config getMyConfig() {
    return this._config;
  }
}
