package de.winniepat.parrotmod.ui;

import icyllis.modernui.animation.ObjectAnimator;
import icyllis.modernui.fragment.Fragment;
import icyllis.modernui.view.View;
import icyllis.modernui.view.ViewGroup;
import icyllis.modernui.widget.LinearLayout;
import icyllis.modernui.widget.TextView;

public abstract class BaseTabFragment extends Fragment {

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

    @Override
    public void onViewCreated(View view, icyllis.modernui.util.DataSet savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.setAlpha(0);
        view.setTranslationY(dp(20));
        ObjectAnimator.ofFloat(view, View.ALPHA, 0, 1).setDuration(400).start();
        ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, dp(20), 0).setDuration(400).start();
    }
}
