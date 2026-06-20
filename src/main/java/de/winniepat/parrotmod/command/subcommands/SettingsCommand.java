package de.winniepat.parrotmod.command.subcommands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import de.winniepat.parrotmod.command.SubCommand;
import de.winniepat.parrotmod.ui.SettingsFragment;
import icyllis.modernui.mc.MuiModApi;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.commands.CommandBuildContext;

public class SettingsCommand implements SubCommand {
    @Override
    public void build(LiteralArgumentBuilder<FabricClientCommandSource> builder, CommandBuildContext registryAccess) {
        builder.then(LiteralArgumentBuilder.<FabricClientCommandSource>literal("settings")
            .executes(context -> {
                MuiModApi.get().openScreen(new SettingsFragment());
                return 1;
            })
        );
    }
}
