package de.winniepat.parrotmod.ui.util;

import icyllis.modernui.graphics.drawable.ShapeDrawable;
import icyllis.modernui.util.DisplayMetrics;
import icyllis.modernui.ModernUI;

public class ParrotTheme {
    public static final int BG_WINDOW = 0xCC09090E;
    public static final int BG_CARD = 0x601A1A24;

    public static final int NEON_ACCENT = 0xFF00FF87;

    public static final int TEXT_TITLE = 0xFFFFFFFF;
    public static final int TEXT_SUBTITLE = 0xFFA0A5B5;
    public static final int TEXT_MUTED = 0xFF6B7280;

    public static final int BORDER_GLASS = 0x20FFFFFF;
    public static final int BORDER_CARD = 0x15FFFFFF;

    public static int dp(float dp) {
        DisplayMetrics metrics = ModernUI.getInstance().getResources().getDisplayMetrics();
        return (int) (dp * metrics.density);
    }

    public static ShapeDrawable createGlassPanel(float radius, float strokeWidth) {
        ShapeDrawable d = new ShapeDrawable();
        d.setCornerRadius(dp(radius));
        d.setColor(BG_WINDOW);
        if (strokeWidth > 0) d.setStroke(dp(strokeWidth), BORDER_GLASS);
        return d;
    }

    public static ShapeDrawable createCard(float radius) {
        ShapeDrawable d = new ShapeDrawable();
        d.setCornerRadius(dp(radius));
        d.setColor(BG_CARD);
        d.setStroke(dp(1), BORDER_CARD);
        return d;
    }
}