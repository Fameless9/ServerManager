package com.fameless.servermanager.command;

import com.fameless.servermanager.Configuration;
import com.fameless.servermanager.ServerManager;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class ReportCommand implements CommandExecutor, Listener {

    private ServerManager serverManager;
    private Cache<UUID, Long> cooldown = CacheBuilder.newBuilder().expireAfterWrite(5, TimeUnit.MINUTES).build();

    private JDA jda;
    private TextChannel reportChannel;
    private Player target;

    public ReportCommand(ServerManager serverManager) {
        this.serverManager = serverManager;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        if (!(commandSender instanceof Player)) return false;
        if (!commandSender.hasPermission("server.report")) {
            serverManager.sendPermissionMessage(commandSender, "server.report");
            return false;
        }
        if (serverManager.getJda() == null || serverManager.getConfig().getString("report-system.report-channel-id").equals("")) {
            commandSender.sendMessage(serverManager.getConfig().getString("messages.reporting-not-configured-message"));
            return false;
        }
        Player player = (Player) commandSender;
        if (cooldown.asMap().containsKey(player.getUniqueId())) {
            long distance = cooldown.asMap().get(player.getUniqueId()) - System.currentTimeMillis();
            player.sendMessage(Configuration.messagePrefix() + ChatColor.RESET + ChatColor.RED + "You must wait another " + TimeUnit.MILLISECONDS.toSeconds(distance) + " seconds to report a player again.");
            return false;
        }
        if (args.length == 1) {
            if (Bukkit.getPlayerExact(args[0]) == null) {
                commandSender.sendMessage(Configuration.messagePrefix() + ChatColor.RESET + ChatColor.RED + "Player is not online or is not valid.");
                return false;
            }
            openReportInv(player);
            target = Bukkit.getPlayer(args[0]);
            jda = serverManager.getJda();
            reportChannel = jda.getTextChannelById(serverManager.getConfig().getString("report-system.report-channel-id"));
            return false;
        } else {
            commandSender.sendMessage(Configuration.messagePrefix() + ChatColor.RESET + ChatColor.RED + "Usage: /report <player> <reason>");
            return false;
        }
    }
    private void openReportInv(Player player) {

        Inventory inventory = Bukkit.createInventory(player, 9, "Report Menu");
        inventory.setItem(0, getItem(new ItemStack(Material.TNT),ChatColor.RED + "Cheating/Hacking",
                ChatColor.DARK_AQUA + "Report the Player for cheating",
                ChatColor.DARK_AQUA + "This can be using a hacked client or abusing bugs"));
        inventory.setItem(1, getItem(new ItemStack(Material.BARRIER),ChatColor.RED + "Abusive chat behaviour",
                ChatColor.DARK_AQUA + "Report a player for chatting in an abusive manner",
                ChatColor.DARK_AQUA + "This can be swearing or trying to sell/scam in chat"));
        inventory.setItem(2, getItem(new ItemStack(Material.BUCKET), ChatColor.RED + "Other",
                ChatColor.DARK_AQUA + "Report a player for something not listed here.",
                ChatColor.DARK_AQUA + "You can give more information after you clicked."));
        player.openInventory(inventory);
    }

    @EventHandler(ignoreCancelled = true)
    public void onInventoryClick(InventoryClickEvent event) {
        if (!event.getView().getTitle().equalsIgnoreCase("report menu")) return;

        event.setCancelled(true);

        int slot = event.getSlot();
        Player player = (Player) event.getWhoClicked();

        switch (slot) {
            case 0:
                Bukkit.getScheduler().runTaskAsynchronously(serverManager, () -> reportChannel.sendMessage("@here !New report: "
                        + player.getName() + " reported " + target.getName() + " for Cheating/Hacking").queue());
                player.sendMessage(Configuration.messagePrefix() + ChatColor.RESET + ChatColor.GREEN + "Player has been reported. Thanks for keeping the community safe!");
                cooldown.put(player.getUniqueId(), System.currentTimeMillis() + 300000);
                player.closeInventory();
                break;
            case 1:
                Bukkit.getScheduler().runTaskAsynchronously(serverManager, () -> reportChannel.sendMessage("@here !New report: "
                        + player.getName() + " reported " + target.getName() + " for Abusive chat behaviour").queue());
                player.sendMessage(Configuration.messagePrefix() + ChatColor.RESET + ChatColor.GREEN + "Player has been reported. Thanks for keeping the community safe!");
                cooldown.put(player.getUniqueId(), System.currentTimeMillis() + 300000);
                player.closeInventory();
                break;
            case 2:
                new AnvilGUI.Builder()
                        .onClick((anvilSlot, stateSnapshot) -> {
                            if (!anvilSlot.equals(AnvilGUI.Slot.OUTPUT)) {
                                return Collections.emptyList();
                            }
                            String givenInformation = stateSnapshot.getText();
                            Bukkit.getScheduler().runTaskAsynchronously(serverManager, () -> reportChannel.sendMessage("@here !New report: "
                                    + player.getName() + " reported " + target.getName() + " for Other.\n" +
                                    "Given Information: " + givenInformation).queue());
                            player.sendMessage(Configuration.messagePrefix() + ChatColor.RESET + ChatColor.GREEN + "Player has been reported. Thanks for keeping the community safe!");
                            cooldown.put(player.getUniqueId(), System.currentTimeMillis() + 300000);
                            player.closeInventory();
                            return Collections.emptyList();
                        })
                        .itemOutput(getItem(new ItemStack(Material.GREEN_DYE),ChatColor.GREEN + "Send report", "",
                                ChatColor.RED + "Please don't report unless you have a valid accusation!"))
                        .title("Report Menu | Other")
                        .text("Enter more information about the report")
                        .plugin(serverManager)
                        .open(player);
                break;
        }
    }

    private ItemStack getItem(ItemStack item, String name, String ...lore) {
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);

        List<String> lores = new ArrayList<>();

        for (String s : lore) {
            lores.add(s);
        }

        meta.setLore(lores);
        item.setItemMeta(meta);
        return item;
    }
}