package com.RajaYonandroRuslitoJBusAF.jbus_android;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.RajaYonandroRuslitoJBusAF.jbus_android.databinding.ActivityAddScheduleBinding;
import com.RajaYonandroRuslitoJBusAF.jbus_android.model.Algorithm;
import com.RajaYonandroRuslitoJBusAF.jbus_android.model.Bus;
import com.RajaYonandroRuslitoJBusAF.jbus_android.model.Schedule;
import com.RajaYonandroRuslitoJBusAF.jbus_android.request.BaseApiService;
import com.RajaYonandroRuslitoJBusAF.jbus_android.request.UtilsApi;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

/**
 * The BusInfoMainAdapter class is an ArrayAdapter that serves as a custom adapter for displaying
 * schedule information in the BusInfoMain activity. It extends the ArrayAdapter class and overrides
 * the getView method to customize the appearance of each item in the ListView.
 */
public class BusInfoMainAdapter extends ArrayAdapter<Schedule> {

    // Class variables and constants
    private Context context;
    private List<Schedule> arrayList;

    /**
     * Creates a new instance of BusInfoMainAdapter.
     *
     * @param /context   The context in which the adapter is being used.
     * @param /resource  The resource ID for a layout file containing a TextView to use when
     *                  instantiating views.
     * @param /arrayList The list of schedules to be displayed by the adapter.
     */
    public BusInfoMainAdapter (@NonNull Context context, int resource, @NonNull List<Schedule> arrayList) {
        super(context, resource, arrayList);
        this.context = context;
        this.arrayList = arrayList;
    }

    /**
     * Get a View that displays the data at the specified position in the data set.
     *
     * @param /position    The position of the item within the adapter's data set.
     * @param /convertView The old view to reuse, if possible.
     * @param /parent      The parent that this view will eventually be attached to.
     * @return The View corresponding to the data at the specified position.
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
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.schedulelist, parent, false);
        }

        // get the position of the view from the ArrayAdapter
        TextView schedListView = convertView.findViewById(R.id.schedList);

        if(schedule != null){
            schedListView.setText(schedule.toString());

        }
        return convertView;
    }


}