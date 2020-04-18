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

    public boolean isGuard(final Player player) {
        return player.hasPermission("guard.player");
    }

    public void makeGuard(final Player player) {
        if (isGuard(player)) {

        }
    }

}
