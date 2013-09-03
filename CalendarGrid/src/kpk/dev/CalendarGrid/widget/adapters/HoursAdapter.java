package kpk.dev.CalendarGrid.widget.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import kpk.dev.CalendarGrid.R;
import kpk.dev.CalendarGrid.widget.models.HourModel;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: krasimir.karamazov
 * Date: 9/3/13
 * Time: 6:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class HoursAdapter extends ArrayAdapter<HourModel> {


    public HoursAdapter(Context context, List<HourModel> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if(row == null) {
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.hour_item_layout, parent, false);
        }
        TextView hourLabel = (TextView)row.findViewById(R.id.h_label);

        if(getItem(position).getIsInSpan()){
            if(getItem(position).getIsFirstInSpan()){
                hourLabel.setBackgroundColor(Color.BLACK);
            }else{
                hourLabel.setBackgroundColor(Color.RED);
            }
            hourLabel.setText("" + getItem(position).getHour());
        }else{
            hourLabel.setText("" + getItem(position).getHour());
        }

        return row;
    }
}
