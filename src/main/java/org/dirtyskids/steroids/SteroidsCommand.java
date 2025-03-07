package org.dirtyskids.steroids;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SteroidsCommand implements CommandExecutor {
    private final RankManager rankManager;

    public SteroidsCommand(RankManager rankManager) {
        this.rankManager = rankManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.isOp()) {
            sender.sendMessage("You do not have permission to use this command.");
            return true;
        }

        if (args.length < 3 || !args[0].equalsIgnoreCase("rank")) {
            sender.sendMessage("Usage: /steroids rank add/remove <player>");
            return false;
        }

        String action = args[1];
        String playerName = args[2];
        OfflinePlayer targetPlayer = Bukkit.getOfflinePlayer(playerName);

        if (action.equalsIgnoreCase("add")) {
            rankManager.addAdmin(targetPlayer);
            sender.sendMessage("Added " + playerName + " as Admin.");
            if (targetPlayer.isOnline()) {
                targetPlayer.getPlayer().setOp(true);
            }
        } else if (action.equalsIgnoreCase("remove")) {
            rankManager.removeAdmin(targetPlayer);
            sender.sendMessage("Removed " + playerName + " as Admin.");
            if (targetPlayer.isOnline()) {
                targetPlayer.getPlayer().setOp(false);
            }
        } else {
            sender.sendMessage("Invalid action. Use 'add' or 'remove'.");
        }
        return true;
    }
}