package de.rene_majewski.rm_plugin.economy.listener;

import java.util.logging.Level;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;

import de.rene_majewski.rm_plugin.RMPlugin;
import de.rene_majewski.rm_plugin.listener.EventListener;

/**
 * Reagiert auf Spieler-Ereignisse.
 * 
 * @author René Majewski
 * @since 0.2
 */
public class EconomyPlayerListener extends EventListener {
  /**
   * Initialisiert die Ereignis-Klasse.
   */
  public EconomyPlayerListener(RMPlugin plugin) {
    super(plugin);

    plugin.logMessage("EconomyPlayerListener wurde initialisiert.", Level.INFO);
  }

  /**
   * Wenn sich ein Player einloggt, wird überprüft, ob der Spieler schon im
   * {@link EconomyManager} registriert ist. Ist dies nicht der Fall wird er
   * angelegt und ihm die Standard Summer überwiesen.
   * 
   * @param event Daten des Ereignisses.
   */
  @EventHandler(priority = EventPriority.NORMAL)
  public void onPlayerJoin(PlayerJoinEvent event) {
    this._plugin.getEconomyManager().playerJoin(event.getPlayer().getUniqueId().toString());

    event.getPlayer().sendMessage("Dein Betrag: " + String.valueOf(this._plugin.getEconomyManager().getBalance(event.getPlayer().getUniqueId().toString())));
  }
}