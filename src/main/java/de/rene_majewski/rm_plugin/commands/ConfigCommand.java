package de.rene_majewski.rm_plugin.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.rene_majewski.rm_plugin.RMPlugin;
import de.rene_majewski.rm_plugin.config.Config;

class ConfigCommand {
  public static boolean config(CommandSender sender, Command command, String[] args, RMPlugin plugin) {
    if ((args.length >= 2) && args[1].equalsIgnoreCase("reload")) {
      return reaload(sender, plugin);
    }

    return false;
  }

  private static boolean reaload(CommandSender sender, RMPlugin plugin) {
    if (sender.hasPermission(Config.PERMISSION_ADMIN_RELOAD)) {
      plugin.getMyConfig().reload();
      sender.sendMessage(plugin.getMyConfig().getString(Config.MESSAGE_CONFIG_RELOAD));
      return true;
    } else {
      sender.sendMessage(ChatColor.RED + plugin.getMyConfig().getString(Config.MESSAGE_NO_PERMISSION));
      return true;
    }
  }
}