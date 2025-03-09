package net.globemc.multicody10.globeessentials.listeners;

import com.palmergames.bukkit.towny.event.TownAddResidentEvent;
import com.palmergames.bukkit.towny.event.player.PlayerKilledPlayerEvent;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.exceptions.TownyException;
import com.palmergames.bukkit.towny.object.Nation;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;
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
    @EventHandler
    public void OnMayorKill(PlayerKilledPlayerEvent event) throws NotRegisteredException {
        Player player = event.getKiller();
        Resident victim = (Resident) event.getVictim();
        if (victim.hasTown()) {
            Town town = victim.getTown();
            if (town.getMayor().equals(victim)) {
                GlobalRegionScheduler scheduler = Bukkit.getGlobalRegionScheduler();
                scheduler.execute(Main.getPlugin(Main.class), () -> {
                    String command = "minecraft:advancement grant " + player.getName() + " only globemc:world/kill_mayor";
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
                });
            }
        }
    }
    @EventHandler
    public void OnLeaderKill(PlayerKilledPlayerEvent event) throws TownyException {
        Player player = event.getKiller();
        Resident victim = (Resident) event.getVictim();
        if (victim.hasNation()) {
            Nation nation = victim.getNation();
            if (nation.getKing().equals(victim)) {
                GlobalRegionScheduler scheduler = Bukkit.getGlobalRegionScheduler();
                scheduler.execute(Main.getPlugin(Main.class), () -> {
                    String command = "minecraft:advancement grant " + player.getName() + " only globemc:world/kill_leader";
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
                });
            }
        }
    }
}
