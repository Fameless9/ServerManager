package com.fameless.servermanager.command;

import com.fameless.servermanager.ServerManager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SpawnArmorStandPresetCommand implements CommandExecutor {

    private ServerManager serverManager;
    private Location location;

    public SpawnArmorStandPresetCommand(ServerManager serverManager) {
        this.serverManager = serverManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!sender.hasPermission("server.armorstandpreset")) {
            serverManager.sendPermissionMessage(sender, "server.armorstandpreset");
            return false;
        }
        if (sender instanceof Player) {

            Player player = (Player) sender;

            String[] customName = new String[]{
                    getLine(1),
                    getLine(2),
                    getLine(3),
                    getLine(4),
                    getLine(5),
                    getLine(6),
                    getLine(7),
                    getLine(8),
                    getLine(9),
                    getLine(10)
            };
            Location location = player.getLocation();
            for (String line : customName) {
                location.subtract(0,0.3,0);
                if (line != null) {
                    ArmorStand stand = (ArmorStand) player.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
                    stand.setInvulnerable(true);
                    stand.setInvisible(true);
                    stand.setGravity(false);
                    stand.setCustomNameVisible(true);
                    stand.setCustomName(line);
                }
            }

        } else {
            sender.sendMessage(ChatColor.RED + "Only players can use this command!");
        }
        return false;
    }
    private String getLine(int line) {
        if (serverManager.getConfig().getString("line" + line) == null) {
            return null;
        }
        if (serverManager.getConfig().getString("line" + line).isEmpty()) {
            return null;
        }
        return serverManager.getConfig().getString("line" + line);
    }
}
