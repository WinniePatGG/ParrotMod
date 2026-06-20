package de.winniepat.parrotmod.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.commands.CommandBuildContext;

public interface SubCommand {
    void build(LiteralArgumentBuilder<FabricClientCommandSource> builder, CommandBuildContext registryAccess);
}
