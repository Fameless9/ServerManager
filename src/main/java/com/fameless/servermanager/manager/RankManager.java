package com.fameless.servermanager.manager;

import com.fameless.servermanager.ServerManager;
import com.fameless.servermanager.rank.Rank;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

public class RankManager {

    private final HashMap<UUID, Rank> rankHashMap = new HashMap<>();

    private final ServerManager serverManager;
    private YamlConfiguration configuration;
    private final File file;

    public RankManager(ServerManager serverManager) {
        this.serverManager = serverManager;
        this.file = new File(serverManager.getDataFolder(), "ranks.yml");
    }

    public void initiateFile() throws IOException {

        if (!file.exists()) {
            file.createNewFile();
        }
        configuration = YamlConfiguration.loadConfiguration(file);
        configuration.save(file);
    }

    public void setRank(Player player, Rank rank) throws IOException {

        rankHashMap.put(player.getUniqueId(), rank);
        configuration.set(player.getUniqueId().toString(), rank.name());
        configuration.save(file);
    }
    public Rank getRank(Player player) {
        return rankHashMap.get(player.getUniqueId());
    }

    public YamlConfiguration getConfiguration() {
        return configuration;
    }
}