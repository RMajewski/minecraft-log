package de.rene_majewski.minecraft_log;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class MinecraftLog extends JavaPlugin 
{
    @Override
    public void onEnable() {
        super.onEnable();

        System.out.println("TEST: Plugin MinecraftLog Version 0.1 loaded");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        System.out.println("[MinecraftLog]: Befehl: " + command.getName());

        if (sender instanceof Player) {
            System.out.println("[MinecraftLog]: Befehl gesendet von: " + sender.getName());
        } else {
            System.out.println("[MinecraftLog]: Befehl gesendet von: Konsole");
        }

        return false;
    }
}
