package de.rene_majewski.rm_plugin.economy.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.rene_majewski.rm_plugin.RMPlugin;
import de.rene_majewski.rm_plugin.commands.CommandClass;
import de.rene_majewski.rm_plugin.config.Config;

/**
 * Dient als Grundlage für die Permission-Befehle.
 * 
 * @author René Majewski
 * @since 0.2
 */
public class EconomyCommand extends CommandClass {
  public EconomyCommand(RMPlugin plugin) {
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
  }


  /**
   * Reagiert auf die Economy-Befehle.
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
  public boolean economyCommand(CommandSender sender, Command command, java.lang.String label, java.lang.String[] args) {
    if (args.length > 2) {
      if (args[1].equalsIgnoreCase("add")) {
        if (args.length == 4) {
          String uuid = args[2];
          if (this._plugin.getEconomyManager().hasBalance(uuid)) {
            double balance = this._plugin.getEconomyManager().getBalance(uuid) + this.getDoubleFromString(args[3], sender, 4);
            this._plugin.getEconomyManager().setBalance(uuid, balance);
            return true;
          } else {
            this.sendMessage(this._plugin.getConfig().getString(Config.MESSAGE_ERROR_NO_PLAYER).replace("?", uuid), sender);
            return true;
          }
        }
      } else if (args[1].equalsIgnoreCase("remove")) {
        return true;
      } else if (args[1].equalsIgnoreCase("set")) {
        return true;
      } else {
        return true;
      }
    } else if (args.length == 2) {
      return true;
    }

    sendHelpMessage(sender);
    return true;
  }

}