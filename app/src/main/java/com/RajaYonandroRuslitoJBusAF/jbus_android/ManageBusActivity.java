package com.RajaYonandroRuslitoJBusAF.jbus_android;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.RajaYonandroRuslitoJBusAF.jbus_android.model.Bus;

import java.util.ArrayList;
import java.util.List;

public class ManageBusActivity extends AppCompatActivity {

    private ListView listView;
    private Button[] btns;
    private ListView newBusListView = null;
    private ImageView addBus = null;
    private List<Bus> listNewBus = new ArrayList<>();
    private int listNewSize;
    private int currentPage = 0;
    private int pageSize = 10;
    private int listSize;
    private int noOfPages;
    private List<Bus> listBus = new ArrayList<>();
    private Button prevButton = null;
    private Button nextButton = null;
    private ListView busListView = null;
    private HorizontalScrollView pageScroll = null;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_bus);

        getSupportActionBar().hide();

        newBusListView = findViewById(R.id.listViewManageBus);
        listNewBus = Bus.sampleBusList(30);
        listNewSize = listNewBus.size();
        ListView listbus = (ListView) findViewById(R.id.listViewManageBus);
        //BusArrayAdapter adapter = new BusArrayAdapter(getApplicationContext(), R.layout.activity_manage_bus, listNewBus);
        /*listbus.setAdapter(adapter);
        newBusListView.setAdapter(adapter);*/
        addBus = findViewById(R.id.addbus);

        addBus.setOnClickListener(view -> {
            moveActivity(this, AddBusActivity.class);
        });
    }

    private void moveActivity(Context ctx, Class<?> cls) {
        Intent intent = new Intent(ctx, cls);
        startActivity(intent);
    }

    private void paginationFooter() {
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
            btns[i].setTextColor(getResources().getColor(R.color.white));

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
                btns[i].setTextColor(getResources().getColor(android.R.color.white));
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
    }

}