package com.example.smartbudget.Ui.Budget;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;
import androidx.viewpager.widget.ViewPager;

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
    @BindView(R.id.compactcalendar_view)
    CompactCalendarView compactcalendar_view;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.date_picker_text_view)
    TextView date_picker_text_view;
    @BindView(R.id.date_picker_arrow)
    ImageView date_picker_arrow;
    @BindView(R.id.date_picker_button)
    RelativeLayout date_picker_button;
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
        setTitle("Budget");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_close_black_24dp);

        compactcalendar_view.setLocale(TimeZone.getDefault(), Locale.KOREA);
        compactcalendar_view.setShouldDrawDaysHeader(true);
        compactcalendar_view.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                setSubTitle(Common.dateFormat.format(dateClicked));
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                setSubTitle(Common.dateFormat.format(firstDayOfNewMonth));
            }
        });

        setCurrentDate(new Date());

        date_picker_button.setOnClickListener(v -> {
            float rotation = isExpanded ? 0 : 180;
            ViewCompat.animate(date_picker_arrow).rotation(rotation).start();

            isExpanded = !isExpanded;
            app_bar.setExpanded(isExpanded, true);
        });
        
        fab.setOnClickListener(v -> {
            Toast.makeText(this, "[FAB BUDGET]", Toast.LENGTH_SHORT).show();
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

    private void setCurrentDate(Date date) {
        Log.d(TAG, "setCurrentDate: called!!");
        setSubTitle(Common.dateFormat.format(date));
        if (compactcalendar_view != null) {
            compactcalendar_view.setCurrentDate(date);
        }
    }

    @Override
    public void setTitle(CharSequence passedTitle) {
        if (title != null) {
            title.setText(passedTitle);
        }
    }

    private void setSubTitle(String subtitle) {
        Log.d(TAG, "setSubTitle: called!!");
        if (date_picker_text_view != null) {
            date_picker_text_view.setText(subtitle);
        }
    }
}
