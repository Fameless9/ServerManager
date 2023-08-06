package com.fameless.servermanager;

public class Configuration {

    private static ServerManager serverManager;

    public Configuration(ServerManager serverManager) {
        this.serverManager = serverManager;
        initiateConfiguration();
    }

    private void initiateConfiguration() {

        // Messages
        if (!serverManager.getConfig().contains("server.message-prefix")) {
            serverManager.getConfig().set("server.message-prefix", "§8[§bServer§8]");
            serverManager.saveConfig();
        }
        if (!serverManager.getConfig().contains("messages.join-message")) {
            serverManager.getConfig().set("messages.join-message", "§bPlayer {player} joined the game.");
            serverManager.saveConfig();
        }
        if (!serverManager.getConfig().contains("messages.quit-message")) {
            serverManager.getConfig().set("messages.quit-message", "§cPlayer {player} left the game.");
            serverManager.saveConfig();
        }
        if (!serverManager.getConfig().contains("messages.permission-message")) {
            serverManager.getConfig().set("messages.permission-message", "§cLacking permission: {permission}");
            serverManager.saveConfig();
        }
        if (!serverManager.getConfig().contains("messages.reporting-not-configured-message")) {
            serverManager.getConfig().set("messages.reporting-not-configured-message", "§cReporting to Discord is not configured on this server.\n" +
                    "If you are an admin, configure a discord bot and moderation channel in the config.yml, or replace this message in the config.yml.");
        }

        // Discord related
        if (!serverManager.getConfig().contains("report-system.discord-token")) {
            serverManager.getConfig().set("report-system.discord-token", "");
            serverManager.saveConfig();
        }
        if (!serverManager.getConfig().contains("report-system.report-channel-id")) {
            serverManager.getConfig().set("report-system.report-channel-id", "");
            serverManager.saveConfig();
        }
        if (!serverManager.getConfig().contains("discord-messages.casttomc")) {
            serverManager.getConfig().set("discord-messages.casttomc", false);
            serverManager.saveConfig();
        }
        if (!serverManager.getConfig().contains("discord-messages.casttomc-channel-id")) {
            serverManager.getConfig().set("discord-messages.casttomc-channel-id", "");
            serverManager.saveConfig();
        }
        if (!serverManager.getConfig().contains("minecraft-messages.casttodc")) {
            serverManager.getConfig().set("minecraft-messages.casttodc", false);
            serverManager.saveConfig();
        }
        if (!serverManager.getConfig().contains("minecraft-messages.casttodc-channel-id")) {
            serverManager.getConfig().set("minecraft-messages.casttodc-channel-id", "");
            serverManager.saveConfig();
        }
    }
    public static String messagePrefix() {
        return serverManager.getConfig().getString("server.message-prefix") + " ";
    }
}