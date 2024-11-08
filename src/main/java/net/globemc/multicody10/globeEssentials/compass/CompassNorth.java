package net.globemc.multicody10.globeEssentials.compass;

import net.globemc.multicody10.globeEssentials.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CompassMeta;

public class CompassNorth implements Listener {
    public CompassNorth(Main plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        final ItemStack compass = new ItemStack(Material.COMPASS);
        final CompassMeta compassMeta = (CompassMeta) compass.getItemMeta();
        if (player.getWorld().getEnvironment().equals(World.Environment.NORMAL)) {
            if (player.getInventory().contains(Material.COMPASS)) {
                assert compassMeta != null;
                if (!compassMeta.hasLodestone()) {
                    player.setCompassTarget(player.getLocation().add(0, 0, -99999));
                }
            }
        }
    }
    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent e) {
        Player player = e.getPlayer();
        final ItemStack compass = new ItemStack(Material.COMPASS);
        final CompassMeta compassMeta = (CompassMeta) compass.getItemMeta();
        if (player.getWorld().getEnvironment().equals(World.Environment.NORMAL)) {
            if (player.getInventory().contains(Material.COMPASS)) {
                assert compassMeta != null;
                if (!compassMeta.hasLodestone()) {
                    player.setCompassTarget(player.getLocation().add(0, 0, -99999));
                }
            }
        }
    }
    @EventHandler
    public void onPlayerDimensionChange(PlayerChangedWorldEvent e) {
        Player player = e.getPlayer();
        final ItemStack compass = new ItemStack(Material.COMPASS);
        final CompassMeta compassMeta = (CompassMeta) compass.getItemMeta();
        if (player.getWorld().getEnvironment().equals(World.Environment.NORMAL)) {
            if (player.getInventory().contains(Material.COMPASS)) {
                assert compassMeta != null;
                if (!compassMeta.hasLodestone()) {
                    player.setCompassTarget(player.getLocation().add(0, 0, -99999));
                }
            }
        }
    }
}
