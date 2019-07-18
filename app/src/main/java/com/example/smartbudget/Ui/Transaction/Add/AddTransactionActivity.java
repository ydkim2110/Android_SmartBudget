package com.example.smartbudget.Ui.Transaction.Add;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.smartbudget.Ui.Transaction.Add.Account.InputAccountActivity;
import com.example.smartbudget.Ui.Transaction.Add.Amount.InputAmountActivity;
import com.example.smartbudget.Ui.Transaction.Add.Category.CategoryDialogFragment;
import com.example.smartbudget.Ui.Transaction.Add.Date.DatePickerDialogFragment;
import com.example.smartbudget.Ui.Transaction.Add.Date.IDialogSendListener;
import com.example.smartbudget.Ui.Transaction.Add.Note.InputNoteActivity;
import com.example.smartbudget.Database.DatabaseUtils;
import com.example.smartbudget.Model.AccountModel;
import com.example.smartbudget.Model.CategoryModel;
import com.example.smartbudget.Model.TransactionModel;
import com.example.smartbudget.Ui.Home.Transaction;
import com.example.smartbudget.Ui.Main.MainActivity;
import com.example.smartbudget.R;
import com.example.smartbudget.Utils.Common;

import java.util.Calendar;
import java.util.Date;

public class AddTransactionActivity extends AppCompatActivity
        implements CategoryDialogFragment.OnDialogSendListener, IDialogSendListener, ITransactionInsertListener {

    private static final String TAG = AddTransactionActivity.class.getSimpleName();
    private static final int INPUT_ACCOUNT_REQUEST = 100;
    private static final int INPUT_NOTE_REQUEST = 200;
    private static final int INPUT_AMOUNT_REQUEST = 300;
    private static final int REQUEST_TAKE_PICTURE = 400;

    private Toolbar mToolbar;
    private RadioGroup mRadioGroup;
    private RadioButton mRBIncome, mRBExpense, mRBTransfer;
    private EditText accountEdt;
    private EditText categoryEdt;
    private EditText descriptionEdt;
    private EditText dateEdt;
    private EditText amountEdt;
    private ImageView photoImage;
    private Button saveBtn;
    private Button cancelBtn;
    private Calendar calendar;

    int _year;
    int _month;
    int _day;

    private AccountModel requestedAccountModel;
    private CategoryModel requestedCategoryModel;
    private String selectedType = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);

        initToolbar();
        initViews();

        if (getIntent().getParcelableExtra(Common.EXTRA_EDIT_TRANSACTION) != null) {
            Transaction transaction = getIntent().getParcelableExtra(Common.EXTRA_EDIT_TRANSACTION);
            String passedCategory = transaction.getCategory();
            String passedDescription = transaction.getDescription();
            int passedAmount = transaction.getAmount();
            String passedDate = transaction.getDate();

            getSupportActionBar().setTitle("Edit Transaction");
            saveBtn.setText("Update");

            categoryEdt.setText(passedCategory);
            descriptionEdt.setText(passedDescription);
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

        descriptionEdt.addTextChangedListener(new TextWatcher() {
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
                if (!TextUtils.isEmpty(descriptionEdt.getText())) {
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

    private void initToolbar() {
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Add Transaction");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initViews() {
        mRadioGroup = findViewById(R.id.radio_group);
        mRBIncome = findViewById(R.id.rb_income);
        mRBExpense = findViewById(R.id.rb_expense);
        mRBTransfer = findViewById(R.id.rb_transfer);
        accountEdt = findViewById(R.id.add_transaction_account);
        categoryEdt = findViewById(R.id.add_transaction_category);
        descriptionEdt = findViewById(R.id.add_transaction_description);
        dateEdt = findViewById(R.id.add_transaction_date);
        amountEdt = findViewById(R.id.add_transaction_amount);
        photoImage = findViewById(R.id.add_transaction_photo);
        cancelBtn = findViewById(R.id.cancel_btn);
        saveBtn = findViewById(R.id.save_btn);
        saveBtn.setEnabled(false);

        calendar = Calendar.getInstance();
        _year = calendar.get(Calendar.YEAR);
        _month = calendar.get(Calendar.MONTH) + 1;
        _day = calendar.get(Calendar.DAY_OF_MONTH);

        dateEdt.setText(new StringBuilder().append(_year).append("-").append(_month).append("-").append(_day));

        mRadioGroup.check(R.id.rb_expense);
    }

    private void handleClickEvent() {
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_income:
                        selectedType = "Income";
                        break;
                    case R.id.rb_expense:
                        selectedType = "Expense";
                        break;
                    case R.id.rb_transfer:
                        selectedType = "Transfer";
                        break;
                    default:
                        return;
                }
            }
        });
        accountEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddTransactionActivity.this, InputAccountActivity.class);
                if (accountEdt.getText() != null) {
                    intent.putExtra("accountName", accountEdt.getText().toString());
                }
                startActivityForResult(intent, INPUT_ACCOUNT_REQUEST);
            }
        });

        categoryEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogFragment = CategoryDialogFragment.newInstance();
                dialogFragment.show(getSupportFragmentManager(), "category_dialog");
            }
        });

        descriptionEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddTransactionActivity.this, InputNoteActivity.class);
                if (descriptionEdt.getText() != null) {
                    intent.putExtra(Common.EXTRA_PASS_INPUT_NOTE, descriptionEdt.getText().toString());
                }
                startActivityForResult(intent, INPUT_NOTE_REQUEST);
            }
        });

        dateEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogFragment = DatePickerDialogFragment.newInstance(dateEdt.getText().toString());
                dialogFragment.show(getSupportFragmentManager(), "date picker");
            }
        });

        amountEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddTransactionActivity.this, InputAmountActivity.class);
                if (descriptionEdt.getText() != null) {
                    intent.putExtra(Common.EXTRA_PASS_INPUT_AMOUNT, amountEdt.getText().toString());
                }
                startActivityForResult(intent, INPUT_AMOUNT_REQUEST);
            }
        });

        photoImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Choose..."), REQUEST_TAKE_PICTURE);

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
                TransactionModel transactionModel = new TransactionModel();
                transactionModel.setTransaction_description(descriptionEdt.getText().toString());
                transactionModel.setTransaction_amount(Double.parseDouble(amountEdt.getText().toString()));
                transactionModel.setTransaction_type(selectedType);
                transactionModel.setTransaction_date(new Date());
                transactionModel.setCategory_id(requestedCategoryModel.getId());
                transactionModel.setAccount_id(requestedAccountModel.getId());
                transactionModel.setTo_account(0);

                DatabaseUtils.insertTransactionAsync(MainActivity.mDBHelper, AddTransactionActivity.this, transactionModel);
            }
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
        }
        else if (requestCode == INPUT_NOTE_REQUEST) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    descriptionEdt.setText(data.getStringExtra(Common.EXTRA_INPUT_NOTE));
                }
            }
        }
        else if (requestCode == INPUT_AMOUNT_REQUEST) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    amountEdt.setText(data.getStringExtra(Common.EXTRA_INPUT_AMOUNT));
                }
            }
        }
        else if (requestCode == REQUEST_TAKE_PICTURE) {
            if (resultCode == RESULT_OK) {
                //data.getData returns the content URI for the selected Image
                Uri selectedImage = data.getData();
                photoImage.setImageURI(selectedImage);
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
    public void sendResult(CategoryModel categoryModel) {
        requestedCategoryModel = categoryModel;
        Log.d(TAG, "sendResult: get the result: " + requestedCategoryModel.getCategory_name());
        Toast.makeText(this, "" + requestedCategoryModel.getCategory_name(), Toast.LENGTH_SHORT).show();
        categoryEdt.setText(requestedCategoryModel.getCategory_name());
    }

    @Override
    public void OnSendListener(String result) {
        dateEdt.setText(result);
    }

    @Override
    public void onTransactionInsertSuccess(Boolean isInserted) {
        if (isInserted) {
            Toast.makeText(this, "Insert Success!!", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

}
