package com.RajaYonandroRuslitoJBusAF.jbus_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.RajaYonandroRuslitoJBusAF.jbus_android.model.Account;
import com.RajaYonandroRuslitoJBusAF.jbus_android.model.BaseResponse;
import com.RajaYonandroRuslitoJBusAF.jbus_android.model.Bus;
import com.RajaYonandroRuslitoJBusAF.jbus_android.model.Payment;
import com.RajaYonandroRuslitoJBusAF.jbus_android.request.BaseApiService;
import com.RajaYonandroRuslitoJBusAF.jbus_android.request.UtilsApi;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentActivity extends AppCompatActivity {

    private BottomNavigationView nav;
    private ListView listView;
    public static List<Bus> listBus = new ArrayList<>();
    public static List<Payment> listPayment = new ArrayList<>();
    private ListView bookingBusView = null;
    private HorizontalScrollView pageScroll = null;
    private Context mContext;
    private BaseApiService mApiService;
    private ImageView acceptPay;
    private ImageView cancelPay;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        getSupportActionBar().hide();

        nav = findViewById(R.id.bottom_nav);

        mContext = this;
        mApiService = UtilsApi.getApiService();
        /*acceptPay = (ImageView) findViewById(R.id.acceptPayment);
        cancelPay = (ImageView) findViewById(R.id.cancelPayment);*/

        nav.setSelectedItemId(R.id.payment);

        nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            //@SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int itemId = item.getItemId();
                if (itemId == R.id.profile) {
                    if(LoginActivity.loggedAccount != null){
                        Toast.makeText(PaymentActivity.this, "Profile", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), AboutMeActivity.class));
                        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_from_left);
                        return true;
                        /*Toast.makeText(MainActivity.this, "Profile", Toast.LENGTH_SHORT).show();
                        moveActivity(mContext, AboutMeActivity.class);*/
                    }else {
                        Toast.makeText(PaymentActivity.this, "Profile", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), NotRegisteredActivity.class));
                        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_from_left);
                        return true;
                        /*Toast.makeText(MainActivity.this, "Profile", Toast.LENGTH_SHORT).show();
                        moveActivity(mContext, NotRegisteredActivity.class);*/
                    }
                }
                else if (itemId == R.id.payment) {
                    if(LoginActivity.loggedAccount != null){
                        Toast.makeText(PaymentActivity.this, "Payment", Toast.LENGTH_SHORT).show();
                        //moveActivity(mContext, PaymentActivity.class);
                        return true;
                    }else {
                        Toast.makeText(PaymentActivity.this, "Payment", Toast.LENGTH_SHORT).show();
                        moveActivity(mContext, NotRegisteredActivity.class);
                    }
                }
                else if (itemId == R.id.home) {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    overridePendingTransition(R.anim.enter_from_left, R.anim.exit_from_right);
                    return true;
                }
                return true;
            }
        });

        /*handleBusBooking();*/
        handlePayment();
        //loadMyBus();
    }
    private void moveActivity(Context ctx, Class<?> cls) {
        Intent intent = new Intent(ctx, cls);
        startActivity(intent);
    }

    private void viewToast(Context ctx, String message) {
        Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show();
    }
    /*protected void loadMyBus() {
        int temp = 0;
        mApiService.getMyBus(*//*LoginActivity.loggedAccount.id*//* 0).enqueue(new Callback<List<Bus>>() {
            @Override
            public void onResponse(Call<List<Bus>> call, Response<List<Bus>> response) {
                if (!response.isSuccessful()) return;

                List<Bus> myBusList = response.body();
                ListView busListView = (ListView) findViewById(R.id.listView);
                listBus = myBusList;
                BusArrayAdapter adapter = new BusArrayAdapter(getApplicationContext(), R.layout.bookedd_seat_listview, myBusList, PaymentActivity.this);
                busListView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Bus>> call, Throwable t) {

            }
        });
    }*/

    protected void handlePayment() {
        mApiService.getMyPayment().enqueue(new Callback<List<Payment>>() {
            @Override
            public void onResponse(Call<List<Payment>> call, Response<List<Payment>> response) {
                if (!response.isSuccessful()) return;

                List<Payment> paymentList = response.body();
                ListView bookedListView = (ListView) findViewById(R.id.listView);
                listPayment = paymentList;
                PaymentArrayAdapter adapter = new PaymentArrayAdapter(getApplicationContext(), R.layout.bookedd_seat_listview, listPayment, PaymentActivity.this);
                bookedListView.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<List<Payment>> call, Throwable t) {

            }
        });
    }




    /*public void handleBusBooking(){
        Account acc = LoginActivity.loggedAccount;
        Payment bus = AddSchedule.loggedBus;
        mApiService.getBuses().enqueue(new Callback<BaseResponse<List<Bus>>>() {
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
                    *//*newBusListView = findViewById(R.id.listBooking);
                    listSize = listBus.size();
                    MakeBookingAdapter adapter = new MakeBookingAdapter(getApplicationContext(), R.layout.listbusbooking, listBus);
                    newBusListView.setAdapter(adapter);*//*

                    bookingBusView = findViewById(R.id.listBooking);
                    MakeBookingAdapter adapter = new MakeBookingAdapter(getApplicationContext(), R.layout.activity_payment, listBus);
                    bookingBusView.setAdapter(adapter);

                    bookingBusView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Toast.makeText(mContext, "Booking", Toast.LENGTH_SHORT).show();
                            moveActivity(mContext, AddSchedule.class);
                        }
                    });

                    *//*
                    newBusListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Toast.makeText(mContext, "Schedule", Toast.LENGTH_SHORT).show();
                            moveActivity(mContext, AddSchedule.class);
                        }
                    });
*//*

                }


            }

            @Override
            public void onFailure(Call<BaseResponse<List<Bus>>> call, Throwable t) {
                Toast.makeText(mContext, "Server ERROR", Toast.LENGTH_SHORT).show();
            }

        });

    }*/
}