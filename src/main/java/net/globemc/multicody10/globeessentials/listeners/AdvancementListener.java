package net.globemc.multicody10.globeessentials.listeners;

import com.palmergames.bukkit.towny.event.TownAddResidentEvent;
import net.globemc.multicody10.globeessentials.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class AdvancementListener implements Listener {
    public AdvancementListener(Main plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }
    @EventHandler
    public void UponTownJoin(TownAddResidentEvent event) {
        Player player = event.getResident().getPlayer();
        if (player != null) {
            Bukkit.getGlobalRegionScheduler().execute((Plugin) this, () -> {
                String command = "minecraft:advancement grant " + player.getName() + " only globemc:world/make_town";
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
            });
        }
    }
}
