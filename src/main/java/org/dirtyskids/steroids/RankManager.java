package org.dirtyskids.steroids;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class RankManager {
    private final Set<UUID> adminPlayers = new HashSet<>();
    private final FileConfiguration config;

    public RankManager(FileConfiguration config) {
        this.config = config;
        loadRanks();
    }

    public void addAdmin(OfflinePlayer player) {
        adminPlayers.add(player.getUniqueId());
        config.set("admins", adminPlayers.stream().map(UUID::toString).toList());
        Bukkit.getPluginManager().getPlugin("Steroids").saveConfig();
    }

    public void removeAdmin(OfflinePlayer player) {
        adminPlayers.remove(player.getUniqueId());
        config.set("admins", adminPlayers.stream().map(UUID::toString).toList());
        Bukkit.getPluginManager().getPlugin("Steroids").saveConfig();
    }

    public boolean isAdmin(OfflinePlayer player) {
        return adminPlayers.contains(player.getUniqueId());
    }

    public void loadRanks() {
        adminPlayers.clear();
        if (config.contains("admins")) {
            for (String uuidStr : config.getStringList("admins")) {
                adminPlayers.add(UUID.fromString(uuidStr));
            }
        }
    }
}
