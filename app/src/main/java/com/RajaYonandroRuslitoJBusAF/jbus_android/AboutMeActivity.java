package com.RajaYonandroRuslitoJBusAF.jbus_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.RajaYonandroRuslitoJBusAF.jbus_android.model.Account;
import com.RajaYonandroRuslitoJBusAF.jbus_android.model.BaseResponse;
import com.RajaYonandroRuslitoJBusAF.jbus_android.model.Renter;
import com.RajaYonandroRuslitoJBusAF.jbus_android.request.BaseApiService;
import com.RajaYonandroRuslitoJBusAF.jbus_android.request.UtilsApi;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AboutMeActivity extends AppCompatActivity {
    private TextView balanceTextView;
    private Button topupButton = null;
    private TextInputEditText topUpEdit;
    private Context mContext;
    private BaseApiService mApiService;
    private int id;
    private TextView registerCompany = null;
    private BottomNavigationView nav;
    private Button logOff = null;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);

        getSupportActionBar().hide();
        //findViewById
        TextView usernameTextView = findViewById(R.id.username);
        TextView emailTextView = findViewById(R.id.email);
        balanceTextView = findViewById(R.id.balance);
        TextView initial = findViewById(R.id.initial);
        topUpEdit = (TextInputEditText) findViewById(R.id.topup_amount);
        topupButton = findViewById(R.id.topup_button);
        registerCompany = findViewById(R.id.regcomp_now);


        mContext = this;
        mApiService = UtilsApi.getApiService();

        //Account acc =
        Account account = LoginActivity.loggedAccount;
        if (LoginActivity.loggedAccount != null) {


            initial.setText(account.name.toUpperCase().substring(0, 1));
            usernameTextView.setText(account.name);
            emailTextView.setText(account.email);
            balanceTextView.setText("IDR " + String.valueOf(account.balance));



            if (LoginActivity.loggedAccount.company != null) {
                // Already a renter - Show renter-related data and a button to manage the bus
                TextView textView = findViewById(R.id.statusRenter);
                textView.setText("You Already Have a Company");
                TextView registerRenter = findViewById(R.id.regcomp_now);
                registerRenter.setVisibility(View.GONE);
                TextView phoneNum = findViewById(R.id.phoneNum);
                TextView comName = findViewById(R.id.compName);
                TextView comAddress = findViewById(R.id.comAddress);
                comName.setText(account.company.companyName);
                phoneNum.setText(account.company.phoneNumber);
                comAddress.setText(account.company.address);
                // Update other UI elements with renter's information like company name, address, phone number

                // Implement a listener for a button or component to manage the bus

                Button manageBusButton = findViewById(R.id.manageNow);
                manageBusButton.setOnClickListener(v -> {
                    moveActivity(mContext, ManageBusActivity.class);
                });
            } else {
                // Not a renter - Show a message prompting to register as a renter
                TextView textView = findViewById(R.id.statusRenter);
                textView.setText("Want to Have Your Own Company?");

                TextView phoneNum = findViewById(R.id.phoneNum);
                TextView comName = findViewById(R.id.compName);
                TextView comAddress = findViewById(R.id.comAddress);
                // Implement a listener for a component to navigate to the registration page
                Button manageBusButton = findViewById(R.id.manageNow);
                manageBusButton.setVisibility(View.GONE);
                phoneNum.setVisibility(View.GONE);
                comName.setVisibility(View.GONE);
                comAddress.setVisibility(View.GONE);

                registerCompany.setOnClickListener(view -> {
                    moveActivity(this, RegisterRenterActivity.class);
                });
            }



        }

        nav = findViewById(R.id.bottom_nav);

        mContext = this;
        mApiService = UtilsApi.getApiService();

        nav.setSelectedItemId(R.id.profile);

        nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            //@SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int itemId = item.getItemId();
                if (itemId == R.id.profile) {
                    Toast.makeText(AboutMeActivity.this, "Profile", Toast.LENGTH_SHORT).show();
                    /*moveActivity(mContext, AboutMeActivity.class);*/
                    return true;
                }
                else if (itemId == R.id.payment) {
                    if(LoginActivity.loggedAccount != null){
                        Toast.makeText(AboutMeActivity.this, "Payment", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), PaymentActivity.class));
                        overridePendingTransition(R.anim.enter_from_left, R.anim.exit_from_right);
                        return true;
                        /*Toast.makeText(MainActivity.this, "Payment", Toast.LENGTH_SHORT).show();
                        moveActivity(mContext, PaymentActivity.class);*/
                    }else {
                        Toast.makeText(AboutMeActivity.this, "Payment", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), NotRegisteredActivity.class));
                        overridePendingTransition(R.anim.enter_from_left, R.anim.exit_from_right);
                        return true;
                        /*Toast.makeText(MainActivity.this, "Payment", Toast.LENGTH_SHORT).show();
                        moveActivity(mContext, NotRegisteredActivity.class);*/
                    }
                }
                else if (itemId == R.id.home) {
                    Toast.makeText(AboutMeActivity.this, "Home", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    overridePendingTransition(R.anim.enter_from_left, R.anim.exit_from_right);
                    return true;
                }
                return true;
            }
        });
        topupButton.setOnClickListener(view -> {
            //Account account = LoginActivity.loggedAccount;
            handleTopUp();

        });


    }

    private void moveActivity(Context ctx, Class<?> cls) {
        Intent intent = new Intent(ctx, cls);
        startActivity(intent);
    }

    private void viewToast(Context ctx, String message) {
        Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show();
    }

    protected void handleTopUp() {
        double topUpAmount = Double.parseDouble(topUpEdit.getText().toString());
        Account acc = LoginActivity.loggedAccount;
        //id = acc.id;
        if (topUpAmount == 0.0d) {
            Toast.makeText(mContext, "Input the amount!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!(topUpAmount == 0.0d)) {
            mApiService.topUp(acc.id, topUpAmount).enqueue(new Callback<BaseResponse<Double>>() {
                @Override
                public void onResponse(Call<BaseResponse<Double>> call, Response<BaseResponse<Double>> response) {
                    if (!response.isSuccessful()) {
                        Toast.makeText(mContext, "ERROR" + response.code(), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    BaseResponse<Double> res = response.body();

                    if (res.success) {
                        viewToast(mContext, "Top Up Success");
                        acc.balance += topUpAmount;
                        finish();
                    }


                    Toast.makeText(mContext, res.message, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<BaseResponse<Double>> call, Throwable t) {
                    Toast.makeText(mContext, "Server ERROR", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}