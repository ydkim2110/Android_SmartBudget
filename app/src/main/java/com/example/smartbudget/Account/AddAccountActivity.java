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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartbudget.Database.DBHelper;
import com.example.smartbudget.Database.DatabaseUtils;
import com.example.smartbudget.Database.Model.AccountModel;
import com.example.smartbudget.R;
import com.example.smartbudget.Utils.Common;
import com.example.smartbudget.Utils.NumberTextWatcher;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

public class AddAccountActivity extends AppCompatActivity implements IAccountInsertListener {

    private static final String TAG = AddAccountActivity.class.getSimpleName();

    private Toolbar mToolbar;

    private EditText accountName;
    private EditText accountDescription;
    private EditText accountAmount;
    private EditText accountType;

    private Button saveBtn;
    private Button cancelBtn;

    private DBHelper mDBHelper;

    private DecimalFormat df;
    private DecimalFormat dfnd;
    private boolean hasFractionalPart;

    private AccountModel passedAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account);
        Log.d(TAG, "onCreate: started!!");

        mDBHelper = new DBHelper(this);

        df = new DecimalFormat("#,###.##");
        df.setDecimalSeparatorAlwaysShown(true);
        dfnd = new DecimalFormat("#,###");
        hasFractionalPart = false;

        if (Common.SELECTED_ACCOUNT != null) {
            passedAccount = Common.SELECTED_ACCOUNT;
        }

        initToolbar();
        initView();

        if (Common.SELECTED_ACCOUNT == null) {
            accountType.setText(getIntent().getStringExtra("type"));
        }

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

        // todo: numberformat
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

        //accountAmount.addTextChangedListener(new NumberTextWatcher(accountAmount));

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
                if (saveBtn.getText().toString().toUpperCase().equals("SAVE")) {
                    Log.d(TAG, "onClick: save");
                    AccountModel accountModel = new AccountModel(name, description,
                            amount, type, new Date(), "KRW");
                    DatabaseUtils.insertAccountAsync(mDBHelper, AddAccountActivity.this, accountModel);
                }
                else if (saveBtn.getText().toString().toUpperCase().equals("UPDATE")) {
                    Log.d(TAG, "onClick: update");
                    int id = passedAccount.getId();
                    AccountModel accountModel = new AccountModel(id, name, description,
                            amount, type, new Date(), "KRW");
                    DatabaseUtils.updateAccountAsync(mDBHelper, AddAccountActivity.this, accountModel);
                }
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

        if (Common.SELECTED_ACCOUNT != null) {
            saveBtn.setText("Update");
            accountName.setText(passedAccount.getAccount_name());
            accountDescription.setText(passedAccount.getAccount_description());
            accountAmount.setText(""+passedAccount.getAccount_amount());
            accountType.setText(passedAccount.getAccount_type());
        } else {
            saveBtn.setText("Save");
        }
    }

    private void initToolbar() {
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        if (Common.SELECTED_ACCOUNT != null) {
            getSupportActionBar().setTitle("Edit Account");
        } else {
            getSupportActionBar().setTitle("Add Account");
        }
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

    @Override
    public void onAccountUpdateSuccess(Boolean isUpdated) {
        if (isUpdated) {
            Log.d(TAG, "onAccountUpdateSuccess: true");
            finish();
        } else {
            Log.d(TAG, "onAccountUpdateSuccess: false");
        }
    }
}
