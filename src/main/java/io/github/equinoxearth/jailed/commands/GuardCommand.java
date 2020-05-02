package io.github.equinoxearth.jailed.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import io.github.equinoxearth.jailed.Jailed;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("guard")
@Description("Base command for all commands involving Guards")
public class GuardCommand extends BaseCommand {

    @Dependency("plugin")
    private Jailed plugin;

    // Define all Sub Commands for the Guard Base //

    @Subcommand("add")
    @CommandPermission("guard.admin.add")
    public void onAdd(CommandSender sender, Player p) {
        plugin.guardManager.addGuard(p);
    }

    /**
     * Starts a guard mode
     * @param sender
     */
    @Subcommand("start")
    @CommandPermission("guard.start")
    @Description("Start a guard shift")
    public void onStart(CommandSender sender, Player p) {
        plugin.guardManager.startShift(p);
    }

    /**
     * Ends a guard mode
     * @param sender
     */
    @Subcommand("end")
    @CommandPermission("guard.end")
    @Description("End a guard shift")
    public void onEnd(CommandSender sender, Player p) {
        plugin.guardManager.endShift(p);
    }

    @Subcommand("setspawn")
    @CommandPermission("guard.setspawn")
    @Description("Set the location of the guard station to your location")
    public void onStationSpawnSet(CommandSender sender, Player p) {
        plugin.guardManager.setStationLocation(p.getLocation());
    }

    /**
     * Lists guards
     * @param sender
     * @param status
     */
    @Subcommand("list")
    @CommandPermission("guard.admin.list")
    @Description("List guards based on supplied parameter. If no parameter, list all including status")
    public void onList(CommandSender sender, @Optional String status, Player p) {

        // Check if any parameters were passed //
        if (status == null) {
            // Display all guards including online status and whether on duty or not //
            sender.sendMessage(plugin.guardManager.getGuards().toString());
        } else if (status.equalsIgnoreCase("onduty")) {

            // Display all guards currently on duty //

        } else if (status.equalsIgnoreCase("offduty")) {

            // Display all guards currently off duty //

        } else if (status.equalsIgnoreCase("online")) {

            // Display all online guards, including whether on duty or off duty //

        } else if (status.equalsIgnoreCase("offline")) {

            // Display all offline guards //

        } else {

            // Let the player know the possible parameters, since they don't I guess //
            sender.sendMessage("Valid parameters are onduty, offduty, online, and offline");

        }

    }

}
