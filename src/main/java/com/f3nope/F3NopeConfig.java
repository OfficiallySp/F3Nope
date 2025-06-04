package com.f3nope;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class F3NopeConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("f3nope.json");

    // Configuration options
    public boolean hideVanillaDebug = true;
    public boolean showCustomText = true;
    public List<String> customTextLines = new ArrayList<>();
    public int textX = 10;
    public int textY = 10;
    public int textColor = 0xFFFFFF; // White
    public boolean textShadow = true;

    public F3NopeConfig() {
        // Default custom text
        customTextLines.add("F3Nope Mod");
        customTextLines.add("Debug info hidden");
        customTextLines.add("Configure with /f3nope");
    }

    public static F3NopeConfig load() {
        if (Files.exists(CONFIG_PATH)) {
            try {
                String json = Files.readString(CONFIG_PATH);
                F3NopeConfig config = GSON.fromJson(json, F3NopeConfig.class);
                F3Nope.LOGGER.info("Loaded F3Nope configuration from file");
                return config;
            } catch (IOException e) {
                F3Nope.LOGGER.error("Failed to load F3Nope configuration: ", e);
            }
        }

        F3NopeConfig config = new F3NopeConfig();
        config.save();
        F3Nope.LOGGER.info("Created new F3Nope configuration file");
        return config;
    }

    public void save() {
        try {
            Files.createDirectories(CONFIG_PATH.getParent());
            String json = GSON.toJson(this);
            Files.writeString(CONFIG_PATH, json);
            F3Nope.LOGGER.info("Saved F3Nope configuration to file");
        } catch (IOException e) {
            F3Nope.LOGGER.error("Failed to save F3Nope configuration: ", e);
        }
    }

    public void reload() {
        F3NopeConfig newConfig = load();
        this.hideVanillaDebug = newConfig.hideVanillaDebug;
        this.showCustomText = newConfig.showCustomText;
        this.customTextLines = newConfig.customTextLines;
        this.textX = newConfig.textX;
        this.textY = newConfig.textY;
        this.textColor = newConfig.textColor;
        this.textShadow = newConfig.textShadow;
    }
}
