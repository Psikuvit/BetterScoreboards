package me.psikuvit.betterscoreboards;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;

public class ScoreboardsFiles {

    private final String name = "scoreboards.yml";
    private final BetterScoreboards plugin;
    private FileConfiguration dataConfig = null;
    private File dataConfigFile = null;

    public ScoreboardsFiles(BetterScoreboards plugin) {
        this.plugin = plugin;
        saveDefaultConfig();
    }

    public void reloadConfig() {
        if (this.dataConfigFile == null)
            this.dataConfigFile = new File(this.plugin.getDataFolder(), name);

        this.dataConfig = YamlConfiguration.loadConfiguration(this.dataConfigFile);

        InputStream defConfigStream = this.plugin.getResource(name);
        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration
                    .loadConfiguration(new InputStreamReader(defConfigStream));
            this.dataConfig.setDefaults(defConfig);
        }
    }

    public FileConfiguration getConfig() {
        if (this.dataConfig == null)
            reloadConfig();
        return this.dataConfig;
    }

    public void saveConfig() {
        if ((this.dataConfig == null) || (this.dataConfigFile == null))
            return;
        try {
            getConfig().save(this.dataConfigFile);
        } catch (IOException ex) {
            this.plugin.getLogger().log(Level.SEVERE, ex, () -> "Could not save config to " + this.dataConfigFile);        }
    }

    public void saveDefaultConfig() {
        if (this.dataConfigFile == null)
            this.dataConfigFile = new File(this.plugin.getDataFolder(), name);
        if (!this.dataConfigFile.exists())
            this.plugin.saveResource(name, false);
    }
}
