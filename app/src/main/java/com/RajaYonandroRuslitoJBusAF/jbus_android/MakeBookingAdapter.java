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

public class MakeBookingAdapter extends ArrayAdapter<Bus> {

    private Context context;
    private List<Bus> arrayList;
    private final Activity activity;
    public MakeBookingAdapter(@NonNull Context context, int resource, @NonNull List<Bus> arrayList, Activity activity) {
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
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listbusbooking_view, parent, false);
        }

        // get the position of the view from the ArrayAdapter
        TextView busNameTextView = convertView.findViewById(R.id.busNamePay);
        TextView busTypeTextView = convertView.findViewById(R.id.busTypePay);
        TextView departureStationTextView = convertView.findViewById(R.id.departurePay);
        TextView arrivalStationTextView = convertView.findViewById(R.id.arrivalPay);
        TextView priceTextView = convertView.findViewById(R.id.pricePay);
        TextView facilitesTextView = convertView.findViewById(R.id.facilitiesPay);
        TextView capacityTextView = convertView.findViewById(R.id.busCapacityPay);

        if(newbus != null){
            //Bus currentBus = getItem(position);
            busNameTextView.setText(newbus.name);
            busTypeTextView.setText(newbus.busType.toString());
            facilitesTextView.setText(newbus.facilities.toString());
            /*departureStationTextView.setText(newbus.departure.city.name());
            arrivalStationTextView.setText(newbus.arrival.city.name());*/
            capacityTextView.setText(Integer.toString(newbus.capacity));
            priceTextView.setText("IDR " + String.format("%.0f", newbus.price.price));
            if(LoginActivity.loggedAccount != null){
                View currentItemView = convertView;
                ImageView addSched = currentItemView.findViewById(R.id.bookSeat);
                addSched.setOnClickListener(v->{
                    Intent i = new Intent(context, makeBookingActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra("busId", newbus.id);
                    context.startActivity(i);
                });
            } else{
                View currentItemView = convertView;
                ImageView addSched = currentItemView.findViewById(R.id.bookSeat);
                addSched.setVisibility(View.GONE);
            }
        }
        return convertView;
    }
}
