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
import xyz.jpenilla.squaremap.api.Squaremap;
import xyz.jpenilla.squaremap.api.PlayerManager;

public class PlayerVisibilityListener implements Listener {
    public PlayerVisibilityListener(Main plugin) {
        this.squareMapAPI = (Squaremap) Bukkit.getPluginManager().getPlugin("squaremap");
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }
    private final Squaremap squareMapAPI;

    @EventHandler
    public void onPlayerCompass(PlayerItemHeldEvent e) {
        Player player = e.getPlayer();
        Material itemInHand = player.getInventory().getItemInMainHand().getType();
        Material itemInOffhand = player.getInventory().getItemInOffHand().getType();

        // Check if the player is holding a compass in either hand
        if (itemInHand == Material.COMPASS || itemInOffhand == Material.COMPASS) {
            PlayerManager playerManager = squareMapAPI.playerManager();

            // Here, check if you need to execute tasks based on whether the player is holding a Lodestone Compass or not
            if (isLodestoneCompass(player.getInventory().getItemInMainHand())) {
                Bukkit.getGlobalRegionScheduler().execute(Main.getPlugin(Main.class), () -> {
                    playerManager.hide(player.getUniqueId());  // Hide the player on the map
                });
            } else {
                Bukkit.getGlobalRegionScheduler().execute(Main.getPlugin(Main.class), () -> {
                    playerManager.show(player.getUniqueId());  // Show the player on the map
                });
            }
        }
    }

    private boolean isLodestoneCompass(ItemStack item) {
        if (!item.hasItemMeta()) return false;
        PersistentDataContainer dataContainer = item.getItemMeta().getPersistentDataContainer();
        NamespacedKey lodestoneKey = new NamespacedKey("minecraft", "LodestoneTracked");

        return dataContainer.has(lodestoneKey, PersistentDataType.BYTE)
                && dataContainer.get(lodestoneKey, PersistentDataType.BYTE) == 1;
    }
}
