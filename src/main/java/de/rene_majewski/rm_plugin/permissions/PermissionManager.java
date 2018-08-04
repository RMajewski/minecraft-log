package de.rene_majewski.rm_plugin.permissions;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import de.rene_majewski.rm_plugin.RMPlugin;
import de.rene_majewski.rm_plugin.Unity;
import de.rene_majewski.rm_plugin.permissions.commands.PermissionCommand;

/**
 * Organisiert die einzelnen Permissions.
 * 
 * @since 0.2
 * @author René Majewski
 */
public class PermissionManager extends Unity {
  /**
   * Gibt den Wert für eine Spieler-Permission an.
   * 
   * @since 0.2
   */
  public static final int CLAZZ_PLAYER = 1;

  /**
   * Gibt den Wert für eine Gruppen-Permission an.
   * 
   * @since 0.2
   */
  public static final int CLAZZ_GROUP = 2;

  /**
   * Speichert die Befehls-Klasse.
   */
  private PermissionCommand _command;

  /**
   * Speichert die einzelnen Spieler-Berechtigungen.
   * 
   * @since 0.2
   */
  private HashMap<UUID, PermissionAttachment> _playerPermissions;

  /**
   * Initialisiert die Klasse.
   * 
   * @param plugin Instanz der Main-Plugin-Klasse.
   * 
   * @since 0.2
   */
  public PermissionManager(RMPlugin plugin) {
    super(plugin);

    this._playerPermissions = new HashMap<UUID,PermissionAttachment>();

    this._command = new PermissionCommand(this._plugin);
    this.registerListeners();
  }

  /**
   * Initialisiert die einzelnen Listeners und registriert sie.
   * 
   * @since 0.2
   */
  @Override
  protected void registerListeners() {

  }

  /**
   * Ermittelt die einzelnen Permissions für den Spieler.
   * 
   * @param uniqueId UUID des Spielers, dessen Permissions ermittelt werden soll.
   */
  private void permissionsSetter(UUID uniqueId) {
    PermissionAttachment attachment = this._playerPermissions.get(uniqueId);

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
   * Gibt die Befehls-Klasse zurück.
   * 
   * @return PermissionCommand Befehls-Klasse.
   */
  public PermissionCommand getPermissionCommand() {
    return this._command;
  }
}