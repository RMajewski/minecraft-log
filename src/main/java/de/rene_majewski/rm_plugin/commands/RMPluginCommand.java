package de.rene_majewski.rm_plugin.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.rene_majewski.rm_plugin.RMPlugin;
import de.rene_majewski.rm_plugin.config.Config;
import de.rene_majewski.rm_plugin.economy.commands.EconomyCommand;
import de.rene_majewski.rm_plugin.permissions.commands.PermissionCommand;
import de.rene_majewski.rm_plugin.warp.commands.WarpCommand;

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
   * Speichert das Objekt für {@link EconomyCommand}.
   * 
   * @since 0.2
   */
  private EconomyCommand _economyCommand;

  /**
   * Speichert das Objekt für {@link HomeCommand}.
   * 
   * @since 0.2
   */
  private HomeCommand _homeCommand;

  /**
   * Speichert das Objekt für {@link WarpCommand}.
   * 
   * @since 0.2
   */
  private WarpCommand _warpCommand;

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
    this._economyCommand = new EconomyCommand(this._plugin);
    this._homeCommand = new HomeCommand(this._plugin);
    this._warpCommand = new WarpCommand(this._plugin);
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
        } else if (args[0].equalsIgnoreCase("balance")) {
          return this._economyCommand.economyCommand(sender, command, label, args);
        } else if (args[0].equalsIgnoreCase("home")) {
          return this._homeCommand.homeCommand(sender, command, label, args);
        } else if (args[0].equalsIgnoreCase("warm")) {
          return this._warpCommand.warpCommand(sender, command, label, args);
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
      this.sendMessage(ChatColor.RED + this._plugin.getMyConfig().getString(Config.MESSAGE_HELP_HELP), sender);

      this._configCommand.sendHelpMessage(sender);

      this.sendMessage(this.createCommandHelpMessage("help", this._plugin.getMyConfig().getString(Config.MESSAGE_HELP_HELP)), sender);

      this._homeCommand.sendHelpMessage(sender);
      this._permissionCommand.sendHelpMessage(sender);
      this._warpCommand.sendHelpMessage(sender);
    } else {
      this.sendNoPermission(sender);
    }
  }
}