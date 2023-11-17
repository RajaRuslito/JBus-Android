package com.RajaYonandroRuslitoJBusAF.jbus_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    private TextView registerNow = null;
    //private Button loginButton = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        registerNow = findViewById(R.id.register_now);
        //loginButton = findViewById(R.id.login_button);
    }
    private void moveActivity(Context ctx, Class<?> cls){
        Intent intent = new Intent(ctx, cls);
    }


}