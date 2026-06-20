package de.winniepat.parrotmod.mixin;

import de.winniepat.parrotmod.ui.SettingsFragment;
import icyllis.modernui.mc.MuiModApi;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.resources.Identifier;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public abstract class TitleScreenMixin extends Screen {

    protected TitleScreenMixin(Component title) {
        super(title);
    }

    @Inject(method = "init", at = @At("TAIL"))
    private void onInit(CallbackInfo ci) {
        WidgetSprites sprites = new WidgetSprites(Identifier.fromNamespaceAndPath("parrotmod", "icon"), Identifier.fromNamespaceAndPath("parrotmod", "icon"));
        this.addRenderableWidget(new ImageButton(this.width / 2 + 104, this.height / 4 + 48, 20, 20, sprites, b -> {
            MuiModApi.get().openScreen(new SettingsFragment());
        }));
    }
}