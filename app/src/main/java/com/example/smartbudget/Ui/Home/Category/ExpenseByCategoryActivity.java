package com.example.smartbudget.Ui.Home.Category;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartbudget.Database.ExpenseBudgetRoom.DBExpenseBudgetUtils;
import com.example.smartbudget.Database.ExpenseBudgetRoom.ExpenseBudgetItem;
import com.example.smartbudget.Database.Model.ExpenseByCategory;
import com.example.smartbudget.Database.TransactionRoom.DBTransactionUtils;
import com.example.smartbudget.Database.Interface.IThisMonthTransactionsByCategoryLoadListener;
import com.example.smartbudget.Interface.IExpenseBudgetLoadListener;
import com.example.smartbudget.Model.EventBus.UpdateExpenseBudgetEvent;
import com.example.smartbudget.R;
import com.example.smartbudget.Ui.Main.MainActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExpenseByCategoryActivity extends AppCompatActivity implements IThisMonthTransactionsByCategoryLoadListener, IExpenseBudgetLoadListener {

    private static final String TAG = ExpenseByCategoryActivity.class.getSimpleName();

    public static final String EXTRA_PASSED_DATE = "PASSED_DATE";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_expense_by_category)
    RecyclerView rv_expense_by_category;
    @BindView(R.id.tv_no_transactions)
    TextView tv_no_transactions;

    private List<ExpenseBudgetItem> mExpenseBudgetItemList;
    private String passed_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_by_category);
        Log.d(TAG, "onCreate: started!!");

        if (getIntent() != null) {
            passed_date = getIntent().getStringExtra(EXTRA_PASSED_DATE);
        }

        initView();
    }

    private void initView() {
        Log.d(TAG, "initView: called!!");
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.toolbar_expense_by_category));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_close_black_24dp);

        rv_expense_by_category.setHasFixedSize(true);
        rv_expense_by_category.setLayoutManager(new LinearLayoutManager(this));

        loadData();
    }

    private void loadData() {
        Log.d(TAG, "loadData: called!!");
        DBExpenseBudgetUtils.getAllExpenseBudget(MainActivity.db, this);
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
    public void onExpenseBudgetLoadSuccess(List<ExpenseBudgetItem> expenseBudgetItemList) {
        Log.d(TAG, "onExpenseBudgetLoadSuccess: called!!");
        mExpenseBudgetItemList = expenseBudgetItemList;

        DBTransactionUtils.getThisMonthTransactionByCategory(MainActivity.db, passed_date, this);
    }

    @Override
    public void onExpenseBudgetLoadFailed(String message) {
        Log.d(TAG, "onExpenseBudgetLoadFailed: called!!");
    }

    @Override
    public void onThisMonthTransactionByCategoryLoadSuccess(List<ExpenseByCategory> expenseByCategoryList) {
        if (expenseByCategoryList == null || expenseByCategoryList.size() < 1) {
            rv_expense_by_category.setVisibility(View.GONE);
            tv_no_transactions.setVisibility(View.VISIBLE);
        } else {
            rv_expense_by_category.setVisibility(View.VISIBLE);
            tv_no_transactions.setVisibility(View.GONE);
            ExpenseByCategoryAdapter budgetAdapter = new ExpenseByCategoryAdapter(ExpenseByCategoryActivity.this,
                    expenseByCategoryList, passed_date, mExpenseBudgetItemList);
            rv_expense_by_category.setAdapter(budgetAdapter);
        }
    }

    @Override
    public void onThisMonthTransactionByCategoryLoadFailed(String message) {
        Log.d(TAG, "onThisMonthTransactionByCategoryLoadFailed: called!!");
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    void reloadDataByUpdate(UpdateExpenseBudgetEvent event){
        if (event.isSuccess()) {
            Toast.makeText(this, "[UPDATE SUCCESS!!]", Toast.LENGTH_SHORT).show();
            loadData();
        }
    }

}
