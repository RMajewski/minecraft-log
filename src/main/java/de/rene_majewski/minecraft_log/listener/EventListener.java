package de.rene_majewski.minecraft_log.listener;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class EventListener implements Listener {
  public EventListener(JavaPlugin plugin) {
    plugin.getServer().getPluginManager().registerEvents(this, plugin);
  }
}