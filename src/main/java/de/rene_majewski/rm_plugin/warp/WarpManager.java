package de.rene_majewski.rm_plugin.warp;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import de.rene_majewski.rm_plugin.RMPlugin;
import de.rene_majewski.rm_plugin.Unity;
import de.rene_majewski.rm_plugin.config.Config;

/**
 * Organisiert die einzelnen Warps.
 * 
 * @author René Majewski
 * 
 * @since 0.2
 */
public class WarpManager extends Unity {
  /**
   * Initialisiert den Warp-Manager.
   * 
   * @param plugin Objekt der Plugin-Main-Klasse.
   * 
   * @since 0.2
   */
  public WarpManager(RMPlugin plugin) {
    super(plugin);
  }

  /**
   * Initialisiert die einzelnen Listeners.
   * 
   * @since 0.2
   */
  protected void registerListeners() {

  }

  /**
   * Legt einen Warp-Punkt an.
   * 
   * @param player Spieler-Objekt, dass den Warp-Punkt anlegt.
   * 
   * @param name Name des Warp-Punktes.
   * 
   * @since 0.2
   */
  public void setWarp(Player player, String name) {
    if (!hasWarp(player, name)) {
      PreparedStatement ps = null;
      
      try {
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

        ps = this._plugin.getMySql().getConnection().prepareStatement(
          "INSERT INTO " + this._plugin.getMySql().getTableName(Config.DB_TABLE_WARP) +
          "(player_id, world_id, name, x, y, z, yaw, pitch) VALUES(?, ?, ?, ?, ?, ?, ?, ?)");
        ps.setInt(1, pid);
        ps.setInt(2, wid);
        ps.setString(3, name);
        ps.setInt(4, player.getLocation().getBlockX());
        ps.setInt(5, player.getLocation().getBlockY());
        ps.setInt(6, player.getLocation().getBlockZ());
        ps.setFloat(7, player.getLocation().getPitch());
        ps.setFloat(8, player.getLocation().getYaw());

        if (ps.executeUpdate() > 0) {
          this._plugin.sendMessage(player, this._plugin.getMyConfig().getString(Config.MESSAGE_WARP_SET_WARP).replace("?", name));
        } else {
          this._plugin.sendMessage(player, this._plugin.getMyConfig().getString(Config.MESSAGE_WARP_SET_WARP_NOT).replace("?", name));
        }
      } catch (SQLException e) {
        this._plugin.sendErrorMessage(player, this._plugin.getMyConfig().getString(Config.MESSAGE_ERROR), e);
      } finally {
        this._plugin.getMySql().closeRessources(null, ps);
      }
    } else {
      this._plugin.sendMessage(player, this._plugin.getMyConfig().getString(Config.MESSAGE_WARP_SET_WARP_EXISTS).replace("?", name));
    }
  }

  /**
   * Löscht den Warp-Punkt.
   * 
   * @param player Spieler-Objekt, dass den Warp-Punkt löscht.
   * 
   * @param name Name des Warp-Punktes, der gelöscht werden soll.
   * 
   * @since 0.2
   */
  public void delWarp(Player player, String name) {
    if (hasWarp(player, name)) {
      PreparedStatement ps = null;
      
      try {
        ps = this._plugin.getMySql().getConnection().prepareStatement(
          "DELETE FROM " + this._plugin.getMySql().getTableName(Config.DB_TABLE_WARP) +
          " WHERE name = ?");
        ps.setString(1, name);

        if (ps.executeUpdate() > 0) {
          this._plugin.sendMessage(player, this._plugin.getMyConfig().getString(Config.MESSAGE_WARP_DEL_WARP).replace("?", name));
        } else {
          this._plugin.sendMessage(player, this._plugin.getMyConfig().getString(Config.MESSAGE_WARP_DEL_WARP_NOT).replace("?", name));
        }
      } catch (SQLException e) {
        this._plugin.sendErrorMessage(player, this._plugin.getMyConfig().getString(Config.MESSAGE_ERROR), e);
      } finally {
        this._plugin.getMySql().closeRessources(null, ps);
      }
    } else {
      this._plugin.sendMessage(player, this._plugin.getMyConfig().getString(Config.MESSAGE_WARP_SET_WARP_EXISTS).replace("?", name));
    }
  }

