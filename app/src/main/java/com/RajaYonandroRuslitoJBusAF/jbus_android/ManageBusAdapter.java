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

public class ManageBusAdapter extends ArrayAdapter<Bus> {

    private Context context;
    private List<Bus> arrayList;
    private final Activity activity;
    public ManageBusAdapter(@NonNull Context context, int resource, @NonNull List<Bus> arrayList, Activity activity) {
        super(context, resource, arrayList);
        this.context = context;
        this.arrayList = arrayList;
        this.activity = activity;
    }
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
        /*TextView priceTextView = convertView.findViewById(R.id.price);*/

        if(newbus != null){
            //Bus currentBus = getItem(position);
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
                context.startActivity(i);
            });
            ImageView busInfo = currentItemView.findViewById(R.id.busInfo);
            busInfo.setOnClickListener(v->{
                Intent i = new Intent(context, BusInformationActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("busId", newbus.id);
                context.startActivity(i);
            });
            /*priceTextView.setText("IDR " + String.format("%.2f", newbus.price.price));*/
        }
        return convertView;
    }
}
