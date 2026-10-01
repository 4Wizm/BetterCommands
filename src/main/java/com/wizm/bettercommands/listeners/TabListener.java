package com.wizm.bettercommands.listeners;

import com.wizm.bettercommands.BetterCommands;
import com.wizm.bettercommands.config.ConfigManager;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.EventExecutor;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class TabListener implements Listener {

    private final BetterCommands plugin;
    private final boolean asyncMode;

    private TabListener(BetterCommands plugin, boolean asyncMode) {
        this.plugin = plugin;
        this.asyncMode = asyncMode;
    }

    @SuppressWarnings("unchecked")
    public static boolean register(BetterCommands plugin) {
        boolean ok = tryRegister(plugin,
                "com.destroystokyo.paper.event.server.AsyncTabCompleteEvent", true);
        if (!ok) {
            ok = tryRegister(plugin,
                    "org.bukkit.event.server.TabCompleteEvent", false);
        }
        return ok;
    }

    @SuppressWarnings("unchecked")
    private static boolean tryRegister(BetterCommands plugin, String className, boolean asyncMode) {
        try {
            final Class<?> eventClass = Class.forName(className);
            final TabListener listener = new TabListener(plugin, asyncMode);

            EventExecutor executor = new EventExecutor() {
                @Override
                public void execute(Listener l, Event event) {
                    listener.handle(event);
                }
            };

            plugin.getServer().getPluginManager().registerEvent(
                    (Class<? extends Event>) eventClass,
                    listener,
                    EventPriority.LOWEST,
                    executor,
                    plugin
            );
            plugin.getLogger().info("Tab listener registrato: " + className);
            return true;
        } catch (Throwable t) {
            plugin.getLogger().info("Tab listener non disponibile: " + className);
            return false;
        }
    }

    private void handle(Event event) {
        try {
            Class<?> cls = event.getClass();

            Method getSender = cls.getMethod("getSender");
            Object sender = getSender.invoke(event);
            if (!(sender instanceof Player)) return;
            Player player = (Player) sender;
            if (player.hasPermission("bettercommands.bypass")) return;

            Method getBuffer = cls.getMethod("getBuffer");
            String buffer = (String) getBuffer.invoke(event);
            if (buffer == null || buffer.isEmpty()) return;
            if (buffer.charAt(0) != '/') return;
            String noSlash = buffer.substring(1);
            if (noSlash.isEmpty() || noSlash.contains(" ")) return;

            Method getCompletions = cls.getMethod("getCompletions");
            Object raw = getCompletions.invoke(event);
            if (!(raw instanceof List)) return;
            @SuppressWarnings("unchecked")
            List<String> completions = (List<String>) raw;
            if (completions.isEmpty()) return;

            ConfigManager cfg = plugin.getConfigManager();
            List<String> filtered = new ArrayList<>(completions.size());
            for (String c : completions) {
                if (c == null) continue;
                String name = c.toLowerCase();
                if (cfg.getAllowedCommands().contains(name)) {
                    filtered.add(c);
                    continue;
                }
                if (cfg.getBlockedMatcher().matches(name)) continue;
                filtered.add(c);
            }

            if (filtered.size() == completions.size()) return;

            Method setCompletions = cls.getMethod("setCompletions", List.class);
            setCompletions.invoke(event, filtered);

        } catch (Throwable ignored) {
        }
    }
}