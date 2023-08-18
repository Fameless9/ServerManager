package com.fameless.servermanager.command;

import com.fameless.servermanager.Configuration;
import com.fameless.servermanager.ServerManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class NickCommand implements CommandExecutor {

    private ServerManager serverManager;

    public NickCommand(ServerManager serverManager) {
        this.serverManager = serverManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!sender.hasPermission("server.nick")) {
            serverManager.sendPermissionMessage(sender, "server.nick");
            return false;
        }
        if (args.length >= 1) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                switch (args[0]) {
                    case "0":
                    case "clear":
                    case "remove":
                        resetName(player);
                        player.sendMessage(Configuration.messagePrefix() + ChatColor.RESET + ChatColor.GREEN + "Your nickname has been reset.");
                        break;
                    default:
                        StringBuilder name = new StringBuilder();

                        for (String s : args) {
                            name.append(s).append(" ");
                        }

                        player.setDisplayName(String.valueOf(name));
                        serverManager.getNicknamesMap().put(String.valueOf(name), player.getName());
                        player.sendMessage(Configuration.messagePrefix() + ChatColor.RESET + ChatColor.GREEN + "Your nickname has been set to " + name);
                        break;
                }
            } else {
                sender.sendMessage(ChatColor.RED + "Only players can use this command!");
            }
        } else {
            sender.sendMessage(ChatColor.RED + "Usage: /nick <nickname, 0, clear, remove>. Nickname may contain spaces.");
        }
        return false;
    }
    private void resetName(Player player) {
        player.setDisplayName(player.getName());
    }
}
