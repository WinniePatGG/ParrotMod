package de.winniepat.parrotmod.mixin;

import de.winniepat.parrotmod.ui.SettingsScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button; // Updated import
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.Component;        // Replaces Text
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class TitleScreenMixin extends Screen {

    protected TitleScreenMixin(Component title) {
        super(title);
    }

    @Inject(at = @At("RETURN"), method = "init")
    private void addCustomButton(CallbackInfo ci) {
        int x = this.width / 2 - 100;
        int y = this.height / 4 + 120;

        this.addRenderableWidget(Button.builder(Component.literal("Parrot Mod"), (button) -> {
            Minecraft.getInstance().setScreen(new SettingsScreen(Component.literal("Settings")));
        }).bounds(x, y, 200, 20).build());
    }
}