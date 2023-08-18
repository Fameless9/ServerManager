package com.fameless.servermanager.manager;

import com.fameless.servermanager.ServerManager;
import com.fameless.servermanager.rank.Rank;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

public class NametagManager {

    private final ServerManager serverManager;

    public NametagManager(ServerManager serverManager) {
        this.serverManager = serverManager;
        setupNametags();
    }

    public void setupNametags() {
        for (Rank rank : Rank.values()) {
            if (Bukkit.getScoreboardManager().getMainScoreboard().getTeam(rank.getOrderSymbol() + rank.name()) == null) {
                Team team = Bukkit.getScoreboardManager().getMainScoreboard().registerNewTeam(rank.getOrderSymbol() + rank.name());
                team.setPrefix(rank.getDisplay());
            }
        }
    }

    public void newTag(Player player) {
        Rank rank = serverManager.getRankManager().getRank(player);
        Team team = Bukkit.getScoreboardManager().getMainScoreboard().getTeam(rank.getOrderSymbol() + rank.name());

        if (team != null) {
            team.addEntry(player.getName());
        }
    }

    public void removeTag(Player player) {

        for (Player target : Bukkit.getOnlinePlayers()) {
            target.getScoreboard().getEntryTeam(player.getName()).removeEntry(player.getDisplayName());
        }
    }
}