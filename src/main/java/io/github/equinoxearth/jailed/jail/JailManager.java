package io.github.equinoxearth.jailed.jail;

import io.github.equinoxearth.jailed.Jailed;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class JailManager {

    private static Jailed plugin;
    private Jail jail;

    private Map<UUID, Player> jailedPlayers;

    public JailManager(Jailed p) {
        plugin = p;
        this.jail = JailLoader.loadJail(new File(plugin.getJailFilePath()));
    }

    public Jail getJail() { return this.jail; }

    public Map<UUID, Player> getJailedPlayers() { return this.jailedPlayers; }
}
