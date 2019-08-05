package com.example.smartbudget.Ui.Main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.smartbudget.Database.DBHelper;
import com.example.smartbudget.Interface.IDateChangeListener;
import com.example.smartbudget.Interface.INSVScrollChangeListener;
import com.example.smartbudget.Interface.IRVScrollChangeListener;
import com.example.smartbudget.R;
import com.example.smartbudget.Ui.Account.AccountActivity;
import com.example.smartbudget.Ui.Budget.BudgetActivity;
import com.example.smartbudget.Ui.Calculator.CalcActivity;
import com.example.smartbudget.Ui.Home.HomeFragment;
import com.example.smartbudget.Ui.Report.ReportActivity;
import com.example.smartbudget.Ui.Transaction.Add.AddTransactionActivity;
import com.example.smartbudget.Ui.Transaction.TransactionActivity;
import com.example.smartbudget.Utils.Common;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, INSVScrollChangeListener, IRVScrollChangeListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private IDateChangeListener mIDateChangeListener;

    private static final int HOME_FRAGMENT = 0;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer_layout;
    @BindView(R.id.nav_view)
    NavigationView nav_view;
    @BindView(R.id.content)
    CoordinatorLayout content;
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

    private boolean isExpanded = false;
    private String currentDate;

    public static DBHelper mDBHelper;

    private int currentFragment = -1;
    private long backKeyPressedTime = 0;
    private int clickedNavItem = 0;

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        if (fragment instanceof IDateChangeListener) {
            mIDateChangeListener = (IDateChangeListener) fragment;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        mDBHelper = new DBHelper(this);

        initView();
        initDrawAndNavigationView();

        gotoFragment(getResources().getString(R.string.menu_home), HomeFragment.getInstance(), HOME_FRAGMENT);
        nav_view.getMenu().getItem(currentFragment).setChecked(true);
    }

    private void initDrawAndNavigationView() {
        Log.d(TAG, "initDrawAndNavigationView: called!!");
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                float slideX = drawerView.getWidth() * slideOffset / 2;
                content.setTranslationX(slideX);
            }
        };
        drawer_layout.addDrawerListener(toggle);
        toggle.syncState();

        nav_view.setNavigationItemSelectedListener(this);
    }

    private void initView() {
        Log.d(TAG, "initView: called!!");
        setSupportActionBar(toolbar);
        setTitle(getResources().getString(R.string.toolbar_title_home));

        compactcalendar_view.setLocale(TimeZone.getDefault(), Locale.KOREA);
        compactcalendar_view.setShouldDrawDaysHeader(true);
        compactcalendar_view.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                setSubTitle(Common.yearmonthDateFormate.format(dateClicked));
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                setSubTitle(Common.yearmonthDateFormate.format(firstDayOfNewMonth));
                currentDate = Common.dateFormat.format(firstDayOfNewMonth);
                mIDateChangeListener.onDateChanged(currentDate);
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
            startActivity(new Intent(MainActivity.this, AddTransactionActivity.class));
        });
    }

    private void setCurrentDate(Date date) {
        Log.d(TAG, "setCurrentDate: called!!");
        setSubTitle(Common.yearmonthDateFormate.format(date));
        if (compactcalendar_view != null) {
            compactcalendar_view.setCurrentDate(date);
        }
        //loadData(date);
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

    @Override
    public void onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START);
        } else {
            if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
                backKeyPressedTime = System.currentTimeMillis();
                Toast.makeText(this, getResources().getString(R.string.toast_on_back_pressed_message),
                        Toast.LENGTH_SHORT).show();
                return;
            }
            if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
                super.onBackPressed();
            }
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            clickedNavItem = R.id.nav_home;
            setCurrentDate(new Date());
            drawer_layout.closeDrawer(GravityCompat.START);
        } else if (id == R.id.nav_account) {
            startActivity(new Intent(MainActivity.this, AccountActivity.class));
        } else if (id == R.id.nav_budget) {
            startActivity(new Intent(MainActivity.this, BudgetActivity.class));
        } else if (id == R.id.nav_transaction) {
            startActivity(new Intent(MainActivity.this, TransactionActivity.class));
        } else if (id == R.id.nav_report) {
            startActivity(new Intent(MainActivity.this, ReportActivity.class));
        } else if (id == R.id.nav_calculator) {
            startActivity(new Intent(MainActivity.this, CalcActivity.class));
        }

        drawer_layout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
                switch (clickedNavItem) {
                    case R.id.nav_home:
                        gotoFragment(getResources().getString(R.string.menu_home), HomeFragment.getInstance(), HOME_FRAGMENT);
                        break;
                }
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

        return true;
    }

    private void gotoFragment(String title, Fragment fragment, int currentFragmentNUM) {
        Log.d(TAG, "gotoFragment: called!!");
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(title);
        invalidateOptionsMenu();

        currentFragment = currentFragmentNUM;

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container, fragment);
        ft.commit();
    }

    @Override
    public void onNSVScrollChangeListener(boolean isDown) {
        if (isDown) {
            fab.hide();
        } else {
            fab.show();
        }
    }

    @Override
    public void onRVScrollChangeListener(boolean isDown) {
        if (isDown) {
            if (fab.isShown()) {
                fab.hide();
            }
        } else {
            if (!fab.isShown()) {
                fab.show();
            }
        }
    }

}
