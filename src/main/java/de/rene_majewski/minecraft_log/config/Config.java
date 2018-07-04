package de.rene_majewski.minecraft_log.config;

import org.bukkit.plugin.java.JavaPlugin;

public final class Config {
  private JavaPlugin _plugin;

  public static final String MESSAGE_NO_PERMISSION = "minecraftlog.messages.no_permission";
  public static final String MESSAGE_CONFIG_RELOAD = "minecraftlog.messages.config_reload";

  public static final String PERMISSION_ADMIN_RELOAD = "minecraftlog.admin.reload";

  public Config(JavaPlugin plugin) {
    this._plugin = plugin;

    this.setDefaults();

    _plugin.getConfig().options().header("MinecraftLog by TerraGermany");

    _plugin.getConfig().options().copyDefaults(true);
    save();
  }

  public void save() {
    _plugin.saveConfig();
  }

  public void reload() {
    _plugin.reloadConfig();
  }

  public String getString(String path) {
    System.out.println(this._plugin.getName());
    return this._plugin.getConfig().getString(path);
  }

  private void setDefaults() {
    _plugin.getConfig().addDefault(MESSAGE_NO_PERMISSION, "Du hast keine Berechtigung für diesen Befehl.");
    _plugin.getConfig().addDefault(MESSAGE_CONFIG_RELOAD, "Du Konfiguration wurde erfolgreich neu geladen");
  }
}