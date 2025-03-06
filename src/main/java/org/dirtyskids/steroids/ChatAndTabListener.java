package org.dirtyskids.steroids;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.entity.Player;

public class ChatAndTabListener implements Listener {
    private final RankManager rankManager;

    public ChatAndTabListener(RankManager rankManager) {
        this.rankManager = rankManager;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String rankPrefix = rankManager.isAdmin(player) ? ChatColor.RED + "[Admin] " : ChatColor.GREEN + "[Player] ";
        event.setFormat(rankPrefix + player.getName() + ": " + event.getMessage());
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String rankPrefix = rankManager.isAdmin(player) ? ChatColor.RED + "[Admin] " : ChatColor.GREEN + "[Player] ";
        player.setPlayerListName(rankPrefix + player.getName());
    }
}
