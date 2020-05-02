package io.github.equinoxearth.jailed.managers;

import io.github.equinoxearth.jailed.Jailed;
import io.github.equinoxearth.jailed.configs.GuardLoader;
import io.github.equinoxearth.jailed.objects.Guard;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.Map;
import java.util.UUID;

public class GuardManager {

    // Variable Declarations //
    private static Jailed plugin;                           // Instance of the plugin //
    private Map<UUID, Guard> guards;
    private Location stationLocation;                       // Station for guards //

    // Create a new instance of GuardManager //
    public GuardManager(Jailed p) {
        plugin = p;                                         // Map the plugin instance into the GuardManager //
        this.guards = GuardLoader.loadGuards(new File(plugin.getGuardsFilePath()));
    }

    public void addGuard(Player p) {
        guards.put(p.getUniqueId(), new Guard(p.getUniqueId()));
    }

    /*public void loadGuards() {
        // Load all the guards here //
        try {
            guards = GuardLoader.loadGuards(new File(plugin.getGuardsFilePath()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveGuards() {
        // Save all the guards here //
        try {
            GuardLoader.saveGuards(new File(plugin.getGuardsFilePath()), guards);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

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
