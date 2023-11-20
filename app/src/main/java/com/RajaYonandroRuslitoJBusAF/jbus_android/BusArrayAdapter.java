package com.RajaYonandroRuslitoJBusAF.jbus_android;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import com.RajaYonandroRuslitoJBusAF.jbus_android.model.Bus;

public class BusArrayAdapter extends ArrayAdapter<Bus> {

    private Context context;
    private List<Bus> arrayList;
    public BusArrayAdapter(@NonNull Context context, int resource, @NonNull List<Bus> arrayList) {
        super(context, resource, arrayList);
        this.context = context;
        this.arrayList = arrayList;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // convertView which is recyclable view
        Bus newbus = getItem(position);

        // of the recyclable view is null then inflate the custom layout for the same
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.bus_view, parent, false);
        }

        // get the position of the view from the ArrayAdapter
        TextView busNameTextView = convertView.findViewById(R.id.textView1);
        TextView departureStationTextView = convertView.findViewById(R.id.textView3);
        TextView arrivalStationTextView = convertView.findViewById(R.id.textView2);
        TextView priceTextView = convertView.findViewById(R.id.price);

        if(newbus != null){
            busNameTextView.setText(newbus.name);
            departureStationTextView.setText("From : " + newbus.departure.city.name());
            arrivalStationTextView.setText(newbus.arrival.city.name());
            priceTextView.setText("IDR " + String.format("%.2f", newbus.price.price));
        }
        return convertView;
    }
}
