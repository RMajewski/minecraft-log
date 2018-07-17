package de.rene_majewski.rm_plugin.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.rene_majewski.rm_plugin.RMPlugin;
import de.rene_majewski.rm_plugin.config.Config;

/**
 * Liest die Konfiguration für das Plugin neu ein.
 * 
 * @author René Majewski
 * @since 0.1
 */
public class RMPluginCommand implements CommandExecutor {
  /**
   * Speichert die Plugin-Klasse.
   */
  private RMPlugin _plugin;

  /**
   * Initialisiert die Klasse.
   * 
   * @param config Konfiguration-Klasse des Plugins.
   */
  public RMPluginCommand(RMPlugin plugin) {
    this._plugin = plugin;
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

      if (command.getName().equalsIgnoreCase("rmplugin")) {
        if ((args.length >= 1) && (args[0].equalsIgnoreCase("config"))) {
          return ConfigCommand.config(sender, command, args, this._plugin);
        }
      }
    }

    return false;
  }
}