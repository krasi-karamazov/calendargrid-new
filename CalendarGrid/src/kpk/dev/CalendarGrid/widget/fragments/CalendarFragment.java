package kpk.dev.CalendarGrid.widget.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import kpk.dev.CalendarGrid.R;
import kpk.dev.CalendarGrid.widget.adapters.InfinitePagerAdapter;
import kpk.dev.CalendarGrid.widget.adapters.MonthPagerAdapter;
import kpk.dev.CalendarGrid.widget.adapters.MonthGridAdapter;
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
public class CalendarFragment extends Fragment {
    private List<MonthGridAdapter> mAdapters;
    private InfiniteViewPager mPager;
    private InfinitePageChangeListener mPageChangeListener;
    private List<DateTime> mDates;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.calendar_layout, container, false);
        DateTime currentDate = new DateTime(new Date());
        mDates = CalendarUtils.getFullWeeks(currentDate.getMonthOfYear(), currentDate.getYear(), DateTimeConstants.SUNDAY);
        mPager = (InfiniteViewPager)rootView.findViewById(R.id.pager);
        mPager.setDatesList(mDates);
        mPageChangeListener = new InfinitePageChangeListener();
        mPageChangeListener.setDateTime(currentDate);
        initGridAdapters(currentDate);
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
        return rootView;
    }

    private AdapterView.OnItemClickListener getOnItemClickListener() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        };
    }

    private AdapterView.OnItemLongClickListener getInItemLongClickListener() {
        return new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                return false;  //To change body of implemented methods use File | Settings | File Templates.
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
        // Refresh title view
        /*monthTitleTextView.setText(new DateTime(year, month, 1, 0, 0, 0, 0)
                .monthOfYear().getAsText().toUpperCase()
                + " " + year);*/

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

    private class InfinitePageChangeListener implements ViewPager.OnPageChangeListener {
        private DateTime mDateTime;
        private int mCurrentPage = InfiniteViewPager.OFFSET;

        public void setCurrentPage(int currentPage) {
            this.mCurrentPage = currentPage;
        }

        public int getCurrentPage() {
            return mCurrentPage;
        }

        public void setDateTime(DateTime dateTime) {
            mDateTime = dateTime;
        }
        @Override
        public void onPageScrolled(int i, float v, int i2) {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void onPageSelected(int i) {
            refreshAdapters(i);
            MonthGridAdapter currentAdapter = mAdapters.get(i % Constants.MAX_NUM_PAGES);

            // Refresh dateInMonthsList
            mDates.clear();
            mDates.addAll(currentAdapter.getDateTimeList());
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
        }

    }
}
