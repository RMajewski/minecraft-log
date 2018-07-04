package de.rene_majewski.minecraft_log;

import org.bukkit.plugin.java.JavaPlugin;

import de.rene_majewski.minecraft_log.config.Config;
import de.rene_majewski.minecraft_log.listener.BlockListener;
import de.rene_majewski.minecraft_log.listener.PlayerListener;

public class MinecraftLog extends JavaPlugin 
{
    private Config _config;

    @Override
    public void onEnable() {
        super.onEnable();

        _config = new Config(this);

        new BlockListener(this);
        new PlayerListener(this);
    }

    @Override
    public void onDisable() {
        _config.save();
    }
}
