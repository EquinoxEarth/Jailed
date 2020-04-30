package io.github.equinoxearth.jailed.guard;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.OfflinePlayer.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Represents Guards in the gamemode.
 */
@SerializableAs("Guard")
public class Guard implements ConfigurationSerializable {

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
     */
    public Guard(UUID pID) {
        player = Bukkit.getPlayer(pID);
        this.playerID = pID;
        this.playerName = player.getPlayerListName();
        this.onDuty = false;

        // Give the guard the default items //
        this.guardArmor = new ItemStack[0];
        this.guardInventory = new ItemStack[0];
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

    /*
     * SERIALIZE & DESERIALIZE SECTION
     */

    /**
     * Build a Guard object from a Map
     * @param map
     */
    public Guard(Map<String, Object> map) {
        this.playerID = UUID.fromString((String) map.get("playerID"));
        this.playerName = (String) map.get("playerName");
        this.playerArmor = ((List<ItemStack>) map.get("playerArmor")).toArray(new ItemStack[0]);
        this.playerInventory = ((List<ItemStack>) map.get("playerInventory")).toArray(new ItemStack[0]);
        this.guardArmor = ((List<ItemStack>) map.get("guardArmor")).toArray(new ItemStack[0]);
        this.guardInventory = ((List<ItemStack>) map.get("guardInventory")).toArray(new ItemStack[0]);
        this.onDuty = false;
    }

    public static Guard deserialize(Map<String, Object> map) {
        return new Guard(map);
    }

    public static Guard valueOf(Map<String, Object> map) {
        return new Guard(map);
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();

        // Add all properties to the map //
        map.put("playerID", this.playerID.toString());
        map.put("playerName", this.playerName);
        map.put("playerArmor", this.playerArmor);
        map.put("playerInventory", this.playerInventory);
        map.put("guardArmor", this.guardArmor);
        map.put("guardInventory", this.guardInventory);

        return map;
    }
}
