package io.github.equinoxearth.jailed.guard;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.OfflinePlayer.*;

import java.util.UUID;

// Represents Guards in the plugin //
public class Guard {

    // <-- Variable Section Begins --> //

    // Player Attributes //
    private Player player;
    private final UUID playerID;                    // Unique Player ID //
    private final String playerName;                // Player Name //
    private ItemStack[] playerArmor;                // Armor normally worn by player //
    private ItemStack[] playerInventory;            // Inventory contents for the player //

    // Guard Attributes //
    private ItemStack[] guardArmor;                 // Armor that the Guard will be wearing //
    private ItemStack[] guardInventory;             // Items that the Guard will be wearing //
    private boolean onDuty;                         // True if on duty, False if off duty //

    // <-- Variable Section Ends --> //

    /**
     * Create a new Default Guard
     * @param pID Mojang UUID for the player
     * @param pName Name of the player
     */
    public Guard(UUID pID, String pName) {
        player = Bukkit.getPlayer(pID);
        this.playerID = pID;
        this.playerName = pName;
        this.onDuty = false;

        // Give the guard the default items //

    }

    /**
     * Fetch the Mojang UUID for the Guard
     * @return UUID Value
     */
    public final UUID getPlayerID() { return this.playerID; }

    /**
     * Check if the Guard is currently on duty
     * @return true or false
     */
    public boolean isOnDuty() { return this.onDuty; }

    public void setPlayerArmor(ItemStack[] armor) {
        this.playerArmor = armor;
    }

    public void startShift() {

    }
}
