package net.globemc.multicody10.globeessentials.chatfilter.filter;

import net.globemc.multicody10.globeessentials.Main;
import org.bukkit.entity.Player;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Filter {
    private final Main plugin;
    private final List<String> wordList = new ArrayList<>();

    public Filter(Main plugin) {
        this.plugin = plugin;
        createWords();
        loadWords();
    }

    public String filter(String message) {
        boolean filterIgnoreCase = plugin.getConfig().getBoolean("settings.filterIgnoreCase");
        for (String word : wordList) {
            if (message.toLowerCase().contains(word.toLowerCase())) {
                if (filterIgnoreCase) {
                    message = message.replaceAll("(?i)" + word, star(word.length()));
                } else {
                    message = message.replaceAll(word, star(word.length()));
                }
            }
        }
        return message;
    }

    public String replace(String message) {
        if (!plugin.getConfig().isConfigurationSection("settings.replaceText")) {
            return message;
        }
        boolean replaceIgnoreCase = plugin.getConfig().getBoolean("settings.replaceIgnoreCase");
        for (String original : Objects.requireNonNull(plugin.getConfig().getConfigurationSection("settings.replaceText")).getKeys(false)) {
            if (message.toLowerCase().contains(original.toLowerCase())) {
                String replace = plugin.getConfig().getString("settings.replaceText." + original);
                if (replaceIgnoreCase) {
                    assert replace != null;
                    message = message.replaceAll("(?i)" + original, replace);
                } else {
                    assert replace != null;
                    message = message.replaceAll(original, replace);
                }
                message = message.replaceAll(original, replace);
            }
        }
        return message;
    }

    public void sendChat(Player sender, String message, String format, Set<Player> players) {
        String sendMessage = format.replace("%1$s", sender.getDisplayName())
                .replace("%2$s", message);
        for (Player player : players) {
            player.sendMessage(sendMessage);
        }
    }

    public String star(Integer size) {
        StringBuilder star = new StringBuilder();
        int counter = 0;
        while (counter < size) {
            star.append("*");
            counter++;
        }
        return star.toString();
    }

    public void createWords() {
        File file = new File(plugin.getDataFolder(), "words" + File.separator + "en.txt");
        if (!file.exists()) {
            plugin.saveResource("words" + File.separator + "en.txt", true);
        }
    }

    public void loadWords() {
        wordList.clear();
        File folder = new File(plugin.getDataFolder(), "words");
        File[] listOfFiles = folder.listFiles();
        assert listOfFiles != null;
        for (File file : listOfFiles) {
            BufferedReader reader;
            try {
                reader = new BufferedReader(new FileReader(file.getPath()));
                String line = reader.readLine();
                while (line != null) {
                    wordList.add(line.toLowerCase());
                    line = reader.readLine();
                }
                reader.close();
            } catch (IOException e) {
            }
        }
    }
}