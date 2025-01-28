package net.globemc.multicody10.globeessentials.chatfilter.filter;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class Spam {
    private Map<Player, Long> chatList = new HashMap<>();
    private Map<Player, Long> commandList = new HashMap<>();

    public Boolean hasPlayerChat(Player player) {
        if (chatList.containsKey(player)) {
            return true;
        } else {
            return false;
        }
    }

    public Boolean hasPlayerCommand(Player player) {
        if (commandList.containsKey(player)) {
            return true;
        } else {
            return false;
        }
    }

    public Long getChatTime(Player player) {
        return chatList.get(player);
    }

    public Long getCommandTime(Player player) {
        return commandList.get(player);
    }

    public void addChatPlayer(Player player) {
        chatList.put(player, System.currentTimeMillis());
    }

    public void addCommandPlayer(Player player) {
        commandList.put(player, System.currentTimeMillis());
    }

    public void removeChatPlayer(Player player) {
        chatList.remove(player);
    }

    public void removeCommandPlayer(Player player) {
        commandList.remove(player);
    }
}