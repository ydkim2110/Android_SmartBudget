package com.example.smartbudget.AddTransaction.Amount;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.smartbudget.AddTransaction.Account.InputAccountActivity;
import com.example.smartbudget.R;
import com.example.smartbudget.Utils.Common;

public class InputAmountActivity extends AppCompatActivity {

    private static final String TAG = InputAmountActivity.class.getSimpleName();

    private EditText mInputAmount;
    private Button mSaveBtn;
    private Button mCancelBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_amount);
        Log.d(TAG, "onCreate: started!!");

        initToolbar();
        initView();
        handleClickEvent();
    }

    private void handleClickEvent() {
        mCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(mInputAmount.getText())) {
                    Toast.makeText(InputAmountActivity.this, "내용을 입력하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent goBackIntent = new Intent();
                goBackIntent.putExtra(Common.EXTRA_INPUT_AMOUNT, mInputAmount.getText().toString());
                setResult(RESULT_OK, goBackIntent);
                finish();
            }
        });
    }

    private void initView() {
        Log.d(TAG, "initView: called!!");
        mInputAmount = findViewById(R.id.input_amount_edt);
        mSaveBtn = findViewById(R.id.input_amount_save_btn);
        mCancelBtn = findViewById(R.id.input_amount_cancel_btn);
    }

    private void initToolbar() {
        Log.d(TAG, "initToolbar: called!!");
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add Amount");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
