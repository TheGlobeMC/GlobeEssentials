package net.globemc.multicody10.globeessentials.compass;

import net.globemc.multicody10.globeessentials.Main;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CompassMeta;

public class CompassUI implements Listener {
    public CompassUI(Main plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }
    public static String yawToFace(Player player) {
        double rotation = player.getLocation().getYaw() - 180;
        if (rotation < 0) {
            rotation += 360.0;
        }
        if (0 <= rotation && rotation < 22.5) return "N";
        if (22.5 <= rotation && rotation < 67.5) return "NE";
        if (67.5 <= rotation && rotation < 112.5) return "E";
        if (112.5 <= rotation && rotation < 157.5) return "SE";
        if (157.5 <= rotation && rotation < 202.5) return "S";
        if (202.5 <= rotation && rotation < 247.5) return "SW";
        if (247.5 <= rotation && rotation < 292.5) return "W";
        if (292.5 <= rotation && rotation < 337.5) return "NW";
        if (337.5 <= rotation && rotation <= 360) return "N";
        return null;
    }
    private String getBiomeName(Player player) {
        Biome biome = (player.getLocation().getBlock().getBiome());
        if (biome == Biome.OCEAN) return "Ocean";
        if (biome == Biome.DEEP_OCEAN) return "Deep Ocean";
        if (biome == Biome.WARM_OCEAN) return "Warm Ocean";
        if (biome == Biome.LUKEWARM_OCEAN) return "Lukewarm Ocean";
        if (biome == Biome.DEEP_LUKEWARM_OCEAN) return "Deep Lukewarm Ocean";
        if (biome == Biome.COLD_OCEAN) return "Cold Ocean";
        if (biome == Biome.DEEP_COLD_OCEAN) return "Deep Cold Ocean";
        if (biome == Biome.FROZEN_OCEAN) return "Frozen Ocean";
        if (biome == Biome.DEEP_FROZEN_OCEAN) return "Deep Frozen Ocean";
        if (biome == Biome.MUSHROOM_FIELDS) return "Mushroom Fields";
        if (biome == Biome.JAGGED_PEAKS) return "Jagged Peaks";
        if (biome == Biome.FROZEN_PEAKS) return "Frozen Peaks";
        if (biome == Biome.STONY_PEAKS) return "Stony Peaks";
        if (biome == Biome.MEADOW) return "Meadow";
        if (biome == Biome.CHERRY_GROVE) return "Cherry Grove";
        if (biome == Biome.GROVE) return "Grove";
        if (biome == Biome.SNOWY_SLOPES) return "Snowy Slopes";
        if (biome == Biome.WINDSWEPT_HILLS) return "Windswept Hills";
        if (biome == Biome.WINDSWEPT_GRAVELLY_HILLS) return "Windswept Gravelly Hills";
        if (biome == Biome.WINDSWEPT_FOREST) return "Windswept Forest";
        if (biome == Biome.FOREST) return "Forest";
        if (biome == Biome.FLOWER_FOREST) return "Flower Forest";
        if (biome == Biome.TAIGA) return "Taiga";
        if (biome == Biome.OLD_GROWTH_PINE_TAIGA) return "Old Growth Pine Taiga";
        if (biome == Biome.OLD_GROWTH_SPRUCE_TAIGA) return "Old Growth Spruce Taiga";
        if (biome == Biome.SNOWY_TAIGA) return "Snowy Taiga";
        if (biome == Biome.BIRCH_FOREST) return "Birch Forest";
        if (biome == Biome.OLD_GROWTH_BIRCH_FOREST) return "Old Growth Birch Forest";
        if (biome == Biome.DARK_FOREST) return "Dark Forest";
        if (biome == Biome.JUNGLE) return "Jungle";
        if (biome == Biome.SPARSE_JUNGLE) return "Sparse Jungle";
        if (biome == Biome.BAMBOO_JUNGLE) return "Bamboo Jungle";
        if (biome == Biome.RIVER) return "River";
        if (biome == Biome.FROZEN_RIVER) return "Frozen River";
        if (biome == Biome.SWAMP) return "Swamp";
        if (biome == Biome.MANGROVE_SWAMP) return "Mangrove Swamp";
        if (biome == Biome.BEACH) return "Beach";
        if (biome == Biome.SNOWY_BEACH) return "Snowy Beach";
        if (biome == Biome.STONY_SHORE) return "Stony Shore";
        if (biome == Biome.PLAINS) return "Plains";
        if (biome == Biome.SUNFLOWER_PLAINS) return "Sunflower Plains";
        if (biome == Biome.SNOWY_PLAINS) return "Snowy Plains";
        if (biome == Biome.ICE_SPIKES) return "Ice Spikes";
        if (biome == Biome.DESERT) return "Desert";
        if (biome == Biome.SAVANNA) return "Savanna";
        if (biome == Biome.SAVANNA_PLATEAU) return "Savanna Plateau";
        if (biome == Biome.WINDSWEPT_SAVANNA) return "Windswept Savanna";
        if (biome == Biome.BADLANDS) return "Badlands";
        if (biome == Biome.WOODED_BADLANDS) return "Wooded Badlands";
        if (biome == Biome.ERODED_BADLANDS) return "Eroded Badlands";
        if (biome == Biome.DEEP_DARK) return "Deep Dark";
        if (biome == Biome.DRIPSTONE_CAVES) return "Dripstone Caves";
        if (biome == Biome.LUSH_CAVES) return "Lush Caves";
        if (biome == Biome.THE_VOID) return "The Void";
        if (biome == Biome.NETHER_WASTES) return "Nether Wastes";
        if (biome == Biome.SOUL_SAND_VALLEY) return "Soul Sand Valley";
        if (biome == Biome.CRIMSON_FOREST) return "Crimson Forest";
        if (biome == Biome.WARPED_FOREST) return "Warped Forest";
        if (biome == Biome.BASALT_DELTAS) return "Basalt Deltas";
        if (biome == Biome.THE_END) return "The End";
        if (biome == Biome.SMALL_END_ISLANDS) return "Small End Islands";
        if (biome == Biome.END_MIDLANDS) return "End Midlands";
        if (biome == Biome.END_HIGHLANDS) return "End Highlands";
        if (biome == Biome.END_BARRENS) return "End Barrens";
        return null;
    }

