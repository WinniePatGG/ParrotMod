package de.winniepat.parrotmod.ui;

import icyllis.modernui.util.DataSet;
import icyllis.modernui.view.LayoutInflater;
import icyllis.modernui.view.View;
import icyllis.modernui.view.ViewGroup;
import icyllis.modernui.widget.LinearLayout;
import icyllis.modernui.widget.ScrollView;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;

public class InfoTabFragment extends BaseTabFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, DataSet savedInstanceData) {
        ScrollView scrollView = new ScrollView(getContext());
        scrollView.setFillViewport(true);

        LinearLayout layout = new LinearLayout(getContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(dp(24), dp(8), dp(24), dp(24));
        scrollView.addView(layout);

        addSectionHeader(layout, "Mod Versions");
        String parrotVersion = FabricLoader.getInstance().getModContainer("parrotmod").orElseThrow().getMetadata().getVersion().getFriendlyString();
        layout.addView(createSettingCard("Parrotmod", parrotVersion));

        String mcVersion = FabricLoader.getInstance().getModContainer("minecraft").orElseThrow().getMetadata().getVersion().getFriendlyString();
        layout.addView(createSettingCard("Minecraft", Minecraft.getInstance().getLaunchedVersion() + " (" + mcVersion + ")"));

        String loaderVersion = FabricLoader.getInstance().getModContainer("fabricloader").orElseThrow().getMetadata().getVersion().getFriendlyString();
        layout.addView(createSettingCard("Fabric Loader", loaderVersion));

        addSectionHeader(layout, "System Info");
        layout.addView(createSettingCard("Java", System.getProperty("java.version") + " (" + System.getProperty("os.arch") + ")"));
        layout.addView(createSettingCard("OS", System.getProperty("os.name") + " (" + System.getProperty("os.version") + ")"));

        long maxMemory = Runtime.getRuntime().maxMemory();
        long totalMemory = Runtime.getRuntime().totalMemory();
        long freeMemory = Runtime.getRuntime().freeMemory();
        long usedMemory = totalMemory - freeMemory;
        layout.addView(createSettingCard("Memory Usage", String.format("%dMB / %dMB", usedMemory / 1024 / 1024, maxMemory / 1024 / 1024)));

        return scrollView;
    }
}
