package com.example.smartbudget.Transaction;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.smartbudget.R;
import com.example.smartbudget.Transaction.Dialog.CategoryDialogFragment;
import com.example.smartbudget.Transaction.Dialog.DatePickerFragment;
import com.example.smartbudget.Transaction.Dialog.IDialogSendListener;
import com.example.smartbudget.Utils.Common;

import java.util.Calendar;

public class AddTransactionActivity extends AppCompatActivity
        implements CategoryDialogFragment.OnDialogSendListener, IDialogSendListener {

    private static final String TAG = AddTransactionActivity.class.getSimpleName();
    private static final int INPUT_NOTE_REQUEST = 100;
    private static final int INPUT_AMOUNT_REQUEST = 200;

    private Toolbar mToolbar;
    private EditText categoryEdt;
    private EditText noteEdt;
    private EditText dateEdt;
    private EditText amountEdt;
    private Button saveBtn;
    private Button cancelBtn;
    private Calendar calendar;

    int _year;
    int _month;
    int _day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);

        initToolbar();
        initViews();
        handleClickEvent();

    }

    private void initToolbar() {
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Add Transaction");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initViews() {
        categoryEdt = findViewById(R.id.add_transaction_category);
        noteEdt = findViewById(R.id.add_transaction_note);
        dateEdt = findViewById(R.id.add_transaction_date);
        amountEdt = findViewById(R.id.add_transaction_amount);
        cancelBtn = findViewById(R.id.cancel_btn);
        saveBtn = findViewById(R.id.save_btn);

        calendar = Calendar.getInstance();
        _year = calendar.get(Calendar.YEAR);
        _month = calendar.get(Calendar.MONTH)+1;
        _day = calendar.get(Calendar.DAY_OF_MONTH);

        dateEdt.setText(new StringBuilder().append(_year).append("-").append(_month).append("-").append(_day));
    }

    private void handleClickEvent() {
        categoryEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogFragment = CategoryDialogFragment.newInstance();
                dialogFragment.show(getSupportFragmentManager(), "category_dialog");
            }
        });

        noteEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddTransactionActivity.this, InputNoteActivity.class);
                if (noteEdt.getText() != null) {
                    intent.putExtra(Common.EXTRA_PASS_INPUT_NOTE, noteEdt.getText().toString());
                }
                startActivityForResult(intent, INPUT_NOTE_REQUEST);
            }
        });

        dateEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogFragment = DatePickerFragment.newInstance(dateEdt.getText().toString());
                dialogFragment.show(getSupportFragmentManager(), "date picker");
            }
        });

        amountEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddTransactionActivity.this, InputAmountActivity.class);
                if (noteEdt.getText() != null) {
                    intent.putExtra(Common.EXTRA_PASS_INPUT_AMOUNT, amountEdt.getText().toString());
                }
                startActivityForResult(intent, INPUT_AMOUNT_REQUEST);
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
                // todo: save the result
                Toast.makeText(AddTransactionActivity.this, "Save Button Clicked!!",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == INPUT_NOTE_REQUEST) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    noteEdt.setText(data.getStringExtra(Common.EXTRA_INPUT_NOTE));
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
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return  true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void sendResult(String result) {
        Log.d(TAG, "sendResult: get the result: " + result);
        Toast.makeText(this, ""+result, Toast.LENGTH_SHORT).show();
        categoryEdt.setText(result);
    }

    @Override
    public void OnSendListener(String result) {
        dateEdt.setText(result);
    }
}
