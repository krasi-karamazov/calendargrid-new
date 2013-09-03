package kpk.dev.CalendarGrid.widget.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


    public MonthGridAdapter(Context context, int month, int year) {
        mContext = context;
        mMonth = month;
        mYear = year;

        populateGrid();
    }

    public void setDimensions(Rect r) {
        //mParentWidth = r.width();
        //mParentHeight = r.height();
        notifyDataSetChanged();
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
        View calendarCellContainer = view;
        TextView calendarCell;
        if(calendarCellContainer == null) {
            calendarCellContainer = inflater.inflate(R.layout.calendar_item, viewGroup, false);

        }
        calendarCell = (TextView)calendarCellContainer.findViewById(R.id.calendar_tv);
        calendarCell.setTextColor(Color.BLACK);

        DateTime cellTime = mDateTimeList.get(i).getDateTime();

        if (cellTime.equals(getToday())) {
            calendarCellContainer.setBackgroundResource(R.drawable.today_drawable);

        } else if(cellTime.getMonthOfYear() < this.mMonth) {
            calendarCellContainer.setBackgroundResource(R.drawable.cell_bg_not_in_current_month);
            calendarCell.setTextColor(StyleHelper.getInstance().getPreviousMonthTextColor());
        }else if(cellTime.getMonthOfYear() > this.mMonth){
            calendarCellContainer.setBackgroundResource(R.drawable.cell_bg_not_in_current_month);
            calendarCell.setTextColor(StyleHelper.getInstance().getNextMonthTextColor());
        }else{
            calendarCellContainer.setBackgroundResource(R.drawable.cell_bg);
            calendarCell.setTextColor(StyleHelper.getInstance().getCurrentMonthTextColor());
        }

        if(mDateTimeList.get(i).getInstances() != null && mDateTimeList.get(i).getInstances().size() > 0) {
            calendarCell.setTextColor(Color.MAGENTA);
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

    public Map<String, Object> getExternalData() {
        return mClientData;
    }

    public void setExternalData(Map<String, Object> externalData) {
        mClientData = externalData;
    }
}
