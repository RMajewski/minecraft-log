package de.rene_majewski.minecraft_log;

import org.bukkit.plugin.java.JavaPlugin;

import de.rene_majewski.minecraft_log.commands.ConfigReloadCommand;
import de.rene_majewski.minecraft_log.config.Config;
import de.rene_majewski.minecraft_log.data.MySql;
import de.rene_majewski.minecraft_log.listener.BlockListener;
import de.rene_majewski.minecraft_log.listener.PlayerListener;

public final class MinecraftLog extends JavaPlugin 
{
    private Config _config;

    private MySql _mysql;

    @Override
    public void onEnable() {
        super.onEnable();

        _config = new Config(this);

        this._mysql = new MySql(_config);

        this.registerCommands();
        this.registerEvents();
    }

    private void registerEvents() {
        new BlockListener(this);
        new PlayerListener(this);
    }

    private void registerCommands() {
        this.getCommand("minecraftLog").setExecutor(new ConfigReloadCommand(this));
    }

    @Override
    public void onDisable() {
    }

    public MySql getMySql() {
        return this._mysql;
    }

    public Config getMyConfig() {
        return this._config;
    }
}
