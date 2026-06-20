package de.winniepat.parrotmod.discord;

import com.google.gson.JsonObject;
import com.jagrosh.discordipc.IPCClient;
import com.jagrosh.discordipc.IPCListener;
import com.jagrosh.discordipc.entities.ActivityType;
import com.jagrosh.discordipc.entities.Packet;
import com.jagrosh.discordipc.entities.RichPresence;
import com.jagrosh.discordipc.entities.User;
import com.jagrosh.discordipc.exceptions.NoDiscordClientException;
import com.mojang.authlib.minecraft.client.MinecraftClient;
import de.winniepat.parrotmod.ParrotLogger;
import de.winniepat.parrotmod.config.ConfigManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class DiscordRPCManager {
    private static final long UPDATE_COOLDOWN_MS = 15_000;

    private static IPCClient client;
    private static boolean connected = false;
    private static long lastUpdate = 0;
    private static long sessionStart;

    private static final List<String> IDLE_LINES = List.of(
            "Contemplating the void",
            "Staring at a crafting table",
            "Definetely not AFK",
            "Waiting for something to happen",
            "Just chilling",
            "Admiring the scenery",
            "Trying to find a use for all these parrots",
            "Listening to some tunes",
            "Wondering if parrots can be tamed with cookies",
            "Watching some parrots fly around",
            "Just hanging out with my parrots",
            "Trying to teach my parrots some new tricks",
            "Just a parrot enthusiast",
            "Trying to find the perfect seed for my parrot farm",
            "Just a parrot in a Minecraft world",
            "Trying to find a way to make parrots dance",
            "Just a parrot, doing parrot things"
    );

    private DiscordRPCManager() {}

    public static void init() {
        ConfigManager.registerListener(cfg -> {
            if (cfg.enableDiscordRPC) {
                if (client == null) {
                    startClient();
                }
            } else {
                shutdown();
            }
        });

        if (ConfigManager.getInstance().enableDiscordRPC) {
            startClient();
        }
    }

    private static void startClient() {
        client = new IPCClient(1517985621396820039L);
        client.setListener(new IPCListener() {
            @Override
            public void onPacketSent(IPCClient client, Packet packet) {

            }

            @Override
            public void onPacketReceived(IPCClient client, Packet packet) {

            }

            @Override
            public void onActivityJoin(IPCClient client, String secret) {

            }

            @Override
            public void onActivitySpectate(IPCClient client, String secret) {

            }

            @Override
            public void onActivityJoinRequest(IPCClient client, String secret, User user) {

            }

            @Override
            public void onReady(IPCClient client) {
                connected = true;
                sessionStart = OffsetDateTime.now().toEpochSecond();
                updatePresence("In the menus", randomIdleLine());
            }

            @Override
            public void onClose(IPCClient client, JsonObject json) {

            }

            @Override
            public void onDisconnect(IPCClient client, Throwable t) {
                connected = false;
            }
        });

        new Thread(() -> {
            try {
                client.connect();
            } catch (NoDiscordClientException e) {
                connected = false;
            }
        }, "discord-rpc-init").start();
    }

    public static void maybeUpdateFromPlayer(LocalPlayer player) {
        long now = System.currentTimeMillis();
        if (now - lastUpdate < UPDATE_COOLDOWN_MS) return;
        lastUpdate = now;
        updateFromPlayer(player);
    }

    private static void updateFromPlayer(LocalPlayer player) {
        ResourceKey<Level> dimension = player.level().dimension();

        var cfg = ConfigManager.getInstance();
        ItemStack held = player.getMainHandItem();
        String heldName = held.isEmpty() ? "empty hands" : held.getHoverName().getString();

        String details;
        if (cfg.showBiomeInRPC) {
            if (dimension == Level.OVERWORLD) {
                details = "Overworld";
            } else if (dimension == Level.NETHER) {
                details = "Nether";
            } else if (dimension == Level.END) {
                details = "The End";
            } else {
                details = "Custom Dimension: " + dimension.toString();
            }
        } else {
            details = "Playing Minecraft";
        }

        StringBuilder stateBuilder = new StringBuilder();
        if (cfg.showHeldItemInRPC) {
            stateBuilder.append("Wielding ").append(heldName);
        }
        if (cfg.showHealthInRPC) {
            if (stateBuilder.length() > 0) stateBuilder.append(" · ");
            stateBuilder.append((int) player.getHealth()).append(" HP");
        }
        String state = stateBuilder.toString();
        if (state.isEmpty()) state = "In-game";

        updatePresence(details, state);
    }

    public static void setIdle() {
        updatePresence("In the menus", randomIdleLine());
    }

    public static void updatePresence(String details, String state) {
        if (!connected || !ConfigManager.getInstance().enableDiscordRPC) return;
        RichPresence presence = new RichPresence.Builder()
                .setActivityType(ActivityType.Playing)
                .setDetails(details)
                .setState(state)
                .setStartTimestamp(OffsetDateTime.now().toEpochSecond())
                .setLargeImage("icon_large", "My Mod")
                .build();
        client.sendRichPresence(presence);
    }

    public static String randomIdleLine() {
        return IDLE_LINES.get(ThreadLocalRandom.current().nextInt(IDLE_LINES.size()));
    }

    private static String prettify(String raw) {
        String spaced = raw.replace('_', ' ');
        return spaced.substring(0, 1).toUpperCase() + spaced.substring(1);
    }

    public static void shutdown() {
        if (client != null) {
            try {
                if (connected) {
                    client.close();
                }
            } catch (Exception e) {
                ParrotLogger.logError(DiscordRPCManager.class, "Error while closing Discord RPC client", e);
            } finally {
                client = null;
                connected = false;
            }
        }
    }
}
