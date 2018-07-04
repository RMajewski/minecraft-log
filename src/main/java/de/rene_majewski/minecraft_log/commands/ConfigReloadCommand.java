package de.rene_majewski.minecraft_log.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.java.JavaPlugin;

import de.rene_majewski.minecraft_log.config.Config;

public class ConfigReloadCommand implements CommandExecutor {
  private Config _config;

  public ConfigReloadCommand(Config config) {
    this._config = config;
  }

  public boolean onCommand(CommandSender sender, Command command, java.lang.String label, java.lang.String[] args) {
    if (args.length != 0) {
      Player player = null;
      if (sender instanceof Player) {
        player = (Player)sender;
      }

      if (command.getName().equalsIgnoreCase("minecraftlog")) {
        if ((args.length >= 1) && (args[0].equalsIgnoreCase("config"))) {
          if (args[1].equalsIgnoreCase("reload")) {
            if (sender.hasPermission(Config.PERMISSION_ADMIN_RELOAD)) {
              this._config.reload();
              sender.sendMessage(this._config.getString(Config.MESSAGE_CONFIG_RELOAD));
              return true;
            } else {
              sender.sendMessage(ChatColor.RED + this._config.getString(Config.MESSAGE_NO_PERMISSION));
              return true;
            }
          }
        }
      }
    }

    return false;
  }
}