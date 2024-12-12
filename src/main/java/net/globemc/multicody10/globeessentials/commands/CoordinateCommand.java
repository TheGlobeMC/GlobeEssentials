package net.globemc.multicody10.globeessentials.commands;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import static net.globemc.multicody10.globeessentials.utils.GlobeEssentialsUtils.globePrefix;

// The code for this partially recycles from 0xBit's Coordinator plugin, so credit where it's due.
public class CoordinateCommand implements CommandExecutor {
    Plugin plugin;

    public CoordinateCommand(Plugin plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            int scale = 5120, tiles = 10, x, z;
            double lat, lng;
            if (args.length > 1) {
                try {
                    String latInput = args[0].replaceAll("[^\\d.-]", "");
                    String lngInput = args[1].replaceAll("[^\\d.-]", "");
                    lat = Double.parseDouble(latInput);
                    lng = Double.parseDouble(lngInput);
                } catch (NumberFormatException e) {
                    player.sendMessage(ChatColor.RED + "Please specify numbers for Latitude and Longitude.");
                    return false;
                }
                x = (int) Math.round(lng * scale / tiles);
                z = (int) (-1 * Math.round(lat * scale / tiles));

                player.sendMessage(ChatColor.DARK_GREEN + "======= " + ChatColor.GREEN + "Coordinate" + ChatColor.DARK_GREEN + " =======");
                player.sendMessage(ChatColor.GOLD + "X: " + ChatColor.YELLOW + x + ChatColor.GOLD + " Z: " + ChatColor.YELLOW + z);
                if (player.hasPermission("globeessentials.admin")) {
                    TextComponent teleportTo = new TextComponent("§3[§bTeleport§3]");
                    teleportTo.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§aTeleports to X: " + x + ", Y: 255, Z: " + z).create()));
                    teleportTo.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tp " + player.getName() + " " + x + " 255 " + z));
                    player.sendMessage(teleportTo);
                }
                player.sendMessage(ChatColor.DARK_GREEN + "==========================");
            } else {
                player.sendMessage("§4Please specify the §clatitude §4and §clongitude §4in decimal form");
                player.sendMessage("§4Correct Usage: [§c/coordinate <latitude> <longitude>§4]");
            }
        } else {
            Bukkit.getLogger().info(globePrefix + "You need to be a player to execute this command.");
        }
        return false;
    }
}