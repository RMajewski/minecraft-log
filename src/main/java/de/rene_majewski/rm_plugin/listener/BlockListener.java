package de.rene_majewski.rm_plugin.listener;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import de.rene_majewski.rm_plugin.RMPlugin;
import de.rene_majewski.rm_plugin.config.Config;

/**
 * Reagiert auf Block-Ereignisse.
 * 
 * @author René Majewski
 * @since 0.1
 */
public class BlockListener extends EventListener {
  /**
   * Initialisiert den Ereignis-Klasse.
   */
  public BlockListener(RMPlugin plugin) {
    super(plugin);
  }

  /**
   * Reagiert darauf, wenn ein Block zerbrochen werden.
   * 
   * @param event Daten des Ereignisses.
   */
  @EventHandler(priority = EventPriority.MONITOR)
  public void onBlockBreak(BlockBreakEvent event) {
    int block_id = this._plugin.getMySql().getBlockdId(event.getBlock());
    int player_id = this._plugin.getMySql().getPlayerId(event.getPlayer());
    int world_id = this._plugin.getMySql().getWorldId(event.getBlock().getWorld());

    PreparedStatement ps = null;
    try {
      ps = this._plugin.getMySql().getConnection().prepareStatement(
        "INSERT INTO " + this._plugin.getMySql().getTableName(Config.DB_TABLE_LOG_BLOCK) + " (block_id, player_id, world_id, x, y, z, type) VALUES (?, ?, ?, ?, ?, ?, ?)");
      ps.setInt(1, block_id);
      ps.setInt(2, player_id);
      ps.setInt(3, world_id);
      ps.setInt(4, event.getBlock().getX());
      ps.setInt(5, event.getBlock().getY());
      ps.setInt(6, event.getBlock().getZ());
      ps.setInt(7, 1);
      ps.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      this._plugin.getMySql().closeRessources(null, ps);
    }
  }

  /**
   * Reagiert darauf, wenn ein Block gesetzt wird.
   * 
   * @param event Daten des Ereignisses.
   */
  @EventHandler(priority = EventPriority.MONITOR)
  public void onBlockPlace(BlockPlaceEvent event) {
    int block_id = this._plugin.getMySql().getBlockdId(event.getBlock());
    int player_id = this._plugin.getMySql().getPlayerId(event.getPlayer());
    int world_id = this._plugin.getMySql().getWorldId(event.getBlock().getWorld());

    PreparedStatement ps = null;
    try {
      ps = this._plugin.getMySql().getConnection().prepareStatement(
        "INSERT INTO " + this._plugin.getMySql().getTableName(Config.DB_TABLE_LOG_BLOCK) + " (block_id, player_id, world_id, x, y, z, type) VALUES (?, ?, ?, ?, ?, ?, ?)");
      ps.setInt(1, block_id);
      ps.setInt(2, player_id);
      ps.setInt(3, world_id);
      ps.setInt(4, event.getBlock().getX());
      ps.setInt(5, event.getBlock().getY());
      ps.setInt(6, event.getBlock().getZ());
      ps.setInt(7, 2);
      ps.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      this._plugin.getMySql().closeRessources(null, ps);
    }
  }
}