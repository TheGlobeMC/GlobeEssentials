package net.globemc.multicody10.globeEssentials.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.Plugin;

import java.util.Objects;

import static net.globemc.multicody10.globeEssentials.utils.GlobeEssentialsUtils.voidDeathPrefix;

public class VoidDeathListener implements Listener {

    public Plugin plugin;

    public VoidDeathListener(Plugin plugin){
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onVoidDeath(PlayerDeathEvent event){
        if(Objects.requireNonNull(event.getEntity().getLastDamageCause()).getCause() == EntityDamageEvent.DamageCause.VOID) {
            if(Objects.requireNonNull(event.getEntity().getPlayer()).getWorld() == Bukkit.getWorld("earth")){
                event.setKeepInventory(true);
                event.setKeepLevel(true);
                event.getEntity().getPlayer().sendMessage(voidDeathPrefix + ChatColor.YELLOW + "You have died in the overworld void, and have kept your inventory/XP.");
            }
        }
    }
}
