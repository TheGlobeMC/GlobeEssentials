package net.globemc.multicody10.globeessentials;

import net.globemc.multicody10.globeessentials.commands.MapCommand;
import net.globemc.multicody10.globeessentials.compass.CompassNorth;
import net.globemc.multicody10.globeessentials.compass.CompassUI;
import net.globemc.multicody10.globeessentials.listeners.CooldownListener;
import net.globemc.multicody10.globeessentials.listeners.VoidDeathListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class Main extends JavaPlugin {

    /**
     * Called when the plugin is enabled.
     * This method performs the initial setup for the plugin, including:
     * - Initializing the commands by invoking the {@link #initCommands()} method.
     * - Setting up event listeners by invoking the {@link #initListeners()} method.
     * - Initializing up the compass API by invoking the {@link #initCompassAPI()} method.
     */
    @Override
    public void onEnable() {
        getLogger().info("GlobeEssentials is enabled.");
        initCommands();
        initListeners();
        initCompassAPI();
    }

    void initCommands(){
        getLogger().info("Initializing Commands...");
        Objects.requireNonNull(this.getCommand("map")).setExecutor(new MapCommand(this));
        getLogger().info("Initialized Commands.");
    }

    void initListeners(){
        getLogger().info("Initializing Listeners...");
        new VoidDeathListener(this);
        new CooldownListener(this);
        getLogger().info("Initialized Listeners.");
    }

    void initCompassAPI(){
        getLogger().info("Initializing Compass Mechanics...");
        new CompassUI(this);
        getLogger().info("Initialized Listeners.");
    }

    @Override
    public void onDisable() {
        // TODO: Plugin shutdown logic
    }
}
