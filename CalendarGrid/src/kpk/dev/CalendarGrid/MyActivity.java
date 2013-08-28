package kpk.dev.CalendarGrid;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import kpk.dev.CalendarGrid.widget.fragments.CalendarFragment;
import kpk.dev.CalendarGrid.widget.fragments.MonthGridFragment;

public class MyActivity extends FragmentActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.container, new CalendarFragment(), null);
        ft.commit();
    }
}
