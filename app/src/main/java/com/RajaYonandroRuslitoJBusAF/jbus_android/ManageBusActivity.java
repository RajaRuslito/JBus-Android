package com.RajaYonandroRuslitoJBusAF.jbus_android;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.RajaYonandroRuslitoJBusAF.jbus_android.databinding.ActivityMainBinding;
import com.RajaYonandroRuslitoJBusAF.jbus_android.databinding.ActivityManageBusBinding;
import com.RajaYonandroRuslitoJBusAF.jbus_android.model.Account;
import com.RajaYonandroRuslitoJBusAF.jbus_android.model.BaseResponse;
import com.RajaYonandroRuslitoJBusAF.jbus_android.model.Bus;
import com.RajaYonandroRuslitoJBusAF.jbus_android.model.Payment;
import com.RajaYonandroRuslitoJBusAF.jbus_android.model.Renter;
import com.RajaYonandroRuslitoJBusAF.jbus_android.model.Station;
import com.RajaYonandroRuslitoJBusAF.jbus_android.request.BaseApiService;
import com.RajaYonandroRuslitoJBusAF.jbus_android.request.UtilsApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManageBusActivity extends AppCompatActivity {

    public static List<Bus> listBus = new ArrayList<>();
    private ImageView addBus = null;
    private ListView busListView = null;
    private HorizontalScrollView pageScroll = null;
    private Context mContext;
    private BaseApiService mApiService;

    ActivityManageBusBinding binding;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_bus);

        binding = ActivityManageBusBinding.inflate(getLayoutInflater());

        getSupportActionBar().hide();

        addBus = findViewById(R.id.addbus);

        mContext = this;
        mApiService = UtilsApi.getApiService();
        busListView = findViewById(R.id.listViewManageBus);

        /*handleMyBus();*/
        loadMyBus();

        addBus.setOnClickListener(view -> {
            moveActivity(this, AddBusActivity.class);
        });

    }
    private void viewToast(Context ctx, String message) {
        Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show();
    }

    protected void loadMyBus() {
        Account acc = LoginActivity.loggedAccount;
        mApiService.getMyBus(acc.id).enqueue(new Callback<List<Bus>>() {
            @Override
            public void onResponse(Call<List<Bus>> call, Response<List<Bus>> response) {
                if (!response.isSuccessful()) return;

                List<Bus> myBusList = response.body();
                listBus = myBusList;
                busListView = findViewById(R.id.listViewManageBus);
                /*listSize = listBus.size();*/
                ManageBusAdapter adapter = new ManageBusAdapter(getApplicationContext(), R.layout.activity_manage_bus, myBusList, ManageBusActivity.this);
                busListView.setAdapter(adapter);


            }

            @Override
            public void onFailure(Call<List<Bus>> call, Throwable t) {

            }
        });
    }
    /*public void handleMyBus(){
        Account acc = LoginActivity.loggedAccount;
        Payment bus = AddSchedule.loggedBus;

        mApiService.getBuses(acc.id).enqueue(new Callback<BaseResponse<List<Bus>>>() {
            @Override
            public void onResponse(Call<BaseResponse<List<Bus>>> call, Response<BaseResponse<List<Bus>>> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(mContext, "ERROR" + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                BaseResponse<List<Bus>> resp = response.body();
                if (response.isSuccessful()) {
                    listBus.clear();
                    for(Bus b : resp.payload) {
                        listBus.add(b);
                    }
                    busListView = findViewById(R.id.listViewManageBus);
                    listSize = listBus.size();
                    busListView.setAdapter(adapter);

                }


            }

            @Override
            public void onFailure(Call<BaseResponse<List<Bus>>> call, Throwable t) {
                Toast.makeText(mContext, "Server ERROR", Toast.LENGTH_SHORT).show();
            }

        });

    }*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.addbus) {
            Intent intent = new Intent(mContext, AddBusActivity.class);
            intent.putExtra("type", "addBus");
            startActivity(intent);
            return true;
        } else return super.onOptionsItemSelected(item);
    }

    private void moveActivity(Context ctx, Class<?> cls) {
        Intent intent = new Intent(ctx, cls);
        startActivity(intent);
    }


}