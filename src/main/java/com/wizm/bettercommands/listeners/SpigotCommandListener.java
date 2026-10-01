package com.wizm.bettercommands.listeners;

import com.wizm.bettercommands.BetterCommands;
import com.wizm.bettercommands.config.ConfigManager;
import com.wizm.bettercommands.util.MessageUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class SpigotCommandListener implements Listener {

    private final BetterCommands plugin;

    public SpigotCommandListener(BetterCommands plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("bettercommands.bypass")) return;

        String message = event.getMessage();
        if (message.length() < 2) return;

        int spaceIndex = message.indexOf(' ');
        String command = (spaceIndex == -1 ? message.substring(1) : message.substring(1, spaceIndex)).toLowerCase();

        ConfigManager cfg = plugin.getConfigManager();
        if (cfg.getAllowedCommands().contains(command)) return;
        if (!cfg.getBlockedMatcher().matches(command)) return;

        event.setCancelled(true);
        player.sendMessage(MessageUtil.color(cfg.getBlockedMessage()));
    }
}