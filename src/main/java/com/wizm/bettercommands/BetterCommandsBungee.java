package com.wizm.bettercommands;

import com.wizm.bettercommands.commands.BungeeReloadCommand;
import com.wizm.bettercommands.config.BungeeConfigManager;
import com.wizm.bettercommands.listeners.BungeeCommandListener;
import net.md_5.bungee.api.plugin.Plugin;

public class BetterCommandsBungee extends Plugin {

    private static BetterCommandsBungee instance;
    private BungeeConfigManager configManager;

    @Override
    public void onEnable() {
        instance = this;
        configManager = new BungeeConfigManager(getDataFolder());
        configManager.loadConfig();
        getProxy().getPluginManager().registerListener(this, new BungeeCommandListener(this));
        getProxy().getPluginManager().registerCommand(this, new BungeeReloadCommand(this));
        getLogger().info("BetterCommands v1.0.0 abilitato (BungeeCord)!");
    }

    @Override
    public void onDisable() {
        getLogger().info("BetterCommands disabilitato.");
    }

    public void reloadPlugin() {
        configManager.loadConfig();
    }

    public static BetterCommandsBungee getInstance() {
        return instance;
    }

    public BungeeConfigManager getConfigManager() {
        return configManager;
    }
}