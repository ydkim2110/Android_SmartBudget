package com.example.smartbudget.Ui.Home.Category;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.MenuItem;

import com.example.smartbudget.Database.DatabaseUtils;
import com.example.smartbudget.Database.Interface.IThisMonthTransactionsLoadListener;
import com.example.smartbudget.Database.TransactionRoom.DBTransactionUtils;
import com.example.smartbudget.Database.TransactionRoom.TransactionItem;
import com.example.smartbudget.Model.Category;
import com.example.smartbudget.R;
import com.example.smartbudget.Ui.Main.MainActivity;
import com.example.smartbudget.Utils.Common;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExpenseByCategoryDetailActivity extends AppCompatActivity implements IThisMonthTransactionsLoadListener {

    private static final String TAG = ExpenseByCategoryDetailActivity.class.getSimpleName();

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_category_detail)
    RecyclerView rv_category_detail;

    private String categoryId;
    private String passed_date;

    private Category category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_by_category_detail);
        Log.d(TAG, "onCreate: started!!");
        ButterKnife.bind(this);

        if (getIntent() != null) {
            categoryId = getIntent().getStringExtra(Common.EXTRA_PASS_BUDGET_CATEGORY);
            category = Common.getExpenseCategory(categoryId);
            passed_date = getIntent().getStringExtra("passed_date");
        }

        initView();

        DBTransactionUtils.getThisMonthTransactionListByCategory(MainActivity.db, passed_date, categoryId,this);

    }

    private void initView() {
        Log.d(TAG, "initView: called!!");

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(category.getCategoryVisibleName(this));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
    public void onThisMonthTransactionsLoadSuccess(List<TransactionItem> transactionItemList) {
        for (int i = 0; i < transactionItemList.size(); i++) {
            Log.d(TAG, "onThisMonthTransactionsLoadSuccess: "+transactionItemList.get(i).getDescription());
        }
    }

    @Override
    public void onThisMonthTransactionsLoadFailed(String message) {

    }
}
