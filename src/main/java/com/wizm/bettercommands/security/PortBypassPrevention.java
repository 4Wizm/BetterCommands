package com.wizm.bettercommands.security;

import com.wizm.bettercommands.BetterCommands;
import com.wizm.bettercommands.util.MessageUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class PortBypassPrevention implements Listener {

    private final BetterCommands plugin;
    private boolean enabled;
    private String proxyIp;

    public PortBypassPrevention(BetterCommands plugin) {
        this.plugin = plugin;
    }

    public void init() {
        refresh();
        if (enabled) {
            plugin.getLogger().info("Port Bypass Prevention attiva. IP proxy: " + proxyIp);
        }
    }

    public void refresh() {
        this.enabled = plugin.getConfigManager().isPortBypassEnabled();
        this.proxyIp = plugin.getConfigManager().getProxyIp();
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onLogin(PlayerLoginEvent event) {
        if (!enabled) return;

        String ip = event.getAddress().getHostAddress();
        if (ip.equals(proxyIp) || ip.equals("127.0.0.1") || ip.equals("0.0.0.0")) return;

        String playerName = event.getPlayer().getName();
        event.disallow(PlayerLoginEvent.Result.KICK_OTHER,
                MessageUtil.color(plugin.getConfigManager().getKickMessage()
                        .replace("%player%", playerName)
                        .replace("%ip%", ip)));

        plugin.getLogger().warning("Port Bypass bloccato: " + playerName + " da " + ip);
    }
}