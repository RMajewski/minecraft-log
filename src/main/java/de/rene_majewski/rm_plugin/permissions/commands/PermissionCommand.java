package de.rene_majewski.rm_plugin.permissions.commands;

import de.rene_majewski.rm_plugin.permissions.PermissionManager;

/**
 * Dient als Grundlage für die Permission-Befehle.
 * 
 * @author René Majewski
 * @since 0.2
 */
public class PermissionCommand {
  /**
   * Speichert den PermissionManager.
   * 
   * @since 0.2
   */
  protected PermissionManager _manager;

  /**
   * Initialisiert die Klasse und speichert den Permission-Manager.
   * 
   * @param manager PermissionManager-Klasse.
   */
  public PermissionCommand(PermissionManager manager) {
    this._manager = manager;
  }
}