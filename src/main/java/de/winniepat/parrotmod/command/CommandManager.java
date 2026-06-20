package de.winniepat.parrotmod.command;

import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import java.util.ArrayList;
import java.util.List;

public class CommandManager {
    private static final List<Command> COMMANDS = new ArrayList<>();

    public static void init() {
        COMMANDS.add(new ParrotModCommand());

        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            for (Command command : COMMANDS) {
                command.register(dispatcher, registryAccess);
            }
        });
    }
}
