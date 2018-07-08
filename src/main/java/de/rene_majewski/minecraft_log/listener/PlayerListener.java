package de.rene_majewski.minecraft_log.listener;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerChatEvent;

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

  @EventHandler
  public void onChatEvent(PlayerChatEvent event) {
    int id = this._plugin.getMySql().getPlayerId(event.getPlayer());

    if (id > 0) {
      PreparedStatement ps = null;
      
      try {
        ps = this._plugin.getMySql().getConnection().prepareStatement("INSERT INTO ? (player_id, message) VALUES(?, ?)");
        ps.setString(1, this._plugin.getMySql().getTableName(Config.DB_TABLE_LOG_CHAT));
        ps.setInt(2, id);
        ps.setString(3, event.getMessage());
        ps.execute();
      } catch (SQLException e) {
        e.printStackTrace();
      } finally {
        this._plugin.getMySql().closeRessources(null, ps);
      }
    }
  }
}