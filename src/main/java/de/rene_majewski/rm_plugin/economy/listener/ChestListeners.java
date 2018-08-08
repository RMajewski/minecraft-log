package de.rene_majewski.rm_plugin.economy.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import de.rene_majewski.rm_plugin.RMPlugin;
import de.rene_majewski.rm_plugin.listener.EventListener;

/**
 * Reagiert auf Ereignisse die mit der Interaktion mit Kisten entstehen.
 * 
 * @author René Majewski
 * @since 0.2
 */
public class ChestListeners extends EventListener {
  /**
   * Initialisiert den Ereignis-Klasse.
   * 
   * @param plugin Objekt der Plugin-Main-Klasse.
   * 
   * @since 0.2
   */
  public ChestListeners(RMPlugin plugin) {
    super(plugin);
  }

  /**
   * Wird aufgerufen, wenn ein Spieler auf eine Kiste klickt.
   * 
   * @param event Daten des Events.
   * 
   * @since 0.2
   */
  @EventHandler(priority = EventPriority.HIGH)
  public void onInteract(PlayerInteractEvent event) {
    Player player = event.getPlayer();

    // Verkaufen
    if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
      player.sendMessage("Du wolltest etwas verkaufen");
    }

    // Kaufen
    else if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
      player.sendMessage("Du wolltest etwas kaufen");
    }
  }
 
}