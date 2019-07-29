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
import android.widget.TextView;

import com.example.smartbudget.Model.Category;
import com.example.smartbudget.Model.EventBus.AddTransactionEvent;
import com.example.smartbudget.Model.EventBus.CategorySelectedEvent;
import com.example.smartbudget.Model.SubCategory;
import com.example.smartbudget.Ui.Transaction.Add.Account.InputAccountActivity;
import com.example.smartbudget.Ui.Transaction.Add.Amount.InputAmountActivity;
import com.example.smartbudget.Ui.Transaction.Add.Category.CategoryDialogFragment;
import com.example.smartbudget.Ui.Transaction.Add.Category.InputCategoryActivity;
import com.example.smartbudget.Ui.Transaction.Add.Date.DatePickerDialogFragment;
import com.example.smartbudget.Ui.Transaction.Add.Date.IDialogSendListener;
import com.example.smartbudget.Ui.Transaction.Add.Note.InputNoteActivity;
import com.example.smartbudget.Database.DatabaseUtils;
import com.example.smartbudget.Model.AccountModel;
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

public class AddTransactionActivity extends AppCompatActivity
        implements CategoryDialogFragment.OnDialogSendListener, IDialogSendListener, ITransactionInsertListener {

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

    int _year;
    int _month;
    int _day;

    private AccountModel requestedAccountModel;
    private String selectedType = "";
    private Category selectedCategory = null;
    private SubCategory selectedSubCategory = null;

    private String selectedPattern = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);
        Log.d(TAG, "onCreate: started!!");

        ButterKnife.bind(this);

        initView();

        if (getIntent().getParcelableExtra(Common.EXTRA_EDIT_TRANSACTION) != null) {

            TransactionModel transactionModel = getIntent().getParcelableExtra(Common.EXTRA_EDIT_TRANSACTION);

            String passedCategory = String.valueOf(transactionModel.getCategory_id());
            String passedAccount = String.valueOf(transactionModel.getAccount_id());
            String passedNote = transactionModel.getTransaction_note();
            String passedDate = transactionModel.getTransaction_date();
            String passedPattern = transactionModel.getTransaction_pattern();
            int passedAmount = (int) transactionModel.getTransaction_amount();

            Log.d(TAG, "onCreate: passedDate: "+passedDate);
            Log.d(TAG, "onCreate: passedPattern: "+passedPattern);

            getSupportActionBar().setTitle(getResources().getString(R.string.toolbar_edit_transaction));
            saveBtn.setText(getResources().getString(R.string.btn_update));

            Log.d(TAG, "onCreate: passedCategory: "+passedCategory);

            accountEdt.setText(passedAccount);
            categoryEdt.setText(passedCategory);
            noteEdt.setText(passedNote);
            amountEdt.setText(Common.changeNumberToComma(passedAmount));
            dateEdt.setText(passedDate);
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

        accountEdt = findViewById(R.id.add_transaction_account);
        categoryEdt = findViewById(R.id.add_transaction_category);
        noteEdt = findViewById(R.id.add_transaction_description);
        dateEdt = findViewById(R.id.add_transaction_date);
        amountEdt = findViewById(R.id.add_transaction_amount);
        cancelBtn = findViewById(R.id.cancel_btn);
        saveBtn = findViewById(R.id.save_btn);
        saveBtn.setEnabled(false);

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
            TransactionModel transactionModel = new TransactionModel();
            transactionModel.setTransaction_note(noteEdt.getText().toString());
            transactionModel.setTransaction_amount(Double.parseDouble(Common.removeComma(amountEdt.getText().toString())));
            transactionModel.setTransaction_type(selectedType);
            transactionModel.setTransaction_pattern(selectedPattern);
            transactionModel.setTransaction_date(DateHelper.changeDateToString(DateHelper.changeStringToDate(dateEdt.getText().toString())));
            transactionModel.setCategory_id(selectedCategory.getCategoryID());
            transactionModel.setAccount_id(requestedAccountModel.getId());
            transactionModel.setTo_account(0);

            DatabaseUtils.insertTransactionAsync(MainActivity.mDBHelper, AddTransactionActivity.this, transactionModel);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == INPUT_ACCOUNT_REQUEST) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    requestedAccountModel = data.getParcelableExtra(Common.EXTRA_INPUT_ACCOUNT);
                    Log.d(TAG, "onActivityResult: data: " + requestedAccountModel.getAccount_name());
                    accountEdt.setText(requestedAccountModel.getAccount_name());
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
            } else if (event.getCategoryType() == Common.TYPE_SUB_CATEGORY) {
                selectedSubCategory = event.getSubCategory();
                categoryEdt.setText(event.getSubCategory().getCategoryVisibleName(getApplicationContext()));
                categoryEdt.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, event.getSubCategory().getIconResourceID(), 0);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    categoryEdt.setCompoundDrawableTintList(ColorStateList.valueOf(event.getSubCategory().getIconColor()));
                }
            }

            if (event.getTransactionType() == Common.TYPE_EXPENSE_TRANSACTION) {
                selectedType = "Expense";
            } else if (event.getTransactionType() == Common.TYPE_INCOME_TRANSACTION) {
                selectedType = "Income";
            } else if (event.getTransactionType() == Common.TYPE_TRANSFER_TRANSACTION) {
                selectedType = "Transfer";
            }
        } else {
            Log.d(TAG, "onEvent: else!!");
        }
    }
}
