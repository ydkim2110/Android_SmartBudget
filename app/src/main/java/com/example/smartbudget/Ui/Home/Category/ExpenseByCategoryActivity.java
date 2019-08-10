package com.example.smartbudget.Ui.Home.Category;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.smartbudget.Database.Model.ExpenseByCategory;
import com.example.smartbudget.Database.TransactionRoom.DBTransactionUtils;
import com.example.smartbudget.Database.Interface.IThisMonthTransactionsByCategoryLoadListener;
import com.example.smartbudget.R;
import com.example.smartbudget.Ui.Budget.BudgetAdapter;
import com.example.smartbudget.Ui.Main.MainActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExpenseByCategoryActivity extends AppCompatActivity implements IThisMonthTransactionsByCategoryLoadListener {

    private static final String TAG = ExpenseByCategoryActivity.class.getSimpleName();

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.rv_expense_by_category)
    RecyclerView rv_expense_by_category;

    private String passed_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_by_category);
        Log.d(TAG, "onCreate: started!!");

        if (getIntent() != null) {
            passed_date = getIntent().getStringExtra("passed_date");
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
        
        fab.setOnClickListener(v -> {
            Toast.makeText(this, "[FAB]", Toast.LENGTH_SHORT).show();
        });


        DBTransactionUtils.getThisMonthTransactionByCategory(MainActivity.db, passed_date, this);

        rv_expense_by_category.setHasFixedSize(true);
        rv_expense_by_category.setLayoutManager(new LinearLayoutManager(this));

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
    public void onThisMonthTransactionByCategoryLoadSuccess(List<ExpenseByCategory> expenseByCategoryList) {
        if (expenseByCategoryList != null) {
            BudgetAdapter budgetAdapter = new BudgetAdapter(ExpenseByCategoryActivity.this, expenseByCategoryList);
            rv_expense_by_category.setAdapter(budgetAdapter);
        }
    }

    @Override
    public void onThisMonthTransactionByCategoryLoadFailed(String message) {

    }
}
