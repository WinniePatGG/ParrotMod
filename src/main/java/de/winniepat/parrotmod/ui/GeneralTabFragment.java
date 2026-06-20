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
        layout.setPadding(dp(28), dp(24), dp(28), dp(24));

        addTabHeader(layout, "Client Settings", "Configure your client-side experience and mod data.");

        addSectionHeader(layout, "Information");
        layout.addView(createSettingCard("Version", "Running on Minecraft " + Minecraft.getInstance().getLaunchedVersion()));

        addSectionHeader(layout, "Data Management");

        LinearLayout reloadCard = createSettingCard("Reload configuration", "Discard unsaved changes and reload from disk.");
        Button reloadBtn = new Button(getContext());
        reloadBtn.setText("Reload");
        reloadBtn.setPadding(dp(16), 0, dp(16), 0);
        reloadBtn.setOnClickListener(v -> ConfigManager.load());
        reloadCard.addView(reloadBtn);
        layout.addView(reloadCard);

        LinearLayout saveCard = createSettingCard("Commit changes", "Save all current settings to the configuration file.");
        Button saveBtn = new Button(getContext());
        saveBtn.setText("Save");
        saveBtn.setPadding(dp(16), 0, dp(16), 0);
        saveBtn.setOnClickListener(v -> ConfigManager.save());
        saveCard.addView(saveBtn);
        layout.addView(saveCard);

        return layout;
    }
}