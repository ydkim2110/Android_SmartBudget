package com.example.smartbudget.Ui.Budget;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smartbudget.Database.AccountRoom.AccountItem;
import com.example.smartbudget.Database.AccountRoom.DBAccountUtils;
import com.example.smartbudget.Database.BudgetRoom.BudgetItem;
import com.example.smartbudget.Database.BudgetRoom.DBBudgetUtils;
import com.example.smartbudget.Database.Interface.IAccountLoadListener;
import com.example.smartbudget.Database.Interface.IBudgetDeleteListener;
import com.example.smartbudget.Database.Interface.IBudgetInsertListener;
import com.example.smartbudget.Model.EventBus.AddBudgetEvent;
import com.example.smartbudget.Model.EventBus.AddDeleteEvent;
import com.example.smartbudget.R;
import com.example.smartbudget.Ui.Input.InputAccountActivity;
import com.example.smartbudget.Ui.Input.InputAmountActivity;
import com.example.smartbudget.Ui.Main.MainActivity;
import com.example.smartbudget.Ui.Transaction.Add.Date.DatePickerDialogFragment;
import com.example.smartbudget.Ui.Transaction.Add.Date.IDialogSendListener;
import com.example.smartbudget.Ui.Input.InputNoteActivity;
import com.example.smartbudget.Utils.Common;

