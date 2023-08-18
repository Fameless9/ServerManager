package com.fameless.servermanager.command;

import com.fameless.servermanager.Configuration;
import com.fameless.servermanager.ServerManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class WhoIsCommand implements CommandExecutor {

    private ServerManager serverManager;

    public WhoIsCommand(ServerManager serverManager) {
        this.serverManager = serverManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (args.length >= 1) {

            StringBuilder name = new StringBuilder();
            for (String s : args) {
                name.append(s).append(" ");
            }
            if (!serverManager.getNicknamesMap().containsKey(String.valueOf(name))) {
                sender.sendMessage(ChatColor.RED + "Nickname entry wasn't found.");
                return false;
            }
            String realName = serverManager.getNicknamesMap().get(String.valueOf(name));
            sender.sendMessage(Configuration.messagePrefix() + ChatColor.RESET + ChatColor.GRAY + "Real name of " + name + "is " + realName);
        } else {
            sender.sendMessage(ChatColor.RED + "You need to specify a nickname.");
        }
        return false;
    }
}
