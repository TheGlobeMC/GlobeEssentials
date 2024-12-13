package net.globemc.multicody10.globeessentials.listeners;

import com.palmergames.bukkit.towny.event.TownAddResidentEvent;
import io.papermc.paper.threadedregions.scheduler.GlobalRegionScheduler;
import net.globemc.multicody10.globeessentials.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class AdvancementListener implements Listener {
    public AdvancementListener(Main plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }
    @EventHandler
    public void UponTownJoin(TownAddResidentEvent event) {
        Player player = event.getResident().getPlayer();
        GlobalRegionScheduler scheduler = Bukkit.getGlobalRegionScheduler();
        if (player != null) {
            scheduler.execute(Main.getPlugin(Main.class), () -> {
                String command = "minecraft:advancement grant " + player.getName() + " only globemc:world/make_town";
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
            });
        }
    }
}
