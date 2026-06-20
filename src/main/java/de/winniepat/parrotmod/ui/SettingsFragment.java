package de.winniepat.parrotmod.ui;

import icyllis.modernui.R;
import icyllis.modernui.animation.ObjectAnimator;
import icyllis.modernui.fragment.Fragment;
import icyllis.modernui.util.DataSet;
import icyllis.modernui.view.*;
import icyllis.modernui.widget.*;
import icyllis.modernui.graphics.drawable.ShapeDrawable;
import icyllis.modernui.graphics.*;

public class SettingsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, DataSet savedInstanceState) {
        LinearLayout root = new LinearLayout(getContext());
        root.setOrientation(LinearLayout.HORIZONTAL);
        root.setLayoutParams(new FrameLayout.LayoutParams(
                dp(700), dp(450), Gravity.CENTER));

        root.setAlpha(0);
        root.setScaleX(0.95f);
        root.setScaleY(0.95f);
        ObjectAnimator.ofFloat(root, View.ALPHA, 0, 1).setDuration(400).start();
        ObjectAnimator.ofFloat(root, View.SCALE_X, 0.95f, 1).setDuration(400).start();
        ObjectAnimator.ofFloat(root, View.SCALE_Y, 0.95f, 1).setDuration(400).start();

        LinearLayout sidebar = new LinearLayout(getContext());
        sidebar.setOrientation(LinearLayout.VERTICAL);
        sidebar.setPadding(dp(12), dp(16), dp(12), dp(16));
        sidebar.setBackground(makeRoundedBg(0xEE161625));

        TextView title = new TextView(getContext());
        title.setText("ParrotMod");
        title.setTextSize(20);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(0xFFFFFFFF);
        title.setPadding(0, 0, 0, dp(20));
        sidebar.addView(title);

        var sidebarParams = new LinearLayout.LayoutParams(dp(160), ViewGroup.LayoutParams.MATCH_PARENT);
        root.addView(sidebar, sidebarParams);

        FrameLayout contentArea = new FrameLayout(getContext());
        contentArea.setId(R.id.content);
        contentArea.setBackground(makeRoundedBg(0xEE101018));

        var contentParams = new LinearLayout.LayoutParams(
                0, ViewGroup.LayoutParams.MATCH_PARENT, 1f);
        contentParams.leftMargin = dp(6);
        root.addView(contentArea, contentParams);

        String[] tabNames = {"Info", "General", "Discord"};
        Fragment[] tabFragments = {
                new InfoTabFragment(),
                new GeneralTabFragment(),
                new DiscordTabFragment()
        };

        getChildFragmentManager().beginTransaction()
                .replace(contentArea.getId(), tabFragments[0])
                .commitNow();

        Button[] tabs = new Button[tabNames.length];
        for (int i = 0; i < tabNames.length; i++) {
            final int index = i;
            Button tab = new Button(getContext());
            tab.setText(tabNames[index]);
            tab.setTextSize(15);
            tab.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
            tab.setPadding(dp(16), dp(8), dp(16), dp(8));
            tab.setBackground(null);

            tab.setOnClickListener(v -> {
                getChildFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(contentArea.getId(), tabFragments[index])
                        .commit();
                for (Button b : tabs) {
                    b.setTextColor(0xFFAAAAAA);
                    b.setBackground(null);
                }
                tab.setTextColor(0xFFFFFFFF);
                tab.setBackground(makeRoundedBg(0x33FFFFFF));
            });

            var tabParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, dp(40));
            tabParams.bottomMargin = dp(8);
            sidebar.addView(tab, tabParams);
            tabs[index] = tab;
        }

        tabs[0].setTextColor(0xFFFFFFFF);
        tabs[0].setBackground(makeRoundedBg(0x33FFFFFF));
        for (int i = 1; i < tabs.length; i++) {
            tabs[i].setTextColor(0xFFAAAAAA);
            tabs[i].setBackground(null);
        }

        return root;
    }

    private int dp(float dp) {
        return (int) (dp * getContext().getResources().getDisplayMetrics().density);
    }

    private ShapeDrawable makeRoundedBg(int color) {
        ShapeDrawable d = new ShapeDrawable();
        d.setCornerRadius(dp(12));
        d.setColor(color);
        return d;
    }
}