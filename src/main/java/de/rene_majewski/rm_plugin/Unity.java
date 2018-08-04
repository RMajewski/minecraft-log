package de.rene_majewski.rm_plugin;

/**
 * Von dieser Klasse werden alle Sub-Plugin-Klassen abgeleitet.
 * 
 * @since 0.2
 * @author René Majewski
 */
public abstract class Unity {
  /**
   * Speichert das Plugin-Objekt.
   */
  protected RMPlugin _plugin;

  /**
   * Initialisiert die Sub-Plugin-Klasse.
   * 
   * @param plugin Instanz der Haupt-Plugin-Klasse.
   */
  public Unity(RMPlugin plugin) {
    this._plugin = plugin;
  }
  /**
   * Initialisiert die einzelnen Listeners und registriert sie.
   * 
   * @since 0.2
   */
  protected abstract void registerListeners();
}