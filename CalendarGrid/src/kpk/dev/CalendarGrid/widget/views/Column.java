package kpk.dev.CalendarGrid.widget.views;

import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Krasimir
 * Date: 9/8/13
 * Time: 8:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class Column {
    private int mLeft;
    private int mRight;
    private List<Integer> mHours = new LinkedList<Integer>();
    public void setLeft(int left) {
        mLeft = left;
    }

    public int getLeft() {
        return mLeft;
    }

    public void setRight(int right) {
        mRight = right;
    }

    public int getRight() {
        return mRight;
    }
}
