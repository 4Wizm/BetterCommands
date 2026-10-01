package com.wizm.bettercommands.commands;

import com.wizm.bettercommands.BetterCommands;
import com.wizm.bettercommands.util.MessageUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReloadCommand implements CommandExecutor {

    private final BetterCommands plugin;

    public ReloadCommand(BetterCommands plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("bettercommands.use.reload")) {
            sender.sendMessage(MessageUtil.color("&cNon hai il permesso!"));
            return true;
        }
        if (args.length == 0 || !args[0].equalsIgnoreCase("reload")) {
            sender.sendMessage(MessageUtil.color("&eUso: /bettercommands reload"));
            return true;
        }
        plugin.reloadPlugin();
        sender.sendMessage(MessageUtil.color("&aConfigurazione ricaricata!"));
        return true;
    }
}