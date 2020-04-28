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
     * Get the Player Mojang UUID of a guard
     * @return
     */
    public UUID getPlayerID() {
        return this.playerID;
    }

    /**
     * Get the Player Name of a guard
     * @return
     */
    public String getPlayerName() {
        return this.playerName;
    }

    /**
     * Get the players armor, used when reverting a player to regular gamemode (Leaving guard mode)
     * @return
     */
    public ItemStack[] getPlayerArmor() {
        return playerArmor;
    }

    /**
     * Get the players inventory, used when reverting a player to regular gamemode (Leaving guard mode)
     * @return
     */
    public ItemStack[] getPlayerInventory() {
        return playerInventory;
    }

    /**
     * Get the guards armor, specific to the gamemode
     * @return
     */
    public ItemStack[] getGuardArmor() {
        return guardArmor;
    }

    /**
     * Get the guards inventory, specific to the gamemode
     * @return
     */
    public ItemStack[] getGuardInventory() {
        return guardInventory;
    }

    /**
     * Get whether or not a guard is on duty, used mainly for the /guard start and stop commands and probably when
     * whacking players with the stick.
     * @return
     */
    public boolean isOnDuty() {
        return onDuty;
    }

    public void setPlayerArmor(ItemStack[] playerArmor) {
        this.playerArmor = playerArmor;
    }

    public void setPlayerInventory(ItemStack[] playerInventory) {
        this.playerInventory = playerInventory;
    }

    public void setGuardArmor(ItemStack[] guardArmor) {
        this.guardArmor = guardArmor;
    }

    public void setGuardInventory(ItemStack[] guardInventory) {
        this.guardInventory = guardInventory;
    }
}
