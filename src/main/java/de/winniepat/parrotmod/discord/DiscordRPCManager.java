package de.winniepat.parrotmod.discord;

import com.google.gson.JsonObject;
import com.jagrosh.discordipc.IPCClient;
import com.jagrosh.discordipc.IPCListener;
import com.jagrosh.discordipc.entities.ActivityType;
import com.jagrosh.discordipc.entities.Packet;
import com.jagrosh.discordipc.entities.RichPresence;
import com.jagrosh.discordipc.entities.User;
import com.jagrosh.discordipc.exceptions.NoDiscordClientException;

import java.time.OffsetDateTime;

public class DiscordRPCManager {
    private static final long APPLICATION_ID = 1517985621396820039L;
    private static IPCClient client;
    private static boolean connected = false;

    private DiscordRPCManager() {}

    public static void init() {
        client = new IPCClient(APPLICATION_ID);
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
                updatePresence("In the menus", "Just launched");
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

    public static void updatePresence(String details, String state) {
        if (!connected) return;
        RichPresence presence = new RichPresence.Builder()
                .setActivityType(ActivityType.Playing)
                .setDetails(details)
                .setState(state)
                .setStartTimestamp(OffsetDateTime.now().toEpochSecond())
                .setLargeImage("icon_large", "My Mod")
                .build();
        client.sendRichPresence(presence);
    }

    public static void shutdown() {
        if (client != null) client.close();
    }
}
