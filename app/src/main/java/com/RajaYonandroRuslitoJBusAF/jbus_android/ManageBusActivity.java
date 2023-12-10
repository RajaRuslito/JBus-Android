package com.RajaYonandroRuslitoJBusAF.jbus_android;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.RajaYonandroRuslitoJBusAF.jbus_android.databinding.ActivityMainBinding;
import com.RajaYonandroRuslitoJBusAF.jbus_android.databinding.ActivityManageBusBinding;
import com.RajaYonandroRuslitoJBusAF.jbus_android.model.Account;
import com.RajaYonandroRuslitoJBusAF.jbus_android.model.BaseResponse;
import com.RajaYonandroRuslitoJBusAF.jbus_android.model.Bus;
import com.RajaYonandroRuslitoJBusAF.jbus_android.model.Payment;
import com.RajaYonandroRuslitoJBusAF.jbus_android.model.Renter;
import com.RajaYonandroRuslitoJBusAF.jbus_android.model.Station;
import com.RajaYonandroRuslitoJBusAF.jbus_android.request.BaseApiService;
import com.RajaYonandroRuslitoJBusAF.jbus_android.request.UtilsApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Activity for managing user's buses, displaying a list of owned buses and allowing the user to add new buses.
 */
public class ManageBusActivity extends AppCompatActivity {

    // Instance variables
    public static List<Bus> listBus = new ArrayList<>();
    private ImageView addBus = null;
    private ListView busListView = null;
    private HorizontalScrollView pageScroll = null;
    private Context mContext;
    private BaseApiService mApiService;
    ActivityManageBusBinding binding;

    /**
     * Initializes the activity and sets up the user interface.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     *                           this Bundle contains the data it most recently supplied in
     *                           {@link #onSaveInstanceState(Bundle)}, otherwise null.
     */
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_bus);

        binding = ActivityManageBusBinding.inflate(getLayoutInflater());

        // Hide the action bar
        getSupportActionBar().hide();

        // UI elements
        addBus = findViewById(R.id.addbus);
        mContext = this;
        mApiService = UtilsApi.getApiService();
        busListView = findViewById(R.id.listViewManageBus);
        ImageView backButton = findViewById(R.id.backButton);

        // Back button click listener
        backButton.setOnClickListener(view -> {
            moveActivity(this, AboutMeActivity.class);
        });

        // Load user's buses
        loadMyBus();

        // Add bus button click listener
        addBus.setOnClickListener(view -> {
            moveActivity(this, AddBusActivity.class);
        });

    }

    /**
     * Displays a toast message in the specified context.
     *
     * @param ctx     The context in which the toast should be displayed.
     * @param message The message to be displayed in the toast.
     */
    private void viewToast(Context ctx, String message) {
        Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Loads the list of buses owned by the user.
     */
    protected void loadMyBus() {
        Account acc = LoginActivity.loggedAccount;
        mApiService.getMyBus(acc.id).enqueue(new Callback<List<Bus>>() {
            @Override
            public void onResponse(Call<List<Bus>> call, Response<List<Bus>> response) {
                if (!response.isSuccessful()) return;
                List<Bus> myBusList = response.body();
                listBus = myBusList;
                busListView = findViewById(R.id.listViewManageBus);
                /*listSize = listBus.size();*/
                ManageBusAdapter adapter = new ManageBusAdapter(getApplicationContext(), R.layout.activity_manage_bus, myBusList, ManageBusActivity.this);
                busListView.setAdapter(adapter);
            }
            @Override
            public void onFailure(Call<List<Bus>> call, Throwable t) {
            }
        });
    }

    /**
     * Handles options item selection.
     *
     * @param item The menu item that was selected.
     * @return true if the selection was handled, false otherwise.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.addbus) {
            Intent intent = new Intent(mContext, AddBusActivity.class);
            intent.putExtra("type", "addBus");
            startActivity(intent);
            return true;
        } else return super.onOptionsItemSelected(item);
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


}