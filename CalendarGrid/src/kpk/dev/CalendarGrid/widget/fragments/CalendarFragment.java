package kpk.dev.CalendarGrid.widget.fragments;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import kpk.dev.CalendarGrid.R;
import kpk.dev.CalendarGrid.listener.CalendarLoaderListener;
import kpk.dev.CalendarGrid.listener.OnDateClickListener;
import kpk.dev.CalendarGrid.listener.OnDateLongClickListener;
import kpk.dev.CalendarGrid.widget.util.CalendarMainLoader;
import kpk.dev.CalendarGrid.widget.adapters.InfinitePagerAdapter;
import kpk.dev.CalendarGrid.widget.adapters.MonthGridAdapter;
import kpk.dev.CalendarGrid.widget.adapters.MonthPagerAdapter;
import kpk.dev.CalendarGrid.widget.models.CalendarModel;
import kpk.dev.CalendarGrid.widget.util.CalendarUtils;
import kpk.dev.CalendarGrid.widget.util.StyleHelper;
import kpk.dev.CalendarGrid.widget.views.InfiniteViewPager;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class CalendarFragment extends DialogFragment implements CalendarLoaderListener {
    public static final int NUM_PAGES = 4;
    private static final String EVENTS_DIALOG_TAG = "events_dialog";
    public static final String START_DAY_OF_WEEK = "starting_day";
    private static final int LEFT = 0;
    private static final int RIGHT = 1;
    private TextView mMonthYearView;
    private InfiniteViewPager mViewPager;
    private CalendarPageChangeListener mPageChangeListener;
    private List<MonthGridFragment> mFragments;

    public final static String MONTH = "month";
    public final static String YEAR = "year";

    private int mMonth = -1;
    private int mYear = -1;
    private ArrayList<CalendarModel> mDatesList;
    private CalendarMainLoader mLoader;

    private int mStartDayOfWeek = ((Calendar.getInstance().getFirstDayOfWeek() + 5) % 7) + 1;;

    private List<MonthGridAdapter> mPageAdapters = new ArrayList<MonthGridAdapter>();

    private OnItemClickListener mDateItemClickListener;
    private OnItemLongClickListener dateItemLongClickListener;

    private OnDateClickListener mDateClickListener;
    private OnDateLongClickListener mDateLongClickListener;

    private int mParentHeight;

    public void setParentHeight(int parentHeight) {
        mParentHeight = parentHeight;
    }

    public MonthGridAdapter getNewDatesGridAdapter(int month, int year) {
        return new MonthGridAdapter(getActivity(), month, year, mParentHeight);
    }

    public int getCurrentYear() {
        return mYear;
    }

    public void setCurrentYear(int year) {
        mYear = year;
    }

    public int getCurrentMonth() {
        return mMonth;
    }

    public void setCurrentMonth(int month) {
        mMonth = month;
    }

    public TextView getMonthTitleTextView() {
        return mMonthYearView;
    }

    public void setMonthTitleTextView(TextView monthTitleTextView) {
        this.mMonthYearView = monthTitleTextView;
    }

    public Bundle getSavedStates() {
        Bundle bundle = new Bundle();
        bundle.putInt(MONTH, mMonth);
        bundle.putInt(YEAR, mYear);
        bundle.putInt(START_DAY_OF_WEEK, mStartDayOfWeek);

        return bundle;
    }

    public void restoreStatesFromKey(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            setArguments(savedInstanceState);
        }
    }

    public int getCurrentVirtualPosition() {
        int currentPage = mViewPager.getCurrentItem();
        return mPageChangeListener.getCurrent(currentPage);
    }

    public void moveToDate(Date date) {
        moveToDateTime(CalendarUtils.convertDateToDateTime(date));
    }

    public void moveToDateTime(DateTime dateTime) {

        DateTime firstOfMonth = new DateTime(mYear, mMonth, 1, 0, 0);
        DateTime lastOfMonth = firstOfMonth.dayOfMonth().withMaximumValue();

        if (dateTime.isBefore(firstOfMonth)) {

            DateTime firstDayNextMonth = dateTime.plusMonths(1);

            mPageChangeListener.setCurrentDateTime(firstDayNextMonth);
            int currentItem = mViewPager.getCurrentItem();
            mPageChangeListener.refreshAdapters(currentItem);

            mViewPager.setCurrentItem(currentItem - 1);
        }

        else if (dateTime.isAfter(lastOfMonth)) {
            DateTime firstDayLastMonth = dateTime.minusMonths(1);

            mPageChangeListener.setCurrentDateTime(firstDayLastMonth);
            int currentItem = mViewPager.getCurrentItem();
            mPageChangeListener.refreshAdapters(currentItem);

            mViewPager.setCurrentItem(currentItem + 1);
        }

    }

    public void setCalendarDate(Date date) {
        setCalendarDateTime(new DateTime(date));
    }

    public void setCalendarDateTime(DateTime dateTime) {
        mMonth = dateTime.getMonthOfYear();
        mYear = dateTime.getYear();
        /*if (caldroidListener != null) {
            caldroidListener.onChangeMonth(month, year);
        }*/

        refreshView();
    }

    public void prevMonth() {
        mViewPager.setCurrentItem(mPageChangeListener.getCurrentPage() - 1, true);
    }

    public void nextMonth() {
        mViewPager.setCurrentItem(mPageChangeListener.getCurrentPage() + 1, true);
    }

    public void setOnDateClickListener(OnDateClickListener onDateClickListener) {
        mDateClickListener = onDateClickListener;
    }

    public void setOnDateLongClickListener(OnDateLongClickListener onDateLongClickListener) {
        mDateLongClickListener = onDateLongClickListener;
    }

    private OnItemClickListener getDateClickListener() {
        mDateItemClickListener = new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CalendarModel model = mDatesList.get(position);
                if(mDateClickListener != null) {
                    mDateClickListener.onDateClick(model);
                }
            }
        };

        return mDateItemClickListener;
    }

    private OnItemLongClickListener getDateLongClickListener() {
        dateItemLongClickListener = new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                CalendarModel model = mDatesList.get(position);
                if(mDateLongClickListener != null) {
                    mDateLongClickListener.onDateLongClick(model);
                }
                return true;
            }
        };

        return dateItemLongClickListener;
    }

    public void refreshView() {
        mMonthYearView.setText(new DateTime(mYear, mMonth, 1, 0, 0, 0, 0).monthOfYear().getAsText().toUpperCase() + " " + mYear);

        for (MonthGridAdapter adapter : mPageAdapters) {
            adapter.refreshData();
            adapter.notifyDataSetChanged();
        }
    }

    private void retrieveInitialArgs() {
        // Get arguments
        Bundle args = getArguments();
        if (args != null) {
            // Get month, year
            mMonth = args.getInt(MONTH, -1);
            mYear = args.getInt(YEAR, -1);

            mStartDayOfWeek = args.getInt(START_DAY_OF_WEEK, DateTimeConstants.SUNDAY);
            if (mStartDayOfWeek > 7) {
                mStartDayOfWeek = mStartDayOfWeek % 7;
            }
        }
        if (mMonth == -1 || mYear == -1) {
            DateTime dateTime = new DateTime();
            mMonth = dateTime.getMonthOfYear();
            mYear = dateTime.getYear();
        }
    }

    public static CalendarFragment getInstance(int month, int year) {
        CalendarFragment f = new CalendarFragment();

        Bundle args = new Bundle();
        args.putInt(MONTH, month);
        args.putInt(YEAR, year);

        f.setArguments(args);

        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        retrieveInitialArgs();

        View view = inflater.inflate(R.layout.calendar_layout, container, false);
        setupArrows(view);
        mMonthYearView = (TextView) view.findViewById(R.id.month_year_view);

        iniDateGrids(view);

        refreshView();

        return view;
    }

    private void setupArrows(View view) {
        final ImageView leftArrow = (ImageView)view.findViewById(R.id.arrow_left);
        final ImageView rightArrow = (ImageView)view.findViewById(R.id.arrow_right);
        if(StyleHelper.getInstance().getShouldShowArrows()) {
            leftArrow.setImageResource(StyleHelper.getInstance().getLeftArrowDrawable());
            rightArrow.setImageResource(StyleHelper.getInstance().getRightArrowDrawable());
            rightArrow.setOnClickListener(getArrowOnClickListener(RIGHT));
            leftArrow.setOnClickListener(getArrowOnClickListener(LEFT));
        }else{
            leftArrow.setVisibility(View.GONE);
            rightArrow.setVisibility(View.GONE);
        }
    }

    private View.OnClickListener getArrowOnClickListener(final int direction) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch(direction){
                    case LEFT:
                        prevMonth();
                        break;
                    case RIGHT:
                        nextMonth();
                        break;
                }
            }
        };
    }

    private void iniDateGrids(View view) {

        DateTime currentDateTime = new DateTime(mYear, mMonth, 1, 0, 0, 0);
        mDatesList = CalendarUtils.getFullWeeks(mMonth, mYear, mStartDayOfWeek);
        mLoader = new CalendarMainLoader(getActivity());
        mLoader.setCalendarListener(this);
        mLoader.resetSearch(mDatesList.get(0).getDateTime(), mDatesList.get(mDatesList.size() - 1).getDateTime(), mDatesList);
        mPageChangeListener = new CalendarPageChangeListener();
        mPageChangeListener.setCurrentDateTime(currentDateTime);

        MonthGridAdapter adapterCurrent = getNewDatesGridAdapter(currentDateTime.getMonthOfYear(), currentDateTime.getYear());

        DateTime nextDateTime = currentDateTime.plusMonths(1);
        MonthGridAdapter adapterNext = getNewDatesGridAdapter( nextDateTime.getMonthOfYear(), nextDateTime.getYear());

        DateTime next2DateTime = nextDateTime.plusMonths(1);
        MonthGridAdapter adapterSecondNext = getNewDatesGridAdapter(next2DateTime.getMonthOfYear(), next2DateTime.getYear());

        DateTime prevDateTime = currentDateTime.minusMonths(1);
        MonthGridAdapter adapterPrev = getNewDatesGridAdapter(prevDateTime.getMonthOfYear(), prevDateTime.getYear());

        mPageAdapters.add(adapterCurrent);
        mPageAdapters.add(adapterNext);
        mPageAdapters.add(adapterSecondNext);
        mPageAdapters.add(adapterPrev);

        mPageChangeListener.setCalendarAdapters(mPageAdapters);

        mViewPager = (InfiniteViewPager) view.findViewById(R.id.pager);
        mViewPager.setDateInMonthsList(mDatesList);

        final MonthPagerAdapter pagerAdapter = new MonthPagerAdapter(getChildFragmentManager());

        mFragments = pagerAdapter.getFragments();
        for (int i = 0; i < NUM_PAGES; i++) {
            MonthGridFragment dateGridFragment = mFragments.get(i);
            MonthGridAdapter adapter = mPageAdapters.get(i);
            dateGridFragment.setParentHeight(mParentHeight);
            dateGridFragment.setGridAdapter(adapter);
            dateGridFragment.setOnItemClickListener(getDateClickListener());
            dateGridFragment.setOnItemLongClickListener(getDateLongClickListener());
        }

        InfinitePagerAdapter infinitePagerAdapter = new InfinitePagerAdapter(pagerAdapter);

        mViewPager.setAdapter(infinitePagerAdapter);

        mViewPager.setOnPageChangeListener(mPageChangeListener);
    }

    @Override
    public void dataReady(List<CalendarModel> models) {
        MonthGridAdapter adapter = mPageChangeListener.getCalendarAdapters().get(mPageChangeListener.getCurrent(mPageChangeListener.getCurrentPage()));
        adapter.getDateTimeList().clear();
        adapter.getDateTimeList().addAll(models);
        adapter.notifyDataSetChanged();
    }

    public class CalendarPageChangeListener implements OnPageChangeListener {
        private int currentPage = InfiniteViewPager.OFFSET;
        private DateTime currentDateTime;
        private List<MonthGridAdapter> calAdapters;


        public int getCurrentPage() {
            return currentPage;
        }

        public void setCurrentPage(int currentPage) {
            this.currentPage = currentPage;
        }


        public DateTime getCurrentDateTime() {
            return currentDateTime;
        }

        public void setCurrentDateTime(DateTime dateTime) {
            this.currentDateTime = dateTime;
            setCalendarDateTime(currentDateTime);
        }


        public List<MonthGridAdapter> getCalendarAdapters() {
            return calAdapters;
        }

        public void setCalendarAdapters(List<MonthGridAdapter> calendarAdapters) {
            this.calAdapters = calendarAdapters;
        }

        private int getNext(int position) {
            return (position + 1) % CalendarFragment.NUM_PAGES;
        }

        private int getPrevious(int position) {
            return (position + 3) % CalendarFragment.NUM_PAGES;
        }

        public int getCurrent(int position) {
            return position % CalendarFragment.NUM_PAGES;
        }

        @Override
        public void onPageScrollStateChanged(int position) {
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        public void refreshAdapters(int position) {
            MonthGridAdapter currentAdapter = calAdapters.get(getCurrent(position));
            MonthGridAdapter prevAdapter = calAdapters.get(getPrevious(position));
            MonthGridAdapter nextAdapter = calAdapters.get(getNext(position));

            if (position == currentPage) {
                currentAdapter.setAdapterDateTime(currentDateTime);
                currentAdapter.notifyDataSetChanged();

                prevAdapter.setAdapterDateTime(currentDateTime.minusMonths(1));
                prevAdapter.notifyDataSetChanged();

                nextAdapter.setAdapterDateTime(currentDateTime.plusMonths(1));
                nextAdapter.notifyDataSetChanged();
            }else if (position > currentPage) {
                currentDateTime = currentDateTime.plusMonths(1);
                nextAdapter.setAdapterDateTime(currentDateTime.plusMonths(1));
                nextAdapter.notifyDataSetChanged();

            }else {
                currentDateTime = currentDateTime.minusMonths(1);
                prevAdapter.setAdapterDateTime(currentDateTime.minusMonths(1));
                prevAdapter.notifyDataSetChanged();
            }
            currentPage = position;
        }

        @Override
        public void onPageSelected(int position) {
            refreshAdapters(position);
            setCalendarDateTime(currentDateTime);
            MonthGridAdapter currentAdapter = calAdapters.get(position % CalendarFragment.NUM_PAGES);
            mDatesList.clear();
            mDatesList.addAll(currentAdapter.getDateTimeList());
            mLoader.resetSearch(mDatesList.get(0).getDateTime(), mDatesList.get(mDatesList.size() - 1).getDateTime(), mDatesList);
        }

    }

}
