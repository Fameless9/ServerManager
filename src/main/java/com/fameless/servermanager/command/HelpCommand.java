package com.fameless.servermanager.command;

import com.fameless.servermanager.ServerManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class HelpCommand implements CommandExecutor {

    private ServerManager serverManager;

    public HelpCommand(ServerManager serverManager) {
        this.serverManager = serverManager;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {


        if (!commandSender.hasPermission("server.help")) {
            serverManager.sendPermissionMessage(commandSender, "server.help");
            return false;
        }

        commandSender.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + "   Help:   \n" +
                ChatColor.RED + ChatColor.BOLD + "\nCommands:\n" +
                ChatColor.GRAY + "-" + ChatColor.AQUA + " /hideab -> hides the actiobar\n" +
                ChatColor.GRAY + "-" + ChatColor.AQUA + " /mute -> mute a player in chat\n" +
                ChatColor.GRAY + "-" + ChatColor.AQUA + " /perm -> configure player's permissions\n" +
                ChatColor.GRAY + "-" + ChatColor.AQUA + " /rank -> configure player's ranks\n" +
                ChatColor.GRAY + "-" + ChatColor.AQUA + " /timeout -> mute players for a set amount of time (sec)\n" +
                ChatColor.GRAY + "-" + ChatColor.AQUA + " /unmute -> unmute a player\n" +
                ChatColor.GRAY + "-" + ChatColor.AQUA + " /vanish -> hide from other players\n" +
                ChatColor.GRAY + "-" + ChatColor.AQUA + " /report -> report a player");


        return false;
    }
}