package net.craftions.admin;

import net.craftions.admin.commands.AdminCommand;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.FileNotFoundException;

public final class AdminTools extends JavaPlugin {

    public static AdminTools plugin = null;
    public static String prefix = "§4§lADMIN ➦ §r";

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin=this;
        getCommand("admintools").setExecutor(new AdminCommand());
        Bukkit.getPluginManager().registerEvents(new PlayerEvent(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
