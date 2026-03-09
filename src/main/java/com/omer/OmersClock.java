package com.omer;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.*;

public class OmersClock implements ModInitializer {
	public static final String MOD_ID = "omers-clock";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LocalTime currentTime = LocalTime.now();
		String timeString = currentTime.toString();
		LOGGER.info("Hello Fabric world!\n"+timeString);
	}
}