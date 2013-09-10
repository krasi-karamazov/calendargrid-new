package kpk.dev.CalendarGrid.widget.util;

/**
 * Created with IntelliJ IDEA.
 * User: krasimir.karamazov
 * Date: 8/29/13
 * Time: 5:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class StyleHelper {
    private static StyleHelper sInstance;
    private int mCurrentMonthTextColor = -1;
    private int mCurrentMonthBackgroundDrawable = -1;
    private int mNextMonthTextColor = - 1;
    private int mPreviousMonthTextColor = -1;
    private int mTitleTextTextColor = -1;
    private int mCurrentDateTextColor = -1;
    private int mCurrentDateBackgroundDrawable = -1;
    private int mPreviousMonthBackgroundDrawable = -1;
    private int mNextMonthBackgroundDrawable = -1;
    private int mDisplayType = -1;
    private boolean mShouldShowArrows;
    private int mLeftArrowDrawable;
    private int mRightArrowDrawable;
    private String mCalendarName;
    private long mSelectecCalendarID = -1;
    private int mEventDatesDrawable;
    private StyleHelper() {

    }

    public void setEventDatesDrawable(int drawable) {
        mEventDatesDrawable = drawable;
    }

    public int getEventDatesDrawable() {
        return mEventDatesDrawable;
    }

    public static StyleHelper getInstance() {
        if(sInstance == null) {
            sInstance = new StyleHelper();
        }
        return sInstance;
    }

    public boolean getShouldShowArrows() {
        return mShouldShowArrows;
    }

    public void setShouldShowArrows(boolean shouldShowArrows) {
        this.mShouldShowArrows = shouldShowArrows;
    }

    public String getCalendarName() {
        return mCalendarName;
    }

    public void setCalendarName(String mCalendarName) {
        this.mCalendarName = mCalendarName;
    }

    public int getCurrentMonthBackgroundDrawable() {
        return mCurrentMonthBackgroundDrawable;
    }

    public void setCurrentMonthBackgroundDrawable(int mCurrentMonthBackgroundDrawable) {
        this.mCurrentMonthBackgroundDrawable = mCurrentMonthBackgroundDrawable;
    }

    public int getCurrentMonthTextColor() {
        return mCurrentMonthTextColor;
    }

    public void setCurrentMonthTextColor(int mCurrentMonthTextColor) {
        this.mCurrentMonthTextColor = mCurrentMonthTextColor;
    }

    public int getNextMonthTextColor() {
        return mNextMonthTextColor;
    }

    public void setNextMonthTextColor(int mNextMonthTextColor) {
        this.mNextMonthTextColor = mNextMonthTextColor;
    }

    public int getPreviousMonthTextColor() {
        return mPreviousMonthTextColor;
    }

    public void setPreviousMonthTextColor(int mPreviousMonthTextColor) {
        this.mPreviousMonthTextColor = mPreviousMonthTextColor;
    }

    public int getTitleTextTextColor() {
        return mTitleTextTextColor;
    }

    public void setTitleTextTextColor(int mTitleTextTextColor) {
        this.mTitleTextTextColor = mTitleTextTextColor;
    }

    public int getCurrentDateTextColor() {
        return mCurrentDateTextColor;
    }

    public void setCurrentDateTextColor(int mCurrentDateTextColor) {
        this.mCurrentDateTextColor = mCurrentDateTextColor;
    }

    public int getCurrentDateBackgroundDrawable() {
        return mCurrentDateBackgroundDrawable;
    }

    public void setCurrentDateBackgroundDrawable(int mCurrentDateBackgroundDrawable) {
        this.mCurrentDateBackgroundDrawable = mCurrentDateBackgroundDrawable;
    }

    public int getPreviousMonthBackgroundDrawable() {
        return mPreviousMonthBackgroundDrawable;
    }

    public void setPreviousMonthBackgroundDrawable(int mPreviousMonthBackgroundDrawable) {
        this.mPreviousMonthBackgroundDrawable = mPreviousMonthBackgroundDrawable;
    }

    public int getNextMonthBackgroundDrawable() {
        return mNextMonthBackgroundDrawable;
    }

    public void setNextMonthBackgroundDrawable(int mNextMonthBackgroundDrawable) {
        this.mNextMonthBackgroundDrawable = mNextMonthBackgroundDrawable;
    }

    public int getDisplayType() {
        return mDisplayType;
    }

    public void setDisplayType(int displayType) {
        this.mDisplayType = displayType;
    }

    public long getSelectedCalendarId() {
        return mSelectecCalendarID;
    }

    public void setSelectedCalendarId(long selectedCalendarId) {
        this.mSelectecCalendarID = selectedCalendarId;
    }

    public int getLeftArrowDrawable() {
        return mLeftArrowDrawable;
    }

    public void setLeftArrowDrawable(int leftArrowDrawable) {
        this.mLeftArrowDrawable = leftArrowDrawable;
    }

    public int getRightArrowDrawable() {
        return mRightArrowDrawable;
    }

    public void setRightArrowDrawable(int rightArrowDrawable) {
        this.mRightArrowDrawable = rightArrowDrawable;
    }
}
