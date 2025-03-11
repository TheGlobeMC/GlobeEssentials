package net.globemc.multicody10.globeessentials.chatfilter.commands;

import net.globemc.multicody10.globeessentials.Main;
import net.globemc.multicody10.globeessentials.chatfilter.data.DataManager;
import net.globemc.multicody10.globeessentials.chatfilter.filter.Filter;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ChatFilterCommand implements CommandExecutor {
    private final Main plugin;
    private final DataManager data;
    private final Filter filter;

    public ChatFilterCommand(Main plugin, DataManager data, Filter filter) {
        this.plugin = plugin;
        this.data = data;
        this.filter = filter;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (sender instanceof Player player) {
            String version = "1.5";
            String author = "Ranull (Original), VRILGQD + Multicody10 (Modified)";

            if (args.length < 1) {
                player.sendMessage(ChatColor.DARK_GRAY + "» " + ChatColor.GREEN + "Filter " + ChatColor.GRAY
                        + ChatColor.GRAY + "v" + version);
                player.sendMessage(ChatColor.GRAY + "/cf ON " + ChatColor.DARK_GRAY + "-" + ChatColor.RESET
                        + " Turn filter " + ChatColor.GREEN + "ON");
                player.sendMessage(ChatColor.GRAY + "/cf OFF " + ChatColor.DARK_GRAY + "-" + ChatColor.RESET
                        + " Turn filter " + ChatColor.RED + "OFF");
                if (sender.hasPermission("globeessentials.reload")) {
                    player.sendMessage(ChatColor.GRAY + "/cf reload " + ChatColor.DARK_GRAY + "-" + ChatColor.RESET
                            + " Reload plugin");
                }
                player.sendMessage(ChatColor.DARK_GRAY + "Author: " + ChatColor.GRAY + author);
                return true;
            }
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("on")) {
                    data.setPlayer(player, true);
                    String filterOn = plugin.getConfig().getString("settings.filterOn").replace("&", "§");
                    if (!filterOn.isEmpty()) {
                        player.sendMessage(filterOn);
                    }
                }
                if (args[0].equalsIgnoreCase("off")) {
                    data.setPlayer(player, false);
                    String filterOff = plugin.getConfig().getString("settings.filterOff").replace("&", "§");
                    if (!filterOff.isEmpty()) {
                        player.sendMessage(filterOff);
                    }
                }
                if (args[0].equalsIgnoreCase("reload")) {
                    if (player.hasPermission("globeessentials.reload")) {
                        plugin.reloadConfig();
                        filter.loadWords();
                        player.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.GRAY + "Filter"
                                + ChatColor.DARK_GRAY + "]" + ChatColor.RESET + " Config reloaded!");
                    } else {
                        player.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.GRAY + "Filter"
                                + ChatColor.DARK_GRAY + "]" + ChatColor.RESET + " No Permission!");
                    }
                }
            }
        } else {
            sender.sendMessage("This command can only be run by a player!");
        }
        return true;
    }
}