package com.example.smartbudget.Account;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.smartbudget.Database.DBHelper;
import com.example.smartbudget.Database.DatabaseUtils;
import com.example.smartbudget.Database.Model.AccountModel;
import com.example.smartbudget.R;

import java.util.Date;

public class AddAccountActivity extends AppCompatActivity {

    private static final String TAG = AddAccountActivity.class.getSimpleName();

    private Toolbar mToolbar;

    private TextView accountName;
    private TextView accountAmount;
    private TextView accountType;

    private Button saveBtn;
    private Button cancelBtn;

    private DBHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account);
        Log.d(TAG, "onCreate: started!!");

        mDbHelper = new DBHelper(this);

        initToolbar();
        initView();

        accountType.setText(getIntent().getStringExtra("type"));

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = accountName.getText().toString();
                double amount = Double.parseDouble(accountAmount.getText().toString());
                String type = accountType.getText().toString();

                AccountModel accountModel = new AccountModel(name, amount, type, new Date(), "KRW");

                DatabaseUtils.insertAccountAsync(mDbHelper, accountModel);
            }
        });

    }

    private void initView() {
        accountName = findViewById(R.id.account_name);
        accountAmount = findViewById(R.id.account_amount);
        accountType = findViewById(R.id.account_type);
        saveBtn = findViewById(R.id.save_btn);
        cancelBtn = findViewById(R.id.cancel_btn);
    }

    private void initToolbar() {
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Add Account");
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
