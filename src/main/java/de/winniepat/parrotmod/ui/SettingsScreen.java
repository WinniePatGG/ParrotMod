package de.winniepat.parrotmod.ui;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class SettingsScreen extends Screen {
    private int tabWidth = 100;
    private int currentTab = 0;

    public SettingsScreen(Component title) {
        super(title);
    }

    @Override
    protected void init() {
        this.addRenderableWidget(Button.builder(Component.literal("General"), (b) -> {
            this.currentTab = 0;
            this.rebuildWidgets();;
        }).bounds(10, 65, tabWidth, 20).build());

        this.addRenderableWidget(Button.builder(Component.literal("Graphics"), (b) -> {
            this.currentTab = 1;
            this.rebuildWidgets();
        }).bounds(10, 65, tabWidth, 20).build());

        if (currentTab == 0) {
            this.addRenderableWidget(Button.builder(Component.literal("Enable Mod"), (b) -> {
                System.out.println("Enable Mod clicked");
            }).bounds(tabWidth + 30, 40, 150, 20).build());
        }
    }

}
