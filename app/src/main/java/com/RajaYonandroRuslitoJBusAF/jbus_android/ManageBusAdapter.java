package com.RajaYonandroRuslitoJBusAF.jbus_android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.RajaYonandroRuslitoJBusAF.jbus_android.model.Bus;

import java.util.List;

/**
 * Custom ArrayAdapter for managing the display of buses in a ListView.
 */
public class ManageBusAdapter extends ArrayAdapter<Bus> {

    // Class variables and constants
    private Context context;
    private List<Bus> arrayList;
    private final Activity activity;

    /**
     * Constructor for the ManageBusAdapter.
     *
     * @param context  The context in which the adapter is created.
     * @param resource The resource ID for a layout file containing a TextView to use when instantiating views.
     * @param arrayList The list of buses to be displayed.
     * @param activity The activity associated with the adapter.
     */
    public ManageBusAdapter(@NonNull Context context, int resource, @NonNull List<Bus> arrayList, Activity activity) {
        super(context, resource, arrayList);
        this.context = context;
        this.arrayList = arrayList;
        this.activity = activity;
    }

    /**
     * Get a View that displays the data at the specified position in the data set.
     *
     * @param position    The position of the item within the adapter's data set of the item whose view we want.
     * @param convertView The old view to reuse, if possible. Note: You should check that this view is non-null and of an appropriate type before using.
     * @param parent      The parent that this view will eventually be attached to.
     * @return A View corresponding to the data at the specified position.
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // convertView which is recyclable view
        Bus newbus = getItem(position);

        // of the recyclable view is null then inflate the custom layout for the same
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.new_bus_view, parent, false);
        }

        // get the position of the view from the ArrayAdapter
        TextView busNameTextView = convertView.findViewById(R.id.textView1);
        TextView busTypeTextView = convertView.findViewById(R.id.busType_id);
        TextView departureStationTextView = convertView.findViewById(R.id.departure_id);
        TextView arrivalStationTextView = convertView.findViewById(R.id.arrival_id);

        if(newbus != null){
            busNameTextView.setText(newbus.name);
            busTypeTextView.setText(newbus.busType.toString());
            departureStationTextView.setText(newbus.departure.stationName);
            arrivalStationTextView.setText(newbus.arrival.stationName);

            View currentItemView = convertView;
            ImageView addSched = currentItemView.findViewById(R.id.addSched);
            addSched.setOnClickListener(v->{
                Intent i = new Intent(context, AddSchedule.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("busId", newbus.id);
                i.putExtra("name", newbus.name);
                context.startActivity(i);
            });
            ImageView busInfo = currentItemView.findViewById(R.id.busInfo);
            busInfo.setOnClickListener(v->{
                Intent i = new Intent(context, BusInformationActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("busId", newbus.id);
                i.putExtra("name", newbus.name);
                context.startActivity(i);
            });
        }
        return convertView;
    }
}
