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

public class PaymentBusAdapter extends ArrayAdapter<Bus> {

    private Context context;
    private List<Bus> arrayList;
    /*private final Activity activity;*/
    public PaymentBusAdapter(@NonNull Context context, int resource, @NonNull List<Bus> arrayList/*, Activity activity*/) {
        super(context, resource, arrayList);
        this.context = context;
        this.arrayList = arrayList;
        /*this.activity = activity;*/
    }

    /*public PaymentBusAdapter(Context mContext, List<Payment> scheduleList) {
        super();
    }*/

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // convertView which is recyclable view
        Bus newbus = getItem(position);

        // of the recyclable view is null then inflate the custom layout for the same
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.payment_list_view, parent, false);
        }


        //TextView paymentTextView = convertView.findViewById(R.id.busScheds);

        if(newbus != null){
            //paymentTextView.setText(newbus.schedules.toString());
            /*priceTextView.setText("IDR " + String.format("%.0f", newbus.price.price));
            View currentItemView = convertView;
            ImageView addSched = currentItemView.findViewById(R.id.bookSeat);
            addSched.setOnClickListener(v->{
                Intent i = new Intent(context, makeBookingActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("busId", newbus.id);
                context.startActivity(i);
            });*/
        }
        return convertView;
    }
}

