package de.rene_majewski.minecraft_log.listener;

import org.bukkit.event.Listener;

import de.rene_majewski.minecraft_log.MinecraftLog;

public class EventListener implements Listener {
  protected MinecraftLog _plugin;

  public EventListener(MinecraftLog plugin) {
    this._plugin = plugin;
    this._plugin.getServer().getPluginManager().registerEvents(this, this._plugin);
  }
}