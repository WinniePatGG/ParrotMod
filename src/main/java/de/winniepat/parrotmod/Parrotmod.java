package de.winniepat.parrotmod;

import de.winniepat.parrotmod.discord.DiscordRPCManager;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;

public class Parrotmod implements ModInitializer {

    public static String BUILD = "1";

    @Override
    public void onInitialize() {
        DiscordRPCManager.init();

        ClientLifecycleEvents.CLIENT_STOPPING.register(client -> DiscordRPCManager.shutdown());
    }
}
