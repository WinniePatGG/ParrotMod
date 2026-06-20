package de.winniepat.parrotmod.ui;

import icyllis.modernui.fragment.Fragment;
import icyllis.modernui.util.DataSet;
import icyllis.modernui.view.LayoutInflater;
import icyllis.modernui.view.View;
import icyllis.modernui.view.ViewGroup;
import icyllis.modernui.widget.LinearLayout;
import icyllis.modernui.widget.Switch;
import icyllis.modernui.widget.TextView;
import net.minecraft.client.Minecraft;

public class DiscordTabFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, DataSet savedInstanceData) {
        LinearLayout layout = new LinearLayout(getContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(dp(12), dp(12), dp(12), dp(12));

        layout.addView(makeLabel("Enable DiscordRPC"));
        Switch toggleDiscordRPC = new Switch(getContext());
        toggleDiscordRPC.setChecked(true); // TODO: load from config
        toggleDiscordRPC.setOnCheckedChangeListener((v, checked) -> {
            // TODO: save to config
            // MyConfig.enableDiscordRPC = checked;
        });
        layout.addView(toggleDiscordRPC);

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
