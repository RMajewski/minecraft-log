package de.rene_majewski.rm_plugin.permissions.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.rene_majewski.rm_plugin.RMPlugin;
import de.rene_majewski.rm_plugin.commands.CommandClass;

/**
 * Dient als Grundlage für die Permission-Befehle.
 * 
 * @author René Majewski
 * @since 0.2
 */
public final class PermissionCommand extends CommandClass {
  /**
   * Speichert die Klasse für die Befehle der Gruppen-Berechtigungen.
   * 
   * @since 0.2
   */
  private GroupCommand _groupCommand;

  /**
   * Speichert die Klasse für die Befehler der Spieler-Berechtigungen.
   * 
   * @since 0.2
   */
  private PlayerCommand _playerCommand;

  /**
   * Initialisiert die Klasse und speichert den Permission-Manager.
   * 
   * @param plugin Objekt der Plugin-Main-Klasse.
   * 
   * @since 0.2
   */
  public PermissionCommand(RMPlugin plugin) {
    super(plugin);

    this._groupCommand = new GroupCommand(this._plugin);
    this._playerCommand = new PlayerCommand(plugin);
  }

  /**
   * Reagiert auf die Permissions-Befehle.
   * 
   * @param sender Objekt, dass den Befehl gesendet hat.
   * 
   * @param command Befehl, der gesendet wurde.
   * 
   * @param label Wurde ein Alias genutzt?
   * 
   * @param args Argumente, die dem Befehl übergeben wurden.
   * 
   * @return <code>true</code>, wenn der Befehl ausgeführt werden konnte.
   * <code>false</code>, wenn der Befehl nicht ausgeführt werden konnte.
   * 
   * @since 0.2
   */
  public boolean permission(CommandSender sender, Command command, java.lang.String label, java.lang.String[] args) {
    if (args.length > 1) {
      if (args[1].equalsIgnoreCase("group")) {
        return this._groupCommand.onGroupCommand(sender, command, label, args);
      } else if (args[1].equalsIgnoreCase("player")) {
        return this._playerCommand.onGroupCommand(sender, command, label, args);
      }
    }

    sendHelpMessage(sender);
    return true;
  }

  /**
   * Sendet den Hilfetext zum angegeben Sender.
   * 
   * @param sender Objekt zu dem der Hilfetext gesendet werden soll.
   * 
   * @since 0.2
   */
  @Override
  public void sendHelpMessage(CommandSender sender) {
    this._groupCommand.sendHelpMessage(sender);
    sender.sendMessage(this.createCommandHelpMessage("permission help", "Zeigt den Hilfetext für die Permissions-Befehle an."));
    this._playerCommand.sendHelpMessage(sender);
  }
}