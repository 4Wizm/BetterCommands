package com.wizm.bettercommands.config;

import com.wizm.bettercommands.util.CommandMatcher;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BungeeConfigManager {

    private final File dataFolder;
    private final File configFile;

    private CommandMatcher blockedMatcher;
    private Set<String> allowedCommands = Collections.emptySet();
    private String blockedMessage;

    public BungeeConfigManager(File dataFolder) {
        this.dataFolder = dataFolder;
        this.configFile = new File(dataFolder, "config.yml");
    }

    public void loadConfig() {
        if (!dataFolder.exists()) dataFolder.mkdirs();
        if (!configFile.exists()) {
            try (InputStream in = getClass().getResourceAsStream("/config.yml")) {
                if (in != null) Files.copy(in, configFile.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Configuration config;
        try {
            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(configFile);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        List<String> blocked = config.getStringList("blocked-commands");
        blockedMatcher = new CommandMatcher(blocked);

        List<String> allowed = config.getStringList("allowed-commands");
        Set<String> set = new HashSet<>(allowed.size());
        for (String a : allowed) {
            if (a != null) set.add(a.toLowerCase());
        }
        allowedCommands = set;

        blockedMessage = config.getString("blocked-message", "&c&l! &7Non hai il permesso di eseguire questo comando!");
    }

    public CommandMatcher getBlockedMatcher() { return blockedMatcher; }
    public Set<String> getAllowedCommands() { return allowedCommands; }
    public String getBlockedMessage() { return blockedMessage; }
}