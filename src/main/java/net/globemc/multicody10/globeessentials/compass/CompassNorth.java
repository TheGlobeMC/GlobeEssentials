package net.globemc.multicody10.globeessentials.compass;

import net.globemc.multicody10.globeessentials.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CompassMeta;

public class CompassNorth implements Listener {
    public CompassNorth(Main plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }
    @EventHandler
    public void JoinCompassPoint(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        ItemStack compass = new ItemStack(Material.COMPASS);
        CompassMeta compassMeta = (CompassMeta) compass.getItemMeta();
        if (player.getWorld().getEnvironment().equals(World.Environment.NORMAL)) {
            if (!compassMeta.hasLodestone()) {
                player.setCompassTarget(player.getLocation().add(0.0D, 0.0D, -1000.0D));
            }
        }
    }
    @EventHandler
    public void TeleportCompassPoint(PlayerTeleportEvent e) {
        Player player = e.getPlayer();
        ItemStack compass = new ItemStack(Material.COMPASS);
        CompassMeta compassMeta = (CompassMeta) compass.getItemMeta();
        if (player.getWorld().getEnvironment().equals(World.Environment.NORMAL)) {
            if (!compassMeta.hasLodestone()) {
                player.setCompassTarget(player.getLocation().add(0.0D, 0.0D, -1000.0D));
            }
        }
    }
}

