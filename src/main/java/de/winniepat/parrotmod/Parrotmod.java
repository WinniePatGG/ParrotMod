package de.winniepat.parrotmod;

import de.winniepat.parrotmod.discord.DiscordRPCManager;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;

public class Parrotmod implements ModInitializer {

    @Override
    public void onInitialize() {
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
