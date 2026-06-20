package de.winniepat.parrotmod.ui;
import de.winniepat.parrotmod.config.ConfigManager;
import icyllis.modernui.util.DataSet;
import icyllis.modernui.view.LayoutInflater;
import icyllis.modernui.view.View;
import icyllis.modernui.view.ViewGroup;
import icyllis.modernui.widget.LinearLayout;
import icyllis.modernui.widget.Switch;
import icyllis.modernui.widget.ScrollView;
import java.util.ArrayList;
import java.util.List;

public class DiscordTabFragment extends BaseTabFragment {

    private final List<View> rpcDetailViews = new ArrayList<>();

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
            updateRPCDetailsEnabled(checked);
        });

        addSectionHeader(layout, "RPC Details");

        rpcDetailViews.add(addSwitchRow(layout, "Show Biome", "Display current biome in status",
                ConfigManager.getInstance().showBiomeInRPC, (v, checked) -> {
            ConfigManager.getInstance().showBiomeInRPC = checked;
            ConfigManager.save();
        }));

        rpcDetailViews.add(addSwitchRow(layout, "Show Held Item", "Display what you are holding",
                ConfigManager.getInstance().showHeldItemInRPC, (v, checked) -> {
            ConfigManager.getInstance().showHeldItemInRPC = checked;
            ConfigManager.save();
        }));

        rpcDetailViews.add(addSwitchRow(layout, "Show Health", "Display your current HP",
                ConfigManager.getInstance().showHealthInRPC, (v, checked) -> {
            ConfigManager.getInstance().showHealthInRPC = checked;
            ConfigManager.save();
        }));

        updateRPCDetailsEnabled(ConfigManager.getInstance().enableDiscordRPC);

        return scrollView;
    }

    private void updateRPCDetailsEnabled(boolean enabled) {
        for (View v : rpcDetailViews) {
            v.setEnabled(enabled);
            v.setAlpha(enabled ? 1.0f : 0.5f);
            if (v instanceof ViewGroup) {
                setChildrenEnabled((ViewGroup) v, enabled);
            }
        }
    }

    private void setChildrenEnabled(ViewGroup vg, boolean enabled) {
        for (int i = 0; i < vg.getChildCount(); i++) {
            View child = vg.getChildAt(i);
            child.setEnabled(enabled);
            if (child instanceof ViewGroup) {
                setChildrenEnabled((ViewGroup) child, enabled);
            }
        }
    }

    private View addSwitchRow(LinearLayout parent, String title, String desc, boolean checked, Switch.OnCheckedChangeListener listener) {
        LinearLayout card = createSettingCard(title, desc);
        Switch s = new Switch(getContext());
        s.setChecked(checked);
        s.setOnCheckedChangeListener(listener);
        card.addView(s);
        parent.addView(card);
        return card;
    }
}
