package com.RajaYonandroRuslitoJBusAF.jbus_android;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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

/**
 * The LoginActivity class is responsible for handling user login functionality.
 * It allows users to enter their email and password to log in. If the login is successful,
 * the user is redirected to the MainActivity. Users can also navigate to the registration
 * page by clicking the "Register Now" link.
 */
public class LoginActivity extends AppCompatActivity {

    // Instance variables
    private TextView registerNow = null;
    private Button loginButton = null;
    private Context mContext;
    private BaseApiService mApiService;
    private CheckBox rememberMeCheckBox;
    private SharedPreferences mPrefs;
    private static final String PREFS_NAME = "PrefsFile";
    private TextInputEditText emailEdit, passwordEdit;
    public static Account loggedAccount;

    /**
     * Called when the activity is starting. This is where most initialization should go:
     * calling setContentView(int) to inflate the activity's UI, using findViewById(int)
     * to programmatically interact with widgets in the UI, and setting click listeners for
     * buttons or links.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously
     *                           being shut down, this Bundle contains the data it most
     *                           recently supplied in onSaveInstanceState(Bundle).
     *                           Note: Otherwise, it is null.
     */
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();

        registerNow = findViewById(R.id.register_now);
        loginButton = findViewById(R.id.login_button);
        //rememberMeCheckBox = findViewById(R.id.checkThis);

        registerNow.setOnClickListener(e -> {
            moveActivity(this, RegisterActivity.class);
        });

        mPrefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        mContext = this;
        mApiService = UtilsApi.getApiService();
        emailEdit = (TextInputEditText) findViewById(R.id.email_login);
        passwordEdit = (TextInputEditText) findViewById(R.id.password_login);

        // Set click listener for the login button
        loginButton.setOnClickListener(e -> {
            handleLogin();
        });

    }

    /**
     * Retrieves and populates user preferences data from the shared preferences.
     * If preferences are found, it sets the corresponding values in the relevant UI elements.
     * The preferences include the user's email, password, and the state of the "Remember Me" checkbox.
     * Unused because some problems arises when Log in
     */
    private void getPreferencesData() {
        SharedPreferences sp = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        if (sp.contains("pref_name")) {
            String u = sp.getString("pref_name", "not found");
            emailEdit.setText(u.toString());
        }
        if (sp.contains("pref_pass")) {
            String u = sp.getString("pref_pass", "not found");
            passwordEdit.setText(u.toString());
        }
        if (sp.contains("pref_check")) {
            Boolean u = sp.getBoolean("pref_check", false);
            rememberMeCheckBox.setChecked(u);
        }
    }

    /**
     * Moves to another activity based on the provided class.
     *
     * @param ctx The context of the current activity.
     * @param cls The class of the activity to move to.
     */
    private void moveActivity(Context ctx, Class<?> cls) {
        Intent intent = new Intent(ctx, cls);
        startActivity(intent);
    }

    /**
     * Displays a toast message with the given message.
     *
     * @param ctx     The context of the current activity.
     * @param message The message to be displayed in the toast.
     */
    private void viewToast(Context ctx, String message) {
        Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Handles the login process by sending a network request.
     */
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