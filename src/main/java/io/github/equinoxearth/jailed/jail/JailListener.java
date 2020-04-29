package io.github.equinoxearth.jailed.jail;

import io.github.equinoxearth.jailed.Jailed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class JailListener implements Listener {
    // Create our instance variable for the plugin //
    private static Jailed instance;

    // Create a new Jail Listener //
    public JailListener(Jailed instance) {
        this.instance = instance;
    }

    // Check if player has moved //
    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onPlayerMove(PlayerMoveEvent event) {

    }
}
