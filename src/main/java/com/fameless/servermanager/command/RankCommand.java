package com.fameless.servermanager.command;

import com.fameless.servermanager.Configuration;
import com.fameless.servermanager.ServerManager;
import com.fameless.servermanager.rank.Rank;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;

public class RankCommand implements CommandExecutor {

    private final ServerManager serverManager;

    public RankCommand(ServerManager serverManager) {
        this.serverManager = serverManager;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        if (!commandSender.hasPermission("server.ranks")) {
            serverManager.sendPermissionMessage(commandSender, "server.ranks");
            return false;
        }
        if (args.length == 3) {
            if (args[0].equalsIgnoreCase("set")) {
                if (Bukkit.getPlayerExact(args[1]) != null) {
                    Player player = Bukkit.getPlayer(args[1]);
                    for (Rank rank : Rank.values()) {
                        if (rank.name().equalsIgnoreCase(args[2])) {
                            try {
                                serverManager.getRankManager().setRank(player, rank);
                                commandSender.sendMessage(Configuration.messagePrefix() + ChatColor.RESET + ChatColor.GREEN + "Success! " + player.getName() + "'s rank was set to " + rank.name().toLowerCase() + ".");
                                return false;
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                } else {
                    commandSender.sendMessage(Configuration.messagePrefix() + ChatColor.RESET + ChatColor.RED + "Player is not online! If you have access to the server files and know the player's UUID, you can change the players rank manually there.");
                }
            } else {
                commandSender.sendMessage(Configuration.messagePrefix() + ChatColor.RESET + ChatColor.RED + "Invalid usage! Please use: /rank set <player> <rank>");
            }
        } else {
            commandSender.sendMessage(Configuration.messagePrefix() + ChatColor.RESET + ChatColor.RED + "Invalid usage! Please use: /rank set <player> <rank>");
        }



        return false;
    }
}