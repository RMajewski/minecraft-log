package de.rene_majewski.rm_plugin.listener;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;

import de.rene_majewski.rm_plugin.RMPlugin;
import de.rene_majewski.rm_plugin.config.Config;

/**
 * Überprüft ob der Spieler gebannt wurde. Wurde der Spieler gebannt, so wird
 * er nicht auf den Server gelassen.
 * 
 * @author René Majewski
 * @since 0.2
 */
public class BanListener extends EventListener {
  /**
   * Initialisiert die Klasse.
   * 
   * @param plugin Objekt der Plugin-Main-Klasse.
   * 
   * @since 0.2
   */
  public BanListener(RMPlugin plugin) {
    super(plugin);
  }

  /**
   * Überprüft beim betreten eines Spielers, ob er gebannt ist. Wurde der
   * Spieler gebannt, wird er nicht auf den Server gelassen.
   * 
   * @param event Daten des Events.
   * 
   * @since 0.2
   */
  @EventHandler(priority = EventPriority.HIGHEST)
  public void onPlayerJoin(PlayerJoinEvent event) {
    PreparedStatement ps = null;
    ResultSet rs = null;

    try {
      int pid = this._plugin.getMySql().getPlayerId(event.getPlayer());
      if (pid > 0) {
        ps = this._plugin.getMySql().getConnection().prepareStatement(
          "SELECT player_id, description FROM " +
          this._plugin.getMySql().getTableName(Config.DB_TABLE_BAN) +
          " WHERE player_id = ?"
        );
        ps.setInt(1, pid);
        rs = ps.executeQuery();

        if (rs.next() && (rs.getInt("player_id") == pid)) {
          event.getPlayer().kickPlayer(this._plugin.getMyConfig().getString(Config.MESSAGE_BAN_KICK).replace("?", rs.getString("description")));
        }
      }
    } catch (SQLException e) {
      this._plugin.sendErrorMessage(event.getPlayer(), this._plugin.getMyConfig().getString(Config.MESSAGE_ERROR), e);
    } finally {
      this._plugin.getMySql().closeRessources(rs, ps);
    }
  }
}