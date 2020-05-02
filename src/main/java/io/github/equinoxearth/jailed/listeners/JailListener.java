package io.github.equinoxearth.jailed.listeners;

import io.github.equinoxearth.jailed.Jailed;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class JailListener implements Listener {
    // Create our instance variable for the plugin //
    private static Jailed plugin;

    // Create a new Jail Listener //
    public JailListener(Jailed p) {
        this.plugin = p;
    }

    // Check if player has moved //
    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onPlayerMove(PlayerMoveEvent event) {
        // Is the player jailed? //
        Player p = event.getPlayer();
        if (plugin.jailManager.getJailedPlayers().containsKey(p.getUniqueId())) {
            // Player is jailed, they can't leave the jail area //
            event.setCancelled(true);
        }
    }
}
