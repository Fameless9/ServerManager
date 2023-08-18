package com.fameless.servermanager.command;

import com.fameless.servermanager.Configuration;
import com.fameless.servermanager.ServerManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MuteCommand implements CommandExecutor {

    private final ServerManager serverManager;

    public MuteCommand(ServerManager serverManager) {
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
                serverManager.getMuteManager().setMuted(player, true);
                serverManager.getMuteManager().setMuteTime(player, -1);
                player.sendMessage(Configuration.messagePrefix() + ChatColor.RESET + ChatColor.RED + "You are now permanently muted.");
                commandSender.sendMessage(Configuration.messagePrefix() + ChatColor.RESET + ChatColor.GREEN + player.getName() + " is now permanently muted.\nUse /unmute " + player.getName() + " to unmute them.\n" +
                        "Use /timeout " + player.getName() + " <seconds> to turn the mute into a timeout.");
                return false;
            } else {
                commandSender.sendMessage(Configuration.messagePrefix() + ChatColor.RESET + Configuration.getPlayerNotFoundMessage());
            }
        } else {
            commandSender.sendMessage(Configuration.messagePrefix() + ChatColor.RESET + ChatColor.RED + "/mute <player>");
        }
        return false;
    }
}
