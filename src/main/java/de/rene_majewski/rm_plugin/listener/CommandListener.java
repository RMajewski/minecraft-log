package de.rene_majewski.rm_plugin.listener;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import de.rene_majewski.rm_plugin.MinecraftLog;
import de.rene_majewski.rm_plugin.config.Config;

/**
* Reagiert auf die Eingabe von Befehlen und logt diese mit.
* 
* @author René Majewski
* @since 0.1
*/
public class CommandListener extends EventListener {
  /**
   * Initialisiert den Ereignis-Klasse.
   */
  public CommandListener(MinecraftLog plugin) {
    super(plugin);
  }

  @EventHandler(priority = EventPriority.MONITOR)
  public void onCommandEnter(PlayerCommandPreprocessEvent event) {
    Player player = event.getPlayer();
    String cmd = event.getMessage();

    int id = -1;

    if (player != null) {
      id = this._plugin.getMySql().getPlayerId(player);
    }
    PreparedStatement ps = null;
    try {
      ps = this._plugin.getMySql().getConnection().prepareStatement(
        "INSERT INTO " + this._plugin.getMySql().getTableName(Config.DB_TABLE_LOG_COMMAND) + " (player_id, command) VALUES (?, ?)");
      ps.setInt(1, id);
      ps.setString(2, cmd);
      ps.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      this._plugin.getMySql().closeRessources(null, ps);
    }
  }

}
