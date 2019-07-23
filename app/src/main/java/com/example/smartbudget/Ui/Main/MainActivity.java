package com.example.smartbudget.Ui.Main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.smartbudget.Database.DBHelper;
import com.example.smartbudget.Model.EventBus.CalendarToggleEvent;
import com.example.smartbudget.R;
import com.example.smartbudget.Ui.Account.AccountFragment;
import com.example.smartbudget.Ui.Budget.BudgetFragment;
import com.example.smartbudget.Ui.Calculator.CalculatorFragment;
import com.example.smartbudget.Ui.Home.HomeFragment;
import com.example.smartbudget.Ui.Report.ReportFragment;
import com.example.smartbudget.Ui.Transaction.Add.AddTransactionActivity;
import com.example.smartbudget.Ui.Transaction.TransactionFragment;
import com.example.smartbudget.Ui.Travel.AddTravelActivity;
import com.example.smartbudget.Ui.Travel.TravelFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import org.greenrobot.eventbus.EventBus;

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

    public static DBHelper mDBHelper;

    private int currentFragment = -1;
    private long backKeyPressedTime = 0;

    private int clickedNavItem = 0;

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

        gotoFragment(getResources().getString(R.string.menu_home), HomeFragment.getInstance(), HOME_FRAGMENT);
        navigationView.getMenu().getItem(currentFragment).setChecked(true);
    }

    private void initDrawAndNavigationView() {
        Log.d(TAG, "initDrawAndNavigationView: called!!");
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
    }

    private void initView() {
        Log.d(TAG, "initView: called!!");
        fab = findViewById(R.id.fab);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
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
        } else if (currentFragment == TRANSACTION_FRAGMENT) {
            getMenuInflater().inflate(R.menu.nav_transaction_menu, menu);
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
        } else if (id == R.id.nav_transaction_menu) {
            EventBus.getDefault().post(new CalendarToggleEvent());
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            clickedNavItem = R.id.nav_home;
        } else if (id == R.id.nav_account) {
            clickedNavItem = R.id.nav_account;
        } else if (id == R.id.nav_budget) {
            clickedNavItem = R.id.nav_budget;
        } else if (id == R.id.nav_transaction) {
            clickedNavItem = R.id.nav_transaction;
        } else if (id == R.id.nav_report) {
            clickedNavItem = R.id.nav_report;
        } else if (id == R.id.nav_calculator) {
            clickedNavItem = R.id.nav_calculator;
        } else if (id == R.id.nav_travel) {
            clickedNavItem = R.id.nav_travel;
        }

        drawer.closeDrawer(GravityCompat.START);

        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
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
                    case R.id.nav_account:
                        gotoFragment(getResources().getString(R.string.menu_account), AccountFragment.getInstance(), ACCOUNT_FRAGMENT);
                        break;
                    case R.id.nav_budget:
                        gotoFragment(getResources().getString(R.string.menu_budget), BudgetFragment.getInstance(), BUDGET_FRAGMENT);
                        break;
                    case R.id.nav_transaction:
                        gotoFragment(getResources().getString(R.string.menu_transaction), TransactionFragment.getInstance(), TRANSACTION_FRAGMENT);
                        break;
                    case R.id.nav_report:
                        gotoFragment(getResources().getString(R.string.menu_report), ReportFragment.getInstance(), REPORT_FRAGMENT);
                        break;
                    case R.id.nav_calculator:
                        gotoFragment(getResources().getString(R.string.menu_calculator), CalculatorFragment.getInstance(), CALCULATOR_FRAGMENT);
                        break;
                    case R.id.nav_travel:
                        gotoFragment(getResources().getString(R.string.menu_travel), TravelFragment.getInstance(), TRAVEL_FRAGMENT);
                        break;
                }
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

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
                currentFragmentNUM == CALCULATOR_FRAGMENT) {
            fab.setVisibility(View.INVISIBLE);
        } else {
            fab.setVisibility(View.VISIBLE);
            fab.setOnClickListener(view -> {
                if (currentFragmentNUM == HOME_FRAGMENT || currentFragmentNUM == TRANSACTION_FRAGMENT) {
                    startActivity(new Intent(MainActivity.this, AddTransactionActivity.class));
                } else if (currentFragmentNUM == BUDGET_FRAGMENT) {
                    Toast.makeText(MainActivity.this, "Budget FAB Click!", Toast.LENGTH_SHORT).show();
                } else if (currentFragmentNUM == TRAVEL_FRAGMENT) {
                    startActivity(new Intent(MainActivity.this, AddTravelActivity.class));
                }
            });
        }
        currentFragment = currentFragmentNUM;

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        //ft.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        ft.replace(R.id.container, fragment);
        ft.commit();
    }

    @Override
    public void onBudgetContainerClicked() {
        Toast.makeText(this, "Budget Click!!", Toast.LENGTH_SHORT).show();
        gotoFragment(getResources().getString(R.string.menu_budget), BudgetFragment.getInstance(), BUDGET_FRAGMENT);
        navigationView.getMenu().getItem(currentFragment).setChecked(true);
    }

}
