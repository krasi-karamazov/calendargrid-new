package kpk.dev.CalendarGrid.widget.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import kpk.dev.CalendarGrid.R;
import kpk.dev.CalendarGrid.util.LogHelper;
import kpk.dev.CalendarGrid.widget.models.Instance;
import org.joda.time.Interval;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Krasimir
 * Date: 9/7/13
 * Time: 12:52 AM
 * To change this template use File | Settings | File Templates.
 */
public class BlocksLayout extends ViewGroup {

    private TimeView mRulerView;
    private View mNowView;
    private List<Instance> mInstances;
    private int mNumColumns;
    private List<Column> mColumns = new LinkedList<Column>();
    private boolean mHasInstances;
    private int mColumnWidth;

    public BlocksLayout(Context context) {
        this(context, null);
    }

    public BlocksLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BlocksLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private void ensureChildren() {
        mRulerView = (TimeView) findViewById(R.id.blocks_ruler);
        if (mRulerView == null) {
            throw new IllegalStateException("Must include a R.id.blocks_ruler view.");
        }
        mRulerView.setDrawingCacheEnabled(true);

        mNowView = findViewById(R.id.now_view);
        if (mNowView == null) {
            throw new IllegalStateException("Must include a R.id.blocks_now view.");
        }
        mNowView.setDrawingCacheEnabled(true);
    }

    public void removeAllBlocks() {
        ensureChildren();
        removeAllViews();
        addView(mRulerView);
        addView(mNowView);
    }

    public void setInstances(List<Instance> instances) {
        mInstances = instances;
        mHasInstances = true;
        requestLayout();
    }

    private void addColumns(){
        mColumnWidth = (mRulerView.getMeasuredWidth() - mRulerView.getHeaderWidth()) / mNumColumns;
        for(int i = 0; i < mNumColumns; i++){
            int columnWidth = mRulerView.getMeasuredWidth() / mNumColumns;
            int left = (i * columnWidth);
            int right = left + columnWidth;
            Column col = new Column();
            col.setLeft(left);
            col.setRight(right);
            mColumns.add(col);
        }
    }

    private void getMaxOverlaps() {
        int maxOverlaps = 0;
        for(int i = 0; i < mInstances.size(); i++) {
            int overlaps = 0;
            for(int j = 0; j < mInstances.size(); j++) {
                if(mInstances.get(i).getId() != mInstances.get(j).getId()) {
                    Interval interval = new Interval(mInstances.get(i).getBeginTime(), mInstances.get(i).getEndTime());
                    Interval intervalOther = new Interval(mInstances.get(j).getBeginTime(), mInstances.get(j).getEndTime());
                    if(interval.overlap(intervalOther) != null){
                        overlaps++;
                    }
                }
            }
            if(overlaps > maxOverlaps) {
                maxOverlaps = overlaps;
            }
        }
        mNumColumns = maxOverlaps;
    }

    private void addBlocks() {
        for(int i = 0; i < mInstances.size(); i++) {
            EventView event = new EventView(getContext(), mInstances.get(i).getBeginTime().getMillis(), mInstances.get(i).getEndTime().getMillis(), "Name " + i, mInstances.get(i).getCalendarColor());
            event.setStartTime(mInstances.get(i).getBeginTime().getMillis());
            event.setEndTime(mInstances.get(i).getEndTime().getMillis());
            event.setId((int)mInstances.get(i).getId());
            addView(event);
        }
        mHasInstances = false;
        requestLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        ensureChildren();
        mRulerView.measure(widthMeasureSpec, heightMeasureSpec);
        mNowView.measure(widthMeasureSpec, heightMeasureSpec);

        final int width = mRulerView.getMeasuredWidth();
        final int height = mRulerView.getMeasuredHeight();
        if(mHasInstances) {
            getMaxOverlaps();
            addColumns();

            addBlocks();
            requestLayout();
        }
        setMeasuredDimension(resolveSize(width, widthMeasureSpec),
                resolveSize(height, heightMeasureSpec));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        ensureChildren();

        final TimeView rulerView = mRulerView;
        final int headerWidth = rulerView.getHeaderWidth();

        rulerView.layout(0, 0, getWidth(), getHeight());

        final int count = getChildCount();
        int columnIndex = 0;
        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            if (child instanceof EventView) {

                final EventView blockView = (EventView) child;
                final int top = rulerView.getTimeVerticalOffset(blockView.getStartTime());
                final int bottom = rulerView.getTimeVerticalOffset(blockView.getEndTime());

                int left = headerWidth;
                int right = left + mColumnWidth;

                child.layout(left, top, right, bottom);
                Rect childRect = new Rect(left, top, right, bottom);
                for(int j = 0; j < count; j++) {
                    View anotherChild = getChildAt(j);
                    if(anotherChild instanceof EventView){
                       if(anotherChild.getId() != child.getId()) {
                           Rect anotherChildRect = new Rect(anotherChild.getLeft(), anotherChild.getTop(), anotherChild.getRight(), anotherChild.getBottom());
                           if(childRect.intersect(anotherChildRect)){
                               LogHelper.d("COLUMN OVERLAP " + i);
                                
                               left = headerWidth + (columnIndex * mColumnWidth);
                               right = left + mColumnWidth;
                               child.layout(left, top, right, bottom);
                           }
                       }
                    }
                }
                if(i >= mNumColumns || columnIndex >= mNumColumns){
                    columnIndex = 0;
                }else{
                    columnIndex++;
                }
            }
        }

        // Align now view to match current time
        final View nowView = mNowView;
        final long now = new Date().getTime();

        final int top = rulerView.getTimeVerticalOffset(now);
        final int bottom = top + nowView.getMeasuredHeight();
        final int left = 0;
        final int right = getWidth();

        nowView.layout(left, top, right, bottom);
    }
}

