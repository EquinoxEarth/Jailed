package io.github.equinoxearth.jailed.guard;

import io.github.equinoxearth.jailed.Jailed;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GuardManager {

    // Variable Declarations //
    private static Jailed plugin;                            // Instance of the plugin //
    private Map<UUID, Guard> guards;
    private Location stationLocation;                               // Station for guards //

    // Create a new instance of GuardManager //
    public GuardManager() {
        this.plugin = Jailed.plugin;                        // Map the plugin instance into the GuardManager //
        this.guards = new HashMap<UUID, Guard>();           // Create the list of guards //
        loadGuards();
    }

    public void loadGuards() {
        // Load all the guards here //
    }

    /**
     * Gets the list of Guards in memory
     * @return
     */
    public Map<UUID, Guard> getGuards() {
        return this.guards;
    }

    public Location getStationLocation() {
        return this.stationLocation;
    }

    public void setStationLocation(Location l) {
        this.stationLocation = l;
    }

    public void startShift(Player p) {
        // Get the Guard instance //
        Guard g = guards.get(p.getUniqueId());

        // Is the Station Location set? //
        if (stationLocation == null) {
            p.sendMessage("There is no Guard Station defined!");
            return;
        }

        // Is the Player already on duty? //
        if (g.isOnDuty()) {
            p.sendMessage("You are already on duty!");
        } else {
            // Save the players inventory //
            g.setPlayerArmor(p.getInventory().getArmorContents());
            g.setPlayerInventory(p.getInventory().getStorageContents());

            // Replace it with Guard items //
            p.getInventory().setArmorContents(g.getGuardArmor());
            p.getInventory().setStorageContents(g.getGuardInventory());
        }
    }

    public void endShift(Player p) {
        Guard g = guards.get(p.getUniqueId());

        // Is the player already off duty? //
        if (!g.isOnDuty()) {
            p.sendMessage("You are already off duty!");
        } else {
            // Save the guards inventory //
            g.setGuardArmor(p.getInventory().getArmorContents());
            g.setGuardInventory(p.getInventory().getStorageContents());

            // Replace it with Player items //
            p.getInventory().setArmorContents(g.getPlayerArmor());
            p.getInventory().setStorageContents(g.getPlayerInventory());
        }
    }

}
