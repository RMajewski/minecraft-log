package de.rene_majewski.rm_plugin;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import de.rene_majewski.rm_plugin.commands.RMPluginCommand;
import de.rene_majewski.rm_plugin.config.Config;
import de.rene_majewski.rm_plugin.data.MySql;
import de.rene_majewski.rm_plugin.economy.EconomyManager;
import de.rene_majewski.rm_plugin.listener.BlockListener;
import de.rene_majewski.rm_plugin.listener.CommandListener;
import de.rene_majewski.rm_plugin.listener.PlayerListener;
import de.rene_majewski.rm_plugin.permissions.PermissionManager;
import de.rene_majewski.rm_plugin.warp.WarpManager;

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

  /**
   * Speichert den Logger.
   * 
   * @since 0.2
   */
  private Logger _logger;

  /**
   * Speichert den Permission-Manager.
   * 
   * @since 0.2
   */
  private PermissionManager _permissions;

  /**
   * Speichert das Sub-Plugin Economy.
   * 
   * @since 0.2
   */
  private EconomyManager _economy;

  /**
   * Speichert das Sub-Plugin Warps.
   * 
   * @since 0.2
   */
  private WarpManager _warp;

  /**
   * Wird aufgerufen, wenn das Plugin initialisiert wird.
   * 
   * @since 0.1
   */
  @Override
  public void onEnable() {
    super.onEnable();

    this._logger = getLogger();

    _config = new Config(this);

    this._mysql = new MySql(_config);
    this._permissions = new PermissionManager(this);
    this._economy = new EconomyManager(this);
    this._warp = new WarpManager(this);

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
        ps = this._mysql.getConnection().prepareStatement("SELECT b.id AS player_id FROM(SELECT a.player_id AS id, count(a.player_id) AS cnt FROM " + this._mysql.getTableName(Config.DB_TABLE_LOG_LOGGIN) + " AS a WHERE a.time > current_date() GROUP BY a.player_id) AS b WHERE MOD(b.cnt,2) <> 0");
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

  /**
   * Gibt den Zugriff zum Permissions-Manager zurück.
   * 
   * @return Objekt der Klasse {@link PermissionManager}.
   * 
   * @since 0.2
   */
  public PermissionManager getPermissionManager() {
    return this._permissions;
  }

  /**
   * Gibt den Zugriff zum Economy-Manager zurück.
   * 
   * @return Objekt der Klasse {@link EconomyManager}.
   * 
   * @since 0.2
   */
  public EconomyManager getEconomyManager() {
    return this._economy;
  }

  /**
   * Gibt den Zugriff zum Warp-Manager zurück.
   * 
   * @return Objekt der Klasse {@link WarpManager}.
   * 
   * @since 0.2
   */
  public WarpManager getWarpManager() {
    return this._warp;
  }

  /**
   * Gibt eine Message über den Logger aus.
   * 
   * @param message Nachricht, die über den Logger ausgegeben werden soll.
   * 
   * @since 0.2
   */
  public void logMessage(String message, Level level) {
    this._logger.log(level, message);
  }

  /**
   * Sendet eine Fehlernachricht zum angegeben Objekt und gibt es über den
   * Logger aus.
   * 
   * @param to Objekt, dass die Nachricht erhalten soll.
   * 
   * @param message Nachricht, die gesendet werden solll.
   * 
   * @param error Fehler, der aufgetreten ist.
   * 
   * @since 0.2
   */
  public void sendErrorMessage(Player to, String message, Exception error) {
    String color = this.getMyConfig().getString(Config.COLOR_ERROR_TEXT);
    this.sendMessage(to, color + this.getMyConfig().getString(Config.MESSAGE_ERROR) + ChatColor.RESET);

    if (error != null) {
      error.printStackTrace();
      
      if (to.hasPermission(Config.PERMISSION_ADMIN_DEBUG) && (error != null)) {
        this.sendMessage(to, this.getMyConfig().getString(Config.COLOR_ERROR_MESSAGE) + error.getMessage() + ChatColor.RESET);
      }

      this.logMessage(error.toString(), Level.WARNING);
    }

    if (message != null && !message.isEmpty()) {
      this.sendMessage(to, color + message + ChatColor.RESET);
    }
  }

  /**
   * Sendet eine Fehlernachricht zum angegeben Objekt und gibt es über den
   * Logger aus.
   * 
   * @param uuid UUID des Objektes, dass die Fehlernachricht erhaltne soll.
   * 
   * @param message Nachricht, die gesendet werden soll.
   * 
   * @param error Fehler, der aufgetreten ist.
   * 
   * @since 0.2
   */
  public void sendErrorMessage(String uuid, String message, Exception error) {
    this.sendErrorMessage(this.getPlayerFromUuid(uuid), message, error);
  }

  /**
   * Sendet die Nachricht zum Spieler.
   * 
   * @param to Spieler, an den die Nachricht gesendet werden soll.
   * 
   * @param message Nachricht, die dem Spieler gesendet werden soll.
   * 
   * @since 0.2
   */
  public void sendMessage(Player to, String message) {
    to.sendMessage(message);
  }

  /**
   * Sendet die Nachricht zum angegeben Spieler.
   * 
   * @param uuid UUID des Spielers, an den die Nachricht gesendet werden soll.
   * 
   * @param message Nachricht, die dem Spieler gesendet werden soll.
   * 
   * @since 0.2
   */
  public void sendMessage(String uuid, String message) {
    this.sendMessage(this.getPlayerFromUuid(uuid), message);
  }

  /**
   * Ermittelt das Spieler-Objekt aus der UUID.
   * 
   * @param uuid UUID dessen Spieler-Objekt ermittelt werden soll.
   * 
   * @return {@link Player}-Objekt, wenn der der Spieler existiert.
   * {@code null}, wenn der Spieler nicht existiert.
   * 
   * @since 0.2
   */
  public Player getPlayerFromUuid(String uuid) {
    return this.getServer().getPlayer(UUID.fromString(uuid));
  }

  /**
   * Ermittelt die UUID des Spielers aus dem Display-Namen.
   * 
   * @param name Name des Spielers, dessen UUID ermittelt werden soll.
   * 
   * @return UUID des Spielers. Konnte der Spieler nicht gefunden werden, so
   * wird {@code null} zurück gegeben.
   * 
   * @since 0.2
   */
  public String getPlayerFromDisplayName(String name) {
    String result = "";

    OfflinePlayer[] players = getServer().getOfflinePlayers();
    for (int i = 0; i < players.length; i++ ) {
      if (players[i].getName().equals(name)) {
        result = players[i].getUniqueId().toString();
        break;
      }
    }
    return result;
  }

  /**
   * Sendet die Nachricht "Keine Berechtigung" zum angegebenen Spieler.
   * 
   * @param to Spieler, zu dem die Nachricht gesendet werden soll.
   * 
   * @since 0.2
   */
  public void sendNoPermission(Player to) {
    this.sendMessage(to, this._config.getString(Config.MESSAGE_NO_PERMISSION));
  }

  /**
   * Sendet die Nachricht "Keine Berechtigung" zum angegebenen Spieler.
   * 
   * @param uuid UUID des Spieler, zu dem die Nachricht gesendet werden soll.
   * 
   * @since 0.2
   */
  public void sendNoPermission(String uuid) {
    this.sendNoPermission(this.getServer().getPlayer(UUID.fromString(uuid)));
  }
}
