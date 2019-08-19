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

import com.example.smartbudget.Database.AccountRoom.AccountItem;
import com.example.smartbudget.Database.AccountRoom.DBAccountUtils;
import com.example.smartbudget.Database.Interface.IAccountsLoadListener;
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

public class AccountActivity extends AppCompatActivity implements IAccountsLoadListener {

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
    @BindView(R.id.tv_total_asset_amount)
    TextView tv_total_asset_amount;
    @BindView(R.id.tv_total_net_amount)
    TextView tv_total_net_amount;
    @BindView(R.id.tv_asset)
    TextView tv_asset;
    @BindView(R.id.tv_debt)
    TextView tv_debt;

    int revenueColor;
    int expenseColor;
    int currentTabPosition;
    int totalAsset = 0;
    int totalDebt = 0;
    int netAsset = 0;
    String moneyUnit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        Log.d(TAG, "onCreate: started!!");

        ButterKnife.bind(this);
        revenueColor = ContextCompat.getColor(this,R.color.colorRevenue);
        expenseColor = ContextCompat.getColor(this,R.color.colorExpense);
        moneyUnit = getResources().getString(R.string.money_unit);

        initView();

        loadData();

    }

    private void loadData() {
        Log.d(TAG, "loadData: called!!");
        DBAccountUtils.getSumAccountsByType(MainActivity.db, this);
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
            mBSAccountAddFragment.show(getSupportFragmentManager(), mBSAccountAddFragment.getTag());
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
    public void onAccountsLoadSuccess(List<AccountItem> accountItemList) {
        Log.d(TAG, "onAccountsLoadSuccess: called!!");
        if (accountItemList != null) {
            for (int i = 0; i < accountItemList.size(); i++) {
                if (accountItemList.get(i).getType().equals("asset")) {
                    totalAsset = (int) accountItemList.get(i).getAmount();
                }
                else if (accountItemList.get(i).getType().equals("debt")) {
                    totalDebt = (int) accountItemList.get(i).getAmount();
                }
            }
        }
        Common.animateTextView(1000, 0, totalAsset, moneyUnit, tv_asset);
        Common.animateTextView(1000, 0, totalDebt, moneyUnit, tv_debt);
        Common.animateTextView(1000, 0, (totalAsset + totalDebt), moneyUnit, tv_total_asset_amount);
        Common.animateTextView(1000, 0, (totalAsset - totalDebt), moneyUnit, tv_total_net_amount);
    }

    @Override
    public void onAccountsLoadFailed(String message) {
        Log.d(TAG, "onAccountsLoadFailed: called!!");
    }
}
