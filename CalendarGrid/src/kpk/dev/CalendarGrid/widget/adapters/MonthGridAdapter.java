package kpk.dev.CalendarGrid.widget.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.TextView;
import kpk.dev.CalendarGrid.R;
import kpk.dev.CalendarGrid.widget.util.StyleHelper;
import kpk.dev.CalendarGrid.widget.models.CalendarModel;
import kpk.dev.CalendarGrid.widget.util.CalendarUtils;
import kpk.dev.CalendarGrid.widget.util.Utils;
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

    protected List<CalendarModel> mDateTimeList;
    protected int mMonth;
    protected int mYear;
    protected Context mContext;
    protected List<DateTime> mDisabledDates;
    protected List<DateTime> mSelectedDates;

    private View.OnTouchListener mTouchListener;
    private View.OnLongClickListener mLongClickListener;
    private int mContainerHeight;


    public MonthGridAdapter(Context context, int month, int year, int containerHeight) {
        mContext = context;
        mMonth = month;
        mYear = year;
        mContainerHeight = containerHeight;
        populateGrid();
    }

    private void populateGrid() {
        
        mStartDayOfWeek = ((Calendar.getInstance().getFirstDayOfWeek() + 5) % 7) + 1;

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
        return mDateTimeList.size();
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
        View calendarCellContainer = view;
        TextView calendarCell;
        if(calendarCellContainer == null) {
            calendarCellContainer = inflater.inflate(R.layout.calendar_item, viewGroup, false);
            AbsListView.LayoutParams params = (AbsListView.LayoutParams)calendarCellContainer.getLayoutParams();
            params.height = (mContainerHeight / 6) - Utils.dpToPx(7, mContext);
            calendarCellContainer.setLayoutParams(params);
        }

        calendarCell = (TextView)calendarCellContainer.findViewById(R.id.calendar_tv);
        calendarCell.setTextColor(Color.BLACK);

        DateTime cellTime = mDateTimeList.get(i).getDateTime();

        if (cellTime.equals(getToday())) {
            calendarCellContainer.setBackgroundResource(StyleHelper.getInstance().getCurrentDateBackgroundDrawable());
            calendarCell.setTextColor(StyleHelper.getInstance().getCurrentDateTextColor());
        } else if(cellTime.getMonthOfYear() < this.mMonth) {
            calendarCellContainer.setBackgroundResource(StyleHelper.getInstance().getPreviousMonthBackgroundDrawable());
            calendarCell.setTextColor(StyleHelper.getInstance().getPreviousMonthTextColor());
        }else if(cellTime.getMonthOfYear() > this.mMonth){
            calendarCellContainer.setBackgroundResource(StyleHelper.getInstance().getNextMonthBackgroundDrawable());
            calendarCell.setTextColor(StyleHelper.getInstance().getNextMonthTextColor());
        }else{
            calendarCellContainer.setBackgroundResource(StyleHelper.getInstance().getCurrentMonthBackgroundDrawable());
            calendarCell.setTextColor(StyleHelper.getInstance().getCurrentMonthTextColor());
        }

        if(mDateTimeList.get(i).getInstances() != null && mDateTimeList.get(i).getInstances().size() > 0) {
            calendarCell.setTextColor(Color.MAGENTA);
            if(StyleHelper.getInstance().getEventDatesDrawable() != -1) {
                Drawable drawable = mContext.getResources().getDrawable(StyleHelper.getInstance().getEventDatesDrawable());
                calendarCell.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
            }
        }

        calendarCell.setText(cellTime.getDayOfMonth() + "");

        return calendarCellContainer;
    }

    public void setAdapterDateTime(DateTime dateTime) {
        if(dateTime != null){
            this.mMonth = dateTime.getMonthOfYear();
            this.mYear = dateTime.getYear();
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

    public void refreshData() {
        populateGrid();
    }
}
