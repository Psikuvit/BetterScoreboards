package me.psikuvit.betterscoreboards;

import me.psikuvit.betterscoreboards.commands.CommandRegisterer;
import org.bukkit.plugin.java.JavaPlugin;

public final class BetterScoreboards extends JavaPlugin {

    static BetterScoreboards plugin;

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        saveDefaultConfig();
        reloadConfig();

        getCommand("bs").setExecutor(new CommandRegisterer(this));


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static BetterScoreboards getPlugin() {
        return plugin;
    }
}
