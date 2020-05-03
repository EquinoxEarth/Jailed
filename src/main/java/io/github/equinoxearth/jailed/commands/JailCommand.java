package io.github.equinoxearth.jailed.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import io.github.equinoxearth.jailed.Jailed;
import net.citizensnpcs.api.command.Command;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.DoubleChestInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

@CommandAlias("jail")
@Description("Group of commands allowing for creation and deletion of jails")
public class JailCommand extends BaseCommand {

    // Dependencies //
    @Dependency("plugin")
    private Jailed plugin;

    @Subcommand("setspawn")
    @CommandPermission("jail.spawn.set")
    @Description("Sets the spawn point for the jail to the senders feet")
    public void onSetSpawn(CommandSender sender, Player p) {
        plugin.jailManager.getJail().setSpawnPoint(p.getLocation());
    }

    @Subcommand("setexit")
    @CommandPermission("jail.exit.set")
    @Description("Sets the exit point for the jail to the senders feet")
    public void onSetExit(CommandSender sender, Player p) {
        plugin.jailManager.getJail().setExitPoint(p.getLocation());
    }

    @Subcommand("items")
    @CommandPermission("jail.items.modify")
    @Description("Shows the items considered contraband")
    public void onItems(CommandSender sender, Player p) {
        Inventory inv = Bukkit.createInventory(null, 54);
        plugin.debug(plugin.contraband.toString());
        inv.addItem(new ItemStack(Material.AIR));

        if (inv == null) {
            p.openInventory(inv);
        } else {
            p.sendMessage("The inventory is null");
        }
    }
}
