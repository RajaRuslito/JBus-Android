package com.RajaYonandroRuslitoJBusAF.jbus_android;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.format.DateFormat;
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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class makeBookingActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_make_booking);

        getSupportActionBar().hide();

        Intent intent = this.getIntent();
        Bundle bundle = getIntent().getExtras();

        if(intent != null) {
            busId = this.getIntent().getIntExtra("busId", -1);
            mContext = this;
            mApiService = UtilsApi.getApiService();

            busName = this.findViewById(R.id.nameOfBus);

            Bus newbus = MainActivity.listBusMain.get(busId);

            busName.setText(newbus.name);
            TextView busIDTextView = this.findViewById(R.id.idOfBus);
            busIDTextView.setText(String.valueOf(busId));
            TextView buyerIdTextview = this.findViewById(R.id.idOfBuyer);
            buyerIdTextview.setText(String.valueOf(LoginActivity.loggedAccount.id));
            TextView companyIdTextview = this.findViewById(R.id.idOfCompany);
            companyIdTextview.setText(String.valueOf(LoginActivity.loggedAccount.company.id));
            bookingSeat = (TextInputEditText) findViewById(R.id.seatToBook);
            TextView capacityOfBus = this.findViewById(R.id.capacityOfBus);
            capacityOfBus.setText(String.valueOf(newbus.capacity));


            busScheduleSpinner = this.findViewById(R.id.schedule_listpay);
            ArrayAdapter<Schedule> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, newbus.schedules);
            adapter.setDropDownViewResource(androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item);
            busScheduleSpinner.setAdapter(adapter);

            busScheduleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
                    bookingSchedules = newbus.schedules.get(position);

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            makeBookButton = this.findViewById(R.id.bookButton);

            makeBookButton.setOnClickListener(view -> {
                handleMakeBooking();
                //moveActivity(mContext, PaymentActivity.class);
            });


        }


    }

    protected void handleMakeBooking(){
        Account acc = LoginActivity.loggedAccount;
        List<String> seatS = new ArrayList<>();
        String seat = bookingSeat.getText().toString();
        String seatPrefix = "AF" + bookingSeat.getText().toString();
        seatS.add(seatPrefix);
        Schedule schedule = (Schedule) busScheduleSpinner.getSelectedItem();
        mApiService.makeBooking(acc.id, acc.company.id, busId, seatS, schedule.departureSchedule.toString()).enqueue(new Callback<BaseResponse<Payment>>() {
            @Override
            public void onResponse(Call<BaseResponse<Payment>> call, Response<BaseResponse<Payment>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(mContext, "ERROR" + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                BaseResponse<Payment> res = response.body();
                if(res.success){
                    viewToast(mContext, res.message);
                    moveActivity(mContext, PaymentActivity.class);
                }
                Toast.makeText(mContext, res.message, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<BaseResponse<Payment>> call, Throwable t) {
                Toast.makeText(mContext, "Server ERROR", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void moveActivity(Context ctx, Class<?> cls) {
        Intent intent = new Intent(ctx, cls);
        startActivity(intent);
    }

    private void viewToast(Context ctx, String message) {
        Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show();
    }

}