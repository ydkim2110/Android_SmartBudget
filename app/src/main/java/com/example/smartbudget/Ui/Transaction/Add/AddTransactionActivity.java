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
import com.example.smartbudget.Database.Interface.ITransactionDeleteListener;
import com.example.smartbudget.Database.TransactionRoom.DBTransactionUtils;
import com.example.smartbudget.Database.TransactionRoom.TransactionItem;
import com.example.smartbudget.Interface.ITransactionInsertListener;
import com.example.smartbudget.Database.Interface.ITransactionUpdateListener;
import com.example.smartbudget.Model.Category;
import com.example.smartbudget.Model.EventBus.AddTransactionEvent;
import com.example.smartbudget.Model.EventBus.CategorySelectedEvent;
import com.example.smartbudget.Model.EventBus.DeleteTransactionEvent;
import com.example.smartbudget.Model.EventBus.DeleteTransactionFromAddEvent;
import com.example.smartbudget.Model.EventBus.UpdateTransactionEvent;
import com.example.smartbudget.Model.EventBus.UpdateTransactionFromAddEvent;
import com.example.smartbudget.Model.SubCategory;
import com.example.smartbudget.Ui.Input.InputAccountActivity;
import com.example.smartbudget.Ui.Input.InputAmountActivity;
import com.example.smartbudget.Ui.Transaction.Add.Category.CategoryDialogFragment;
import com.example.smartbudget.Ui.Transaction.Add.Category.InputCategoryActivity;
import com.example.smartbudget.Ui.Transaction.Add.Date.DatePickerDialogFragment;
import com.example.smartbudget.Ui.Transaction.Add.Date.IDialogSendListener;
import com.example.smartbudget.Ui.Input.InputNoteActivity;
import com.example.smartbudget.Model.CategoryModel;
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
        IAccountLoadListener, ITransactionUpdateListener, ITransactionDeleteListener {

    private static final String TAG = AddTransactionActivity.class.getSimpleName();
    public static final String EXTRA_REQUEST_PAGE = "REQUEST_PAGE";

    private static final int INPUT_ACCOUNT_REQUEST = 100;
    private static final int INPUT_CATEGORY_REQUEST = 200;
    private static final int INPUT_NOTE_REQUEST = 300;
    private static final int INPUT_AMOUNT_REQUEST = 400;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.edt_account)
    EditText edt_account;
    @BindView(R.id.edt_category)
    EditText edt_category;
    @BindView(R.id.edt_amount)
    EditText edt_amount;
    @BindView(R.id.edt_description)
    EditText edt_description;
    @BindView(R.id.edt_date)
    EditText edt_date;
    @BindView(R.id.btn_save)
    Button btn_save;

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
        DBTransactionUtils.deleteTransactionAsync(MainActivity.db, this, mTransactionItem);
        finish();
    }

    int _year;
    int _month;
    int _day;

    private AccountItem mAccountItem;
    private TransactionItem mTransactionItem;
    private String selectedType = "";
    private Category selectedCategory = null;
    private SubCategory selectedSubCategory = null;

    private String selectedPattern = "";
    private String requestPage = "";

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
            btn_save.setText(getResources().getString(R.string.btn_update));

            TransactionItem transactionItem = getIntent().getParcelableExtra(Common.EXTRA_EDIT_TRANSACTION);
            requestPage = getIntent().getStringExtra(EXTRA_REQUEST_PAGE);

            DBAccountUtils.getAccount(MainActivity.db, transactionItem.getAccountId(), this);

            mTransactionItem = transactionItem;
            selectedType = mTransactionItem.getType();
            selectedPattern = mTransactionItem.getPattern();
        }

        handleClickEvent();

        checkNoEmptyInputs();
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

        edt_category.addTextChangedListener(new TextWatcher() {
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
    }

    private void checkInputs() {
        Log.d(TAG, "checkInputs: called!!");

        if (!TextUtils.isEmpty(edt_account.getText())) {
            if (!TextUtils.isEmpty(edt_category.getText())) {
                if (!TextUtils.isEmpty(edt_description.getText())) {
                    if (!TextUtils.isEmpty(edt_amount.getText())) {
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
    }

    @SuppressLint("ResourceAsColor")
    private void initView() {
        btn_save.setEnabled(false);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.toolbar_add_transaction));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        iv_delete.setVisibility(View.GONE);

        calendar = Calendar.getInstance();
        _year = calendar.get(Calendar.YEAR);
        _month = calendar.get(Calendar.MONTH) + 1;
        _day = calendar.get(Calendar.DAY_OF_MONTH);

        edt_date.setText(Common.dateFormat.format(calendar.getTime()));

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
        edt_account.setOnClickListener(v -> {
            Intent intent = new Intent(AddTransactionActivity.this, InputAccountActivity.class);
            if (edt_account.getText() != null) {
                intent.putExtra("accountName", edt_account.getText().toString());
            }
            startActivityForResult(intent, INPUT_ACCOUNT_REQUEST);
        });

        edt_category.setOnClickListener(v -> {
            Intent intent = new Intent(AddTransactionActivity.this, InputCategoryActivity.class);
            startActivityForResult(intent, INPUT_CATEGORY_REQUEST);
        });

        edt_description.setOnClickListener(v -> {
            Intent intent = new Intent(AddTransactionActivity.this, InputNoteActivity.class);
            if (edt_description.getText() != null) {
                intent.putExtra(Common.EXTRA_PASS_INPUT_NOTE, edt_description.getText().toString());
            }
            startActivityForResult(intent, INPUT_NOTE_REQUEST);
        });

        edt_date.setOnClickListener(v -> {
            DialogFragment dialogFragment = DatePickerDialogFragment.newInstance(edt_date.getText().toString());
            dialogFragment.show(getSupportFragmentManager(), "date picker");
        });

        edt_amount.setOnClickListener(v -> {
            Intent intent = new Intent(AddTransactionActivity.this, InputAmountActivity.class);
            if (edt_amount.getText() != null) {
                intent.putExtra(Common.EXTRA_PASS_INPUT_AMOUNT, edt_amount.getText().toString());
            }
            startActivityForResult(intent, INPUT_AMOUNT_REQUEST);
        });

        btn_save.setOnClickListener(v -> {

            TransactionItem transactionItem = new TransactionItem();

            if (btn_save.getText().toString().toLowerCase().equals("save")) {

                transactionItem.setDescription(edt_description.getText().toString());
                transactionItem.setAmount(Double.parseDouble(Common.removeComma(edt_amount.getText().toString())));
                transactionItem.setType(selectedType);
                transactionItem.setPattern(selectedPattern);
                transactionItem.setDate(DateHelper.changeDateToString(DateHelper.changeStringToDate(edt_date.getText().toString())));
                transactionItem.setCategoryId(selectedCategory.getCategoryID());
                transactionItem.setSubCategoryId(selectedSubCategory != null ? selectedSubCategory.getId() : selectedCategory.getCategoryID());
                transactionItem.setAccountId(mAccountItem.getId());

                if (selectedType.equals("Expense") || selectedType.equals("Income")) {
                    DBTransactionUtils.insertTransactionAsync(MainActivity.db, AddTransactionActivity.this, transactionItem);
                } else if (selectedType.equals("Transfer")) {
                    transactionItem.setToAccount(0);
                    // todo: transfer

                }
            }
            else if (btn_save.getText().toString().toLowerCase().equals("update")) {

                transactionItem.setId(mTransactionItem.getId());
                transactionItem.setDescription(edt_description.getText().toString());
                transactionItem.setAmount(Double.parseDouble(Common.removeComma(edt_amount.getText().toString())));
                transactionItem.setType(selectedType);
                transactionItem.setPattern(selectedPattern);
                transactionItem.setDate(DateHelper.changeDateToString(DateHelper.changeStringToDate(edt_date.getText().toString())));
                transactionItem.setCategoryId(selectedCategory.getCategoryID());
                transactionItem.setSubCategoryId(selectedSubCategory != null ? selectedSubCategory.getId() : selectedCategory.getCategoryID());
                transactionItem.setAccountId(mAccountItem.getId());

                if (selectedType.equals(getResources().getString(R.string.expense)) || selectedType.equals(getResources().getString(R.string.income))) {
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
                    edt_account.setText(mAccountItem.getName());
                }
            }
        } else if (requestCode == INPUT_NOTE_REQUEST) {
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
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void OnSendListener(String result) {
        edt_date.setText(result);
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
                edt_category.setText(event.getCategory().getCategoryVisibleName(getApplicationContext()));
                edt_category.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, event.getCategory().getIconResourceID(), 0);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    edt_category.setCompoundDrawableTintList(ColorStateList.valueOf(event.getCategory().getIconColor()));
                }
            } else if (event.getCategoryType() == Common.TYPE_SUB_CATEGORY) {
                selectedSubCategory = event.getSubCategory();
                edt_category.setText(event.getSubCategory().getCategoryVisibleName(getApplicationContext()));
                edt_category.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, event.getSubCategory().getIconResourceID(), 0);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    edt_category.setCompoundDrawableTintList(ColorStateList.valueOf(event.getSubCategory().getIconColor()));
                }
            }

            if (event.getTransactionType() == Common.TYPE_EXPENSE_TRANSACTION) {
                selectedType = "Expense";
                if (ll_pattern_container.getVisibility() == View.GONE)
                    ll_pattern_container.setVisibility(View.VISIBLE);
            } else if (event.getTransactionType() == Common.TYPE_INCOME_TRANSACTION) {
                selectedType = "Income";
                if (ll_pattern_container.getVisibility() == View.VISIBLE)
                    ll_pattern_container.setVisibility(View.GONE);
            } else if (event.getTransactionType() == Common.TYPE_TRANSFER_TRANSACTION) {
                selectedType = "Transfer";
                if (ll_pattern_container.getVisibility() == View.VISIBLE)
                    ll_pattern_container.setVisibility(View.GONE);
            }
        } else {
            Log.d(TAG, "onEvent: else!!");
        }
    }

    @Override
    public void onAccountLoadSuccess(AccountItem accountItem) {
        mAccountItem = accountItem;

        String passedCategory = "default";
        if (mTransactionItem.getType().equals("Expense")) {
            if (ll_pattern_container.getVisibility() == View.GONE)
                ll_pattern_container.setVisibility(View.VISIBLE);
            Category expenseCategory = Common.getExpenseCategory(mTransactionItem.getCategoryId());
            selectedCategory = expenseCategory;
            passedCategory = expenseCategory.getCategoryVisibleName(this);
            edt_category.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, expenseCategory.getIconResourceID(), 0);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                edt_category.setCompoundDrawableTintList(ColorStateList.valueOf(expenseCategory.getIconColor()));
            }
        } else if (mTransactionItem.getType().equals("Income")) {
            if (ll_pattern_container.getVisibility() == View.VISIBLE)
                ll_pattern_container.setVisibility(View.GONE);
            Category incomeCategory = Common.getIncomeCategory(mTransactionItem.getCategoryId());
            selectedCategory = incomeCategory;
            passedCategory = incomeCategory.getCategoryVisibleName(this);
            edt_category.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, incomeCategory.getIconResourceID(), 0);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                edt_category.setCompoundDrawableTintList(ColorStateList.valueOf(incomeCategory.getIconColor()));
            }
        }
        String passedAccount = mAccountItem.getName();
        String passedNote = mTransactionItem.getDescription();
        String passedDate = mTransactionItem.getDate();
        String passedPattern = mTransactionItem.getPattern();
        int passedAmount = (int) mTransactionItem.getAmount();

        edt_category.setText(passedCategory);
        edt_account.setText(passedAccount);
        edt_description.setText(passedNote);
        edt_amount.setText(Common.changeNumberToComma(passedAmount));
        edt_date.setText(passedDate);
    }

    @Override
    public void onAccountLoadFailed(String message) {
        Log.d(TAG, "onAccountLoadFailed: called!!");
    }

    @Override
    public void onTransactionUpdateSuccess(Boolean isInserted) {
        Log.d(TAG, "onTransactionUpdateSuccess: called!!");
        if (isInserted) {
            if (requestPage.equals("HomeFragment")) {
                EventBus.getDefault().post(new UpdateTransactionEvent(true));
            }
            else if (requestPage.equals("TransactionActivity")) {
                EventBus.getDefault().postSticky(new UpdateTransactionFromAddEvent(true));
            }
        } else {
            Toast.makeText(this, "Update Failed. Try again!", Toast.LENGTH_SHORT).show();
        }
        finish();
    }

    @Override
    public void onTransactionDeleteSuccess(boolean isDeleted) {
        Log.d(TAG, "onTransactionDeleteSuccess: called!!");
        if (isDeleted) {
            if (requestPage.equals("HomeFragment")) {
                EventBus.getDefault().post(new DeleteTransactionEvent(true));
            }
            else if (requestPage.equals("TransactionActivity")) {
                EventBus.getDefault().postSticky(new DeleteTransactionFromAddEvent(true));
            }
        } else {
            Toast.makeText(this, "Delete Failed. Try again!", Toast.LENGTH_SHORT).show();
        }
        finish();
    }

    @Override
    public void onTransactionDeleteFailed(String message) {
        Log.d(TAG, "onTransactionDeleteSuccess: called!!");
    }
}
