package de.rene_majewski.rm_plugin.config;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Klasse für die Konfiguration.
 * 
 * @author René Majewski
 * @since 0.1
 */
public final class Config {
  /**
   * Speichert die Plugin-Klasse.
   * 
   * @see RMPlugin
   * 
   * @since 0.1
   */
  private JavaPlugin _plugin;

  /**
   * Gibt den Pfad zur Nachricht für "Keine Berechtigung" an.
   * 
   * @since 0.1
   */
  public static final String MESSAGE_NO_PERMISSION = "rmplugin.messages.no_permission";

  /**
   * Gibt den Pfad zur Nachricht für "Konfiguration neu geladen" an.
   * 
   * @since 0.1
   */
  public static final String MESSAGE_CONFIG_RELOAD = "rmplugin.messages.config_reload";

  /**
   * Gibt den Pfad zur Einstellung für den Datenbank-Server an.
   * 
   * @since 0.1
   */
  public static final String DB_CONFIG_HOST = "rmplugin.db.host";

  /**
   * Gibt den Pfad zur Einstellung für den Port des Datenbank-Servers an.
   * 
   * @since 0.1
   */
  public static final String DB_CONFIG_PORT = "rmplugin.db.port";

  /**
   * Gibt den Pfad zur Einstellung für den Benutzer des Datenbank-Servers an.
   * 
   * @since 0.1
   */
  public static final String DB_CONFIG_USER = "rmplugin.db.user";

  /**
   * Gibt den Pfad zur Einstellung des Passworts des Datenbank-Servers an.
   * 
   * @since 0.1
   */
  public static final String DB_CONFIG_PASSWORD = "rmplugin.db.password";

  /**
   * Gibt den Pfad zur Einstellung des Namens der Datenbank auf dem
   * Datenbank-Server an.
   * 
   * @since 0.1
   */
  public static final String DB_CONFIG_DATABASE = "rmplugin.db.database";

  /**
   * Gibt den Pfad zur Einstellung des Tabellen-Präfixes für die Tabellen des
   * Datenbank-Servers an.
   */
  public static final String DB_CONFIG_PREFIX = "rmplugin.db.prefix";

  /**
   * Gibt den Pfad zur Einstellung der Tabelle für die Spielernamen an.
   * 
   * @since 0.1
   */
  public static final String DB_TABLE_PLAYER = "rmplugin.db.tables.player";

  /**
   * Gibt den Pfad zur Einstellung der Tabelle für die Welten an.
   * 
   * @since 0.1
   */
  public static final String DB_TABLE_WORLD = "rmplugin.db.tables.world";

  /**
   * Gibt den Pfad zur Einstellung der Tabelle für die existierenden Blöcke an.
   * 
   * @since 0.1
   */
  public static final String DB_TABLE_BLOCK = "rmplugin.db.tables.block";

  /**
   * Gibt den Pfad zur Einstellung der Tabelle zum Loggen der Befehle an.
   * 
   * @since 0.1
   */
  public static final String DB_TABLE_LOG_COMMAND = "rmplugin.db.tables.log_command";

  /**
   * Gibt den Pfad zur Einstellung der Tabelle zum Loggen des Chats an.
   * 
   * @since 0.1
   */
  public static final String DB_TABLE_LOG_CHAT = "rmplugin.db.tables.log_chat";

  /**
   * Gibt den Pfad zur Einstellung der Tabelle zum Loggen für Log ins und Log
   * outs an.
   * 
   * @since 0.1
   */
  public static final String DB_TABLE_LOG_LOGGIN = "rmplugin.db.tables.log_loggin";

  /**
   * Gibt den Pfad zur Einstellung der Tabelle zum Loggen zum Betreten ein Welt
   * an.
   * 
   * @since 0.1
   */
  public static final String DB_TABLE_LOG_WORLD_CHANGE = "rmplugin.db.tables.log_world_change";

  /**
   * Gibt den Pfad zur Einstellung der Tabelle zum Loggen der Interaktion mit
   * Blöcken an.
   * 
   * @since 0.1
   */
  public static final String DB_TABLE_LOG_BLOCK = "rmplugin.db.tables.log_block";


  /**
   * Gib den Namen der Permission zum Neuladen der Konfiguration an.
   * 
   * @since 0.1
   */
  public static final String PERMISSION_ADMIN_RELOAD = "rmplugin.admin.reload";

