package com.fameless.servermanager.manager.managerlistener;

import com.fameless.servermanager.ServerManager;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.permissions.PermissionAttachment;
import java.util.Set;

public class PermissionListener implements Listener {

    private ServerManager serverManager;

    public PermissionListener(ServerManager serverManager) {
        this.serverManager = serverManager;
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerJoin(PlayerJoinEvent event) {

        if (!serverManager.getPermissionManager().getPermissionMap().containsKey(event.getPlayer().getUniqueId())) {
            PermissionAttachment attachment = event.getPlayer().addAttachment(serverManager);
            serverManager.getPermissionManager().getPermissionMap().put(event.getPlayer().getUniqueId(), attachment);
        }

        ConfigurationSection section = serverManager.getPermissionManager().getConfiguration().getConfigurationSection(event.getPlayer().getUniqueId() + ".");

        if (section == null) {
            return;
        }

        Set<String> permissions = section.getKeys(true);


        for (String permission : permissions) {

            if (!section.getBoolean(permission)) {
                continue;
            }
            serverManager.getPermissionManager().addPermission(event.getPlayer(), permission);
        }
    }
}
