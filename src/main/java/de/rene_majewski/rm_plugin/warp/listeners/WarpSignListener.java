package de.rene_majewski.rm_plugin.warp.listeners;

import java.util.logging.Level;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.SignChangeEvent;

import de.rene_majewski.rm_plugin.RMPlugin;
import de.rene_majewski.rm_plugin.config.Config;
import de.rene_majewski.rm_plugin.listener.EventListener;

/**
* Reagiert auf die Benutzung von Schildern für Warps.
* 
* @author René Majewski
* @since 0.1
*/
public class WarpSignListener extends EventListener {
  /**
   * Initialisiert den Listener.
   * 
   * @param plugin Objekt der Plugin-Main-Klasse.
   * 
   * @since 0.2
   */
  public WarpSignListener(RMPlugin plugin) {
    super(plugin);
  }

  /**
   * Reagiert darauf, wenn ein Schild geändert wird.
   * 
   * @param event Daten des Events.
   * 
   * @since 0.2
   */
  @EventHandler
  public void onSignChange(SignChangeEvent event) {
    Player player = event.getPlayer();

    if (event.getLine(0).equalsIgnoreCase("[warp]")) {
      if (event.getLine(1).equalsIgnoreCase("destination")) {
        if (player.hasPermission(Config.PERMISSION_WARP_SET_WARP)) {
          this._plugin.getWarpManager().setWarp(player, event.getLine(2));
        }
      }
    }
  }
}