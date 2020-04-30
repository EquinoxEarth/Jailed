package io.github.equinoxearth.jailed.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import io.github.equinoxearth.jailed.Jailed;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("jail")
@Description("Group of commands allowing for creation and deletion of jails")
public class JailCommand extends BaseCommand {

    // Dependencies //
    @Dependency("plugin")
    private Jailed plugin;

    /**
     * Set X position 1 for a jail
     * @param player
     */
    @Subcommand("pos1")
    @CommandPermission("jail.p1")
    public void pointOne(CommandSender sender, Player player, String name) {

    }

    @Subcommand("pos2")
    @CommandPermission("jail.p2")
    public void pointTwo(CommandSender sender, Player player, String name) {

    }

}
