package com.example.smartbudget.Ui.Account;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.example.smartbudget.R;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AccountActivity extends AppCompatActivity {

    private static final String TAG = AccountActivity.class.getSimpleName();

    @BindView(R.id.app_bar)
    AppBarLayout app_bar;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tab_account)
    TabLayout tab_account;
    @BindView(R.id.vp_account)
    ViewPager vp_account;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        Log.d(TAG, "onCreate: started!!");

        ButterKnife.bind(this);

        initView();

    }

    private void initView() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.toolbar_account));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_close_white_24dp);

        AccountPagerAdapter adapter = new AccountPagerAdapter(getSupportFragmentManager(), this);

        vp_account.setAdapter(adapter);

        tab_account.setupWithViewPager(vp_account);

        fab.setOnClickListener(v -> {
            Toast.makeText(this, "[FAB CLICK]", Toast.LENGTH_SHORT).show();
        });

        app_bar.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            if (Math.abs(verticalOffset)-appBarLayout.getTotalScrollRange() == 0) {
                Log.d(TAG, "onOffsetChanged: collapsed");
                tab_account.setTabTextColors(ColorStateList.valueOf(Color.BLACK));
            }
            else {
                Log.d(TAG, "onOffsetChanged: expanded");
                tab_account.setTabTextColors(ColorStateList.valueOf(Color.WHITE));
            }
        });
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
