package com.RajaYonandroRuslitoJBusAF.jbus_android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.RajaYonandroRuslitoJBusAF.jbus_android.model.Algorithm;
import com.RajaYonandroRuslitoJBusAF.jbus_android.model.Bus;
import com.RajaYonandroRuslitoJBusAF.jbus_android.model.Schedule;

import java.util.List;

/**
 * The BusInfoAdapter class is an ArrayAdapter used for displaying a list of schedules associated with a bus
 * in a customized view. It inflates a custom layout for each item in the list, showing relevant schedule information.
 */
public class BusInfoAdapter extends ArrayAdapter<Schedule> {

    // Class variables and constants
    private Context context;
    private List<Schedule> arrayList;

    /**
     * Initializes the BusInfoAdapter with the specified context, resource, and list of schedules.
     *
     * @param /context   The context in which the adapter is being used.
     * @param /resource  The resource ID for the layout file containing a TextView to use when instantiating views.
     * @param /arrayList The list of schedules to be displayed.
     */
    public BusInfoAdapter (@NonNull Context context, int resource, @NonNull List<Schedule> arrayList) {
        super(context, resource, arrayList);
        this.context = context;
        this.arrayList = arrayList;
    }

    /**
     * Returns the custom view for each item in the array. If the recycled view is null, it inflates the
     * custom layout for the item. Binds data from the Schedule object to the corresponding TextView.
     *
     * @param /position    The position of the item within the array.
     * @param /convertView The recycled view to populate.
     * @param /parent      The parent view that the recycled view will be attached to.
     * @return The View for the position in the AdapterView.
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // convertView which is recyclable view
        Schedule schedule = getItem(position);

        Bus bus = Algorithm.<Bus>find(ManageBusActivity.listBus, b -> {
            return b.schedules == schedule;
        });

        // of the recyclable view is null then inflate the custom layout for the same
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.mainschedlist, parent, false);
        }

        // get the position of the view from the ArrayAdapter
        TextView schedListView = convertView.findViewById(R.id.schedList);

        if(schedule != null){
            schedListView.setText(schedule.toString());
        }
        return convertView;
    }
}
