package io.github.equinoxearth.jailed.config;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.Map;

/**
 * Baseline for how configs will be setup. This includes the list of guards and jails, as well as
 * default plugin settings
 */
public class Config implements ConfigurationSerializable {

    @Override
    public Map<String, Object> serialize() {
        return null;
    }
}
