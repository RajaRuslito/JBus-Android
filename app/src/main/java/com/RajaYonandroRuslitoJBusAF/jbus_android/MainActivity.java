package com.RajaYonandroRuslitoJBusAF.jbus_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.RajaYonandroRuslitoJBusAF.jbus_android.databinding.ActivityMainBinding;
import com.RajaYonandroRuslitoJBusAF.jbus_android.model.Account;
import com.RajaYonandroRuslitoJBusAF.jbus_android.model.BaseResponse;
import com.RajaYonandroRuslitoJBusAF.jbus_android.model.Bus;
import com.RajaYonandroRuslitoJBusAF.jbus_android.request.BaseApiService;
import com.RajaYonandroRuslitoJBusAF.jbus_android.request.UtilsApi;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private Button[] btns;
    private int currentPage = 0;
    private int pageSize = 5;
    private int listSize;
    private int noOfPages;
    public static List<Bus> listBusMain = new ArrayList<>();
    private Button prevButton = null;
    private Button nextButton = null;
    private ListView busListView = null;
    private HorizontalScrollView pageScroll = null;
    //private View accountButton = null;
    //@SuppressLint("MissingInflatedId")
    private BottomNavigationView nav;
    private Context mContext;
    private BaseApiService mApiService;
    private int id;
    ActivityMainBinding binding;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        //busListView = findViewById(R.id.listView);
        /*//*listBus = Bus.sampleBusList(30);*//*
        listSize = listBus.size();

        BusArrayAdapter adapter = new BusArrayAdapter(getApplicationContext(), R.layout.modern_bus_view, listBus);
        listbus.setAdapter(adapter);
        busListView.setAdapter(adapter);*/
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

        nav = findViewById(R.id.bottom_nav);

        mContext = this;
        mApiService = UtilsApi.getApiService();

        nav.setSelectedItemId(R.id.home);

        nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            /*@Override*/
            /*public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                return false;
            }
        });
        nav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            //@SuppressLint("NonConstantResourceId")*/
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int itemId = item.getItemId();
                if (itemId == R.id.home) {
                    Toast.makeText(MainActivity.this, "Home", Toast.LENGTH_SHORT).show();
                    /*moveActivity(mContext, MainActivity.class);*/
                    return true;
                }
                else if (itemId == R.id.payment) {
                    //Toast.makeText(MainActivity.this, "Payment", Toast.LENGTH_SHORT).show();
                    if(LoginActivity.loggedAccount != null){
                        Toast.makeText(MainActivity.this, "Payment", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), PaymentActivity.class));
                        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_from_left);
                        return true;
                        /*Toast.makeText(MainActivity.this, "Payment", Toast.LENGTH_SHORT).show();
                        moveActivity(mContext, PaymentActivity.class);*/
                    }else {
                        Toast.makeText(MainActivity.this, "Payment", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), NotRegisteredActivity.class));
                        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_from_left);
                        return true;
                        /*Toast.makeText(MainActivity.this, "Payment", Toast.LENGTH_SHORT).show();
                        moveActivity(mContext, NotRegisteredActivity.class);*/
                    }
                }
                else if (itemId == R.id.profile) {
                    if(LoginActivity.loggedAccount != null){
                        Toast.makeText(MainActivity.this, "Profile", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), AboutMeActivity.class));
                        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_from_left);
                        return true;
                        /*Toast.makeText(MainActivity.this, "Profile", Toast.LENGTH_SHORT).show();
                        moveActivity(mContext, AboutMeActivity.class);*/
                    }else {
                        Toast.makeText(MainActivity.this, "Profile", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), NotRegisteredActivity.class));
                        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_from_left);
                        return true;
                        /*Toast.makeText(MainActivity.this, "Profile", Toast.LENGTH_SHORT).show();
                        moveActivity(mContext, NotRegisteredActivity.class);*/
                    }
                }
                return true;
            }
        });

        loadMyBus();



    }

    /*public void handleMyBus(){
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
                    listBus.clear();
                    for(Bus b : resp.payload) {
                        listBus.add(b);
                    }
                    ListView listbus = (ListView) findViewById(R.id.listView);
                    listSize = listBus.size();
                    BusArrayAdapter adapter = new BusArrayAdapter(getApplicationContext(), R.layout.activity_main, listBus);
                    listbus.setAdapter(adapter);

                }


            }

            @Override
            public void onFailure(Call<BaseResponse<List<Bus>>> call, Throwable t) {
                Toast.makeText(mContext, "Server ERROR", Toast.LENGTH_SHORT).show();
            }

        });

    }*/
    protected void loadMyBus() {
        int temp = 0;
        mApiService.getMyBus(/*LoginActivity.loggedAccount.id*/ temp).enqueue(new Callback<List<Bus>>() {
            @Override
            public void onResponse(Call<List<Bus>> call, Response<List<Bus>> response) {
                if (!response.isSuccessful()) return;

                List<Bus> myBusList = response.body();
                ListView busListView = (ListView) findViewById(R.id.listBooking);
                listBusMain = myBusList;
                MakeBookingAdapter adapter = new MakeBookingAdapter(getApplicationContext(), R.layout.listbusbooking_view, listBusMain, MainActivity.this);
                busListView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Bus>> call, Throwable t) {

            }
        });
    }
   /* public void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar_menu, menu);
        return true;
    }

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

    private void moveActivity(Context ctx, Class<?> cls){
        Intent intent = new Intent(ctx, cls);
        startActivity(intent);
    }

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