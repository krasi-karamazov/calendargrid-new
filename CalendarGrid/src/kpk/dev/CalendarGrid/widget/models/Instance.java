package kpk.dev.CalendarGrid.widget.models;

import org.joda.time.DateTime;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: krasimir.karamazov
 * Date: 8/30/13
 * Time: 1:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class Instance implements Serializable {
    static final long serialVersionUID = 42L;
    private DateTime mBeginTime;
    private DateTime mEndTime;
    private int mBeginDay;
    private int mEndDay;
    private long mEventId;
    private long mCalendarId;
    private int mCalendarColor;
    private String mTitle;
    private String mDescription;

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        this.mDescription = description;
    }

    public long getCalendarId() {
        return mCalendarId;
    }

    public void setCalendarId(long calendarId) {
        this.mCalendarId = mCalendarId;
    }

    public int getCalendarColor() {
        return mCalendarColor;
    }

    public void setCalendarColor(int calendarColor) {
        this.mCalendarColor = calendarColor;
    }

    public DateTime getBeginTime() {
        return mBeginTime;
    }

    public void setBeginTime(DateTime mBeginTime) {
        this.mBeginTime = mBeginTime;
    }

    public DateTime getEndTime() {
        return mEndTime;
    }

    public void setEndTime(DateTime mEndTime) {
        this.mEndTime = mEndTime;
    }

    public int getBeginDay() {
        return mBeginDay;
    }

    public void setBeginDay(int mBeginDay) {
        this.mBeginDay = mBeginDay;
    }

    public int getEndDay() {
        return mEndDay;
    }

    public void setEndDay(int mEndDay) {
        this.mEndDay = mEndDay;
    }

    public long getEventId() {
        return mEventId;
    }

    public void setEventId(long mEventId) {
        this.mEventId = mEventId;
    }
}
