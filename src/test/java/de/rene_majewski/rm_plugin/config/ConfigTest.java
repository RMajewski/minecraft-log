package de.rene_majewski.rm_plugin.config;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.FileConfigurationOptions;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * Testet die Klasse Config.
 * 
 * @author René Majewski
 * @since 0.1
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest({JavaPlugin.class, FileConfiguration.class, FileConfigurationOptions.class})
public class ConfigTest {
  @Mock
  JavaPlugin plugin;

  @Mock
  FileConfigurationOptions options;

  @Mock
  FileConfiguration fileConfig;

  Config config;

  public void standardSetUp() {
    when(plugin.getConfig()).thenReturn(fileConfig);
    
    when(fileConfig.options()).thenReturn(options);

    config = new Config(plugin);
  }

  @Test
  public void testRightDatabasePath() {
    assertEquals(Config.DB_CONFIG_HOST, "rmplugin.db.host");
    assertEquals(Config.DB_CONFIG_PORT, "rmplugin.db.port");
    assertEquals(Config.DB_CONFIG_USER, "rmplugin.db.user");
    assertEquals(Config.DB_CONFIG_PASSWORD, "rmplugin.db.password");
    assertEquals(Config.DB_CONFIG_DATABASE, "rmplugin.db.database");
    assertEquals(Config.DB_CONFIG_PREFIX, "rmplugin.db.prefix");
  }

  @Test
  public void testRightDatabaseTablePaths() {
    assertEquals(Config.DB_TABLE_PLAYER, "rmplugin.db.tables.player");
    assertEquals(Config.DB_TABLE_LOG_COMMAND, "rmplugin.db.tables.log_command");
  }

  @Test
  public void testRightPermissionPaths() {
    assertEquals(Config.PERMISSION_ADMIN_RELOAD, "rmplugin.admin.reload");
  }

  @Test
  public void testRightMessagePaths() {
    assertEquals(Config.MESSAGE_NO_PERMISSION, "rmplugin.messages.no_permission");
    assertEquals(Config.MESSAGE_CONFIG_RELOAD, "rmplugin.messages.config_reload");
  }

  @Test
  public void testRightDefaults() {
    standardSetUp();

    verify(fileConfig, times(1)).addDefault(Config.MESSAGE_NO_PERMISSION, "Du hast keine Berechtigung für diesen Befehl.");
    verify(fileConfig, times(1)).addDefault(Config.MESSAGE_CONFIG_RELOAD, "Du Konfiguration wurde erfolgreich neu geladen");

    verify(fileConfig, times(1)).addDefault(Config.DB_CONFIG_HOST, "localhost");
    verify(fileConfig, times(1)).addDefault(Config.DB_CONFIG_PORT, 3306);
    verify(fileConfig, times(1)).addDefault(Config.DB_CONFIG_USER, "user");
    verify(fileConfig, times(1)).addDefault(Config.DB_CONFIG_PASSWORD, "");
    verify(fileConfig, times(1)).addDefault(Config.DB_CONFIG_DATABASE, "minecraft");
    verify(fileConfig, times(1)).addDefault(Config.DB_CONFIG_PREFIX, "rm_plugin_");

    verify(fileConfig, times(1)).addDefault(Config.DB_TABLE_PLAYER, "player");
    verify(fileConfig, times(1)).addDefault(Config.DB_TABLE_LOG_COMMAND, "log_command");
  }

  @Test
  public void testSaveConfigOnInit() {
    standardSetUp();

    verify(plugin, times(1)).saveConfig();
  }

  @Test
  public void testSetOptionsOnInit() {
    standardSetUp();

    verify(fileConfig, times(2)).options();
    verify(options, times(1)).header(anyString());
    verify(options, times(1)).copyDefaults(true);
  }
}