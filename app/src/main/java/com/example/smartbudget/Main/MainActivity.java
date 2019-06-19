package com.example.smartbudget.Main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.smartbudget.Account.AccountFragment;
import com.example.smartbudget.Calculator.CalculatorFragment;
import com.example.smartbudget.Database.DBContract;
import com.example.smartbudget.R;
import com.example.smartbudget.Budget.BudgetFragment;
import com.example.smartbudget.Database.DBHelper;
import com.example.smartbudget.Home.HomeFragment;
import com.example.smartbudget.Report.ReportFragment;
import com.example.smartbudget.AddTransaction.AddTransactionActivity;
import com.example.smartbudget.Transaction.TransactionFragment;
import com.example.smartbudget.Travel.TravelFragment;

import java.util.Calendar;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.HorizontalCalendarView;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, HomeFragment.IBudgetContainerClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static final int HOME_FRAGMENT = 0;
    private static final int ACCOUNT_FRAGMENT = 1;
    private static final int BUDGET_FRAGMENT = 2;
    private static final int TRANSACTION_FRAGMENT = 3;
    private static final int REPORT_FRAGMENT = 4;
    private static final int CALCULATOR_FRAGMENT = 5;
    private static final int TRAVEL_FRAGMENT = 6;

    private NavigationView navigationView;
    private Toolbar mToolbar;
    private FloatingActionButton fab;
    private DrawerLayout drawer;
    private HorizontalCalendar horizontalCalendar;
    private FrameLayout calendarViewContainer;

    public static DBHelper mDBHelper;

    private int currentFragment = -1;
    private long backKeyPressedTime = 0;


    private ICalendarChangeListener mICalendarChangeListener;

    public void setICalendarChangeListener(ICalendarChangeListener mICalendarChangeListener) {
        this.mICalendarChangeListener = mICalendarChangeListener;
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        if (fragment instanceof HomeFragment) {
            HomeFragment homeFragment = (HomeFragment) fragment;
            ((HomeFragment) fragment).setIBudgetContainerClickListener(this);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_main);

        mDBHelper = new DBHelper(this);

        initToolbar();
        initView();
        initDrawAndNavigationView();
        horizontalCalendarView();

        gotoFragment(getResources().getString(R.string.menu_home), HomeFragment.getInstance(), HOME_FRAGMENT);
        navigationView.getMenu().getItem(currentFragment).setChecked(true);

        loadData();
    }

    private void horizontalCalendarView() {
        Log.d(TAG, "horizontalCalendarView: called!!");
        /* starts before 1 month from now */
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, -12);

        /* ends after 1 month from now */
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 3);

        horizontalCalendar = new HorizontalCalendar.Builder(this, R.id.calendarView)
                .range(startDate, endDate)
                .datesNumberOnScreen(5) // Number of Dates cells shown on screen (default to 5).
                .mode(HorizontalCalendar.Mode.MONTHS)
                .configure() // starts configuration.
                .formatMiddleText("MMM")
                .formatBottomText("yyyy")
                .sizeTopText(12)
                .sizeMiddleText(12)
                .sizeBottomText(12)
                .showTopText(false)
                .showBottomText(true)
                .end() // ends configuration.
                .build();

        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Calendar date, int position) {
                mICalendarChangeListener.onCalendarClicked(date);
            }

            @Override
            public void onCalendarScroll(HorizontalCalendarView calendarView, int dx, int dy) {
                super.onCalendarScroll(calendarView, dx, dy);
            }

            @Override
            public boolean onDateLongClicked(Calendar date, int position) {
                return super.onDateLongClicked(date, position);
            }

        });
    }

    private void initDrawAndNavigationView() {
        Log.d(TAG, "initDrawAndNavigationView: called!!");
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
    }

    private void loadData() {
        Log.d(TAG, "loadData: called!!");
        GetAllCategoryAsync getAllCategoryAsync = new GetAllCategoryAsync();
        getAllCategoryAsync.execute();

        GetAllTransactionAsync getAllTransactionAsync = new GetAllTransactionAsync();
        getAllTransactionAsync.execute();
    }

    private void initView() {
        Log.d(TAG, "initView: called!!");
        fab = findViewById(R.id.fab);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        calendarViewContainer = findViewById(R.id.calendarView_container);
    }

    private void initToolbar() {
        Log.d(TAG, "initToolbar: called!!");
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (currentFragment == HOME_FRAGMENT) {
            getMenuInflater().inflate(R.menu.nav_home_menu, menu);
        } else if (currentFragment == ACCOUNT_FRAGMENT) {
            getMenuInflater().inflate(R.menu.nav_account_menu, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.nav_account_menu) {
            Toast.makeText(this, "account menu click!!", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            gotoFragment(getResources().getString(R.string.menu_home), HomeFragment.getInstance(), HOME_FRAGMENT);
        } else if (id == R.id.nav_account) {
            gotoFragment(getResources().getString(R.string.menu_account), AccountFragment.getInstance(), ACCOUNT_FRAGMENT);
        } else if (id == R.id.nav_budget) {
            gotoFragment(getResources().getString(R.string.menu_budget), BudgetFragment.getInstance(), BUDGET_FRAGMENT);
        } else if (id == R.id.nav_transaction) {
            gotoFragment(getResources().getString(R.string.menu_transaction), TransactionFragment.getInstance(), TRANSACTION_FRAGMENT);
        } else if (id == R.id.nav_report) {
            gotoFragment(getResources().getString(R.string.menu_report), ReportFragment.getInstance(), REPORT_FRAGMENT);
        } else if (id == R.id.nav_calculator) {
            gotoFragment(getResources().getString(R.string.menu_calculator), CalculatorFragment.getInstance(), CALCULATOR_FRAGMENT);
        } else if (id == R.id.nav_travel) {
            gotoFragment(getResources().getString(R.string.menu_travel), TravelFragment.getInstance(), TRAVEL_FRAGMENT);
        } else if (id == R.id.nav_share) {
            Toast.makeText(this, "share click!!", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_send) {
            Toast.makeText(this, "send click!!", Toast.LENGTH_SHORT).show();
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void gotoFragment(String title, Fragment fragment, final int currentFragmentNUM) {
        Log.d(TAG, "gotoFragment: called!!");
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(title);
        invalidateOptionsMenu();

        setFragment(fragment, currentFragmentNUM);
    }

    @SuppressLint("RestrictedApi")
    private void setFragment(Fragment fragment, final int currentFragmentNUM) {
        Log.d(TAG, "setFragment: called");
        if (currentFragmentNUM == ACCOUNT_FRAGMENT || currentFragmentNUM == REPORT_FRAGMENT ||
                currentFragmentNUM == CALCULATOR_FRAGMENT || currentFragmentNUM == TRAVEL_FRAGMENT) {
            calendarViewContainer.setVisibility(View.GONE);
        } else {
            calendarViewContainer.setVisibility(View.GONE);
        }

        if (currentFragmentNUM == ACCOUNT_FRAGMENT || currentFragmentNUM == REPORT_FRAGMENT ||
                currentFragmentNUM == CALCULATOR_FRAGMENT || currentFragmentNUM == TRAVEL_FRAGMENT) {
            fab.setVisibility(View.INVISIBLE);
        } else {
            fab.setVisibility(View.VISIBLE);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (currentFragmentNUM == HOME_FRAGMENT) {
                        startActivity(new Intent(MainActivity.this, AddTransactionActivity.class));
                    }
                    else if (currentFragmentNUM == BUDGET_FRAGMENT) {
                        Toast.makeText(MainActivity.this, "Budget FAB Click!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        currentFragment = currentFragmentNUM;

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        ft.replace(R.id.container, fragment);
        ft.commit();
    }

    @Override
    public void onBudgetContainerClicked() {
        Toast.makeText(this, "Budget Click!!", Toast.LENGTH_SHORT).show();
        gotoFragment(getResources().getString(R.string.menu_budget), BudgetFragment.getInstance(), BUDGET_FRAGMENT);
        navigationView.getMenu().getItem(currentFragment).setChecked(true);
    }

    private class GetAllCategoryAsync extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            Cursor cursor = mDBHelper.getAllCategories();
            try {
                do {
                    long id = cursor.getLong(cursor.getColumnIndexOrThrow(DBContract.Category._ID));
                    String name = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Category.COL_NAME));
                    String icon = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Category.COL_ICON));
                    Log.d("GetAllCategoryAsync", "id: " + id);
                    Log.d("GetAllCategoryAsync", "name: " + name);
                    Log.d("GetAllCategoryAsync", "icon: " + icon);
                }
                while (cursor.moveToNext());
            } finally {
                cursor.close();
            }
            return null;
        }
    }

    private class GetAllTransactionAsync extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            Cursor cursor = mDBHelper.getAllTransactions();
            try {
                do {
                    long id = cursor.getLong(cursor.getColumnIndexOrThrow(DBContract.Transaction._ID));
                    String description = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Transaction.COL_DESCRIPTION));
                    String amount = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Transaction.COL_AMOUNT));
                    String date = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Transaction.COL_DATE));
                    String category_id = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Transaction.COL_CATEGORY_ID));
                    String account_id = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Transaction.COL_ACCOUNT_ID));
                    String to_account = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Transaction.COL_TO_ACCOUNT));
                    Log.d("GetAllTransactionAsync", "id: " + id);
                    Log.d("GetAllTransactionAsync", "description: " + description);
                    Log.d("GetAllTransactionAsync", "amount: " + amount);
                    Log.d("GetAllTransactionAsync", "date: " + date);
                    Log.d("GetAllTransactionAsync", "category_id: " + category_id);
                    Log.d("GetAllTransactionAsync", "account_id: " + account_id);
                    Log.d("GetAllTransactionAsync", "to_account: " + to_account);
                }
                while (cursor.moveToNext());
            } finally {
                cursor.close();
            }
            return null;
        }
    }

}
