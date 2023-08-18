package com.fameless.servermanager.listener;

import com.fameless.servermanager.ServerManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class FreezeListener implements Listener {

    private ServerManager serverManager;

    public FreezeListener(ServerManager serverManager) {
        this.serverManager = serverManager;
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerMove(PlayerMoveEvent event) {
        if (serverManager.getFreezedPlayers().contains(event.getPlayer())) event.setCancelled(true);
    }
}
