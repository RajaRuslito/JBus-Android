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

public class NotRegisteredActivity extends AppCompatActivity {

    private TextView registerNow = null;
    private TextView loginNow = null;
    private BottomNavigationView nav;
    private Context mContext;
    private BaseApiService mApiService;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_not_registered);

        getSupportActionBar().hide();

        registerNow = findViewById(R.id.register_now);

        registerNow.setOnClickListener(e -> {
            moveActivity(this, RegisterActivity.class);
        });

        loginNow = findViewById(R.id.login_now);

        loginNow.setOnClickListener(e -> {
            moveActivity(this, LoginActivity.class);
        });

        nav = findViewById(R.id.bottom_nav);

        mContext = this;
        mApiService = UtilsApi.getApiService();

        nav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            //@SuppressLint("NonConstantResourceId")
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

    private void moveActivity(Context ctx, Class<?> cls) {
        Intent intent = new Intent(ctx, cls);
        startActivity(intent);
    }

    private void viewToast(Context ctx, String message) {
        Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show();
    }
}