package com.example.smartbudget.Ui.Home.Overview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.smartbudget.Database.Interface.IThisMonthTransactionsLoadListener;
import com.example.smartbudget.Database.TransactionRoom.DBTransactionUtils;
import com.example.smartbudget.Database.TransactionRoom.TransactionItem;
import com.example.smartbudget.R;
import com.example.smartbudget.Ui.Main.MainActivity;
import com.example.smartbudget.Utils.Common;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TransactionListByCategoryActivity extends AppCompatActivity implements IThisMonthTransactionsLoadListener {

    private static final String TAG = TransactionListByCategoryActivity.class.getSimpleName();

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_list_by_category)
    RecyclerView rv_list_by_category;

    private String passed_date;
    private String categoryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_list_by_category);
        Log.d(TAG, "onCreate: started!!");
        ButterKnife.bind(this);

        if (getIntent() != null) {
            passed_date= getIntent().getStringExtra("date");
            categoryId= getIntent().getStringExtra("categoryId");
        }

        initView();


    }

    private void initView() {
        Log.d(TAG, "initView: called!!");

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(Common.getExpenseCategory(categoryId).getCategoryVisibleName(this));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rv_list_by_category.setHasFixedSize(true);
        rv_list_by_category.setLayoutManager(new LinearLayoutManager(this));

        DBTransactionUtils.getThisMonthTransactionListByCategory(MainActivity.db, passed_date, categoryId, this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
            default:
                return true;
        }
    }

    @Override
    public void onThisMonthTransactionsLoadSuccess(List<TransactionItem> transactionItemList) {
        Log.d(TAG, "onThisMonthTransactionsLoadSuccess: called!!");
        if (transactionItemList == null || transactionItemList.size() < 1) {

        }
        else {
            TransactionListByCategoryAdapter adapter = new TransactionListByCategoryAdapter(this, transactionItemList);
            rv_list_by_category.setAdapter(adapter);
        }
    }

    @Override
    public void onThisMonthTransactionsLoadFailed(String message) {
        Log.d(TAG, "onThisMonthTransactionsLoadFailed: called!!");
    }
}
