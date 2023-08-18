package com.fameless.servermanager.command;

import com.fameless.servermanager.Configuration;
import com.fameless.servermanager.ServerManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UnmuteCommand implements CommandExecutor {

    private final ServerManager serverManager;

    public UnmuteCommand(ServerManager serverManager) {
        this.serverManager = serverManager;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        if (!commandSender.hasPermission("server.mute")) {
            serverManager.sendPermissionMessage(commandSender, "server.mute");
            return false;
        }

        if (args.length == 1) {
            if (Bukkit.getPlayerExact(args[0]) != null) {
                Player player = Bukkit.getPlayer(args[0]);
                serverManager.getMuteManager().setMuted(player, false);
                commandSender.sendMessage(Configuration.messagePrefix() + ChatColor.RESET + ChatColor.GREEN + player.getName() + " is now unmuted.");
                player.sendMessage(Configuration.messagePrefix() + ChatColor.RESET + ChatColor.GREEN + "You are now unmuted.");
            } else {
                commandSender.sendMessage(Configuration.messagePrefix() + ChatColor.RESET + Configuration.getPlayerNotFoundMessage());
            }
        } else {
            commandSender.sendMessage(Configuration.messagePrefix() + ChatColor.RESET + ChatColor.RED + "/unmute <player>");
        }
        return false;
    }
}
