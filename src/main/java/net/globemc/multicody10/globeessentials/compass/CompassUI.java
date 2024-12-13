package net.globemc.multicody10.globeessentials.compass;

import net.globemc.multicody10.globeessentials.Main;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class CompassUI implements Listener {
    public CompassUI(Main plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }
    /**
     * Event handler for when a player switches items in their hotbar.
     * Displays information in the action bar depending on whether the player is holding a compass.
     *
     * @param e the event that triggered this handler
     */
    @EventHandler
    public void onPlayerCompass(PlayerItemHeldEvent e) {
        Player player = e.getPlayer();
        ItemStack itemInHand = player.getInventory().getItemInMainHand();
        ItemStack itemInOffhand = player.getInventory().getItemInOffHand();

        // Check if the item is a compass (either in main hand or offhand)
        if (itemInHand.getType() == Material.COMPASS || itemInOffhand.getType() == Material.COMPASS) {
            String actionBarMessage = getActionBarMessage(player, itemInHand);
            // Send the action bar message to the player
            player.getScheduler().execute(Main.getPlugin(Main.class), () -> {
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(actionBarMessage));
            },null,5L);
        }
    }

    /**
     * Generates the action bar message based on the player's world, compass type, and location.
     *
     * @param player the player holding the compass
     * @param compass the compass item in hand
     * @return the action bar message as a string
     */
    private String getActionBarMessage(Player player, ItemStack compass) {
        String biomeName = getBiomeName(player);
        String worldColor;
        String worldName = player.getWorld().getEnvironment().name();

        // Determine the color and message based on the world
        switch (worldName) {
            case "NORMAL":
                worldColor = "§7";
                break;
            case "NETHER":
                worldColor = "§4";
                break;
            case "THE_END":
                worldColor = "§5";
                break;
            default:
                worldColor = "§f"; // Default color for unknown worlds
                break;
        }

        // Check if the compass is a lodestone compass
        if (isLodestoneCompass(compass)) {
            return worldColor + "[ " + worldColor + "Lodestone Paired! " + worldColor + "| " + biomeName + " ]";
        } else {
            // If not a lodestone compass, show player info
            String direction = yawToFace(player);
            return worldColor + "[ " + direction + " | " + getPlayerCoordinates(player) + " | " + biomeName + " ]";
        }
    }

    /**
     * Checks if the given compass is a lodestone compass.
     *
     * @param item the compass item
     * @return true if the compass is a lodestone compass, false otherwise
     */
    private boolean isLodestoneCompass(ItemStack item) {
        if (!item.hasItemMeta()) return false;
        PersistentDataContainer dataContainer = item.getItemMeta().getPersistentDataContainer();
        return dataContainer.has(new NamespacedKey("minecraft", "LodestoneTracked"), PersistentDataType.BYTE)
                && dataContainer.get(new NamespacedKey("minecraft", "LodestoneTracked"), PersistentDataType.BYTE) == 1;
    }

    /**
     * Returns the player's current coordinates as a string (X, Y, Z).
     *
     * @param player the player whose coordinates are being retrieved
     * @return the coordinates as a string
     */
    private String getPlayerCoordinates(Player player) {
        return player.getLocation().getBlockX() + " " +
                player.getLocation().getBlockY() + " " +
                player.getLocation().getBlockZ();
    }

    /**
     * Gets the biome name of the block the player is standing on.
     *
     * @param player the player whose biome is being checked
     * @return the biome name as a string
     */
    private String getBiomeName(Player player) {
        Biome biome = player.getLocation().getBlock().getBiome();
        return biome.name().replace("_", " ").toLowerCase();
    }

    /**
     * Converts the player's yaw to a corresponding cardinal or intercardinal direction.
     *
     * @param player the player whose yaw is being checked
     * @return the cardinal/intercardinal direction based on the player's yaw
     */
    public static String yawToFace(Player player) {
        float rotation = (player.getLocation().getYaw() % 360 + 360) % 360; // Normalize the yaw to a positive value (0-360)

        // Divide the 360° into 8 segments, each representing a direction
        String[] directions = {"N", "NE", "E", "SE", "S", "SW", "W", "NW"};
        int index = Math.round(rotation / 45); // Each segment is 45 degrees

        return directions[index % 8]; // Wrap around if index >= 8
    }
}
