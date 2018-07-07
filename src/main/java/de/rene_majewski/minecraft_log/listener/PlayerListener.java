package de.rene_majewski.minecraft_log.listener;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerChatEvent;

import de.rene_majewski.minecraft_log.MinecraftLog;

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
  }
}