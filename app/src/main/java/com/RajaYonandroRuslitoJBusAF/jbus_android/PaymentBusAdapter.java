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

/** This class is not used as of now
 */
public class PaymentBusAdapter extends ArrayAdapter<Bus> {

    private Context context;
    private List<Bus> arrayList;
    public PaymentBusAdapter(@NonNull Context context, int resource, @NonNull List<Bus> arrayList) {
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
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.payment_list_view, parent, false);
        }


        //TextView paymentTextView = convertView.findViewById(R.id.busScheds);

        if(newbus != null){

        }
        return convertView;
    }
}

