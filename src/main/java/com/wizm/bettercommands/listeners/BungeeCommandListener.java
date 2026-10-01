package com.wizm.bettercommands.listeners;

import com.wizm.bettercommands.BetterCommandsBungee;
import com.wizm.bettercommands.config.BungeeConfigManager;
import com.wizm.bettercommands.util.MessageUtil;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

public class BungeeCommandListener implements Listener {

    private final BetterCommandsBungee plugin;

    public BungeeCommandListener(BetterCommandsBungee plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(ChatEvent event) {
        if (!event.isCommand()) return;
        if (!(event.getSender() instanceof ProxiedPlayer)) return;

        ProxiedPlayer player = (ProxiedPlayer) event.getSender();
        if (player.hasPermission("bettercommands.bypass")) return;

        String message = event.getMessage();
        if (message.length() < 2) return;

        int spaceIndex = message.indexOf(' ');
        String command = (spaceIndex == -1 ? message.substring(1) : message.substring(1, spaceIndex)).toLowerCase();

        BungeeConfigManager cfg = plugin.getConfigManager();
        if (cfg.getAllowedCommands().contains(command)) return;
        if (!cfg.getBlockedMatcher().matches(command)) return;

        event.setCancelled(true);
        player.sendMessage(MessageUtil.color(cfg.getBlockedMessage()));
    }
}