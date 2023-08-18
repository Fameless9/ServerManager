package com.fameless.servermanager.command;

import com.fameless.servermanager.ServerManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ArmorStandCommand implements CommandExecutor {

    private ServerManager serverManager;

    public ArmorStandCommand(ServerManager serverManager) {
        this.serverManager = serverManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!sender.hasPermission("server.armorstand")) {
            serverManager.sendPermissionMessage(sender, "server.armorstand");
            return false;
        }
        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "Please specify a hologram text.");
            return false;
        }
        if (sender instanceof Player) {

            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < args.length; i++) {
                if (i != 0) {
                    builder.append(" ");
                }
                builder.append(args[i]);
            }

            Player player = (Player) sender;

            ArmorStand stand = (ArmorStand) player.getWorld().spawnEntity(player.getLocation(), EntityType.ARMOR_STAND);
            stand.setInvisible(true);
            stand.setGravity(false);
            stand.setInvulnerable(true);

            stand.setCustomNameVisible(true);
            stand.setCustomName(ChatColor.translateAlternateColorCodes('&', String.valueOf(builder)));

        } else {
            sender.sendMessage(ChatColor.RED + "Only players can use this command!");
        }
        return false;
    }
}
