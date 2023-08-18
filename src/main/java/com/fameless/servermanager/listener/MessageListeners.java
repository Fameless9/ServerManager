package com.fameless.servermanager.listener;

import com.fameless.servermanager.Configuration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class MessageListeners implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onPlayerJoin(PlayerJoinEvent event) {

        String joinMessage = Configuration.messagePrefix() + Configuration.joinMessage();
        String newJoinMessage = joinMessage.replace("{player}", event.getPlayer().getName());
        event.setJoinMessage(newJoinMessage);
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerQuit(PlayerQuitEvent event) {

        String quitMessage = Configuration.messagePrefix() + Configuration.quitMessage();
        String newQuitMessage = quitMessage.replace("{player}", event.getPlayer().getName());
        event.setQuitMessage(newQuitMessage);
    }
}
