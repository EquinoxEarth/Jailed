package io.github.equinoxearth.jailed;

import co.aikar.commands.BukkitCommandManager;
import co.aikar.commands.MessageKeys;
import co.aikar.commands.MessageType;
import io.github.equinoxearth.jailed.commands.GuardCommand;
import io.github.equinoxearth.jailed.commands.JailCommand;
import io.github.equinoxearth.jailed.objects.Guard;
import io.github.equinoxearth.jailed.listeners.GuardListener;
import io.github.equinoxearth.jailed.configs.GuardLoader;
import io.github.equinoxearth.jailed.managers.GuardManager;
import io.github.equinoxearth.jailed.objects.Jail;
import io.github.equinoxearth.jailed.configs.JailLoader;
import io.github.equinoxearth.jailed.managers.JailManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

public class Jailed extends JavaPlugin {

    /* File Paths */
    private static String mainDirectory;
    private static String flatFileDirectory;
    private static String guardsFile;
    private static String jailFile;

    public static Jailed plugin;
    private static BukkitCommandManager commandManager;

    public GuardManager guardManager;
    public JailManager jailManager;

    public ArrayList<ItemStack> contraband;

    /**
     * Static block for Registration
     */
    static {
        ConfigurationSerialization.registerClass(Guard.class, "Guard");
        ConfigurationSerialization.registerClass(Jail.class, "Jail");
    }

    /**
     * Initial loading of the plugin
     */
    @Override
    public void onEnable() {
        /*
         * We need to make sure to create new instances of everything the plugin
         * will need to run, this includes all our listeners, our plugin integrations,
         * and then we'll need to register all our events
         */

        plugin = this;
        setupFilePaths();
        registerCommands();

        guardManager = new GuardManager(this);
        jailManager = new JailManager(this);

        // Register plugin listeners //
        getServer().getPluginManager().registerEvents(new GuardListener(this), this);

        // Send initial setup messages //
        if(jailManager.getJail().getSpawnPoint() == null || jailManager.getJail().getExitPoint() == null) {
            plugin.debug("The jail hasn't been set up yet! Use /jail setspawn and /jail setexit");
        }

        ArrayList<ItemStack> contraband = new ArrayList<ItemStack>(); // Fake contraband variable
        if(contraband.isEmpty()) {
            plugin.debug("The contraband list is empty, might want to add some items to it using " +
                    "/jail items");
            contraband.add(new ItemStack(Material.AIR));
        }

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
            if (g.isOnDuty()) {
                guardManager.endShift(Bukkit.getPlayer(g.getPlayerID()));
            }
        }

        // Save all files //
        GuardLoader.saveGuards(new File(getGuardsFilePath()), guardManager.getGuards());
        JailLoader.saveJail(new File(getJailFilePath()), jailManager.getJail());

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
        jailFile = flatFileDirectory + "jails.yml";

        // Create any folders that don't exist //
        File t = new File(mainDirectory);
        if(!t.exists()) {
            t.mkdir();
            t = new File(flatFileDirectory);
            if(!t.exists()) {
                t.mkdir();
            }
        }
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
        commandManager.registerDependency(Jailed.class, "plugin", plugin);

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

    public static String getGuardsFilePath() { return guardsFile; }

    public static String getJailFilePath() { return jailFile; }
}
