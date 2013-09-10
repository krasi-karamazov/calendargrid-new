package kpk.dev.CalendarGrid.widget.util;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import kpk.dev.CalendarGrid.listener.CalendarLoaderListener;
import kpk.dev.CalendarGrid.widget.models.CalendarModel;
import kpk.dev.CalendarGrid.widget.models.Instance;
import org.joda.time.DateTime;
import org.joda.time.Interval;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: krasimir.karamazov
 * Date: 8/30/13
 * Time: 12:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class CalendarMainLoader implements LoaderManager.LoaderCallbacks<Cursor> {
    private Context mContext;
    private DateTime mStartDate;
    private DateTime mEndDate;
    private static final int LOADER_ID = 10;
    private Multimap<DateTime, Instance> mInstancesMap = LinkedHashMultimap.create();
    private List<CalendarModel> mModels;
    private CalendarLoaderListener mListener;
    public CalendarMainLoader(Context context) {
        mContext = context;
    }

    public void setCalendarListener(CalendarLoaderListener listener) {
        mListener = listener;
    }

    public void resetSearch(DateTime startDate, DateTime endDate, List<CalendarModel> models) {
        if(mModels != null) {
            mModels.clear();
        }else{
            mModels = new ArrayList<CalendarModel>();
        }
        mModels.addAll(models);
        mStartDate = startDate;
        mEndDate = endDate;
        LoaderManager manager = ((FragmentActivity)mContext).getSupportLoaderManager();
        Loader loader = manager.getLoader(LOADER_ID);
        if(loader != null) {
            if(loader.isStarted()) {
                ((FragmentActivity)mContext).getSupportLoaderManager().restartLoader(LOADER_ID, null, CalendarMainLoader.this);
            }
        }else{
            ((FragmentActivity)mContext).getSupportLoaderManager().initLoader(10, null, CalendarMainLoader.this);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        Uri.Builder uriBuilder = CalendarContract.Instances.CONTENT_URI.buildUpon();
        ContentUris.appendId(uriBuilder, mStartDate.getMillis());
        ContentUris.appendId(uriBuilder, mEndDate.getMillis());
        if(StyleHelper.getInstance().getCalendarName() != null){
            if(StyleHelper.getInstance().getSelectedCalendarId() == -1){
                getCalendarId();
            }
        }

        Uri uri = uriBuilder.build();
        String selection = null;
        if(StyleHelper.getInstance().getSelectedCalendarId() != -1){
            selection = CalendarContract.Instances.CALENDAR_ID + "='" + StyleHelper.getInstance().getSelectedCalendarId() +"'";
        }
        CursorLoader cursorLoader = new CursorLoader(mContext, uri, null, selection, null, null);
        return cursorLoader;
    }

    private void getCalendarId() {
        Uri uri = CalendarContract.Calendars.CONTENT_URI;
        ContentResolver cr = mContext.getContentResolver();
        String[] projection = {CalendarContract.Calendars._ID};
        String selection = CalendarContract.Calendars.CALENDAR_DISPLAY_NAME + "='" + StyleHelper.getInstance().getCalendarName() + "'";
        Cursor cursor = cr.query(uri, projection, selection, null, null);
        if(cursor.moveToFirst()) {
            long id = cursor.getLong(cursor.getColumnIndexOrThrow(CalendarContract.Calendars._ID));
            StyleHelper.getInstance().setSelectedCalendarId(id);
        }
        cursor.close();
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        mInstancesMap.clear();
        while(cursor.moveToNext()) {
            long beginTime = cursor.getLong(cursor.getColumnIndexOrThrow(CalendarContract.Instances.BEGIN));
            long endTime = cursor.getLong(cursor.getColumnIndexOrThrow(CalendarContract.Instances.END));
            Instance instance = new Instance();
            DateTime beginDateTime = new DateTime(beginTime);
            DateTime endDateTime = new DateTime(endTime);
            instance.setBeginDay(beginDateTime.getDayOfMonth());
            instance.setEndDay(endDateTime.getDayOfMonth());
            instance.setBeginTime(beginDateTime);
            instance.setEndTime(endDateTime);
            instance.setId(cursor.getLong(cursor.getColumnIndexOrThrow(CalendarContract.Instances._ID)));
            instance.setEventId(cursor.getLong(cursor.getColumnIndexOrThrow(CalendarContract.Instances.EVENT_ID)));
            instance.setEventId(cursor.getLong(cursor.getColumnIndexOrThrow(CalendarContract.Instances._ID)));
            instance.setIsAllday((cursor.getInt(cursor.getColumnIndexOrThrow(CalendarContract.Instances.ALL_DAY)) == 0) ? false : true);
            instance.setInterval(new Interval(beginTime, endTime));
            instance.setCalendarId(cursor.getLong(cursor.getColumnIndexOrThrow(CalendarContract.Instances.CALENDAR_ID)));
            String color = cursor.getString(cursor.getColumnIndexOrThrow(CalendarContract.Instances.CALENDAR_COLOR));
            instance.setCalendarColor((0xff000000 + Integer.parseInt(color)));
            instance.setDuration(endDateTime.getMillis() - beginDateTime.getMillis());
            instance.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(CalendarContract.Instances.TITLE)));
            instance.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(CalendarContract.Instances.DESCRIPTION)));
            DateTime key = beginDateTime.withMillisOfDay(0);
            mInstancesMap.put(key, instance);
        }

        for(int i = 0; i < mModels.size(); i++) {
            DateTime dateTime = mModels.get(i).getDateTime();

            if(mInstancesMap.get(dateTime).size() > 0) {
                mModels.get(i).setInstances(new ArrayList<Instance>(mInstancesMap.get(dateTime)));
            }
        }
        mListener.dataReady(mModels);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        //cursorLoader.stopLoading();
    }
}
