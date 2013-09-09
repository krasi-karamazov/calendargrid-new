package kpk.dev.CalendarGrid.widget.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.format.Time;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import org.joda.time.DateTime;

/**
 * Created with IntelliJ IDEA.
 * User: Krasimir
 * Date: 9/7/13
 * Time: 12:42 AM
 * To change this template use File | Settings | File Templates.
 */
public class TimeView extends View {

    private int mHeaderWidth = 70;
    private int mHourHeight = 90;
    private boolean mHorizontalDivider = true;
    private int mLabelTextSize = 20;
    private int mLabelPaddingLeft = 8;
    private int mLabelColor = Color.BLACK;
    private int mDividerColor = Color.LTGRAY;
    private int mStartHour = 0;
    private int mEndHour = 24;

    public TimeView(Context context) {
        this(context, null);
    }

    public TimeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TimeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mHourHeight = dpToPx(60);
    }

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }

    /**
     *
     * @param timeMillis - required time
     * @return offset from top
     */
    public int getTimeVerticalOffset(long timeMillis) {
        DateTime time = new DateTime(timeMillis);
        final int minutes = ((time.getHourOfDay() - mStartHour) * 60) + time.getMinuteOfHour();
        return (minutes * mHourHeight) / 60;
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int hours = mEndHour - mStartHour;

        int width = mHeaderWidth;
        int height = mHourHeight * hours;

        setMeasuredDimension(resolveSize(width, widthMeasureSpec),
                resolveSize(height, heightMeasureSpec));
    }

    private Paint mDividerPaint = new Paint();
    private Paint mLabelPaint = new Paint();

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        final int hourHeight = mHourHeight;

        final Paint dividerPaint = mDividerPaint;
        dividerPaint.setColor(mDividerColor);
        dividerPaint.setStyle(Paint.Style.FILL);

        final Paint labelPaint = mLabelPaint;
        labelPaint.setColor(mLabelColor);
        labelPaint.setTextSize(mLabelTextSize);
        labelPaint.setTypeface(Typeface.DEFAULT_BOLD);
        labelPaint.setAntiAlias(true);

        final Paint.FontMetricsInt metrics = labelPaint.getFontMetricsInt();
        final int labelHeight = Math.abs(metrics.ascent);
        final int labelOffset = labelHeight + mLabelPaddingLeft;

        final int right = getRight();

        final int hours = mEndHour - mStartHour;
        for (int i = 0; i < hours; i++) {
            final int dividerY = hourHeight * i;
            final int nextDividerY = hourHeight * (i + 1);
            canvas.drawLine(0, dividerY, right, dividerY, dividerPaint);
            canvas.drawRect(0, dividerY, mHeaderWidth, nextDividerY, dividerPaint);

            final int hour = mStartHour + i;
            String label;
            if (hour == 0) {
                label = "12am";
            } else if (hour <= 11) {
                label = hour + "am";
            } else if (hour == 12) {
                label = "12pm";
            } else {
                label = (hour - 12) + "pm";
            }

            final float labelWidth = labelPaint.measureText(label);

            canvas.drawText(label, 0, label.length(), mHeaderWidth - labelWidth
                    - mLabelPaddingLeft, dividerY + labelOffset, labelPaint);
        }
    }

    public int getHeaderWidth() {
        return mHeaderWidth;
    }
}

