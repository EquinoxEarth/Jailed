package io.github.equinoxearth.jailed.jail;

import io.github.equinoxearth.jailed.Jailed;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JailLoader {

    public static Jailed plugin = Jailed.plugin;

    public static Jail loadJail(File file) {
        FileConfiguration jailFile = new YamlConfiguration();
        Jail jail = new Jail();

        // Check if the file exists //
        if(!file.exists()) {
            try {
                plugin.debug("Creating Jail file...");
                file.createNewFile();
                plugin.debug("Jail file created, you need to create the jail!");
            } catch (IOException e) {
                plugin.debug("Could not create Jail file!");
                e.printStackTrace();
            }
        } else {
            // ALL LOADING DONE HERE //
            try {
                jailFile.load(file);

                // LOOP THROUGH ALL ENTRIES //
                jailFile.get("jail");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InvalidConfigurationException e) {
                e.printStackTrace();
            }
        }

        return jail;
    }

    public static void saveJail(File file, Jail jail) {
        FileConfiguration jailsFile = new YamlConfiguration();

        // Check if the file exists //
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                plugin.debug("Could not save Jail file!");
                e.printStackTrace();
            }
        }
        // ALL SAVING DONE HERE //
        jailsFile.set("jail", jail);

        // Save the file //
        try {
            jailsFile.save(file);
        } catch (IOException e) {
            plugin.debug("Could not save the Jail file!");
            e.printStackTrace();
        }
    }
}
