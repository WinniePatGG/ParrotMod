package de.winniepat.parrotmod.ui;

import icyllis.modernui.animation.ObjectAnimator;
import icyllis.modernui.fragment.Fragment;
import icyllis.modernui.graphics.drawable.ShapeDrawable;
import icyllis.modernui.view.Gravity;
import icyllis.modernui.view.View;
import icyllis.modernui.view.ViewGroup;
import icyllis.modernui.widget.LinearLayout;
import icyllis.modernui.widget.TextView;

public abstract class BaseTabFragment extends Fragment {

    protected static final int COLOR_CARD = 0xFF1A1A22;
    protected static final int COLOR_ACCENT = 0xFF00E5FF;

    protected int dp(float dp) {
        return (int) (dp * getContext().getResources().getDisplayMetrics().density);
    }

    protected TextView makeLabel(String text) {
        TextView tv = new TextView(getContext());
        tv.setText(text);
        tv.setTextSize(14);
        tv.setTextColor(0xFFEEEEEE);
        var p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        p.topMargin = dp(12);
        p.bottomMargin = dp(4);
        tv.setLayoutParams(p);
        return tv;
    }

    protected void addSectionHeader(LinearLayout parent, String title) {
        TextView tv = new TextView(getContext());
        tv.setText(title.toUpperCase());
        tv.setTextSize(11);
        tv.setTextColor(COLOR_ACCENT);
        var p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        p.topMargin = dp(24);
        p.bottomMargin = dp(8);
        p.leftMargin = dp(4);
        parent.addView(tv, p);
    }

    protected LinearLayout createSettingCard(String title, String description) {
        LinearLayout card = new LinearLayout(getContext());
        card.setOrientation(LinearLayout.HORIZONTAL);
        card.setGravity(Gravity.CENTER_VERTICAL);
        card.setPadding(dp(16), dp(12), dp(16), dp(12));
        card.setBackground(makeRoundedBg(COLOR_CARD, 12));

        var cardParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        cardParams.bottomMargin = dp(8);
        card.setLayoutParams(cardParams);

        LinearLayout textLayout = new LinearLayout(getContext());
        textLayout.setOrientation(LinearLayout.VERTICAL);
        var textParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
        card.addView(textLayout, textParams);

        TextView titleView = new TextView(getContext());
        titleView.setText(title);
        titleView.setTextSize(15);
        titleView.setTextColor(0xFFFFFFFF);
        textLayout.addView(titleView);

        if (description != null) {
            TextView descView = new TextView(getContext());
            descView.setText(description);
            descView.setTextSize(12);
            descView.setTextColor(0xFF888888);
            textLayout.addView(descView);
        }

        return card;
    }

    protected ShapeDrawable makeRoundedBg(int color, float radius) {
        ShapeDrawable d = new ShapeDrawable();
        d.setCornerRadius(dp(radius));
        d.setColor(color);
        return d;
    }

    @Override
    public void onViewCreated(View view, icyllis.modernui.util.DataSet savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.setAlpha(0);
        view.setTranslationY(dp(10));
        ObjectAnimator.ofFloat(view, View.ALPHA, 0, 1).setDuration(400).start();
        ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, dp(10), 0).setDuration(400).start();
    }
}
