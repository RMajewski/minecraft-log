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
  public static final String MESSAGE_NO_PERMISSION = "rmplugin.messages.error.no_permission";

  /**
   * Gibt den Pfad zur Nachricht für "Kommando kann nicht in der Konsole
   * ausgeführt werden" an.
   * 
   * @since 0.2
   */
  public static final String MESSAGE_NO_CONSOLE = "rmplugin.messages.error.no_console";

  /**
   * Gibt den Pfad zur Nachricht für "Konfiguration neu geladen" an.
   * 
   * @since 0.1
   */
  public static final String MESSAGE_CONFIG_RELOAD = "rmplugin.messages.config.reload";

  /**
   * Gibt den Pfad zur Nachricht für "Konfiguration gespeichert" an.
   * 
   * @since 0.2
   */
  public static final String MESSAGE_CONFIG_SAVE = "rmplugin.messages.config.save";

  /**
   * Gibt den Pfad zur Nachricht für die Überschrift der Hilfe an.
   * 
   * @since 0.2
   */
  public static final String MESSAGE_HELP_COMMAND = "rmplugin.messages.help.command";

  /**
   * Gibt den Pfad zur Hilfe-Nachricht an.
   * 
   * @since 0.2
   */
  public static final String MESSAGE_HELP_HELP = "rmplugin.messages.help.help";

  /**
   * Gibt den Pfad zur Nachricht für die Nachricht "Gruppe erzeugt" an.
   * 
   * @since 0.2
   */
  public static final String MESSAGE_PERMISSION_GROUP_CREATE = "rmplugin.messages.permission.group.create";

  /**
   * Gibt den Pfad zur Nachricht für die Nachricht "Gruppe nicht erzeugt" an.
   * 
   * @since 0.2
   */
  public static final String MESSAGE_PERMISSION_GROUP_CREATE_NO = "rmplugin.messages.permission.group.create_no";

  /**
   * Gibt den Pfad zur Nachricht für die Nachricht "Gruppe gelöscht" an.
   * 
   * @since 0.2
   */
  public static final String MESSAGE_PERMISSION_GROUP_DELETE = "rmplugin.messages.permission.group.delete";

  /**
   * Gibt den Pfad zur Nachricht "Gruppe konnte nicht gelöscht werden" an.
   * 
   * @since 0.2
   */
  public static final String MESSAGE_PERMISSION_GROUP_DELETE_NO = "rmplugin.messages.permission.group.delete_no";

  /**
   * Gibt den Pfad zur Nachricht "Vater-Gruppe wurde gesetzt" an.
   * 
   * @since 0.2
   */
  public static final String MESSAGE_PERMISSION_GROUP_ADD_PARENT = "rmplugin.messages.permission.group.add.parent";
  
  /**
   * Gibt den Pfad zur Nchricht "Vater-Gruppe konnte nicht gesetzt werden" an.
   * 
   * @since 0.2
   */
  public static final String MESSAGE_PERMISSION_GROUP_ADD_PARENT_NO = "rmplugin.messages.permission.group.add.parent_no";

  /**
   * Gibt den Pfad zur Nachricht "Vater Gruppe wurde entfernt" an.
   * 
   * @since 0.2
   */
  public static final String MESSAGE_PERMISSION_GROUP_REMOVE_PARENT = "rmplugin.messages.permission.group.remove.parent";

  /**
   * Gibt den Pfad zur Nachrciht "Vater-Gruppe konnte nicht entfernt werden" an.
   * 
   * @since 0.2
   */
  public static final String MESSAGE_PERMISSION_GROUP_REMOVE_PARENT_NO = "rmplugin.messages.permission.group.remove.parent_no";

  /**
   * Gibt den Pfad zur Nachricht "Permission wurde gesetzt" an.
   * 
   * @since 0.2
   */
  public static final String MESSAGE_PERMISSION_GROUP_ADD_PERMISSION = "rmplugin.messages.permission.group.add.permission";

  /**
   * Gibt den Pfad zur Nachricht "Permission wurde nicht gesetzt" an.
   * 
   * @since 0.2
   */
  public static final String MESSAGE_PERMISSION_GROUP_ADD_PERMISSION_NO = "rmplugin.messages.permission.group.add.permission_no";

  /**
   * Gibt den Pfad zur Nachricht "Permission wurde entfernt" an.
   * 
   * @since 0.2
   */
  public static final String MESSAGE_PERMISSION_GROUP_REMOVE_PERMISSION = "rmplugin.messages.permission.group.remove.permission";

  /**
   * Gibt den Pfad zur Nachricht "Permission konnte nicht entfernt werden" an.
   */
  public static final String MESSAGE_PERMISSION_GROUP_REMOVE_PERMISSION_NO = "rmplugin.messages.permission.group.remove.permission_no";

  /**
   * Gibt den Pfad zur Nachricht "Vater-Gruppen konnten nicht ausgelesen werden" an.
   * 
   * @since 0.2
   */
  public static final String MESSAGE_ERROR_PERMISSION_SHOW_PARENTS = "rmplugin.messages.permission.group.show.parents";

  /**
   * Gibt den Pfad zur Nachricht "Kinder-Gruppen konnten nicht ausgelesen werden" an.
   * 
   * @since 0.2
   */
  public static final String MESSAGE_ERROR_PERMISSION_SHOW_CHILDS = "rmplugin.messages.permission.group.show.childs";

  /**
   * Gibt den Pfad zur Nachricht "Permissions konnten nicht ausgelesen werden" an.
   * 
   * @since 0.2
   */
  public static final String MESSAGE_ERROR_PERMISSION_SHOW_PERMISSIONS = "rmplugin.messages.permission.group.show.permissions";

  /**
   * Gibt den Pfad zur Fehler-Nachricht für "add parent Fehler mit Parameter"
   * an. 
   * 
   * @since 0.2
   */
  public static final String MESSAGE_ERROR_PERMISSION_GROUP_ADD_PARENT = "rmplugin.messages.error.permission.group.add.parent";

  /**
   * Gibt den Pfad zur Fehler-Nachricht für "remove parent Fehler mit Parameter"
   * 
   * @since 0.2
   */
  public static final String MESSAGE_ERROR_PERMISSION_GROUP_REMOVE_PARENT = "rmplugin.messages.error.permission.group.remove.parent";

  /**
   * Gibt den Pfad zu Fehler-Nachricht für "add permission Fehler mit Parameter"
   * an.
   * 
   * @since 0.2
   */
  public static final String MESSAGE_ERROR_PERMISSION_GROUP_ADD_PERMISSION = "rmplugin.messages.error.permission.group.add.permission";

  /**
   * Gibt den Pfad zur Fehler-Nachricht für "Gruppe nicht gefunden" an.
   * 
   * @since 0.2
   */
  public static final String MESSAGE_ERROR_PERMISSION_NO_GROUP_FOUND = "rmplugin.messages.error.permission.group.not_found";

  /**
   * Gibt den Pfad zur Fehler-Nachrciht für "Permission nicht gefunden" an.
   * 
   * @since 0.2
   */
  public static final String MESSAGE_ERROR_NO_PERMISSION_FOUND = "rmplugin.messages.error.permission.not_found";

  /**
   * Gibt den Pfad zur Fehler-nachricht für "kein Begrüßungs-Geld" an.
   * 
   * @since 0.2
   */
  public static final String MESSAGE_ERROR_ECONOMY_NO_STANDARD_BALANCE = "rmplugin.messages.error.economy.no_standard_balance";

  /**
   * Gibt den Pfad zur Nachrciht für die Nachricht "Fehler ist aufgetreten".
   * 
   * @since 0.2
   */
  public static final String MESSAGE_ERROR = "rmplugin.messages.errors.error";

  /**
   * Gibt den Pfad zur Nachricht "Gruppe angeben".
   * 
   * @since 0.2
   */
  public static final String MESSAGE_ERROR_NO_GROUP = "rmplugin.messages.errors.no_group";

  /**
   * Gibt den Pfad zur Nachricht "Kein Double-Wert" an.
   * 
   * @since 0.2
   */
  public static final String MESSAGE_ERROR_NO_DOUBLE = "rmplugin.messages.errors.no_double";

  /**
   * Gibt den Pfad zur Nachricht "Spieler existiert" an.
   * 
   * @since 0.2
   */
  public static final String MESSAGE_ERROR_NO_PLAYER = "rmplugin.messages.errors.no_player";

  /**
   * Gibt den Pfad zur Nachricht "Welt existiert nicht" an.
   * 
   * @since 0.2
   */
  public static final String MESSAGE_ERROR_NO_WORLD = "rmplugin.messages.errors.no_world";

  /**
   * Gibt den Pfad zur Nachricht "Home nicht gesetzt" an.
   * 
   * @since 0.2
   */
  public static final String MESSAGE_ERROR_SET_HOME = "rmplugin.messages.errors.set_home";

  /**
   * Gibt den Pfad zur Nachricht "Home nicht gelöscht" an.
   * 
   * @since 0.2
   */
  public static final String MESSAGE_ERROR_DELETE_HOME = "rmplugin.messages.errors.delete_home";

  /**
   * Gibt den Pfad zur Nachricht "Spieler hat für 1. Login 10 € bekommen."
   * 
   * @since 0.2
   */
  public static final String MESSAGE_ECONOMY_START = "rmplugin.messages.economy.start";

  /**
   * Gibt den Pfad zur Nachricht "Du besitzt aktuellen Geldbetrag" an.
   * 
   * @since 0.2
   */
  public static final String MESSAGE_ECONOMY_OWN_MONEY = "rmplugin.messages.economy.own_money";

  /**
   * Gibt den Pfad zur Nachricht "Home gesetzt" an.
   * 
   * @since 0.2
   */
  public static final String MESSAGE_HOME_SET_HOME = "rmplugin.messages.home.set_home";

  /**
   * Gibt den Pfad zur Nachrcit "Home existiert bereits" an.
   * 
   * @since 0.2
   */
  public static final String MESSAGE_HOME_HOME_EXISTS = "rmplugin.messages.home.home_exiists";

  /**
   * Gibt den Pfad zur Nachricht "Home existiert nicht" an.
   * 
   * @since 0.2
   */
  public static final String MESSAGE_HOME_NOT_HOME = "rmplugin.messages.home.not_home";

  /**
   * Gibt den Pfad zur Nachricht "Home wurde gelöscht" an.
   * 
   * @since 0.2
   */
  public static final String MESSAGE_HOME_DELETE_HOME = "rmplugin.messages.home.delete_home";

  /**
   * Gibt den Pfad zur Nachricht "Du hast dich zu Home teleportiert" an.
   * 
   * @since 0.2
   */
  public static final String MESSAGE_HOME_TELEPORT = "rmplugin.messages.home.teleport";

  /**
   * Gibt den Pfad zur Nachricht "Du besitzt folgende Homes" an.
   * 
   * @since 0.2
   */
  public static final String MESSAGE_HOME_LIST = "rmplugin.messages.home.list";


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
   * Gibt den Pfad zur Einstellung der Tabelle für die Permissions an.
   * 
   * @since 0.2
   */
  public static final String DB_TABLE_PERMISSION = "rmplugin.db.tables.permission";

  /**
   * Gibt den Pfad zur Einstellung der Tabelle für die Gruppen an.
   * 
   * @since 0.2
   */
  public static final String DB_TABLE_PERMISSION_GROUP = "rmplugin.db.tables.permission_group";

  /**
   * Gibt den Pfad zur Einstellung der Tabelle für die Gruppen-Vererbungen an.
   * 
   * @since 0.2
   */
  public static final String DB_TABLE_PERMISSION_GROUP_PARENT = "rmplugin.db.tables.permission_group_parent";

  /**
   * Gibt den Pfad zur Einstellung der Tabelle für die erteilten Permissions.
   * 
   * @since 0.2
   */
  public static final String DB_TABLE_PERMISSION_ALLOCATE = "rmplugin.db.tables.permmision_allocate";

  /**
   * Gibt den Pfad zur Einstellung der Tabelle für die Geld-Beträge der Spieler.
   * 
   * @since 0.2
   */
  public static final String DB_TABLE_BALANCE = "rmplugin.db.tables.balance";

  /**
   * Gibt den Pfad zur Einstellung der Tabelle für die Kontoauszüge an.
   * 
   * @since 0.2
   */
  public static final String DB_TABLE_BALANCE_STATEMENT = "rmplugin.db.tables.balance_statement";

  /**
   * Gibt den Pfad zur Einstellung der Tabelle für die Home-Punkte an.
   * 
   * @since 0.2
   */
  public static final String DB_TABLE_HOME = "rmplugin.db.tables.home";


  /**
   * Gib den Namen der Permission zum Neuladen der Konfiguration an.
   * 
   * @since 0.1
   */
  public static final String PERMISSION_ADMIN_RELOAD = "rmplugin.admin.reload";

  /**
   * Gibt den Namen der Permission zum Speichern der Konfiguration an.
   * 
   * @since 0.2
   */
  public static final String PERMISSION_ADMIN_SAVE = "rmplugin.admin.save";

  /**
   * Gibt den Namen der Permission zum Anzeigen der Debug-Nachrichten an.
   * 
   * @since 0.2
   */
  public static final String PERMISSION_ADMIN_DEBUG = "rmplugin.admin.debug";

  /**
   * Gibt den Namen der Permission zum Anzeigen des Hilfetextes an.
   * 
   * @since 0.2
   */
  public static final String PERMISSION_COMMAND_HELP = "rmplugin.help";

  /**
   * Gibt den Namen der Permission zum Anzeigen der eigenen Economy-Balance an.
   * 
   * @since 0.2
   */
  public static final String ECONOMY_COMMAND_BALANCE = "rmplugin.economy.balance";

  /**
   * Gibt den Namen der Permission zum Setzen der Economy-Balance für einen
   * Spieler an.
   * 
   * @since 0.2
   */
  public static final String ECONOMY_COMMAND_BALANCE_SET = "rmplugin.economy.balance.set";

  /**
   * Gibt den Namen der Permission zum Hinzufügen eines Betrages zu einem
   * Spieler an.
   * 
   * @since 0.2
   */
  public static final String ECONOMY_COMMAND_BALANCE_ADD = "rmplugin.economy.balance.add";

  /**
   * Gibt den Namen der Permission zum Entfernen einer Economy-Balance eines
   * Spielers an.
   * 
   * @since 0.2
   */
  public static final String ECONOMY_COMMAND_BALANCE_REMOVE = "rmplugin.economy.balance.remove";


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
   * Gibt den Namen zur Einstellung der Farbe für Fehler-Meldungen an.
   * 
   * @since 0.2
   */
  public static final String COLOR_ERROR_MESSAGE = "rmplugin.color.error.message";

  /**
   * Gibt den Namen zur Einstellung der Farbe für Fehler-Text an.
   * 
   * @since 0.2
   */
  public static final String COLOR_ERROR_TEXT = "rmplugin.color.error.text";

  /**
   * Gibt den Namen zur Einstellung der Farbe zur Überschrift der Permissions an.
   * 
   * @since 0.2
   */
  public static final String COLOR_PERMISSION_HEADER = "rmplugin.color.permission.header";

  /**
   * Gibt den Namen zur Einstellung der Farbe für einzelne Permissions an.
   * 
   * @since 0.2
   */
  public static final String COLOR_PERMISSION_VALUE = "rmplugin.color.permission.value";

  /**
   * Gibt den Namen zur Einstellung der Farbe für normalen Text innerhalb der
   * Permissions an.
   * 
   * @since 0.2
   */
  public static final String COLOR_PERMISSION_TEXT = "rmplugin.color.permission.text";

  /**
   * Gibt den Namen zur Einstellung des Betrages, den ein neuer Spieler
   * erhält.
   * 
   * @since 0.2
   */
  public static final String ECONOMY_STANDARD_BALANCE = "rmplugin.economy.standard_balance";

  /**
   * Gibt den Namen zur Einstellung der Währung an.
   * 
   * @since 0.2
   */
  public static final String ECONOMY_CURRENCY = "rmplugin.economy.currency";

  /**
   * Gibt den Namen zur Einstellungen des Namens, der auf einen  Chest-Sign
   * angegeben werden muss.
   * 
   * @since 0.2
   */
  public static final String ECONOMY_SHOP_SIGN_NAME = "rmplugin.economy.shop.sign_name";


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
   * Liest einen Double-Wert aus der Konfiguration.
   * 
   * @param path Pfad zum Double-Wert, der aus der Konfiguration gelesen werden
   * soll.
   * 
   * @return Aus der Konfiguration ermittelter Double-Wert.
   * 
   * @since 0.2
   */
  public double getDouble(String path) {
    return this._plugin.getConfig().getDouble(path);
  }

  /**
   * Setzt die Default-Werte zu den einzelnen Pfaden.
   */
  private void setDefaults() {
    _plugin.getConfig().addDefault(MESSAGE_NO_PERMISSION, "Du hast keine Berechtigung für diesen Befehl.");
    _plugin.getConfig().addDefault(MESSAGE_NO_CONSOLE, "Du kannst dieses Kommando nicht von der Konsole ausführen");
    _plugin.getConfig().addDefault(MESSAGE_CONFIG_RELOAD, "Die Konfiguration wurde erfolgreich neu geladen.");
    _plugin.getConfig().addDefault(MESSAGE_CONFIG_SAVE, "Die Konfiguration wurde erfolgreich gespeichert.");
    _plugin.getConfig().addDefault(MESSAGE_HELP_HELP, "Bitte verwende den Befehl wie folgt:");
    _plugin.getConfig().addDefault(MESSAGE_HELP_COMMAND, "Zeigt diesen Hilfetext an.");
    _plugin.getConfig().addDefault(MESSAGE_ERROR, "Es ist ein Fehler aufgetreten.");
    _plugin.getConfig().addDefault(MESSAGE_ERROR_NO_DOUBLE, "Du muss beim ?. Argument einen gültigen Double-Wert angeben.");
    _plugin.getConfig().addDefault(MESSAGE_ERROR_NO_PLAYER, "Der Spieler '?' existiert nicht auf diesen Server");
    _plugin.getConfig().addDefault(MESSAGE_ERROR_NO_WORLD, "Die Welt '?' existiert nicht auf diesen Server");
    _plugin.getConfig().addDefault(MESSAGE_ERROR_NO_GROUP, "Du musst einen Gruppen-Namen angegeben.");
    _plugin.getConfig().addDefault(MESSAGE_ERROR_PERMISSION_GROUP_ADD_PARENT, "Falsche Anzahl an Parameter.");
    _plugin.getConfig().addDefault(MESSAGE_ERROR_PERMISSION_GROUP_REMOVE_PARENT, "Falsche Anzahl an Parameter.");
    _plugin.getConfig().addDefault(MESSAGE_ERROR_PERMISSION_NO_GROUP_FOUND, "Die Gruppe '?' konnte nicht gefunden werden.");
    _plugin.getConfig().addDefault(MESSAGE_ERROR_NO_PERMISSION_FOUND, "Die Permission '?' konnte nicht gefunden werden.");
    _plugin.getConfig().addDefault(MESSAGE_ERROR_PERMISSION_SHOW_PARENTS, "Von der Gruppe '?' konnten nicht die Vater-Gruppen ausgelesen werden.");
    _plugin.getConfig().addDefault(MESSAGE_ERROR_PERMISSION_SHOW_CHILDS, "Von der Gruppe '?' konnten nicht die Kinder-Gruppen ausgelesen werden.");
    _plugin.getConfig().addDefault(MESSAGE_ERROR_PERMISSION_SHOW_PERMISSIONS, "Von der Gruppe '?' konnten die Permissions nicht ausgelesen werden.");
    _plugin.getConfig().addDefault(MESSAGE_ERROR_ECONOMY_NO_STANDARD_BALANCE, "Leider konnte dir das Begrüßungs-Geld nicht überwiesen werden. Bitte wende dich an einen Administrator.");
    _plugin.getConfig().addDefault(MESSAGE_ERROR_SET_HOME, "Leider konnte dein Home-Punkt '?' nicht erstellt werden.");
    _plugin.getConfig().addDefault(MESSAGE_ERROR_DELETE_HOME, "Leider konnte dein Home-Punkt '?' nicht gelöscht werden.");

    _plugin.getConfig().addDefault(MESSAGE_PERMISSION_GROUP_CREATE, "Die Gruppe '?' wurde erfolgreichreich erzeugt.");
    _plugin.getConfig().addDefault(MESSAGE_PERMISSION_GROUP_CREATE_NO, "Die Gruppe '?' konnte nicht erzeugt werden.");
    _plugin.getConfig().addDefault(MESSAGE_PERMISSION_GROUP_DELETE, "Die Gruppe '?' wurde gelöscht.");
    _plugin.getConfig().addDefault(MESSAGE_PERMISSION_GROUP_DELETE_NO, "Die Gruppe '?' konnte nicht gelöscht werden.");
    _plugin.getConfig().addDefault(MESSAGE_PERMISSION_GROUP_ADD_PARENT, "Der Gruppe '?' wurde die Vater-Gruppe '?' hinzugefügt.");
    _plugin.getConfig().addDefault(MESSAGE_PERMISSION_GROUP_ADD_PARENT_NO, "Der Gruppe '?' konnte die Vater-Gruppe '?' nicht hinzugefügt.");
    _plugin.getConfig().addDefault(MESSAGE_PERMISSION_GROUP_ADD_PERMISSION, "Der Gruppe '?' wurde die Permission '?' hinzugefügt.");
    _plugin.getConfig().addDefault(MESSAGE_PERMISSION_GROUP_ADD_PERMISSION_NO, "Der Gruppe '?' konnte die Permission '?' nicht hinzugefügt werden.");
    _plugin.getConfig().addDefault(MESSAGE_PERMISSION_GROUP_REMOVE_PARENT, "Bei der Gruppe '?' wurde die Vater-Gruppe '?' gelöscht.");
    _plugin.getConfig().addDefault(MESSAGE_PERMISSION_GROUP_REMOVE_PARENT_NO, "Bei der Gruppe '?' konnte die Vater-Gruppe '?' nicht gelöscht werden.");
    _plugin.getConfig().addDefault(MESSAGE_PERMISSION_GROUP_REMOVE_PERMISSION, "Der Gruppe '?' wurde die Permission '?' entzogen.");
    _plugin.getConfig().addDefault(MESSAGE_PERMISSION_GROUP_REMOVE_PERMISSION_NO, "Der Gruppe '?' konnte die Permission '?' nicht entzogen werden.");

    _plugin.getConfig().addDefault(MESSAGE_ECONOMY_START, "Du hast zur Begrüßung ? ? bekommen.");
    _plugin.getConfig().addDefault(MESSAGE_ECONOMY_OWN_MONEY, "Du besitzt aktuell §l?§r ?.");

    _plugin.getConfig().addDefault(MESSAGE_HOME_SET_HOME, "Dein Home-Punkt '?' wurde erstellt.");
    _plugin.getConfig().addDefault(MESSAGE_HOME_HOME_EXISTS, "Es existiert schon ein Home-Punkt mit dem Namen '?'. Wähle einen anderen Namen oder löscht ihn.");
    _plugin.getConfig().addDefault(MESSAGE_HOME_NOT_HOME, "Der Home-Punkt '?' existiert nicht. Bitte lege den Home-Punkt vorher an.");
    _plugin.getConfig().addDefault(MESSAGE_HOME_DELETE_HOME, "Der Home-Punkt '?' wurde gelöscht");
    _plugin.getConfig().addDefault(MESSAGE_HOME_TELEPORT, "Du hast dich zu '?' teleportiert.");
    _plugin.getConfig().addDefault(MESSAGE_HOME_LIST, "Du hast folgende Home-Punkte: ?");

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
    _plugin.getConfig().addDefault(DB_TABLE_PERMISSION, "permission");
    _plugin.getConfig().addDefault(DB_TABLE_PERMISSION_GROUP, "group");
    _plugin.getConfig().addDefault(DB_TABLE_PERMISSION_GROUP_PARENT, "group_parent");
    _plugin.getConfig().addDefault(DB_TABLE_PERMISSION_ALLOCATE, "permission_allocate");
    _plugin.getConfig().addDefault(DB_TABLE_BALANCE, "balance");
    _plugin.getConfig().addDefault(DB_TABLE_BALANCE_STATEMENT, "balance_statement");
    _plugin.getConfig().addDefault(DB_TABLE_HOME, "home");

    _plugin.getConfig().addDefault(COLOR_HELP_TEXT, "§7");
    _plugin.getConfig().addDefault(COLOR_HELP_COMMAND, "§6");
    _plugin.getConfig().addDefault(COLOR_PERMISSION_HEADER, "§1");
    _plugin.getConfig().addDefault(COLOR_PERMISSION_VALUE, "§b");
    _plugin.getConfig().addDefault(COLOR_PERMISSION_TEXT, "§7");
    _plugin.getConfig().addDefault(COLOR_ERROR_TEXT, "§c");
    _plugin.getConfig().addDefault(COLOR_ERROR_MESSAGE, "§4§l");

    _plugin.getConfig().addDefault(ECONOMY_STANDARD_BALANCE, 10.0);
    _plugin.getConfig().addDefault(ECONOMY_CURRENCY, "€");
    _plugin.getConfig().addDefault(ECONOMY_SHOP_SIGN_NAME, "Shop");
  }
}