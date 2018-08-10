package de.rene_majewski.rm_plugin.warp.listeners;

import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import de.rene_majewski.rm_plugin.RMPlugin;
import de.rene_majewski.rm_plugin.config.Config;
import de.rene_majewski.rm_plugin.listener.EventListener;

/**
 * Reagiert auf Spieler-Ereignisse.
 * 
 * @author René Majewski
 * @since 0.2
 */
public class WarpPlayerListener extends EventListener {
  /**
   * Initialisiert den Listener.
   * 
   * @param plugin Objekt der Plugin-Main-Klasse.
   * 
   * @since 0.2
   */
  public WarpPlayerListener(RMPlugin plugin) {
    super(plugin);
  }

  /**
   * Reagiert auf Spieler-Interaktionen.
   * 
   * @param event Daten des Events.
   * 
   * @since 0.2
   */
  @EventHandler
  public void onPlayerInteractive(PlayerInteractEvent event) {
    Player player = event.getPlayer();

    if ((event.getAction() == Action.RIGHT_CLICK_BLOCK) || (event.getAction() == Action.LEFT_CLICK_BLOCK)) {
      if (event.getClickedBlock().getState() instanceof Sign) {
        Sign sign = (Sign)event.getClickedBlock().getState();
        if (sign.getLine(0).equalsIgnoreCase("[Warp]")) {
          if (player.hasPermission(Config.PERMISSION_WARP_WARP)) {
            this._plugin.getWarpManager().warp(player, sign.getLine(1));
          } else {
            this._plugin.sendNoPermission(player);
          }
        }
      }
    }
  }
}