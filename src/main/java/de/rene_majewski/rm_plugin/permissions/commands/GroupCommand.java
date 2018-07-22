package de.rene_majewski.rm_plugin.permissions.commands;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.rene_majewski.rm_plugin.RMPlugin;
import de.rene_majewski.rm_plugin.commands.CommandClass;
import de.rene_majewski.rm_plugin.config.Config;
import de.rene_majewski.rm_plugin.permissions.PermissionManager;

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
        this.deleteGroup(args[3], sender);
        return true;
      } else if (args[2].equalsIgnoreCase("add")) {
        if (args.length == 6) {
          this.add(args[3], args[4], args[5], sender);
          return true;
        }
      } else if (args[2].equalsIgnoreCase("remove")) {
        if (args.length == 6) {
          this.remove(args[3], args[4], args[5], sender);
          return true;
        }
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
   * Löscht die angegebene Gruppe.
   * 
   * @param name Name der Gruppe, die gelöscht werden soll.
   * 
   * @param sender Sender, der die Gruppe löscht.
   * 
   * @since 0.2
   */
  private void deleteGroup(String name, CommandSender sender) {
    if (name != null && !name.isEmpty()) {
      PreparedStatement ps = null;
      try {
        ps = this._plugin.getMySql().getConnection().prepareStatement("DELETE FROM " + this._plugin.getMySql().getTableName(Config.DB_TABLE_PERMISSION_GROUP) + " WHERE name=?");
        ps.setString(1, name);
        if (ps.executeUpdate() > 0) {
          this.sendMessage(this._plugin.getMyConfig().getString(Config.MESSAGE_PERMISSION_GROUP_DELETE).replace("?", name), sender);
        } else {
          this.sendMessage(this._plugin.getMyConfig().getString(Config.MESSAGE_PERMISSION_GROUP_DELETE_NO).replace("?", name), sender);
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    } else {
      this.sendMessage(this._plugin.getMyConfig().getString(Config.MESSAGE_ERROR_NO_GROUP), sender);
    }
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
        this.sendErrorMessage(sender, e, this._plugin.getMyConfig().getString(Config.MESSAGE_PERMISSION_GROUP_CREATE_NO).replace("?", name));
      } finally {
        this._plugin.getMySql().closeRessources(null, ps);
      }
    } else {
      this.sendMessage(this._plugin.getMyConfig().getString(Config.MESSAGE_ERROR_NO_GROUP), sender);
    }
  }

  /**
   * Setzt bei einer Gruppe verschiedene Eigenschaften.
   * 
   * @param command Befehl, der ausgefühlt werden soll.
   * 
   * @param arg1 Parameter 1 des Befehls.
   * 
   * @param arg2 Parameter 2 des Befehls.
   * 
   * @param sender Sender, der den Befehl gesendet hat.
   * 
   * @since 0.2
   */
  private void add(String command, String arg1, String arg2, CommandSender sender) {
    if (command.equalsIgnoreCase("parent")) {
      if (arg1 == null || arg1.isEmpty() || arg2 == null || arg2.isEmpty()) {
        this.sendMessage(this._plugin.getMyConfig().getString(Config.MESSAGE_ERROR_PERMISSION_GROUP_ADD_PARENT), sender);
        this.sendMessage(this.createCommandHelpMessage("permission group add parent [Group name] [Parent Group name]", "Fügt einer Grupp einen Vater-Gruppe hinzu."), sender);
        return;
      }

      addParent(arg1, arg2, sender);
    } else if (command.equalsIgnoreCase("permission") || (command.equalsIgnoreCase("perm"))) {
      if (arg1 == null || arg1.isEmpty() || arg2 == null || arg2.isEmpty()) {
        this.sendMessage(this._plugin.getMyConfig().getString(Config.MESSAGE_ERROR_PERMISSION_GROUP_ADD_PERMISSION), sender);
        this.sendMessage(this.createCommandHelpMessage("permission group add permission [Group name] [Permission name]", "Fügt einer Grupp eine Permission hinzu."), sender);
        return;
      }

      addPermission(arg1, arg2, sender);
    }
  }

  /**
   * Löscht bei einer Grupper verschiedene Eigenschaften.
   * 
   * @param command Befehl, der ausgeführt werden soll.
   * 
   * @param arg1 Parameter 1 des Befehls.
   * 
   * @param arg2 Parameter 2 des Befehls.
   * 
   * @param sender Sender, der den Befehl gesendet hat.
   * 
   * @since 0.2
   */
  private void remove(String command, String arg1, String arg2, CommandSender sender) {
    if (command.equalsIgnoreCase("parent")) {
      if (arg1 == null || arg1.isEmpty() || arg2 == null || arg2.isEmpty()) {
        this.sendMessage(this._plugin.getMyConfig().getString(Config.MESSAGE_ERROR_PERMISSION_GROUP_REMOVE_PARENT), sender);
        this.sendMessage(this.createCommandHelpMessage("permission group remove parent [Group name] [Parent Group name]", "Löscht bei einer Vater-Gruppe die Kind-Gruppe."), sender);
        return;
      }

      removeParent(arg1, arg2, sender);
    } else if (command.equalsIgnoreCase("permission") || command.equalsIgnoreCase("perm")) {
      if (arg1 == null || arg1.isEmpty() || arg2 == null || arg2.isEmpty()) {
        this.sendMessage(this._plugin.getMyConfig().getString(Config.MESSAGE_ERROR_PERMISSION_GROUP_ADD_PERMISSION), sender);
        this.sendMessage(this.createCommandHelpMessage("permission group remove permission [Group name] [Permission name]", "Fügt einer Grupp eine Permission hinzu."), sender);
        return;
      }

      removePermission(arg1, arg2, sender);
    }
  }

  /**
   * Fügt einer Vater-Gruppe eine Kind-Gruppe hinzu.
   * 
   * @param child Kind-Gruppe, die der Eltern-Gruppe hinzugefügt werden soll.
   * 
   * @param parent Eltern-Gruppe, der die Kind-Gruppe hinzugefügt werden soll.
   * 
   * @param sender Sender, der den Befehl geschickt hat.
   * 
   * @since 0.2
   */
  private void addParent(String child, String parent, CommandSender sender) {
    PreparedStatement ps = null;
    int group1 = this._plugin.getMySql().getGroupId(child);

    if (group1 == -1) {
      this.sendMessage(this._plugin.getMyConfig().getString(Config.MESSAGE_ERROR_PERMISSION_NO_GROUP_FOUND).replace("?", child), sender);
      return;
    }

    int group2 = this._plugin.getMySql().getGroupId(parent);
    if (group2 == -1) {
      this.sendMessage(this._plugin.getMyConfig().getString(Config.MESSAGE_ERROR_PERMISSION_NO_GROUP_FOUND).replace("?", parent), sender);
      return;
    }

    try {
      ps = this._plugin.getMySql().getConnection().prepareStatement("INSERT INTO " + this._plugin.getMySql().getTableName(Config.DB_TABLE_PERMISSION_GROUP_PARENT) + "(child_id,parent_id) VALUES(?,?)");
      ps.setInt(1, Integer.valueOf(group1));
      ps.setInt(2, Integer.valueOf(group2));
      if (ps.executeUpdate() > 0) {
        this.sendMessage(this._plugin.getMyConfig().getString(Config.MESSAGE_PERMISSION_GROUP_ADD_PARENT).replaceFirst("(\\?)", child).replace("?", parent), sender);
      } else {
        this.sendMessage(this._plugin.getMyConfig().getString(Config.MESSAGE_PERMISSION_GROUP_ADD_PARENT_NO).replaceFirst("(\\?)", child).replace("?", parent), sender);
      }
    } catch (SQLException e) {
      this.sendErrorMessage(sender, e, this._plugin.getMyConfig().getString(Config.MESSAGE_PERMISSION_GROUP_ADD_PARENT_NO).replaceFirst("(\\?)", child).replace("?", parent));
    } finally {
      this._plugin.getMySql().closeRessources(null, ps);
    }
  }

  /**
   * Löscht die Kind-Gruppe einer Vater-Gruppe.
   * 
   * @param child Kind-Gruppe, der von der Eltern-Gruppe gelöscht werden soll.
   * 
   * @param parent Eltern-Gruppe, bei der eine Kind-Gruppe gelöscht wrden soll.
   * 
   * @param sender Sender, der den Befehl geschickt hat.
   * 
   * @since 0.2
   */
  private void removeParent(String child, String parent, CommandSender sender) {
    PreparedStatement ps = null;
    int group1 = this._plugin.getMySql().getGroupId(child);

    if (group1 == -1) {
      this.sendMessage(this._plugin.getMyConfig().getString(Config.MESSAGE_ERROR_PERMISSION_NO_GROUP_FOUND).replace("?", child), sender);
      return;
    }

    int group2 = this._plugin.getMySql().getGroupId(parent);
    if (group2 == -1) {
      this.sendMessage(this._plugin.getMyConfig().getString(Config.MESSAGE_ERROR_PERMISSION_NO_GROUP_FOUND).replace("?", parent), sender);
      return;
    }

    try {
      ps = this._plugin.getMySql().getConnection().prepareStatement("DELETE FROM " + this._plugin.getMySql().getTableName(Config.DB_TABLE_PERMISSION_GROUP_PARENT) + " WHERE child_id=? AND parent_id=?");
      ps.setInt(1, Integer.valueOf(group1));
      ps.setInt(2, Integer.valueOf(group2));
      if (ps.executeUpdate() > 0) {
        this.sendMessage(this._plugin.getMyConfig().getString(Config.MESSAGE_PERMISSION_GROUP_REMOVE_PARENT).replaceFirst("(\\?)", child).replace("?", parent), sender);
      } else {
        this.sendMessage(this._plugin.getMyConfig().getString(Config.MESSAGE_PERMISSION_GROUP_REMOVE_PARENT_NO).replaceFirst("(\\?)", child).replace("?", parent), sender);
      }
    } catch (SQLException e) {
      this.sendErrorMessage(sender, e, this._plugin.getMyConfig().getString(Config.MESSAGE_PERMISSION_GROUP_REMOVE_PARENT_NO).replaceFirst("(\\?)", child).replace("?", parent));
    } finally {
      this._plugin.getMySql().closeRessources(null, ps);
    }
  }

  /**
   * Fügt eine Permission der Gruppe hinzu.
   * 
   * @param group Gruppe, der die Permission hinzugefügt werden soll.
   * 
   * @param permission Permission, die der Gruppe hinzugefügt werden soll.
   * 
   * @param sender Sender, der diesen Befehl gesendet.
   * 
   * @since 0.2
   */
  private void addPermission(String group, String permission, CommandSender sender) {
    PreparedStatement ps = null;
    int group1 = this._plugin.getMySql().getGroupId(group);

    if (group1 == -1) {
      this.sendMessage(this._plugin.getMyConfig().getString(Config.MESSAGE_ERROR_PERMISSION_NO_GROUP_FOUND).replace("?", group), sender);
      return;
    }

    int perm = this._plugin.getMySql().getPermissionId(permission);
    if (perm == -1) {
      this.sendMessage(this._plugin.getMyConfig().getString(Config.MESSAGE_ERROR_NO_PERMISSION_FOUND).replace("?", permission), sender);
      return;
    }

    boolean negate = false;
    if (permission.startsWith("-")) {
      negate = true;
      permission = permission.substring(1);
    }

    try {
      ps = this._plugin.getMySql().getConnection().prepareStatement("INSERT INTO " + this._plugin.getMySql().getTableName(Config.DB_TABLE_PERMISSION_ALLOCATE) + "(group_id,permission_id,clazz,negate) VALUES(?,?,?,?)");
      ps.setInt(1, Integer.valueOf(group1));
      ps.setInt(2, Integer.valueOf(perm));
      ps.setInt(3, PermissionManager.CLAZZ_GROUP);
      ps.setBoolean(4, negate);
      if (ps.executeUpdate() > 0) {
        this.sendMessage(this._plugin.getMyConfig().getString(Config.MESSAGE_PERMISSION_GROUP_ADD_PERMISSION).replaceFirst("(\\?)", group).replace("?", permission), sender);
      } else {
        this.sendMessage(this._plugin.getMyConfig().getString(Config.MESSAGE_PERMISSION_GROUP_ADD_PERMISSION_NO).replaceFirst("(\\?)", group).replace("?", permission), sender);
      }
    } catch (SQLException e) {
      this.sendErrorMessage(sender, e, this._plugin.getMyConfig().getString(Config.MESSAGE_PERMISSION_GROUP_ADD_PERMISSION_NO).replaceFirst("(\\?)", group).replace("?", permission));
    } finally {
      this._plugin.getMySql().closeRessources(null, ps);
    }
  }

  /**
   * Löscht die Permission einer Grupper.
   * 
   * @param group Gruppe, bei der die Permission gelöscht werden soll.
   * 
   * @param permission Permission, die der Gruppe entzogen werden soll.
   * 
   * @sender Sender, der diesen Befehl gesendet hat.
   * 
   * @since 0.2
   */
  private void removePermission(String group, String permission, CommandSender sender) {
    PreparedStatement ps = null;
    int group1 = this._plugin.getMySql().getGroupId(group);

    if (group1 == -1) {
      this.sendMessage(this._plugin.getMyConfig().getString(Config.MESSAGE_ERROR_PERMISSION_NO_GROUP_FOUND).replace("?", group), sender);
      return;
    }

    int perm = this._plugin.getMySql().getPermissionId(permission);
    if (perm == -1) {
      this.sendMessage(this._plugin.getMyConfig().getString(Config.MESSAGE_ERROR_NO_PERMISSION_FOUND).replace("?", permission), sender);
      return;
    }

    if (permission.startsWith("-")) {
      permission = permission.substring(1);
    }

    try {
      ps = this._plugin.getMySql().getConnection().prepareStatement("DELETE FROM " + this._plugin.getMySql().getTableName(Config.DB_TABLE_PERMISSION_ALLOCATE) + " WHERE group_id=? AND permission_id=? AND clazz=?");
      ps.setInt(1, Integer.valueOf(group1));
      ps.setInt(2, Integer.valueOf(perm));
      ps.setInt(3, PermissionManager.CLAZZ_GROUP);
      if (ps.executeUpdate() > 0) {
        this.sendMessage(this._plugin.getMyConfig().getString(Config.MESSAGE_PERMISSION_GROUP_REMOVE_PERMISSION).replaceFirst("(\\?)", group).replace("?", permission), sender);
      } else {
        this.sendMessage(this._plugin.getMyConfig().getString(Config.MESSAGE_PERMISSION_GROUP_REMOVE_PERMISSION_NO).replaceFirst("(\\?)", group).replace("?", permission), sender);
      }
    } catch (SQLException e) {
      this.sendErrorMessage(sender, e, this._plugin.getMyConfig().getString(Config.MESSAGE_PERMISSION_GROUP_REMOVE_PERMISSION_NO).replaceFirst("(\\?)", group).replace("?", permission));
    } finally {
      this._plugin.getMySql().closeRessources(null, ps);
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