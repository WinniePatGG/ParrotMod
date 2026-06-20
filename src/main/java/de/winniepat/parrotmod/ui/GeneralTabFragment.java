package de.winniepat.parrotmod.ui;

import de.winniepat.parrotmod.Parrotmod;
import de.winniepat.parrotmod.config.ConfigManager;
import icyllis.modernui.fragment.Fragment;
import icyllis.modernui.util.DataSet;
import icyllis.modernui.view.*;
import icyllis.modernui.widget.*;
import net.minecraft.client.Minecraft;

public class GeneralTabFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, DataSet savedInstanceData) {
        LinearLayout layout = new LinearLayout(getContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(dp(12), dp(12), dp(12), dp(12));

        layout.addView(makeLabel("Minecraft Version: " + Minecraft.getInstance().getLaunchedVersion()));

        Button reloadBtn = new Button(getContext());
        reloadBtn.setText("Reload Config");
        reloadBtn.setOnClickListener(v -> ConfigManager.load());
        var p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        p.topMargin = dp(16);
        layout.addView(reloadBtn, p);

        Button saveBtn = new Button(getContext());
        saveBtn.setText("Save Config");
        saveBtn.setOnClickListener(v -> ConfigManager.save());
        layout.addView(saveBtn, p);

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