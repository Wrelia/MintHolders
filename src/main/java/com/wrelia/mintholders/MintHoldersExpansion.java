package com.wrelia.mintholders;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MintHoldersExpansion extends PlaceholderExpansion {

    private final MintHolders plugin;

    public MintHoldersExpansion(MintHolders plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "mintholders";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Wrelia";
    }

    @Override
    public @NotNull String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public @Nullable String onRequest(OfflinePlayer player, @NotNull String identifier) {
        ConfigurationSection root = plugin.getConfig().getConfigurationSection("");
        
        if (root == null) {
            return null;
        }

        for (String key : root.getKeys(false)) {
            if (key.equalsIgnoreCase(identifier)) {
                ConfigurationSection config = root.getConfigurationSection(key);
                
                if (config == null) {
                    return null;
                }

                String source = config.getString("source");
                String errorValue = config.getString("error", "Hata");

                if (source == null || source.isEmpty()) {
                    return errorValue;
                }

                // Source placeholder'ı resolve et
                String sourcePlaceholder = "%" + source + "%";
                String resolvedValue = resolvePlaceholder(player, sourcePlaceholder);

                if (resolvedValue == null || resolvedValue.isEmpty() || resolvedValue.equals(sourcePlaceholder)) {
                    return errorValue;
                }

                // Values mapping'inden değeri bul
                ConfigurationSection values = config.getConfigurationSection("values");
                if (values == null) {
                    return errorValue;
                }

                for (String mapKey : values.getKeys(false)) {
                    if (mapKey.equalsIgnoreCase(resolvedValue)) {
                        return values.getString(mapKey);
                    }
                }

                return errorValue;
            }
        }

        return null;
    }

    private String resolvePlaceholder(OfflinePlayer player, String placeholder) {
        if (player != null && player.isOnline()) {
            return PlaceholderAPI.setPlaceholders(player.getPlayer(), placeholder);
        }
        return PlaceholderAPI.setPlaceholders(null, placeholder);
    }
}
