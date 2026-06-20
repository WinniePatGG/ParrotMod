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
        layout.setPadding(dp(28), dp(24), dp(28), dp(24));
        scrollView.addView(layout);

        addTabHeader(layout, "System Overview", "Detailed information about your environment and mod versions.");

        addSectionHeader(layout, "Software Manifest");
        String parrotVersion = FabricLoader.getInstance().getModContainer("parrotmod").orElseThrow().getMetadata().getVersion().getFriendlyString();
        layout.addView(createSettingCard("ParrotMod", "Stable Build v" + parrotVersion));

        String mcVersion = FabricLoader.getInstance().getModContainer("minecraft").orElseThrow().getMetadata().getVersion().getFriendlyString();
        layout.addView(createSettingCard("Minecraft Engine", Minecraft.getInstance().getLaunchedVersion() + " (" + mcVersion + ")"));

        String loaderVersion = FabricLoader.getInstance().getModContainer("fabricloader").orElseThrow().getMetadata().getVersion().getFriendlyString();
        layout.addView(createSettingCard("Fabric Loader", "Build " + loaderVersion));

        addSectionHeader(layout, "Runtime Environment");
        layout.addView(createSettingCard("Java Runtime", System.getProperty("java.version") + " [" + System.getProperty("os.arch") + "]"));
        layout.addView(createSettingCard("Operating System", System.getProperty("os.name") + " [" + System.getProperty("os.version") + "]"));

        long maxMemory = Runtime.getRuntime().maxMemory();
        long totalMemory = Runtime.getRuntime().totalMemory();
        long freeMemory = Runtime.getRuntime().freeMemory();
        long usedMemory = totalMemory - freeMemory;
        layout.addView(createSettingCard("Memory Allocation", String.format("%d MiB / %d MiB in use", usedMemory / 1024 / 1024, maxMemory / 1024 / 1024)));

        return scrollView;
    }
}
