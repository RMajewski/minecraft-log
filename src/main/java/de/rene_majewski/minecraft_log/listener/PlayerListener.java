package de.rene_majewski.minecraft_log.listener;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import de.rene_majewski.minecraft_log.MinecraftLog;
import de.rene_majewski.minecraft_log.config.Config;

/**
 * Reagiert auf Spieler-Ereignisse.
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
      PreparedStatement ps = null;

      try {
        ps = this._plugin.getMySql().getConnection().prepareStatement("INSERT INTO " + this._plugin.getMySql().getTableName(Config.DB_TABLE_LOG_LOGGIN) + " (player_id, status) VALUES (?, ?)");
        ps.setInt(1, id);
        ps.setInt(2, 0);
        ps.execute();
      } catch (SQLException e) {
        e.printStackTrace();
      } finally {
        this._plugin.getMySql().closeRessources(null, ps);
      }
    }
  }
}