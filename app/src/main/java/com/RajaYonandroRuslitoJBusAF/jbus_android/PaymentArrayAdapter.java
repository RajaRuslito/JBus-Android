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
import com.RajaYonandroRuslitoJBusAF.jbus_android.model.Payment;

import java.util.List;

public class PaymentArrayAdapter extends ArrayAdapter<Payment> {

    private Context context;
    private List<Payment> arrayList;
    private List<Bus> newBus;
    private final Activity activity;

    public PaymentArrayAdapter(@NonNull Context context, int resource, @NonNull List<Payment> arrayList, Activity activity) {
        super(context, resource, arrayList);
        this.context = context;
        this.arrayList = arrayList;
        this.activity = activity;
    }


    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // convertView which is recyclable view
        Payment newpay = getItem(position);

        // of the recyclable view is null then inflate the custom layout for the same
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.bookedd_seat_listview, parent, false);
        }

        // get the position of the view from the ArrayAdapter
        TextView busNameTextView = convertView.findViewById(R.id.textView1);
        int busId = newpay.getBusId();
        Bus newbus = MainActivity.listBusMain.get(busId);
        String busName = newbus.name;
        TextView bookedSeatsTextView = convertView.findViewById(R.id.bookedSeatConf);
        TextView bookedSchedTextView = convertView.findViewById(R.id.bookedSchedConf);


        if (newpay != null) {
            busNameTextView.setText(busName);
            bookedSeatsTextView.setText(newpay.busSeats.toString());
            bookedSchedTextView.setText(newpay.departureDate.toString());

            View currentItemView = convertView;
            ImageView editBooking = currentItemView.findViewById(R.id.editBookingImage);
            editBooking.setOnClickListener(v -> {
                Intent i = new Intent(context, PaymentStatusActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("busId", newbus.id);
                i.putExtra("buyerId", newpay.buyerId);
                i.putExtra("busSeats", newpay.busSeats.toString());
                i.putExtra("departureDate", newpay.departureDate.toString());

                context.startActivity(i);

            });
        }
        return convertView;
    }
}