package de.rene_majewski.rm_plugin.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.rene_majewski.rm_plugin.RMPlugin;
import de.rene_majewski.rm_plugin.config.Config;
import de.rene_majewski.rm_plugin.permissions.commands.PermissionCommand;

/**
 * Liest die Konfiguration für das Plugin neu ein.
 * 
 * @author René Majewski
 * @since 0.1
 */
public class RMPluginCommand extends CommandClass  implements CommandExecutor {
  /**
   * Speichert das Objekt für {@link ConfigCommand}.
   * 
   * @since 0.2
   */
  private ConfigCommand _configCommand;

  /**
   * Speichert das Objekt für {@link PermissionCommand}.
   * 
   * @since 0.2
   */
  private PermissionCommand _permissionCommand;

  /**
   * Initialisiert die Klasse.
   * 
   * @param config Konfiguration-Klasse des Plugins.
   * 
   * @since 0.1
   */
  public RMPluginCommand(RMPlugin plugin) {
    super(plugin);

    this._configCommand = new ConfigCommand(this._plugin);
    this._permissionCommand = new PermissionCommand(this._plugin);
  }

  /**
   * Wird ausgeführt, wenn ein Befehl eingegeben wurde.
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
   * @since 0.1
   */
  public boolean onCommand(CommandSender sender, Command command, java.lang.String label, java.lang.String[] args) {
    Player player = null;
    if (sender instanceof Player) {
      player = (Player)sender;
      return true;
    }

    if (command.getName().equalsIgnoreCase("rmplugin")) {
      if (args.length >= 1) {
        if (args[0].equalsIgnoreCase("help")) {
          sendHelpMessage(sender);
          return true;
        } else if (args[0].equalsIgnoreCase("config")) {
          return this._configCommand.config(sender, command, args, this._plugin);
        } else if (args[0].equalsIgnoreCase("permission")) {
          return this._permissionCommand.permission(sender, command, label, args);
        }
      } else {
        sendHelpMessage(sender);
        return true;
      }
    }

    return false;
  }

  /**
   * Sendet den allgemeinen Hilfetext zum Sender.
   * 
   * @param sender Objekt, zu dem der Hilfetext gesendet werden soll.
   * 
   * @since 0.1
   */
  public void sendHelpMessage(CommandSender sender) {
    if (sender.hasPermission(Config.PERMISSION_COMMAND_HELP)) {
      sender.sendMessage(ChatColor.RED + "Bitte verwende das Kommando wie folgt:");

      this._configCommand.sendHelpMessage(sender);

      sender.sendMessage(this.createCommandHelpMessage("help", "Zeigt diesen Hilfetext an."));

      this._permissionCommand.sendHelpMessage(sender);
    } else {
      this.sendNoPermission(sender);
    }
  }
}