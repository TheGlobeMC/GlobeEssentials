package net.globemc.multicody10.globeessentials.listeners;

import com.palmergames.bukkit.towny.event.TownAddResidentEvent;
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
    public void UponTownJoin(TownAddResidentEvent e){
        Player player = e.getResident().getPlayer();
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "advancement grant " + player + " only globemc:world/make_town");
    }
}
