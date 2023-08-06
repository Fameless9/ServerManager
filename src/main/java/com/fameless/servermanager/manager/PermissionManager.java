package com.fameless.servermanager.manager;

import com.fameless.servermanager.ServerManager;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

public class PermissionManager {

    private HashMap<UUID, PermissionAttachment> permissionMap = new HashMap<>();

    private ServerManager serverManager;
    private File file;
    private YamlConfiguration configuration;

    public PermissionManager(ServerManager serverManager) {
        this.serverManager = serverManager;
        initiateFile();
    }

    private void initiateFile() {

        file = new File(serverManager.getDataFolder(), "permissions.yml");

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        configuration = YamlConfiguration.loadConfiguration(file);
    }

    public void addPermission(Player player, String permission) {

        try {
            PermissionAttachment attachment = permissionMap.get(player.getUniqueId());
            attachment.setPermission(permission, true);
            configuration.set(player.getUniqueId() + "." + permission, true);
            configuration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void removePermission(Player player, String permission) {

        try {
            PermissionAttachment attachment = permissionMap.get(player.getUniqueId());
            attachment.unsetPermission(permission);
            configuration.set(player.getUniqueId() + "." + permission, false);
            configuration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public HashMap<UUID, PermissionAttachment> getPermissionMap() {
        return permissionMap;
    }

    public YamlConfiguration getConfiguration() {
        return configuration;
    }
}