package com.RajaYonandroRuslitoJBusAF.jbus_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.RajaYonandroRuslitoJBusAF.jbus_android.model.Account;
import com.RajaYonandroRuslitoJBusAF.jbus_android.model.Algorithm;
import com.RajaYonandroRuslitoJBusAF.jbus_android.model.BaseResponse;
import com.RajaYonandroRuslitoJBusAF.jbus_android.model.Bus;
import com.RajaYonandroRuslitoJBusAF.jbus_android.model.Payment;
import com.RajaYonandroRuslitoJBusAF.jbus_android.request.BaseApiService;
import com.RajaYonandroRuslitoJBusAF.jbus_android.request.UtilsApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Activity for handling the payment status, including accepting and canceling bookings.
 */
public class PaymentStatusActivity extends AppCompatActivity {

    // Instance variables
    private Context mContext;
    private BaseApiService mApiService;
    private int busId;
    private int buyerId;
    private int id;
    private Button acceptBooking, cancelBooking;
    private TextView stat;
    private Intent intent;

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
        setContentView(R.layout.activity_payment_status);

        getSupportActionBar().hide();

        mContext = this;
        mApiService = UtilsApi.getApiService();

        intent = this.getIntent();
        Bundle bundle = getIntent().getExtras();

        if(intent != null){
            TextView busName = this.findViewById(R.id.busNameFinal);
            TextView bookedSeat = this.findViewById(R.id.bookedSeatFinal);
            TextView bookedSched = this.findViewById(R.id.bookedSchedFinal);
            TextView buyerIdTextView = this.findViewById(R.id.buyerId);
            acceptBooking = this.findViewById(R.id.acceptBooking);
            cancelBooking = this.findViewById(R.id.cancelBooking);
            busId = this.getIntent().getIntExtra("busId", -1);
            buyerId = this.getIntent().getIntExtra("buyerId", -1);
            id = this.getIntent().getIntExtra("id", -1);

            Bus newbus = Algorithm.<Bus>find(MainActivity.listBusMain, bus -> {
                return bus.id == busId;
            });

            busName.setText(newbus.name);

            Payment payment = Algorithm.<Payment>find(PaymentActivity.listPayment, pay -> {
                return pay.id == buyerId;
            });

            buyerIdTextView.setText(String.valueOf(LoginActivity.loggedAccount.id));
            bookedSeat.setText(intent.getStringExtra("busSeats"));
            bookedSched.setText(intent.getStringExtra("departureDate"));

            switch (intent.getStringExtra("status")){
                case "SUCCESS":

                case "FAILED":
                    acceptBooking.setVisibility(View.GONE);
                    cancelBooking.setVisibility(View.GONE);
                    stat = this.findViewById(R.id.stat);
                    stat.setText("Status Already Confirmed");
                    break;

                default:
//                    stat.setVisibility(View.GONE);

                    break;
            }

            acceptBooking.setOnClickListener(view -> {
                handleAccept();
            });

            cancelBooking.setOnClickListener(view -> {
                handleCancel();
            });

        }
    }

    /**
     * Handles the acceptance of a payment.
     */
    protected void handleAccept() {
        Account acc = LoginActivity.loggedAccount;
        Payment pay = PaymentActivity.listPayment.get(buyerId);
        Intent intent = this.getIntent();
        Bundle bundle = getIntent().getExtras();

        if(intent != null) {
            mApiService.accept(id, acc.id, busId).enqueue(new Callback<BaseResponse<Payment>>() {
                @Override
                public void onResponse(Call<BaseResponse<Payment>> call, Response<BaseResponse<Payment>> response) {
                    if (!response.isSuccessful()) {
                        Toast.makeText(mContext, "ERROR" + response.code(), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    BaseResponse<Payment> res = response.body();

                    if (res.success) {
                        viewToast(mContext, "Validating Your Ticket");
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

    /**
     * Handles the cancellation of a payment.
     */
    protected void handleCancel() {
        //id = acc.id;
        Account acc = LoginActivity.loggedAccount;
        Intent intent = this.getIntent();
        Bundle bundle = getIntent().getExtras();
        /*Payment payment = Algorithm.<Payment>find(PaymentActivity.listPayment, pay -> {
            return pay.buyerId == buyerId;
        });*/
        Payment pay = PaymentActivity.listPayment.get(buyerId);

        if(intent != null) {
            //buyerId = this.getIntent().getIntExtra("buyerId", -1);

            mApiService.cancel(id).enqueue(new Callback<BaseResponse<Payment>>() {
                @Override
                public void onResponse(Call<BaseResponse<Payment>> call, Response<BaseResponse<Payment>> response) {
                    if (!response.isSuccessful()) {
                        Toast.makeText(mContext, "ERROR" + response.code(), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    BaseResponse<Payment> res = response.body();

                    if (res.success) {
                        viewToast(mContext, "Cancelling Your Ticket");
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