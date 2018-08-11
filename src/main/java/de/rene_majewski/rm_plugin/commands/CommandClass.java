package de.rene_majewski.rm_plugin.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.rene_majewski.rm_plugin.RMPlugin;
import de.rene_majewski.rm_plugin.config.Config;

/**
 * Alle Befehls-Klassen werden von dieser Klasse abgeleitet. Es werden
 * Hilfs-Methoden bereitgestellt oder Methoden definiert, die von den
 * abgeleiteten Klassen implementiert werden müssen.
 * 
 * @author René Majewski
 * @since 0.2
 */
public abstract class CommandClass {
  /**
   * Speichert das Objekt zur Plugin-Main-Klasse.
   * 
   * @since 0.2
   */
  protected RMPlugin _plugin;

  /**
   * Initialisiert diese Klasse.
   * 
   * @param plugin Objekt zur Plugin-Main-Klasse
   */
  public CommandClass(RMPlugin plugin) {
    this._plugin = plugin;
  }


  /**
   * Formatiert den Befehl mit einer Farbe und fügt den Hilfetext an.
   * 
   * Die Farben werden aus der Konfiguration geladen. Um den Befehl
   * hevorzuheben, wird {@link Config#COLOR_HELP_COMMAND} verwendet. Für den
   * normalen Hilfetext wird {@link Config#COLOR_HELP_TEXT} verwendet.
   * 
   * @param command Befehle, zu dem der Hiltext ausgegeben werden soll.
   * 
   * @param text Hilfetext, der nach dem Befehl angezeigt werden soll.
   */
  public String createCommandHelpMessage(String command, String text) {
    StringBuffer sb = new StringBuffer();

    sb.append(this._plugin.getMyConfig().getString(Config.COLOR_HELP_COMMAND));
    sb.append("/rmplugin ");
    sb.append(command);
    sb.append(" ");
    sb.append(this._plugin.getMyConfig().getString(Config.COLOR_HELP_TEXT));
    sb.append(text);

    return sb.toString();
  }

  /**
   * Sendet den Hilfetext für den Befehl zum Sender.
   * 
   * @param sender Objekt, zu dem der Hilfetext gesendet werden soll.
   * 
   * @since 0.2
   */
  public abstract void sendHelpMessage(CommandSender sender);

  /**
   * Sendet die Nachricht {@link Config#MESSAGE_NO_PERMISSION}.
   * 
   * @param sender Objekt, zu dem die Nachricht gesendet werden soll.
   * 
   * @since 0.2
   */
  public void sendNoPermission(CommandSender sender) {
    if (sender instanceof Player) {
      ((Player)sender).sendMessage(this._plugin.getMyConfig().getString(Config.MESSAGE_NO_PERMISSION));
    } else {
      sender.sendMessage(this._plugin.getMyConfig().getString(Config.MESSAGE_NO_PERMISSION));
    }
  }

  /**
   * Sendet die übergebene Nachricht an den Sender.
   * 
   * Es wird die Nachricht in Teilnachrichten anhand von Zeilenumbrüchen
   * aufgeteilt. Die Teilnachrichten werden dann an den Sender gesendet.
   * 
   * @param buffer Nachricht, die gesendet werden soll.
   * 
   * @param sender Objekt, an das die Nachrichten gesendet werden sollen.
   * 
   * @since 0.2
   */
  public void sendMessage(StringBuffer buffer, CommandSender sender) {
    if ((buffer != null) || (sender != null)) {
      return;
    }

    this.sendMessage(buffer.toString(), sender);
    System.out.println("Test");

    if (buffer.indexOf("\n") != -1) {
      int index = 0;
      int end = 0;
      while ((end = buffer.indexOf("\n", index)) != -1) {
        String message = buffer.substring(index, end);
        this.sendMessage(message, sender);
        index = end + 1;
      }
    } else {
      this.sendMessage(buffer.toString(), sender);
    }
  }

  /**
   * Sender die Übergebene Nachricht an den Sender.
   * 
   * Es wird unterschiedenen ob die Nachricht einen Spieler oder an die
   * Konsole gesendet werden soll.
   * 
   * @param text Nachricht, die verschickt werden soll.
   * 
   * @param sender Objekt, an das die Nachricht geschickt werden soll.
   * 
   * @since 0.2
   */
  public void sendMessage(String text, CommandSender sender) {
    if (sender instanceof Player) {
      ((Player)sender).sendMessage(text);
    } else {
      sender.sendMessage(text);
    }
  }

  /**
   * Sendet eine Fehler-Message.
   * 
   * @param sender Sender, an dem die Fehler-Nachricht geschickt werden soll.
   * 
   * @param error Fehler, der aufgetreten ist.
   * 
   * @param message Zusätzliche Nachricht, die an den Sender geschickt werden
   * soll.
   * 
   * @since 0.2
   */
  public void sendErrorMessage(CommandSender sender, Exception error, String message) {
    error.printStackTrace();
    String color = this._plugin.getMyConfig().getString(Config.COLOR_ERROR_TEXT);
    this.sendMessage(color + this._plugin.getMyConfig().getString(Config.MESSAGE_ERROR) + ChatColor.RESET, sender);

    if (sender.hasPermission(Config.PERMISSION_ADMIN_DEBUG)) {
      this.sendMessage(this._plugin.getMyConfig().getString(Config.COLOR_ERROR_MESSAGE) + error.getMessage() + ChatColor.RESET, sender);
    }

    if (message != null && !message.isEmpty()) {
      this.sendMessage(color + message + ChatColor.RESET, sender);
    }
  }

  /**
   * Ermittelt aus dem übergebenen Text einen Double-Wert. Wurde kein
   * Double-Wert angegeben, so wird an den Sender eine Nachricht
   * geschickt.
   * 
   * @param text Text, der zu einen Double-Wert geparst werden soll.
   * 
   * @param sender Objekt, dass den Text geschickt hat.
   * 
   * @param count An welcher Stelle der Argumente der Double-Wert steht.
   * 
   * @return Der Double-Wert, der in text steht. Ist ein Fehler aufgetreten, so
   * wird {@code 0} zurück gegeben.
   */
  protected double getDoubleFromString(String text, CommandSender sender, int count) {
    double result = 0;

    try {
      result = Double.parseDouble(text);
    } catch (Exception e) {
      this.sendErrorMessage(sender, e, Config.MESSAGE_ERROR_NO_DOUBLE.replace("?", String.valueOf(count)));
    }

    return result;
  }

  /**
   * Erstellt aus dem übergebenen Array eine Zeichenkette.
   * 
   * @param args Array, aus dem eine Zeichenkette erstellt werden soll.
   * 
   * @param start Element, ab dem aus dem Array eine Zeichenkette erstellt
   * werden soll.
   * 
   * @return Erstellt Zeichenkette.
   * 
   * @since 0.2
   */
  protected String getStringFromArray(String[] args, int start) {
    StringBuffer sb = new StringBuffer();

    for (int i = start; i < args.length; i++) {
      if (i > start) {
        sb.append(" ");
      }
      sb.append(args[i]);
    }

    return sb.toString();
  }
}