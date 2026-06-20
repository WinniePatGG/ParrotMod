package de.winniepat.parrotmod.ui;

import de.winniepat.parrotmod.config.ConfigManager;
import de.winniepat.parrotmod.ui.util.ParrotTheme;
import icyllis.modernui.util.DataSet;
import icyllis.modernui.view.LayoutInflater;
import icyllis.modernui.view.View;
import icyllis.modernui.view.ViewGroup;
import icyllis.modernui.widget.LinearLayout;
import icyllis.modernui.widget.ScrollView;

public class GeneralTabFragment extends BaseTabFragment {

    private LinearLayout contentLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, DataSet savedInstanceData) {
        ScrollView scroll = new ScrollView(getContext());

        contentLayout = new LinearLayout(getContext());
        contentLayout.setOrientation(LinearLayout.VERTICAL);
        contentLayout.setPadding(ParrotTheme.dp(40), ParrotTheme.dp(40), ParrotTheme.dp(40), ParrotTheme.dp(40));
        scroll.addView(contentLayout);

        addPageHeader(contentLayout, "Client Settings", "Customize your gameplay experience.");

        addSectionLabel(contentLayout, "Interface");

        contentLayout.addView(createToggleCard("Smooth Animations", "Enable fluid transitions across the mod GUI.",
                ConfigManager.getInstance().enableSmoothUI, (v, checked) -> {
                    ConfigManager.getInstance().enableSmoothUI = checked;
                    ConfigManager.save();
                }));

        contentLayout.addView(createToggleCard("Compact Mode", "Reduce padding to show more information at once.",
                ConfigManager.getInstance().enableCompactMode, (v, checked) -> {
                    ConfigManager.getInstance().enableCompactMode = checked;
                    ConfigManager.save();
                }));

        return scroll;
    }

    @Override
    public void onViewCreated(View view, DataSet savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        animateChildren(contentLayout);
    }
}