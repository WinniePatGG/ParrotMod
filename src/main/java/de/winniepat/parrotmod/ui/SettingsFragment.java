package de.winniepat.parrotmod.ui;

import icyllis.modernui.R;
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
                dp(520), dp(320), Gravity.CENTER));

        LinearLayout sidebar = new LinearLayout(getContext());
        sidebar.setOrientation(LinearLayout.VERTICAL);
        sidebar.setPadding(dp(8), dp(8), dp(8), dp(8));
        sidebar.setBackground(makeRoundedBg(0xCC1A1A2E));

        var sidebarParams = new LinearLayout.LayoutParams(dp(110), ViewGroup.LayoutParams.MATCH_PARENT);
        root.addView(sidebar, sidebarParams);

        FrameLayout contentArea = new FrameLayout(getContext());
        contentArea.setId(R.id.content);
        contentArea.setBackground(makeRoundedBg(0xCC101018));

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
            tab.setText(tabNames[i]);
            tab.setTextSize(13);
            tab.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
            tab.setPadding(dp(10), dp(6), dp(10), dp(6));
            tab.setBackground(null);

            tab.setOnClickListener(v -> {
                getChildFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(contentArea.getId(), tabFragments[index])
                        .commit();
                for (Button b : tabs) b.setTextColor(0xFFAAAAAA);
                tab.setTextColor(0xFFFFFFFF);
            });

            var tabParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, dp(32));
            tabParams.bottomMargin = dp(4);
            sidebar.addView(tab, tabParams);
            tabs[i] = tab;
        }

        tabs[0].setTextColor(0xFFFFFFFF);
        for (int i = 1; i < tabs.length; i++) tabs[i].setTextColor(0xFFAAAAAA);

        return root;
    }

    private int dp(float dp) {
        return (int) (dp * getContext().getResources().getDisplayMetrics().density);
    }

    private ShapeDrawable makeRoundedBg(int color) {
        ShapeDrawable d = new ShapeDrawable();
        d.setCornerRadius(dp(6));
        d.setColor(color);
        return d;
    }
}