package com.example.smartbudget.Travel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.animation.ObjectAnimator;
import android.animation.StateListAnimator;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.smartbudget.R;
import com.example.smartbudget.Transaction.DynamicFragment;
import com.example.smartbudget.Transaction.DynamicFragmentAdapter;
import com.example.smartbudget.Utils.Common;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;

public class TravelDetailActivity extends AppCompatActivity {

    private static final String TAG = TravelDetailActivity.class.getSimpleName();

    private Travel travel;
    private Toolbar mToolbar;

    @BindView(R.id.travel_detail_tab)
    TabLayout travel_detail_tab;
    @BindView(R.id.travel_detail_viewPager)
    ViewPager travel_detail_viewPager;

    private int count = -1;
    private ArrayList<String> diffDays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_detail);
        Log.d(TAG, "onCreate: started!!");

        ButterKnife.bind(this);

        travel = getIntent().getParcelableExtra("travel_list");

        initToolbar();
        initView();
    }

    private void initView() {
        Log.d(TAG, "initView: called!!");

        count = Common.getDiffDays(travel.getStart_date(), travel.getEnd_date()).length;


        Log.d(TAG, "initView: travel getStart_date: " + travel.getStart_date());
        Log.d(TAG, "initView: travel getEnd_date: " + travel.getEnd_date());
        diffDays = new ArrayList<>();
        for (int i = 0; i <Common.getDiffDays(travel.getStart_date(), travel.getEnd_date()).length; i++) {
            Log.d(TAG, "initView: differ: "+Common.getDiffDays(travel.getStart_date(), travel.getEnd_date())[i]);
            diffDays.add(Common.getDiffDays(travel.getStart_date(), travel.getEnd_date())[i]);
        }



        setupViewPager(travel_detail_viewPager);

        travel_detail_tab.setupWithViewPager(travel_detail_viewPager);

        travel_detail_tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }

    private void setupViewPager(ViewPager viewPager) {
        TravelDetailFragmentAdapter adapter = new TravelDetailFragmentAdapter(getSupportFragmentManager());
        for(int i=0 ; i<count; i++){
            adapter.addFragment(TravelDetailFragment.newInstance(), diffDays.get(i));
            viewPager.setAdapter(adapter);
        }
    }

    private void initToolbar() {
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(travel.getName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);

        AppBarLayout appBarLayout = findViewById(R.id.appBarLayout);
        StateListAnimator stateListAnimator = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            stateListAnimator = new StateListAnimator();
            stateListAnimator.addState(new int[0], ObjectAnimator.ofFloat(appBarLayout, "elevation", 0));
            appBarLayout.setStateListAnimator(stateListAnimator);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
