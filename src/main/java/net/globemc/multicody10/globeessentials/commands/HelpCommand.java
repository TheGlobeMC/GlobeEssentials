package net.globemc.multicody10.globeessentials.commands;

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

import static net.globemc.multicody10.globeessentials.utils.GlobeEssentialsUtils.globePrefix;

public class HelpCommand implements CommandExecutor {
    Plugin plugin;

    public HelpCommand(Plugin plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player){
            Player player = ((Player) sender).getPlayer();
            assert player != null;
            TextComponent message = new TextComponent(globePrefix + ChatColor.AQUA + "For help on the server, you may click this link here!");
            message.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://globemc.net/wiki/index.php/Help"));
            message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.DARK_AQUA + "Help Page")));
            player.spigot().sendMessage(message);
            return true;
        }
        return false;
    }
}