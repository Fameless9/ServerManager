package com.fameless.servermanager.manager;

import com.fameless.servermanager.ServerManager;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class MuteManager {

    private final File file;
    private YamlConfiguration configuration;
    private final ServerManager serverManager;

    public MuteManager(ServerManager serverManager) {
        this.serverManager = serverManager;
        this.file = new File(serverManager.getDataFolder(), "mutedlist.yml");
    }

    public void initiateFile() throws IOException {

        if (!file.exists()) {
            file.createNewFile();
        }
        configuration = YamlConfiguration.loadConfiguration(file);
        configuration.save(file);
    }

    public void setMuted(Player player, boolean muted) {
        try {
            configuration.set(player.getUniqueId() + ".muted", muted);
            configuration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public boolean getMuted(Player player) {
        boolean muted = configuration.getBoolean(player.getUniqueId() + ".muted", false);
        return muted;
    }
    public void setMuteTime(Player player, int time) {

        UUID uuid = player.getUniqueId();
        try {
            configuration.set(uuid + ".time", time);
            configuration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getMuteTime(UUID uuid) {
        return configuration.getInt(uuid.toString() + ".time");
    }

    public YamlConfiguration getConfiguration() {
        return configuration;
    }
}
