package de.rene_majewski.rm_plugin.warp.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.rene_majewski.rm_plugin.RMPlugin;
import de.rene_majewski.rm_plugin.commands.CommandClass;
import de.rene_majewski.rm_plugin.config.Config;

/**
 * Befehle zum verwalten der Warp-Punkte und zum teleportieren an diese.
 * 
 * @author René Majewski
 * 
 * @since 0.2
 */
public class WarpCommand extends CommandClass {
  /**
   * Initialisiert diese Klasse.
   * 
   * @param plugin Objekt der 
   */
  public WarpCommand(RMPlugin plugin) {
    super(plugin);
  }

  /**
   * Reagiert auf die Befehle {@code /rmplugin warp },
   * {@code /rmplugin warp setwarp} und {@code /rmplugin warp delwarp}.
   * 
   * @since 0.2
   */
  public boolean warpCommand(CommandSender sender, Command command, java.lang.String label, java.lang.String[] args) {
    if (args.length >= 2) {
      if (args[0].equalsIgnoreCase("warp")) {
        Player player = null;

        if (sender instanceof Player) {
          player = (Player)sender;
        } else {
          sender.sendMessage(this._plugin.getMyConfig().getString(Config.MESSAGE_NO_CONSOLE));
          return true;
        }

        if (args[1].equalsIgnoreCase("list")) {
          this.list(player);
          return true;
        }

        if (args[2].length() > 0) {
          if (args[1].equalsIgnoreCase("warp")) {
            this.warp(player, args[2]);
            return true;
          }

          if (args[1].equalsIgnoreCase("setwarp")) {
            this.setWarp(player, args[2]);
            return true;
          }

          if (args[1].equalsIgnoreCase("delwarp")) {
            this.delWarp(player, args[2]);
            return true;
          }

          if (args[1].equalsIgnoreCase("show")) {
            this.show(player, args[2]);
            return true;
          }
        }
      }
    }

    this.sendHelpMessage(sender);
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
    this.sendMessage(this.createCommandHelpMessage("warp help", "Zeigt den Hilfetext für die Home-Befehle an"), sender);
    this.sendMessage(this.createCommandHelpMessage("help warp [Name]", "Teleportiert dich zu den mit Name angegebenen Warp-Punkt. Der Warp-Punkt muss existieren"), sender);
    this.sendMessage(this.createCommandHelpMessage("help setwarp [Name]", "Erstellt den Warp-Punkt mit den angegebenen Namen."), sender);
    this.sendMessage(this.createCommandHelpMessage("help delwarp [Name]", "Löscht den Warp-Punkt mit denen angegebenen Namen. Der Warp-Punkt muss existieren."), sender);
  }

  private void setWarp(Player player, String name) {
    if (player.hasPermission(Config.PERMISSION_WARP_SET_WARP)) {

    } else {
      this.sendNoPermission(player);
    }
  }

  private void delWarp(Player player, String name) {
    if (player.hasPermission(Config.PERMISSION_WARP_DEL_WARP)) {

    } else {
      this.sendNoPermission(player);
    }
  }

  private void warp(Player player, String name) {
    if (player.hasPermission(Config.PERMISSION_WARP_WARP)) {

    } else {
      this.sendNoPermission(player);
    }
  }

  private void list(Player player) {
    if (player.hasPermission(Config.PERMISSION_WARP_LIST)) {

    } else {
      this.sendNoPermission(player);
    }
  }

  private void show(Player player, String name) {
    if (player.hasPermission(Config.PERMISSION_WARP_SHOW)) {

    } else {
      this.sendNoPermission(player);
    }
  }
}