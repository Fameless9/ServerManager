package com.fameless.servermanager.manager.managerlistener;

import com.fameless.servermanager.ServerManager;
import com.fameless.servermanager.rank.Rank;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.IOException;

public class AssignRank implements Listener {

    private final ServerManager serverManager;

    public AssignRank(ServerManager serverManager) {
        this.serverManager = serverManager;
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerJoin(PlayerJoinEvent event) {

        if (serverManager.getRankManager().getConfiguration().contains(event.getPlayer().getUniqueId().toString())) {
            for (Rank rank : Rank.values()) {
                if (rank.name().equals(serverManager.getRankManager().getConfiguration().get(event.getPlayer().getUniqueId().toString()))) {
                    try {
                        serverManager.getRankManager().setRank(event.getPlayer(), rank);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        if (serverManager.getRankManager().getRank(event.getPlayer()) == null) {
            try {
                serverManager.getRankManager().setRank(event.getPlayer(), Rank.GUEST);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        serverManager.getNametagManager().newTag(event.getPlayer());
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerQuit(PlayerQuitEvent event) {
        serverManager.getNametagManager().removeTag(event.getPlayer());
    }
}
