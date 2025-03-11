package net.globemc.multicody10.globeessentials.chatfilter.api;

import net.globemc.multicody10.globeessentials.chatfilter.data.DataManager;
import net.globemc.multicody10.globeessentials.chatfilter.filter.Filter;
import net.globemc.multicody10.globeessentials.chatfilter.filter.Spam;
import org.bukkit.entity.Player;

// The code for this is from Ranull's ChatFilter plugin, so credit where it's due.
public class ChatFilterAPI {
    private final Filter filter;
    private final DataManager dataManager;

    public ChatFilterAPI(Filter filter, Spam spam, DataManager dataManager) {
        this.filter = filter;
        this.dataManager = dataManager;
    }

    public String filterString(String string) {
        return filter.filter(string);
    }

    public String replaceString(String string) {
        return filter.replace(string);
    }

    public boolean getPlayerSetting(Player player) {
        return dataManager.getPlayerSetting(player);
    }
}
