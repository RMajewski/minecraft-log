package de.rene_majewski.rm_plugin.listener;

import org.bukkit.event.Listener;

import de.rene_majewski.rm_plugin.RMPlugin;

public class EventListener implements Listener {
  protected RMPlugin _plugin;

  public EventListener(RMPlugin plugin) {
    this._plugin = plugin;
    this._plugin.getServer().getPluginManager().registerEvents(this, this._plugin);
  }
}