package de.winniepat.parrotmod.ui;
import de.winniepat.parrotmod.config.ConfigManager;
import icyllis.modernui.util.DataSet;
import icyllis.modernui.view.LayoutInflater;
import icyllis.modernui.view.View;
import icyllis.modernui.view.ViewGroup;
import icyllis.modernui.widget.LinearLayout;
import icyllis.modernui.widget.Switch;
import icyllis.modernui.widget.ScrollView;

public class DiscordTabFragment extends BaseTabFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, DataSet savedInstanceData) {
        ScrollView scrollView = new ScrollView(getContext());
        scrollView.setFillViewport(true);

        LinearLayout layout = new LinearLayout(getContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(dp(24), dp(8), dp(24), dp(24));
        scrollView.addView(layout);

        addSectionHeader(layout, "Discord Integration");

        addSwitchRow(layout, "Enable DiscordRPC", "Show your game status on Discord",
                ConfigManager.getInstance().enableDiscordRPC, (v, checked) -> {
            ConfigManager.getInstance().enableDiscordRPC = checked;
            ConfigManager.save();
        });

        addSectionHeader(layout, "RPC Details");

        addSwitchRow(layout, "Show Biome", "Display current biome in status",
                ConfigManager.getInstance().showBiomeInRPC, (v, checked) -> {
            ConfigManager.getInstance().showBiomeInRPC = checked;
            ConfigManager.save();
        });

        addSwitchRow(layout, "Show Held Item", "Display what you are holding",
                ConfigManager.getInstance().showHeldItemInRPC, (v, checked) -> {
            ConfigManager.getInstance().showHeldItemInRPC = checked;
            ConfigManager.save();
        });

        addSwitchRow(layout, "Show Health", "Display your current HP",
                ConfigManager.getInstance().showHealthInRPC, (v, checked) -> {
            ConfigManager.getInstance().showHealthInRPC = checked;
            ConfigManager.save();
        });

        return scrollView;
    }

    private void addSwitchRow(LinearLayout parent, String title, String desc, boolean checked, Switch.OnCheckedChangeListener listener) {
        LinearLayout card = createSettingCard(title, desc);
        Switch s = new Switch(getContext());
        s.setChecked(checked);
        s.setOnCheckedChangeListener(listener);
        card.addView(s);
        parent.addView(card);
    }
}
