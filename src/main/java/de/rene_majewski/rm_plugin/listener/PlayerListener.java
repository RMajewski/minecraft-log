package de.rene_majewski.rm_plugin.listener;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import de.rene_majewski.rm_plugin.MinecraftLog;
import de.rene_majewski.rm_plugin.config.Config;
import de.rene_majewski.rm_plugin.data.MySql;

/**
 * Reagiert auf Spieler-Ereignisse.
 * 
 * @author René Majewski
 * @since 0.1
 */
public class PlayerListener extends EventListener {
  /**
   * Initalisiert die Ereignis-Klasse.
   */
  public PlayerListener(MinecraftLog plugin) {
    super(plugin);
  }

  /**
   * Reagiert auf Chat-Ereignisse.
   * 
   * @param event Daten des Events.
   */
  @EventHandler(priority = EventPriority.MONITOR)
  public void onChatEvent(AsyncPlayerChatEvent event) {
    int id = this._plugin.getMySql().getPlayerId(event.getPlayer());

    if (id > 0) {
      PreparedStatement ps = null;
      
      try {
        ps = this._plugin.getMySql().getConnection().prepareStatement("INSERT INTO " + this._plugin.getMySql().getTableName(Config.DB_TABLE_LOG_CHAT) + " (player_id, message) VALUES(?, ?)");
        ps.setInt(1, id);
        ps.setString(2, event.getMessage());
        ps.execute();
      } catch (SQLException e) {
        e.printStackTrace();
      } finally {
        this._plugin.getMySql().closeRessources(null, ps);
      }
    }
  }

  /**
   * Reagiert auf Login-Ereignisse.
   * 
   * @param event Daten des Events
   */
  @EventHandler(priority = EventPriority.MONITOR)
  public void onPlayerJoin(PlayerJoinEvent event) {
    int id =  this._plugin.getMySql().getPlayerId(event.getPlayer());

    if (id > 0) {
      PreparedStatement ps = null;

      try {
        ps = this._plugin.getMySql().getConnection().prepareStatement("INSERT INTO " + this._plugin.getMySql().getTableName(Config.DB_TABLE_LOG_LOGGIN) + " (player_id, status) VALUES (?, ?)");
        ps.setInt(1, id);
        ps.setInt(2, 1);
        ps.execute();
      } catch (SQLException e) {
        e.printStackTrace();
      } finally {
        this._plugin.getMySql().closeRessources(null, ps);
      }
    }
  }

  /**
   * Reagiert auf Logout-Ereignisse.
   * 
   * @param event Daten das Events.
   */
  @EventHandler(priority = EventPriority.MONITOR)
  public void onPlayerQuit(PlayerQuitEvent event) {
    int id = this._plugin.getMySql().getPlayerId(event.getPlayer());

    if (id > 0) {
      PlayerListener.logout(id, this._plugin.getMySql());
    }
  }

  /**
   * Reagiert auf Ereignisse, wenn ein Spieler die Welt wechselt.
   * 
   * @param event Daten des Ereignisses.
   */
  @EventHandler(priority = EventPriority.MONITOR)
  public void onPlayerChangeWorld(PlayerChangedWorldEvent event) {
    int player = this._plugin.getMySql().getPlayerId(event.getPlayer());
    int world = this._plugin.getMySql().getWorldId(event.getPlayer().getWorld());

    if ((player > 0) && (world > 0)) {
      PreparedStatement ps = null;

      try {
        ps = this._plugin.getMySql().getConnection().prepareStatement("INSERT INTO " + this._plugin.getMySql().getTableName(Config.DB_TABLE_LOG_WORLD_CHANGE) + " (player_id, world_id, x, y, z) VALUES (?, ?, ?, ?, ?)");
        ps.setInt(1, player);
        ps.setInt(2, world);
        ps.setDouble(3, event.getPlayer().getLocation().getX());
        ps.setDouble(4, event.getPlayer().getLocation().getY());
        ps.setDouble(5, event.getPlayer().getLocation().getZ());
        ps.executeUpdate();
      } catch (SQLException e) {
        e.printStackTrace();
      } finally {
        this._plugin.getMySql().closeRessources(null, ps);
      }
    }
  }

  /**
   * Logt den angegebenen Spieler aus der Datenbank aus.
   * 
   * @param id ID des Spielers, der ausgelogt werden soll.
   * 
   * @param mysql MySQL-Klasse, über die die Verbindung zur Datenbank
   * hergestellt wurde.
   */
  public static void logout(int id, MySql mysql) {
    PreparedStatement ps = null;

    try {
      ps = mysql.getConnection().prepareStatement("INSERT INTO " + mysql.getTableName(Config.DB_TABLE_LOG_LOGGIN) + " (player_id, status) VALUES (?, ?)");
      ps.setInt(1, id);
      ps.setInt(2, 0);
      ps.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      mysql.closeRessources(null, ps);
    }
  }
}