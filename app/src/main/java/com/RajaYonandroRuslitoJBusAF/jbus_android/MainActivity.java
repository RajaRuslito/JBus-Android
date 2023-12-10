package com.RajaYonandroRuslitoJBusAF.jbus_android;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import com.RajaYonandroRuslitoJBusAF.jbus_android.databinding.ActivityMainBinding;
import com.RajaYonandroRuslitoJBusAF.jbus_android.model.Account;
import com.RajaYonandroRuslitoJBusAF.jbus_android.model.BaseResponse;
import com.RajaYonandroRuslitoJBusAF.jbus_android.model.Bus;
import com.RajaYonandroRuslitoJBusAF.jbus_android.request.BaseApiService;
import com.RajaYonandroRuslitoJBusAF.jbus_android.request.UtilsApi;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * The MainActivity class serves as the main screen of the JBus Android application.
 * It displays a list of buses and allows users to navigate to different sections of the app
 * through a bottom navigation bar. Users can search for buses, view their profile, and make payments.
 */
public class MainActivity extends AppCompatActivity {

    // Instace variables
    private Button[] btns;
    private int currentPage = 0;
    private int pageSize = 5;
    private int listSize;
    private int noOfPages;
    public static List<Bus> listBusMain = new ArrayList<>();
    private List<Bus> searchList = new ArrayList<>();
    private Button prevButton = null;
    private Button nextButton = null;
    private ListView busListView = null;
    private HorizontalScrollView pageScroll = null;
    private BottomNavigationView nav;
    private Context mContext;
    private BaseApiService mApiService;
    ActivityMainBinding binding;
    private SearchView searchView;
    private ListView listbus;

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
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();


        /*prevButton = findViewById(R.id.prev_page);
        nextButton = findViewById(R.id.next_page);
        pageScroll = findViewById(R.id.page_number_scroll);*/

        /*paginationFooter();
        goToPage(currentPage);



        prevButton.setOnClickListener(v -> {
            currentPage = currentPage != 0 ? currentPage - 1 : 0;
            goToPage(currentPage);
        });

        nextButton.setOnClickListener(v -> {
            currentPage = currentPage != noOfPages - 1 ? currentPage + 1 : currentPage;
            goToPage(currentPage);
        });*/

        searchView = (SearchView) findViewById(R.id.searchMain);
        searchView.clearFocus();


        nav = findViewById(R.id.bottom_nav);

        mContext = this;
        mApiService = UtilsApi.getApiService();

