package com.RajaYonandroRuslitoJBusAF.jbus_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

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
import com.RajaYonandroRuslitoJBusAF.jbus_android.model.Algorithm;
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

/**
 * Activity for handling payment-related functionality.
 */
public class PaymentActivity extends AppCompatActivity {

    // Instance variables
    private BottomNavigationView nav;
    private ListView listView;
    public static List<Bus> listBus = new ArrayList<>();
    public static List<Payment> listPayment = new ArrayList<>();
    private ListView bookingBusView = null;
    private HorizontalScrollView pageScroll = null;
    private Context mContext;
    private BaseApiService mApiService;

    /**
     * Initializes the payment activity.
     *
     * @param savedInstanceState The saved state of the activity.
     */
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        getSupportActionBar().hide();

        // Initialize UI elements
        nav = findViewById(R.id.bottom_nav);
        mContext = this;
        mApiService = UtilsApi.getApiService();

        // Set up bottom navigation
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
                    }else {
                        Toast.makeText(PaymentActivity.this, "Profile", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), NotRegisteredActivity.class));
                        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_from_left);
                        return true;
                    }
                }
                else if (itemId == R.id.payment) {
                    if(LoginActivity.loggedAccount != null){
                        Toast.makeText(PaymentActivity.this, "Payment", Toast.LENGTH_SHORT).show();
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

        // Handle payment functionality
        handlePayment();
    }

    /**
     * Starts a new activity.
     *
     * @param ctx The context from which the activity is started.
     * @param cls The class of the activity to start.
     */
    private void moveActivity(Context ctx, Class<?> cls) {
        Intent intent = new Intent(ctx, cls);
        startActivity(intent);
    }

    /**
     * Displays a toast message.
     *
     * @param ctx     The context in which the toast message should be displayed.
     * @param message The message to display in the toast.
     */
    private void viewToast(Context ctx, String message) {
        Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Handles payment-related functionality, including retrieving user payments.
     */
    protected void handlePayment() {
        Account acc = LoginActivity.loggedAccount;
        mApiService.getMyPayment(acc.id).enqueue(new Callback<List<Payment>>() {
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
}