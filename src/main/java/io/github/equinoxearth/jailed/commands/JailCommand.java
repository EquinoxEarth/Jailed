package io.github.equinoxearth.jailed.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import io.github.equinoxearth.jailed.Jailed;
import org.bukkit.entity.Player;

@CommandAlias("jail")
@Description("Group of commands allowing for creation and deletion of jails")
public class JailCommand extends BaseCommand {

    // Dependencies //
    @Dependency("jPlugin")
    private Jailed jPlugin;

    @Subcommand("pos1")
    @CommandPermission("jail.p1")
    public void pointOne(final Player player) {

    }

}
