package io.github.equinoxearth.jailed.guard;

import io.github.equinoxearth.jailed.Jailed;
import io.github.equinoxearth.jailed.guard.Guard;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GuardManager {

    // Variable Declarations //
    private final Jailed plugin;                            // Instance of the plugin //
    private Map<UUID, Guard> guards;
    private Location stationPos;                            // Place where Guards teleport to //

    // Create a new instance of GuardManager //
    public GuardManager() {
        this.plugin = Jailed.plugin;                        // Map the plugin instance into the GuardManager //
        this.guards = new HashMap<UUID, Guard>();           // Create the list of guards //
    }

    public Map<UUID, Guard> getGuards() {
        return this.guards;
    }

    public void startShift(Player p) {
        Guard g = guards.get(p.getUniqueId());

        // Is the player already on duty? //
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
