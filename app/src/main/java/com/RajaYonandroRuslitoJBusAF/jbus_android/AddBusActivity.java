package com.RajaYonandroRuslitoJBusAF.jbus_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.RajaYonandroRuslitoJBusAF.jbus_android.model.Account;
import com.RajaYonandroRuslitoJBusAF.jbus_android.model.BaseResponse;
import com.RajaYonandroRuslitoJBusAF.jbus_android.model.Bus;
import com.RajaYonandroRuslitoJBusAF.jbus_android.model.BusType;
import com.RajaYonandroRuslitoJBusAF.jbus_android.model.Facility;
import com.RajaYonandroRuslitoJBusAF.jbus_android.model.Station;
import com.RajaYonandroRuslitoJBusAF.jbus_android.request.BaseApiService;
import com.RajaYonandroRuslitoJBusAF.jbus_android.request.UtilsApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddBusActivity extends AppCompatActivity {
    private BusType[] busType = BusType.values();
    private BusType selectedBusType;
    private Spinner busTypeSpinner;
    private Spinner departureSpinner;
    private Spinner arrivalSpinner;
    private List<Station> stationList = new ArrayList<>();
    private Station departureStation;
    private Station arrivalStation;
    private int selectedDeptStationID;
    private int selectedArrStationID;
    private Context mContext;
    private BaseApiService mApiService;
    private EditText addBusName, busCapacity, busPrice;
    private CheckBox acCheckBox, wifiCheckBox, toiletCheckBox, lcdCheckBox;
    private CheckBox coolboxCheckBox, lunchCheckBox, baggageCheckBox, electricCheckBox;
    private List<Facility> selectedFacilities = new ArrayList<>();
    private Button addBusButton = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bus);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        mContext = this;
        mApiService = UtilsApi.getApiService();

        addBusName = findViewById(R.id.busName);
        busCapacity = findViewById(R.id.capacity);
        busPrice = findViewById(R.id.price);
        departureSpinner = findViewById(R.id.departure_dropdown);
        arrivalSpinner = findViewById(R.id.arrival_dropdown);
        addBusButton = findViewById(R.id.addbusButton);

        acCheckBox = findViewById(R.id.checkBoxAC);
        wifiCheckBox = findViewById(R.id.checkBoxWifi);
        toiletCheckBox = findViewById(R.id.checkBoxToilet);
        lcdCheckBox = findViewById(R.id.checkBoxLCD);
        coolboxCheckBox = findViewById(R.id.checkBoxCB);
        lunchCheckBox = findViewById(R.id.checkBoxLunch);
        baggageCheckBox = findViewById(R.id.checkBoxLB);
        electricCheckBox = findViewById(R.id.checkBoxES);

        handleBusType();
        handleStation();

        addBusButton.setOnClickListener(view ->{
            handleFacilites();
            handleCreateBus();
        });
    }

    public void handleFacilites(){
        selectedFacilities.clear();
        if(acCheckBox.isChecked()){
            selectedFacilities.add(Facility.AC);
        }
        if(wifiCheckBox.isChecked()){
            selectedFacilities.add(Facility.WIFI);
        }
        if(toiletCheckBox.isChecked()){
            selectedFacilities.add(Facility.TOILET);
        }
        if(lcdCheckBox.isChecked()){
            selectedFacilities.add(Facility.LCD_TV);
        }
        if(coolboxCheckBox.isChecked()){
            selectedFacilities.add(Facility.COOL_BOX);
        }
        if(lunchCheckBox.isChecked()){
            selectedFacilities.add(Facility.LUNCH);
        }
        if(baggageCheckBox.isChecked()){
            selectedFacilities.add(Facility.LARGE_BAGGAGE);
        }
        if(electricCheckBox.isChecked()){
            selectedFacilities.add(Facility.ELECTRIC_SOCKET);
        }

    }

    public void handleStation(){
        mApiService.getAllStation().enqueue(new Callback<List<Station>>() {
            @Override
            public void onResponse(Call<List<Station>> call, Response<List<Station>> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(mContext, "ERROR" + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                stationList = response.body();
                ArrayAdapter<Station> depArr = new ArrayAdapter(mContext, android.R.layout.simple_list_item_1, stationList);
                depArr.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                departureSpinner.setAdapter(depArr);
                arrivalSpinner.setAdapter(depArr);
                AdapterView.OnItemSelectedListener deptOISL = new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
                        departureStation = stationList.get(position);
                        selectedDeptStationID = departureStation.id;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                };
                departureSpinner.setOnItemSelectedListener(deptOISL);

                AdapterView.OnItemSelectedListener arrOISL = new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        arrivalStation = stationList.get(position);
                        selectedArrStationID = arrivalStation.id;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                };
                arrivalSpinner.setOnItemSelectedListener(arrOISL);
            }

            @Override
            public void onFailure(Call<List<Station>> call, Throwable t) {
                Toast.makeText(mContext, "Server ERROR", Toast.LENGTH_SHORT).show();
            }
        });

    }


    public void handleBusType(){
        busTypeSpinner = this.findViewById(R.id.bus_type_dropdown);
        ArrayAdapter adBus = new ArrayAdapter(this, android.R.layout.simple_list_item_1, busType);
        adBus.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        busTypeSpinner.setAdapter(adBus);

        AdapterView.OnItemSelectedListener busTypeOISL = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
                selectedBusType = busType[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        };
        busTypeSpinner.setOnItemSelectedListener(busTypeOISL);
    }
    private void viewToast(Context ctx, String message){
        Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show();
    }
    public void handleCreateBus(){
        String nameS = addBusName.getText().toString();
        int capacity = Integer.parseInt(busCapacity.getText().toString());
        int price = Integer.parseInt(busPrice.getText().toString());


        mApiService.create(LoginActivity.loggedAccount.id, nameS, capacity, selectedFacilities, selectedBusType, price, selectedDeptStationID, selectedArrStationID ).enqueue(new Callback<BaseResponse<Bus>>() {
            @Override
            public void onResponse(Call<BaseResponse<Bus>> call, Response<BaseResponse<Bus>> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(mContext, "ERROR" + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                BaseResponse<Bus> res = response.body();
                if(res.success){
                    moveActivity(mContext, ManageBusActivity.class);
                    viewToast(mContext, "Success Add Bus");
                    finish();
                }
                Toast.makeText(mContext, res.message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<BaseResponse<Bus>> call, Throwable t) {
                Toast.makeText(mContext, "Server ERROR", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void moveActivity(Context ctx, Class<?> cls) {
        Intent intent = new Intent(ctx, cls);
        startActivity(intent);
    }

}