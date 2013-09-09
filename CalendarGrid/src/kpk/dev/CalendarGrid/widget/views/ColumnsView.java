package kpk.dev.CalendarGrid.widget.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.ViewGroup;

import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Krasimir
 * Date: 9/8/13
 * Time: 8:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class ColumnsView extends ViewGroup {
    private static final int NUM_COLUMNS = 7;
    private static final int COLUMNS_MARGIN = 1;
    private List<Column> mColumns = new LinkedList<Column>();
    public ColumnsView(Context context) {
        super(context);
    }

    public ColumnsView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ColumnsView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int columnWidth = getMeasuredWidth() / NUM_COLUMNS;
        for(int i = 0; i < NUM_COLUMNS; i++) {
            int left = (i * columnWidth) + dpToPx(COLUMNS_MARGIN);
            int right = left + columnWidth;
            Column col = new Column();
            col.setLeft(left);
            col.setRight(right);
            mColumns.add(col);
            //EventView but = new EventView(getContext());
            /*but.setLeft(left);
            but.setRight(right);
            addView(but);*/
        }
    }

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();

        return (int)((dp * displayMetrics.density) + 0.5);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        for(int i = 0; i < getChildCount(); i++) {
            getChildAt(i).layout(mColumns.get(i).getLeft(), 0, mColumns.get(i).getRight(), getMeasuredHeight());
        }
    }
}
