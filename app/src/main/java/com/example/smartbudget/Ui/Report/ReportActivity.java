package com.example.smartbudget.Ui.Report;

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

public class ReportActivity extends AppCompatActivity {

    private static final String TAG = ReportActivity.class.getSimpleName();

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tab_sub_report)
    TabLayout tab_report;
    @BindView(R.id.vp_sub_report)
    ViewPager vp_report;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        Log.d(TAG, "onCreate: started!!");

        initView();
    }

    private void initView() {
        Log.d(TAG, "initView: called!!");
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        setTitle(getResources().getString(R.string.toolbar_title_report));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_close_black_24dp);

        ReportPagerAdapter adapter = new ReportPagerAdapter(getSupportFragmentManager(), this);

        vp_report.setAdapter(adapter);

        tab_report.setupWithViewPager(vp_report);

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
