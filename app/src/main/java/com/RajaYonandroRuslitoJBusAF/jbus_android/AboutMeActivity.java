package com.RajaYonandroRuslitoJBusAF.jbus_android;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.RajaYonandroRuslitoJBusAF.jbus_android.model.Account;
import com.RajaYonandroRuslitoJBusAF.jbus_android.model.BaseResponse;
import com.RajaYonandroRuslitoJBusAF.jbus_android.request.BaseApiService;
import com.RajaYonandroRuslitoJBusAF.jbus_android.request.UtilsApi;
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

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);
        //findViewById
        TextView usernameTextView = findViewById(R.id.username);
        TextView emailTextView = findViewById(R.id.email);
        balanceTextView = findViewById(R.id.balance);
        TextView initial = findViewById(R.id.initial);
        topUpEdit = (TextInputEditText) findViewById(R.id.topup_amount);
        topupButton = findViewById(R.id.topup_button);

        mContext = this;
        mApiService = UtilsApi.getApiService();

        //Account acc =
        Account account = LoginActivity.loggedAccount;
        if (LoginActivity.loggedAccount != null) {

            initial.setText(account.name.toUpperCase().substring(0, 1));
            usernameTextView.setText(account.name);
            emailTextView.setText(account.email);
            balanceTextView.setText("IDR " + String.valueOf(account.balance));
        }


        topupButton.setOnClickListener(view -> {
            //Account account = LoginActivity.loggedAccount;
            handleTopUp();

        });


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