package com.example.smartbudget.Transaction;

import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.smartbudget.Database.DBHelper;
import com.example.smartbudget.Database.DatabaseUtils;
import com.example.smartbudget.R;
import com.example.smartbudget.Transaction.Dialog.CategoryDialogFragment;

import java.util.Calendar;
import java.util.List;

public class AddTransactionActivity extends AppCompatActivity
        implements CategoryDialogFragment.OnDialogSendListener {

    private static final String TAG = AddTransactionActivity.class.getSimpleName();

    private Toolbar mToolbar;
    private EditText edt_category;
    private EditText edt_date;
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
        edt_category = findViewById(R.id.add_transaction_category);
        edt_date = findViewById(R.id.add_transaction_date);

        calendar = Calendar.getInstance();
        _year = calendar.get(Calendar.YEAR);
        _month = calendar.get(Calendar.MONTH)+1;
        _day = calendar.get(Calendar.DAY_OF_MONTH);

        edt_date.setText(new StringBuilder().append(_year).append("-").append(_month).append("-").append(_day));
    }

    private void handleClickEvent() {
        edt_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogFragment = CategoryDialogFragment.newInstance();
                dialogFragment.show(getSupportFragmentManager(), "category_dialog");
            }
        });

        edt_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddTransactionActivity.this, "setOnClickListener", Toast.LENGTH_SHORT).show();
            }
        });
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
        edt_category.setText(result);
    }
}
