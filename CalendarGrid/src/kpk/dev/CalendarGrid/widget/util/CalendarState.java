package kpk.dev.CalendarGrid.widget.util;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

/**
 * Created with IntelliJ IDEA.
 * User: krasimir.karamazov
 * Date: 9/3/13
 * Time: 3:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class CalendarState extends View.BaseSavedState {
    private int mMonth;
    private int mYear;

    public CalendarState(Parcelable superState) {
        super(superState);
    }

    public void setYear(int year) {
        mYear = year;
    }

    public int getYear() {
        return mYear;
    }

    public void setMonth(int month) {
        mMonth = month;
    }

    public int getMonth() {
        return mMonth;
    }

    @Override
    public int describeContents() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(mMonth);
        out.writeInt(mYear);
    }

    public static Creator<CalendarState> creator = new Creator<CalendarState>() {
        @Override
        public CalendarState createFromParcel(Parcel in) {
            return new CalendarState(in);
        }

        @Override
        public CalendarState[] newArray(int size) {
            return new CalendarState[size];  //To change body of implemented methods use File | Settings | File Templates.
        }
    };

    private CalendarState(Parcel in) {
        super(in);
        mMonth = in.readInt();
        mYear = in.readInt();
    }
}
