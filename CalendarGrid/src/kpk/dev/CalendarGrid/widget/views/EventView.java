package kpk.dev.CalendarGrid.widget.views;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.widget.Button;
import kpk.dev.CalendarGrid.R;

/**
 * Created with IntelliJ IDEA.
 * User: Krasimir
 * Date: 9/8/13
 * Time: 9:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class EventView extends Button {
    private long mEndTime;
    private long mStartTime;
    private String mTitle;
    private boolean mAllDay;
    public EventView(Context context, long startTime, long endTime, String title, int color) {
        super(context);
        LayerDrawable drawable = (LayerDrawable)context.getResources().getDrawable(R.drawable.btn_block);
        drawable.getDrawable(0).setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        setBackgroundDrawable(drawable);
    }

    public void setAllDay(boolean isAllDay) {
        mAllDay = isAllDay;
    }

    public boolean getAllDay() {
        return mAllDay;
    }

    public void setTitle(String title) {
        mTitle = title;
        this.setText(mTitle);
    }

    public String getTitle() {
        return mTitle;
    }

    public long getStartTime() {
        return mStartTime;
    }

    public void setStartTime(long startTime) {
        mStartTime = startTime;
    }

    public long getEndTime() {
        return mEndTime;
    }

    public void setEndTime(long endTime) {
        mEndTime = endTime;
    }
}
