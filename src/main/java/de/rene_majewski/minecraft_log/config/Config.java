package de.rene_majewski.minecraft_log.config;

import org.bukkit.plugin.java.JavaPlugin;

public final class Config {
  private JavaPlugin _plugin;

  public static final String MESSAGE_NO_PERMISSION = "minecraftlog.messages.no_permission";
  public static final String MESSAGE_CONFIG_RELOAD = "minecraftlog.messages.config_reload";

  public static final String DB_CONFIG_HOST = "minecraftlog.db.host";
  public static final String DB_CONFIG_PORT = "minecraftlog.db.port";
  public static final String DB_CONFIG_USER = "minecraftlog.db.user";
  public static final String DB_CONFIG_PASSWORD = "minecraftlog.db.password";
  public static final String DB_CONFIG_DATABASE = "minecraftlog.db.database";
  public static final String DB_CONFIG_PREFIX = "minecraftlog.db.prefix";

  public static final String DB_TABLE_PLAYER = "minecraftlog.db.tables.player";
  public static final String DB_TABLE_WORLD = "minecraftlog.db.tables.world";
  public static final String DB_TABLE_LOG_COMMAND = "minecraftlog.db.tables.log_command";
  public static final String DB_TABLE_LOG_CHAT = "minecraftlog.db.tables.log_chat";
  public static final String DB_TABLE_LOG_LOGGIN = "minecraftlog.db.tables.log_loggin";
  public static final String DB_TABLE_LOG_WORLD_CHANGE = "minecraftlog.db.tables.log_world_change";

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
    return this._plugin.getConfig().getString(path);
  }

  public int getInteger(String path) {
    return this._plugin.getConfig().getInt(path);
  }

  private void setDefaults() {
    _plugin.getConfig().addDefault(MESSAGE_NO_PERMISSION, "Du hast keine Berechtigung für diesen Befehl.");
    _plugin.getConfig().addDefault(MESSAGE_CONFIG_RELOAD, "Du Konfiguration wurde erfolgreich neu geladen");

    _plugin.getConfig().addDefault(DB_CONFIG_HOST, "localhost");
    _plugin.getConfig().addDefault(DB_CONFIG_PORT, 3306);
    _plugin.getConfig().addDefault(DB_CONFIG_USER, "user");
    _plugin.getConfig().addDefault(DB_CONFIG_PASSWORD, "");
    _plugin.getConfig().addDefault(DB_CONFIG_DATABASE, "minecraft");
    _plugin.getConfig().addDefault(DB_CONFIG_PREFIX, "minecraft_log_");

    _plugin.getConfig().addDefault(DB_TABLE_PLAYER, "player");
    _plugin.getConfig().addDefault(DB_TABLE_WORLD, "world");
    _plugin.getConfig().addDefault(DB_TABLE_LOG_COMMAND, "log_command");
    _plugin.getConfig().addDefault(DB_TABLE_LOG_CHAT, "log_chat");
    _plugin.getConfig().addDefault(DB_TABLE_LOG_LOGGIN, "log_loggin");
    _plugin.getConfig().addDefault(DB_TABLE_LOG_WORLD_CHANGE, "log_world_change");
  }
}