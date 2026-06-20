package de.winniepat.parrotmod.command.subcommands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import de.winniepat.parrotmod.command.SubCommand;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.resolver.ServerAddress;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.network.chat.Component;
import net.minecraft.server.ServerInfo;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.attribute.EnvironmentAttribute;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.level.dimension.DimensionType;

public class ServerCommand implements SubCommand {
    @Override
    public void build(LiteralArgumentBuilder<FabricClientCommandSource> builder, CommandBuildContext registryAccess) {
        builder.then(LiteralArgumentBuilder.<FabricClientCommandSource>literal("server")
            .executes(context -> {
                Minecraft mc = Minecraft.getInstance();
                ServerData serverData = mc.getCurrentServer();

                if (serverData != null) {
                    context.getSource().sendFeedback(Component.literal("IP: " + serverData.ip));
                    context.getSource().sendFeedback(Component.literal("Port: " + getPort(serverData)));
                    context.getSource().sendFeedback(Component.literal("Motd:"));
                    context.getSource().sendFeedback(Component.literal(serverData.motd.getString()));
                    context.getSource().sendFeedback(Component.literal("Version: " + serverData.version.getString()));
                    context.getSource().sendFeedback(Component.literal("Protocol Version" + serverData.protocol));

                    String difficulty = mc.level.getDifficulty().getDisplayName().getString();
                    Difficulty diff = new DifficultyInstance(
                            mc.level.getDifficulty(),
                            mc.level.getGameTime(),
                            mc.level.getChunk(mc.player.blockPosition()).getInhabitedTime(),
                            DimensionType.MOON_BRIGHTNESS_PER_PHASE[mc.level.environmentAttributes().getValue(EnvironmentAttributes.MOON_PHASE, mc.player.blockPosition()).index()]
                    ).getDifficulty();

                    context.getSource().sendFeedback(Component.literal("Difficulty: " + difficulty + " (Local: " + diff.getDisplayName().getString() + ")"));
                    context.getSource().sendFeedback(Component.literal("Day: " + mc.level.getGameTime() / 24000L));
                }
                return 1;
            })
        );
    }

    public int getPort(ServerData serverData) {
        String rawAddress = serverData.ip;
        ServerAddress address = ServerAddress.parseString(rawAddress);

        return address.getPort();
    }
}
