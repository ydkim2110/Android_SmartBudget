package com.example.smartbudget.Ui.Home.Category;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.MenuItem;

import com.example.smartbudget.Database.DatabaseUtils;
import com.example.smartbudget.Database.Interface.IThisMonthSumTransactionBySubCategoryListener;
import com.example.smartbudget.Database.Interface.IThisMonthTransactionsLoadListener;
import com.example.smartbudget.Database.Model.SumTransactionBySubCategory;
import com.example.smartbudget.Database.TransactionRoom.DBTransactionUtils;
import com.example.smartbudget.Database.TransactionRoom.TransactionItem;
import com.example.smartbudget.Model.Category;
import com.example.smartbudget.R;
import com.example.smartbudget.Ui.Main.MainActivity;
import com.example.smartbudget.Utils.Common;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExpenseByCategoryDetailActivity extends AppCompatActivity implements IThisMonthSumTransactionBySubCategoryListener {

    private static final String TAG = ExpenseByCategoryDetailActivity.class.getSimpleName();

    public static final String EXTRA_PASSED_DATE = "PASSED_DATE";
    public static final String EXTRA_CATEGORY_ID = "CATEGORY_ID";

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
            categoryId = getIntent().getStringExtra(EXTRA_CATEGORY_ID);
            category = Common.getExpenseCategory(categoryId);
            passed_date = getIntent().getStringExtra(EXTRA_PASSED_DATE);
        }

        initView();

        DBTransactionUtils.getThisMonthSumTransactionBySubCategory(MainActivity.db, passed_date, categoryId,this);

    }

    private void initView() {
        Log.d(TAG, "initView: called!!");

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(category.getCategoryVisibleName(this));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rv_category_detail.setHasFixedSize(true);
        rv_category_detail.setLayoutManager(new GridLayoutManager(this, 2));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onThisMonthSumTransactionBySubCategorySuccess(List<SumTransactionBySubCategory> sumTransactionBySumCategoryList) {
        Log.d(TAG, "onThisMonthSumTransactionBySubCategorySuccess: called!!");
        ExpenseByCategoryDetailAdapter adapter = new ExpenseByCategoryDetailAdapter(this, sumTransactionBySumCategoryList);
        rv_category_detail.setAdapter(adapter);
    }

    @Override
    public void onThisMonthSumTransactionBySubCategoryFailed(String message) {
        Log.d(TAG, "onThisMonthSumTransactionBySubCategoryFailed: called!!");
    }
}
