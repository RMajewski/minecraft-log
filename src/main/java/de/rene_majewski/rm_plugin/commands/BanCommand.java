package de.rene_majewski.rm_plugin.commands;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.rene_majewski.rm_plugin.RMPlugin;
import de.rene_majewski.rm_plugin.config.Config;

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
    if (args.length >= 2) {
      if (args[0].equalsIgnoreCase("ban")) {


        if (args[2].length() > 0) {
          int pid = this._plugin.getMySql().getPlayerId(this._plugin.getPlayerFromDisplayName(args[2]));
          if (pid == -1) {
            this.sendMessage(this._plugin.getMyConfig().getString(Config.MESSAGE_ERROR_NO_PLAYER).replace("?", args[2]), sender);
            return true;
          }

          if (args[1].equalsIgnoreCase("ban")) {
            this.ban(sender, pid, args[2], this.getStringFromArray(args, 3));
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
    this.sendMessage(this.createCommandHelpMessage("ban ban [Name] [Grund]", "Bannt einen Spieler."), sender);
    this.sendMessage(this.createCommandHelpMessage("ban unban [Name]", "Hebt die Bannung eines Spielers auf."), sender);
    this.sendMessage(this.createCommandHelpMessage("ban list", "Zeigt die Liste aller gebannten Spieler an"), sender);
    this.sendMessage(this.createCommandHelpMessage("ban show [Name]", "Zeigt alle Informationen über die Bannung eines Spielers an."), sender);
  }

  /**
   * Bannt einen Spieler.
   * 
   * @param sender Objekt, dass den Bann-Befehl gesendet hat.
   * 
   * @param playerId Spieler, der gebannt werden soll.
   * 
   * @param playerName Display-Name des Spielers, der gebannt werden soll.
   * 
   * @param description Begründung, warum der Spieler gebannt werden soll.
   * 
   * @since 0.2
   */
  private void ban(CommandSender sender, int playerId, String playerName, String description) {
    int fid = getSenderId(sender);

    if (playerId == -1) {
      this.sendMessage(this._plugin.getMyConfig().getString(Config.MESSAGE_ERROR_NO_PLAYER), sender);
      return;
    }

    PreparedStatement ps = null;
    try {
      ps = this._plugin.getMySql().getConnection().prepareStatement(
        "INSERT INTO " + this._plugin.getMySql().getTableName(Config.DB_TABLE_BAN) +
        "(player_id, from_id, description) VALUES(?, ?, ?)");
      ps.setInt(1, playerId);
      ps.setInt(2, fid);
      ps.setString(3, description);
      if (ps.executeUpdate() > 0) {
        this.sendMessage(this._plugin.getMyConfig().getString(Config.MESSAGE_BAN_BAN).replace("?", playerName), sender);
      } else {
        this.sendMessage(this._plugin.getMyConfig().getString(Config.MESSAGE_BAN_NO_BAN), sender);
      }
    } catch (SQLException e) {
      this.sendErrorMessage(sender, e, this._plugin.getMyConfig().getString(Config.MESSAGE_ERROR));
    } finally {
      this._plugin.getMySql().closeRessources(null, ps);
    }
  }

  /**
   * Ermittelt die ID des Spielers, der den Bann-Befehl gesendet hat.
   * 
   * @param sender Objekt, dass den Befehl gesendet hat.
   * 
   * @return Gibt {@code -1} zurück, wenn der Befehl von der Console ausgeführt
   * wurde. Wurde der Befehl von einen Spieler ausgeführt, so wird seine ID
   * zurück gegeben.
   * 
   * @since 0.2
   */
  private int getSenderId(CommandSender sender) {
    if (sender instanceof Player) {
      return this._plugin.getMySql().getPlayerId((Player)sender);
    }

    return -1;
  }
}