    @EventHandler
    public void OnPlayerCompass(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        ItemStack itemInHand = player.getInventory().getItemInMainHand();
        ItemStack itemInOffhand = player.getInventory().getItemInOffHand();
        ItemStack compass = new ItemStack(Material.COMPASS);
        CompassMeta compassMeta = (CompassMeta) compass.getItemMeta();
        if ((itemInHand.getType() == Material.COMPASS) || (itemInOffhand.getType() == Material.COMPASS)) {
            if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (player.hasCooldown(Material.COMPASS))
                    return;
                assert compassMeta != null;
                if (!compassMeta.hasLodestone()) {
                    if (player.getWorld().getEnvironment().equals(World.Environment.NORMAL)) {
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§7[ §f" + yawToFace(player) + " §7| §f" + player.getLocation().getBlockX() + " " + player.getLocation().getBlockY() + " " + player.getLocation().getBlockZ() + " §7| §f" + getBiomeName(player) + " §7]"));
                        player.setCooldown(Material.COMPASS, 80);
                    }
                    if (player.getWorld().getEnvironment().equals(World.Environment.NETHER)) {
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§4[ §c§kUnknown §4| §c" + getBiomeName(player) + " §4]"));
                        player.setCooldown(Material.COMPASS, 80);
                    }
                    if (player.getWorld().getEnvironment().equals(World.Environment.THE_END)) {
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§5[ §d§kUnknown §5| §d" + getBiomeName(player) + " §5]"));
                        player.setCooldown(Material.COMPASS, 80);
                    }
                } else if (compassMeta.hasLodestone()) {
                    if (player.getWorld().getEnvironment().equals(World.Environment.NORMAL)) {
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§7[ §fLodestone Paired! §7| §f" + getBiomeName(player) + " §7]"));
                        player.setCooldown(Material.COMPASS, 80);
                    }
                    if (player.getWorld().getEnvironment().equals(World.Environment.NETHER)) {
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§4[ §cLodestone Paired! §4| §c" + getBiomeName(player) + " §4]"));
                        player.setCooldown(Material.COMPASS, 80);
                    }
                    if (player.getWorld().getEnvironment().equals(World.Environment.THE_END)) {
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§5[ §dLodestone Paired! §5| §d" + getBiomeName(player) + " §5]"));
                        player.setCooldown(Material.COMPASS, 80);
                    }
                }
            }
        }
    }
}
