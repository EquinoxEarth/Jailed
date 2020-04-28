package io.github.equinoxearth.jailed.jail;

import org.bukkit.Location;

/*
 * Represents an instance of a jail.
 */
public class Jail {

    // ATTRIBUTE SECTION //
    private String jailName;
    private Location firstPos;
    private Location secondPos;
    private Location spawnPoint;
    private Location exitPoint;

    /**
     * Create a jail with only a name
     * @param n Name of the jail
     */
    public Jail (String n) {
        this.jailName = n;
    }

    /**
     * Get the name of this jail instance
     * @return
     */
    public String getJailName() {
        return this.jailName;
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
}
