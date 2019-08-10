package com.example.smartbudget.Ui.Budget;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.smartbudget.Database.DBBudgetUtils;
import com.example.smartbudget.Database.DatabaseUtils;
import com.example.smartbudget.Interface.IBudgetLoadListener;
import com.example.smartbudget.Interface.IDBInsertListener;
import com.example.smartbudget.Model.AccountModel;
import com.example.smartbudget.Model.BudgetModel;
import com.example.smartbudget.R;
import com.example.smartbudget.Ui.Input.InputAccountActivity;
import com.example.smartbudget.Ui.Input.InputAmountActivity;
import com.example.smartbudget.Ui.Main.MainActivity;
import com.example.smartbudget.Ui.Transaction.Add.AddTransactionActivity;
import com.example.smartbudget.Ui.Transaction.Add.Date.DatePickerDialogFragment;
import com.example.smartbudget.Ui.Transaction.Add.Date.IDialogSendListener;
import com.example.smartbudget.Ui.Transaction.Add.Note.InputNoteActivity;
import com.example.smartbudget.Utils.Common;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddBudgetActivity extends AppCompatActivity implements IDialogSendListener, IDBInsertListener {

    private static final String TAG = AddBudgetActivity.class.getSimpleName();
    private static final int INPUT_ACCOUNT_REQUEST = 100;
    private static final int INPUT_NOTE_REQUEST = 200;
    private static final int INPUT_AMOUNT_REQUEST = 300;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
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

    private Calendar mCalendar = Calendar.getInstance();

    private boolean isStartDate = false;

    private AccountModel mAccountModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_budget);
        Log.d(TAG, "onCreate: started!!");

        initView();
    }

    private void initView() {
        Log.d(TAG, "initView: called!!");
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        setTitle("Add Budget");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        edt_start_date.setText(Common.dateFormat.format(mCalendar.getTime()));
        mCalendar.add(Calendar.MONTH, 1);
        edt_end_date.setText(Common.dateFormat.format(mCalendar.getTime()));

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
            BudgetModel budgetModel = new BudgetModel();

            budgetModel.setDescription(edt_description.getText().toString());
            budgetModel.setAmount(Double.parseDouble(edt_amount.getText().toString()));
            budgetModel.setStartDate(edt_start_date.getText().toString());
            budgetModel.setEndDate(edt_end_date.getText().toString());
            budgetModel.setAccountId(mAccountModel.getId());

            DBBudgetUtils.insertBudgetAsync(MainActivity.mDBHelper, this, budgetModel);

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == INPUT_ACCOUNT_REQUEST) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    mAccountModel = data.getParcelableExtra(Common.EXTRA_INPUT_ACCOUNT);
                    edt_account.setText(mAccountModel.getName());
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

    @Override
    public void OnSendListener(String result) {
        if (isStartDate)
            edt_start_date.setText(result);
        else
            edt_end_date.setText(result);
    }

    @Override
    public void onDBInsertSuccess(Boolean isInserted) {
        Log.d(TAG, "onDBInsertSuccess: called!!");
        Toast.makeText(this, "[Success!!]", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onDBInsertFailed(String message) {
        Log.d(TAG, "onDBInsertFailed: called!!");
    }
}
