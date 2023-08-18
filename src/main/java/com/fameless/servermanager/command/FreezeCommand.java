package com.fameless.servermanager.command;

import com.fameless.servermanager.Configuration;
import com.fameless.servermanager.ServerManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FreezeCommand implements CommandExecutor {

    private ServerManager serverManager;

    public FreezeCommand(ServerManager serverManager) {
        this.serverManager = serverManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender.hasPermission("server.freeze")) {
            if (args.length == 1) {
                if (Bukkit.getPlayerExact(args[0]) != null) {
                    Player target = Bukkit.getPlayer(args[0]);
                    serverManager.getFreezedPlayers().add(target);
                    target.sendMessage(Configuration.messagePrefix() + Configuration.getFreezeMessage());
                    sender.sendMessage(Configuration.messagePrefix() + ChatColor.GREEN + "Player has been frozen.");
                } else {
                    sender.sendMessage(Configuration.messagePrefix() + Configuration.getPlayerNotFoundMessage());
                }
            }

        } else {
            serverManager.sendPermissionMessage(sender, "server.freeze");
        }


        return false;
    }
}
