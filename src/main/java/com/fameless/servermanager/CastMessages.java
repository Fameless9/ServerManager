package com.fameless.servermanager;

import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class CastMessages extends ListenerAdapter implements Listener {

    private ServerManager serverManager;

    public CastMessages(ServerManager serverManager) {
        this.serverManager = serverManager;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (serverManager.getConfig().getBoolean("discord-messages.casttomc")) {
            if (event.getAuthor().isBot()) return;
            if (event.getChannelType() == ChannelType.TEXT){
                TextChannel channel = (TextChannel) event.getChannel();
                if (channel.equals(serverManager.getJda().getTextChannelById(serverManager.getConfig().getString("discord-messages.casttomc-channel-id")))) {
                    Bukkit.broadcastMessage("\uE009 " + event.getAuthor().getName() + ChatColor.GRAY + ": " + event.getMessage().getContentRaw());
                }
            }
        }
    }
    @EventHandler
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        if (serverManager.getConfig().getBoolean("minecraft-messages.casttodc")) {
            TextChannel textChannel = serverManager.getJda().getTextChannelById(serverManager.getConfig().getString("minecraft-messages.casttodc-channel-id"));
            textChannel.sendMessage("[Minecraft] " + event.getPlayer().getName() + ": " + event.getMessage()).queue();
        }
    }
}
