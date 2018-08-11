package de.rene_majewski.rm_plugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.rene_majewski.rm_plugin.RMPlugin;

/**
 * Befehle zum bannen und unbannen von Spielern.
 * 
 * @author René Majewski
 * @since 0.2
 */
public class BanCommand extends CommandClass {
  /**
   * Initialiesiert die Klasse.
   * 
   * @param plugin Objekt der Plugin-Main-Klasse.
   * 
   * @since 0.2
   */
  public BanCommand(RMPlugin plugin) {
    super(plugin);
  }

  /**
   * Reagiert auf die Befehle {@code ban} und {@code unban}.
   * 
   * @since 0.2 
   */
  public boolean banCommand(CommandSender sender, Command command, String label, String[] args) {
    return true;
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
    this.sendMessage(this.createCommandHelpMessage("ban ban [Name] [Grund]", "Bannt einen Spieler."), sender);
    this.sendMessage(this.createCommandHelpMessage("ban unban [Name]", "Hebt die Bannung eines Spielers auf."), sender);
    this.sendMessage(this.createCommandHelpMessage("ban list", "Zeigt die Liste aller gebannten Spieler an"), sender);
    this.sendMessage(this.createCommandHelpMessage("ban show [Name]", "Zeigt alle Informationen über die Bannung eines Spielers an."), sender);
  }
}