package com.wizm.bettercommands.config;

import com.wizm.bettercommands.BetterCommands;
import com.wizm.bettercommands.util.CommandMatcher;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ConfigManager {

    private final BetterCommands plugin;

    private CommandMatcher blockedMatcher;
    private Set<String> allowedCommands = Collections.emptySet();
    private String blockedMessage;

    private boolean portBypassEnabled;
    private String proxyIp;
    private String kickMessage;

    public ConfigManager(BetterCommands plugin) {
        this.plugin = plugin;
    }

    public void loadConfig() {
        plugin.saveDefaultConfig();
        plugin.reloadConfig();
        FileConfiguration config = plugin.getConfig();

        List<String> blocked = config.getStringList("blocked-commands");
        blockedMatcher = new CommandMatcher(blocked);

        List<String> allowed = config.getStringList("allowed-commands");
        Set<String> set = new HashSet<>(allowed.size());
        for (String a : allowed) {
            if (a != null) set.add(a.toLowerCase());
        }
        allowedCommands = set;

        blockedMessage = config.getString("blocked-message", "&c&l! &7Non hai il permesso di eseguire questo comando!");

        portBypassEnabled = config.getBoolean("port-bypass-prevention.enabled", true);
        proxyIp = config.getString("port-bypass-prevention.proxy-ip", "127.0.0.1");
        kickMessage = config.getString("port-bypass-prevention.kick-message", "&c&l! &7Accesso diretto non consentito!");
    }

    public CommandMatcher getBlockedMatcher() { return blockedMatcher; }
    public Set<String> getAllowedCommands() { return allowedCommands; }
    public String getBlockedMessage() { return blockedMessage; }
    public boolean isPortBypassEnabled() { return portBypassEnabled; }
    public String getProxyIp() { return proxyIp; }
    public String getKickMessage() { return kickMessage; }
}