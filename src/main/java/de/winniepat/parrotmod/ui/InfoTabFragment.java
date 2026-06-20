package de.winniepat.parrotmod.ui;

import de.winniepat.parrotmod.ui.util.ParrotTheme;
import icyllis.modernui.util.DataSet;
import icyllis.modernui.view.LayoutInflater;
import icyllis.modernui.view.View;
import icyllis.modernui.view.ViewGroup;
import icyllis.modernui.widget.LinearLayout;
import icyllis.modernui.widget.ScrollView;
import icyllis.modernui.widget.TextView;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;

public class InfoTabFragment extends BaseTabFragment {

    private LinearLayout contentLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, DataSet savedInstanceData) {
        ScrollView scroll = new ScrollView(getContext());
        scroll.setFillViewport(true);

        contentLayout = new LinearLayout(getContext());
        contentLayout.setOrientation(LinearLayout.VERTICAL);
        contentLayout.setPadding(ParrotTheme.dp(40), ParrotTheme.dp(40), ParrotTheme.dp(40), ParrotTheme.dp(40));
        scroll.addView(contentLayout);

        addPageHeader(contentLayout, "System Dashboard", "Real-time environment analytics");

        addSectionLabel(contentLayout, "Software Manifest");

        LinearLayout row1 = new LinearLayout(getContext());
        row1.setOrientation(LinearLayout.HORIZONTAL);
        row1.addView(createStatGrid("Parrot Engine", "v" + FabricLoader.getInstance().getModContainer("parrotmod").orElseThrow().getMetadata().getVersion().getFriendlyString()), new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));

        View spacer1 = new View(getContext());
        row1.addView(spacer1, new LinearLayout.LayoutParams(ParrotTheme.dp(16), 1));

        row1.addView(createStatGrid("Minecraft", Minecraft.getInstance().getLaunchedVersion()), new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
        contentLayout.addView(row1);

        addSectionLabel(contentLayout, "Hardware Status");

        LinearLayout row2 = new LinearLayout(getContext());
        row2.setOrientation(LinearLayout.HORIZONTAL);

        long maxMem = Runtime.getRuntime().maxMemory() / 1024 / 1024;
        long usedMem = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024 / 1024;

        row2.addView(createStatGrid("Memory Usage", usedMem + " / " + maxMem + " MB"), new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));

        View spacer2 = new View(getContext());
        row2.addView(spacer2, new LinearLayout.LayoutParams(ParrotTheme.dp(16), 1));

        row2.addView(createStatGrid("Architecture", System.getProperty("os.arch").toUpperCase()), new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
        contentLayout.addView(row2);

        return scroll;
    }

    private LinearLayout createStatGrid(String label, String value) {
        LinearLayout box = new LinearLayout(getContext());
        box.setOrientation(LinearLayout.VERTICAL);
        box.setPadding(ParrotTheme.dp(20), ParrotTheme.dp(20), ParrotTheme.dp(20), ParrotTheme.dp(20));
        box.setBackground(ParrotTheme.createCard(12));

        TextView valTxt = new TextView(getContext());
        valTxt.setText(value);
        valTxt.setTextSize(20);
        valTxt.setTextColor(ParrotTheme.NEON_ACCENT);
        box.addView(valTxt);

        TextView lblTxt = new TextView(getContext());
        lblTxt.setText(label.toUpperCase());
        lblTxt.setTextSize(11);
        lblTxt.setTextColor(ParrotTheme.TEXT_MUTED);
        lblTxt.setPadding(0, ParrotTheme.dp(4), 0, 0);
        box.addView(lblTxt);

        return box;
    }

    @Override
    public void onViewCreated(View view, DataSet savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        animateChildren(contentLayout);
    }
}