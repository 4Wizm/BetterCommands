

package com.wizm.bettercommands.commands;

import com.wizm.bettercommands.BetterCommandsBungee;
import com.wizm.bettercommands.util.MessageUtil;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class BungeeReloadCommand extends Command {

    private final BetterCommandsBungee plugin;

    public BungeeReloadCommand(BetterCommandsBungee plugin) {
        super("bettercommands", "bettercommands.use.reload", "bc");
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!sender.hasPermission("bettercommands.use.reload")) {
            sender.sendMessage(MessageUtil.color("&cNon hai il permesso!"));
            return;
        }
        if (args.length == 0 || !args[0].equalsIgnoreCase("reload")) {
            sender.sendMessage(MessageUtil.color("&eUso: /bettercommands reload"));
            return;
        }
        plugin.reloadPlugin();
        sender.sendMessage(MessageUtil.color("&aConfigurazione ricaricata!"));
    }
}