package de.rene_majewski.rm_plugin.economy.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;

import de.rene_majewski.rm_plugin.RMPlugin;
import de.rene_majewski.rm_plugin.config.Config;
import de.rene_majewski.rm_plugin.economy.EconomyManager;
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

    String tmp = this._plugin.getMyConfig().getString(Config.MESSAGE_ECONOMY_OWN_MONEY);
    tmp = tmp.replaceFirst("\\?", String.valueOf(this._plugin.getEconomyManager().getBalance(event.getPlayer().getUniqueId().toString())));
    tmp = tmp.replace("?", this._plugin.getMyConfig().getString(Config.ECONOMY_CURRENCY));
    event.getPlayer().sendMessage(tmp);
  }
}