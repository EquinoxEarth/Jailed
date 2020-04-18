package io.github.equinoxearth.jailed.config;

import io.github.equinoxearth.jailed.Jailed;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigLoader {

    protected static final Jailed plugin = Jailed.plugin;
    protected String fileName;
    private File configFile;
    protected FileConfiguration config;

    public ConfigLoader(String relativePath, String fileName) {
        this.fileName = fileName;
        configFile = new File(plugin.getDataFolder(), relativePath + File.separator + fileName);
        loadFile();
    }

    public ConfigLoader(String fileName) {
        this.fileName = fileName;
        configFile = new File(plugin.getDataFolder(), fileName);
        loadFile();
    }

    public void loadFile() {

        if(!configFile.exists()) {

            // Create the File //
            plugin.debug("Creating Jailed " + fileName + " File...");
            plugin.saveResource(fileName, false);

        } else {

            plugin.debug("Loading Jailed " + fileName + " File...");

        }

        // Load the File //
        config = YamlConfiguration.loadConfiguration(configFile);

    }

    public void saveFile() {

        try {

            config.save(configFile);

        } catch (IOException e) {

            plugin.debug("Could not save Jailed " + fileName);
            plugin.debug(e.toString());

        }

    }

}
