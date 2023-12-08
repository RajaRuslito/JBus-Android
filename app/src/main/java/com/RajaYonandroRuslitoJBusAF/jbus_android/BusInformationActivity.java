package com.RajaYonandroRuslitoJBusAF.jbus_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.RajaYonandroRuslitoJBusAF.jbus_android.databinding.ActivityAddScheduleBinding;
import com.RajaYonandroRuslitoJBusAF.jbus_android.model.Bus;
import com.RajaYonandroRuslitoJBusAF.jbus_android.model.Schedule;
import com.RajaYonandroRuslitoJBusAF.jbus_android.request.BaseApiService;
import com.RajaYonandroRuslitoJBusAF.jbus_android.request.UtilsApi;
import com.google.android.material.textfield.TextInputEditText;

public class BusInformationActivity extends AppCompatActivity {
    private Context mContext;
    private BaseApiService mApiService;
    ActivityAddScheduleBinding binding;
    private TextInputEditText dateText = null;
    private int busId;
    private ImageView addSchedule;
    private String selectedDate;
    private Schedule bookingSchedules;
    private TextView busName = null;
    private Spinner busScheduleSpinner;
    private TextInputEditText bookingSeat;
    private Button makeBookButton;
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

            Bus newbus = ManageBusActivity.listBus.get(busId);

            busName.setText(newbus.name);
            TextView busIDTextView = this.findViewById(R.id.idOfBus);
            busIDTextView.setText(String.valueOf(busId));
            TextView companyIdTextview = this.findViewById(R.id.idOfCompany);
            companyIdTextview.setText(String.valueOf(LoginActivity.loggedAccount.company.id));
            //bookingSeat = (TextInputEditText) findViewById(R.id.seatToBook);
            TextView busTypeTextView = this.findViewById(R.id.typeOfBus);
            busTypeTextView.setText(newbus.busType.toString());
            TextView facilities = this.findViewById(R.id.facilities);
            facilities.setText(newbus.facilities.toString());
            TextView capacity = this.findViewById(R.id.capacity);
            capacity.setText(String.valueOf(newbus.capacity));


            busScheduleSpinner = this.findViewById(R.id.schedule_listpay);
            ArrayAdapter<Schedule> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, newbus.schedules);
            adapter.setDropDownViewResource(androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item);
            busScheduleSpinner.setAdapter(adapter);

        }
    }
}