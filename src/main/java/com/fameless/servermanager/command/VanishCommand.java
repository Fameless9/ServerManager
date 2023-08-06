package com.fameless.servermanager.command;

import com.fameless.servermanager.Configuration;
import com.fameless.servermanager.ServerManager;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class VanishCommand implements CommandExecutor {

    private ServerManager serverManager;
    private List<UUID> vanishedPlayers = new ArrayList<>();

    public VanishCommand(ServerManager serverManager) {
        this.serverManager = serverManager;
        run();
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        if (!(commandSender instanceof Player)) return false;
        Player player = (Player) commandSender;
        if (player.hasPermission("server.vanish")) {
            if (vanishedPlayers.contains(player.getUniqueId())) {
                vanishedPlayers.remove(player.getUniqueId());
                player.showPlayer(serverManager, player);
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(" "));
                player.sendMessage(Configuration.messagePrefix() + ChatColor.RESET + ChatColor.GREEN + "You are now visible to other players.");
            } else {
                vanishedPlayers.add(player.getUniqueId());
                player.hidePlayer(serverManager, player);
                player.sendMessage(ChatColor.GREEN + "You are now vanished.");
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.RED + "You are currently vanished."));
            }
        } else {
            serverManager.sendPermissionMessage(commandSender, "server.vanish");
        }
        return false;
    }
    private void run() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (int i = 0; i < vanishedPlayers.size(); i++) {
                    Player player = Bukkit.getPlayer(vanishedPlayers.get(i));
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.RED + "You are currently vanished."));
                }
            }
        }.runTaskTimer(serverManager, 0,20);
    }
}