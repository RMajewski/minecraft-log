package de.rene_majewski.minecraft_log;

import org.bukkit.plugin.java.JavaPlugin;

import de.rene_majewski.minecraft_log.commands.ConfigReloadCommand;
import de.rene_majewski.minecraft_log.config.Config;

public final class MinecraftLog extends JavaPlugin 
{
    private Config _config;

    @Override
    public void onEnable() {
        super.onEnable();

        _config = new Config(this);

        // new BlockListener(this);
        // new PlayerListener(this);

        this.getCommand("minecraftlog").setExecutor(new ConfigReloadCommand(_config));
    }

    @Override
    public void onDisable() {
        _config.save();
    }
}
