package net.globemc.multicody10.globeEssentials;

import net.globemc.multicody10.globeEssentials.commands.MapCommand;
import net.globemc.multicody10.globeEssentials.listeners.VoidDeathListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class GlobeEssentials extends JavaPlugin {

    /**
     * Called when the plugin is enabled.
     * This method performs the initial setup for the plugin, including:
     * - Initializing the commands by invoking the {@link #initCommands()} method.
     * - Setting up event listeners by invoking the {@link #initListeners()} method.
     */
    @Override
    public void onEnable() {
        getLogger().info("GlobeEssentials is enabled.");
        initCommands();
        initListeners();
    }

    void initCommands(){
        getLogger().info("Initializing Commands...");
        Objects.requireNonNull(this.getCommand("map")).setExecutor(new MapCommand(this));
        getLogger().info("Initialized Commands.");
    }

    void initListeners(){
        getLogger().info("Initializing Listeners...");
        new VoidDeathListener(this);
        getLogger().info("Initialized Listeners.");
    }

    @Override
    public void onDisable() {
        // TODO: Plugin shutdown logic
    }
}
