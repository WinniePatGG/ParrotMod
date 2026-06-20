package de.winniepat.parrotmod.ui;

import de.winniepat.parrotmod.config.ConfigManager;
import icyllis.modernui.util.DataSet;
import icyllis.modernui.view.LayoutInflater;
import icyllis.modernui.view.View;
import icyllis.modernui.view.ViewGroup;
import icyllis.modernui.widget.LinearLayout;
import icyllis.modernui.widget.Switch;
import icyllis.modernui.widget.TextView;
import icyllis.modernui.view.Gravity;

public class DiscordTabFragment extends BaseTabFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, DataSet savedInstanceData) {
        LinearLayout layout = new LinearLayout(getContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(dp(20), dp(20), dp(20), dp(20));

        addSwitch(layout, "Enable DiscordRPC", ConfigManager.getInstance().enableDiscordRPC, (v, checked) -> {
            ConfigManager.getInstance().enableDiscordRPC = checked;
            ConfigManager.save();
        });

        addSwitch(layout, "Show Biome in RPC", ConfigManager.getInstance().showBiomeInRPC, (v, checked) -> {
            ConfigManager.getInstance().showBiomeInRPC = checked;
            ConfigManager.save();
        });

        addSwitch(layout, "Show Held Item in RPC", ConfigManager.getInstance().showHeldItemInRPC, (v, checked) -> {
            ConfigManager.getInstance().showHeldItemInRPC = checked;
            ConfigManager.save();
        });

        addSwitch(layout, "Show Health in RPC", ConfigManager.getInstance().showHealthInRPC, (v, checked) -> {
            ConfigManager.getInstance().showHealthInRPC = checked;
            ConfigManager.save();
        });

        return layout;
    }

    private void addSwitch(LinearLayout parent, String label, boolean checked, Switch.OnCheckedChangeListener listener) {
        LinearLayout row = new LinearLayout(getContext());
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setGravity(Gravity.CENTER_VERTICAL);
        var rowParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rowParams.bottomMargin = dp(12);
        row.setLayoutParams(rowParams);

        TextView tv = new TextView(getContext());
        tv.setText(label);
        tv.setTextSize(15);
        tv.setTextColor(0xFFEEEEEE);
        var labelParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
        row.addView(tv, labelParams);

        Switch s = new Switch(getContext());
        s.setChecked(checked);
        s.setOnCheckedChangeListener(listener);
        row.addView(s);

        parent.addView(row);
    }
}
