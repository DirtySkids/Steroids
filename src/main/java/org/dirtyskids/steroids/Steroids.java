package org.dirtyskids.steroids;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;

public final class Steroids extends JavaPlugin implements Listener {

    private RankManager rankManager;

    @Override
    public void onEnable() {
        getLogger().info("Steroids is enabled");
       Bukkit.getPluginManager().registerEvents(new FurnaceListener(), this);
       Bukkit.getPluginManager().registerEvents(this, this);
       Bukkit.getPluginManager().registerEvents(new OreXPListener(), this);
       getServer().getWorlds().forEach(world -> world.getPopulators().add(new OrePopulator()));
       new ScoreboardUpdater(this).runTaskTimer(this, 0L, 20L);
       saveDefaultConfig();
       rankManager = new RankManager(getConfig());

        getCommand("steroids").setExecutor(new SteroidsCommand(rankManager));
        getServer().getPluginManager().registerEvents(new ChatAndTabListener(rankManager), this);
        getServer().getPluginManager().registerEvents(new CommandBlockListener(rankManager), this);

    }

    @Override
    public @NotNull Path getDataPath() {
        return super.getDataPath();
    }

    @Override
    public void onDisable() {
        Bukkit.getScheduler().cancelTasks(this);
        saveConfig();
        getLogger().info("Steroids plugin Disabled!");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        setupScoreboard(player);
    }

    public void setupScoreboard(Player player) {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getNewScoreboard();

        // Deaths playtime & TPS
        Objective objective = board.registerNewObjective("info", "dummy", ChatColor.GOLD + "Player Stats");
        objective.setDisplaySlot(org.bukkit.scoreboard.DisplaySlot.SIDEBAR);

        Score deaths = objective.getScore(ChatColor.RED + "Deaths: ");
        deaths.setScore(player.getStatistic(org.bukkit.Statistic.DEATHS));

        /*Score playtime = objective.getScore(ChatColor.GREEN + "Playtime: ");
        playtime.setScore(0);*/

        Score tps = objective.getScore(ChatColor.BLUE + "TPS: ");
        tps.setScore(20);

        player.setScoreboard(board);
    }
}

class ScoreboardUpdater extends BukkitRunnable {
    private final JavaPlugin plugin;

    public ScoreboardUpdater(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            Scoreboard board = player.getScoreboard();
            Objective objective = board.getObjective("info");

            if (objective != null) {
                Score deaths = objective.getScore(ChatColor.RED + "Deaths: ");
                deaths.setScore(player.getStatistic(Statistic.DEATHS));

              /*  Score playtime = objective.getScore(ChatColor.GREEN + "Playtime: ");
                int timeOnline = (int) (player.getStatistic(org.bukkit.Statistic.PLAY_ONE_MINUTE) / 20L);
                playtime.setScore(timeOnline);*/

                Score tps = objective.getScore(ChatColor.BLUE + "TPS: ");
                double currentTps = Math.min(Bukkit.getTPS()[0], 20);  // Get the average TPS over the last minute
                tps.setScore((int) currentTps);
            }
        }
    }
}
