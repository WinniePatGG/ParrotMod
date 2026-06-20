package de.winniepat.parrotmod.command.subcommands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import de.winniepat.parrotmod.command.SubCommand;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.network.chat.Component;

public class InfoCommand implements SubCommand {
    @Override
    public void build(LiteralArgumentBuilder<FabricClientCommandSource> builder, CommandBuildContext registryAccess) {
        builder.then(LiteralArgumentBuilder.<FabricClientCommandSource>literal("info")
            .executes(context -> {
                context.getSource().sendFeedback(Component.literal("ParrotMod - A utility mod for Minecraft."));
                return 1;
            })
        );
    }
}
