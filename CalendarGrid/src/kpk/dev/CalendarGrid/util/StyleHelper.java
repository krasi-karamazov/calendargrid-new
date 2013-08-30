package kpk.dev.CalendarGrid.util;

import android.graphics.Color;
import android.graphics.drawable.Drawable;

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
    private float mCurrentMonthTextSize = -1;
    private float mNextMonthTextSize = -1;
    private float mPreviousMonthTextSize = -1;
    private float mTitleTextTextSize = -1;
    private int mTitleTextTextColor = -1;
    private int mCurrentDateTextColor = -1;
    private float mCurrentDateTextSize = -1;
    private int mCurrentDateBackgroundDrawable = -1;
    private int mPreviousMonthBackgroundDrawable = -1;
    private int mNextMonthBackgroundDrawable = -1;
    private int mTitleTextGravity = -1;
    private boolean mShouldShowEvents;
    private String mCalendarName;
    private StyleHelper() {

    }

    public static StyleHelper getInstance() {
        if(sInstance == null) {
            sInstance = new StyleHelper();
        }
        return sInstance;
    }

    public boolean getShouldShowEvents() {
        return mShouldShowEvents;
    }

    public void setShouldShowEvents(boolean mShouldShowEvents) {
        this.mShouldShowEvents = mShouldShowEvents;
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

    public float getCurrentMonthTextSize() {
        return mCurrentMonthTextSize;
    }

    public void setCurrentMonthTextSize(float mCurrentMonthTextSize) {
        this.mCurrentMonthTextSize = mCurrentMonthTextSize;
    }

    public float getNextMonthTextSize() {
        return mNextMonthTextSize;
    }

    public void setNextMonthTextSize(float mNextMonthTextSize) {
        this.mNextMonthTextSize = mNextMonthTextSize;
    }

    public float getPreviousMonthTextSize() {
        return mPreviousMonthTextSize;
    }

    public void setPreviousMonthTextSize(float mPreviousMonthTextSize) {
        this.mPreviousMonthTextSize = mPreviousMonthTextSize;
    }

    public float getTitleTextTextSize() {
        return mTitleTextTextSize;
    }

    public void setTitleTextTextSize(float mTitleTextTextSize) {
        this.mTitleTextTextSize = mTitleTextTextSize;
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

    public float getCurrentDateTextSize() {
        return mCurrentDateTextSize;
    }

    public void setCurrentDateTextSize(float mCurrentDateTextSize) {
        this.mCurrentDateTextSize = mCurrentDateTextSize;
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

    public int getTitleTextGravity() {
        return mTitleTextGravity;
    }

    public void setTitleTextGravity(int mTitleTextGravity) {
        this.mTitleTextGravity = mTitleTextGravity;
    }
}
