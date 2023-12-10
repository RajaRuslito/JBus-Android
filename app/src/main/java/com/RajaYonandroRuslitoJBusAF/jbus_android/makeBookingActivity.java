package com.RajaYonandroRuslitoJBusAF.jbus_android;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.RajaYonandroRuslitoJBusAF.jbus_android.databinding.ActivityAddScheduleBinding;
import com.RajaYonandroRuslitoJBusAF.jbus_android.model.Account;
import com.RajaYonandroRuslitoJBusAF.jbus_android.model.Algorithm;
import com.RajaYonandroRuslitoJBusAF.jbus_android.model.BaseResponse;
import com.RajaYonandroRuslitoJBusAF.jbus_android.model.Bus;
import com.RajaYonandroRuslitoJBusAF.jbus_android.model.Payment;
import com.RajaYonandroRuslitoJBusAF.jbus_android.model.Schedule;
import com.RajaYonandroRuslitoJBusAF.jbus_android.request.BaseApiService;
import com.RajaYonandroRuslitoJBusAF.jbus_android.request.UtilsApi;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.timepicker.MaterialTimePicker;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * This activity allows users to make a booking for a selected bus schedule.
 * Users can choose the schedule, seat, and initiate the booking process.
 */
public class makeBookingActivity extends AppCompatActivity {

    // Instance variables
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
    private Spinner seatSelect;
    //private TextInputEditText bookingSeat;
    private Button makeBookButton;
    private int thisBuyerId;
    private int accId;
    private List<String> busSeats;
    private List<String> seatAvaibleList = new ArrayList<>();
    private String selectedSeat = null;

    /**
     * Called when the activity is starting. This is where most initialization should go:
     * calling setContentView(int) to inflate the activity's UI, using findViewById(int)
     * to programmatically interact with widgets in the UI, and setting up event listeners.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously
     *                           being shut down, this Bundle contains the data it most
     *                           recently supplied in onSaveInstanceState(Bundle).
     *                           Note: Otherwise, it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_booking);

        getSupportActionBar().hide();

        Intent intent = this.getIntent();
        Bundle bundle = getIntent().getExtras();

        if (intent != null) {
            busId = this.getIntent().getIntExtra("busId", -1);
            mContext = this;
            mApiService = UtilsApi.getApiService();

            busName = this.findViewById(R.id.nameOfBus);

            Bus newbus = Algorithm.<Bus>find(MainActivity.listBusMain, bus -> {
                return bus.id == busId;
            });

            busName.setText(intent.getStringExtra("busName"));
            TextView busIDTextView = this.findViewById(R.id.idOfBus);
            busIDTextView.setText(String.valueOf(busId));
            TextView companyIdTextview = this.findViewById(R.id.idOfCompany);
            companyIdTextview.setText(String.valueOf(LoginActivity.loggedAccount.id));
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
            seatSelect = this.findViewById(R.id.bookMySeat);

            TextView arrCity = this.findViewById(R.id.arrivalCity);
            arrCity.setText(newbus.arrival.city.toString());
            TextView arrStation = this.findViewById(R.id.arrivalStation);
            arrStation.setText(newbus.arrival.stationName);
            TextView busPrice = this.findViewById(R.id.price);
            busPrice.setText("IDR " + String.format("%.0f", newbus.price.price));

            busScheduleSpinner = this.findViewById(R.id.schedule_listpay);
            ArrayAdapter<Schedule> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, newbus.schedules);
            adapter.setDropDownViewResource(androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item);
            busScheduleSpinner.setAdapter(adapter);

            busScheduleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
                    bookingSchedules = newbus.schedules.get(position);
                    for (Map.Entry<String, Boolean> seat : bookingSchedules.seatAvailability.entrySet()) {
                        if (seat.getValue().equals(true)) {
                            seatAvaibleList.add(seat.getKey());
                        }
                    }
                    AdapterView.OnItemSelectedListener seatOISL = new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
                            // mengisi field selectedBusType sesuai dengan item yang dipilih
                            selectedSeat = seatAvaibleList.get(position);

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    };
                    ArrayAdapter seatBus = new ArrayAdapter(mContext, android.R.layout.simple_list_item_1, seatAvaibleList);
                    seatBus.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                    seatSelect.setAdapter(seatBus);
                    seatSelect.setOnItemSelectedListener(seatOISL);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });


            makeBookButton = this.findViewById(R.id.bookButton);

            makeBookButton.setOnClickListener(view -> {
                handleMakeBooking();
            });


        }
    }

    /**
     * Handles the creation of the activity, setting up UI components and retrieving necessary data.
     */
    //... (remaining methods)

    /**
     * Handles the process of making a booking. Retrieves the selected schedule, seat, and initiates the booking API call.
     */
    protected void handleMakeBooking() {
        Account acc = LoginActivity.loggedAccount;
        List<String> seatS = new ArrayList<>();
        seatS.add(selectedSeat);

        Schedule schedule = (Schedule) busScheduleSpinner.getSelectedItem();

        Bus newbus = Algorithm.<Bus>find(MainActivity.listBusMain, bus -> {
            return bus.accountId == accId;
        });


        mApiService.makeBooking(LoginActivity.loggedAccount.id, accId, busId, seatS, schedule.departureSchedule.toString()).enqueue(new Callback<BaseResponse<Payment>>() {
            @Override
            public void onResponse(Call<BaseResponse<Payment>> call, Response<BaseResponse<Payment>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(mContext, "ERROR" + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                BaseResponse<Payment> res = response.body();
                if (res.success) {
                    viewToast(mContext, res.message);
                    moveActivity(mContext, PaymentActivity.class);
                    /*thisBuyerId++;*/
                }
                Toast.makeText(mContext, res.message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<BaseResponse<Payment>> call, Throwable t) {
                Toast.makeText(mContext, "Server ERROR", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Navigates to another activity.
     *
     * @param ctx The context from which the activity is being called.
     * @param cls The class of the target activity.
     */
    private void moveActivity(Context ctx, Class<?> cls) {
        Intent intent = new Intent(ctx, cls);
        startActivity(intent);
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