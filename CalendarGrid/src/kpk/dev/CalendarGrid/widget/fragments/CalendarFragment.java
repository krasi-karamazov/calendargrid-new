package kpk.dev.CalendarGrid.widget.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import kpk.dev.CalendarGrid.R;
import kpk.dev.CalendarGrid.widget.util.CalendarUtils;
import kpk.dev.CalendarGrid.widget.views.InfiniteViewPager;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: krasimir.karamazov
 * Date: 8/28/13
 * Time: 7:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class CalendarFragment extends Fragment {
    private InfiniteViewPager mPager;
    private InfinitePageChangeListener mPageChangeListener;
    private List<DateTime> mDates;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.calendar_layout, container, false);
        DateTime currentDate = new DateTime(new Date());
        mDates = CalendarUtils.getFullWeeks(currentDate.getMonthOfYear(), currentDate.getYear(), DateTimeConstants.SUNDAY);
        mPager = (InfiniteViewPager)rootView.findViewById(R.id.pager);
        mPageChangeListener = new InfinitePageChangeListener();
        return rootView;
    }

    private class InfinitePageChangeListener implements ViewPager.OnPageChangeListener {
        private DateTime mDateTime;
        public void setDateTime(DateTime dateTime) {
            mDateTime = dateTime;
        }
        @Override
        public void onPageScrolled(int i, float v, int i2) {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void onPageSelected(int i) {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void onPageScrollStateChanged(int i) {
            //To change body of implemented methods use File | Settings | File Templates.
        }
    }
}