        // Set up bottom navigation bar
        nav.setSelectedItemId(R.id.home);
        nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Handle bottom navigation item clicks
                int itemId = item.getItemId();
                if (itemId == R.id.home) {
                    Toast.makeText(MainActivity.this, "Home", Toast.LENGTH_SHORT).show();
                    return true;
                }
                else if (itemId == R.id.payment) {
                    // Check if the user is logged in
                    if(LoginActivity.loggedAccount != null){
                        Toast.makeText(MainActivity.this, "Payment", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), PaymentActivity.class));
                        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_from_left);
                        return true;
                    }else {
                        Toast.makeText(MainActivity.this, "Payment", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), NotRegisteredActivity.class));
                        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_from_left);
                        return true;
                    }
                }
                else if (itemId == R.id.profile) {
                    // Check if the user is logged in
                    if(LoginActivity.loggedAccount != null){
                        Toast.makeText(MainActivity.this, "Profile", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), AboutMeActivity.class));
                        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_from_left);
                        return true;
                    }else {
                        Toast.makeText(MainActivity.this, "Profile", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), NotRegisteredActivity.class));
                        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_from_left);
                        return true;
                    }
                }
                return true;
            }
        });

        // Load and handle user's buses
        handleMyBus();


    }

    /**
     * Handles the retrieval and display of the user's buses.
     */
    public void handleMyBus(){
        Account acc = LoginActivity.loggedAccount;
        mApiService.getBuses().enqueue(new Callback<BaseResponse<List<Bus>>>() {
            @Override
            public void onResponse(Call<BaseResponse<List<Bus>>> call, Response<BaseResponse<List<Bus>>> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(mContext, "ERROR" + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                BaseResponse<List<Bus>> resp = response.body();
                if (response.isSuccessful()) {
                    listBusMain.clear();
                    for(Bus b : resp.payload) {
                        listBusMain.add(b);
                    }
                    listbus = (ListView) findViewById(R.id.listBooking);
                    listSize = listBusMain.size();
                    MakeBookingAdapter adapter = new MakeBookingAdapter(getApplicationContext(), R.layout.listbusbooking_view, listBusMain, MainActivity.this);
                    listbus.setAdapter(adapter);

                    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                        @Override
                        public boolean onQueryTextSubmit(String s) {
                            return false;
                        }

                        @Override
                        public boolean onQueryTextChange(String s) {
                            //adapter.getFilter().filter(s);
                            //filterList(s);
                            List<Bus> filteredBus = new ArrayList<>();
                            for (Bus bus : listBusMain){
                                if (bus.name.toLowerCase().contains(s.toLowerCase())){
                                    filteredBus.add(bus);
                                }
                            }
                            MakeBookingAdapter adapter = new MakeBookingAdapter(getApplicationContext(), R.layout.listbusbooking_view, filteredBus, MainActivity.this);
                            listbus.setAdapter(adapter);
                            return false;
                        }
                    });

                }


            }

            @Override
            public void onFailure(Call<BaseResponse<List<Bus>>> call, Throwable t) {
                Toast.makeText(mContext, "Server ERROR", Toast.LENGTH_SHORT).show();
            }

        });

    }

    /**
     * Creates the options menu in the action bar.
     *
     * @param menu The options menu in which you place your items.
     * @return Returns true for the menu to be displayed; false for it not to be displayed.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar_menu, menu);
        return true;
    }

    /**
     * Handles options menu item selection.
     *
     * @param item The menu item that was selected.
     * @return Returns false to allow normal menu processing to proceed, true to consume it here.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==R.id.profile) {
            accountProfile();
        }

        return super.onOptionsItemSelected(item);
    }

    private void accountProfile() {
        Intent intent = new Intent(this, AboutMeActivity.class);
        startActivity(intent) ;
    }

    /**
     * Moves to another activity based on the provided class.
     *
     * @param ctx The context of the current activity.
     * @param cls The class of the activity to move to.
     */
    private void moveActivity(Context ctx, Class<?> cls){
        Intent intent = new Intent(ctx, cls);
        startActivity(intent);
    }

    /**
     * Displays a toast message with the given message.
     *
     * @param ctx     The context of the current activity.
     * @param message The message to be displayed in the toast.
     */
    private void viewToast(Context ctx, String message){
        Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show();
    }

    /*private void paginationFooter() {
        int val = listSize % pageSize;
        val = val == 0 ? 0 : 1;
        noOfPages = listSize / pageSize + val;

        LinearLayout ll = findViewById(R.id.btn_layout);
        btns = new Button[noOfPages];

        if (noOfPages <= 5) {
            ((FrameLayout.LayoutParams) ll.getLayoutParams()).gravity = Gravity.CENTER;
        }

        for (int i = 0; i < noOfPages; i++) {
            btns[i] = new Button(this);
            btns[i].setBackgroundColor(getResources().getColor(android.R.color.transparent));
            btns[i].setText("" + (i + 1)); // Ganti dengan warna yang kalian mau
            btns[i].setTextColor(getResources().getColor(R.color.black));

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    100,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            ll.addView(btns[i], lp);

            final int j = i;
            btns[j].setOnClickListener(v -> {
                currentPage = j;
                goToPage(j);
            });
        }
    }
    @SuppressLint("UseCompatLoadingForDrawables")
    private void goToPage(int index) {
        for (int i = 0; i< noOfPages; i++) {
            if (i == index) {
                btns[index].setBackgroundDrawable(getResources().getDrawable(R.drawable.circle));
                btns[i].setTextColor(getResources().getColor(android.R.color.black));
                scrollToItem(btns[index]);
                viewPaginatedList(listBus, currentPage);
            } else {
                btns[i].setBackgroundColor(getResources().getColor(android.R.color.transparent));
                btns[i].setTextColor(getResources().getColor(android.R.color.black));
            }
        }
    }
    private void scrollToItem(Button item) {
        int scrollX = item.getLeft() - (pageScroll.getWidth() - item.getWidth()) / 2;
        pageScroll.smoothScrollTo(scrollX, 0);
    }
    private void viewPaginatedList(List<Bus> listBus, int page) {
        int startIndex = page * pageSize;
        int endIndex = Math.min(startIndex + pageSize, listBus.size());
        List<Bus> paginatedList = listBus.subList(startIndex, endIndex);
        BusArrayAdapter paginatedAdapter = new BusArrayAdapter(this, R.layout.bus_view, paginatedList);
        busListView.setAdapter(paginatedAdapter);
    }*/
}