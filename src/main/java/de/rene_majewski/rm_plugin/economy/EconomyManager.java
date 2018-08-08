package de.rene_majewski.rm_plugin.economy;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import de.rene_majewski.rm_plugin.RMPlugin;
import de.rene_majewski.rm_plugin.Unity;
import de.rene_majewski.rm_plugin.config.Config;
import de.rene_majewski.rm_plugin.economy.datas.EconomyStatement;
import de.rene_majewski.rm_plugin.economy.listener.EconomyPlayerListener;

/**
 * Basis-Klasse für das Wirtschaftssystem.
 * 
 * @since 0.2
 * @author René Majewski
 */
public class EconomyManager extends Unity {
  /**
   * Initialisiert diese Klasse.
   * 
   * @param plugin Instanz der Main-Plugin-Klasse.
   */
  public EconomyManager(RMPlugin plugin) {
    super(plugin);
  }

  /**
   * Initialisiert die einzelnen Listener.
   */
  @Override
  protected void registerListeners() {
    new EconomyPlayerListener(this._plugin);
  }

  /**
   * Setzt den Geld-Betrag, den ein Spieler hat neu.
   * 
   * @param uuid UUID des Spielers, dessen Geld-Betrag neu gesetzt werden soll.
   * 
   * @param amount Neuer Betrag, den der Spieler zur Verfügung hat.
   * 
   * @return Gibt {@code true} zurück, wenn der Betrag des Spielers gesetzt
   * werden konnte. Konnte der Betrag nicht gesetzt werden bzw. trat ein Fehler
   * auf, so wird {@code false} zurück gegeben.
   */
  public boolean setBalance(String uuid, double amount, int status) {
    boolean result = true;
    PreparedStatement ps = null;
    int player_id = this._plugin.getMySql().getPlayerId(this._plugin.getPlayerFromUuid(uuid));
    try {
      ps = this._plugin.getMySql().getConnection().prepareStatement("INSERT INTO " + this._plugin.getMySql().getTableName(Config.DB_TABLE_BALANCE) + "(player_id, balance) VALUES(?,?)");
      ps.setInt(1, player_id);
      ps.setDouble(2, amount);
      ps.executeUpdate();
      this._plugin.getMySql().closeRessources(null, ps);

      switch (status) {
        case EconomyStatement.REGISTRATION:
          ps = this._plugin.getMySql().getConnection().prepareStatement("INSERT INTO " + this._plugin.getMySql().getTableName(Config.DB_TABLE_BALANCE_STATEMENT) + "(player_id, amount, status) VALUES(?, ?, ?)");
          ps.setInt(1, player_id);
          ps.setDouble(2, amount);
          ps.setInt(3, status);
          ps.executeUpdate();
          break;
      }
    } catch(SQLException e) {
      this._plugin.sendErrorMessage(this._plugin.getPlayerFromUuid(uuid), this._plugin.getMyConfig().getString(Config.MESSAGE_ERROR), e);
      result = false;
    } finally {
      this._plugin.getMySql().closeRessources(null, ps);
    }

    return result;
  }

  /**
   * Ermittelt den Geld-Betrag für einen Spieler.
   * 
   * @param uuid UUID des Spieler, dessen Geld-Betrag ermittelt werden soll.
   * 
   * @return Geld-Betrag des übergebenen Spielers.
   */
  public double getBalance(String uuid) {
    double result = 0.0;
    PreparedStatement ps = null;
    ResultSet rs = null;
    try {
      ps = this._plugin.getMySql().getConnection().prepareStatement("SELECT balance FROM " + this._plugin.getMySql().getTableName(Config.DB_TABLE_BALANCE)  +" WHERE player_id = ?");
      ps.setInt(1, this._plugin.getMySql().getPlayerId(this._plugin.getPlayerFromUuid(uuid)));
      rs = ps.executeQuery();
      if (rs.next()) {
        result = rs.getDouble("balance");
      }
    } catch (SQLException e) {
      this._plugin.sendErrorMessage(this._plugin.getPlayerFromUuid(uuid), this._plugin.getMyConfig().getString(Config.MESSAGE_ERROR), e);
    } finally {
      this._plugin.getMySql().closeRessources(rs, ps);
    }
    return result;
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
    boolean result = false;

    PreparedStatement ps = null;
    ResultSet rs = null;

    try {
      ps = this._plugin.getMySql().getConnection().prepareStatement("SELECT player_id FROM " + this._plugin.getMySql().getTableName(Config.DB_TABLE_BALANCE) + " WHERE player_id = ?");
      ps.setInt(1, this._plugin.getMySql().getPlayerId(this._plugin.getPlayerFromUuid(uuid)));
      rs = ps.executeQuery();
      result = rs.next();
    } catch (SQLException e) {
      this._plugin.sendErrorMessage(this._plugin.getPlayerFromUuid(uuid), this._plugin.getMyConfig().getString(Config.MESSAGE_ERROR), e);
    } finally {
      this._plugin.getMySql().closeRessources(rs, ps);
    }

    return result;
  }

  /**
   * Überprüft, ob der Spieler schon in der Datenbank existiert. Ist dies nicht
   * der Fall, wird der Spieler angelegt und ihm den Standard-Betrag überwiesen.
   * 
   * @param uuid UUID des Spieler, der überprüft werden soll.
   */
  public void playerJoin(String uuid) {
    if (!hasBalance(uuid)) {
      double balance = this._plugin.getMyConfig().getDouble(Config.ECONOMY_STANDARD_BALANCE);

      if (this.setBalance(uuid, balance, EconomyStatement.REGISTRATION)) {
        String tmp = this._plugin.getMyConfig().getString(Config.MESSAGE_ECONOMY_START);
        tmp = tmp.replaceFirst("\\?", String.valueOf(balance));
        tmp = tmp.replace("?", this._plugin.getMyConfig().getString(Config.ECONOMY_CURRENCY));
        this._plugin.sendMessage(uuid, tmp);
      } else {
        this._plugin.sendErrorMessage(uuid, this._plugin.getMyConfig().getString(Config.MESSAGE_ERROR_ECONOMY_NO_STANDARD_BALANCE), null);
      }
    }
  }
}