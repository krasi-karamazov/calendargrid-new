package kpk.dev.CalendarGrid.widget.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import kpk.dev.CalendarGrid.R;
import kpk.dev.CalendarGrid.widget.adapters.MonthGridAdapter;
import kpk.dev.CalendarGrid.widget.util.Constants;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: krasimir.karamazov
 * Date: 8/28/13
 * Time: 5:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class MonthGridFragment extends Fragment {

    private GridView mGridView;
    private MonthGridAdapter mAdapter;
    private AdapterView.OnItemClickListener mOnItemClickListener;
    private AdapterView.OnItemLongClickListener mOnItemLongClickListener;
    private Map<String, Object> mInternalData;

    public AdapterView.OnItemClickListener getOnItemClickListener() {
        return mOnItemClickListener;
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public AdapterView.OnItemLongClickListener getOnItemLongClickListener() {
        return mOnItemLongClickListener;
    }

    public void setOnItemLongClickListener(AdapterView.OnItemLongClickListener onItemLongClickListener) {
        mOnItemLongClickListener = onItemLongClickListener;
    }

    public MonthGridAdapter getGridAdapter() {
        return mAdapter;
    }

    public void setGridAdapter(MonthGridAdapter gridAdapter) {
        mAdapter = gridAdapter;
    }

    public GridView getGridView() {
        return mGridView;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mGridView = (GridView)inflater.inflate(R.layout.month_grid_fragment_layout, container, false);
        DateTime monthToDisplay = DateTime.now();
        mInternalData = new HashMap<String, Object>();
        if(mAdapter != null) {
            mGridView.setAdapter(mAdapter);
        }else{
            mAdapter = new MonthGridAdapter(getActivity(), monthToDisplay.getMonthOfYear(), monthToDisplay.getYear(), getInternalData(), null);
            mGridView.setAdapter(mAdapter);
        }

        if(mOnItemClickListener != null){
            mGridView.setOnItemClickListener(mOnItemClickListener);
        }

        if(mOnItemLongClickListener != null){
            mGridView.setOnItemLongClickListener(mOnItemLongClickListener);
        }

        return mGridView;
    }

    private Map<String, Object> getInternalData() {

        mInternalData.clear();
        mInternalData.put(Constants.DISABLED_DATES, null);
        mInternalData.put(Constants.SELECTED_DATES, null);
        mInternalData.put(Constants.MIN_DATE, null);
        mInternalData.put(Constants.MIN_DATE, null);
        mInternalData.put(Constants.START_DAY_OF_WEEK, DateTimeConstants.SUNDAY);

        /*// For internal use
        mInternalData.put(_BACKGROUND_FOR_DATETIME_MAP, backgroundForDateTimeMap);
        mInternalData.put(_TEXT_COLOR_FOR_DATETIME_MAP, textColorForDateTimeMap);*/

        return mInternalData;
    }
}
