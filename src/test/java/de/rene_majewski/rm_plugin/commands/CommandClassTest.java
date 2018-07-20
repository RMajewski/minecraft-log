package de.rene_majewski.rm_plugin.commands;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.anyString;
import static org.junit.Assert.*;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import de.rene_majewski.rm_plugin.RMPlugin;
import de.rene_majewski.rm_plugin.config.Config;

/**
 * Testet die Klasse CommandClass.
 * 
 * @author René Majewski
 * @since 0.2
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Command.class, CommandSender.class, RMPlugin.class, Config.class})
public class CommandClassTest {

  /**
   * Test-Klasse, da die zu testende Klasse abstrakte Methoden hat.
   * 
   * @author René Majewski
   * @since 0.2
   */
  class CommandClazz extends CommandClass {
    /**
     * Initialisiert die Test-Klasse.
     * 
     * @param plugin Objekt der Plugin-Main-Klasse.
     * 
     * @since 0.2
     */
    public CommandClazz(RMPlugin plugin) {
      super(plugin);
    }

    /**
     * Wird nicht implementiert.
     * 
     * @since 0.2
     */
    @Override
    public void sendHelpMessage(CommandSender sender) {
    }

    /**
     * Gibt das Objekt der Plugin-Main-Klasse.
     * 
     * @return Die gespeicherte Plugin-Main-Klasse.
     * 
     * @since 0.2
     */
    public RMPlugin getPlugin() {
      return this._plugin;
    }
  }

  /**
   * Speichert das Mock-Objekt für die {@link CommandSender}-Klasse.
   * 
   * @since 0.2
   */
  @Mock
  private CommandSender sender;
  
  /**
   * Speichert das Mock-Objekt für die {@link Command}-Klasse.
   */
  @Mock
  private Command command;

  /**
   * Speichert das Mock-Objekt für die {@link Config}-Klasse.
   */
  @Mock
  private Config config;

  /**
   * Speichert das Mock-Objekt für die {@link RMPlugin}-Klasse.
   */
  @Mock
  private RMPlugin plugin;

  /**
   * Speichert das Test-Objekt für die {@link CommandClass}-Klasse.
   */
  private CommandClazz clazz;

  /**
   * Initialisiert die einzelnen Tests.
   */
  @Before
  public void setUp() {
    when(plugin.getMyConfig()).thenReturn(config);

    clazz = new CommandClazz(plugin);
  }

  /**
   * Testet, ob das Plugin-Objekt richtig gespeichert wird.
   */
  @Test
  public void testGetPlugin() {
    assertEquals(this.plugin, this.clazz.getPlugin());
  }

  /**
   * Testet, ob die Hilfe-Nachricht richtig formatiert wird.
   */
  @Ignore
  @Test
  public void testCreateCommandHelpMessage() {
    String command = "command";
    String message = "This is a test command.";
    String color1 = "§1";
    String color2 = "§2";

    when(config.getString(Config.COLOR_HELP_COMMAND)).thenReturn(color1);
    when(config.getString(Config.COLOR_HELP_TEXT)).thenReturn(color2);

    StringBuffer sb = new StringBuffer();
    sb.append(color1);
    sb.append("/rmplugin ");
    sb.append(command);
    sb.append(" ");
    sb.append(color2);
    sb.append(message);

    assertEquals(sb.toString(), clazz.createCommandHelpMessage(command, message));
    verify(config, times(1)).getString(Config.COLOR_HELP_COMMAND);
    verify(config, times(1)).getString(Config.COLOR_HELP_TEXT);
  }

  /**
   * Testet, ob der Text für "Keine Berechtigung" richtig gesendet wird.
   * 
   * @since 0.2
   */
  @Test
  public void testSendNoPermission() {
    String message = "test";
    when(config.getString(Config.MESSAGE_NO_PERMISSION)).thenReturn(message);

    this.clazz.sendNoPermission(sender);

    verify(config, times(1)).getString(Config.MESSAGE_NO_PERMISSION);
    verify(sender, times(1)).sendMessage(message);
  }

  /**
   * Testet, ob die Funktion
   * {@link CommandClass#sendMessage(StringBuffer, CommandSender)} keine
   * Nachricht sendet, wenn kein {@code null} als Puffer übergeben wird.
   * 
   * @since 0.2
   */
  @Ignore
  @Test
  public void testSendMessageWithNoBuffer() {
    // this.clazz.sendMessage(null, sender);

    // verify(sender, times(0)).sendMessage(anyString());
  }

  /**
   * Testet, ob die Funktion
   * {@link CommandClass#sendMessage(StringBuffer, CommandSender)} keine
   * Nachricht sendet und kein Fehler auftritt, wenn {@code null} als
   * Sender übergeben wird.
   * 
   * @since 0.2
   */
  @Test(expected = Test.None.class)
  public void testSendMessageWithNoCommandSender() {
    StringBuffer buffer = new StringBuffer();
    this.clazz.sendMessage(buffer, null);
  }
}
