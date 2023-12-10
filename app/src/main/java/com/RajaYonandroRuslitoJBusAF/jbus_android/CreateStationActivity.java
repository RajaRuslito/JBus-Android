package com.RajaYonandroRuslitoJBusAF.jbus_android;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.RajaYonandroRuslitoJBusAF.jbus_android.model.Account;
import com.RajaYonandroRuslitoJBusAF.jbus_android.model.BaseResponse;
import com.RajaYonandroRuslitoJBusAF.jbus_android.model.City;
import com.RajaYonandroRuslitoJBusAF.jbus_android.model.Renter;
import com.RajaYonandroRuslitoJBusAF.jbus_android.model.Schedule;
import com.RajaYonandroRuslitoJBusAF.jbus_android.model.Station;
import com.RajaYonandroRuslitoJBusAF.jbus_android.request.BaseApiService;
import com.RajaYonandroRuslitoJBusAF.jbus_android.request.UtilsApi;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * The CreateStationActivity class is responsible for handling the creation of a new station.
 * It allows the user to input the station's name, city, and address. The user can then submit the
 * information to create a new station by clicking the "Create Station" button.
 */
public class CreateStationActivity extends AppCompatActivity {

    // Instance variables
    private Button createStat = null;
    private BaseApiService mApiService;
    private Context mContext;
    private TextInputEditText statName, city, addresses;
    private int id;
    private Spinner selectedCity;
    private String cityS;

    /**
     * Called when the activity is starting. This is where most initialization should go:
     * calling setContentView(int) to inflate the activity's UI, using findViewById(int)
     * to programmatically interact with widgets in the UI, and calling
     * populateData() to populate data for spinners or other views.
     *
     * @param /savedInstanceState If the activity is being re-initialized after previously
     *                           being shut down, this Bundle contains the data it most
     *                           recently supplied in onSaveInstanceState(Bundle).
     *                           Note: Otherwise, it is null.
     */
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_station);

        getSupportActionBar().hide();

        createStat = findViewById(R.id.createStation_button);
        mContext = this;
        mApiService = UtilsApi.getApiService();
        statName = (TextInputEditText) findViewById(R.id.stationName);
        addresses = (TextInputEditText) findViewById(R.id.address);

        selectedCity = findViewById(R.id.citySpinner);

        handleCity();

        createStat.setOnClickListener(view -> {
            handleCreateStation();
        });
    }

    /**
     * Populates the city spinner with data and sets an item selection listener.
     */
    protected void handleCity(){
        ArrayAdapter<City> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, City.values());
        adapter.setDropDownViewResource(androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item);
        selectedCity.setAdapter(adapter);
        selectedCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
                //temp = (position);
                if(position == 0){
                    cityS = "jakarta";
                } else if (position == 1){
                    cityS = "depok";
                } else if (position == 2) {
                    cityS = "bandung";
                } else if (position == 3) {
                    cityS = "yogyakarta";
                } else if (position == 4) {
                    cityS = "semarang";
                } else if (position == 5) {
                    cityS = "surabaya";
                } else if (position == 6) {
                    cityS = "bali";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    /**
     * Moves to another activity based on the provided class.
     *
     * @param ctx The context of the current activity.
     * @param cls The class of the activity to move to.
     */
    private void moveActivity(Context ctx, Class<?> cls) {
        Intent intent = new Intent(ctx, cls);
        startActivity(intent);
    }

    /**
     * Displays a toast message with the given message.
     *
     * @param ctx     The context of the current activity.
     * @param message The message to be displayed in the toast.
     */
    private void viewToast(Context ctx, String message) {
        Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Handles the creation of a new station by sending a network request.
     */
    protected void handleCreateStation() {
        String statNameS = statName.getText().toString();
        String addresseS = addresses.getText().toString();

        if (statNameS.isEmpty() || cityS.isEmpty() || addresseS.isEmpty()) {
            Toast.makeText(mContext, "Fields Cannot Be Empty!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!statNameS.isEmpty() && !cityS.isEmpty() && !addresseS.isEmpty()) {
            mApiService.createStation(statNameS, cityS, addresseS).enqueue(new Callback<BaseResponse<Station>>() {
                @Override
                public void onResponse(Call<BaseResponse<Station>> call, Response<BaseResponse<Station>> response) {
                    if (!response.isSuccessful()) {
                        Toast.makeText(mContext, "ERROR" + response.code(), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    BaseResponse<Station> res = response.body();

                    if (res.success) {
                        viewToast(mContext, "TRUE");
                        moveActivity(mContext, AboutMeActivity.class);
                        finish();
                    }
                    Toast.makeText(mContext, res.message, Toast.LENGTH_SHORT).show();
                }
                @Override
                public void onFailure(Call<BaseResponse<Station>> call, Throwable t) {
                    Toast.makeText(mContext, "Server ERROR", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}