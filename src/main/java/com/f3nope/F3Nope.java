package com.f3nope;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class F3Nope implements ModInitializer {
	public static final String MOD_ID = "f3nope";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	private static F3NopeConfig config;

	@Override
	public void onInitialize() {
		LOGGER.info("F3Nope mod initializing...");

		// Load configuration
		config = F3NopeConfig.load();

		// Register commands
		F3NopeCommands.register();

		LOGGER.info("F3Nope mod initialized successfully!");
	}

	public static F3NopeConfig getConfig() {
		return config;
	}

	public static void setConfig(F3NopeConfig newConfig) {
		config = newConfig;
	}
}
