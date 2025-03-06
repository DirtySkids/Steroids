package org.dirtyskids.steroids;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.entity.Player;

public class CommandBlockListener implements Listener {
    private final RankManager rankManager;

    public CommandBlockListener(RankManager rankManager) {
        this.rankManager = rankManager;
    }

    @EventHandler
    public void onCommandPreprocess(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        if (!player.isOp() && !rankManager.isAdmin(player)) {
            String command = event.getMessage().split(" ")[0].substring(1);
            if (Bukkit.getPluginCommand(command).getPermission().equalsIgnoreCase("op")) {
                event.setCancelled(true);
                player.sendMessage(ChatColor.RED + "Hey, you're not allowed to use that!");
            }
        }
    }
}
