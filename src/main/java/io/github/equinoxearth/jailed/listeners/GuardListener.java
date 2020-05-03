package io.github.equinoxearth.jailed.listeners;

import io.github.equinoxearth.jailed.Jailed;
import io.github.equinoxearth.jailed.managers.GuardManager;
import io.github.equinoxearth.jailed.managers.JailManager;
import io.github.equinoxearth.jailed.objects.Guard;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class GuardListener implements Listener {

    private Jailed plugin;
    private static GuardManager guardManager;
    private static JailManager jailManager;

    public GuardListener(Jailed p) {
        this.plugin = p;
        this.guardManager = plugin.guardManager;
        this.jailManager = plugin.jailManager;
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
            Guard g = new Guard(p.getUniqueId());
            g.setPlayerInventory(p.getInventory().getStorageContents());
            g.setPlayerArmor(p.getInventory().getArmorContents());

            guardManager.getGuards().put(g.getPlayerID(), g);
            plugin.debug("Player added to Guard list");
            plugin.debug(guardManager.getGuards().toString());
            return;
        }

        plugin.debug("Player already in Guard list");
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

            // Fake instance of Contraband //
            ArrayList<ItemStack> contraband = new ArrayList<ItemStack>();

            // Store both the Attacker and the Victim //
            Player attacker = ((Player) event.getDamager());
            Player victim = ((Player) event.getEntity());

            // Check if the Damager is a Guard //
            if (guardManager.getGuards().containsKey(attacker.getUniqueId())) {
                // Store the guard instance //
                Guard g = guardManager.getGuards().get(attacker.getUniqueId());

                // Check victim inventory for contraband, assume no //
                boolean criminal = false;
                int worth = 0;
                for (ItemStack item : victim.getInventory().getStorageContents()) {
                    if (contraband.contains(item)) {
                        criminal = true;
                        // Still need to come up with a way to set & get the worth of an item //
                        worth++; // Add the items worth to the variable //
                    }
                }

                // Jail the player //
                if (criminal) {
                    if (jailManager.jailPlayer(victim)) {
                        plugin.debug("Jailed " + victim.getName() + "!");
                    } else {
                        attacker.sendMessage("Unable to jail " + victim.getName() + "! Consult an admin.");
                    }
                }
            }
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
