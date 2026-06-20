package de.winniepat.parrotmod.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import de.winniepat.parrotmod.command.subcommands.InfoCommand;
import de.winniepat.parrotmod.command.subcommands.ServerCommand;
import de.winniepat.parrotmod.command.subcommands.SettingsCommand;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;

public class ParrotModCommand implements Command {
    private final List<SubCommand> subCommands = new ArrayList<>();

    public ParrotModCommand() {
        subCommands.add(new SettingsCommand());
        subCommands.add(new InfoCommand());
        subCommands.add(new ServerCommand());
    }

    @Override
    public void register(CommandDispatcher<FabricClientCommandSource> dispatcher, CommandBuildContext registryAccess) {
        LiteralArgumentBuilder<FabricClientCommandSource> builder = LiteralArgumentBuilder.literal("parrotmod");

        for (SubCommand subCommand : subCommands) {
            subCommand.build(builder, registryAccess);
        }

        builder.executes(context -> {
            context.getSource().sendFeedback(Component.literal("ParrotMod is active! Use /parrotmod settings to open the configuration."));
            return 1;
        });

        dispatcher.register(builder);
    }
}
