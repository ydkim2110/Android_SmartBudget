package com.example.smartbudget.Ui.Account;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.example.smartbudget.R;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AccountActivity extends AppCompatActivity {

    private static final String TAG = AccountActivity.class.getSimpleName();

    public static BSAccountAddFragment mBSAccountAddFragment;

    @BindView(R.id.app_bar)
    AppBarLayout app_bar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsing_toolbar;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tab_account)
    TabLayout tab_account;
    @BindView(R.id.vp_account)
    ViewPager vp_account;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    int revenueColor;
    int expenseColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        Log.d(TAG, "onCreate: started!!");

        ButterKnife.bind(this);
        revenueColor = ContextCompat.getColor(this,R.color.colorRevenue);
        expenseColor = ContextCompat.getColor(this,R.color.colorExpense);

        initView();

    }

    private void initView() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_close_white_24dp);

        AccountPagerAdapter adapter = new AccountPagerAdapter(getSupportFragmentManager(), this);

        vp_account.setAdapter(adapter);

        tab_account.setupWithViewPager(vp_account);

        fab.setOnClickListener(v -> {
            mBSAccountAddFragment = BSAccountAddFragment.getInstance();
            mBSAccountAddFragment.show(getSupportFragmentManager(), mBSAccountAddFragment.getTag());
        });

        vp_account.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab_account));
        tab_account.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                changeColor(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        app_bar.setBackgroundResource(R.color.colorBlack);


        app_bar.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            if (Math.abs(verticalOffset) - appBarLayout.getTotalScrollRange() == 0) {
                Log.d(TAG, "onOffsetChanged: collapsed");
                tab_account.setTabTextColors(ColorStateList.valueOf(Color.BLACK));
            } else {
                Log.d(TAG, "onOffsetChanged: expanded");
                tab_account.setTabTextColors(ColorStateList.valueOf(Color.WHITE));
            }
        });
    }

    private void changeColor(int position) {
        Log.d(TAG, "changeColor: called!!");
        if (position == 0) {
            Log.d(TAG, "onTabSelected: 0");
            app_bar.setBackgroundColor(revenueColor);
            tab_account.setSelectedTabIndicator(R.color.colorRevenue);
        } else if (position == 1) {
            Log.d(TAG, "onTabSelected: 1");
            app_bar.setBackgroundColor(expenseColor);
            tab_account.setSelectedTabIndicator(R.color.colorExpense);
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
