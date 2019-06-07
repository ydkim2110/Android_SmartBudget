package com.example.smartbudget.Transaction;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.smartbudget.R;

import java.util.Calendar;

public class AddTransactionActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText edt_date;
    private Calendar calendar;

    int _year;
    int _month;
    int _day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add Transaction");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edt_date = findViewById(R.id.add_transaction_date);

        calendar = Calendar.getInstance();
        _year = calendar.get(Calendar.YEAR);
        _month = calendar.get(Calendar.MONTH)+1;
        _day = calendar.get(Calendar.DAY_OF_MONTH);

        edt_date.setText(new StringBuilder().append(_year).append("-").append(_month).append("-").append(_day));

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
}
