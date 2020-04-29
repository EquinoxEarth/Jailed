package io.github.equinoxearth.jailed.guard;

import io.github.equinoxearth.jailed.Jailed;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class GuardListener implements Listener {

    private static Jailed plugin;
    private static GuardManager guardManager;

    public GuardListener() {
        this.plugin = Jailed.plugin;
        this.guardManager = plugin.guardManager;
    }

    /**
     * Handles adding joining players to the list of guards if they have the
     * necessary permissions.
     * @param event
     */
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        // Get the instance of Player //
        Player p = event.getPlayer();

        // Check if the player is already in the guard list //
        if (!guardManager.getGuards().containsKey(p.getUniqueId()) && p.hasPermission("guard.start")) {
            // Create a new guard instance and put it in the list //
            Guard g = new Guard(p.getUniqueId(), p.getName());
            guardManager.getGuards().put(g.getPlayerID(), g);
        }
    }

    /**
     * Handles preventing guards from dropping any of their items
     * @param event
     */
    @EventHandler
    public void onGuardDropItem(PlayerDropItemEvent event) {
        // Get the instance of Player //
        Player p = event.getPlayer();

        // Check if the player is a guard AND is on duty //
        if(guardManager.getGuards().containsKey(p.getUniqueId())) {
            Guard g = guardManager.getGuards().get(p.getUniqueId());
            if(g.isOnDuty()) {
                // Prevent the item from being dropped //
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onGuardAttack(EntityDamageByEntityEvent event) {
        // Make sure both entities are instances of Player //
        if(event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
            /*
             * Code here will check if the item the guard hit with is the baton.
             * Afterwards, it will search the players inventory and remove any items that are contained in the
             * list of "contraband" items. It will then add up the worth of the items and pay that amount to the
             * guard that arrested the player.
             */
        }
    }

    @EventHandler
    public void onGuardDeath(EntityDeathEvent event) {
        // Check for an instance of Player //
        if(event.getEntity() instanceof Player) {
            // Store the player instance //
            Player p = ((Player) event.getEntity());
            Guard g = guardManager.getGuards().getOrDefault(p.getUniqueId(), null);

            // Is the player a guard AND are they on duty? //
            if(g != null && g.isOnDuty()) {
                // Save inventory for respawn //
                g.setGuardInventory(p.getInventory().getStorageContents());
                g.setGuardArmor(p.getInventory().getArmorContents());

                // Prevent inventory from being dropped //
                event.getDrops().clear();
            }
        }
    }

    @EventHandler
    public void onGuardRespawn(PlayerRespawnEvent event) {
        // Store the Player instance //
        Player p = event.getPlayer();
        Guard g = guardManager.getGuards().getOrDefault(p.getUniqueId(), null);

        // Is the player a guard AND are they on duty? //
        if(g != null && g.isOnDuty()) {
            // Give the guard their inventory back //
            p.getInventory().setStorageContents(g.getGuardInventory());
            p.getInventory().setArmorContents(g.getGuardArmor());

            // Send the guard to the spawn point //
            p.teleport(guardManager.getStationLocation());
        }
    }
}
