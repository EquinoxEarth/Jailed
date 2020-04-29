package io.github.equinoxearth.jailed.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import io.github.equinoxearth.jailed.Jailed;
import io.github.equinoxearth.jailed.guard.Guard;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("guard")
@Description("Base command for all commands involving Guards")
public class GuardCommand extends BaseCommand {

    protected static Jailed plugin = Jailed.plugin;

    // Define all Sub Commands for the Guard Base //

    /**
     * Starts a guard mode
     * @param sender
     */
    @Subcommand("start")
    @CommandPermission("guard.start")
    @Description("Start a guard shift")
    public void onStart(CommandSender sender) {
        plugin.guardManager.startShift(Bukkit.getPlayer(sender.getName()));
    }

    /**
     * Ends a guard mode
     * @param sender
     */
    @Subcommand("end")
    @CommandPermission("guard.end")
    @Description("End a guard shift")
    public void onEnd(CommandSender sender) {
        plugin.guardManager.endShift(Bukkit.getPlayer(sender.getName()));
    }

    @Subcommand("setspawn")
    @CommandPermission("guard.setspawn")
    @Description("Set the location of the guard station to your location")
    public void onStationSpawnSet(CommandSender sender) {
        plugin.guardManager.setStationLocation(Bukkit.getPlayer(sender.getName()).getLocation());
    }

    /**
     * Lists guards
     * @param sender
     * @param status
     */
    @Subcommand("list")
    @CommandPermission("guard.admin.list")
    @Description("List guards based on supplied parameter. If no parameter, list all including status")
    public void onList(CommandSender sender, @Optional String status) {

        // Check if any parameters were passed //
        if (status == null) {
            // Display all guards including online status and whether on duty or not //
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
