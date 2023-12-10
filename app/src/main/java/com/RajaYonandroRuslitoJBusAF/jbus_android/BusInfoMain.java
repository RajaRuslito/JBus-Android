package com.RajaYonandroRuslitoJBusAF.jbus_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.RajaYonandroRuslitoJBusAF.jbus_android.databinding.ActivityAddScheduleBinding;
import com.RajaYonandroRuslitoJBusAF.jbus_android.model.Algorithm;
import com.RajaYonandroRuslitoJBusAF.jbus_android.model.Bus;
import com.RajaYonandroRuslitoJBusAF.jbus_android.model.Schedule;
import com.RajaYonandroRuslitoJBusAF.jbus_android.request.BaseApiService;
import com.RajaYonandroRuslitoJBusAF.jbus_android.request.UtilsApi;
import com.google.android.material.textfield.TextInputEditText;

/**
 * The BusInfoMain class is an activity that displays detailed information about a specific bus.
 * It retrieves data from the intent and displays it in the corresponding layout elements.
 */
public class BusInfoMain extends AppCompatActivity {

    // Class variables and constants
    private Context mContext;
    private BaseApiService mApiService;
    ActivityAddScheduleBinding binding;
    private TextInputEditText dateText = null;
    private int busId;
    private TextView busName = null;

    /**
     * Called when the activity is starting. Displays detailed information about a specific bus.
     *
     * @param /savedInstanceState A Bundle object containing the activity's previously saved state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_information);

        getSupportActionBar().hide();

        Intent intent = this.getIntent();
        Bundle bundle = getIntent().getExtras();

        if(intent != null) {
            busId = this.getIntent().getIntExtra("busId", -1);
            mContext = this;
            mApiService = UtilsApi.getApiService();

            busName = this.findViewById(R.id.nameOfBus);

            Bus newbus = Algorithm.<Bus>find(MainActivity.listBusMain, bus -> {
                return bus.id == busId;
            });

            busName.setText(newbus.name);
            TextView busIDTextView = this.findViewById(R.id.idOfBus);
            busIDTextView.setText(String.valueOf(busId));
            TextView companyIdTextview = this.findViewById(R.id.idOfCompany);
            companyIdTextview.setText(String.valueOf(newbus.accountId));
            //bookingSeat = (TextInputEditText) findViewById(R.id.seatToBook);
            TextView busTypeTextView = this.findViewById(R.id.typeOfBus);
            busTypeTextView.setText(newbus.busType.toString());
            TextView facilities = this.findViewById(R.id.facilities);
            facilities.setText(newbus.facilities.toString());
            TextView capacity = this.findViewById(R.id.capacity);
            capacity.setText(String.valueOf(newbus.capacity));
            TextView depCity = this.findViewById(R.id.departureCity);
            depCity.setText(newbus.departure.city.toString());
            TextView depStation = this.findViewById(R.id.departureStation);
            depStation.setText(newbus.departure.stationName);

            TextView arrCity = this.findViewById(R.id.arrivalCity);
            arrCity.setText(newbus.arrival.city.toString());
            TextView arrStation = this.findViewById(R.id.arrivalStation);
            arrStation.setText(newbus.arrival.stationName);
            TextView busPrice = this.findViewById(R.id.price);
            busPrice.setText("IDR " + String.format("%.0f", newbus.price.price));

            ListView listView = this.findViewById(R.id.scheduleListView);
            BusInfoAdapter adapter = new BusInfoAdapter(getApplicationContext(), R.layout.schedulelist, newbus.schedules);
            listView.setAdapter(adapter);

        }
    }
}