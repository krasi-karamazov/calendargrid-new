package kpk.dev.CalendarGrid.widget.util;

import kpk.dev.CalendarGrid.widget.models.Instance;

import java.util.Comparator;

/**
 * Created with IntelliJ IDEA.
 * User: krasimir.karamazov
 * Date: 9/4/13
 * Time: 5:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class InstancesComparator implements Comparator<Instance> {

    @Override
    public int compare(Instance instance, Instance instance2) {
        return Long.valueOf(instance2.getDuration()).compareTo(Long.valueOf(instance.getDuration()));
    }
}
