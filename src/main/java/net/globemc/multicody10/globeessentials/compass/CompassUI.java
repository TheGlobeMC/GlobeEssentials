package net.globemc.multicody10.globeessentials.compass;

import net.globemc.multicody10.globeessentials.Main;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class CompassUI implements Listener {
    public CompassUI(Main plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }
    @EventHandler
    public void onPlayerCompass(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        ItemStack itemInHand = player.getInventory().getItemInMainHand();
        ItemStack itemInOffhand = player.getInventory().getItemInOffHand();
        String actionBarMessage = getActionBarMessage(player, itemInHand);
        if(e.getAction().equals(Action.RIGHT_CLICK_AIR)){
            if (itemInHand.getType() == Material.COMPASS || itemInOffhand.getType() == Material.COMPASS) {
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(actionBarMessage));
            }
        }
    }
    private String getActionBarMessage(Player player, ItemStack compass) {
        String biomeName = getBiomeName(player);
        String worldColor;
        String darkWorldColor;
        String worldName = player.getWorld().getEnvironment().name();

        // Determine the color and message based on the world
        switch (worldName) {
            case "NORMAL":
                worldColor = "§f";
                darkWorldColor = "§7";
                break;
            case "NETHER":
                worldColor = "§c";
                darkWorldColor = "§4";
                break;
            case "THE_END":
                worldColor = "§d";
                darkWorldColor = "§5";
                break;
            default:
                worldColor = "§f"; // Default color for unknown worlds
                darkWorldColor = "§7";
                break;
        }

        // Check if the compass is a lodestone compass
        if (isLodestoneCompass(compass)) {
            return darkWorldColor + "[ " + worldColor + "Lodestone Paired! " + darkWorldColor + "| " + worldColor + biomeName + darkWorldColor + " ]";
        } else {
            // If not a lodestone compass, show player info
            if (player.getWorld().getEnvironment().equals(World.Environment.NORMAL)) {
                String direction = yawToFace(player);
                return darkWorldColor + "[ " + worldColor + direction + darkWorldColor + " | " + worldColor + getPlayerCoordinates(player) + darkWorldColor + " | " + worldColor + biomeName + darkWorldColor +" ]";
            } else if (player.getWorld().getEnvironment().equals(World.Environment.NETHER) || player.getWorld().getEnvironment().equals(World.Environment.THE_END)){
                return darkWorldColor + "[ " + worldColor + "§kUnknown " + darkWorldColor + "| " + worldColor + biomeName + darkWorldColor +" ]";
            }
        }
        return biomeName;
    }
    private boolean isLodestoneCompass(ItemStack item) {
        if (!item.hasItemMeta()) return false;
        PersistentDataContainer dataContainer = item.getItemMeta().getPersistentDataContainer();
        return dataContainer.has(new NamespacedKey("minecraft", "lodestone_tracked"), PersistentDataType.BYTE)
                && dataContainer.get(new NamespacedKey("minecraft", "lodestone_tracked"), PersistentDataType.BYTE) == 1;
    }
    private String getPlayerCoordinates(Player player) {
        return player.getLocation().getBlockX() + " " +
                player.getLocation().getBlockY() + " " +
                player.getLocation().getBlockZ();
    }
    private String getBiomeName(Player player) {
        Biome biome = player.getLocation().getBlock().getBiome();
        return capitalizeWords(biome.name().toLowerCase().replace("_", " "));
    }
    private String capitalizeWords(String input) {
        StringBuilder formatted = new StringBuilder();
        for (String word : input.split(" ")) {
            formatted.append(Character.toUpperCase(word.charAt(0)))
                    .append(word.substring(1))
                    .append(" ");
        }
        return formatted.toString().trim();
    }
    public static String yawToFace(Player player) {
        float rotation = (player.getLocation().getYaw() % 360 + 360) % 360; // Normalize the yaw to a positive value (0-360)

        // Divide the 360° into 8 segments, each representing a direction
        String[] directions = {"S", "SW", "W", "NW", "N", "NE", "E", "SE"};
        int index = Math.round(rotation / 45); // Each segment is 45 degrees

        return directions[index % 8]; // Wrap around if index >= 8
    }
}
