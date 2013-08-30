package kpk.dev.CalendarGrid.widget.fragments;


import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import kpk.dev.CalendarGrid.R;

import kpk.dev.CalendarGrid.listener.CalendarLoaderListener;
import kpk.dev.CalendarGrid.listener.OnDateClickListener;
import kpk.dev.CalendarGrid.listener.OnDateLongClickListener;
import kpk.dev.CalendarGrid.util.CalendarMainLoader;
import kpk.dev.CalendarGrid.util.LogHelper;
import kpk.dev.CalendarGrid.util.StyleHelper;
import kpk.dev.CalendarGrid.widget.adapters.InfinitePagerAdapter;
import kpk.dev.CalendarGrid.widget.adapters.MonthPagerAdapter;
import kpk.dev.CalendarGrid.widget.adapters.MonthGridAdapter;
import kpk.dev.CalendarGrid.widget.models.CalendarModel;
import kpk.dev.CalendarGrid.widget.util.CalendarUtils;
import kpk.dev.CalendarGrid.widget.util.Constants;
import kpk.dev.CalendarGrid.widget.views.InfiniteViewPager;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: krasimir.karamazov
 * Date: 8/28/13
 * Time: 7:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class CalendarFragment extends Fragment implements CalendarLoaderListener {
    private List<MonthGridAdapter> mAdapters;
    private InfiniteViewPager mPager;
    private InfinitePageChangeListener mPageChangeListener;
    private List<CalendarModel> mDates;
    private DateTime mCurrentDate;
    private TextView mTitleView;
    private int mCurrentMonth;
    private int mCurrentYear;
    private CalendarMainLoader mCalendarMainLoader;
    private OnDateClickListener mOnDateClickListener;
    private OnDateLongClickListener mOnDateLongClickListener;
    public static final String EVENTS_DIALOG_TAG = "events_dialog_tag";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.calendar_layout, container, false);
        mCalendarMainLoader = new CalendarMainLoader(getActivity());
        mCalendarMainLoader.setCalendarListener(this);
        mCurrentDate = new DateTime(new Date());
        mDates = CalendarUtils.getFullWeeks(mCurrentDate.getMonthOfYear(), mCurrentDate.getYear(), DateTimeConstants.SUNDAY);
        mPager = (InfiniteViewPager)rootView.findViewById(R.id.pager);
        mPager.setDatesList(mDates);

        mTitleView = (TextView)rootView.findViewById(R.id.month_year_label);
        mTitleView.setTextSize(TypedValue.COMPLEX_UNIT_SP, StyleHelper.getInstance().getTitleTextTextSize());
        mTitleView.setTextColor(StyleHelper.getInstance().getTitleTextTextColor());
        mPageChangeListener = new InfinitePageChangeListener();
        mPageChangeListener.setDateTime(mCurrentDate);
        initGridAdapters(mCurrentDate);
        final MonthPagerAdapter monthPagerAdapter = new MonthPagerAdapter(getActivity().getSupportFragmentManager());
        List<MonthGridFragment> fragments = monthPagerAdapter.getFragments();
        for(int i = 0; i < Constants.MAX_NUM_PAGES; i++){
            MonthGridFragment fragment = fragments.get(i);
            fragment.setGridAdapter(mAdapters.get(i));
            fragment.setOnItemClickListener(getOnItemClickListener());
            fragment.setOnItemLongClickListener(getInItemLongClickListener());
        }
        InfinitePagerAdapter infinitePagerAdapter = new InfinitePagerAdapter(monthPagerAdapter);
        mPager.setOnPageChangeListener(mPageChangeListener);
        mPager.setAdapter(infinitePagerAdapter);
        mPager.setPageMargin(0);


        return rootView;
    }

    public void showEvents(CalendarModel model) {
        final Bundle args = new Bundle();
        args.putSerializable(EventsListDialogFragment.CALENDAR_MODEL_ARGS_KEY, model);
        EventsListDialogFragment dialog = EventsListDialogFragment.getInstance(args);
        dialog.show(getActivity().getSupportFragmentManager(), EVENTS_DIALOG_TAG);
    }

    @Override
    public void onStart() {
        super.onStart();
        final DateTime pageDateTime = mPageChangeListener.getDateTime();
        mCalendarMainLoader.resetSearch(pageDateTime, pageDateTime.plusMonths(1), mDates);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPager.post(new Runnable() {
            @Override
            public void run() {
                for(MonthGridAdapter adapter : mAdapters) {
                    Rect r = new Rect();
                    r.set(0, 0, mPager.getWidth(), mPager.getHeight());
                    adapter.setDimensions(r);
                }
            }
        });
    }

    public void setOnDateClickListener(OnDateClickListener clickListener) {
        mOnDateClickListener = clickListener;
    }

    public void setOnDateLongClickListener(OnDateLongClickListener longClickListener) {
        mOnDateLongClickListener = longClickListener;
    }

    private AdapterView.OnItemClickListener getOnItemClickListener() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(mOnDateClickListener != null) {
                    mOnDateClickListener.onDateClick(mDates.get(position));
                }else{
                    LogHelper.d("native behaviour");
                }
            }
        };
    }

    private AdapterView.OnItemLongClickListener getInItemLongClickListener() {
        return new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if(mOnDateLongClickListener != null) {
                    mOnDateLongClickListener.onDateLongClick(mDates.get(position));
                }else{
                    LogHelper.d("native behaviour");
                }
                return true;
            }
        };
    }

    private void initGridAdapters(DateTime currentDate) {
        mAdapters = new ArrayList<MonthGridAdapter>();
        MonthGridAdapter adapterCurrent = generateAdapter(currentDate.getMonthOfYear(), currentDate.getYear());
        DateTime nextMonth = currentDate.plusMonths(1);
        MonthGridAdapter adapterNext = generateAdapter(nextMonth.getMonthOfYear(), nextMonth.getYear());
        DateTime secondNextMonth = nextMonth.plusMonths(1);
        MonthGridAdapter adapterSecondNext = generateAdapter(secondNextMonth.getMonthOfYear(), secondNextMonth.getYear());
        DateTime previousMonth = currentDate.minusMonths(1);
        MonthGridAdapter adapterPrevious = generateAdapter(previousMonth.getMonthOfYear(), previousMonth.getYear());
        mAdapters.add(adapterCurrent);
        mAdapters.add(adapterNext);
        mAdapters.add(adapterSecondNext);
        mAdapters.add(adapterPrevious);
    }

    private MonthGridAdapter generateAdapter(int month, int year) {
        final MonthGridAdapter adapter = new MonthGridAdapter(getActivity(), month, year, getInternalCalendarData(), null);
        return adapter;
    }

    private Map<String, Object> getInternalCalendarData() {
        final Map<String, Object> internalData = new HashMap<String, Object>();
        internalData.put(Constants.DISABLED_DATES, null);
        internalData.put(Constants.SELECTED_DATES, null);
        internalData.put(Constants.MIN_DATE, null);
        internalData.put(Constants.MIN_DATE, null);
        internalData.put(Constants.START_DAY_OF_WEEK, DateTimeConstants.SUNDAY);
        return internalData;
    }

    public void setCalendarDate(Date date) {
        setCalendarDateTime(new DateTime(date));
    }

    public void setCalendarDateTime(DateTime dateTime) {
        //month = dateTime.getMonthOfYear();
        //year = dateTime.getYear();

        // Notify listener
        /*if (caldroidListener != null) {
            caldroidListener.onChangeMonth(month, year);
        }*/

        refreshView();
    }

    public void refreshView() {



        // Refresh the date grid views
        for (MonthGridAdapter adapter : mAdapters) {
            // Reset caldroid data
            adapter.setInternalData(getInternalCalendarData());

            // Reset extra data
            adapter.setExternalData(null);

            // Refresh view
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void dataReady(List<CalendarModel> models) {
        mDates.clear();
        mDates.addAll(models);
        mPageChangeListener.getCurrentAdaper().notifyDataSetChanged();
    }

    private class InfinitePageChangeListener implements ViewPager.OnPageChangeListener {
        private DateTime mDateTime;
        private int mCurrentPage = InfiniteViewPager.OFFSET;
        private MonthGridAdapter mCurrentAdapter;

        public void setCurrentPage(int currentPage) {
            this.mCurrentPage = currentPage;
        }

        public int getCurrentPage() {
            return mCurrentPage;
        }

        public void setDateTime(DateTime dateTime) {
            mDateTime = dateTime;
        }

        public DateTime getDateTime() {
            return mDateTime;
        }

        @Override
        public void onPageScrolled(int i, float v, int i2) {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void onPageSelected(int i) {
            refreshAdapters(i);
            mCurrentAdapter = mAdapters.get(i % Constants.MAX_NUM_PAGES);

            // Refresh dateInMonthsList
            mDates.clear();
            mDates.addAll(mCurrentAdapter.getDateTimeList());
            mCalendarMainLoader.resetSearch(mDateTime, mDateTime.plusMonths(1), mDates);
        }

        private MonthGridAdapter getCurrentAdaper() {
            return mCurrentAdapter;
        }

        @Override
        public void onPageScrollStateChanged(int i) {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        private int getPrevious(int position) {
            return (position + 3) % Constants.MAX_NUM_PAGES;
        }

        private int getCurrent(int position) {
            return position % Constants.MAX_NUM_PAGES;
        }

        private int getNext(int position) {
            return (position + 1) % Constants.MAX_NUM_PAGES;
        }

        public void refreshAdapters(int position) {
            // Get adapters to refresh
            MonthGridAdapter currentAdapter = mAdapters.get(getCurrent(position));
            MonthGridAdapter prevAdapter = mAdapters.get(getPrevious(position));
            MonthGridAdapter nextAdapter = mAdapters.get(getNext(position));

            if (position == mCurrentPage) {
                // Refresh current adapter

                currentAdapter.setAdapterDateTime(mDateTime);
                currentAdapter.notifyDataSetChanged();

                // Refresh previous adapter
                prevAdapter.setAdapterDateTime(mDateTime.minusMonths(1));
                prevAdapter.notifyDataSetChanged();

                // Refresh next adapter
                nextAdapter.setAdapterDateTime(mDateTime.plusMonths(1));
                nextAdapter.notifyDataSetChanged();
            }
            // Detect if swipe right or swipe left
            // Swipe right
            else if (position > mCurrentPage) {
                // Update current date time to next month
                mDateTime = mDateTime.plusMonths(1);

                // Refresh the adapter of next gridview
                nextAdapter.setAdapterDateTime(mDateTime.plusMonths(1));
                nextAdapter.notifyDataSetChanged();

            }
            // Swipe left
            else {
                // Update current date time to previous month
                mDateTime = mDateTime.minusMonths(1);

                // Refresh the adapter of previous gridview
                prevAdapter.setAdapterDateTime(mDateTime.minusMonths(1));
                prevAdapter.notifyDataSetChanged();
            }

            // Update current page
            mCurrentPage = position;
            mCurrentMonth = mDateTime.getMonthOfYear();
            mCurrentYear = mDateTime.getYear();
            mTitleView.setText(new DateTime(mCurrentYear, mCurrentMonth, 1, 0, 0, 0, 0).monthOfYear().getAsText().toUpperCase() + " " + mCurrentYear);
        }

    }
}
