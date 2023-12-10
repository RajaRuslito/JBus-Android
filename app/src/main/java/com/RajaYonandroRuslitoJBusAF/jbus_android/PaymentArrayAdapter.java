package com.RajaYonandroRuslitoJBusAF.jbus_android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.RajaYonandroRuslitoJBusAF.jbus_android.model.Algorithm;
import com.RajaYonandroRuslitoJBusAF.jbus_android.model.Bus;
import com.RajaYonandroRuslitoJBusAF.jbus_android.model.Payment;

import java.util.List;

/**
 * Custom ArrayAdapter for displaying Payment information in a ListView.
 */
public class PaymentArrayAdapter extends ArrayAdapter<Payment> {

    // Class variables and constants
    private Context context;
    public static List<Payment> arrayList;
    private List<Bus> newBus;
    private final Activity activity;

    /**
     * Constructor for the PaymentArrayAdapter.
     *
     * @param context   The context in which the adapter is created.
     * @param resource  The resource ID for a layout file containing a TextView to use when instantiating views.
     * @param arrayList The List of Payment objects to be displayed.
     * @param activity  The activity associated with the adapter.
     */
    public PaymentArrayAdapter(@NonNull Context context, int resource, @NonNull List<Payment> arrayList, Activity activity) {
        super(context, resource, arrayList);
        this.context = context;
        this.arrayList = arrayList;
        this.activity = activity;
    }

    /**
     * Get a View that displays the data at the specified position in the data set.
     *
     * @param position    The position of the item within the adapter's data set.
     * @param convertView The old view to reuse, if possible.
     * @param parent      The parent that this view will eventually be attached to.
     * @return A View corresponding to the data at the specified position.
     */
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
        Bus newbus = Algorithm.<Bus>find(MainActivity.listBusMain, bus -> {
            return bus.id == busId;
        });
        TextView bookedSeatsTextView = convertView.findViewById(R.id.bookedSeatConf);
        TextView bookedSchedTextView = convertView.findViewById(R.id.bookedSchedConf);

        if (newpay != null) {
            busNameTextView.setText(newbus.name);
            bookedSeatsTextView.setText(newpay.busSeats.toString());
            bookedSchedTextView.setText(newpay.departureDate.toString());

            View currentItemView = convertView;
            ImageView editBooking = currentItemView.findViewById(R.id.editBookingImage);
            editBooking.setOnClickListener(v -> {
                Intent i = new Intent(context, PaymentStatusActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("busId", newbus.id);
                i.putExtra("buyerId", newpay.buyerId);
                i.putExtra("id", newpay.id);
                i.putExtra("renterId", newpay.renterId);
                i.putExtra("busSeats", newpay.busSeats.toString());
                i.putExtra("departureDate", newpay.departureDate.toString());
                i.putExtra("status", newpay.status.toString());
                context.startActivity(i);

            });
        }
        return convertView;
    }
}