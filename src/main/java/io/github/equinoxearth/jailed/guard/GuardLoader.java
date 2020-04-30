package io.github.equinoxearth.jailed.guard;

import co.aikar.commands.annotation.Dependency;
import io.github.equinoxearth.jailed.Jailed;
import io.github.equinoxearth.jailed.guard.Guard;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GuardLoader {

    public static Jailed plugin = Jailed.plugin;

    public static Map<UUID, Guard> loadGuards(File file) throws Exception {
        FileConfiguration guardsFile = new YamlConfiguration();
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                plugin.debug("Something went wrong loading the guards file");
            }

            return new HashMap<UUID, Guard>();
        } else {
            // ALL LOADING DONE HERE //
            Map<UUID, Guard> guardList = new HashMap<UUID, Guard>();
            guardsFile.load(file);

            guardsFile.getKeys(false).forEach(uuid -> {
                guardList.put(UUID.fromString(uuid), (Guard) guardsFile.get(uuid));
            });

            return guardList;
        }
    }

    public static void saveGuards(File file, Map<UUID, Guard> guards) throws Exception {
        FileConfiguration guardsFile = new YamlConfiguration();
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                plugin.debug("Something went wrong saving the guards file");
            }
        } else {
            // ALL SAVING DONE HERE //
            guards.keySet().forEach(uuid ->
                    guardsFile.set(uuid.toString(), guards.get(uuid)));

            // Save the file //
            guardsFile.save(file);
        }
    }
}
