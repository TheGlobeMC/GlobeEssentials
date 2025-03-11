package net.globemc.multicody10.globeessentials.chatfilter.data;

import net.globemc.multicody10.globeessentials.Main;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DataManager {
    private final Main plugin;
    private FileConfiguration data;
    private File dataFile;
    private final Map<Player, Boolean> playerList = new HashMap<>();

    public DataManager(Main plugin) {
        this.plugin = plugin;
        createDataFile();
        loadPlayers();
    }

    public Set<Player> getFilterRecipients(Set<Player> recipients) {
        boolean filterDefault = plugin.getConfig().getBoolean("settings.filterDefault");
        Set<Player> players = new HashSet<>();
        for (Player player : recipients) {
            if (playerList.containsKey(player)) {
                if (playerList.get(player)) {
                    players.add(player);
                }
            } else {
                if (filterDefault) {
                    players.add(player);
                }
            }
        }
        return players;
    }

    public boolean getPlayerSetting(Player player) {
        if (playerList.containsKey(player)) {
            return playerList.get(player);
        }
        return plugin.getConfig().getBoolean("settings.filterDefault");
    }

    public void savePlayerFilter(Player player) {
        if (playerList.containsKey(player)) {
            Boolean value = playerList.get(player);
            data.set(player.getUniqueId() + ".chatFilter", value);
            saveData();
        }
    }

    public Boolean hasData(Player player) {
        return data.isSet(player.getUniqueId().toString());
    }

    public void setPlayer(Player player, Boolean value) {
        playerList.put(player, value);
        savePlayerFilter(player);
    }

    public void loadPlayer(Player player) {
        if (data.isSet(player.getUniqueId() + ".chatFilter")) {
            Boolean value = data.getBoolean(player.getUniqueId() + ".chatFilter");
            playerList.put(player, value);
        }
    }

    public void removePlayer(Player player) {
        playerList.remove(player);
    }

    public void saveData() {
        try {
            data.save(dataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadPlayers() {
        for (Player player : plugin.getServer().getOnlinePlayers()) {
            if (hasData(player)) {
                loadPlayer(player);
            }
        }
    }

    public void createDataFile() {
        dataFile = new File(plugin.getDataFolder(), "data.yml");
        if (!dataFile.exists()) {
            dataFile.getParentFile().mkdirs();
            try {
                dataFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        data = new YamlConfiguration();
        try {
            data.load(dataFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
}