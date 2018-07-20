package de.rene_majewski.rm_plugin.permissions.commands;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.rene_majewski.rm_plugin.RMPlugin;
import de.rene_majewski.rm_plugin.commands.CommandClass;
import de.rene_majewski.rm_plugin.config.Config;

/**
 * Reagiert auf die Gruppen-Befehle.
 * 
 * @author René Majewski
 * 
 * @since 0.2
 */
class GroupCommand extends CommandClass {

  /**
   * Initialisiert diese Klasse
   * 
   * @param plugin Objekt der Plugin-Main-Klasse.
   * 
   * @since 0.2
   */
  public GroupCommand(RMPlugin plugin) {
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
    this.sendMessage(this.createCommandHelpMessage("permission group help", "Hilfetext für den Befehl für die Gruppen-Berechtigungen."), sender);   
  }

  /**
   * Reagiert auf die Gruppen-Befehle für die Berechtigungen.
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
  public boolean onGroupCommand(CommandSender sender, Command command, java.lang.String label, java.lang.String[] args) {
    if (args.length > 2) {
      if (args[2].equalsIgnoreCase("list")) {
        this.list(sender);
        return true;
      } else if (args[2].equalsIgnoreCase("new")) {
        this.createGroup(args[3], sender);
        return true;
      } else if (args[2].equalsIgnoreCase("delete")) {
        return true;
      } else if (args[2].equalsIgnoreCase("add")) {
        return true;
      } else if (args[2].equalsIgnoreCase("remove")) {
        return true;
      } else if (args[2].equalsIgnoreCase("help")) {
        this.sendHelpMessage(sender);
        return true;
      } else {

      }
    }

    this.sendHelpMessage(sender);
    return true;
  }

  /**
   * Erstellt eine neue Gruppe.
   * 
   * @param name Name der neuen Gruppe.
   * 
   * @param sender Sender, der die Gruppe erzeugt.
   * 
   * @since 0.2
   */
  private void createGroup(String name, CommandSender sender) {
    if (name != null && !name.isEmpty()) {
      PreparedStatement ps = null;
      try {
        ps = this._plugin.getMySql().getConnection().prepareStatement("INSERT INTO " + this._plugin.getMySql().getTableName(Config.DB_TABLE_PERMISSION_GROUP) + "(name) VALUES (?)");
        ps.setString(1, name);
        if (ps.executeUpdate() > 0) {
          this.sendMessage(this._plugin.getMyConfig().getString(Config.MESSAGE_PERMISSION_GROUP_CREATE).replace("?", name), sender);
        } else {
          this.sendMessage(this._plugin.getMyConfig().getString(Config.MESSAGE_PERMISSION_GROUP_CREATE_NO).replace("?", name), sender);
        }
      } catch (SQLException e) {
        e.printStackTrace();
        this.sendMessage(this._plugin.getMyConfig().getString(Config.MESSAGE_ERROR), sender);
        this.sendMessage(this._plugin.getMyConfig().getString(Config.MESSAGE_PERMISSION_GROUP_CREATE_NO).replace("?", name), sender);
      } finally {
        this._plugin.getMySql().closeRessources(null, ps);
      }
    }
  }

/**
   * Zeigt die Liste mit allen Gruppen an.
   * 
   * @param sender Objekt, dass den Befehl gesendet hat.
   * 
   * @since 0.2
   */
  private void list(CommandSender sender) {
    PreparedStatement ps = null;
    ResultSet rs = null;

    try {
      ps = this._plugin.getMySql().getConnection().prepareStatement("SELECT name FROM " + this._plugin.getMySql().getTableName(Config.DB_TABLE_PERMISSION_GROUP) + " ORDER BY name");
      rs = ps.executeQuery();

      StringBuffer sb = new StringBuffer();
      sb.append(this._plugin.getMyConfig().getString(Config.COLOR_PERMISSION_HEADER));
      sb.append("Liste aller Gruppen");
      sb.append("\n");
      sb.append(this._plugin.getMyConfig().getString(Config.COLOR_PERMISSION_TEXT));
      sb.append("-------------------\n");

      while (rs.next()) {
        sb.append(this._plugin.getMyConfig().getString(Config.COLOR_PERMISSION_TEXT));
        sb.append("-");
        sb.append(this._plugin.getMyConfig().getString(Config.COLOR_PERMISSION_VALUE));
        sb.append(rs.getString(1));
        sb.append("\n");
      }

      sender.sendMessage(sb.toString());
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      this._plugin.getMySql().closeRessources(rs, ps);
    }
  }
}