import org.greenrobot.eventbus.EventBus;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddBudgetActivity extends AppCompatActivity implements IDialogSendListener, IBudgetInsertListener, IBudgetDeleteListener, IAccountLoadListener {

    private static final String TAG = AddBudgetActivity.class.getSimpleName();
    private static final int INPUT_ACCOUNT_REQUEST = 100;
    private static final int INPUT_NOTE_REQUEST = 200;
    private static final int INPUT_AMOUNT_REQUEST = 300;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_type_expense)
    TextView tv_type_expense;
    @BindView(R.id.tv_type_income)
    TextView tv_type_income;
    @BindView(R.id.edt_account)
    EditText edt_account;
    @BindView(R.id.edt_amount)
    EditText edt_amount;
    @BindView(R.id.edt_description)
    EditText edt_description;
    @BindView(R.id.edt_start_date)
    EditText edt_start_date;
    @BindView(R.id.edt_end_date)
    EditText edt_end_date;

    @BindView(R.id.btn_cancel)
    Button btn_cancel;
    @BindView(R.id.btn_save)
    Button btn_save;
    @BindView(R.id.iv_delete)
    ImageView iv_delete;

    @OnClick(R.id.iv_delete)
    void deleteBudget() {
        DBBudgetUtils.deleteBudget(MainActivity.db, this, mBudgetItem);
    }


    private Calendar mCalendar = Calendar.getInstance();

    private boolean isStartDate = false;

    private String selectedType = "";

    private BudgetItem mBudgetItem;
    private AccountItem mAccountItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_budget);
        Log.d(TAG, "onCreate: started!!");

        initView();

        if (getIntent().getParcelableExtra(Common.EXTRA_EDIT_BUDGET) != null) {

            iv_delete.setVisibility(View.VISIBLE);

            getSupportActionBar().setTitle(getResources().getString(R.string.toolbar_edit_budget));
            btn_save.setText(getResources().getString(R.string.btn_update));

            mBudgetItem = getIntent().getParcelableExtra(Common.EXTRA_EDIT_BUDGET);

            DBAccountUtils.getAccount(MainActivity.db, mBudgetItem.getAccountId(), this);
        }

        checkNoEmptyInputs();
    }

    private void initView() {
        Log.d(TAG, "initView: called!!");
        ButterKnife.bind(this);

        tv_type_expense.setBackgroundResource(R.drawable.shape_tv_waste_pressed);
        tv_type_expense.setTextColor(Color.WHITE);
        selectedType = "Expense";

        setSupportActionBar(toolbar);
        setTitle("Add Budget");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        edt_start_date.setText(Common.dateFormat.format(mCalendar.getTime()));
        mCalendar.add(Calendar.MONTH, 1);
        edt_end_date.setText(Common.dateFormat.format(mCalendar.getTime()));

        tv_type_expense.setOnClickListener(v -> {
            selectedType = "Expense";
            tv_type_expense.setBackgroundResource(R.drawable.shape_tv_waste_pressed);
            tv_type_expense.setTextColor(Color.WHITE);

            tv_type_income.setBackgroundResource(R.drawable.shape_tv_invest);
            tv_type_income.setTextColor(Color.GRAY);
        });

        tv_type_income.setOnClickListener(v -> {
            selectedType = "Income";
            tv_type_income.setBackgroundResource(R.drawable.shape_tv_invest_pressed);
            tv_type_income.setTextColor(Color.WHITE);

            tv_type_expense.setBackgroundResource(R.drawable.shape_tv_waste);
            tv_type_expense.setTextColor(Color.GRAY);
        });

        edt_account.setOnClickListener(v -> {
            Intent intent = new Intent(AddBudgetActivity.this, InputAccountActivity.class);
            if (edt_account.getText() != null) {
                intent.putExtra("accountName", edt_account.getText().toString());
            }
            startActivityForResult(intent, INPUT_ACCOUNT_REQUEST);
        });

        edt_amount.setOnClickListener(v -> {
            Intent intent = new Intent(AddBudgetActivity.this, InputAmountActivity.class);
            if (edt_amount.getText() != null) {
                intent.putExtra(Common.EXTRA_PASS_INPUT_AMOUNT, edt_amount.getText().toString());
            }
            startActivityForResult(intent, INPUT_AMOUNT_REQUEST);
        });

        edt_description.setOnClickListener(v -> {
            Intent intent = new Intent(AddBudgetActivity.this, InputNoteActivity.class);
            if (edt_amount.getText() != null) {
                intent.putExtra(Common.EXTRA_PASS_INPUT_NOTE, edt_description.getText().toString());
            }
            startActivityForResult(intent, INPUT_NOTE_REQUEST);
        });

        edt_start_date.setOnClickListener(v -> {
            isStartDate = true;
            DialogFragment dialogFragment = DatePickerDialogFragment.newInstance(edt_start_date.getText().toString());
            dialogFragment.show(getSupportFragmentManager(), "date picker");
        });

        edt_end_date.setOnClickListener(v -> {
            isStartDate = false;
            DialogFragment dialogFragment = DatePickerDialogFragment.newInstance(edt_end_date.getText().toString());
            dialogFragment.show(getSupportFragmentManager(), "date picker");
        });

        btn_cancel.setOnClickListener(v -> finish());

        btn_save.setOnClickListener(v -> {
            BudgetItem budgetItem = new BudgetItem();

            budgetItem.setDescription(edt_description.getText().toString());
            budgetItem.setAmount(Double.parseDouble(edt_amount.getText().toString()));
            budgetItem.setStartDate(edt_start_date.getText().toString());
            budgetItem.setEndDate(edt_end_date.getText().toString());
            budgetItem.setType(selectedType);
            budgetItem.setAccountId(mAccountItem.getId());

            DBBudgetUtils.insertBudget(MainActivity.db, this, budgetItem);

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == INPUT_ACCOUNT_REQUEST) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    mAccountItem = data.getParcelableExtra(Common.EXTRA_INPUT_ACCOUNT);
                    edt_account.setText(mAccountItem.getName());
                }
            }
        }
        else if (requestCode == INPUT_NOTE_REQUEST) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    edt_description.setText(data.getStringExtra(Common.EXTRA_INPUT_NOTE));
                }
            }
        } else if (requestCode == INPUT_AMOUNT_REQUEST) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    edt_amount.setText(data.getStringExtra(Common.EXTRA_INPUT_AMOUNT));
                }
            }
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

    private void checkNoEmptyInputs() {
        Log.d(TAG, "checkNoEmptyInputs: called!!");
        edt_account.addTextChangedListener(new TextWatcher() {
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
        edt_amount.addTextChangedListener(new TextWatcher() {
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
        edt_description.addTextChangedListener(new TextWatcher() {
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
        edt_start_date.addTextChangedListener(new TextWatcher() {
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
        edt_end_date.addTextChangedListener(new TextWatcher() {
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

    private void checkInputs() {
        Log.d(TAG, "checkInputs: called!!");

        if (!TextUtils.isEmpty(edt_account.getText())) {
            if (!TextUtils.isEmpty(edt_amount.getText())) {
                if (!TextUtils.isEmpty(edt_description.getText())) {
                    if (!TextUtils.isEmpty(edt_start_date.getText())) {
                        if (!TextUtils.isEmpty(edt_start_date.getText())) {
                            btn_save.setEnabled(true);
                        } else {
                            btn_save.setEnabled(false);
                        }
                    } else {
                        btn_save.setEnabled(false);
                    }
                } else {
                    btn_save.setEnabled(false);
                }
            } else {
                btn_save.setEnabled(false);
            }
        } else {
            btn_save.setEnabled(false);
        }
    }

    @Override
    public void OnSendListener(String result) {
        if (isStartDate)
            edt_start_date.setText(result);
        else
            edt_end_date.setText(result);
    }

    @Override
    public void onBudgetInsertSuccess(Boolean isInserted) {
        Log.d(TAG, "onBudgetInsertSuccess: called!!");
        if (isInserted) {
            EventBus.getDefault().postSticky(new AddBudgetEvent(true));
        }
        finish();
    }

    @Override
    public void onBudgetInsertFailed(String message) {
        Log.d(TAG, "onBudgetInsertFailed: called!!");
    }

    @Override
    public void onBudgetDeleteSuccess(boolean isDeleted) {
        Log.d(TAG, "onBudgetDeleteSuccess: called!!");
        if (isDeleted) {
            EventBus.getDefault().postSticky(new AddDeleteEvent(true));
        }
        finish();
    }

    @Override
    public void onBudgetDeleteFailed(String message) {
        Log.d(TAG, "onBudgetDeleteFailed: called!!");

    }

    @Override
    public void onAccountLoadSuccess(AccountItem accountItem) {
        mAccountItem = accountItem;

        selectedType = mBudgetItem.getType();

        if (selectedType.equals("Expense")) {
            tv_type_expense.setBackgroundResource(R.drawable.shape_tv_waste_pressed);
            tv_type_expense.setTextColor(Color.WHITE);

            tv_type_income.setBackgroundResource(R.drawable.shape_tv_invest);
            tv_type_income.setTextColor(Color.GRAY);
        }
        else if (selectedType.equals("Income")) {
            selectedType = "Income";
            tv_type_income.setBackgroundResource(R.drawable.shape_tv_invest_pressed);
            tv_type_income.setTextColor(Color.WHITE);

            tv_type_expense.setBackgroundResource(R.drawable.shape_tv_waste);
            tv_type_expense.setTextColor(Color.GRAY);
        }

        edt_amount.setText(String.valueOf((int) mBudgetItem.getAmount()));
        edt_description.setText(mBudgetItem.getDescription());
        edt_start_date.setText(mBudgetItem.getStartDate());
        edt_end_date.setText(mBudgetItem.getEndDate());
        edt_account.setText(mAccountItem.getName());
    }

    @Override
    public void onAccountLoadFailed(String message) {

    }
}
