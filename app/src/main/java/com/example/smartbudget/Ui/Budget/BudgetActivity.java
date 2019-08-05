package com.example.smartbudget.Ui.Budget;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartbudget.R;
import com.example.smartbudget.Utils.Common;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BudgetActivity extends AppCompatActivity {

    private static final String TAG = BudgetActivity.class.getSimpleName();

    @BindView(R.id.app_bar)
    AppBarLayout app_bar;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.tab_budget)
    TabLayout tab_budget;
    @BindView(R.id.vp_budget)
    ViewPager vp_budget;

    private boolean isExpanded = false;

    BudgetPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);
        Log.d(TAG, "onCreate: started!!");

        initView();

    }

    private void initView() {
        Log.d(TAG, "initView: called!!");
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        setTitle(getResources().getString(R.string.toolbar_title_budget));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_close_black_24dp);


        fab.setOnClickListener(v -> {
            startActivity(new Intent(BudgetActivity.this, AddBudgetActivity.class));
        });

        adapter = new BudgetPagerAdapter(getSupportFragmentManager(), this);
        vp_budget.setAdapter(adapter);
        tab_budget.setupWithViewPager(vp_budget);

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
