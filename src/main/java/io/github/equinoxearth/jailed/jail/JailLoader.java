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

    public static Map<String, Jail> loadJails(File file) {
        FileConfiguration jailsFile = new YamlConfiguration();
        Map<String, Jail> jailList = new HashMap<String, Jail>();

        // Check if the file exists //
        if(!file.exists()) {
            try {
                plugin.debug("Creating Jails file...");
                file.createNewFile();
                plugin.debug("Jails file created, you need to create the jail!");
            } catch (IOException e) {
                plugin.debug("Could not create Jails file!");
                e.printStackTrace();
            }
        } else {
            // ALL LOADING DONE HERE //
            try {
                jailsFile.load(file);

                // LOOP THROUGH ALL ENTRIES //
                jailsFile.getKeys(false).forEach(name -> {
                   jailList.put(name, (Jail) jailsFile.get(name));
                });
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InvalidConfigurationException e) {
                e.printStackTrace();
            }
        }

        return jailList;
    }

    public static void saveJails(File file, Map<String, Jail> jails) {
        FileConfiguration jailsFile = new YamlConfiguration();

        // Check if the file exists //
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                plugin.debug("Could not save Jails file!");
                e.printStackTrace();
            }
        }
        // ALL SAVING DONE HERE //
        jails.keySet().forEach(name -> {
            jailsFile.set(name, jails.get(name));
        });

        // Save the file //
        try {
            jailsFile.save(file);
        } catch (IOException e) {
            plugin.debug("Could not save the Jails file!");
            e.printStackTrace();
        }
    }
}
