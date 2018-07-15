package de.rene_majewski.rm_plugin.listener;

import org.bukkit.event.Listener;

import de.rene_majewski.rm_plugin.MinecraftLog;

public class EventListener implements Listener {
  protected MinecraftLog _plugin;

  public EventListener(MinecraftLog plugin) {
    this._plugin = plugin;
    this._plugin.getServer().getPluginManager().registerEvents(this, this._plugin);
  }
}