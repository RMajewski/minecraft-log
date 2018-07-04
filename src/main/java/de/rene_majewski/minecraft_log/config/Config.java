package de.rene_majewski.minecraft_log.config;

import org.bukkit.plugin.java.JavaPlugin;

public final class Config {
  private JavaPlugin _plugin;

  public Config(JavaPlugin plugin) {
    this._plugin = plugin;

    initConfig();
  }

  private void initConfig() {
    _plugin.reloadConfig();
    _plugin.getConfig().options().header("MinecraftLog by TerraGermany");

    _plugin.getConfig().options().copyDefaults(true);
    save();
  }

  public void save() {
    _plugin.saveConfig();
  }
}