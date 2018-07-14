package de.rene_majewski.minecraft_log;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.plugin.java.JavaPlugin;

import de.rene_majewski.minecraft_log.commands.ConfigReloadCommand;
import de.rene_majewski.minecraft_log.config.Config;
import de.rene_majewski.minecraft_log.data.MySql;
import de.rene_majewski.minecraft_log.listener.BlockListener;
import de.rene_majewski.minecraft_log.listener.CommandListener;
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
        new CommandListener(this);
    }

    private void registerCommands() {
        this.getCommand("minecraftLog").setExecutor(new ConfigReloadCommand(this));
    }

    @Override
    public void onDisable() {
        if (this._mysql.hasConnection()) {
            PreparedStatement ps = null;
            ResultSet rs = null;

            try {
                ps = this._mysql.getConnection().prepareStatement("SELECT b.id AS player_id FROM(SELECT a.player_id AS id, count(a.player_id) AS cnt FROM minecraft.minecraft_log_log_loggin AS a WHERE a.time > current_date() GROUP BY a.player_id) AS b WHERE MOD(b.cnt,2) <> 0");
                rs = ps.executeQuery();
                while (rs.next()) {
                    PlayerListener.logout(rs.getInt("player_id"), this._mysql);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                this._mysql.closeRessources(rs, ps);
            }

            this._mysql.closeConnection();
        }
    }

    public MySql getMySql() {
        return this._mysql;
    }

    public Config getMyConfig() {
        return this._config;
    }
}
