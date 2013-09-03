package kpk.dev.CalendarGrid.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import kpk.dev.CalendarGrid.R;
import kpk.dev.CalendarGrid.listener.OnDateClickListener;
import kpk.dev.CalendarGrid.listener.OnDateLongClickListener;
import kpk.dev.CalendarGrid.util.StyleHelper;
import kpk.dev.CalendarGrid.widget.fragments.CalendarFragment;
import kpk.dev.CalendarGrid.widget.fragments.DayViewFragment;
import kpk.dev.CalendarGrid.widget.models.CalendarModel;
import kpk.dev.CalendarGrid.widget.util.CalendarState;
import org.joda.time.DateTime;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: krasimir.karamazov
 * Date: 8/29/13
 * Time: 4:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class RootView extends FrameLayout {
    private OnDateClickListener mClickListener;
    private OnDateLongClickListener mLongClickListener;
    public static final int ID = 666;
    private static final String FRAGMENT_TAG = "calendar_fragment";
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
        parseAttributes(attrs);
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

            styleHelper.setCurrentDateBackgroundDrawable(arr.getResourceId(R.styleable.MMCalendar_current_date_background_drawable, R.drawable.cell_bg));
            styleHelper.setNextMonthBackgroundDrawable(arr.getResourceId(R.styleable.MMCalendar_next_month_dates_background_drawable, R.drawable.calendar_item_backgorund_not_in_month));
            styleHelper.setPreviousMonthBackgroundDrawable(arr.getResourceId(R.styleable.MMCalendar_previous_month_dates_background_drawable, R.drawable.calendar_item_backgorund_not_in_month));
            styleHelper.setCurrentMonthBackgroundDrawable(arr.getResourceId(R.styleable.MMCalendar_dates_from_current_month_background_drawable, R.drawable.cell_bg));
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
        this.setId(ID);
        if(!(getContext() instanceof Activity)  && !(getContext() instanceof FragmentActivity)) {
            throw new IllegalStateException("The context must be from a fragment activity");
        }
    }

    public void setOnDateClickListener(OnDateClickListener listener) {
        mClickListener = listener;
    }

    public void setOnDateLongClickListener(OnDateLongClickListener listener) {
        mLongClickListener = listener;
    }

    public void showEvents(CalendarModel model) {
        CalendarFragment fragment = (CalendarFragment)((FragmentActivity) getContext()).getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
        if(fragment != null) {
            fragment.showDayView(model);
        }
    }

    public void showDayView(CalendarModel model) {
        Bundle args = new Bundle();
        args.putSerializable(DayViewFragment.CALENDAR_MODEL_ARGS_KEY, model);
        DayViewFragment dayViewFragment = DayViewFragment.getInstance(args);
        FragmentTransaction ft = ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction();
        ft.add(this.getId(), dayViewFragment, " ");
        ft.addToBackStack("bla");
        ft.commit();
        /*CalendarFragment fragment = (CalendarFragment)((FragmentActivity) getContext()).getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
        if(fragment != null) {
            fragment.showDayView(model);
        }*/
    }

    public void initCalendar(Bundle savedInstanceState) {
        CalendarFragment fragment = new CalendarFragment();
        fragment.setOnDateLongClickListener(mLongClickListener);
        fragment.setOnDateClickListener(mClickListener);
        FragmentTransaction ft = ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction();
        DateTime time = new DateTime(new Date());
        if(savedInstanceState != null){
            fragment.restoreStatesFromKey(savedInstanceState);
        }
        ft.replace(this.getId(), fragment, FRAGMENT_TAG);
        ft.commit();

    }

    @Override
    protected Parcelable onSaveInstanceState() {
        CalendarState state = null;
        CalendarFragment fragment = (CalendarFragment)((FragmentActivity)getContext()).getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
        if(fragment != null) {
            state = new CalendarState(super.onSaveInstanceState());
            state.setMonth(fragment.getCurrentMonth());
            state.setYear(fragment.getCurrentYear());
        }
        return state;    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if(state != null) {
            if(!(state instanceof CalendarState)) {
                super.onRestoreInstanceState(state);
                return;
            }
            CalendarState ss = (CalendarState)state;
            super.onRestoreInstanceState(ss.getSuperState());

            Bundle bundle = new Bundle();
            bundle.putInt(CalendarFragment.MONTH, ss.getMonth());
            bundle.putInt(CalendarFragment.YEAR, ss.getYear());
            initCalendar(bundle);
        }
    }
}
