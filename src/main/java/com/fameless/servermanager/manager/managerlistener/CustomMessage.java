package com.fameless.servermanager.manager.managerlistener;

import com.fameless.servermanager.Configuration;
import com.fameless.servermanager.ServerManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class CustomMessage implements Listener {

    private final ServerManager serverManager;

    public CustomMessage(ServerManager serverManager) {
        this.serverManager = serverManager;
    }

    @EventHandler(ignoreCancelled = true)
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {

        event.setCancelled(true);

        if (serverManager.getMuteManager().getMuted(event.getPlayer())) {
            event.getPlayer().sendMessage(Configuration.messagePrefix() + ChatColor.RESET + ChatColor.RED + "Message wasn't sent because you are muted.");
            return;
        }

        String prefix = serverManager.getRankManager().getRank(event.getPlayer()).getDisplay();
        Bukkit.broadcastMessage(prefix + event.getPlayer().getDisplayName() + ChatColor.GRAY + ": " + event.getMessage());
    }
}
