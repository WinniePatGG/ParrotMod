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

    private static final int COLOR_BACKGROUND = 0xFF18181F;
    private static final int COLOR_SIDEBAR = 0xFF121217;
    private static final int COLOR_ACCENT = 0xFF5CF392;
    private static final int COLOR_TEXT_DIM = 0xFF9EA3AF;
    private static final int COLOR_TAB_SELECTED = 0x125CF392;
    private static final int COLOR_BORDER = 0x1AFFFFFF;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, DataSet savedInstanceState) {
        FrameLayout root = new FrameLayout(getContext());
        root.setLayoutParams(new FrameLayout.LayoutParams(dp(760), dp(520), Gravity.CENTER));

        View bg = new View(getContext());
        bg.setBackground(makeBorderedBg(COLOR_BACKGROUND, COLOR_BORDER, 14, 1.2f));
        root.addView(bg);

        LinearLayout layout = new LinearLayout(getContext());
        layout.setOrientation(LinearLayout.HORIZONTAL);
        root.addView(layout);

        FrameLayout sidebarContainer = new FrameLayout(getContext());
        var sidebarParams = new LinearLayout.LayoutParams(dp(190), ViewGroup.LayoutParams.MATCH_PARENT);
        layout.addView(sidebarContainer, sidebarParams);

        View sidebarBg = new View(getContext());
        sidebarBg.setBackground(makeRoundedBg(COLOR_SIDEBAR, 14));
        sidebarContainer.addView(sidebarBg);

        LinearLayout sidebarContent = new LinearLayout(getContext());
        sidebarContent.setOrientation(LinearLayout.VERTICAL);
        sidebarContent.setPadding(0, dp(28), 0, dp(16));
        sidebarContainer.addView(sidebarContent, new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        View separator = new View(getContext());
        var sepParams = new LinearLayout.LayoutParams(dp(1), ViewGroup.LayoutParams.MATCH_PARENT);
        separator.setOutlineAmbientShadowColor(COLOR_BORDER);
        layout.addView(separator, sepParams);

        LinearLayout titleLayout = new LinearLayout(getContext());
        titleLayout.setOrientation(LinearLayout.VERTICAL);
        titleLayout.setGravity(Gravity.CENTER);
        titleLayout.setPadding(0, 0, 0, dp(32));

        TextView title = new TextView(getContext());
        title.setText("PARROT");
        title.setTextSize(22);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(COLOR_ACCENT);
        titleLayout.addView(title);

        sidebarContent.addView(titleLayout);

        FrameLayout tabsContainer = new FrameLayout(getContext());
        sidebarContent.addView(tabsContainer, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 0, 1f));

        LinearLayout tabsLayout = new LinearLayout(getContext());
        tabsLayout.setOrientation(LinearLayout.VERTICAL);
        tabsLayout.setPadding(dp(12), 0, dp(12), 0);
        tabsContainer.addView(tabsLayout, new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.TOP));


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

        View[] tabContainers = new View[tabNames.length];
        View[] indicators = new View[tabNames.length];
        TextView[] textViews = new TextView[tabNames.length];

        for (int i = 0; i < tabNames.length; i++) {
            final int index = i;
            
            FrameLayout tabContainer = new FrameLayout(getContext());
            
            View indicator = new View(getContext());
            indicator.setBackground(makeRoundedBg(COLOR_ACCENT, 2));
            indicator.setVisibility(View.INVISIBLE);
            var indParams = new FrameLayout.LayoutParams(dp(3), dp(18), Gravity.START | Gravity.CENTER_VERTICAL);
            tabContainer.addView(indicator, indParams);

            TextView tabText = new TextView(getContext());
            tabText.setText(tabNames[index]);
            tabText.setTextSize(14);
            tabText.setPadding(dp(24), 0, dp(16), 0);
            tabText.setTextColor(COLOR_TEXT_DIM);
            tabText.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
            tabContainer.addView(tabText, new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, dp(40)));

            tabContainer.setOnClickListener(v -> {
                getChildFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(contentArea.getId(), tabFragments[index])
                        .commit();

                for (int j = 0; j < tabNames.length; j++) {
                    boolean selected = (j == index);
                    textViews[j].setTextColor(selected ? 0xFFFFFFFF : COLOR_TEXT_DIM);
                    tabContainers[j].setBackground(selected ? makeRoundedBg(COLOR_TAB_SELECTED, 8) : null);
                    indicators[j].setVisibility(selected ? View.VISIBLE : View.INVISIBLE);
                }
            });

            var tabParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dp(40));
            tabParams.bottomMargin = dp(6);
            tabsLayout.addView(tabContainer, tabParams);
            
            tabContainers[index] = tabContainer;
            indicators[index] = indicator;
            textViews[index] = tabText;
        }

        TextView footer = new TextView(getContext());
        String version = FabricLoader.getInstance().getModContainer("parrotmod")
                .map(m -> m.getMetadata().getVersion().getFriendlyString()).orElse("?.?.?");
        footer.setText("v" + version);
        footer.setTextSize(10);
        footer.setTextColor(0x33FFFFFF);
        footer.setGravity(Gravity.CENTER);
        footer.setPadding(dp(16), dp(12), dp(16), dp(12));
        sidebarContent.addView(footer);

        root.setAlpha(0);
        root.setScaleX(0.98f);
        root.setScaleY(0.98f);
        ObjectAnimator.ofFloat(root, View.ALPHA, 0, 1).setDuration(350).start();
        ObjectAnimator.ofFloat(root, View.SCALE_X, 0.98f, 1).setDuration(350).start();
        ObjectAnimator.ofFloat(root, View.SCALE_Y, 0.98f, 1).setDuration(350).start();

        root.post(() -> {
            tabContainers[0].callOnClick();
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

    private ShapeDrawable makeBorderedBg(int color, int strokeColor, float radius, float strokeWidth) {
        ShapeDrawable d = new ShapeDrawable();
        d.setCornerRadius(dp(radius));
        d.setColor(color);
        d.setStroke(dp(strokeWidth), strokeColor);
        return d;
    }
}