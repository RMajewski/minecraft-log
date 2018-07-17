package de.rene_majewski.rm_plugin.commands;

import org.bukkit.command.CommandSender;

import de.rene_majewski.rm_plugin.RMPlugin;
import de.rene_majewski.rm_plugin.config.Config;

/**
 * Alle Befehls-Klassen werden von dieser Klasse abgeleitet. Es werden
 * Hilfs-Methoden bereitgestellt oder Methoden definiert, die von den
 * abgeleiteten Klassen implementiert werden müssen.
 * 
 * @author René Majewski
 * @since 0.2
 */
public abstract class CommandClass {
  /**
   * Speichert das Objekt zur Plugin-Main-Klasse.
   * 
   * @since 0.2
   */
  protected RMPlugin _plugin;

  /**
   * Initialisiert diese Klasse.
   * 
   * @param plugin Objekt zur Plugin-Main-Klasse
   */
  public CommandClass(RMPlugin plugin) {
    this._plugin = plugin;
  }


  /**
   * Formatiert den Befehl mit einer Farbe und fügt den Hilfetext an.
   * 
   * Die Farben werden aus der Konfiguration geladen. Um den Befehl
   * hevorzuheben, wird {@link Config#COLOR_HELP_COMMAND} verwendet. Für den
   * normalen Hilfetext wird {@link Config#COLOR_HELP_TEXT} verwendet.
   * 
   * @param command Befehle, zu dem der Hiltext ausgegeben werden soll.
   * 
   * @param text Hilfetext, der nach dem Befehl angezeigt werden soll.
   */
  public String createCommandHelpMessage(String command, String text) {
    StringBuffer sb = new StringBuffer();

    sb.append(this._plugin.getMyConfig().getString(Config.COLOR_HELP_COMMAND));
    sb.append("/rmplugin ");
    sb.append(command);
    sb.append(" ");
    sb.append(this._plugin.getMyConfig().getString(Config.COLOR_HELP_TEXT));
    sb.append(text);

    return sb.toString();
  }

  /**
   * Sendet den Hilfetext für den Befehl zum Sender.
   * 
   * @param sender Objekt, zu dem der Hilfetext gesendet werden soll.
   * 
   * @since 0.2
   */
  public abstract void sendHelpMessage(CommandSender sender);

  /**
   * Sendet die Nachricht {@link Config#MESSAGE_NO_PERMISSION}.
   * 
   * @param sender Objekt, zu dem die Nachricht gesendet werden soll.
   * 
   * @since 0.2
   */
  public void sendNoPermission(CommandSender sender) {
    sender.sendMessage(this._plugin.getMyConfig().getString(Config.MESSAGE_NO_PERMISSION));
  }
}