  /**
   * Gibt den Namen der Permission zum Anzeigen des Hilfetextes an.
   * 
   * @since 0.2
   */
  public static final String PERMISSION_COMMAND_HELP = "rmplugin.help";


  /**
   * Gibt den Namen zur Einstellung der Farbe zum hervorben einen Befehls in
   * der Hilfe an.
   * 
   * @since 0.2
   */
  public static final String COLOR_HELP_COMMAND = "rmplugin.color.help.command";


  /**
   * Gibt den Namen zur Einstellung der Farbe zum normalen Hilfe-Text an.
   * 
   * @since 0.2
   */
  public static final String COLOR_HELP_TEXT = "rmplugin.color.help.text";


  /**
   * Initialisiert die Konfiguration.
   * 
   * @param plugin Objekt der Plugin-Klasse {@link RMPlugin}.
   * 
   * @since 0.1
   */
  public Config(JavaPlugin plugin) {
    this._plugin = plugin;

    this.setDefaults();

    _plugin.getConfig().options().header("RM-Plugin by TerraGermany");

    _plugin.getConfig().options().copyDefaults(true);
    save();
  }

  /**
   * Speichert die Konfguration
   * 
   * @since 0.1
   */
  public void save() {
    _plugin.saveConfig();
  }

  /**
   * Lädt die Konfiguratio neu.
   * 
   * @since 0.1
   */
  public void reload() {
    _plugin.reloadConfig();
  }

  /**
   * Liest eine Zeichenkette aus der Konfiguration.
   * 
   * @param path Pfad zur Zeichenkette, die zurückgegeben werden soll.
   * 
   * @return Gelsene Zeichenkette aus der Konfiguration.
   * 
   * @since 0.1
   */
  public String getString(String path) {
    return this._plugin.getConfig().getString(path);
  }

  /**
   * Liest eine Ganzzahl aus der Konfiguration.
   * 
   * @param path Pfaf zur Ganzzahl, die aus der Konfiguration gelesen werden
   * soll.
   * 
   * @return Aus der Konfiguration ermittelte Ganzzahl.
   * 
   * @since 0.1
   */
  public int getInteger(String path) {
    return this._plugin.getConfig().getInt(path);
  }

  /**
   * Setzt die Default-Werte zu den einzelnen Pfaden.
   */
  private void setDefaults() {
    _plugin.getConfig().addDefault(MESSAGE_NO_PERMISSION, "Du hast keine Berechtigung für diesen Befehl.");
    _plugin.getConfig().addDefault(MESSAGE_CONFIG_RELOAD, "Du Konfiguration wurde erfolgreich neu geladen");

    _plugin.getConfig().addDefault(DB_CONFIG_HOST, "localhost");
    _plugin.getConfig().addDefault(DB_CONFIG_PORT, 3306);
    _plugin.getConfig().addDefault(DB_CONFIG_USER, "user");
    _plugin.getConfig().addDefault(DB_CONFIG_PASSWORD, "");
    _plugin.getConfig().addDefault(DB_CONFIG_DATABASE, "minecraft");
    _plugin.getConfig().addDefault(DB_CONFIG_PREFIX, "rm_plugin_");

    _plugin.getConfig().addDefault(DB_TABLE_PLAYER, "player");
    _plugin.getConfig().addDefault(DB_TABLE_WORLD, "world");
    _plugin.getConfig().addDefault(DB_TABLE_BLOCK, "block");
    _plugin.getConfig().addDefault(DB_TABLE_LOG_COMMAND, "log_command");
    _plugin.getConfig().addDefault(DB_TABLE_LOG_CHAT, "log_chat");
    _plugin.getConfig().addDefault(DB_TABLE_LOG_LOGGIN, "log_loggin");
    _plugin.getConfig().addDefault(DB_TABLE_LOG_WORLD_CHANGE, "log_world_change");
    _plugin.getConfig().addDefault(DB_TABLE_LOG_BLOCK, "log_block");

    _plugin.getConfig().addDefault(COLOR_HELP_TEXT, ChatColor.DARK_GRAY);
    _plugin.getConfig().addDefault(COLOR_HELP_COMMAND, ChatColor.BOLD);
  }
}