package com.fameless.servermanager.manager.managerlistener;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;

public class ResoucePackListener implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onPlayerJoin(PlayerJoinEvent event) {

        event.getPlayer().setResourcePack("https://drive.usercontent.google.com/download?id=126GkNa5YX4HW1gg8YEDNKXYiP-KAGyKX&export=download&authuser=1&confirm=t&uuid=b15f797e-0f1f-4a7a-9399-80b92aef2fad&at=AC2mKKSQvjR0xE5NfSKUO9N12zLH:1691103343829");
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerResourcePackStatus(PlayerResourcePackStatusEvent event) {

        if (event.getStatus().equals(PlayerResourcePackStatusEvent.Status.DECLINED)) {
            event.getPlayer().kickPlayer(ChatColor.RED.toString() + ChatColor.BOLD + "Resource pack couldn't be loaded!\n" +
                    ChatColor.BOLD + ChatColor.AQUA + "Make sure to allow resource packs.");
        }
        if (event.getStatus().equals(PlayerResourcePackStatusEvent.Status.FAILED_DOWNLOAD)) {
            event.getPlayer().kickPlayer(ChatColor.RED.toString() + ChatColor.BOLD + "Failed to download Resource pack!\n" +
                    "Try again or contact an admin.");
        }
    }
}