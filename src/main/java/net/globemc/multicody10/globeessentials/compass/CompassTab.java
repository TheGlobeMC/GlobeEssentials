package net.globemc.multicody10.globeessentials.compass;

import io.papermc.paper.threadedregions.scheduler.EntityScheduler;
import io.papermc.paper.threadedregions.scheduler.ScheduledTask;
import net.globemc.multicody10.globeessentials.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import net.kyori.adventure.text.Component;
import org.bukkit.permissions.PermissionAttachment;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.bukkit.Bukkit.getLogger;
import static org.bukkit.Bukkit.getServer;

public class CompassTab implements Listener {
    public CompassTab(Main plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
        startCompassCheckTask();
    }
    private final Map<UUID, PermissionAttachment> attachments = new HashMap<>();
    private void startCompassCheckTask() {
        // Get all online players once to avoid concurrent modification issues during iteration
        getServer().getOnlinePlayers().forEach(player -> {
            EntityScheduler scheduler = player.getScheduler();
            scheduler.runAtFixedRate(Main.getPlugin(Main.class), (ScheduledTask task) -> {
                ItemStack itemInHand = player.getInventory().getItemInMainHand();
                UUID uuid = player.getUniqueId();

                if (itemInHand.getType() != Material.COMPASS) {
                    revokePermission(player, uuid);
                    return;
                }
                grantPermission(player, uuid);
            }, null, 0L, 20L); // Run every 1 second (20 ticks)
        });
    }

    private void revokePermission(Player player, UUID uuid) {
        if (attachments.containsKey(uuid)) { // Check if the player has a permission attachment
            try {
                player.removeAttachment(attachments.remove(uuid)); // Remove the permission
                player.removeMetadata("compass.permission.player", Main.getPlugin(Main.class)); // Clear metadata
                player.sendActionBar(Component.text("§cCompass permission revoked."));
            } catch (IllegalArgumentException e) {
                getLogger().warning("Tried to remove a non-existent permission attachment from " + player.getName());
            }
        }
    }

    private void grantPermission(Player player, UUID uuid) {
        if (!attachments.containsKey(uuid)) { // If no attachment exists, grant permission
            PermissionAttachment attachment = player.addAttachment(Main.getPlugin(Main.class), "globe.compass", true);
            attachments.put(uuid, attachment); // Store the attachment
            player.setMetadata("compass.permission.player", new org.bukkit.metadata.FixedMetadataValue(Main.getPlugin(Main.class), uuid));
            player.sendActionBar(Component.text("§aCompass permission granted."));
        }
    }
}