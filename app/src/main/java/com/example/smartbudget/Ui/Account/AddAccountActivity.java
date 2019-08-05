package com.example.smartbudget.Ui.Account;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.smartbudget.Database.DatabaseUtils;
import com.example.smartbudget.Model.AccountModel;
import com.example.smartbudget.Ui.Main.MainActivity;
import com.example.smartbudget.R;
import com.example.smartbudget.Utils.Common;

import java.text.DecimalFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddAccountActivity extends AppCompatActivity implements IAccountInsertListener {

    private static final String TAG = AddAccountActivity.class.getSimpleName();

    private BSAccountAddFragment mBSAccountAddFragment;
    private BSAccountMenuFragment mBSAccountMenuFragment;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_name)
    EditText tv_name;
    @BindView(R.id.tv_description)
    EditText tv_description;
    @BindView(R.id.tv_amount)
    EditText tv_amount;
    @BindView(R.id.tv_high_category)
    EditText tv_high_category;
    @BindView(R.id.save_btn)
    Button save_btn;
    @BindView(R.id.cancel_btn)
    Button cancel_btn;

    private DecimalFormat df;
    private DecimalFormat dfnd;
    private boolean hasFractionalPart;

    private AccountModel passedAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account);
        Log.d(TAG, "onCreate: started!!");

        initView();

        df = new DecimalFormat("#,###.##");
        df.setDecimalSeparatorAlwaysShown(true);
        dfnd = new DecimalFormat("#,###");
        hasFractionalPart = false;

        if (Common.SELECTED_ACCOUNT != null) {
            passedAccount = Common.SELECTED_ACCOUNT;
        }

        if (Common.SELECTED_ACCOUNT == null) {
            tv_high_category.setText(getIntent().getStringExtra("high_category"));
        }

        tv_name.addTextChangedListener(new TextWatcher() {
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

        tv_description.addTextChangedListener(new TextWatcher() {
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

        tv_amount.addTextChangedListener(new TextWatcher() {
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

        cancel_btn.setOnClickListener(v -> finish());

        save_btn.setOnClickListener(v -> {
            String name = tv_name.getText().toString();
            String description = tv_description.getText().toString();
            double amount = Double.parseDouble(tv_amount.getText().toString());
            String highCategory = tv_high_category.getText().toString();
            String type = "";
            if (tv_high_category.getText().equals("대출") || tv_high_category.getText().equals("기타부채")) {
                type = "debt";
            } else {
                type = "asset";
            }

            if (save_btn.getText().toString().toUpperCase().equals("SAVE")) {
                Log.d(TAG, "onClick: save");
                AccountModel accountModel = new AccountModel(name, description,
                        amount, highCategory, type, new Date(), "KRW");
                DatabaseUtils.insertAccountAsync(MainActivity.mDBHelper, AddAccountActivity.this, accountModel);
            } else if (save_btn.getText().toString().toUpperCase().equals("UPDATE")) {
                Log.d(TAG, "onClick: update");
                int id = passedAccount.getId();
                AccountModel accountModel = new AccountModel(id, name, description,
                        amount, highCategory, type, new Date(), "KRW");
                DatabaseUtils.updateAccountAsync(MainActivity.mDBHelper, AddAccountActivity.this, accountModel);
            }
        });

    }

    private void checkInputs() {
        Log.d(TAG, "checkInputs: called!!");

        if (!TextUtils.isEmpty(tv_name.getText())) {
            if (!TextUtils.isEmpty(tv_description.getText())) {
                if (!TextUtils.isEmpty(tv_amount.getText())) {
                    save_btn.setEnabled(true);
                } else {
                    save_btn.setEnabled(false);
                }
            } else {
                save_btn.setEnabled(false);
            }
        } else {
            save_btn.setEnabled(false);
        }
    }

    private void initView() {
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        if (Common.SELECTED_ACCOUNT != null) {
            getSupportActionBar().setTitle("Edit Account");
        } else {
            getSupportActionBar().setTitle("Add Account");
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        save_btn.setEnabled(false);

        if (Common.SELECTED_ACCOUNT != null) {
            save_btn.setText("Update");
            tv_name.setText(passedAccount.getName());
            tv_description.setText(passedAccount.getDescription());
            tv_amount.setText("" + passedAccount.getAmount());
            tv_high_category.setText(passedAccount.getType());
        } else {
            save_btn.setText("Save");
        }
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
