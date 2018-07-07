package de.rene_majewski.minecraft_log.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.java.JavaPlugin;

import de.rene_majewski.minecraft_log.config.Config;

/**
 * Liest die Konfiguration für das Plugin neu ein.
 * 
 * @author René Majewski
 * @since 0.1
 */
public class ConfigReloadCommand implements CommandExecutor {
  /**
   * Speichert die Konfiguration-Klasse des Plugins.
   */
  private Config _config;

  /**
   * Initialisiert die Klasse.
   * 
   * @param config Konfiguration-Klasse des Plugins.
   */
  public ConfigReloadCommand(Config config) {
    this._config = config;
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
   */
  public boolean onCommand(CommandSender sender, Command command, java.lang.String label, java.lang.String[] args) {
    if (args.length != 0) {
      Player player = null;
      if (sender instanceof Player) {
        player = (Player)sender;
      }

      if (command.getName().equalsIgnoreCase("minecraftlog")) {
        if ((args.length >= 1) && (args[0].equalsIgnoreCase("config"))) {
          if ((args.length >= 2) && args[1].equalsIgnoreCase("reload")) {
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