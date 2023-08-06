package com.fameless.servermanager;

import com.fameless.servermanager.command.*;
import com.fameless.servermanager.manager.MuteManager;
import com.fameless.servermanager.manager.NametagManager;
import com.fameless.servermanager.manager.PermissionManager;
import com.fameless.servermanager.manager.RankManager;
import com.fameless.servermanager.manager.managerlistener.AssignRank;
import com.fameless.servermanager.manager.managerlistener.CustomMessage;
import com.fameless.servermanager.manager.managerlistener.PermissionListener;
import com.fameless.servermanager.manager.managerlistener.ResoucePackListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.*;

public final class ServerManager extends JavaPlugin implements Listener {

    private JDA jda;

    private final RankManager rankManager = new RankManager(this);
    private final MuteManager muteManager = new MuteManager(this);
    private final PermissionManager permissionManager = new PermissionManager(this);
    private CastMessages castMessages = new CastMessages(this);
    private NametagManager nametagManager;
    private ReportCommand repotClass = new ReportCommand(this);

    private final HashMap<UUID, Boolean> hideActionbarMap = new HashMap<>();

    @Override
    public void onEnable() {

        saveConfig();

        Configuration config = new Configuration(this);
        nametagManager = new NametagManager(this);

        Bukkit.getPluginManager().registerEvents(new CustomMessage(this), this);
        Bukkit.getPluginManager().registerEvents(new AssignRank(this), this);
        Bukkit.getPluginManager().registerEvents(new PermissionListener(this),this);
        Bukkit.getPluginManager().registerEvents(new ResoucePackListener(),this);
        Bukkit.getPluginManager().registerEvents(new MessageListeners(this),this);
        Bukkit.getPluginManager().registerEvents(repotClass,this);
        Bukkit.getPluginManager().registerEvents(castMessages,this);
        Bukkit.getPluginManager().registerEvents(this, this);

        getCommand("rank").setExecutor(new RankCommand(this));
        getCommand("mute").setExecutor(new MuteCommand(this));
        getCommand("unmute").setExecutor(new UnmuteCommand(this));
        getCommand("timeout").setExecutor(new TimeoutCommand(this));
        getCommand("hideab").setExecutor(new HideActionbarCommand(this));
        getCommand("perm").setExecutor(new PermissionCommand(this));
        getCommand("smhelp").setExecutor(new HelpCommand(this));
        getCommand("vanish").setExecutor(new VanishCommand(this));
        getCommand("report").setExecutor(repotClass);

        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }
        try {
            muteManager.initiateFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            rankManager.initiateFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<GatewayIntent> intents = new ArrayList<>();
        intents.addAll(Arrays.asList(GatewayIntent.values()));

        if (!getConfig().getString("report-system.discord-token").equals("")) {
            jda = JDABuilder.createDefault(getConfig().getString("report-system.discord-token"), intents)
                    .enableIntents(intents)
                    .addEventListeners(castMessages)
                    .build();
        } else {
            jda = null;
        }
    }

    public RankManager getRankManager() {
        return rankManager;
    }

    public MuteManager getMuteManager() {
        return muteManager;
    }

    public NametagManager getNametagManager() {
        return nametagManager;
    }

    public PermissionManager getPermissionManager() {
        return permissionManager;
    }

    public HashMap<UUID, Boolean> getHideActionbarMap() {
        return hideActionbarMap;
    }

    public JDA getJda() {
        return jda;
    }

    public void sendPermissionMessage(CommandSender sender, String permission) {

        String message = getConfig().getString("messages.permission-message");
        String newMessage = message.replace("{permission}", permission);
        sender.sendMessage(newMessage);
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (!hideActionbarMap.containsKey(event.getPlayer().getUniqueId())) {
            hideActionbarMap.put(event.getPlayer().getUniqueId(), false);
        }
    }
}