  /**
   * Teleportiert den Spieler zum angegeben Warp-Punkt.
   * 
   * @param player Spieler-Objekt, dass zum Warp-Punkt teleportiert werden
   * soll.
   * 
   * @param name Name des Warp-Punktes.
   * 
   * @since 0.2
   */
  public void warp(Player player, String name) {
    if (hasWarp(player, name)) {
      PreparedStatement ps = null;
      ResultSet rs = null;
      
      try {
        ps = this._plugin.getMySql().getConnection().prepareStatement(
          "SELECT h.x, h.y, h.z, h.yaw, h.pitch, w.name AS world FROM " +
          this._plugin.getMySql().getTableName(Config.DB_TABLE_WARP) + " AS h INNER JOIN " +
          this._plugin.getMySql().getTableName(Config.DB_TABLE_WORLD) + " AS w ON (w.id = h.world_id) WHERE h.name = ?");
        ps.setString(1, name);

        rs = ps.executeQuery();

        if (rs.next()) {
          Location loc = new Location(
            Bukkit.getWorld(rs.getString("world")),
            rs.getInt("x"),
            rs.getInt("y"),
            rs.getInt("z"),
            rs.getFloat("yaw"),
            rs.getFloat("pitch")
          );
          player.teleport(loc);
          this._plugin.sendMessage(player, this._plugin.getMyConfig().getString(Config.MESSAGE_WARP_TELEPORT).replace("?", name));
        } else {
          this._plugin.sendMessage(player, this._plugin.getMyConfig().getString(Config.MESSAGE_WARP_SET_WARP_NOT));
        }  
      } catch (SQLException e) {
        this._plugin.sendErrorMessage(player, this._plugin.getMyConfig().getString(Config.MESSAGE_ERROR), e);
      } finally {
        this._plugin.getMySql().closeRessources(rs, ps);
      }
    } else {
      this._plugin.sendMessage(player, this._plugin.getMyConfig().getString(Config.MESSAGE_WARP_SET_WARP_EXISTS).replace("?", name));
    }
  }

  /**
   * Liest alle Warp-Punkte aus der Datenbank und bereitet diese zur Anzeige
   * auf.
   * 
   * @param player Spieler, der die Liste der Warp-Punkte angezeigt bekommen
   * möchte.
   * 
   * @since 0.2
   */
  public void list(Player player) {
    PreparedStatement ps = null;
    ResultSet rs = null;
    
    try {
      ps = this._plugin.getMySql().getConnection().prepareStatement(
        "SELECT name FROM " +
        this._plugin.getMySql().getTableName(Config.DB_TABLE_WARP) + " ORDER BY name");

      rs = ps.executeQuery();

      String tmp = "";

      while (rs.next()) {
        if (rs.getRow() > 1) {
          tmp += ", ";
        }
        tmp += "'" + rs.getString("name") + "'";
      }

      if (tmp.length() > 0) {
        this._plugin.sendMessage(player, this._plugin.getMyConfig().getString(Config.MESSAGE_WARP_LIST).replace("?", tmp));
      } else {
        this._plugin.sendMessage(player, this._plugin.getMyConfig().getString(Config.MESSAGE_WARP_LIST_NOT));
      }

    } catch (SQLException e) {
      this._plugin.sendErrorMessage(player, this._plugin.getMyConfig().getString(Config.MESSAGE_ERROR), e);
    } finally {
      this._plugin.getMySql().closeRessources(rs, ps);
    }
  }

  /**
   * Überprüft, ob der angegebene Warp-Punkt schon existiert.
   * 
   * @param name Name des Warp-Punktes, der auf seine Existenz überprüft werden
   * soll.
   * 
   * @param player Spieler, der einen Warp-Befehl aufgerufen hat.
   * 
   * @return {@code true}, wenn der Warp-Punkt schon existiert. {@code false},
   * wenn der Warp-Punkt noch nicht existiert.
   * 
   * @since 0.2
   */
  private boolean hasWarp(Player player, String name) {
    boolean result = false;

    PreparedStatement ps = null;
    ResultSet rs = null;

    try {
      ps = this._plugin.getMySql().getConnection().prepareStatement("SELECT name FROM " +
        this._plugin.getMySql().getTableName(Config.DB_TABLE_WARP) +
        " WHERE name = ?");
      ps.setString(1, name);

      rs = ps.executeQuery();
      result = rs.next();
    } catch (SQLException e) {
      this._plugin.sendErrorMessage(player, this._plugin.getMyConfig().getString(Config.MESSAGE_ERROR), e);
    } finally {
      this._plugin.getMySql().closeRessources(rs, ps);
    }

    return result;
  }
}