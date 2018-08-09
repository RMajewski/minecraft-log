package de.rene_majewski.rm_plugin.economy.listener;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import de.rene_majewski.rm_plugin.RMPlugin;
import de.rene_majewski.rm_plugin.config.Config;
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
      Chest shop = getShopChest(event);
      if (shop != null) {
        sell(player);
      }
    }

    // Kaufen
    // else if ((event.getAction() == Action.LEFT_CLICK_BLOCK) && ((event.getClickedBlock().getState() instanceof Sign) || (event.getClickedBlock().getState() instanceof Chest))) {
    //   buy(player);
    // }
  }

  /**
   * Wird aufgerufen, wenn der Spieler etwas kauft.
   * 
   * @since 0.2
   */
  private void buy(Player player) {
    player.sendMessage("Du wolltest etwas kaufen.");
  }

  /**
   * Wird aufgerufen, wenn der Spieler etwas verkauft.
   * 
   * @since 0.2
   */
  private void sell(Player player) {
    player.sendMessage("Du wolltest etwas verkaufen.");
  }

  /**
   * Überprüft, ob der angegebene Block eine Shop-Kiste ist.
   * 
   * @param event Daten des Events Spieler interagiert.
   * 
   * @return {@code null}, wenn es sich nicht um eine Shop-Kiste handelt.
   * Handelt es sich um eine Shop-Kiste, wird diese zurückgegeben.
   * 
   * @since 0.2
   */
  private Chest getShopChest(PlayerInteractEvent event) {
    Block block = event.getClickedBlock();

    if (block.getType() == Material.WALL_SIGN) {
      org.bukkit.block.Sign sign = (org.bukkit.block.Sign)block.getState();

      if (sign.getLine(0).equals("[" + this._plugin.getMyConfig().getString(Config.ECONOMY_SHOP_SIGN_NAME) + "]")) {
        org.bukkit.material.Sign s = (org.bukkit.material.Sign)sign.getData();

        if (block.getRelative(s.getAttachedFace()).getType() == Material.CHEST) {
          return (Chest)block.getRelative(s.getAttachedFace()).getState();
        }
      }
    }

    return null;
  }
}