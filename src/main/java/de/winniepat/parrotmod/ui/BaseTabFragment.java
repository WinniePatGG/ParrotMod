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

    protected static final int COLOR_CARD = 0xFF23232D;
    protected static final int COLOR_ACCENT = 0xFF5CF392;
    protected static final int COLOR_ACCENT_DIM = 0xFF3E8E5A;
    protected static final int COLOR_TEXT_MAIN = 0xFFF0F0F0;
    protected static final int COLOR_TEXT_SEC = 0xFF9EA3AF;
    protected static final int COLOR_CARD_BORDER = 0x265CF392;

    protected int dp(float dp) {
        return (int) (dp * getContext().getResources().getDisplayMetrics().density);
    }

    protected TextView makeLabel(String text) {
        TextView tv = new TextView(getContext());
        tv.setText(text);
        tv.setTextSize(14);
        tv.setTextColor(COLOR_TEXT_MAIN);
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
        p.bottomMargin = dp(10);
        p.leftMargin = dp(4);
        parent.addView(tv, p);
    }

    protected void addTabHeader(LinearLayout parent, String title, String description) {
        TextView titleView = new TextView(getContext());
        titleView.setText(title);
        titleView.setTextSize(24);
        titleView.setTextColor(0xFFFFFFFF);
        titleView.setPadding(0, 0, 0, dp(2));
        parent.addView(titleView);

        if (description != null) {
            TextView descView = new TextView(getContext());
            descView.setText(description);
            descView.setTextSize(13);
            descView.setTextColor(COLOR_TEXT_SEC);
            descView.setPadding(0, 0, 0, dp(16));
            parent.addView(descView);
        }

        View divider = new View(getContext());
        var divParams = new LinearLayout.LayoutParams(dp(40), dp(3));
        divParams.bottomMargin = dp(16);
        divider.setLayoutParams(divParams);
        divider.setBackground(makeRoundedBg(COLOR_ACCENT, 2));
        parent.addView(divider);
    }

    protected LinearLayout createSettingCard(String title, String description) {
        LinearLayout card = new LinearLayout(getContext());
        card.setOrientation(LinearLayout.HORIZONTAL);
        card.setGravity(Gravity.CENTER_VERTICAL);
        card.setPadding(dp(18), dp(14), dp(18), dp(14));
        card.setBackground(makeBorderedBg(COLOR_CARD, COLOR_CARD_BORDER, 10, 1));

        var cardParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        cardParams.bottomMargin = dp(10);
        card.setLayoutParams(cardParams);

        LinearLayout textLayout = new LinearLayout(getContext());
        textLayout.setOrientation(LinearLayout.VERTICAL);
        var textParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
        card.addView(textLayout, textParams);

        TextView titleView = new TextView(getContext());
        titleView.setText(title);
        titleView.setTextSize(15.5f);
        titleView.setTextColor(COLOR_TEXT_MAIN);
        textLayout.addView(titleView);

        if (description != null) {
            TextView descView = new TextView(getContext());
            descView.setText(description);
            descView.setTextSize(12.5f);
            descView.setTextColor(COLOR_TEXT_SEC);
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

    protected ShapeDrawable makeBorderedBg(int color, int strokeColor, float radius, float strokeWidth) {
        ShapeDrawable d = new ShapeDrawable();
        d.setCornerRadius(dp(radius));
        d.setColor(color);
        d.setStroke(dp(strokeWidth), strokeColor);
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
