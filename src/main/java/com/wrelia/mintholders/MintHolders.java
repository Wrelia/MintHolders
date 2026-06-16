package com.wrelia.mintholders;

import org.bukkit.plugin.java.JavaPlugin;

public class MintHolders extends JavaPlugin {

    private static MintHolders instance;
    private MintHoldersExpansion expansion;

    @Override
    public void onEnable() {
        instance = this;
        
        saveDefaultConfig();
        
        boolean papiAvailable = getServer().getPluginManager().getPlugin("PlaceholderAPI") != null;
        
        if (papiAvailable) {
            expansion = new MintHoldersExpansion(this);
            expansion.register();
        }
        
        sendStartupBanner(papiAvailable);
        
        getCommand("mintholders").setExecutor(new CommandHandler(this));
    }
    
    private void sendStartupBanner(boolean papiAvailable) {
        String[] banner = {
            "",
            "§a███╗   ███╗██╗███╗   ██╗████████╗ ██████╗ ██████╗  █████╗ ",
            "§a████╗ ████║██║████╗  ██║╚══██╔══╝██╔═══██╗██╔══██╗██╔══██╗",
            "§a██╔████╔██║██║██╔██╗ ██║   ██║   ██║   ██║██████╔╝███████║",
            "§a██║╚██╔╝██║██║██║╚██╗██║   ██║   ██║   ██║██╔══██╗██╔══██║",
            "§a██║ ╚═╝ ██║██║██║ ╚████║   ██║   ╚██████╔╝██║  ██║██║  ██║",
            "§a╚═╝     ╚═╝╚═╝╚═╝  ╚═══╝   ╚═╝    ╚═════╝ ╚═╝  ╚═╝╚═╝  ╚═╝",
            "",
            "§8  [§a✔§8] §7Plugin §aAktif! §7| §fSürüm §b" + getDescription().getVersion(),
            "§8  [§a✔§8] §7Yapımcı §8» §fWrelia",
            papiAvailable ? 
                "§8  [§a✔§8] §7Gereken Eklenti §8» §fPlaceholderAPI" : 
                "§8  [§c✘§8] §7Eklenti §8» §cPAPI bulunamadı",
            papiAvailable ? 
                "§8  [§a✔§8] §7Durum §8» §aPlugin Aktif!" : 
                "§8  [§c✘§8] §7Durum §8» §cPlugin Devre Dışı! §7| §cPAPI gereklidir",
            ""
        };
        
        for (String line : banner) {
            getLogger().info(line);
        }
    }

    @Override
    public void onDisable() {
        if (expansion != null) {
            expansion.unregister();
        }
        getLogger().info("MintHolders has been disabled!");
    }

    public static MintHolders getInstance() {
        return instance;
    }

    public void reload() {
        reloadConfig();
        getLogger().info("Configuration reloaded!");
    }
}
