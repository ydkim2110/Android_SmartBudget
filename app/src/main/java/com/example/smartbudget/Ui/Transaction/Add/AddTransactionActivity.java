package com.example.smartbudget.Ui.Transaction.Add;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartbudget.Database.AccountRoom.AccountItem;
import com.example.smartbudget.Database.AccountRoom.DBAccountUtils;
import com.example.smartbudget.Database.Interface.IAccountLoadListener;
import com.example.smartbudget.Database.TransactionRoom.DBTransactionUtils;
import com.example.smartbudget.Database.TransactionRoom.TransactionItem;
import com.example.smartbudget.Interface.ITransactionInsertListener;
import com.example.smartbudget.Interface.ITransactionUpdateListener;
import com.example.smartbudget.Model.Category;
import com.example.smartbudget.Model.EventBus.AddTransactionEvent;
import com.example.smartbudget.Model.EventBus.CategorySelectedEvent;
import com.example.smartbudget.Model.SubCategory;
import com.example.smartbudget.Ui.Input.InputAccountActivity;
import com.example.smartbudget.Ui.Input.InputAmountActivity;
import com.example.smartbudget.Ui.Transaction.Add.Category.CategoryDialogFragment;
import com.example.smartbudget.Ui.Transaction.Add.Category.InputCategoryActivity;
import com.example.smartbudget.Ui.Transaction.Add.Date.DatePickerDialogFragment;
import com.example.smartbudget.Ui.Transaction.Add.Date.IDialogSendListener;
import com.example.smartbudget.Ui.Transaction.Add.Note.InputNoteActivity;
import com.example.smartbudget.Database.DatabaseUtils;
import com.example.smartbudget.Model.CategoryModel;
import com.example.smartbudget.Model.TransactionModel;
import com.example.smartbudget.Ui.Main.MainActivity;
import com.example.smartbudget.R;
import com.example.smartbudget.Utils.Common;
import com.example.smartbudget.Utils.DateHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddTransactionActivity extends AppCompatActivity
        implements CategoryDialogFragment.OnDialogSendListener, IDialogSendListener, ITransactionInsertListener,
        IAccountLoadListener, ITransactionUpdateListener {

    private static final String TAG = AddTransactionActivity.class.getSimpleName();

    private static final int INPUT_ACCOUNT_REQUEST = 100;
    private static final int INPUT_CATEGORY_REQUEST = 200;
    private static final int INPUT_NOTE_REQUEST = 300;
    private static final int INPUT_AMOUNT_REQUEST = 400;

    private Toolbar mToolbar;
    private EditText accountEdt;
    private EditText categoryEdt;
    private EditText noteEdt;
    private EditText dateEdt;
    private EditText amountEdt;
    private Button saveBtn;
    private Button cancelBtn;
    private Calendar calendar;

    @BindView(R.id.tv_normal)
    TextView tv_normal;
    @BindView(R.id.tv_waste)
    TextView tv_waste;
    @BindView(R.id.tv_invest)
    TextView tv_invest;
    @BindView(R.id.iv_delete)
    ImageView iv_delete;
    @BindView(R.id.ll_pattern_container)
    LinearLayout ll_pattern_container;

    @OnClick(R.id.iv_delete)
    void deleteTransaction() {
        DatabaseUtils.deleteTransactionAsync(MainActivity.mDBHelper, mTransactionModel);
        finish();
    }

    int _year;
    int _month;
    int _day;

    private AccountItem mAccountItem;
    private TransactionModel mTransactionModel;
    private String selectedType = "";
    private Category selectedCategory = null;
    private SubCategory selectedSubCategory = null;

    private String selectedPattern = "";

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);
        Log.d(TAG, "onCreate: started!!");

        ButterKnife.bind(this);

        initView();

        if (getIntent().getParcelableExtra(Common.EXTRA_EDIT_TRANSACTION) != null) {

            iv_delete.setVisibility(View.VISIBLE);

            getSupportActionBar().setTitle(getResources().getString(R.string.toolbar_edit_transaction));
            saveBtn.setText(getResources().getString(R.string.btn_update));

            TransactionModel transactionModel = getIntent().getParcelableExtra(Common.EXTRA_EDIT_TRANSACTION);

            DBAccountUtils.getAccount(MainActivity.db, transactionModel.getAccountId(), this);

            mTransactionModel = transactionModel;
            selectedType = mTransactionModel.getType();
            selectedPattern = mTransactionModel.getPattern();
        }

        handleClickEvent();

        checkNoEmptyInputs();
    }

    private void checkNoEmptyInputs() {
        Log.d(TAG, "checkNoEmptyInputs: called!!");

        accountEdt.addTextChangedListener(new TextWatcher() {
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

        categoryEdt.addTextChangedListener(new TextWatcher() {
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

        noteEdt.addTextChangedListener(new TextWatcher() {
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

        amountEdt.addTextChangedListener(new TextWatcher() {
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

        if (!TextUtils.isEmpty(accountEdt.getText())) {
            if (!TextUtils.isEmpty(categoryEdt.getText())) {
                if (!TextUtils.isEmpty(noteEdt.getText())) {
                    if (!TextUtils.isEmpty(amountEdt.getText())) {
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
        } else {
            saveBtn.setEnabled(false);
        }
    }

    @SuppressLint("ResourceAsColor")
    private void initView() {
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.toolbar_add_transaction));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        accountEdt = findViewById(R.id.tv_add_budget_account);
        categoryEdt = findViewById(R.id.add_transaction_category);
        noteEdt = findViewById(R.id.add_transaction_description);
        dateEdt = findViewById(R.id.add_transaction_date);
        amountEdt = findViewById(R.id.tv_add_budget_amount);
        cancelBtn = findViewById(R.id.cancel_btn);
        saveBtn = findViewById(R.id.save_btn);
        saveBtn.setEnabled(false);

        iv_delete.setVisibility(View.GONE);

        calendar = Calendar.getInstance();
        _year = calendar.get(Calendar.YEAR);
        _month = calendar.get(Calendar.MONTH) + 1;
        _day = calendar.get(Calendar.DAY_OF_MONTH);

        dateEdt.setText(Common.dateFormat.format(calendar.getTime()));

        tv_normal.setBackgroundResource(R.drawable.shape_tv_normal_pressed);
        tv_normal.setTextColor(Color.WHITE);
        selectedPattern = "Normal";

        tv_normal.setOnClickListener(v -> {
            selectedPattern = "Normal";
            tv_normal.setBackgroundResource(R.drawable.shape_tv_normal_pressed);
            tv_normal.setTextColor(Color.WHITE);

            tv_waste.setBackgroundResource(R.drawable.shape_tv_waste);
            tv_waste.setTextColor(Color.GRAY);

            tv_invest.setBackgroundResource(R.drawable.shape_tv_invest);
            tv_invest.setTextColor(Color.GRAY);
        });

        tv_waste.setOnClickListener(v -> {
            selectedPattern = "Waste";
            tv_waste.setBackgroundResource(R.drawable.shape_tv_waste_pressed);
            tv_waste.setTextColor(Color.WHITE);

            tv_normal.setBackgroundResource(R.drawable.shape_tv_normal);
            tv_normal.setTextColor(Color.GRAY);

            tv_invest.setBackgroundResource(R.drawable.shape_tv_invest);
            tv_invest.setTextColor(Color.GRAY);
        });

        tv_invest.setOnClickListener(v -> {
            selectedPattern = "Invest";
            tv_invest.setBackgroundResource(R.drawable.shape_tv_invest_pressed);
            tv_invest.setTextColor(Color.WHITE);

            tv_normal.setBackgroundResource(R.drawable.shape_tv_normal);
            tv_normal.setTextColor(Color.GRAY);

            tv_waste.setBackgroundResource(R.drawable.shape_tv_waste);
            tv_waste.setTextColor(Color.GRAY);
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void handleClickEvent() {
        accountEdt.setOnClickListener(v -> {
            Intent intent = new Intent(AddTransactionActivity.this, InputAccountActivity.class);
            if (accountEdt.getText() != null) {
                intent.putExtra("accountName", accountEdt.getText().toString());
            }
            startActivityForResult(intent, INPUT_ACCOUNT_REQUEST);
        });

        categoryEdt.setOnClickListener(v -> {
            Intent intent = new Intent(AddTransactionActivity.this, InputCategoryActivity.class);
            startActivityForResult(intent, INPUT_CATEGORY_REQUEST);
        });

        noteEdt.setOnClickListener(v -> {
            Intent intent = new Intent(AddTransactionActivity.this, InputNoteActivity.class);
            if (noteEdt.getText() != null) {
                intent.putExtra(Common.EXTRA_PASS_INPUT_NOTE, noteEdt.getText().toString());
            }
            startActivityForResult(intent, INPUT_NOTE_REQUEST);
        });

        dateEdt.setOnClickListener(v -> {
            DialogFragment dialogFragment = DatePickerDialogFragment.newInstance(dateEdt.getText().toString());
            dialogFragment.show(getSupportFragmentManager(), "date picker");
        });

        amountEdt.setOnClickListener(v -> {
            Intent intent = new Intent(AddTransactionActivity.this, InputAmountActivity.class);
            if (noteEdt.getText() != null) {
                intent.putExtra(Common.EXTRA_PASS_INPUT_AMOUNT, amountEdt.getText().toString());
            }
            startActivityForResult(intent, INPUT_AMOUNT_REQUEST);
        });

        cancelBtn.setOnClickListener(v -> finish());

        saveBtn.setOnClickListener(v -> {

            TransactionItem transactionItem = new TransactionItem();

            if (saveBtn.getText().toString().toLowerCase().equals("save")) {
                transactionItem.setDescription(noteEdt.getText().toString());
                transactionItem.setAmount(Double.parseDouble(Common.removeComma(amountEdt.getText().toString())));
                transactionItem.setType(selectedType);
                transactionItem.setPattern(selectedPattern);
                transactionItem.setDate(DateHelper.changeDateToString(DateHelper.changeStringToDate(dateEdt.getText().toString())));
                transactionItem.setCategoryId(selectedCategory.getCategoryID());
                transactionItem.setSubCategoryId("");
                transactionItem.setAccountId(mAccountItem.getId());

                if (selectedType.equals("Expense") || selectedType.equals("Income")) {
                    DBTransactionUtils.insertTransactionAsync(MainActivity.db, AddTransactionActivity.this, transactionItem);
                } else if (selectedType.equals("Transfer")) {
                    transactionItem.setToAccount(0);
                    // todo: transfer

                }
            }
            else if (saveBtn.getText().toString().toLowerCase().equals("update")) {
                transactionItem.setId(mTransactionModel.getId());
                transactionItem.setDescription(noteEdt.getText().toString());
                transactionItem.setAmount(Double.parseDouble(Common.removeComma(amountEdt.getText().toString())));
                transactionItem.setType(selectedType);
                transactionItem.setPattern(selectedPattern);
                transactionItem.setDate(DateHelper.changeDateToString(DateHelper.changeStringToDate(dateEdt.getText().toString())));
                transactionItem.setCategoryId(selectedCategory.getCategoryID());
                transactionItem.setSubCategoryId("");
                transactionItem.setAccountId(mAccountItem.getId());

                if (selectedType.equals("Expense") || selectedType.equals("Income")) {
                    DBTransactionUtils.updateTransactionAsync(MainActivity.db, AddTransactionActivity.this, transactionItem);
                } else if (selectedType.equals("Transfer")) {
                    transactionItem.setToAccount(0);
                    // todo: transfer
                }
            }


        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == INPUT_ACCOUNT_REQUEST) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    mAccountItem = data.getParcelableExtra(Common.EXTRA_INPUT_ACCOUNT);
                    accountEdt.setText(mAccountItem.getName());
                }
            }
        } else if (requestCode == INPUT_NOTE_REQUEST) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    noteEdt.setText(data.getStringExtra(Common.EXTRA_INPUT_NOTE));
                }
            }
        } else if (requestCode == INPUT_AMOUNT_REQUEST) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    amountEdt.setText(data.getStringExtra(Common.EXTRA_INPUT_AMOUNT));
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void OnSendListener(String result) {
        dateEdt.setText(result);
    }

    @Override
    public void onTransactionInsertSuccess(Boolean isInserted) {
        if (isInserted) {
            EventBus.getDefault().post(new AddTransactionEvent());
            finish();
        }
    }

    @Override
    public void sendResult(CategoryModel categoryModel) {

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
    public void onEvent(CategorySelectedEvent event) {
        Log.d(TAG, "onEvent: called!!");
        EventBus.getDefault().removeStickyEvent(event);
        if (event.isSuccess()) {
            if (event.getCategoryType() == Common.TYPE_CATEGORY) {
                selectedCategory = event.getCategory();
                categoryEdt.setText(event.getCategory().getCategoryVisibleName(getApplicationContext()));
                categoryEdt.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, event.getCategory().getIconResourceID(), 0);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    categoryEdt.setCompoundDrawableTintList(ColorStateList.valueOf(event.getCategory().getIconColor()));
                }
            }
            else if (event.getCategoryType() == Common.TYPE_SUB_CATEGORY) {
                selectedSubCategory = event.getSubCategory();
                categoryEdt.setText(event.getSubCategory().getCategoryVisibleName(getApplicationContext()));
                categoryEdt.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, event.getSubCategory().getIconResourceID(), 0);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    categoryEdt.setCompoundDrawableTintList(ColorStateList.valueOf(event.getSubCategory().getIconColor()));
                }
            }

            if (event.getTransactionType() == Common.TYPE_EXPENSE_TRANSACTION) {
                selectedType = "Expense";
                if (ll_pattern_container.getVisibility() == View.GONE)
                    ll_pattern_container.setVisibility(View.VISIBLE);
            }
            else if (event.getTransactionType() == Common.TYPE_INCOME_TRANSACTION) {
                selectedType = "Income";
                if (ll_pattern_container.getVisibility() == View.VISIBLE)
                    ll_pattern_container.setVisibility(View.GONE);
            }
            else if (event.getTransactionType() == Common.TYPE_TRANSFER_TRANSACTION) {
                selectedType = "Transfer";
                if (ll_pattern_container.getVisibility() == View.VISIBLE)
                    ll_pattern_container.setVisibility(View.GONE);
            }
        }
        else {
            Log.d(TAG, "onEvent: else!!");
        }
    }

    @Override
    public void onAccountLoadSuccess(AccountItem accountItem) {
        mAccountItem = accountItem;

        String passedCategory = "default";
        if (mTransactionModel.getType().equals("Expense")) {
            if (ll_pattern_container.getVisibility() == View.GONE)
                ll_pattern_container.setVisibility(View.VISIBLE);
            Category expenseCategory = Common.getExpenseCategory(mTransactionModel.getCategoryId());
            selectedCategory = expenseCategory;
            passedCategory = expenseCategory.getCategoryVisibleName(this);
            categoryEdt.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, expenseCategory.getIconResourceID(), 0);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                categoryEdt.setCompoundDrawableTintList(ColorStateList.valueOf(expenseCategory.getIconColor()));
            }
        } else if (mTransactionModel.getType().equals("Income")) {
            if (ll_pattern_container.getVisibility() == View.VISIBLE)
                ll_pattern_container.setVisibility(View.GONE);
            Category incomeCategory = Common.getIncomeCategory(mTransactionModel.getCategoryId());
            selectedCategory = incomeCategory;
            passedCategory = incomeCategory.getCategoryVisibleName(this);
            categoryEdt.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, incomeCategory.getIconResourceID(), 0);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                categoryEdt.setCompoundDrawableTintList(ColorStateList.valueOf(incomeCategory.getIconColor()));
            }
        }
        String passedAccount = mAccountItem.getName();
        String passedNote = mTransactionModel.getNote();
        String passedDate = mTransactionModel.getDate();
        String passedPattern = mTransactionModel.getPattern();
        int passedAmount = (int) mTransactionModel.getAmount();

        categoryEdt.setText(passedCategory);
        accountEdt.setText(passedAccount);
        noteEdt.setText(passedNote);
        amountEdt.setText(Common.changeNumberToComma(passedAmount));
        dateEdt.setText(passedDate);
    }

    @Override
    public void onAccountLoadFailed(String message) {

    }

    @Override
    public void onTransactionUpdateSuccess(Boolean isInserted) {
        if (isInserted) {
            Toast.makeText(this, "[UPDATE!!]", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "[UPDATE Fail!!]", Toast.LENGTH_SHORT).show();
        }
    }
}
