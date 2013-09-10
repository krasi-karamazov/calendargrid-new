package kpk.dev.CalendarGrid.widget.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import kpk.dev.CalendarGrid.R;
import kpk.dev.CalendarGrid.util.LogHelper;
import kpk.dev.CalendarGrid.widget.models.CalendarModel;
import kpk.dev.CalendarGrid.widget.models.HourModel;
import kpk.dev.CalendarGrid.widget.models.Instance;
import kpk.dev.CalendarGrid.widget.views.BlocksLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: krasimir.karamazov
 * Date: 9/3/13
 * Time: 6:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class DayViewFragment extends Fragment {
    public static final String CALENDAR_MODEL_ARGS_KEY = "calendar_model";
    private CalendarModel mModel;
    private LinearLayout mHoursContainer;
    private RelativeLayout mMainContainer;
    private BlocksLayout mBlocksLayout;
    private List<Instance> mInstances;

    public static DayViewFragment getInstance(Bundle args) {
        DayViewFragment fragment = new DayViewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.day_view_layout, container, false);
        mBlocksLayout = (BlocksLayout)rootView.findViewById(R.id.blocks);
        //mHoursContainer = (LinearLayout)rootView.findViewById(R.id.hours_container);
        //mMainContainer = (RelativeLayout)rootView.findViewById(R.id.day_view_container);
        mModel = (CalendarModel)getArguments().getSerializable(CALENDAR_MODEL_ARGS_KEY);
        mBlocksLayout.setInstances(mModel.getInstances());
        //addHoursList();
        //addEvents();
        return rootView;
    }

    private void sortInstances() {
        mInstances = new ArrayList<Instance>();
        final List<Instance> instances = mModel.getInstances();

        ///Collections.sort(instances, new InstancesComparator());
        mInstances.addAll(instances);
        LogHelper.d("instances " + mInstances);
    }

    private void addEvents() {
        /*for(int i = 0; i < mModel.getInstances().size(); i++) {
            for(int j = 0; j < mModel.getInstances().size(); j++) {
                int overlaps = 0;
                if(mModel.getInstances().get(i).getInterval().overlaps(mModel.getInstances().get(j).getInterval()) && j != i) {
                    overlaps++;

                }
                mModel.getInstances().get(i).setOverlaps(overlaps);
            }
        }*/
        for(int i = 0; i < mModel.getInstances().size(); i++) {
            Button v = new Button(getActivity());
            long diff = mModel.getInstances().get(i).getEndTime().getMillis() - mModel.getInstances().get(i).getBeginTime().getMillis();
            int hours   = (int) ((diff / (1000*60*60)) % 24);
            int minutes = (int) ((diff / (1000*60)) % 60);
            int height = dpToPx(hours * 60);
            int width = getActivity().getResources().getDisplayMetrics().widthPixels - dpToPx(40);
            int overlaps = mModel.getInstances().get(i).getOverlaps();
            int marginTop = dpToPx((mModel.getInstances().get(i).getBeginTime().getHourOfDay() * 60));
            int itemWidth = width / mModel.getInstances().size();
            int marginLeft = dpToPx(40) + (i * itemWidth);
            if(mModel.getInstances().get(i).getIsAllday()) {
                height = dpToPx(24 * 60);
                marginTop = 0;
            }

            v.setHeight(height);

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(itemWidth, height);
            v.setText(mModel.getInstances().get(i).getTitle());
            //v.setGravity(Gravity.TOP | Gravity.LEFT);
            params.topMargin = marginTop;
            params.leftMargin = marginLeft;
            v.setLayoutParams(params);
            v.setBackgroundColor(mModel.getInstances().get(i).getCalendarColor());
            mMainContainer.addView(v);

        }
    }

    public int pxToDp(int px) {
        DisplayMetrics displayMetrics = getActivity().getResources().getDisplayMetrics();
        int dp = Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return dp;
    }

    private void addHoursList() {
        final List<HourModel> models = new ArrayList<HourModel>();
        for(int i = 0; i < 24; i++) {
            HourModel model = new HourModel();
            model.setHours(Integer.valueOf(i).toString());
            models.add(model);
            addView(model);
        }
    }

    public void addView(HourModel model) {
        LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.hour_item_layout, mHoursContainer, false);
        v.setId(Integer.valueOf(model.getHour()));
        TextView tv = (TextView)v.findViewById(R.id.h_label);
        TextView tvName = (TextView)v.findViewById(R.id.item_name);
        tv.setText(model.getHour());
        tvName.setText("");
        mHoursContainer.addView(v);
    }

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = getActivity().getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }
}
