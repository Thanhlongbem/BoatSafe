

package vnu.uet.boatsafe.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;


public final class ViewUtils {

    private ViewUtils() {
        // This utility class is not publicly instantiable
    }

    public static float pxToDp(float px) {
        float densityDpi = Resources.getSystem().getDisplayMetrics().densityDpi;
        return px / (densityDpi / 160f);
    }

    public static int dpToPx(float dp) {
        float density = Resources.getSystem().getDisplayMetrics().density;
        return Math.round(dp * density);
    }

    public static void changeIconDrawableColor(Context context, Drawable drawable, int color) {
        if (drawable != null) {
            drawable.mutate();
            drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        }
    }
}
