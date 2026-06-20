package de.winniepat.parrotmod.ui;

import de.winniepat.parrotmod.Parrotmod;
import icyllis.modernui.fragment.Fragment;
import icyllis.modernui.util.DataSet;
import icyllis.modernui.view.LayoutInflater;
import icyllis.modernui.view.View;
import icyllis.modernui.view.ViewGroup;
import icyllis.modernui.widget.LinearLayout;
import icyllis.modernui.widget.TextView;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;

public class InfoTabFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, DataSet savedInstanceData) {
        LinearLayout layout = new LinearLayout(getContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(dp(12), dp(12), dp(12), dp(12));

        String parrotVersion = FabricLoader.getInstance().getModContainer("parrotmod").orElseThrow().getMetadata().getVersion().getFriendlyString();
        layout.addView(makeLabel("Parrotmod version: " + parrotVersion));

        String mcVersion = FabricLoader.getInstance().getModContainer("minecraft").orElseThrow().getMetadata().getVersion().getFriendlyString();
        layout.addView(makeLabel("Minecraft Version: " + Minecraft.getInstance().getLaunchedVersion() + " (" + mcVersion + ")"));

        String loaderVersion = FabricLoader.getInstance().getModContainer("fabricloader").orElseThrow().getMetadata().getVersion().getFriendlyString();
        layout.addView(makeLabel("Fabric Loader Version: " + loaderVersion));

        int modCount = FabricLoader.getInstance().getAllMods().size();
        layout.addView(makeLabel("Loaded Mods: " + modCount));

        layout.addView(makeLabel("Java Version: " + System.getProperty("java.version") + " (" + System.getProperty("os.arch") + ")"));
        layout.addView(makeLabel("OS: " + System.getProperty("os.name") + " (" + System.getProperty("os.version") + ")"));

        long maxMemory = Runtime.getRuntime().maxMemory();
        long totalMemory = Runtime.getRuntime().totalMemory();
        long freeMemory = Runtime.getRuntime().freeMemory();
        long usedMemory = totalMemory - freeMemory;
        layout.addView(makeLabel(String.format("Memory: %dMB / %dMB", usedMemory / 1024 / 1024, maxMemory / 1024 / 1024)));

        return layout;
    }

    private TextView makeLabel(String text) {
        TextView tv = new TextView(getContext());
        tv.setText(text);
        tv.setTextSize(12);
        tv.setTextColor(0xFFCCCCCC);
        var p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        p.topMargin = dp(8);
        p.bottomMargin = dp(2);
        tv.setLayoutParams(p);
        return tv;
    }

    private int dp(float dp) {
        return (int) (dp * getContext().getResources().getDisplayMetrics().density);
    }
}
