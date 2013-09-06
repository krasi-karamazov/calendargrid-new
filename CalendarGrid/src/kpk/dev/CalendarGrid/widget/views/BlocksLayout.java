package kpk.dev.CalendarGrid.widget.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import kpk.dev.CalendarGrid.R;

/**
 * Created with IntelliJ IDEA.
 * User: Krasimir
 * Date: 9/7/13
 * Time: 12:52 AM
 * To change this template use File | Settings | File Templates.
 */
public class BlocksLayout extends ViewGroup {
    private int mColumns = 3;

    private TimeView mRulerView;
    private View mNowView;

    public BlocksLayout(Context context) {
        this(context, null);
    }

    public BlocksLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BlocksLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        /*final TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.BlocksLayout, defStyle, 0);*/

        mColumns = 3;//a.getInt(R.styleable.TimeRulerView_headerWidth, mColumns);

        //a.recycle();
    }

    private void ensureChildren() {
        mRulerView = (TimeView) findViewById(R.id.blocks_ruler);
        if (mRulerView == null) {
            throw new IllegalStateException("Must include a R.id.blocks_ruler view.");
        }
        mRulerView.setDrawingCacheEnabled(true);

        //mNowView = findViewById(R.id.blocks_now);
        /*if (mNowView == null) {
            throw new IllegalStateException("Must include a R.id.blocks_now view.");
        }*/
        //mNowView.setDrawingCacheEnabled(true);
    }

    /**
     * Remove any {@link BlockView} instances, leaving only
     * {@link TimeRulerView} remaining.
     */
    public void removeAllBlocks() {
        ensureChildren();
        removeAllViews();
        addView(mRulerView);
       // addView(mNowView);
    }

    /*public void addBlock(BlockView blockView) {
        blockView.setDrawingCacheEnabled(true);
        addView(blockView, 1);
    }*/

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        ensureChildren();

        mRulerView.measure(widthMeasureSpec, heightMeasureSpec);
//        mNowView.measure(widthMeasureSpec, heightMeasureSpec);

        final int width = mRulerView.getMeasuredWidth();
        final int height = mRulerView.getMeasuredHeight();

        setMeasuredDimension(resolveSize(width, widthMeasureSpec),
                resolveSize(height, heightMeasureSpec));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        ensureChildren();

        final TimeView rulerView = mRulerView;
        final int headerWidth = rulerView.getHeaderWidth();
        final int columnWidth = (getWidth() - headerWidth) / mColumns;

        rulerView.layout(0, 0, getWidth(), getHeight());

        final int count = getChildCount();
        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            if (child.getVisibility() == GONE) continue;

            /*if (child instanceof BlockView) {
                final BlockView blockView = (BlockView) child;
                final int top = rulerView.getTimeVerticalOffset(blockView.getStartTime());
                final int bottom = rulerView.getTimeVerticalOffset(blockView.getEndTime());
                final int left = headerWidth + (blockView.getColumn() * columnWidth);
                final int right = left + columnWidth;
                child.layout(left, top, right, bottom);
            }*/
        }

        // Align now view to match current time
        final View nowView = mNowView;
        //final long now = UIUtils.getCurrentTime(getContext());

        //final int top = rulerView.getTimeVerticalOffset(now);
        //final int bottom = top + nowView.getMeasuredHeight();
        //final int left = 0;
        //final int right = getWidth();

        //nowView.layout(left, top, right, bottom);
    }
}

