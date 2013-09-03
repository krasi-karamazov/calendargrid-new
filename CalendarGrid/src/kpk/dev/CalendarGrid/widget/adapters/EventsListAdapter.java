package kpk.dev.CalendarGrid.widget.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import kpk.dev.CalendarGrid.R;

import kpk.dev.CalendarGrid.widget.models.Instance;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: krasimir.karamazov
 * Date: 8/30/13
 * Time: 7:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class EventsListAdapter extends ArrayAdapter<Instance> {

    public EventsListAdapter(Context context, List<Instance> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }

    static class ViewHolder{
        TextView titleText;
        TextView colorView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder;
        if(row == null) {
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.event_list_item_layout, parent, false);
            holder = new ViewHolder();
            holder.titleText = (TextView)row.findViewById(R.id.title);
            holder.colorView = (TextView)row.findViewById(R.id.calendar_color);
            row.setTag(holder);
        }
        holder = (ViewHolder)row.getTag();
        holder.titleText.setText(getItem(position).getTitle());
        holder.colorView.setBackgroundColor(getItem(position).getCalendarColor());

        return row;
    }
}
