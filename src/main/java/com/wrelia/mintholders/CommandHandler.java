package com.wrelia.mintholders;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandHandler implements CommandExecutor, TabCompleter {

    private final MintHolders plugin;

    public CommandHandler(MintHolders plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) {
            sender.sendMessage("§eMintHolders §7- §fVersion " + plugin.getDescription().getVersion());
            sender.sendMessage("§7Author: §f" + plugin.getDescription().getAuthors());
            return true;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission("mintholders.reload")) {
                sender.sendMessage("§cBu komutu kullanmak için yetkiniz yok!");
                return true;
            }

            plugin.reload();
            sender.sendMessage("§aMintHolders yapılandırması yeniden yüklendi!");
            return true;
        }

        sender.sendMessage("§cGeçersiz komut! Kullanım: /mintholders reload");
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (args.length == 1) {
            if (sender.hasPermission("mintholders.reload")) {
                return Arrays.asList("reload");
            }
        }
        return new ArrayList<>();
    }
}
