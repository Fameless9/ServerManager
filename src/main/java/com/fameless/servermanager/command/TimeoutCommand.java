package com.fameless.servermanager.command;

import com.fameless.servermanager.Configuration;
import com.fameless.servermanager.ServerManager;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

public class TimeoutCommand implements CommandExecutor, Listener {

    private final ServerManager serverManager;

    public TimeoutCommand(ServerManager serverManager) {
        this.serverManager = serverManager;
        start();
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        if (!commandSender.hasPermission("server.timeout")) {
            serverManager.sendPermissionMessage(commandSender, "server.timeout");
            return false;
        }
        if (args.length == 2) {
            if (Bukkit.getPlayerExact(args[0]) != null) {
                Player player = Bukkit.getPlayer(args[0]);
                int time;
                try {
                    time = Integer.parseInt(args[1]);
                } catch (NumberFormatException e) {
                    commandSender.sendMessage(Configuration.messagePrefix() + ChatColor.RESET + ChatColor.RED + "Argument 2 must be a number.");
                    return false;
                }
                serverManager.getMuteManager().setMuteTime(player, time);
                serverManager.getMuteManager().setMuted(player, true);
                serverManager.getHideActionbarMap().put(player.getUniqueId(), false);
                commandSender.sendMessage(Configuration.messagePrefix() + ChatColor.RESET + ChatColor.GREEN + "Player " + player.getName() + " is timeouted for " + time + " seconds. Use /unmute " + player.getName() + " to unmute them.");
                player.sendMessage(Configuration.messagePrefix() + ChatColor.RESET + ChatColor.RED + "You were timeouted by a moderator for " + time + " seconds.\n" +
                        Configuration.messagePrefix() + ChatColor.RESET + ChatColor.GRAY + "To toggle the Actionbar on and off, do /hideab.");
            } else {
                commandSender.sendMessage(Configuration.messagePrefix() + ChatColor.RESET + ChatColor.RED + "Player couldn't be found!");
            }
        } else {
            commandSender.sendMessage(Configuration.messagePrefix() + ChatColor.RESET + ChatColor.RED  + "Please use: /timeout <player> <seconds>");
        }
        return false;
    }
    public void start() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (!serverManager.getMuteManager().getConfiguration().contains(player.getUniqueId().toString())) {
                        continue;
                    }
                    if (!serverManager.getMuteManager().getMuted(player)) {
                        continue;
                    }
                    int time = serverManager.getMuteManager().getMuteTime(player.getUniqueId());
                    if (time == -1 && serverManager.getMuteManager().getMuted(player) && !serverManager.getHideActionbarMap().get(player.getUniqueId())) {
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.RED + "You are permanently muted | /hideab"));
                    }
                    if (time >= 0) {
                        int newTime = time - 1;
                        serverManager.getMuteManager().setMuteTime(player,newTime);
                        if (!serverManager.getHideActionbarMap().get(player.getUniqueId())) {
                            sendActionbar(player, newTime);
                        }
                        if (newTime % 60 == 0 && newTime != 0) {
                            if (newTime / 60 == 1) {
                                player.sendMessage(Configuration.messagePrefix() + ChatColor.RESET + ChatColor.RED + "Your timeout will last another: 1 minute.");
                            } else {
                                player.sendMessage(Configuration.messagePrefix() + ChatColor.RESET + ChatColor.RED + "Your timeout will last another " + newTime / 60 + " minutes.");
                            }
                        }
                        if (newTime == 0) {
                            player.sendMessage(ChatColor.GREEN + "You are now unmuted.");
                            serverManager.getMuteManager().setMuted(player, false);
                        }
                    }
                }
            }
        }.runTaskTimer(serverManager, 0, 20);
    }
    private void sendActionbar(Player player, int time) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(Configuration.messagePrefix() + ChatColor.RESET + ChatColor.RED + "You are muted for " + time + "s"));
    }
}