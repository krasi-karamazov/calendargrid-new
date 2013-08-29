package kpk.dev.CalendarGrid.widget.views;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import org.joda.time.DateTime;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: krasimir.karamazov
 * Date: 8/28/13
 * Time: 7:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class InfiniteViewPager extends ViewPager {
    //how many pages to offset so we can go back
    public static final int OFFSET = 1000;

    //we need this so we can set wrap_content and the size be calculated if we need it
    private List<DateTime> mDatesList;

    //the row height. See onMeasure.
    private int mRowHeight = 0;

    public InfiniteViewPager(Context context) {
        super(context);
    }

    public InfiniteViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setDatesList(List<DateTime> dates) {
        mDatesList = dates;
    }

    @Override
    public void setAdapter(PagerAdapter adapter) {
        super.setAdapter(adapter);
        setCurrentItem(OFFSET);
    }

    @Override
    public PagerAdapter getAdapter() {
        return super.getAdapter();
    }

    /**
     * See http://stackoverflow.com/questions/9313554/measuring-a-viewpager for reference
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
   /* @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // Calculate row height
        int rows = mDatesList.size() / 7;

        boolean wrapHeight = MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.AT_MOST;

        int height = getMeasuredHeight();
        if (wrapHeight && mRowHeight == 0) {
			*//*
			 * The first super.onMeasure call made the pager take up all the
			 * available height. Since we really wanted to wrap it, we need to
			 * remeasure it. Luckily, after that call the first child is now
			 * available. So, we take the height from it.
			 *//*

            int width = getMeasuredWidth();

            // Use the previously measured width but simplify the calculations
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(width,
                    MeasureSpec.EXACTLY);

			*//*
			 * If the pager actually has any children, take the first child's
			 * height and call that our own
			 *//*
            if (getChildCount() > 0) {
                View firstChild = getChildAt(0);

				*//*
				 * The child was previously measured with exactly the full
				 * height. Allow it to wrap this time around.
				 *//*
                firstChild.measure(widthMeasureSpec, MeasureSpec
                        .makeMeasureSpec(height, MeasureSpec.AT_MOST));

                height = firstChild.getMeasuredHeight();
                mRowHeight = height / rows;
            }
        }

        int calHeight = 0;
        // because the calendar tipically has 6 rows we hardcode the value
        calHeight = mRowHeight * 6;


        // If the calculated height is bigger than the parent height, set it to
        // parent height so the gridview can be scrolled
        if (calHeight > height) {
            calHeight = height;
        }

        heightMeasureSpec = MeasureSpec.makeMeasureSpec(calHeight,
                MeasureSpec.EXACTLY);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }*/
}
