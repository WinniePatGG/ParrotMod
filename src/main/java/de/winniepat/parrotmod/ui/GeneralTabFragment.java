package de.winniepat.parrotmod.ui;

import de.winniepat.parrotmod.config.ConfigManager;
import icyllis.modernui.util.DataSet;
import icyllis.modernui.view.LayoutInflater;
import icyllis.modernui.view.View;
import icyllis.modernui.view.ViewGroup;
import icyllis.modernui.widget.Button;
import icyllis.modernui.widget.LinearLayout;
import net.minecraft.client.Minecraft;

public class GeneralTabFragment extends BaseTabFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, DataSet savedInstanceData) {
        LinearLayout layout = new LinearLayout(getContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(dp(20), dp(20), dp(20), dp(20));

        layout.addView(makeLabel("Minecraft Version: " + Minecraft.getInstance().getLaunchedVersion()));

        Button reloadBtn = new Button(getContext());
        reloadBtn.setText("Reload Config");
        reloadBtn.setOnClickListener(v -> ConfigManager.load());
        var p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        p.topMargin = dp(24);
        layout.addView(reloadBtn, p);

        Button saveBtn = new Button(getContext());
        saveBtn.setText("Save Config");
        saveBtn.setOnClickListener(v -> ConfigManager.save());
        var p2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        p2.topMargin = dp(12);
        layout.addView(saveBtn, p2);

        return layout;
    }
}