package com.example.smartbudget.Account;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import java.util.List;

public class AddAccountActivity extends AppCompatActivity implements IAccountInsertListener {

    private static final String TAG = AddAccountActivity.class.getSimpleName();

    private Toolbar mToolbar;

    private TextView accountName;
    private TextView accountDescription;
    private TextView accountAmount;
    private TextView accountType;

    private Button saveBtn;
    private Button cancelBtn;

    private DBHelper mDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account);
        Log.d(TAG, "onCreate: started!!");

        mDBHelper = new DBHelper(this);

        initToolbar();
        initView();

        accountType.setText(getIntent().getStringExtra("type"));

        accountName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        accountDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        accountAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

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
                String description = accountDescription.getText().toString();
                double amount = Double.parseDouble(accountAmount.getText().toString());
                String type = accountType.getText().toString();

                AccountModel accountModel = new AccountModel(name, description, amount, type, new Date(), "KRW");

                DatabaseUtils.insertAccountAsync(mDBHelper, AddAccountActivity.this, accountModel);
            }
        });

    }

    private void checkInputs() {
        Log.d(TAG, "checkInputs: called!!");

        if (!TextUtils.isEmpty(accountName.getText())) {
            if (!TextUtils.isEmpty(accountDescription.getText())) {
                if (!TextUtils.isEmpty(accountAmount.getText())) {
                    saveBtn.setEnabled(true);
                } else {
                    saveBtn.setEnabled(false);
                }
            } else {
                saveBtn.setEnabled(false);
            }
        } else {
            saveBtn.setEnabled(false);
        }
    }

    private void initView() {
        accountName = findViewById(R.id.account_name);
        accountDescription = findViewById(R.id.account_description);
        accountAmount = findViewById(R.id.account_amount);
        accountType = findViewById(R.id.account_type);
        saveBtn = findViewById(R.id.save_btn);
        saveBtn.setEnabled(false);
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

    @Override
    public void onAccountInsertSuccess(Boolean isInserted) {
        if (isInserted) {
            Log.d(TAG, "onAccountInsertSuccess: true");
            finish();
        } else {
            Log.d(TAG, "onAccountInsertSuccess: false");

        }
    }
}
