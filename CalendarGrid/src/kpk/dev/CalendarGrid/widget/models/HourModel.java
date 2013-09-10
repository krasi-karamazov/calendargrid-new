package kpk.dev.CalendarGrid.widget.models;

/**
 * Created with IntelliJ IDEA.
 * User: krasimir.karamazov
 * Date: 9/3/13
 * Time: 6:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class HourModel {
    private String mName;
    private String mHour;
    private boolean mIsFirstInSpan;
    private boolean mIsInSpan;
    public HourModel() {

    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public void setIsFirstInSpan(boolean firstInSpan) {
        mIsFirstInSpan = firstInSpan;
    }

    public boolean getIsFirstInSpan() {
        return mIsFirstInSpan;
    }

    public void setIsInSpan(boolean inSpan) {
        mIsInSpan = inSpan;
    }

    public boolean getIsInSpan() {
        return mIsInSpan;
    }

    public void setHours(String hour) {
        mHour = hour;
    }

    public String getHour() {
        return mHour;
    }

}
