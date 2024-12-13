package net.globemc.multicody10.globeessentials.listeners;

import net.globemc.multicody10.globeessentials.Main;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import xyz.jpenilla.squaremap.api.Squaremap;
import xyz.jpenilla.squaremap.api.PlayerManager;

public class PlayerVisibilityListener implements Listener {
    public PlayerVisibilityListener(Main plugin) {
        // Make sure to initialize the SquareMap API
        this.squareMapAPI = (Squaremap) Bukkit.getPluginManager().getPlugin("SquareMap");
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }
    // Assuming you have access to SquareMapAPI
    private final Squaremap squareMapAPI;

    @EventHandler
    public void onPlayerCompass(PlayerItemHeldEvent e) {
        Player player = e.getPlayer();
        Material itemInHand = player.getInventory().getItemInMainHand().getType();
        Material itemInOffhand = player.getInventory().getItemInOffHand().getType();

        // Check if the player is holding a compass in either hand
        if (itemInHand == Material.COMPASS || itemInOffhand == Material.COMPASS) {
            // Get the SquareMap PlayerManager for player-specific tasks
            PlayerManager playerManager = squareMapAPI.playerManager();

            // Here, check if you need to execute tasks based on whether the player is holding a Lodestone Compass or not
            if (isLodestoneCompass(player.getInventory().getItemInMainHand())) {
                // Schedule the task with Folia's region-specific scheduler
                Bukkit.getScheduler().runTask((Plugin) this, () -> {
                    // Example: Send action bar message when player is holding a Lodestone Compass
                    playerManager.hide(player.getUniqueId());  // Hide the player on the map
                });
            } else {
                // If not a Lodestone Compass, show the player again on the map
                Bukkit.getScheduler().runTask((Plugin) this, () -> {
                    playerManager.show(player.getUniqueId());  // Show the player on the map
                });
            }
        }
    }

    private boolean isLodestoneCompass(ItemStack item) {
        // Check if the compass has a Lodestone persistent data key
        if (!item.hasItemMeta()) return false;

        PersistentDataContainer dataContainer = item.getItemMeta().getPersistentDataContainer();
        NamespacedKey lodestoneKey = new NamespacedKey("minecraft", "LodestoneTracked");

        // If "LodestoneTracked" key exists and is true, it's a Lodestone compass
        return dataContainer.has(lodestoneKey, PersistentDataType.BYTE)
                && dataContainer.get(lodestoneKey, PersistentDataType.BYTE) == 1;
    }
}
