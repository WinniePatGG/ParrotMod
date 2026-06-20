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
        layout.setPadding(dp(24), dp(8), dp(24), dp(24));

        addSectionHeader(layout, "Client Info");
        layout.addView(createSettingCard("Minecraft Version", Minecraft.getInstance().getLaunchedVersion()));

        addSectionHeader(layout, "Config Management");

        LinearLayout reloadCard = createSettingCard("Reload Config", "Reset all settings to saved values");
        Button reloadBtn = new Button(getContext());
        reloadBtn.setText("Reload");
        reloadBtn.setOnClickListener(v -> ConfigManager.load());
        reloadCard.addView(reloadBtn);
        layout.addView(reloadCard);

        LinearLayout saveCard = createSettingCard("Save Config", "Persist current settings to disk");
        Button saveBtn = new Button(getContext());
        saveBtn.setText("Save");
        saveBtn.setOnClickListener(v -> ConfigManager.save());
        saveCard.addView(saveBtn);
        layout.addView(saveCard);

        return layout;
    }
}