package kpk.dev.CalendarGrid.widget.util;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created with IntelliJ IDEA.
 * User: krasimir.karamazov
 * Date: 9/9/13
 * Time: 5:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class Utils {
    public static int dpToPx(int dp, Context context) {
        DisplayMetrics displayMetrics = context.getApplicationContext().getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }
}
