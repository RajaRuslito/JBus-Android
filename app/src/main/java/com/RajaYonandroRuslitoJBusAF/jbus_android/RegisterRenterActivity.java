package com.RajaYonandroRuslitoJBusAF.jbus_android;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.RajaYonandroRuslitoJBusAF.jbus_android.model.Account;
import com.RajaYonandroRuslitoJBusAF.jbus_android.model.BaseResponse;
import com.RajaYonandroRuslitoJBusAF.jbus_android.model.Renter;
import com.RajaYonandroRuslitoJBusAF.jbus_android.request.BaseApiService;
import com.RajaYonandroRuslitoJBusAF.jbus_android.request.UtilsApi;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterRenterActivity extends AppCompatActivity {

    private TextView manageNow = null;
    private Button regCompButton = null;
    private BaseApiService mApiService;
    private Context mContext;
    private TextInputEditText compName, address, phoneNumb;
    private int id;

    //@SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_renter);

        manageNow = findViewById(R.id.manage_now);
        regCompButton = findViewById(R.id.registerRenter_button);

        mContext = this;
        mApiService = UtilsApi.getApiService();
// sesuaikan dengan ID yang kalian buat di layout
        compName = (TextInputEditText) findViewById(R.id.companyName);
        address = (TextInputEditText) findViewById(R.id.compAddress);
        phoneNumb = (TextInputEditText) findViewById(R.id.phoneNumb_reg);

        regCompButton.setOnClickListener(view -> {
            handleRegisterRenter();
        });

    }

    private void moveActivity(Context ctx, Class<?> cls) {
        Intent intent = new Intent(ctx, cls);
        startActivity(intent);
    }

    private void viewToast(Context ctx, String message) {
        Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show();
    }

    protected void handleRegisterRenter() {
        String nameS = compName.getText().toString();
        String addressS = address.getText().toString();
        String phoneNumS = phoneNumb.getText().toString();
        Account acc = LoginActivity.loggedAccount;

        if (nameS.isEmpty() || addressS.isEmpty() || phoneNumS.isEmpty()) {
            Toast.makeText(mContext, "Fields Cannot Be Empty!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!nameS.isEmpty() && !addressS.isEmpty() && !phoneNumS.isEmpty()) {


            //Account account = new Account(nameS, emailS, passwordS, 0.0D);
            mApiService.registerRenter(acc.id, nameS, phoneNumS, addressS).enqueue(new Callback<BaseResponse<Renter>>() {
                @Override
                public void onResponse(Call<BaseResponse<Renter>> call, Response<BaseResponse<Renter>> response) {
                    if (!response.isSuccessful()) {
                        Toast.makeText(mContext, "ERROR" + response.code(), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    BaseResponse<Renter> res = response.body();

                    if (res.success) {
                        viewToast(mContext, "TRUE");
                        LoginActivity.loggedAccount.company = res.payload;
                        moveActivity(mContext, AboutMeActivity.class);
                        finish();
                    }
                    Toast.makeText(mContext, res.message, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<BaseResponse<Renter>> call, Throwable t) {
                    Toast.makeText(mContext, "Server ERROR", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}