package com.fameless.servermanager.command;

import com.fameless.servermanager.Configuration;
import com.fameless.servermanager.ServerManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class UnfreezeCommand implements CommandExecutor {

    private ServerManager serverManager;

    public UnfreezeCommand(ServerManager serverManager) {
        this.serverManager = serverManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1) {
            if (!sender.hasPermission("server.freeze")) {
                serverManager.sendPermissionMessage(sender, "server.freeze");
                return false;
            }
            if (Bukkit.getPlayerExact(args[0]) != null) {
                Player target = Bukkit.getPlayer(args[0]);
                serverManager.getFreezedPlayers().remove(target);
                target.sendMessage(Configuration.getUnfreezeMessage());
                sender.sendMessage(ChatColor.GREEN + "Player has been unfrozen.");
            } else {
                sender.sendMessage(Configuration.getPlayerNotFoundMessage());
            }
        }
        return false;
    }
}
