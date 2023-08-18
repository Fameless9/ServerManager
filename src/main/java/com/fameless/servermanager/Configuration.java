package com.fameless.servermanager;

public class Configuration {

    private static ServerManager serverManager;

    public Configuration(ServerManager serverManager) {
        Configuration.serverManager = serverManager;
    }

    public static String messagePrefix() {
        return serverManager.getConfig().getString("server.message-prefix") + " ";
    }
    public static  String joinMessage() {
        return serverManager.getConfig().getString("messages.join-message");
    }
    public static String quitMessage() {
        return serverManager.getConfig().getString("messages.quit-message");
    }
    public static String getPermissionMessage() {
        return serverManager.getConfig().getString("messages.permission-message");
    }
    public static String getReportingNotConfiguredMessage() {
        return serverManager.getConfig().getString("messages.reporting-not-configured-message");
    }
    public static String getDiscordToken() {
        return serverManager.getConfig().getString("report-system.discord-token");
    }
    public static String getReportChannelID() {
        return serverManager.getConfig().getString("report-system.report-channel-id");
    }
    public static boolean castMessagesToMc() {
        return serverManager.getConfig().getBoolean("discord-messages.casttomc");
    }
    public static String castToMcChannelID() {
        return serverManager.getConfig().getString("discord-messages.casttomc-channel-id");
    }
    public static boolean castMessagesToDc() {
        return serverManager.getConfig().getBoolean("discord-messages.casttodc");
    }
    public static String castToDcChannelID() {
        return serverManager.getConfig().getString("discord-messages.casttodc-channel-id");
    }
    public static String getPlayerNotFoundMessage() {
        return serverManager.getConfig().getString("player-not-found-message");
    }
    public static String getFreezeMessage() {
        return serverManager.getConfig().getString("player-freezed");
    }
    public static String getUnfreezeMessage() {
        return serverManager.getConfig().getString("player-unfrozen");
    }
}
