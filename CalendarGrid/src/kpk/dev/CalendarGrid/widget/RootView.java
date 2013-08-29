package kpk.dev.CalendarGrid.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import kpk.dev.CalendarGrid.R;
import kpk.dev.CalendarGrid.util.StyleHelper;
import kpk.dev.CalendarGrid.widget.fragments.CalendarFragment;

/**
 * Created with IntelliJ IDEA.
 * User: krasimir.karamazov
 * Date: 8/29/13
 * Time: 4:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class RootView extends FrameLayout {
    public RootView(Context context) {
        super(context);
        init();
    }

    public RootView(Context context, AttributeSet attrs) {
        super(context, attrs);
        parseAttributes(attrs);
        init();
    }

    public RootView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }
    private void parseAttributes(AttributeSet attrs) {
        TypedArray arr = getContext().obtainStyledAttributes(attrs, R.styleable.MMCalendar, 0, 0);
        final StyleHelper styleHelper = StyleHelper.getInstance();
        try{
            styleHelper.setCurrentMonthTextColor(arr.getColor(R.styleable.MMCalendar_dates_from_current_month_text_color, Color.BLACK));
            styleHelper.setNextMonthTextColor(arr.getColor(R.styleable.MMCalendar_dates_from_next_month_text_color, Color.LTGRAY));
            styleHelper.setPreviousMonthTextColor(arr.getColor(R.styleable.MMCalendar_dates_from_previous_month_text_color, Color.LTGRAY));
            styleHelper.setCurrentMonthTextSize(arr.getDimension(R.styleable.MMCalendar_dates_from_current_month_text_size, 14f));
            styleHelper.setNextMonthTextSize(arr.getDimension(R.styleable.MMCalendar_dates_from_next_month_text_size, 14f));
            styleHelper.setPreviousMonthTextSize(arr.getDimension(R.styleable.MMCalendar_dates_from_previous_month_text_size, 14f));

            styleHelper.setCurrentDateBackgroundDrawable(arr.getDrawable(R.styleable.MMCalendar_current_date_background_drawable));
            styleHelper.setNextMonthBackgroundDrawable(arr.getDrawable(R.styleable.MMCalendar_next_month_dates_background_drawable));
            styleHelper.setPreviousMonthBackgroundDrawable(arr.getDrawable(R.styleable.MMCalendar_previous_month_dates_background_drawable));
            styleHelper.setCurrentMonthBackgroundDrawable(arr.getDrawable(R.styleable.MMCalendar_dates_from_current_month_background_drawable));
            styleHelper.setTitleTextTextSize(arr.getDimension(R.styleable.MMCalendar_month_year_text_size, 20f));
            styleHelper.setTitleTextTextColor(arr.getColor(R.styleable.MMCalendar_month_year_text_color, Color.BLACK));

            styleHelper.setCurrentDateTextSize(arr.getDimension(R.styleable.MMCalendar_current_date_text_size, 14f));
            styleHelper.setCurrentDateTextColor(arr.getColor(R.styleable.MMCalendar_current_date_text_color, Color.RED));
            styleHelper.setTitleTextGravity(arr.getInteger(R.styleable.MMCalendar_title_text_gravity, 0));
            styleHelper.setShouldShowEvents(arr.getBoolean(R.styleable.MMCalendar_should_show_calendar_events, false));
            styleHelper.setCalendarName(arr.getString(R.styleable.MMCalendar_calendar_name));
        }finally {
            arr.recycle();
        }
    }

    private void init() {
        this.setId(666);
        if(!(getContext() instanceof Activity)  && !(getContext() instanceof FragmentActivity)) {
            throw new IllegalStateException("the parent must be a fragmentActivity");
        }
        FragmentTransaction ft = ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction();
        ft.add(this.getId(), new CalendarFragment(), "null");
        ft.commit();
    }
}
