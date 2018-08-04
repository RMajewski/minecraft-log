package de.rene_majewski.rm_plugin.economy;

import java.util.HashMap;

import de.rene_majewski.rm_plugin.RMPlugin;
import de.rene_majewski.rm_plugin.Unity;

/**
 * Basis-Klasse für das Wirtschaftssystem.
 * 
 * @since 0.2
 * @author René Majewski
 */
public class EconomyManager extends Unity {
  /**
   * Speichert zu jeden Spieler den dazugehörigen Betrag.
   */
  private HashMap<String, Double> _balance;

  /**
   * Initialisiert diese Klasse.
   * 
   * @param plugin Instanz der Main-Plugin-Klasse.
   */
  public EconomyManager(RMPlugin plugin) {
    super(plugin);

    this._balance = new HashMap<String, Double>();
  }

  /**
   * Initialisiert die einzelnen Listener.
   */
  @Override
  protected void registerListeners() {

  }

  /**
   * Setzt den Geld-Betrag, den ein Spieler hat neu.
   * 
   * @param uuid UUID des Spielers, dessen Geld-Betrag neu gesetzt werden soll.
   * 
   * @param amount Neuer Betrag, den der Spieler zur Verfügung hat.
   */
  public void setBalance(String uuid, double amount) {
    this._balance.put(uuid, amount);
  }

  /**
   * Ermittelt den Geld-Betrag für einen Spieler.
   * 
   * @param uuid UUID des Spieler, dessen Geld-Betrag ermittelt werden soll.
   * 
   * @return Geld-Betrag des übergebenen Spielers.
   */
  public double getBalance(String uuid) {
    return this._balance.get(uuid);
  }

  /**
   * Ermittelt ob der Spieler schon in der Liste existiert oder nicht.
   * 
   * @param uuid UUID des Spieler, bei dem ermittelt werden soll, ob zu ihm
   * schon ein Geld-Betrag existiert oder nicht.
   * 
   * @return {@code true}, wenn zu dem Spieler schon ein Geld-Betrag existiert.
   * {@code false}, wenn noch kein Betrag zu dem Spieler existiert.
   */
  public boolean hasBalance(String uuid) {
    return this._balance.containsKey(uuid);
  }
}