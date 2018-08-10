package de.rene_majewski.rm_plugin.commands;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.rene_majewski.rm_plugin.RMPlugin;
import de.rene_majewski.rm_plugin.config.Config;

/**
 * Befehle zum verwalten der eigenen Home-Punkte und zum teleportieren an
 * diese.
 * 
 * @author René Majewski
 * 
 * @since 0.2
 */
public class HomeCommand extends CommandClass {
  /**
   * Initialisiert die Klasse.
   * 
   * @param plugin Objekt der Plugin-Main-Klasse.
   * 
   * @since 0.2
   */
  public HomeCommand(RMPlugin plugin) {
    super(plugin);
  }

  /**
   * Reagiert auf die Befehle {@code /rmplugin home tp},
   * {@code /rmplugin home sethome} und {@code /rmplugin home delhome}.
   * 
   * @since 0.2
   */
  public boolean homeCommand(CommandSender sender, Command command, java.lang.String label, java.lang.String[] args) {
    if (args.length >= 2) {
      if (args[0].equalsIgnoreCase("home")) {
        Player player = null;

        if (sender instanceof Player) {
          player = (Player)sender;
        } else {
          sender.sendMessage(this._plugin.getMyConfig().getString(Config.MESSAGE_NO_CONSOLE));
          return true;
        }

        if (args[1].equalsIgnoreCase("list")) {
          listHomes(player);
          return true;
        }

        if (args[2].length() > 0) {
          if (args[1].equalsIgnoreCase("tp")) {
            tpHome(player, args[2]);
            return true;
          } else if (args[1].equalsIgnoreCase("sethome")) {
            setHome(player, args[2]);
            return true;
          } else if (args[1].equalsIgnoreCase("delhome")) {
            delHome(player, args[2]);
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
    this.sendMessage(this.createCommandHelpMessage("home help", "Zeigt den Hilfetext für die Home-Befehle an"), sender);
    this.sendMessage(this.createCommandHelpMessage("help tp [Name]", "Teleportiert dich zu den mit Name angegebenen Home-Punkt. Du musst den Home-Punkt vorher erstellt haben"), sender);
    this.sendMessage(this.createCommandHelpMessage("help sethome [Name]", "Erstellt den Home-Punkt mit den angegebenen Namen."), sender);
    this.sendMessage(this.createCommandHelpMessage("help delhome [Name]", "Löscht den Home-Punkt mit denen angegebenen Namen. Du musst den Home-Punkt vorher gesetzt haben."), sender);
  }

  /**
   * Setzt den Home-Punkt des Spielers auf die aktuellen Koordinaten des
   * Spielers.
   * 
   * @param player Spieler, der den Befehl aufgerufen hat.
   * 
   * @param name Name des Home-Punktes.
   * 
   * @since 0.2
   */
  private void setHome(Player player, String name) {
    if (isHomeCreated(name, player)) {
      this._plugin.sendMessage(player, this._plugin.getMyConfig().getString(Config.MESSAGE_HOME_HOME_EXISTS).replace("?", name));
      return;
    }

    PreparedStatement ps = null;

    try {
      ps = this._plugin.getMySql().getConnection().prepareStatement("INSERT INTO " + this._plugin.getMySql().getTableName(Config.DB_TABLE_HOME) + "(player_id, world_id, name, x, y, z, yaw, pitch) VALUES(?, ?, ?, ?, ?, ?, ?, ?)");

      int pid = this._plugin.getMySql().getPlayerId(player);
      if (pid == -1) {
        String tmp = this._plugin.getMyConfig().getString(Config.MESSAGE_ERROR_NO_PLAYER);
        tmp = tmp.replace("?", player.getDisplayName());
        this._plugin.sendMessage(player, tmp);
      }

      int wid = this._plugin.getMySql().getWorldId(player.getWorld());
      if (wid == -1) {
        String tmp = this._plugin.getMyConfig().getString(Config.MESSAGE_ERROR_NO_WORLD);
        tmp = tmp.replace("?", player.getWorld().getName());
        this._plugin.sendMessage(player, tmp);
      }

      ps.setInt(1, pid);
      ps.setInt(2, wid);
      ps.setString(3, name);
      ps.setInt(4, player.getLocation().getBlockX());
      ps.setInt(5, player.getLocation().getBlockY());
      ps.setInt(6, player.getLocation().getBlockZ());
      ps.setFloat(7, player.getLocation().getPitch());
      ps.setFloat(8, player.getLocation().getYaw());
      if (ps.executeUpdate() > 0) {
        this._plugin.sendMessage(player, this._plugin.getMyConfig().getString(Config.MESSAGE_HOME_SET_HOME).replace("?", name));
      } else {
        this._plugin.sendMessage(player, this._plugin.getMyConfig().getString(Config.MESSAGE_ERROR_SET_HOME).replace("?", name));
      }
    } catch (SQLException e) {
      this._plugin.sendErrorMessage(player, this._plugin.getMyConfig().getString(Config.MESSAGE_ERROR), e);
    } finally {
      this._plugin.getMySql().closeRessources(null, ps);
    }
  }

  /**
   * Löscht den Home-Punkt des Spielers.
   * 
   * @param player Spieler, der den Befehl aufgerufen hat.
   * 
   * @param name Name des Home-Punktes, der gelöscht werden soll.
   * 
   * @since 0.2
   */
  private void delHome(Player player, String name) {
    if (!isHomeCreated(name, player)) {
      this._plugin.sendMessage(player, this._plugin.getMyConfig().getString(Config.MESSAGE_HOME_NOT_HOME).replace("?", name));
      return;
    }

    PreparedStatement ps = null;
    try {
      ps = this._plugin.getMySql().getConnection().prepareStatement("DELETE FROM " + this._plugin.getMySql().getTableName(Config.DB_TABLE_HOME) + " WHERE name = ? AND player_id = ?");
      ps.setString(1, name);
      ps.setInt(2, this._plugin.getMySql().getPlayerId(player));
      if (ps.executeUpdate() > 0) {
        this._plugin.sendMessage(player, this._plugin.getMyConfig().getString(Config.MESSAGE_HOME_DELETE_HOME).replace("?", name));
      } else {
        this._plugin.sendMessage(player, this._plugin.getMyConfig().getString(Config.MESSAGE_ERROR_DELETE_HOME).replace("?", name));
      }
    } catch (SQLException e) {
      this._plugin.sendErrorMessage(player, this._plugin.getMyConfig().getString(Config.MESSAGE_ERROR), e);
    } finally {
      this._plugin.getMySql().closeRessources(null, ps);
    }
  }

  /**
   * Teleportiert den Spieler zum angegebenen Home-Punkt.
   * 
   * @param player Spieler, der sich zu seinem Home-Punkt teleportieren möchte.
   * 
   * @param name Name des Home-Punktes.
   * 
   * @since 0.2
   */
  private void tpHome(Player player, String name) {
    if (!isHomeCreated(name, player)) {
      this._plugin.sendMessage(player, this._plugin.getMyConfig().getString(Config.MESSAGE_HOME_NOT_HOME).replace("?", name));
      return;
    }

    PreparedStatement ps = null;
    ResultSet rs = null;
    try {
      ps = this._plugin.getMySql().getConnection().prepareStatement(
        "SELECT h.x, h.y, h.z, h.yaw, h.pitch, w.name AS world FROM " +
        this._plugin.getMySql().getTableName(Config.DB_TABLE_HOME) + " AS h INNER JOIN " +
        this._plugin.getMySql().getTableName(Config.DB_TABLE_WORLD) + " AS w ON (w.id = h.world_id) WHERE h.name = ? AND h.player_id = ?");
      ps.setString(1, name);
      ps.setInt(2, this._plugin.getMySql().getPlayerId(player));

      rs = ps.executeQuery();
      
      if (rs.next()) {
        Location loc = new Location(
          Bukkit.getWorld(rs.getString("world")),
          rs.getInt("x"),
          rs.getInt("y"),
          rs.getInt("z"),
          rs.getFloat("yaw"),
          rs.getFloat("pitch"));
        player.teleport(loc);
        this._plugin.sendMessage(player, this._plugin.getMyConfig().getString(Config.MESSAGE_HOME_TELEPORT).replace("?", name));
      } else {
        this._plugin.sendMessage(player, this._plugin.getMyConfig().getString(Config.MESSAGE_HOME_NOT_HOME));
      }
    } catch (SQLException e) {
      this._plugin.sendErrorMessage(player, this._plugin.getMyConfig().getString(Config.MESSAGE_ERROR), e);
    } finally {
      this._plugin.getMySql().closeRessources(null, ps);
    }    
  }

  /**
   * Gibt eine Liste mit allen Homes eines Spielers aus.
   * 
   * @param player Spieler, dessen Homes ausgegeben werden sollen.
   * 
   * @since 0.2
   */
  private void listHomes(Player player) {
    PreparedStatement ps = null;
    ResultSet rs = null;
    try {
      ps = this._plugin.getMySql().getConnection().prepareStatement("SELECT name FROM " + this._plugin.getMySql().getTableName(Config.DB_TABLE_HOME) + " WHERE player_id = ?");
      ps.setInt(1, this._plugin.getMySql().getPlayerId(player));

      rs = ps.executeQuery();
      
      String tmp = "";

      while(rs.next()) {
        if (rs.getRow() > 1) {
          tmp += ", ";
        }
        tmp += "'" + rs.getString("name") + "'";
      }

      this._plugin.sendMessage(player, this._plugin.getMyConfig().getString(Config.MESSAGE_HOME_LIST).replace("?", tmp));
    } catch (SQLException e) {
      this._plugin.sendErrorMessage(player, this._plugin.getMyConfig().getString(Config.MESSAGE_ERROR), e);
    } finally {
      this._plugin.getMySql().closeRessources(null, ps);
    }
  }

  /**
   * Überprüft, ob der angegebene Home-Punkt erstellt wurde.
   * 
   * @param name Name des Home-Punktes.
   * 
   * @param player Spieler, dessen Home-Punkt überprüft werden soll.
   * 
   * @since 0.2
   */
  private boolean isHomeCreated(String name, Player player) {
    boolean result = false;

    PreparedStatement ps = null;
    ResultSet rs = null;
    try {
      ps = this._plugin.getMySql().getConnection().prepareStatement("SELECT name FROM " + this._plugin.getMySql().getTableName(Config.DB_TABLE_HOME) + " WHERE name = ? AND player_id = ?");
      ps.setString(1, name);
      ps.setInt(2, this._plugin.getMySql().getPlayerId(player));
      rs = ps.executeQuery();

      if (rs.next()) {
        result = true;
      }
    } catch (SQLException e) {
      this._plugin.sendErrorMessage(player, this._plugin.getMyConfig().getString(Config.MESSAGE_ERROR), e);
    } finally {
      this._plugin.getMySql().closeRessources(null, ps);
    }

    return result;
  }
}