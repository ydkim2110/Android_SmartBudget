package com.example.smartbudget.Ui.Account;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.example.smartbudget.Database.DatabaseUtils;
import com.example.smartbudget.Interface.ISumAccountsLoadListener;
import com.example.smartbudget.Model.AccountModel;
import com.example.smartbudget.R;
import com.example.smartbudget.Ui.Main.MainActivity;
import com.example.smartbudget.Utils.Common;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AccountActivity extends AppCompatActivity implements ISumAccountsLoadListener {

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
    @BindView(R.id.tv_balance_total)
    TextView tv_balance_total;
    @BindView(R.id.tv_asset)
    TextView tv_asset;
    @BindView(R.id.tv_debt)
    TextView tv_debt;

    int revenueColor;
    int expenseColor;
    int currentTabPosition;
    int assetTotal = 0;
    int debtTotal = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        Log.d(TAG, "onCreate: started!!");

        ButterKnife.bind(this);
        revenueColor = ContextCompat.getColor(this,R.color.colorRevenue);
        expenseColor = ContextCompat.getColor(this,R.color.colorExpense);

        initView();

        DatabaseUtils.getSumAccountsByType(MainActivity.mDBHelper, this);

    }

    private void initView() {
        currentTabPosition = 0;

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_close_white_24dp);

        AccountPagerAdapter adapter = new AccountPagerAdapter(getSupportFragmentManager(), this);

        vp_account.setAdapter(adapter);

        tab_account.setupWithViewPager(vp_account);

        fab.setOnClickListener(v -> {
            mBSAccountAddFragment = BSAccountAddFragment.getInstance();
            mBSAccountAddFragment.show(getSupportFragmentManager(),
                    mBSAccountAddFragment.getTag());
        });

        vp_account.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab_account));
        tab_account.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    app_bar.setBackgroundColor(revenueColor);
                }
                else if (tab.getPosition() == 1) {
                    app_bar.setBackgroundColor(expenseColor);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        app_bar.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            if (Math.abs(verticalOffset) - appBarLayout.getTotalScrollRange() == 0) {
                tab_account.setTabTextColors(ColorStateList.valueOf(Color.BLACK));
            }
            else {
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

    @Override
    public void onSumAccountsLoadSuccess(List<AccountModel> accountModelList) {
        if (accountModelList != null) {
            for (int i = 0; i < accountModelList.size(); i++) {
                if (accountModelList.get(i).getType().equals("asset")) {
                    assetTotal = (int) accountModelList.get(i).getAmount();
                }
                else if (accountModelList.get(i).getType().equals("debt")) {
                    debtTotal = (int) accountModelList.get(i).getAmount();
                }
            }
        }
        Common.animateTextView(0, assetTotal, tv_asset);
        Common.animateTextView(0, debtTotal, tv_debt);
        Common.animateTextView(0, (assetTotal - debtTotal), tv_balance_total);
    }

    @Override
    public void onSumAccountsLoadFailed(String message) {

    }
}
