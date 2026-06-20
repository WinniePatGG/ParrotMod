package de.winniepat.parrotmod.ui;

import icyllis.modernui.R;
import icyllis.modernui.animation.ObjectAnimator;
import icyllis.modernui.fragment.Fragment;
import icyllis.modernui.util.DataSet;
import icyllis.modernui.view.*;
import icyllis.modernui.widget.*;
import icyllis.modernui.graphics.drawable.ShapeDrawable;
import icyllis.modernui.graphics.*;
import net.fabricmc.loader.api.FabricLoader;

public class SettingsFragment extends Fragment {

    private static final int COLOR_BACKGROUND = 0xFF0F0F13;
    private static final int COLOR_SIDEBAR = 0xFF15151B;
    private static final int COLOR_ACCENT = 0xFF00E5FF;
    private static final int COLOR_TEXT_DIM = 0xFFAAAAAA;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, DataSet savedInstanceState) {
        // Main container with shadow/margin if needed, but here it's centering
        FrameLayout root = new FrameLayout(getContext());
        root.setLayoutParams(new FrameLayout.LayoutParams(dp(720), dp(480), Gravity.CENTER));

        // Background
        View bg = new View(getContext());
        bg.setBackground(makeRoundedBg(COLOR_BACKGROUND, 16));
        root.addView(bg);

        LinearLayout layout = new LinearLayout(getContext());
        layout.setOrientation(LinearLayout.HORIZONTAL);
        root.addView(layout);

        // Sidebar Container
        FrameLayout sidebarContainer = new FrameLayout(getContext());
        var sidebarParams = new LinearLayout.LayoutParams(dp(170), ViewGroup.LayoutParams.MATCH_PARENT);
        layout.addView(sidebarContainer, sidebarParams);

        View sidebarBg = new View(getContext());
        sidebarBg.setBackground(makeRoundedBg(COLOR_SIDEBAR, 16));
        // We only want right side to be sharp, but for simplicity we just overlap or use a large radius
        sidebarContainer.addView(sidebarBg);

        LinearLayout sidebarContent = new LinearLayout(getContext());
        sidebarContent.setOrientation(LinearLayout.VERTICAL);
        sidebarContent.setPadding(0, dp(24), 0, dp(16)); // Removed horizontal padding to allow edge indicator
        sidebarContainer.addView(sidebarContent, new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        TextView title = new TextView(getContext());
        title.setText("PARROT");
        title.setTextSize(22);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(COLOR_ACCENT);
        title.setPadding(dp(16), 0, dp(16), dp(32)); // Added horizontal padding back to title
        sidebarContent.addView(title);

        // Tabs container
        FrameLayout tabsContainer = new FrameLayout(getContext());
        sidebarContent.addView(tabsContainer, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 0, 1f));

        LinearLayout tabsLayout = new LinearLayout(getContext());
        tabsLayout.setOrientation(LinearLayout.VERTICAL);
        // Center tabs vertically in available space
        tabsContainer.addView(tabsLayout, new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER));


        // Content Area
        FrameLayout contentArea = new FrameLayout(getContext());
        contentArea.setId(R.id.content);
        var contentParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1f);
        layout.addView(contentArea, contentParams);

        String[] tabNames = {"Info", "General", "Discord"};
        Fragment[] tabFragments = {
                new InfoTabFragment(),
                new GeneralTabFragment(),
                new DiscordTabFragment()
        };

        Button[] tabButtons = new Button[tabNames.length];
        for (int i = 0; i < tabNames.length; i++) {
            final int index = i;
            Button tab = new Button(getContext());
            tab.setText(tabNames[index]);
            tab.setTextSize(14);
            tab.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
            tab.setPadding(dp(32), 0, dp(16), 0);
            tab.setBackground(null);
            tab.setTextColor(COLOR_TEXT_DIM);

            tab.setOnClickListener(v -> {
                getChildFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(contentArea.getId(), tabFragments[index])
                        .commit();

                for (int j = 0; j < tabButtons.length; j++) {
                    boolean selected = (j == index);
                    tabButtons[j].setTextColor(selected ? 0xFFFFFFFF : COLOR_TEXT_DIM);
                    tabButtons[j].setBackground(selected ? makeRoundedBg(0x12FFFFFF, 8) : null);
                }

            });

            var tabParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dp(44));
            tabParams.bottomMargin = dp(4);
            tabsLayout.addView(tab, tabParams);
            tabButtons[index] = tab;
        }

        // Footer
        TextView footer = new TextView(getContext());
        String version = FabricLoader.getInstance().getModContainer("parrotmod")
                .map(m -> m.getMetadata().getVersion().getFriendlyString()).orElse("?.?.?");
        footer.setText("ParrotMod v" + version);
        footer.setTextSize(11);
        footer.setTextColor(0x44FFFFFF);
        footer.setGravity(Gravity.CENTER);
        footer.setPadding(dp(16), dp(12), dp(16), dp(12));
        sidebarContent.addView(footer);

        // Entrance Animation
        root.setAlpha(0);
        root.setScaleX(0.96f);
        root.setScaleY(0.96f);
        ObjectAnimator.ofFloat(root, View.ALPHA, 0, 1).setDuration(450).start();
        ObjectAnimator.ofFloat(root, View.SCALE_X, 0.96f, 1).setDuration(450).start();
        ObjectAnimator.ofFloat(root, View.SCALE_Y, 0.96f, 1).setDuration(450).start();

        // Initial tab
        root.post(() -> {
            tabButtons[0].callOnClick();
        });

        return root;
    }

    private int dp(float dp) {
        return (int) (dp * getContext().getResources().getDisplayMetrics().density);
    }

    private ShapeDrawable makeRoundedBg(int color, float radius) {
        ShapeDrawable d = new ShapeDrawable();
        d.setCornerRadius(dp(radius));
        d.setColor(color);
        return d;
    }
}