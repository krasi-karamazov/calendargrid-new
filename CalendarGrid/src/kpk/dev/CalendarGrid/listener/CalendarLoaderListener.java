package kpk.dev.CalendarGrid.listener;

import kpk.dev.CalendarGrid.widget.models.CalendarModel;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: krasimir.karamazov
 * Date: 8/30/13
 * Time: 2:46 PM
 * To change this template use File | Settings | File Templates.
 */
public interface CalendarLoaderListener {
    void dataReady(List<CalendarModel> models);
}
