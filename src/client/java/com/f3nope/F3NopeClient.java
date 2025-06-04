package com.f3nope;

import net.fabricmc.api.ClientModInitializer;

public class F3NopeClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		F3Nope.LOGGER.info("F3Nope client initialized!");
	}
}
