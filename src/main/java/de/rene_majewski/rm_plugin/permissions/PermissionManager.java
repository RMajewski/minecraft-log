package de.rene_majewski.rm_plugin.permissions;

import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import de.rene_majewski.rm_plugin.RMPlugin;
import de.rene_majewski.rm_plugin.config.Config;

/**
 * Organisiert die einzelnen Permissions.
 * 
 * @since 0.2
 * @author René Majewski
 */
public class PermissionManager {
  /**
   * Speichert die Main-Klasse des Plugins.
   * 
   * @since 0.2
   */
  private RMPlugin _plugin;

  /**
   * Speichert die einzelnen Spieler-Berechtigungen.
   * 
   * @since 0.2
   */
  private HashMap<UUID, PermissionAttachment> _playerPermissions;

  /**
   * Initialisiert die Klasse.
   * 
   * @param config Konfiguration des Objektes.
   * 
   * @since 0.2
   */
  public PermissionManager(RMPlugin plugin) {
    super();

    this._plugin = plugin;

    this._playerPermissions = new HashMap<UUID,PermissionAttachment>();
  }

  /**
   * Erstellt die Berechtigungen für den Spieler.
   * 
   * @param player Spieler, bei dem die Berechtigungen erstellt werden sollen.
   * 
   * @since 0.2
   */
  public void setupPermission(Player player) {
    PermissionAttachment attachment = player.addAttachment(this._plugin);
    this._playerPermissions.put(player.getUniqueId(), attachment);
    permissionsSetter(player.getUniqueId());
  }

  /**
   * Ermittelt die einzelnen Permissions für den Spieler.
   * 
   * @param uniqueId UUID des Spielers, dessen Permissions ermittelt werden soll.
   */
  private void permissionsSetter(UUID uniqueId) {
    PermissionAttachment attachment = this._playerPermissions.get(uniqueId);

  }

}