package com.example.smartbudget.Ui.Account;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.smartbudget.Database.DatabaseUtils;
import com.example.smartbudget.Interface.IThisMonthTransactionLoadListener;
import com.example.smartbudget.Model.AccountModel;
import com.example.smartbudget.Model.TransactionModel;
import com.example.smartbudget.R;
import com.example.smartbudget.Ui.Main.MainActivity;
import com.example.smartbudget.Utils.Common;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AccountDetailActivity extends AppCompatActivity implements IThisMonthTransactionLoadListener {

    private static final String TAG = AccountDetailActivity.class.getSimpleName();

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_account_detail)
    RecyclerView rv_account_detail;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_amount)
    TextView tv_amount;

    private AccountDetailAdapter mAdapter;

    private AccountModel account;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_detail);
        Log.d(TAG, "onCreate: started!!");

        if (getIntent() != null) {
            account = getIntent().getParcelableExtra("account_name");
            Log.d(TAG, "account name: "+account.getId());
        }

        initView();
    }

    private void initView() {
        Log.d(TAG, "initView: called!!");
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.toolbar_account_detail));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tv_name.setText(account.getName());
        tv_amount.setText(new StringBuilder(Common.changeNumberToComma((int) account.getAmount())).append("원"));

        rv_account_detail.setHasFixedSize(true);
        rv_account_detail.setLayoutManager(new LinearLayoutManager(this));
        rv_account_detail.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        DatabaseUtils.getThisMonthTransactionsByAccount(MainActivity.mDBHelper, Common.dateFormat.format(new Date()), account.getId(), this);
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
    public void onTransactionLoadSuccess(List<TransactionModel> transactionList) {
        Log.d(TAG, "onTransactionLoadSuccess: "+transactionList.size());
        if (transactionList != null) {
            mAdapter = new AccountDetailAdapter(this, transactionList);
            rv_account_detail.setAdapter(mAdapter);
        }
    }

    @Override
    public void onTransactionDeleteSuccess(boolean isSuccess) {

    }

}
