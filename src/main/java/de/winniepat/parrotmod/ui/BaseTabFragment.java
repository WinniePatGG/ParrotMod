package de.winniepat.parrotmod.ui;

import de.winniepat.parrotmod.ui.util.ParrotTheme;
import icyllis.modernui.animation.ObjectAnimator;
import icyllis.modernui.fragment.Fragment;
import icyllis.modernui.view.Gravity;
import icyllis.modernui.view.View;
import icyllis.modernui.view.ViewGroup;
import icyllis.modernui.widget.LinearLayout;
import icyllis.modernui.widget.Switch;
import icyllis.modernui.widget.TextView;

public abstract class BaseTabFragment extends Fragment {

    protected void animateChildren(ViewGroup group) {
        for (int i = 0; i < group.getChildCount(); i++) {
            View child = group.getChildAt(i);
            child.setAlpha(0);
            child.setTranslationX(ParrotTheme.dp(20));

            ObjectAnimator alpha = ObjectAnimator.ofFloat(child, View.ALPHA, 0, 1);
            alpha.setDuration(400);
            alpha.setStartDelay(i * 40L);
            alpha.start();

            ObjectAnimator slide = ObjectAnimator.ofFloat(child, View.TRANSLATION_X, ParrotTheme.dp(20), 0);
            slide.setDuration(500);
            slide.setStartDelay(i * 40L);
            slide.start();
        }
    }

    protected void addPageHeader(LinearLayout parent, String title, String subtitle) {
        LinearLayout headerBox = new LinearLayout(getContext());
        headerBox.setOrientation(LinearLayout.VERTICAL);
        headerBox.setPadding(0, 0, 0, ParrotTheme.dp(24));

        TextView titleView = new TextView(getContext());
        titleView.setText(title);
        titleView.setTextSize(28);
        titleView.setTextColor(ParrotTheme.TEXT_TITLE);
        headerBox.addView(titleView);

        TextView subView = new TextView(getContext());
        subView.setText(subtitle);
        subView.setTextSize(14);
        subView.setTextColor(ParrotTheme.NEON_ACCENT);
        headerBox.addView(subView);

        parent.addView(headerBox);
    }

    protected void addSectionLabel(LinearLayout parent, String title) {
        TextView tv = new TextView(getContext());
        tv.setText(title.toUpperCase());
        tv.setTextSize(12);
        tv.setTextColor(ParrotTheme.TEXT_SUBTITLE);
        var p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        p.topMargin = ParrotTheme.dp(16);
        p.bottomMargin = ParrotTheme.dp(12);
        parent.addView(tv, p);
    }

    protected LinearLayout createToggleCard(String title, String desc, boolean isChecked, Switch.OnCheckedChangeListener listener) {
        LinearLayout card = new LinearLayout(getContext());
        card.setOrientation(LinearLayout.HORIZONTAL);
        card.setGravity(Gravity.CENTER_VERTICAL);
        card.setPadding(ParrotTheme.dp(20), ParrotTheme.dp(16), ParrotTheme.dp(20), ParrotTheme.dp(16));
        card.setBackground(ParrotTheme.createCard(12));

        var cardParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        cardParams.bottomMargin = ParrotTheme.dp(12);
        card.setLayoutParams(cardParams);

        LinearLayout textLayout = new LinearLayout(getContext());
        textLayout.setOrientation(LinearLayout.VERTICAL);
        card.addView(textLayout, new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));

        TextView titleView = new TextView(getContext());
        titleView.setText(title);
        titleView.setTextSize(16);
        titleView.setTextColor(ParrotTheme.TEXT_TITLE);
        textLayout.addView(titleView);

        TextView descView = new TextView(getContext());
        descView.setText(desc);
        descView.setTextSize(13);
        descView.setTextColor(ParrotTheme.TEXT_SUBTITLE);
        textLayout.addView(descView);

        Switch toggle = new Switch(getContext());
        toggle.setChecked(isChecked);
        toggle.setOnCheckedChangeListener(listener);
        card.addView(toggle);

        return card;
    }
}