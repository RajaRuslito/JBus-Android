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

/**
 * Activity for user registration.
 */
public class RegisterActivity extends AppCompatActivity {

    private TextView loginNow = null;
    private Button registerButton = null;
    private BaseApiService mApiService;
    private Context mContext;
    private TextInputEditText name, email, password;
    public static Account loggedAccount;

    /**
     * Called when the activity is starting. This is where most initialization should go:
     * calling setContentView(int) to inflate the activity's UI, using findViewById(int)
     * to programmatically interact with widgets in the UI, and setting up event listeners.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously
     *                           being shut down, this Bundle contains the data it most
     *                           recently supplied in onSaveInstanceState(Bundle).
     *                           Note: Otherwise, it is null.
     */
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
        });

        mContext = this;
        mApiService = UtilsApi.getApiService();
        // sesuaikan dengan ID yang kalian buat di layout
        name = (TextInputEditText) findViewById(R.id.username_reg);
        email = (TextInputEditText) findViewById(R.id.email_reg);
        password = (TextInputEditText) findViewById(R.id.password_reg);

    }

    /**
     * Moves to another activity.
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
     * Handles the registration process.
     */
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