package com.fameless.servermanager.command;

import com.fameless.servermanager.ServerManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

public class RenameCommand implements CommandExecutor {

    private ServerManager serverManager;

    public RenameCommand(ServerManager serverManager) {
        this.serverManager = serverManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!sender.hasPermission("server.rename")) {
            serverManager.sendPermissionMessage(sender, "server.rename");
            return false;
        }
        if (args.length < 1) {
            sender.sendMessage(ChatColor.RED + "You need to spicify a new name!");
            return false;
        }
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.getItemInHand().getItemMeta() != null) {

                StringBuilder name = new StringBuilder();

                for (String s : args) {
                    name.append(s).append(" ");
                }

                ItemStack itemStack = player.getItemInHand();
                ItemMeta meta = itemStack.getItemMeta();
                meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', String.valueOf(name)));
                itemStack.setItemMeta(meta);
            } else {
                player.sendMessage(ChatColor.RED + "You have no item in your main hand.");
            }
        } else {
            sender.sendMessage(ChatColor.RED + "Only players can use this command!");
        }


        return false;
    }
}
