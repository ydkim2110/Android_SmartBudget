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
import android.widget.Toast;

import com.example.smartbudget.Calculator.CalculatorFragment;
import com.example.smartbudget.Database.DBContract;
import com.example.smartbudget.R;
import com.example.smartbudget.Budget.BudgetFragment;
import com.example.smartbudget.Database.DBHelper;
import com.example.smartbudget.Home.HomeFragment;
import com.example.smartbudget.Statstics.StatsticsFragment;
import com.example.smartbudget.Transaction.AddTransactionActivity;
import com.example.smartbudget.Travel.TravelFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, HomeFragment.IBudgetContainerClickListener {

    private static final int HOME_FRAGMENT = 0;
    private static final int BUDGET_FRAGMENT = 1;
    private static final int STATSTICS_FRAGMENT = 2;
    private static final int CALCULATOR_FRAGMENT = 3;
    private static final int TRAVEL_FRAGMENT = 4;

    private NavigationView navigationView;
    private Toolbar toolbar;
    private FloatingActionButton fab;
    private DrawerLayout drawer;

    private DBHelper dbHelper;

    private int currentFragment = -1;
    private long backKeyPressedTime = 0;

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

        dbHelper = new DBHelper(this);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = findViewById(R.id.fab);

        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        gotoFragment(HomeFragment.newInstance(), HOME_FRAGMENT);
        navigationView.getMenu().getItem(currentFragment).setChecked(true);

        GetAllCategoryAsync getAllCategoryAsync = new GetAllCategoryAsync();
        getAllCategoryAsync.execute();

        GetAllAccountAsync getAllAccountAsync = new GetAllAccountAsync();
        getAllAccountAsync.execute();

        GetAllTransactionAsync getAllTransactionAsync = new GetAllTransactionAsync();
        getAllTransactionAsync.execute();

    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
                backKeyPressedTime = System.currentTimeMillis();
                Toast.makeText(this, "\'뒤로\'버튼을 한번 더 누르시면 종료됩니다.",
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
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            gotoFragment(HomeFragment.newInstance(), HOME_FRAGMENT);
            getSupportActionBar().setTitle("Home");
        } else if (id == R.id.nav_budget) {
            gotoFragment(BudgetFragment.newInstance(), BUDGET_FRAGMENT);
            getSupportActionBar().setTitle("Budget");
        } else if (id == R.id.nav_overview) {
            gotoFragment(StatsticsFragment.newInstance(), STATSTICS_FRAGMENT);
            getSupportActionBar().setTitle("Statstics");
            getSupportActionBar().setElevation(0);
        } else if (id == R.id.nav_calculator) {
            gotoFragment(CalculatorFragment.newInstance(), CALCULATOR_FRAGMENT);
            getSupportActionBar().setTitle("Calculator");
        } else if (id == R.id.nav_travel) {
            gotoFragment(TravelFragment.newInstance(), TRAVEL_FRAGMENT);
            getSupportActionBar().setTitle("Travel");
        }else if (id == R.id.nav_share) {
            Toast.makeText(this, "share click!!", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_send) {
            Toast.makeText(this, "send click!!", Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @SuppressLint("RestrictedApi")
    private void gotoFragment(Fragment fragment, final int currentFragmentNUM) {
        currentFragment = currentFragmentNUM;

        if (currentFragment == STATSTICS_FRAGMENT || currentFragment == CALCULATOR_FRAGMENT ||
                currentFragment == TRAVEL_FRAGMENT) {
            fab.setVisibility(View.INVISIBLE);
        } else {
            fab.setVisibility(View.VISIBLE);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (currentFragmentNUM == HOME_FRAGMENT) {
                        startActivity(new Intent(MainActivity.this, AddTransactionActivity.class));
                    }
                    else if (currentFragment == BUDGET_FRAGMENT) {
                        Toast.makeText(MainActivity.this, "Budget FAB Click!", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        ft.replace(R.id.container, fragment);
        ft.commit();
    }

    @Override
    public void onBudgetContainerClicked() {
        Toast.makeText(this, "Budget Click!!", Toast.LENGTH_SHORT).show();
        gotoFragment(BudgetFragment.newInstance(), BUDGET_FRAGMENT);
        getSupportActionBar().setTitle("My Budget");
        navigationView.getMenu().getItem(currentFragment).setChecked(true);
    }

    private class GetAllCategoryAsync extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            Cursor cursor = dbHelper.getAllCategories();
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

    private class GetAllAccountAsync extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            Cursor cursor = dbHelper.getAllAccounts();
            try {
                do {
                    long id = cursor.getLong(cursor.getColumnIndexOrThrow(DBContract.Account._ID));
                    String name = cursor.getString(cursor.getColumnIndexOrThrow("account_name"));
                    String amount = cursor.getString(cursor.getColumnIndexOrThrow("account_amount"));
                    String type = cursor.getString(cursor.getColumnIndexOrThrow("account_type"));
                    String create_at = cursor.getString(cursor.getColumnIndexOrThrow("account_create_at"));
                    String currency = cursor.getString(cursor.getColumnIndexOrThrow("account_currency"));
                    Log.d("GetAllAccountAsync", "id: " + id);
                    Log.d("GetAllAccountAsync", "name: " + name);
                    Log.d("GetAllAccountAsync", "amount: " + amount);
                    Log.d("GetAllAccountAsync", "type: " + type);
                    Log.d("GetAllAccountAsync", "create_at: " + create_at);
                    Log.d("GetAllAccountAsync", "currency: " + currency);
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
            Cursor cursor = dbHelper.getAllTransactions();
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
