package com.example.smartbudget.Main;

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
import android.widget.Toast;

import com.example.smartbudget.Database.DBContract;
import com.example.smartbudget.R;
import com.example.smartbudget.Budget.BudgetFragment;
import com.example.smartbudget.Database.DBHelper;
import com.example.smartbudget.Database.Model.AccountModel;
import com.example.smartbudget.Home.HomeFragment;
import com.example.smartbudget.Statstics.StatsticsFragment;
import com.example.smartbudget.Transaction.AddTransactionActivity;

import java.util.Date;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(this);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddTransactionActivity.class));
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        gotoFragment(HomeFragment.newInstance());

        navigationView.getMenu().getItem(0).setChecked(true);

        GetAllCategoryAsync getAllCategoryAsync = new GetAllCategoryAsync();
        getAllCategoryAsync.execute();

//        GetAllAccountAsync getAllAccountAsync = new GetAllAccountAsync();
//        getAllAccountAsync.execute();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
            gotoFragment(HomeFragment.newInstance());
            getSupportActionBar().setTitle("Home");
        } else if (id == R.id.nav_budget) {
            gotoFragment(BudgetFragment.newInstance());
            getSupportActionBar().setTitle("My Budget");
        } else if (id == R.id.nav_overview) {
            gotoFragment(StatsticsFragment.newInstance());

        } else if (id == R.id.nav_calculator) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void gotoFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container, fragment);
        ft.commit();
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

}
