package com.RajaYonandroRuslitoJBusAF.jbus_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class AboutMeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);
        //findViewById
        TextView usernameTextView = findViewById(R.id.username);
        TextView emailTextView = findViewById(R.id.email);
        TextView balanceTextView = findViewById(R.id.balance);
        usernameTextView.setText("Dancer");
        emailTextView.setText("Dancer@boreal.valley");
        balanceTextView.setText("IDR 1.000.000.00");
    }
}