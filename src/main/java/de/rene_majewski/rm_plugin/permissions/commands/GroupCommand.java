package de.rene_majewski.rm_plugin.permissions.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.rene_majewski.rm_plugin.RMPlugin;
import de.rene_majewski.rm_plugin.commands.CommandClass;

/**
 * Reagiert auf die Gruppen-Befehle.
 * 
 * @author René Majewski
 * 
 * @since 0.2
 */
class GroupCommand extends CommandClass {

  /**
   * Initialisiert diese Klasse
   * 
   * @param plugin Objekt der Plugin-Main-Klasse.
   * 
   * @since 0.2
   */
  public GroupCommand(RMPlugin plugin) {
    super(plugin);
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
    sender.sendMessage(this.createCommandHelpMessage("permission group help", "Hilfetext für den Befehl für die Gruppen-Berechtigungen."));   
  }

  /**
   * Reagiert auf die Gruppen-Befehle für die Berechtigungen.
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
  public boolean onGroupCommand(CommandSender sender, Command command, java.lang.String label, java.lang.String[] args) {
    return false;
  }
}