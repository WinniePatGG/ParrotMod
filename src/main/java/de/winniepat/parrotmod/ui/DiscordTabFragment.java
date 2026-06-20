package de.winniepat.parrotmod.ui;

import de.winniepat.parrotmod.config.ConfigManager;
import de.winniepat.parrotmod.ui.util.ParrotTheme;
import icyllis.modernui.animation.ObjectAnimator;
import icyllis.modernui.util.DataSet;
import icyllis.modernui.view.LayoutInflater;
import icyllis.modernui.view.View;
import icyllis.modernui.view.ViewGroup;
import icyllis.modernui.widget.LinearLayout;
import icyllis.modernui.widget.ScrollView;

public class DiscordTabFragment extends BaseTabFragment {

    private LinearLayout contentLayout;
    private LinearLayout subSettingsGroup;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, DataSet savedInstanceData) {
        ScrollView scroll = new ScrollView(getContext());

        contentLayout = new LinearLayout(getContext());
        contentLayout.setOrientation(LinearLayout.VERTICAL);
        contentLayout.setPadding(ParrotTheme.dp(40), ParrotTheme.dp(40), ParrotTheme.dp(40), ParrotTheme.dp(40));
        scroll.addView(contentLayout);

        addPageHeader(contentLayout, "Discord Rich Presence", "Broadcast your Minecraft activity.");

        addSectionLabel(contentLayout, "Core Integration");

        contentLayout.addView(createToggleCard("Enable Rich Presence", "Connect Parrot to your Discord client.",
                ConfigManager.getInstance().enableDiscordRPC, (v, checked) -> {
                    ConfigManager.getInstance().enableDiscordRPC = checked;
                    ConfigManager.save();
                    animateSubSettingsState(checked);
                }));

        addSectionLabel(contentLayout, "Activity Customization");

        subSettingsGroup = new LinearLayout(getContext());
        subSettingsGroup.setOrientation(LinearLayout.VERTICAL);
        contentLayout.addView(subSettingsGroup);

        subSettingsGroup.addView(createToggleCard("Location Tracking", "Broadcast your current biome and dimension.",
                ConfigManager.getInstance().showBiomeInRPC, (v, checked) -> {
                    ConfigManager.getInstance().showBiomeInRPC = checked;
                    ConfigManager.save();
                }));

        subSettingsGroup.addView(createToggleCard("Equipment Status", "Show your currently held items and armor.",
                ConfigManager.getInstance().showHeldItemInRPC, (v, checked) -> {
                    ConfigManager.getInstance().showHeldItemInRPC = checked;
                    ConfigManager.save();
                }));

        subSettingsGroup.addView(createToggleCard("Vitality Info", "Display your exact health and hunger.",
                ConfigManager.getInstance().showHealthInRPC, (v, checked) -> {
                    ConfigManager.getInstance().showHealthInRPC = checked;
                    ConfigManager.save();
                }));

        applySubSettingsState(ConfigManager.getInstance().enableDiscordRPC);

        return scroll;
    }

    private void animateSubSettingsState(boolean enabled) {
        float targetAlpha = enabled ? 1.0f : 0.4f;
        ObjectAnimator.ofFloat(subSettingsGroup, View.ALPHA, subSettingsGroup.getAlpha(), targetAlpha).setDuration(250).start();
        setChildrenEnabled(subSettingsGroup, enabled);
    }

    private void applySubSettingsState(boolean enabled) {
        subSettingsGroup.setAlpha(enabled ? 1.0f : 0.4f);
        setChildrenEnabled(subSettingsGroup, enabled);
    }

    private void setChildrenEnabled(ViewGroup group, boolean enabled) {
        for (int i = 0; i < group.getChildCount(); i++) {
            View child = group.getChildAt(i);
            child.setEnabled(enabled);
            if (child instanceof ViewGroup vg) setChildrenEnabled(vg, enabled);
        }
    }

    @Override
    public void onViewCreated(View view, DataSet savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        animateChildren(contentLayout);
    }
}