package kpk.dev.CalendarGrid.widget.models;

import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: krasimir.karamazov
 * Date: 8/30/13
 * Time: 2:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class CalendarModel implements Serializable {
    static final long serialVersionUID = 41L;
    private List<Instance> mInstances;
    private DateTime mDateTime;

    public List<Instance> getInstances() {
        return mInstances;
    }

    public void setInstances(List<Instance> mInstances) {
        this.mInstances = mInstances;
    }

    public DateTime getDateTime() {
        return mDateTime;
    }

    public void setDateTime(DateTime mDateTime) {
        this.mDateTime = mDateTime;
    }
}
