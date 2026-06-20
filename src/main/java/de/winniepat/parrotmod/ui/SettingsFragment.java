package de.winniepat.parrotmod.ui;

import de.winniepat.parrotmod.ui.util.ParrotTheme;
import icyllis.modernui.R;
import icyllis.modernui.animation.ObjectAnimator;
import icyllis.modernui.fragment.Fragment;
import icyllis.modernui.util.DataSet;
import icyllis.modernui.view.*;
import icyllis.modernui.widget.*;

public class SettingsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, DataSet savedInstanceState) {
        FrameLayout root = new FrameLayout(getContext());
        root.setLayoutParams(new FrameLayout.LayoutParams(ParrotTheme.dp(840), ParrotTheme.dp(580), Gravity.CENTER));

        View bg = new View(getContext());
        bg.setBackground(ParrotTheme.createGlassPanel(16, 1.5f));
        root.addView(bg);

        LinearLayout layout = new LinearLayout(getContext());
        layout.setOrientation(LinearLayout.HORIZONTAL);
        root.addView(layout);

        LinearLayout sidebar = new LinearLayout(getContext());
        sidebar.setOrientation(LinearLayout.VERTICAL);
        sidebar.setPadding(ParrotTheme.dp(20), ParrotTheme.dp(32), ParrotTheme.dp(20), ParrotTheme.dp(24));
        layout.addView(sidebar, new LinearLayout.LayoutParams(ParrotTheme.dp(220), ViewGroup.LayoutParams.MATCH_PARENT));

        TextView title = new TextView(getContext());
        title.setText("PARROT");
        title.setTextSize(26);
        title.setTextColor(ParrotTheme.NEON_ACCENT);
        title.setGravity(Gravity.CENTER_HORIZONTAL);
        sidebar.addView(title);

        TextView subtitle = new TextView(getContext());
        subtitle.setText("CONTROL CENTER");
        subtitle.setTextSize(10);
        subtitle.setTextColor(ParrotTheme.TEXT_MUTED);
        subtitle.setGravity(Gravity.CENTER_HORIZONTAL);
        subtitle.setPadding(0, 0, 0, ParrotTheme.dp(40));
        sidebar.addView(subtitle);

        LinearLayout tabsLayout = new LinearLayout(getContext());
        tabsLayout.setOrientation(LinearLayout.VERTICAL);
        sidebar.addView(tabsLayout, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1f));

        FrameLayout contentArea = new FrameLayout(getContext());
        contentArea.setId(R.id.content);
        layout.addView(contentArea, new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1f));

        String[] tabNames = {"Dashboard", "Client Settings", "Discord RPC"};
        Fragment[] tabFragments = { new InfoTabFragment(), new GeneralTabFragment(), new DiscordTabFragment() };

        View[] indicators = new View[tabNames.length];
        TextView[] textViews = new TextView[tabNames.length];
        View[] tabButtons = new View[tabNames.length];

        for (int i = 0; i < tabNames.length; i++) {
            final int index = i;
            FrameLayout tabBtn = new FrameLayout(getContext());
            tabButtons[i] = tabBtn;

            View indicator = new View(getContext());
            icyllis.modernui.graphics.drawable.ShapeDrawable indBg = new icyllis.modernui.graphics.drawable.ShapeDrawable();
            indBg.setCornerRadius(ParrotTheme.dp(4));
            indBg.setColor(ParrotTheme.NEON_ACCENT);
            indicator.setBackground(indBg);
            indicator.setAlpha(0f);

            tabBtn.addView(indicator, new FrameLayout.LayoutParams(ParrotTheme.dp(4), ParrotTheme.dp(24), Gravity.START | Gravity.CENTER_VERTICAL));

            TextView tabText = new TextView(getContext());
            tabText.setText(tabNames[index]);
            tabText.setTextSize(15);
            tabText.setTextColor(ParrotTheme.TEXT_SUBTITLE);
            tabText.setPadding(ParrotTheme.dp(20), ParrotTheme.dp(12), 0, ParrotTheme.dp(12));
            tabBtn.addView(tabText);

            tabBtn.setOnClickListener(v -> {
                getChildFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(contentArea.getId(), tabFragments[index])
                        .commit();

                for (int j = 0; j < tabNames.length; j++) {
                    boolean active = (j == index);
                    textViews[j].setTextColor(active ? ParrotTheme.TEXT_TITLE : ParrotTheme.TEXT_SUBTITLE);
                    ObjectAnimator.ofFloat(indicators[j], View.ALPHA, indicators[j].getAlpha(), active ? 1f : 0f).setDuration(200).start();
                }
            });

            var btnParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            btnParams.bottomMargin = ParrotTheme.dp(8);
            tabsLayout.addView(tabBtn, btnParams);

            indicators[index] = indicator;
            textViews[index] = tabText;
        }

        root.setAlpha(0);
        root.setScaleX(0.95f);
        root.setScaleY(0.95f);
        ObjectAnimator.ofFloat(root, View.ALPHA, 0, 1).setDuration(400).start();
        ObjectAnimator.ofFloat(root, View.SCALE_X, 0.95f, 1).setDuration(500).start();
        ObjectAnimator.ofFloat(root, View.SCALE_Y, 0.95f, 1).setDuration(500).start();

        root.post(() -> tabButtons[0].callOnClick());

        return root;
    }
}