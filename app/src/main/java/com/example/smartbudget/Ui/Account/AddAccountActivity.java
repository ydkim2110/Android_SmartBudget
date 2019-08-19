package com.example.smartbudget.Ui.Account;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.smartbudget.Database.AccountRoom.AccountItem;
import com.example.smartbudget.Database.AccountRoom.DBAccountUtils;
import com.example.smartbudget.Database.DatabaseUtils;
import com.example.smartbudget.Database.Interface.IAccountInsertListener;
import com.example.smartbudget.Database.Interface.IAccountUpdateListener;
import com.example.smartbudget.Database.Interface.IBudgetInsertListener;
import com.example.smartbudget.Database.Interface.IDBUpdateListener;
import com.example.smartbudget.Model.AccountModel;
import com.example.smartbudget.Model.EventBus.AddAccountEvent;
import com.example.smartbudget.Model.EventBus.UpdateAccountEvent;
import com.example.smartbudget.Ui.Main.MainActivity;
import com.example.smartbudget.R;
import com.example.smartbudget.Utils.Common;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddAccountActivity extends AppCompatActivity implements IAccountInsertListener, IAccountUpdateListener {

    private static final String TAG = AddAccountActivity.class.getSimpleName();

    public static final String EXTRA_TAG = "tag";
    public static final String EXTRA_HIGH_CATEGORY = "HIGH_CATEGORY";
    public static final String EXTRA_HIGH_CATEGORY_ID = "HIGH_CATEGORY_ID";

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

    private AccountItem passedAccount;
    private AccountItem mAccountItem;
    private String passedHighCategory;
    private String passedHighCategoryId;
    private String selectedFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account);
        Log.d(TAG, "onCreate: started!!");
        ButterKnife.bind(this);

        if (getIntent() != null) {
            if (Common.SELECTED_ACCOUNT != null) {
                passedAccount = Common.SELECTED_ACCOUNT;
                passedHighCategory = Common.getAccountTitleById(this, passedAccount.getHighCategory());
                passedHighCategoryId = passedAccount.getHighCategory();
            }
        }

        initView();

        df = new DecimalFormat("#,###.##");
        df.setDecimalSeparatorAlwaysShown(true);
        dfnd = new DecimalFormat("#,###");
        hasFractionalPart = false;

        if (Common.SELECTED_ACCOUNT == null) {
            passedHighCategory = getIntent().getStringExtra(EXTRA_HIGH_CATEGORY);
            passedHighCategoryId = getIntent().getStringExtra(EXTRA_HIGH_CATEGORY_ID);

            tv_high_category.setText(passedHighCategory);
        }

        watchTextChange();

        cancel_btn.setOnClickListener(v -> finish());

        save_btn.setOnClickListener(v -> {
            String name = tv_name.getText().toString();
            String description = tv_description.getText().toString();
            double amount = Double.parseDouble(tv_amount.getText().toString());
            String highCategoryId = passedHighCategoryId;
            String type;

            if (passedHighCategoryId.equals("debt") || passedHighCategoryId.equals("other_debt")) {
                type = "debt";
            }
            else {
                type = "asset";
            }

            if (save_btn.getText().toString().toUpperCase().equals("SAVE")) {
                Log.d(TAG, "onClick: save");
                mAccountItem = new AccountItem(name, description,
                        amount, highCategoryId, type, Common.dateFormat.format(new Date()), "KRW");
                DBAccountUtils.insertAccountAsync(MainActivity.db, AddAccountActivity.this, mAccountItem);
            }
            else if (save_btn.getText().toString().toUpperCase().equals("UPDATE")) {
                Log.d(TAG, "onClick: update");
                int id = passedAccount.getId();
                mAccountItem = new AccountItem(id, name, description,
                        amount, highCategoryId, type, Common.dateFormat.format(new Date()), "KRW");
                DBAccountUtils.updateAccountAsync(MainActivity.db, AddAccountActivity.this, mAccountItem);
            }
        });

    }

    private void watchTextChange() {
        Log.d(TAG, "watchTextChange: called!!");
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
    }

    @Override
    protected void onDestroy() {
        Common.SELECTED_ACCOUNT = null;
        super.onDestroy();
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
        Log.d(TAG, "initView: called");

        setSupportActionBar(toolbar);

        if (Common.SELECTED_ACCOUNT != null) {
            getSupportActionBar().setTitle("Edit Account");
        }
        else {
            getSupportActionBar().setTitle("Add Account");
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        save_btn.setEnabled(false);

        if (Common.SELECTED_ACCOUNT != null) {
            save_btn.setText("Update");
            tv_name.setText(passedAccount.getName());
            tv_description.setText(passedAccount.getDescription());
            tv_amount.setText(new StringBuilder(String.valueOf((int) passedAccount.getAmount())));
            tv_high_category.setText(passedHighCategory);
        }
        else {
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
            EventBus.getDefault().postSticky(new AddAccountEvent(true));
            finish();
        } else {
            Toast.makeText(this, "INSERT FAILED!!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onAccountInsertFailed(String message) {
        Log.d(TAG, "onAccountInsertFailed: called!!");
    }

    @Override
    public void onAccountUpdateSuccess(boolean isUpdated) {
        Log.d(TAG, "onAccountUpdateSuccess: called!!");
        if (isUpdated) {
            EventBus.getDefault().postSticky(new UpdateAccountEvent(true));
            finish();
        } else {
            Toast.makeText(this, "UPDATE FAILED!!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onAccountUpdateFailed(String message) {
        Log.d(TAG, "onAccountUpdateFailed: called!!");
    }
}
