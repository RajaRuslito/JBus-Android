package com.RajaYonandroRuslitoJBusAF.jbus_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.RajaYonandroRuslitoJBusAF.jbus_android.model.Account;
import com.RajaYonandroRuslitoJBusAF.jbus_android.model.BaseResponse;
import com.RajaYonandroRuslitoJBusAF.jbus_android.model.Bus;
import com.RajaYonandroRuslitoJBusAF.jbus_android.model.Payment;
import com.RajaYonandroRuslitoJBusAF.jbus_android.request.BaseApiService;
import com.RajaYonandroRuslitoJBusAF.jbus_android.request.UtilsApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentStatusActivity extends AppCompatActivity {

    private Context mContext;
    private BaseApiService mApiService;
    private int busId;
    private int buyerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_status);

        getSupportActionBar().hide();

        mContext = this;
        mApiService = UtilsApi.getApiService();

        Intent intent = this.getIntent();
        Bundle bundle = getIntent().getExtras();

        if(intent != null){
            TextView busName = this.findViewById(R.id.busNameFinal);
            TextView bookedSeat = this.findViewById(R.id.bookedSeatFinal);
            TextView bookedSched = this.findViewById(R.id.bookedSchedFinal);

            busId = this.getIntent().getIntExtra("busId", -1);
            buyerId = this.getIntent().getIntExtra("buyerId", -1);

            Bus newbus = MainActivity.listBusMain.get(busId);

            busName.setText(/*String.valueOf(busId)*/newbus.name);

            Payment payment = PaymentActivity.listPayment.get(buyerId);

            bookedSeat.setText(intent.getStringExtra("busSeats"));
            bookedSched.setText(intent.getStringExtra("departureDate"));

            Button acceptBooking = this.findViewById(R.id.acceptBooking);
            acceptBooking.setOnClickListener(view -> {
                handleAccept();
            });
            Button cancelBooking = this.findViewById(R.id.cancelBooking);
            cancelBooking.setOnClickListener(view -> {
                handleCancel();
            });

        }
    }

    protected void handleAccept() {
        //id = acc.id;
        Account acc = LoginActivity.loggedAccount;
        Double bus = AddBusActivity.harga;

        Intent intent = this.getIntent();
        Bundle bundle = getIntent().getExtras();

        if(intent != null) {
            buyerId = this.getIntent().getIntExtra("buyerId", -1);

            mApiService.accept(buyerId).enqueue(new Callback<BaseResponse<Payment>>() {
                @Override
                public void onResponse(Call<BaseResponse<Payment>> call, Response<BaseResponse<Payment>> response) {
                    if (!response.isSuccessful()) {
                        Toast.makeText(mContext, "ERROR" + response.code(), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    BaseResponse<Payment> res = response.body();

                    if (res.success) {
                        viewToast(mContext, "Validating Your Ticket");
                        acc.balance = acc.balance - bus/*bus.price.price*/;
                        finish();
                    }


                    Toast.makeText(mContext, res.message, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<BaseResponse<Payment>> call, Throwable t) {
                    Toast.makeText(mContext, "Server ERROR", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    protected void handleCancel() {
        //id = acc.id;
        Account acc = LoginActivity.loggedAccount;
        Bus bus = (Bus) ManageBusActivity.listBus;
        Intent intent = this.getIntent();
        Bundle bundle = getIntent().getExtras();

        if(intent != null) {
            buyerId = this.getIntent().getIntExtra("buyerId", -1);

            mApiService.cancel(buyerId).enqueue(new Callback<BaseResponse<Payment>>() {
                @Override
                public void onResponse(Call<BaseResponse<Payment>> call, Response<BaseResponse<Payment>> response) {
                    if (!response.isSuccessful()) {
                        Toast.makeText(mContext, "ERROR" + response.code(), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    BaseResponse<Payment> res = response.body();

                    if (res.success) {
                        viewToast(mContext, "Validating Your Ticket");
                        //acc.balance = acc.balance - bus.price.price;
                        finish();
                    }


                    Toast.makeText(mContext, res.message, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<BaseResponse<Payment>> call, Throwable t) {
                    Toast.makeText(mContext, "Server ERROR", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    private void moveActivity(Context ctx, Class<?> cls) {
        Intent intent = new Intent(ctx, cls);
        startActivity(intent);
    }

    private void viewToast(Context ctx, String message) {
        Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show();
    }
}