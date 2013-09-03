package kpk.dev.CalendarGrid.widget.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import kpk.dev.CalendarGrid.R;
import kpk.dev.CalendarGrid.util.CalendarEventsLoader;
import kpk.dev.CalendarGrid.widget.adapters.EventsListAdapter;
import kpk.dev.CalendarGrid.widget.models.CalendarModel;
import kpk.dev.CalendarGrid.widget.models.Event;
import kpk.dev.CalendarGrid.widget.models.Instance;

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
    private ListView mEventsListView;
    private EventsListAdapter mAdapter;
    private List<Instance> mInstances;
    public static EventsListDialogFragment getInstance(Bundle args) {
        EventsListDialogFragment fragment = new EventsListDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);    //To change body of overridden methods use File | Settings | File Templates.
        setRetainInstance(true);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity());
        LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rootView = inflater.inflate(R.layout.events_dialog_layout, null, false);
        dialog.setContentView(rootView);
        mEventsListView = (ListView)rootView.findViewById(R.id.events_list);
        CalendarModel model = (CalendarModel)getArguments().getSerializable(CALENDAR_MODEL_ARGS_KEY);
        mInstances = model.getInstances();
        mAdapter = new EventsListAdapter(getActivity(), mInstances);
        mEventsListView.setAdapter(mAdapter);
        dialog.setTitle(model.getDateTime().toString("dd MMMM yyyy"));
        return dialog;
    }
}
