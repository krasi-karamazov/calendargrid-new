package kpk.dev.CalendarGrid.widget.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import kpk.dev.CalendarGrid.widget.fragments.MonthGridFragment;
import kpk.dev.CalendarGrid.widget.util.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: krasimir.karamazov
 * Date: 8/29/13
 * Time: 12:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class MonthPagerAdapter extends FragmentPagerAdapter {
    private List<MonthGridFragment> mFragments;

    public MonthPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public List<MonthGridFragment> getFragments() {
        if(mFragments == null) {
            mFragments = new ArrayList<MonthGridFragment>();
            for(int i = 0; i < Constants.MAX_NUM_PAGES; i++) {
                mFragments.add(new MonthGridFragment());
            }
        }
        return mFragments;
    }

    public void setFragments(List<MonthGridFragment> fragments) {
        mFragments = fragments;
    }

    @Override
    public Fragment getItem(int i) {
        return mFragments.get(i);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int getCount() {
        return Constants.MAX_NUM_PAGES;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
