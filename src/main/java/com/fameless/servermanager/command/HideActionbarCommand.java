package com.fameless.servermanager.command;

import com.fameless.servermanager.ServerManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HideActionbarCommand implements CommandExecutor {

    private final ServerManager serverManager;

    public HideActionbarCommand(ServerManager serverManager) {
        this.serverManager = serverManager;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            serverManager.getHideActionbarMap().put(player.getUniqueId(), !serverManager.getHideActionbarMap().get(player.getUniqueId()));
        }

        return false;
    }
}