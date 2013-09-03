package kpk.dev.CalendarGrid.widget.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import kpk.dev.CalendarGrid.R;
import kpk.dev.CalendarGrid.util.LogHelper;
import kpk.dev.CalendarGrid.widget.adapters.HoursAdapter;
import kpk.dev.CalendarGrid.widget.models.CalendarModel;
import kpk.dev.CalendarGrid.widget.models.HourModel;
import kpk.dev.CalendarGrid.widget.models.Instance;
import org.joda.time.DateTime;

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
    private ListView mListView;

    public static DayViewFragment getInstance(Bundle args) {
        DayViewFragment fragment = new DayViewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.day_view_layout, container, false);
        mListView = (ListView)rootView.findViewById(R.id.hours_list);
        mModel = (CalendarModel)getArguments().getSerializable(CALENDAR_MODEL_ARGS_KEY);
        mListView.setAdapter(generateAdapter());
        return rootView;
    }

    private HoursAdapter generateAdapter() {
        final List<HourModel> models = new ArrayList<HourModel>();
        for(int i = 0; i < 24; i++) {
            HourModel model = new HourModel();
            model.setHours(Integer.valueOf(i).toString());
            for(Instance instance : mModel.getInstances()) {
                long diff = instance.getEndTime().getMillis() - instance.getBeginTime().getMillis();
                int hours   = (int) ((diff / (1000*60*60)) % 24);
                if(i == instance.getBeginTime().getHourOfDay()){
                    HourModel firstInSpan = new HourModel();
                    firstInSpan.setIsInSpan(true);
                    firstInSpan.setIsFirstInSpan(true);
                    firstInSpan.setHours(Integer.valueOf(i++).toString());
                    models.add(firstInSpan);
                    for(int h = 0; h < hours; h++) {
                        HourModel modelInSpan = new HourModel();
                        modelInSpan.setHours(Integer.valueOf(i++).toString());
                        modelInSpan.setIsInSpan(true);
                        modelInSpan.setIsFirstInSpan(false);
                        models.add(modelInSpan);
                    }
                }else{
                    model.setIsInSpan(false);

                    models.add(model);
                    break;
                }

            }
        }

        return new HoursAdapter(getActivity(), models);
    }


}
