package de.winniepat.parrotmod;

import de.winniepat.parrotmod.config.ConfigManager;
import de.winniepat.parrotmod.discord.DiscordRPCManager;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Parrotmod implements ModInitializer {

    private static final Logger DEBUG_LOGGER = LoggerFactory.getLogger("ParrotMod-Debug");

    public static final boolean DEBUG = true;

    @Override
    public void onInitialize() {
        ConfigManager.load();
        DiscordRPCManager.init();

        ClientLifecycleEvents.CLIENT_STOPPING.register(client -> DiscordRPCManager.shutdown());

        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> DiscordRPCManager.setIdle());

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player != null) {
                DiscordRPCManager.maybeUpdateFromPlayer(client.player);
            }
        });
    }
}
