package com.fameless.servermanager.command;

import com.fameless.servermanager.Configuration;
import com.fameless.servermanager.ServerManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PermissionCommand implements CommandExecutor {

    private ServerManager serverManager;

    public PermissionCommand(ServerManager serverManager) {
        this.serverManager = serverManager;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {


        if (!commandSender.hasPermission("server.permissions")) {
            serverManager.sendPermissionMessage(commandSender, "server.permissions");
            return false;
        }
        if (args.length == 3) {
            if (Bukkit.getPlayerExact(args[1]) != null) {
                Player player = Bukkit.getPlayer(args[1]);
                switch (args[0]) {
                    case "grant":
                        serverManager.getPermissionManager().addPermission(player, args[2]);
                        commandSender.sendMessage(Configuration.messagePrefix() + ChatColor.RESET + ChatColor.GREEN + "Permission " + args[2] + " has been granted to player " + player.getName());
                        break;
                    case "remove":
                        serverManager.getPermissionManager().removePermission(player, args[2]);
                        commandSender.sendMessage(Configuration.messagePrefix() + ChatColor.RESET + ChatColor.GREEN + "Permission " + args[2] + " has been removed from player " + player.getName());
                        break;
                    default:
                        commandSender.sendMessage(Configuration.messagePrefix() + ChatColor.RESET + ChatColor.RED + "Please use: /perm <grant/remove> <player> <permission>");
                        break;
                }
            } else {
                commandSender.sendMessage(Configuration.messagePrefix() + ChatColor.RESET + Configuration.getPlayerNotFoundMessage());
            }
        } else {
            commandSender.sendMessage(Configuration.messagePrefix() + ChatColor.RESET + ChatColor.RED + "Please use: /perm <add/remove> <player> <permission>");
        }
        return false;
    }
}
