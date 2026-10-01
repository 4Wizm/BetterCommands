package com.wizm.bettercommands;

import com.wizm.bettercommands.commands.ReloadCommand;
import com.wizm.bettercommands.config.ConfigManager;
import com.wizm.bettercommands.listeners.SpigotCommandListener;
import com.wizm.bettercommands.listeners.TabListener;
import com.wizm.bettercommands.security.PortBypassPrevention;
import org.bukkit.plugin.java.JavaPlugin;

public class BetterCommands extends JavaPlugin {

    private static BetterCommands instance;
    private ConfigManager configManager;
    private PortBypassPrevention portBypassPrevention;

    @Override
    public void onEnable() {
        instance = this;

        configManager = new ConfigManager(this);
        configManager.loadConfig();

        portBypassPrevention = new PortBypassPrevention(this);
        portBypassPrevention.init();

        getServer().getPluginManager().registerEvents(new SpigotCommandListener(this), this);
        getServer().getPluginManager().registerEvents(portBypassPrevention, this);

        getCommand("bettercommands").setExecutor(new ReloadCommand(this));

        if (TabListener.register(this)) {
            getLogger().info("Blocco Tab attivo.");
        } else {
            getLogger().info("Blocco Tab non disponibile su questa versione.");
        }

        getLogger().info("BetterCommands v1.0.0 abilitato!");
    }

    @Override
    public void onDisable() {
        getLogger().info("BetterCommands disabilitato.");
    }

    public void reloadPlugin() {
        configManager.loadConfig();
        portBypassPrevention.refresh();
    }

    public static BetterCommands getInstance() {
        return instance;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }
}