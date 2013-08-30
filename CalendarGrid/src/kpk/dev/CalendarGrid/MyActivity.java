package kpk.dev.CalendarGrid;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import kpk.dev.CalendarGrid.listener.OnDateClickListener;
import kpk.dev.CalendarGrid.listener.OnDateLongClickListener;
import kpk.dev.CalendarGrid.util.LogHelper;
import kpk.dev.CalendarGrid.widget.RootView;
import kpk.dev.CalendarGrid.widget.models.CalendarModel;

public class MyActivity extends FragmentActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

    }

    @Override
    protected void onResume() {
        super.onResume();    //To change body of overridden methods use File | Settings | File Templates.
        final RootView view = (RootView)findViewById(RootView.ID);
        view.setOnDateClickListener(new OnDateClickListener() {
            @Override
            public void onDateClick(CalendarModel model) {
                view.showEvents(model);
            }
        });

        view.setOnDateLongClickListener(new OnDateLongClickListener() {
            @Override
            public void onDateLongClick(CalendarModel model) {
                //To change body of implemented methods use File | Settings | File Templates.
            }
        });
    }
}
