package kpk.dev.CalendarGrid.widget.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.TextView;
import kpk.dev.CalendarGrid.R;
import kpk.dev.CalendarGrid.util.StyleHelper;
import kpk.dev.CalendarGrid.widget.models.CalendarModel;
import kpk.dev.CalendarGrid.widget.util.CalendarUtils;
import kpk.dev.CalendarGrid.widget.util.Constants;
import org.joda.time.DateTime;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: krasimir.karamazov
 * Date: 8/28/13
 * Time: 5:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class MonthGridAdapter extends BaseAdapter {
    //GETTERS AND SETTERS ARE AT THE BOTTOM
    protected DateTime mMinDate;
    protected DateTime mMaxDate;
    protected DateTime mToday;
    protected int mStartDayOfWeek;
    protected Resources mResources;

    protected List<CalendarModel> mDateTimeList;
    protected int mMonth;
    protected int mYear;
    protected Context mContext;
    protected List<DateTime> mDisabledDates;
    protected List<DateTime> mSelectedDates;

    //search optimization attempt
    protected Map<DateTime, Integer> mDisabledDatesMap = new HashMap<DateTime, Integer>();
    protected Map<DateTime, Integer> mSelectedDatesMap = new HashMap<DateTime, Integer>();

    private Map<String, Object> mInternalData;
    private Map<String, Object> mClientData;

    private View.OnTouchListener mTouchListener;
    private View.OnLongClickListener mLongClickListener;
    private int mParentWidth;
    private int mParentHeight;


    public MonthGridAdapter(Context context, int month, int year, Map<String, Object> internalData, Map<String, Object> clientData) {
        mContext = context;
        mMonth = month;
        mYear = year;
        mInternalData = internalData;
        mClientData = clientData;

        populateGrid();
    }

    public void setDimensions(Rect r) {
        mParentWidth = r.width();
        mParentHeight = r.height();
        notifyDataSetChanged();
    }

    private void populateGrid() {
        mDisabledDates = (List<DateTime>) mInternalData.get(Constants.DISABLED_DATES);
        if (mDisabledDates != null) {
            mDisabledDatesMap.clear();
            for (DateTime dateTime : mDisabledDates) {
                mDisabledDatesMap.put(dateTime, 1);
            }
        }

        mSelectedDates = (ArrayList<DateTime>) mInternalData.get(Constants.SELECTED_DATES);
        if (mSelectedDates != null) {
            mSelectedDatesMap.clear();
            for (DateTime dateTime : mSelectedDates) {
                mSelectedDatesMap.put(dateTime, 1);
            }
        }

        mMinDate = (DateTime) mInternalData.get(Constants.MIN_DATE);
        mMaxDate = (DateTime) mInternalData.get(Constants.MAX_DATE);
        mStartDayOfWeek = (Integer) mInternalData.get(Constants.START_DAY_OF_WEEK);

        mDateTimeList = CalendarUtils.getFullWeeks(mMonth, mYear, mStartDayOfWeek);
    }

    protected DateTime getToday() {
        if(mToday == null){
            mToday = CalendarUtils.convertDateToDateTime(new Date());
        }
        return mToday;
    }



    @Override
    public int getCount() {
        return mDateTimeList.size();  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Object getItem(int i) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public long getItemId(int i) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        TextView calendarCell = (TextView)view;
        if(calendarCell == null) {
            calendarCell = (TextView)inflater.inflate(R.layout.calendar_item, viewGroup, false);
        }
        calendarCell.setTextColor(Color.BLACK);

        calendarCell.setLayoutParams(new AbsListView.LayoutParams((mParentWidth / 7) - 2, (mParentHeight / 6) - 6));

        DateTime cellTime = mDateTimeList.get(i).getDateTime();

        boolean shouldResetDisabledView = false;
        boolean shouldResetSelectedView = false;
        //Check if date is in range
        if((mMinDate != null && cellTime.isBefore(mMinDate)) || (mMaxDate != null && cellTime.isAfter(mMaxDate)) || (mDisabledDates != null && mDisabledDates.contains(cellTime))){
            calendarCell.setTextColor(Color.MAGENTA);
            calendarCell.setBackgroundResource(R.drawable.disabled_date_drawable);
            if(cellTime.equals(mToday)) {
                calendarCell.setTextColor(StyleHelper.getInstance().getCurrentDateTextColor());
                calendarCell.setTextSize(TypedValue.COMPLEX_UNIT_SP, StyleHelper.getInstance().getCurrentDateTextSize());
                calendarCell.setBackgroundResource(StyleHelper.getInstance().getCurrentDateBackgroundDrawable());
            }
        }else{
            shouldResetDisabledView = true;
        }

        if(mSelectedDates != null && mSelectedDates.contains(cellTime)) {
            calendarCell.setBackgroundColor(mResources.getColor(Color.BLUE));
            calendarCell.setTextColor(mResources.getColor(Color.WHITE));
        }else{
           shouldResetSelectedView = true;
        }

        if (shouldResetDisabledView && shouldResetSelectedView) {
            if (cellTime.equals(getToday())) {
                calendarCell.setTextSize(TypedValue.COMPLEX_UNIT_SP, StyleHelper.getInstance().getCurrentDateTextSize());
                calendarCell.setTextColor(StyleHelper.getInstance().getCurrentDateTextColor());
                calendarCell.setBackgroundResource(StyleHelper.getInstance().getCurrentDateBackgroundDrawable());
            } else if(cellTime.getMonthOfYear() < this.mMonth) {
                calendarCell.setTextSize(TypedValue.COMPLEX_UNIT_SP, StyleHelper.getInstance().getPreviousMonthTextSize());
                calendarCell.setBackgroundResource(StyleHelper.getInstance().getPreviousMonthBackgroundDrawable());
                calendarCell.setTextColor(StyleHelper.getInstance().getPreviousMonthTextColor());
            }else if(cellTime.getMonthOfYear() > this.mMonth){
                calendarCell.setTextSize(TypedValue.COMPLEX_UNIT_SP, StyleHelper.getInstance().getNextMonthTextSize());
                calendarCell.setBackgroundResource(StyleHelper.getInstance().getNextMonthBackgroundDrawable());
                calendarCell.setTextColor(StyleHelper.getInstance().getNextMonthTextColor());
            }else{
                calendarCell.setTextSize(TypedValue.COMPLEX_UNIT_SP, StyleHelper.getInstance().getCurrentMonthTextSize());
                calendarCell.setBackgroundResource(StyleHelper.getInstance().getCurrentMonthBackgroundDrawable());
                calendarCell.setTextColor(StyleHelper.getInstance().getCurrentMonthTextColor());
            }
        }
        if(mDateTimeList.get(i).getInstances() != null && mDateTimeList.get(i).getInstances().size() > 0) {
            calendarCell.setTextColor(Color.MAGENTA);
        }

        calendarCell.setText(cellTime.getDayOfMonth() + "");

        return calendarCell;
    }

    public void setAdapterDateTime(DateTime dateTime) {
        if(dateTime != null){
            this.mMonth = dateTime.getMonthOfYear();
            this.mYear = dateTime.getYear();
            mDateTimeList = CalendarUtils.getFullWeeks(this.mMonth, this.mYear, mStartDayOfWeek);
        }else{
            mDateTimeList = CalendarUtils.getFullWeeks(this.mMonth, this.mYear, mStartDayOfWeek);
        }
    }

    public List<CalendarModel> getDateTimeList() {
        return mDateTimeList;
    }

    public void setTouchListener(View.OnTouchListener touchListener) {
        mTouchListener = touchListener;
    }

    public View.OnTouchListener getOnTouchListener() {
        return mTouchListener;
    }

    public View.OnLongClickListener getOnLongClickListener() {
        return mLongClickListener;
    }

    public void setOnLongClickListener(View.OnLongClickListener longTouchListener) {
        mLongClickListener = longTouchListener;
    }

    public void setMinDate(DateTime minDate) {
        mMinDate = minDate;
    }

    public DateTime getMinDate() {
        return mMinDate;
    }

    public void setMaxDate(DateTime maxDate) {
        mMaxDate = maxDate;
    }

    public DateTime getMaxDate() {
        return mMaxDate;
    }

    public void setToday(DateTime today) {
        mToday = today;
    }

    public void setStartDayOfWeek(int startDayOfWeek) {
        mStartDayOfWeek = startDayOfWeek;
    }

    public int getStartDayOfWeek() {
        return mStartDayOfWeek;
    }

    public List<DateTime> getDisabledDates() {
        return mDisabledDates;
    }

    public void setDisabledDate(List<DateTime> disabledDates) {
        mDisabledDates = disabledDates;
    }

    public List<DateTime> getSelectedDates() {
        return mSelectedDates;
    }

    public void setSelectedDates(List<DateTime> selectedDates) {
        mSelectedDates = selectedDates;
    }

    public Map<String, Object> getInternalData() {
        return mInternalData;
    }

    public void setInternalData(Map<String, Object> internalData) {
        mInternalData = internalData;
    }

    public Map<String, Object> getExternalData() {
        return mClientData;
    }

    public void setExternalData(Map<String, Object> externalData) {
        mClientData = externalData;
    }
}
