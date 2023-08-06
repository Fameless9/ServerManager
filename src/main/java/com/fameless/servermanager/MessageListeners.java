package com.fameless.servermanager;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class MessageListeners implements Listener {

    private ServerManager serverManager;

    public MessageListeners(ServerManager serverManager) {
        this.serverManager = serverManager;
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerJoin(PlayerJoinEvent event) {

        String joinMessage = serverManager.getConfig().getString("messages.join-message");
        String newJoinMessage = joinMessage.replace("{player}", event.getPlayer().getName());
        event.setJoinMessage(newJoinMessage);
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerQuit(PlayerQuitEvent event) {

        String quitMessage = serverManager.getConfig().getString("messages.quit-message");
        String newQuitMessage = quitMessage.replace("{player}", event.getPlayer().getName());
        event.setQuitMessage(newQuitMessage);
    }
}
