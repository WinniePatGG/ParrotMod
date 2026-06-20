package de.winniepat.parrotmod.ui;

import de.winniepat.parrotmod.config.ConfigManager;
import icyllis.modernui.fragment.Fragment;
import icyllis.modernui.util.DataSet;
import icyllis.modernui.view.LayoutInflater;
import icyllis.modernui.view.View;
import icyllis.modernui.view.ViewGroup;
import icyllis.modernui.widget.LinearLayout;
import icyllis.modernui.widget.Switch;
import icyllis.modernui.widget.TextView;

public class DiscordTabFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, DataSet savedInstanceData) {
        LinearLayout layout = new LinearLayout(getContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(dp(12), dp(12), dp(12), dp(12));

        layout.addView(makeLabel("Enable DiscordRPC"));
        Switch toggleDiscordRPC = new Switch(getContext());
        toggleDiscordRPC.setChecked(ConfigManager.getInstance().enableDiscordRPC);
        toggleDiscordRPC.setOnCheckedChangeListener((v, checked) -> {
            ConfigManager.getInstance().enableDiscordRPC = checked;
            ConfigManager.save();
        });
        layout.addView(toggleDiscordRPC);

        layout.addView(makeLabel("Show Biome in RPC"));
        Switch toggleBiome = new Switch(getContext());
        toggleBiome.setChecked(ConfigManager.getInstance().showBiomeInRPC);
        toggleBiome.setOnCheckedChangeListener((v, checked) -> {
            ConfigManager.getInstance().showBiomeInRPC = checked;
            ConfigManager.save();
        });
        layout.addView(toggleBiome);

        layout.addView(makeLabel("Show Held Item in RPC"));
        Switch toggleHeldItem = new Switch(getContext());
        toggleHeldItem.setChecked(ConfigManager.getInstance().showHeldItemInRPC);
        toggleHeldItem.setOnCheckedChangeListener((v, checked) -> {
            ConfigManager.getInstance().showHeldItemInRPC = checked;
            ConfigManager.save();
        });
        layout.addView(toggleHeldItem);

        layout.addView(makeLabel("Show Health in RPC"));
        Switch toggleHealth = new Switch(getContext());
        toggleHealth.setChecked(ConfigManager.getInstance().showHealthInRPC);
        toggleHealth.setOnCheckedChangeListener((v, checked) -> {
            ConfigManager.getInstance().showHealthInRPC = checked;
            ConfigManager.save();
        });
        layout.addView(toggleHealth);

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
