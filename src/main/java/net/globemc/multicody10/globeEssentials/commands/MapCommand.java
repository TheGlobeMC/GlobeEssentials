package net.globemc.multicody10.globeEssentials.commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import static net.globemc.multicody10.globeEssentials.utils.GlobeEssentialsUtils.mapPrefix;

public class MapCommand implements CommandExecutor {
    Plugin plugin;

    public MapCommand(Plugin plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player){
            Player player = ((Player) sender).getPlayer();
            assert player != null;
            TextComponent message = new TextComponent(mapPrefix + ChatColor.LIGHT_PURPLE + "Here is your link!");
            message.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://map.globemc.net/#minecraft_overworld;flat;"
                    + player.getLocation().getBlockX() + "," + player.getLocation().getBlockY() + "," + player.getLocation().getBlockZ()
                    + ";8"));
            message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.DARK_GRAY + "Click to open the webmap in your browser at your location.")));
            player.spigot().sendMessage(message);
            return true;
        }
        return false;
    }
}