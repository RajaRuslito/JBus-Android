package com.RajaYonandroRuslitoJBusAF.jbus_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private TextView loginNow = null;
    private Button registerButton = null;
    private BaseApiService mApiService;
    private Context mContext;
    private TextInputEditText name, email, password;
    public static Account loggedAccount;

    //private Button registerButton = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().hide();

        loginNow = findViewById(R.id.register_now);
        registerButton = findViewById(R.id.register_button);

        loginNow.setOnClickListener(e -> {
            moveActivity(this, LoginActivity.class);
        });
        registerButton.setOnClickListener(e -> {
            handleRegister();
            moveActivity(this, LoginActivity.class);
        });

        mContext = this;
        mApiService = UtilsApi.getApiService();
// sesuaikan dengan ID yang kalian buat di layout
        name = (TextInputEditText) findViewById(R.id.username_reg);
        email = (TextInputEditText) findViewById(R.id.email_reg);
        password = (TextInputEditText) findViewById(R.id.password_reg);
        //registerButton = findViewById(R.id.button_register);

    }

    private void moveActivity(Context ctx, Class<?> cls) {
        Intent intent = new Intent(ctx, cls);
        startActivity(intent);
    }

    private void viewToast(Context ctx, String message) {
        Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show();
    }

    protected void handleRegister() {
        String nameS = name.getText().toString();
        String emailS = email.getText().toString();
        String passwordS = password.getText().toString();

        if (nameS.isEmpty() || emailS.isEmpty() || passwordS.isEmpty()) {
            Toast.makeText(mContext, "Fields Cannot Be Empty!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!nameS.isEmpty() && !emailS.isEmpty() && !passwordS.isEmpty()) {
            viewToast(this, "TRUE");

            //Account account = new Account(nameS, emailS, passwordS, 0.0D);
            mApiService.register(nameS, emailS, passwordS).enqueue(new Callback<BaseResponse<Account>>() {
                @Override
                public void onResponse(Call<BaseResponse<Account>> call, Response<BaseResponse<Account>> response) {
                    if (!response.isSuccessful()) {
                        Toast.makeText(mContext, "ERROR" + response.code(), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    BaseResponse<Account> res = response.body();

                    if (res.success) {
                        moveActivity(mContext, LoginActivity.class);
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