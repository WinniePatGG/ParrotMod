package de.winniepat.parrotmod.ui;

import icyllis.modernui.fragment.Fragment;
import icyllis.modernui.util.DataSet;
import icyllis.modernui.view.*;
import icyllis.modernui.widget.*;

public class GeneralTabFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, DataSet savedInstanceState) {
        LinearLayout layout = new LinearLayout(getContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(dp(12), dp(12), dp(12), dp(12));

        // --- A toggle/switch setting ---
        layout.addView(makeLabel("Enable feature X"));
        Switch toggleX = new Switch(getContext());
        toggleX.setChecked(true); // load from your config
        toggleX.setOnCheckedChangeListener((v, checked) -> {
            // save to config
            MyConfig.enableFeatureX = checked;
        });
        layout.addView(toggleX, rowParams());

        // --- Another toggle ---
        layout.addView(makeLabel("Show HUD overlay"));
        Switch toggleHUD = new Switch(getContext());
        toggleHUD.setChecked(MyConfig.showHud);
        toggleHUD.setOnCheckedChangeListener((v, checked) -> MyConfig.showHud = checked);
        layout.addView(toggleHUD, rowParams());

        // --- A slider ---
        layout.addView(makeLabel("Opacity"));
        SeekBar slider = new SeekBar(getContext());
        slider.setMax(100);
        slider.setProgress((int)(MyConfig.opacity * 100));
        slider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar bar, int progress, boolean fromUser) {
                if (fromUser) MyConfig.opacity = progress / 100f;
            }
            public void onStartTrackingTouch(SeekBar bar) {}
            public void onStopTrackingTouch(SeekBar bar) {}
        });
        layout.addView(slider, rowParams());

        return layout;
    }

    private TextView makeLabel(String text) {
        TextView tv = new TextView(getContext());
        tv.setText(text);
        tv.setTextSize(12);
        tv.setTextColor(0xFFCCCCCC);
        var p = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        p.topMargin = dp(8);
        p.bottomMargin = dp(2);
        tv.setLayoutParams(p);
        return tv;
    }

    private LinearLayout.LayoutParams rowParams() {
        return new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, dp(28));
    }

    private int dp(float dp) {
        return (int) (dp * getContext().getResources().getDisplayMetrics().density);
    }
}