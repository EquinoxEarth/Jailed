package io.github.equinoxearth.jailed;

import co.aikar.commands.BukkitCommandManager;
import co.aikar.commands.MessageKeys;
import co.aikar.commands.MessageType;
import com.sun.corba.se.impl.activation.CommandHandler;
import io.github.equinoxearth.jailed.commands.GuardCommand;
import io.github.equinoxearth.jailed.commands.JailCommand;
import io.github.equinoxearth.jailed.guard.Guard;
import io.github.equinoxearth.jailed.guard.GuardListener;
import io.github.equinoxearth.jailed.guard.GuardManager;
import io.github.equinoxearth.jailed.jail.Jail;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public final class Jailed extends JavaPlugin {

    /* File Paths */
    private static String mainDirectory;
    private static String flatFileDirectory;
    private static String guardsFile;

    public static Jailed plugin;
    private static BukkitCommandManager commandManager;

    public static GuardManager guardManager;

    @Override
    public void onEnable() {
        /*
         * We need to make sure to create new instances of everything the plugin
         * will need to run, this includes all our listeners, our plugin integrations,
         * and then we'll need to register all our events
         */

        plugin = this;
        guardManager = new GuardManager();
        setupFilePaths();
        registerCommands();

        // Register plugin listeners //
        getServer().getPluginManager().registerEvents(new GuardListener(), this);

        // This needs to come last, after we've successfully gotten through everything //
        getLogger().info("Jailed is Enabled.");
    }

    @Override
    public void onDisable() {

        // Pull every guard off duty //
        Guard g;
        Map<UUID, Guard> gList = guardManager.getGuards();
        // Iterate through the list of guards //
        for (Map.Entry<UUID, Guard> entry : gList.entrySet()) {
            g = entry.getValue();
            guardManager.endShift(Bukkit.getPlayer(g.getPlayerID()));
        }

        getLogger().info("Jailed is Disabled.");
    }

    public void debug(String message) {
        getLogger().info("[Debug] " + message);
    }

    /**
     * Setup file paths for storage
     */
    private void setupFilePaths() {
        mainDirectory = getDataFolder().getPath() + File.separator;
        flatFileDirectory = mainDirectory + "flatfiles" + File.separator;
        guardsFile = flatFileDirectory + "guards.yml";
    }

    /**
     * Register all commands for the plugin
     */
    private void registerCommands() {

        // Create the Command Manager //
        commandManager = new BukkitCommandManager(this);

        // Setup any replacement values for use in annotations dynamically. //

        // Register any Custom Command Contexts //
        /*
        commandManager.getCommandContexts().registerContext(
                /* The class of the object to resolve*//*
        SomeObject.class,
                /* A resolver method - Placed the resolver in its own class for organizational purposes *//*
                SomeObject.getContextResolver());
        */

        // Register any Command Completions - accessible with @CommandCompletion("@test") //
        /*
        commandManager.getCommandCompletions().registerAsyncCompletion("test", c ->
                Arrays.asList("foo123", "bar123", "baz123")
        );
         */

        // Register Command Conditions //
        /*
        commandManager.getCommandConditions().addCondition(SomeObject.class, "limits", (c, exec, value) -> {
            if (value == null) {
                return;
            }
            if (c.hasConfig("min") && c.getConfigValue("min", 0) > value.getValue()) {
                throw new ConditionFailedException("Min value must be " + c.getConfigValue("min", 0));
            }
            if (c.hasConfig("max") && c.getConfigValue("max", 3) < value.getValue()) {
                throw new ConditionFailedException("Max value must be " + c.getConfigValue("max", 3));
            }
        });
         */

        // Register Dependencies - injected into fields of command classes marked with @Dependency. Plugin is registered by default //
        /*
        Something something = new Something();
        something.doSomething("with this something");
        commandManager.registerDependency(Something.class, something);
         */

        // Register Commands //
        commandManager.registerCommand(new GuardCommand().setExceptionHandler((command, registeredCommand, sender, args, t) -> {
            sender.sendMessage(MessageType.ERROR, MessageKeys.ERROR_GENERIC_LOGGED);
            return true;
        }));
        commandManager.registerCommand(new JailCommand().setExceptionHandler((command, registerCommand, sender, args, t) -> {
            sender.sendMessage(MessageType.ERROR, MessageKeys.ERROR_GENERIC_LOGGED);
            return true;
        }));

    }

    public static String getMainDirectory() {
        return mainDirectory;
    }

    public static String getFlatFileDirectory() {
        return flatFileDirectory;
    }

    public static String getGuardsFilePath() {
        return guardsFile;
    }
}
