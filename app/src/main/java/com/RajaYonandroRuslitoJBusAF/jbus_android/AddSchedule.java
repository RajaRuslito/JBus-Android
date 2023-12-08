package com.RajaYonandroRuslitoJBusAF.jbus_android;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
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

public class AddSchedule extends AppCompatActivity {

    private Context mContext;
    private BaseApiService mApiService;
    ActivityAddScheduleBinding binding;
    private TextInputEditText dateText = null;
    private int busId;
    private ImageView addSchedule;
    private String selectedDate;
    private TextView busName = null;
    private Spinner busScheduleSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule);
        getSupportActionBar().hide();

        binding = ActivityAddScheduleBinding.inflate(getLayoutInflater());

        Intent intent = this.getIntent();
        Bundle bundle = getIntent().getExtras();

        if(intent != null) {
            /*addSched = (TextInputEditText) findViewById(R.id.addSchedule);*/
            Button addSchedButton = findViewById(R.id.addScheduleButton);
            /*addSchedule = this.findViewById(R.id.add_a_schedule);*/
            dateText = this.findViewById(R.id.selectedDateText);
            addSchedule = this.findViewById(R.id.add_a_schedule);
            busId = this.getIntent().getIntExtra("busId", -1);
            mContext = this;
            mApiService = UtilsApi.getApiService();

            dateText.setOnClickListener(v->showDialog());

            busName = this.findViewById(R.id.busNameSched);

            Bus busbus = ManageBusActivity.listBus.get(busId);

            busName.setText(busbus.name);

            /*String name = intent.getStringExtra("busName");
            binding.busNameSched.setText(name);*/

            /*ListView scheduleList = this.findViewById(R.id.schedule_list);
            ArrayAdapter<Schedule> adapter = new ArrayAdapter<>(mContext, R.layout.activity_add_schedule, newbus.schedules);
            scheduleList.setAdapter(adapter);*/

            busScheduleSpinner = this.findViewById(R.id.schedule_list);
            ArrayAdapter<Schedule> adapter = new ArrayAdapter<>(mContext, android.R.layout.simple_list_item_1, busbus.schedules);
            adapter.setDropDownViewResource(androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item);
            busScheduleSpinner.setAdapter(adapter);

            addSchedButton.setOnClickListener(v->handleAddSchedule());
            /*showSchedule();*/
            /*addSchedButton.setOnClickListener(view -> {
                //Account account = LoginActivity.loggedAccount;
                handleAddSchedule();

            });*/
        }


    }

    protected void handleAddSchedule() {
        mApiService.addSchedule(busId, selectedDate).enqueue(new Callback<BaseResponse<Bus>>() {
            @Override
            public void onResponse(Call<BaseResponse<Bus>> call, Response<BaseResponse<Bus>> response) {
                if (!response.isSuccessful()) return;
                BaseResponse<Bus> res = response.body();

                if (res.success) {
                    viewToast(mContext, "Adding Schedule Success!");
                    moveActivity(mContext, ManageBusActivity.class);
                    finish();
                }


                Toast.makeText(mContext, res.message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<BaseResponse<Bus>> call, Throwable t) {
                Toast.makeText(mContext, "Server ERROR", Toast.LENGTH_SHORT).show();
            }
                /*List<Schedule> l = response.body().payload.schedules;
                ArrayAdapter<Schedule> adapter = new ArrayAdapter<>(mContext, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, l);
                scheduleList.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<BaseResponse<Bus>> call, Throwable t) {

            }*/
        });
    }
    protected void showDialog(){
        AlertDialog dialog = new AlertDialog.Builder(mContext).create();
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_date_setter, null);
        TextInputLayout editTextDate = dialogView.findViewById(R.id.editTextDate);
        TextInputLayout editTextTime = dialogView.findViewById(R.id.editTextTime);
        Button saveDate = dialogView.findViewById(R.id.buttonSaveDate);

        AtomicReference<String> theDate = new AtomicReference<>();
        AtomicReference<String> theTime = new AtomicReference<>();

        editTextDate.getEditText().setOnClickListener(t -> {
            MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
            builder.setTitleText("Select Date");
            builder.setCalendarConstraints(new CalendarConstraints.Builder().setValidator(DateValidatorPointForward.now()).build());
            MaterialDatePicker datePicker = builder.build();
            datePicker.show(getSupportFragmentManager(), "DATE_PICKER");
            datePicker.addOnPositiveButtonClickListener(selection -> {
                String date = DateFormat.format("yyyy-MM-dd", (long) selection).toString();
                editTextDate.getEditText().setText(date);
                theDate.set(date);
            });
        });

        editTextTime.getEditText().setOnClickListener(t -> {
            MaterialTimePicker.Builder builder = new MaterialTimePicker.Builder();
            builder.setTitleText("Select Time");
            MaterialTimePicker timePicker = builder.build();
            timePicker.show(getSupportFragmentManager(), "TIME_PICKER");
            timePicker.addOnPositiveButtonClickListener(selection -> {
                String formattedTime = formatTime(""+timePicker.getHour(), ""+timePicker.getMinute());;
                editTextTime.getEditText().setText(formattedTime);
                theTime.set(formattedTime);
            });
        });

        saveDate.setOnClickListener(t -> {
            Toast.makeText(mContext, "Date saved", Toast.LENGTH_SHORT).show();
            selectedDate = theDate.get() + " " + theTime.get();
            dateText.setText(selectedDate);
            dialog.dismiss();
        });
        dialog.setView(dialogView);
        dialog.setCancelable(true);
        dialog.show();
    }


    private String formatTime(String hour, String minute) {
        if (hour.length() == 1) {
            hour = "0" + hour;
        }
        if (minute.length() == 1) {
            minute = "0" + minute;
        }
        return hour + ":" + minute + ":00";
    }
    private void moveActivity(Context ctx, Class<?> cls) {
        Intent intent = new Intent(ctx, cls);
        startActivity(intent);
    }

    private void viewToast(Context ctx, String message) {
        Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show();
    }



}