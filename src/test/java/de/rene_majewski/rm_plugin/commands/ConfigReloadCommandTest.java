package de.rene_majewski.rm_plugin.commands;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.ChatColor;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import de.rene_majewski.rm_plugin.MinecraftLog;
import de.rene_majewski.rm_plugin.config.Config;

/**
 * Testet die Klasse ConfigReloadCommand.
 * 
 * @author René Majewski
 * @since 0.1
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Command.class, CommandSender.class, MinecraftLog.class, Config.class})
public class ConfigReloadCommandTest {
  @Mock
  private CommandSender sender;
  
  @Mock
  private Command command;

  @Mock
  private Config config;

  @Mock
  private MinecraftLog plugin;

  private ConfigReloadCommand cfc;

  @Before
  public void setUp() {
    cfc = new ConfigReloadCommand(plugin);
  }

  /**
   * Testet, ob <code>false</code> zurückgegeben wird, wenn keine Argumente übergeben werden.
   */
  @Test
  public void testOnCommandWithoutArgs() {
    assertFalse(cfc.onCommand(sender, command, "test", new String[0]));
  }

  /**
   * Testet, ob <code>false</code> zurückgegeben wird, wenn ein falscher Befehl angegeben wird.
   */
  @Test
  public void testOnCommandWithFalseCommand() {
    when(command.getName()).thenReturn("test");
    
    assertFalse(cfc.onCommand(sender, command, "test", new String[0]));
  }

  /**
   * Testet, ob <code>false</code> zurückgegeben wird, wenn nur 1 Argument übergeben wird.
   */
  @Test
  public void testOnCommandWithOneArgument() {
    when(command.getName()).thenReturn("minecraftlog");
    
    String[] args = { "config" };

    assertFalse(cfc.onCommand(sender, command, "minecraftlog", args));
  }

  /**
   * Testet, ob <code>false</code> zurückgegeben wird, wenn als 1. Argument nicht <i>config</i> übergeben wird.
   */
  @Test
  public void testOnCommandWithNoConfigAsFirstArgument() {
    when(command.getName()).thenReturn("minecraftlog");

    String[] args = { "test", "reload" };

    assertFalse(cfc.onCommand(sender, command, "minecraftlog", args));
  }

  /**
   * Testet, ob <code>true</code> zurückgegeben wird, wenn als 1. Argument
   * <i>config</config> und als zweites Argument <i>reload</i> zurückgegeben
   * wird. Außerdem wird getestet, ob die richtige Nachricht an den
   * <code>sender</code> gesendet wird, wenn die Permission
   * <i>minecraftlog.admin.reload</i> nicht gesetzt ist.
   */
  @Test
  public void testOnCommandWithRightArgumentsAndNoPermission() {
    when(command.getName()).thenReturn("minecraftlog");
    when(sender.hasPermission(Config.PERMISSION_ADMIN_RELOAD)).thenReturn(false);

    String[] args = { "config", "reload" };
    String message = "test";

    when(config.getString(Config.MESSAGE_NO_PERMISSION)).thenReturn(message);
    when(plugin.getMyConfig()).thenReturn(config);

    assertTrue(cfc.onCommand(sender, command, "minecraftlog", args));
    verify(sender, times(1)).hasPermission(Config.PERMISSION_ADMIN_RELOAD);
    verify(sender, times(1)).sendMessage(ChatColor.RED + message);
    verify(plugin, times(1)).getMyConfig();
  }

  /**
   * Testet, ob <code>true</code> zurückgegeben wird, wenn als 1. Argument
   * <i>config</config> und als zweites Argument <i>reload</i> zurückgegeben
   * wird. Außerdem wird getestet, ob die richtige Nachricht an den
   * <code>sender</code> gesendet wird, wenn die Permission
   * <i>minecraftlog.admin.reload</i> nicht gesetzt ist.
   */
  @Test
  public void testOnCommandWithRightArgumentsAndRightPermission() {
    when(command.getName()).thenReturn("minecraftlog");
    when(sender.hasPermission(Config.PERMISSION_ADMIN_RELOAD)).thenReturn(true);

    String[] args = { "config", "reload" };
    String message = "test";

    when(config.getString(Config.MESSAGE_CONFIG_RELOAD)).thenReturn(message);
    when(plugin.getMyConfig()).thenReturn(config);

    assertTrue(cfc.onCommand(sender, command, "minecraftlog", args));
    verify(sender, times(1)).hasPermission(Config.PERMISSION_ADMIN_RELOAD);
    verify(sender, times(1)).sendMessage(message);
    verify(config, times(1)).reload();
    verify(plugin, times(2)).getMyConfig();
  }
}