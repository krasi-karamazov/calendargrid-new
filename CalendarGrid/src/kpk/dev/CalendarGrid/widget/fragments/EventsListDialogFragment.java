package kpk.dev.CalendarGrid.widget.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import kpk.dev.CalendarGrid.R;
import kpk.dev.CalendarGrid.widget.adapters.EventsListAdapter;
import kpk.dev.CalendarGrid.widget.models.CalendarModel;

import java.util.ArrayList;
import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * User: krasimir.karamazov
 * Date: 8/30/13
 * Time: 6:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class EventsListDialogFragment extends DialogFragment {
    public static final String CALENDAR_MODEL_ARGS_KEY = "calendar_model";
    private CalendarModel mModel;
    private ListView mEventsListView;
    public static EventsListDialogFragment getInstance(Bundle args) {
        EventsListDialogFragment fragment = new EventsListDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rootView = inflater.inflate(R.layout.events_dialog_layout, null, false);
        mEventsListView = (ListView)rootView.findViewById(R.id.events_list);
        mEventsListView.setAdapter(getEventsAdapter(getArguments()));
        builder.setView(rootView);
        return builder.show();
    }

    public ListAdapter getEventsAdapter(Bundle bundle) {
        CalendarModel model = (CalendarModel)bundle.getSerializable(CALENDAR_MODEL_ARGS_KEY);
        List<Long> mEventIds = new ArrayList<Long>();

        for(int i = 0; i < model.getInstances().size(); i++) {
            mEventIds.add(model.getInstances().get(i).getEventId());
        }

        return new EventsListAdapter(getActivity(), android.R.layout.simple_list_item_1, null);
    }
}
