package com.example.smartbudget.Ui.Budget;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.example.smartbudget.R;
import com.example.smartbudget.Utils.Common;

public class ViewTransactionActivity extends AppCompatActivity {

    private static final String TAG = ViewTransactionActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_transaction);
        Log.d(TAG, "onCreate: started!!");

        String budgetCategory = "";

        if (getIntent() != null) {
            budgetCategory = getIntent().getStringExtra(Common.EXTRA_PASS_BUDGET_CATEGORY);
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(budgetCategory);
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
}
