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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.RajaYonandroRuslitoJBusAF.jbus_android.model.Bus;

import java.util.List;

/**
 * An ArrayAdapter for populating a ListView with Bus items for booking.
 */
public class MakeBookingAdapter extends ArrayAdapter<Bus> {

    // Class variables and constants
    private Context context;
    private List<Bus> arrayList;
    private final Activity activity;

    /**
     * Constructs a MakeBookingAdapter.
     *
     * @param context   The context in which the adapter is being used.
     * @param resource  The resource ID for a layout file containing a TextView to use when
     *                  instantiating views.
     * @param arrayList The List of Bus items to be displayed.
     * @param activity  The activity associated with the adapter.
     */
    public MakeBookingAdapter(@NonNull Context context, int resource, @NonNull List<Bus> arrayList, Activity activity) {
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
            departureStationTextView.setText(newbus.departure.stationName);
            arrivalStationTextView.setText(newbus.arrival.stationName);
            capacityTextView.setText(Integer.toString(newbus.capacity));
            priceTextView.setText("IDR " + String.format("%.0f", newbus.price.price));
            View currentItemView = convertView;

            if(LoginActivity.loggedAccount != null){
                ImageView addSched = currentItemView.findViewById(R.id.bookSeat);
                addSched.setOnClickListener(v->{
                    Intent i = new Intent(context, makeBookingActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra("busId", newbus.id);
                    i.putExtra("departure", newbus.schedules.toString());
                    context.startActivity(i);
                });
                ImageView busInfo = currentItemView.findViewById(R.id.info);
                busInfo.setOnClickListener(v->{
                    Intent i = new Intent(context, BusInfoMain.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra("busId", newbus.id);
                    context.startActivity(i);
                });
            } else{
                ImageView addSched = currentItemView.findViewById(R.id.bookSeat);
                addSched.setVisibility(View.GONE);
                ImageView busInfo = currentItemView.findViewById(R.id.info);
                busInfo.setVisibility(View.GONE);
            }
        }
        return convertView;
    }

    /**
     * Displays a toast message in the specified context.
     *
     * @param ctx     The context in which the toast should be displayed.
     * @param message The message to be displayed in the toast.
     */
    private void viewToast(Context ctx, String message) {
        Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show();
    }
}
