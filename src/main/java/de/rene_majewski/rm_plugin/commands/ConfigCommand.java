package de.rene_majewski.rm_plugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.rene_majewski.rm_plugin.RMPlugin;
import de.rene_majewski.rm_plugin.config.Config;

/**
 * Befehle zum Neuladen und Speichern der Konfiguration.
 * 
 * @author René Majewski
 * 
 * @since 0.1
 */
public class ConfigCommand extends CommandClass {

  /**
   * Initialisiert die Klasse.
   * 
   * @param plugin Objekt zur Plugin-Main-Klasse.
   * 
   * @since 0.2
   */
  public ConfigCommand(RMPlugin plugin) {
    super(plugin);
  }

  /**
   * Reagiert auf die Befehle {@code /rmplugin config reload} und
   * {@code /rmplugin config save}.
   * 
   * @since 0.1
   */
  public boolean config(CommandSender sender, Command command, String[] args, RMPlugin plugin) {
    if ((args.length >= 2) && args[1].equalsIgnoreCase("reload")) {
      reaload(sender);
    } else {
      this.sendHelpMessage(sender);
    }

    return true;
  }

  /**
   * Lädt die Konfiguration neu.
   * 
   * Zuerst wird überprüft, ob der Spieler die Berechtigung
   * {@link Config#PERMISSION_ADMIN_RELOAD} hat. Ist dies der Fall so wird die
   * Konfiguration neugeladen. Hat der sender die Berechtigung nicht, so wird
   * dem sender {@link #} gesandt.
   * 
   * @param sender Objekt, was den Hilfetext angefordert hat.
   * 
   * @since 0.1
   */
  private void reaload(CommandSender sender) {
    if (sender.hasPermission(Config.PERMISSION_ADMIN_RELOAD)) {
      this._plugin.getMyConfig().reload();
      sender.sendMessage(this._plugin.getMyConfig().getString(Config.MESSAGE_CONFIG_RELOAD));
    } else {
      this.sendNoPermission(sender);
    }
  }

  /**
   * Sendet den Hilfetext für diesen Befehl zum Sender.
   * 
   * @param sender Objekt, das den Hilfetext empfangen soll.
   * 
   * @since 0.2
   */
  @Override
  public void sendHelpMessage(CommandSender sender) {
    this.sendMessage(this.createCommandHelpMessage("config help", "Zeigt den Hilfetext für die Konfigurations-Befehle an."), sender);
    this.sendMessage(this.createCommandHelpMessage("config reload", "Lädt die Konfiguration für das RM-Plugin neu."), sender);
    this.sendMessage(this.createCommandHelpMessage("config save", "Speichert die Konfiguration des RM-Plugin ab."), sender);
  }
}