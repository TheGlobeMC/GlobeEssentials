package net.globemc.multicody10.globeessentials;

import net.globemc.multicody10.globeessentials.chatfilter.api.ChatFilterAPI;
import net.globemc.multicody10.globeessentials.chatfilter.commands.ChatFilterCommand;
import net.globemc.multicody10.globeessentials.chatfilter.data.DataManager;
import net.globemc.multicody10.globeessentials.chatfilter.events.Events;
import net.globemc.multicody10.globeessentials.chatfilter.filter.Filter;
import net.globemc.multicody10.globeessentials.chatfilter.filter.Spam;
import net.globemc.multicody10.globeessentials.commands.CoordinateCommand;
import net.globemc.multicody10.globeessentials.commands.HelpCommand;
import net.globemc.multicody10.globeessentials.commands.MapCommand;
import net.globemc.multicody10.globeessentials.commands.RecipesCommand;
import net.globemc.multicody10.globeessentials.compass.CompassNorth;
import net.globemc.multicody10.globeessentials.compass.CompassUI;
import net.globemc.multicody10.globeessentials.listeners.AdvancementListener;
import net.globemc.multicody10.globeessentials.listeners.CooldownListener;
import net.globemc.multicody10.globeessentials.listeners.VoidDeathListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class Main extends JavaPlugin {
    public static ChatFilterAPI chatFilterAPI;
    @Override
    public void onEnable() {
        saveDefaultConfig();
        getLogger().info("GlobeEssentials is enabled.");
        initCommands();
        initListeners();
        initCompassAPI();
        initFilterAPI();
    }

    void initCommands(){
        getLogger().info("Initializing Commands...");
        Objects.requireNonNull(this.getCommand("map")).setExecutor(new MapCommand(this));
        Objects.requireNonNull(this.getCommand("help")).setExecutor(new HelpCommand(this));
        Objects.requireNonNull(this.getCommand("recipes")).setExecutor(new RecipesCommand(this));
        Objects.requireNonNull(this.getCommand("coordinate")).setExecutor(new CoordinateCommand(this));
        getLogger().info("Initialized Commands.");
    }

    void initListeners(){
        getLogger().info("Initializing Listeners...");
        new AdvancementListener(this);
        new CooldownListener(this);
        new VoidDeathListener(this);
        getLogger().info("Initialized Listeners.");
    }

    void initCompassAPI(){
        getLogger().info("Initializing Compass Mechanics...");
        new CompassNorth(this);
        getLogger().info("Initialized Compass Mechanics.");
    }

    void initFilterAPI(){
        getLogger().info("Initializing Chat Filter...");
        DataManager dataManager = new DataManager(this);
        Filter filter = new Filter(this);
        Spam spam = new Spam();
        chatFilterAPI = new ChatFilterAPI(filter, spam, dataManager);
        getServer().getPluginManager().registerEvents(new Events(this, filter, dataManager, spam), this);
        Objects.requireNonNull(getCommand("filter")).setExecutor(new ChatFilterCommand(this, dataManager, filter));
        getLogger().info("Initialized Chat Filter.");
    }
    public ChatFilterAPI getAPI() {
        return chatFilterAPI;
    }
}
