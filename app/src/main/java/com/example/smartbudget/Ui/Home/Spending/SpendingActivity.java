package com.example.smartbudget.Ui.Home.Spending;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.smartbudget.R;
import com.google.android.material.tabs.TabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SpendingActivity extends AppCompatActivity {

    private static final String TAG = SpendingActivity.class.getSimpleName();

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tab_spending_pattern)
    TabLayout tab_spending_pattern;
    @BindView(R.id.vp_spending_pattern)
    ViewPager vp_spending_pattern;

    PagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spending);
        Log.d(TAG, "onCreate: started!!");

        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.toolbar_spending_pattern));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        adapter = new PagerAdapter(getSupportFragmentManager(), this);
        vp_spending_pattern.setAdapter(adapter);
        tab_spending_pattern.setupWithViewPager(vp_spending_pattern);
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
