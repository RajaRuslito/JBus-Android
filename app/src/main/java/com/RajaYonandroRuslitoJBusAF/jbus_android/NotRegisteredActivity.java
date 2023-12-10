package com.RajaYonandroRuslitoJBusAF.jbus_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.RajaYonandroRuslitoJBusAF.jbus_android.request.BaseApiService;
import com.RajaYonandroRuslitoJBusAF.jbus_android.request.UtilsApi;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

/**
 * Activity for handling users who are not registered.
 */
public class NotRegisteredActivity extends AppCompatActivity {

    // Instance variables
    private TextView registerNow = null;
    private TextView loginNow = null;
    private BottomNavigationView nav;
    private Context mContext;
    private BaseApiService mApiService;

    /**
     * Initializes the activity.
     *
     * @param savedInstanceState The saved state of the activity.
     */
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_not_registered);

        getSupportActionBar().hide();

        mContext = this;
        mApiService = UtilsApi.getApiService();

        // Set click listeners for register and login buttons
        registerNow = findViewById(R.id.register_now);
        registerNow.setOnClickListener(e -> {
            moveActivity(this, RegisterActivity.class);
        });
        loginNow = findViewById(R.id.login_now);
        loginNow.setOnClickListener(e -> {
            moveActivity(this, LoginActivity.class);
        });

        // Set up bottom navigation
        nav = findViewById(R.id.bottom_nav);

        nav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int itemId = item.getItemId();
                if (itemId == R.id.home) {
                    Toast.makeText(NotRegisteredActivity.this, "Payment", Toast.LENGTH_SHORT).show();
                    moveActivity(mContext, MainActivity.class);
                }
                else if (itemId == R.id.payment) {
                    Toast.makeText(NotRegisteredActivity.this, "Payment", Toast.LENGTH_SHORT).show();
                }
                else if (itemId == R.id.profile) {
                    Toast.makeText(NotRegisteredActivity.this, "Profile", Toast.LENGTH_SHORT).show();
                    moveActivity(mContext, NotRegisteredActivity.class);
                }
                return true;
            }
        });
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
}