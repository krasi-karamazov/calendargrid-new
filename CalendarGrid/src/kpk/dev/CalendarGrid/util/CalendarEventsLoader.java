package kpk.dev.CalendarGrid.util;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import kpk.dev.CalendarGrid.widget.models.Event;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: krasimir.karamazov
 * Date: 8/30/13
 * Time: 7:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class CalendarEventsLoader extends AsyncTask<List<Long>, Void, List<Event>> {

    private Context mContext;

    public CalendarEventsLoader(Context context) {
        mContext = context;
    }

    @Override
    protected List<Event> doInBackground(List<Long>... lists) {
        ContentResolver cr = mContext.getContentResolver();
        List<Long> eventIds = lists[0];
        Cursor cursor = cr.query(CalendarContract.Events.CONTENT_URI, null, null, null, null);
        return null;
    }
}
