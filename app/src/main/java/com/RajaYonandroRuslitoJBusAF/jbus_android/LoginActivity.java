package com.RajaYonandroRuslitoJBusAF.jbus_android;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import com.RajaYonandroRuslitoJBusAF.jbus_android.model.Account;
import com.RajaYonandroRuslitoJBusAF.jbus_android.model.BaseResponse;
import com.RajaYonandroRuslitoJBusAF.jbus_android.request.BaseApiService;
import com.RajaYonandroRuslitoJBusAF.jbus_android.request.UtilsApi;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private TextView registerNow = null;
    private Button loginButton = null;
    private Context mContext;
    private BaseApiService mApiService;
    private TextInputEditText emailEdit, passwordEdit;
    public static Account loggedAccount;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();

        registerNow = findViewById(R.id.register_now);
        loginButton = findViewById(R.id.login_button);

        registerNow.setOnClickListener(e -> {
            moveActivity(this, RegisterActivity.class);
        });


        mContext = this;
        mApiService = UtilsApi.getApiService();
        emailEdit = (TextInputEditText) findViewById(R.id.email_login);
        passwordEdit = (TextInputEditText) findViewById(R.id.password_login);

        loginButton.setOnClickListener(e -> {
            handleLogin();
        });
    }

    private void moveActivity(Context ctx, Class<?> cls) {
        Intent intent = new Intent(ctx, cls);
        startActivity(intent);
    }

    private void viewToast(Context ctx, String message) {
        Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show();
    }

    protected void handleLogin() {
        String emailS = emailEdit.getText().toString();
        String passwordS = passwordEdit.getText().toString();

        if (emailS.isEmpty() || passwordS.isEmpty()) {
            Toast.makeText(mContext, "Fields Cannot Be Empty!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!emailS.isEmpty() && !passwordS.isEmpty()) {

            mApiService.login(emailS, passwordS).enqueue(new Callback<BaseResponse<Account>>() {
                @Override
                public void onResponse(Call<BaseResponse<Account>> call, Response<BaseResponse<Account>> response) {
                    if (!response.isSuccessful()) {
                        Toast.makeText(mContext, "ERROR" + response.code(), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    BaseResponse<Account> res = response.body();

                    if (res.success) {
                        loggedAccount = res.payload;
                        moveActivity(mContext, MainActivity.class);
                        viewToast(mContext, "Log in success");
                        finish();
                    }


                    Toast.makeText(mContext, res.message, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<BaseResponse<Account>> call, Throwable t) {
                    Toast.makeText(mContext, "Server ERROR", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}