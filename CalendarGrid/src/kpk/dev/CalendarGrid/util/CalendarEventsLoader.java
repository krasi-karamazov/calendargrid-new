package kpk.dev.CalendarGrid.util;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
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
        for(Long id : eventIds) {
            //cr
        }
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
