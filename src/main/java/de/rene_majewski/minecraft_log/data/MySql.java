package de.rene_majewski.minecraft_log.data;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class MySql {
  private String _host;
  private String _port;
  private String _user;
  private String _password;
  private String _database;

  public MySql() {
    File file = new File("plugins/MinecraftLog/", "database.yml");

    FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
  }
}