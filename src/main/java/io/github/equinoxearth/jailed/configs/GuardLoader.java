package io.github.equinoxearth.jailed.configs;

import io.github.equinoxearth.jailed.Jailed;
import io.github.equinoxearth.jailed.objects.Guard;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GuardLoader {

    public static Jailed plugin = Jailed.plugin;

    public static Map<UUID, Guard> loadGuards(File file) {
        FileConfiguration guardsFile = new YamlConfiguration();
        Map<UUID, Guard> guardList = new HashMap<UUID, Guard>();

        // Check if the file exists //
        if(!file.exists()) {
            plugin.debug("Creating Guards file...");
            try {
                file.createNewFile();
                plugin.debug("Guards file created!");
            } catch (IOException e) {
                plugin.debug("Could not create Guards file!");
                e.printStackTrace();
            }
        } else {
            // ALL LOADING DONE HERE //
            try {
                guardsFile.load(file);

                // LOOP THROUGH ALL ENTRIES //
                guardsFile.getKeys(false).forEach(uuid -> {
                    guardList.put(UUID.fromString(uuid), (Guard) guardsFile.get(uuid));
                });
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InvalidConfigurationException e) {
                e.printStackTrace();
            }
        }

        return guardList;
    }

    public static void saveGuards(File file, Map<UUID, Guard> guards) {
        FileConfiguration guardsFile = new YamlConfiguration();

        // Check if the file exists //
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                plugin.debug("Could not create the Guards file!");
                e.printStackTrace();
            }
        }
        // ALL SAVING DONE HERE //
        guards.keySet().forEach(uuid -> {
                guardsFile.set(uuid.toString(), guards.get(uuid));
        });

        // Save the file //
        try {
            guardsFile.save(file);
        } catch (IOException e) {
            plugin.debug("Could not save the Guards file!");
            e.printStackTrace();
        }
    }
}
