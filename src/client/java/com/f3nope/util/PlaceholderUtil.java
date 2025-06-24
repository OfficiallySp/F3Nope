package com.f3nope.util;

import com.f3nope.F3Nope;
import com.f3nope.F3NopeConfig;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.SharedConstants;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.PlayerListEntry;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PlaceholderUtil {

    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss");
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static String replacePlaceholders(String text, F3NopeConfig config, MinecraftClient client) {
        if (!config.enablePlaceholders || text == null || text.isEmpty()) {
            return text;
        }

        String result = text;
        LocalDateTime now = LocalDateTime.now();

        // FPS placeholder
        if (config.showFps && result.contains("%fps%")) {
            result = result.replace("%fps%", String.valueOf(client.getCurrentFps()));
        }

        // Ping placeholder
        if (config.showPing && result.contains("%ping%")) {
            int ping = getCurrentPing(client);
            result = result.replace("%ping%", String.valueOf(ping));
        }

        // Date and time placeholders
        if (config.showDateTime) {
            if (result.contains("%time%")) {
                result = result.replace("%time%", now.format(TIME_FORMAT));
            }
            if (result.contains("%date%")) {
                result = result.replace("%date%", now.format(DATE_FORMAT));
            }
        }

        // Version placeholders
        if (config.showVersions) {
            if (result.contains("%mc_version%")) {
                result = result.replace("%mc_version%", SharedConstants.getGameVersion().getName());
            }
            if (result.contains("%fabric_version%")) {
                String fabricVersion = FabricLoader.getInstance().getModContainer("fabricloader")
                    .map(modContainer -> modContainer.getMetadata().getVersion().getFriendlyString())
                    .orElse("Unknown");
                result = result.replace("%fabric_version%", fabricVersion);
            }
            if (result.contains("%mod_version%")) {
                String modVersion = FabricLoader.getInstance().getModContainer(F3Nope.MOD_ID)
                    .map(modContainer -> modContainer.getMetadata().getVersion().getFriendlyString())
                    .orElse("Unknown");
                result = result.replace("%mod_version%", modVersion);
            }
        }

        return result;
    }

    private static int getCurrentPing(MinecraftClient client) {
        if (client.getNetworkHandler() == null || client.player == null) {
            return 0; // Single player or not connected
        }

        ClientPlayNetworkHandler networkHandler = client.getNetworkHandler();
        PlayerListEntry playerEntry = networkHandler.getPlayerListEntry(client.player.getUuid());

        return playerEntry != null ? playerEntry.getLatency() : 0;
    }
}
