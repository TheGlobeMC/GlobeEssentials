package net.globemc.multicody10.globeessentials.compass;

import io.papermc.paper.threadedregions.scheduler.EntityScheduler;
import net.globemc.multicody10.globeessentials.Main;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class CompassNorth implements Listener {
    public CompassNorth(Main plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getWorld().getEnvironment().equals(World.Environment.NORMAL)) {
                EntityScheduler scheduler = player.getScheduler();
                scheduler.runAtFixedRate(plugin, _ -> player.setCompassTarget(player.getLocation().add(0.0D, 0.0D, -100.0D)), null, 0L, 30L);
            }
        }
    }
}

