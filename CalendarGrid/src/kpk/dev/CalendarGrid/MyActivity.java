package kpk.dev.CalendarGrid;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import kpk.dev.CalendarGrid.listener.OnDateClickListener;
import kpk.dev.CalendarGrid.listener.OnDateLongClickListener;
import kpk.dev.CalendarGrid.widget.RootView;
import kpk.dev.CalendarGrid.widget.models.CalendarModel;

public class MyActivity extends FragmentActivity {
    private RootView view;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        view = (RootView)findViewById(RootView.ID);


        view.setOnDateClickListener(new OnDateClickListener() {
            @Override
            public void onDateClick(CalendarModel model) {
                view.showDayView(model);
            }
        });

        view.setOnDateLongClickListener(new OnDateLongClickListener() {
            @Override
            public void onDateLongClick(CalendarModel model) {

            }
        });
        view.initCalendar(null);
    }
}
