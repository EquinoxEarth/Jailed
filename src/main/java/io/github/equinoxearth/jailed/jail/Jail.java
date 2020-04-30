package io.github.equinoxearth.jailed.jail;

import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.HashMap;
import java.util.Map;

/*
 * Represents an instance of a jail.
 */
public class Jail implements ConfigurationSerializable {

    // ATTRIBUTE SECTION //
    private Location firstPos;
    private Location secondPos;
    private Location spawnPoint;
    private Location exitPoint;

    /**
     * Create an unconfigured jail
     */
    public Jail () {

    }

    /**
     * Get the first corner coordinate
     * @return
     */
    public Location getFirstPos() {
        return this.firstPos;
    }

    /**
     * Get the second corner coordinate
     * @return
     */
    public Location getSecondPos() {
        return this.secondPos;
    }

    /**
     * Get the spawn point coordinate
     * @return
     */
    public Location getSpawnPoint() {
        return this.spawnPoint;
    }

    /**
     * Get the exit point coordinate
     * @return
     */
    public Location getExitPoint() {
        return this.exitPoint;
    }

    /*
     * SERIALIZE & DESERIALIZE SECTION
     */

    public Jail(Map<String, Object> map) {
        this.firstPos = (Location) map.get("firstPos");
        this.secondPos = (Location) map.get("secondPos");
        this.spawnPoint = (Location) map.get("spawnPoint");
        this.exitPoint = (Location) map.get("exitPoint");
    }

    public static Jail deserialize(Map<String, Object> map) { return new Jail(map); }

    public static Jail valueOf(Map<String, Object> map) { return new Jail(map); }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();

        // ADD ALL PROPERTIES TO THE MAP //
        map.put("firstPos", this.firstPos);
        map.put("secondPos", this.secondPos);
        map.put("spawnPoint", this.spawnPoint);
        map.put("exitPoint", this.exitPoint);

        return map;
    }
}
