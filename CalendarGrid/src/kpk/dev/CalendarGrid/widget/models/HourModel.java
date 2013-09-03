package kpk.dev.CalendarGrid.widget.models;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: krasimir.karamazov
 * Date: 9/3/13
 * Time: 6:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class HourModel {
    private List<Instance> mInstances;
    private String mHour;
    private boolean mIsFirstInSpan;
    private boolean mIsInSpan;
    public HourModel() {

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

    public void setInstances(List<Instance> instances) {
        mInstances = instances;
    }

    public List<Instance> getInstances() {
        return mInstances;
    }

    public void setHours(String hour) {
        mHour = hour;
    }

    public String getHour() {
        return mHour;
    }

}
