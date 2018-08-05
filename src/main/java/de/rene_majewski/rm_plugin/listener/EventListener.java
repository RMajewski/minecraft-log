package de.rene_majewski.rm_plugin.listener;

import org.bukkit.event.Listener;

import de.rene_majewski.rm_plugin.RMPlugin;

/**
 * Von dieser Klasse werden alle Event-Listener 
 * 
 * @author René Majewski
 * @since 0.1
 */
public class EventListener implements Listener {
  /**
   * Speichert die Main-Klasse des Plugins.
   */
  protected RMPlugin _plugin;

  /**
   * Initialisiert den Event-Listener.
   */
  public EventListener(RMPlugin plugin) {
    this._plugin = plugin;
    this._plugin.getServer().getPluginManager().registerEvents(this, this._plugin);
  }
}