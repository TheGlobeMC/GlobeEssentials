package net.globemc.multicody10.globeessentials.chatfilter.events;

import net.globemc.multicody10.globeessentials.Main;
import net.globemc.multicody10.globeessentials.chatfilter.data.DataManager;
import net.globemc.multicody10.globeessentials.chatfilter.filter.Filter;
import net.globemc.multicody10.globeessentials.chatfilter.filter.Spam;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.List;
import java.util.Set;

public class Events implements Listener {
    private final Main plugin;
    private final Filter chatFilter;
    private final DataManager dataManager;
    private final Spam antiSpam;

    public Events(Main plugin, Filter chatFilter, DataManager dataManager, Spam antiSpam) {
        this.plugin = plugin;
        this.chatFilter = chatFilter;
        this.dataManager = dataManager;
        this.antiSpam = antiSpam;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        dataManager.loadPlayer(event.getPlayer());
        antiSpam.addChatPlayer(event.getPlayer());
        antiSpam.addCommandPlayer(event.getPlayer());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        dataManager.savePlayerFilter(event.getPlayer());
        dataManager.removePlayer(event.getPlayer());
        antiSpam.removeChatPlayer(event.getPlayer());
        antiSpam.removeCommandPlayer(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        if (!event.isCancelled()) {
            if (plugin.getConfig().getBoolean("settings.replaceMessage")) {
                event.setMessage(chatFilter.replace(event.getMessage()));
            }
            if (plugin.getConfig().getBoolean("settings.replaceFormat")) {
                event.setFormat(chatFilter.replace(event.getFormat()));
            }

            String message = event.getMessage();
            String format = event.getFormat();
            if (plugin.getConfig().getBoolean("settings.filterMessage")) {
                message = chatFilter.filter(message);
            }
            if (plugin.getConfig().getBoolean("settings.filterFormat")) {
                format = chatFilter.filter(format);
            }

            Set<Player> recipients = dataManager.getFilterRecipients(event.getRecipients());
            chatFilter.sendChat(event.getPlayer(), message, format, recipients);
            event.getRecipients().removeAll(recipients);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerChatAntiSpam(AsyncPlayerChatEvent event) {
        if (event.isCancelled() ||
                event.getPlayer().hasPermission("chatfilter.bypass")) {
            return;
        }
        long chatDelay = (long) plugin.getConfig().getInt("settings.chatDelay") * 1000;
        if (chatDelay <= 0) {
            return;
        }
        if (antiSpam.hasPlayerChat(event.getPlayer())) {
            Long currentTime = System.currentTimeMillis();
            Long chatTime = antiSpam.getChatTime(event.getPlayer());
            double timeLeft = (double) (chatTime - currentTime + chatDelay) / 1000;
            if (timeLeft <= 0.00) {
                antiSpam.addChatPlayer(event.getPlayer());
                return;
            }
            String chatDelayMessage = plugin.getConfig().getString("settings.chatDelayMessage")
                    .replace("$time", String.valueOf(Math.round(timeLeft * 100.0) / 100.0))
                    .replace("&", "§");
            if (!chatDelayMessage.isEmpty()) {
                event.getPlayer().sendMessage(chatDelayMessage);
            }
            event.setCancelled(true);
        } else {
            antiSpam.addChatPlayer(event.getPlayer());
        }
    }

    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        if (event.getPlayer().hasPermission("chatfilter.bypass")) {
            return;
        }
        long commandDelay = (long) plugin.getConfig().getInt("settings.commandDelay") * 1000;
        if (commandDelay <= 0) {
            return;
        }
        if (antiSpam.hasPlayerCommand(event.getPlayer())) {
            Long currentTime = System.currentTimeMillis();
            Long commandTime = antiSpam.getCommandTime(event.getPlayer());
            double timeLeft = (double) (commandTime - currentTime + commandDelay) / 1000;
            if (timeLeft <= 0.00) {
                antiSpam.addCommandPlayer(event.getPlayer());
                return;
            }
            String commandDelayMessage = plugin.getConfig().getString("settings.commandDelayMessage")
                    .replace("$time", String.valueOf(Math.round(timeLeft * 100.0) / 100.0))
                    .replace("&", "§");
            if (!commandDelayMessage.isEmpty()) {
                event.getPlayer().sendMessage(commandDelayMessage);
            }
            event.setCancelled(true);
        } else {
            antiSpam.addCommandPlayer(event.getPlayer());
        }
    }

    @EventHandler
    public void onPlayerAdvertise(AsyncPlayerChatEvent event) {
        if (event.getPlayer().hasPermission("chatfilter.bypass")) {
            return;
        }
        List<String> regEx = plugin.getConfig().getStringList("settings.advertiseRegEx");
        for (String match : regEx) {
            if (event.getMessage().matches(match)) {
                event.setCancelled(true);
                String advertiseMessage = plugin.getConfig().getString("settings.advertiseMessage")
                        .replace("&", "§");
                if (!advertiseMessage.isEmpty()) {
                    event.getPlayer().sendMessage(advertiseMessage);
                }
                String advertiseStaff = plugin.getConfig().getString("settings.advertiseStaff")
                        .replace("$player", event.getPlayer().getName())
                        .replace("$message", event.getMessage()).replace("&", "§");
                if (!advertiseStaff.isEmpty()) {
                    for (Player player : plugin.getServer().getOnlinePlayers()) {
                        if (player.hasPermission("chatfilter.staff")) {
                            player.sendMessage(advertiseStaff);
                        }
                    }
                }
                break;
            }
        }
    }

    @EventHandler
    public void onPlayerCaps(AsyncPlayerChatEvent event) {
        if (event.getPlayer().hasPermission("chatfilter.bypass")) {
            return;
        }
        char[] charArray = event.getMessage().toCharArray();
        int counter = 0;
        for (char c : charArray) {
            if (Character.isUpperCase(c)) {
                counter++;
            }
        }
        int capsAmount = plugin.getConfig().getInt("settings.capsAmount");
        if (counter >= capsAmount) {
            event.setMessage(event.getMessage().toLowerCase());
            String capsMessage = plugin.getConfig().getString("settings.capsMessage");
            if (capsMessage != null && !capsMessage.isEmpty()) {
                event.getPlayer().sendMessage(capsMessage.replace("&", "§"));
            }
        }
    }
}