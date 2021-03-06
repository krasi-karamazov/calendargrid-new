package kpk.dev.CalendarGrid.widget.views;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import kpk.dev.CalendarGrid.R;
import kpk.dev.CalendarGrid.widget.models.Instance;
import kpk.dev.CalendarGrid.widget.util.Utils;
import org.joda.time.Interval;

import java.util.Date;
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
    private boolean mHasInstances;
    private int mColumnWidth;
    private LinearLayout mAllDayEventsContainer;

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

        mAllDayEventsContainer = (LinearLayout)findViewById(R.id.all_day_events_container);
        if (mAllDayEventsContainer == null) {
            throw new IllegalStateException("Must include a R.id.all_day_events_container view.");
        }
    }

    public void removeAllBlocks() {
        ensureChildren();
        removeAllViews();
        addView(mRulerView);
        addView(mNowView);
        addView(mAllDayEventsContainer);
    }

    public void setInstances(List<Instance> instances) {
        mInstances = instances;
        mHasInstances = true;
        requestLayout();
    }

    private void definedColumnWidth(){
        if(mNumColumns == 0) {
            mNumColumns = 1;
        }
        mColumnWidth = (mRulerView.getMeasuredWidth() - mRulerView.getHeaderWidth()) / mNumColumns;
    }

    private void getMaxOverlaps() {
        int maxOverlaps = 0;
        for(int i = 0; i < mInstances.size(); i++) {
            int overlaps = 0;
            for(int j = 0; j < mInstances.size(); j++) {
                if(mInstances.get(i).getId() != mInstances.get(j).getId()) {
                    Interval interval = new Interval(mInstances.get(i).getBeginTime(), mInstances.get(i).getEndTime());
                    Interval intervalOther = new Interval(mInstances.get(j).getBeginTime(), mInstances.get(j).getEndTime());
                    boolean overlap = interval.overlaps(intervalOther);
                    if(overlap && !mInstances.get(i).getIsAllday() && !mInstances.get(j).getIsAllday()){
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
            event.setAllDay(mInstances.get(i).getIsAllday());
            event.setEndTime(mInstances.get(i).getEndTime().getMillis());
            event.setId((int) mInstances.get(i).getId());
            event.setTextColor(Color.WHITE);
            event.setGravity(Gravity.TOP|Gravity.LEFT);
            event.setTitle(mInstances.get(i).getTitle());
            addViewInLayout(event);
        }
        mHasInstances = false;
        requestLayout();
    }

    private void addViewInLayout(EventView event){
        ViewGroup container;
        if(event.getAllDay()) {
            mNumColumns --;
            container = mAllDayEventsContainer;
        }else{
            container = this;
        }
        container.addView(event);
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
            definedColumnWidth();
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
        final LinearLayout allDayEventsContainer = mAllDayEventsContainer;
        final int headerWidth = rulerView.getHeaderWidth();
        if(allDayEventsContainer.getChildCount() != 0) {
            allDayEventsContainer.setVisibility(View.VISIBLE);
            allDayEventsContainer.layout(0, 0, getWidth(), Utils.dpToPx(90, getContext()));
            int height = allDayEventsContainer.getHeight() / allDayEventsContainer.getChildCount();
            for(int a = 0; a < allDayEventsContainer.getChildCount(); a++) {
                final View child = allDayEventsContainer.getChildAt(a);
                child.layout(0, a * height, allDayEventsContainer.getWidth(), (a * height) + height);
            }
        }else{
            allDayEventsContainer.setVisibility(View.GONE);
        };

        rulerView.layout(0, allDayEventsContainer.getHeight(), getWidth(), getHeight());

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
                Rect childRect = new Rect(left, top, right, bottom);
                for(int j = 0; j < count; j++) {
                    View anotherChild = getChildAt(j);
                    if(anotherChild instanceof EventView){
                       if(anotherChild.getId() != child.getId()) {
                           Rect anotherChildRect = new Rect(anotherChild.getLeft(), anotherChild.getTop(), anotherChild.getRight(), anotherChild.getBottom());
                           if(childRect.intersect(anotherChildRect) || childRect.intersects(anotherChildRect.left, anotherChildRect.top, anotherChildRect.right, anotherChildRect.bottom) || childRect.contains(anotherChildRect) || anotherChildRect.contains(childRect)) {
                               columnIndex++;
                               childRect.left = headerWidth + (columnIndex * mColumnWidth);
                               childRect.right = childRect.left + mColumnWidth;
                           }
                       }
                    }
                }

                columnIndex = 0;
                child.layout(childRect.left, childRect.top, childRect.right, childRect.bottom);
            }
        }

        final View nowView = mNowView;
        final long now = new Date().getTime();

        final int top = rulerView.getTimeVerticalOffset(now);
        final int bottom = top + nowView.getMeasuredHeight();
        final int left = 0;
        final int right = getWidth();

        nowView.layout(left, top, right, bottom);
    }
}

