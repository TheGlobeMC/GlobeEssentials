package net.globemc.multicody10.globeessentials.listeners;

import net.globemc.multicody10.globeessentials.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;

public class CooldownListener implements Listener {
    public CooldownListener(Main plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }
    @EventHandler(priority = EventPriority.LOW)
    public void EnchantedAppleEat(PlayerItemConsumeEvent e) {
        Player player = e.getPlayer();
        ItemStack is = e.getItem();
        if (is.getType().equals(Material.ENCHANTED_GOLDEN_APPLE)) {
            player.setCooldown(Material.ENCHANTED_GOLDEN_APPLE, 6000);
        }
    }
    @EventHandler(priority = EventPriority.LOW)
    public void GoldenAppleEat(PlayerItemConsumeEvent e) {
        Player player = e.getPlayer();
        ItemStack is = e.getItem();
        if (is.getType().equals(Material.GOLDEN_APPLE)) {
            player.setCooldown(Material.GOLDEN_APPLE, 600);
        }
    }
}
