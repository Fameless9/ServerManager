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

public class SeeEnderchestCommand implements CommandExecutor {

    private ServerManager serverManager;

    public SeeEnderchestCommand(ServerManager serverManager) {
        this.serverManager = serverManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!sender.hasPermission("server.seeinv")) {
            serverManager.sendPermissionMessage(sender, "server.seeinv");
            return false;
        }
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can use this command!");
            return false;
        }
        if (args.length != 1) {
            sender.sendMessage(ChatColor.RED + "Please specify a player");
            return false;
        }
        if (Bukkit.getPlayerExact(args[0]) == null) {
            sender.sendMessage(Configuration.getPlayerNotFoundMessage());
            return false;
        }
        Player player = (Player) sender;
        Player target = Bukkit.getPlayer(args[0]);

        player.openInventory(target.getEnderChest());

        return false;
    }
}
