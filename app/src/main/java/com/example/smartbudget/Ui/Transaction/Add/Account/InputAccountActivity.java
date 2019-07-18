package com.example.smartbudget.Ui.Transaction.Add.Account;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartbudget.Ui.Account.IAccountLoadListener;
import com.example.smartbudget.Database.DatabaseUtils;
import com.example.smartbudget.Model.AccountModel;
import com.example.smartbudget.Ui.Main.MainActivity;
import com.example.smartbudget.R;
import com.example.smartbudget.Utils.Common;

import java.util.ArrayList;
import java.util.List;

public class InputAccountActivity extends AppCompatActivity implements IAccountLoadListener, InputAccountAdapter.SaveButtonListener {

    private static final String TAG = InputAccountActivity.class.getSimpleName();

    private RecyclerView mRecyclerView;

    private TextView mNoAccountMessage;
    private Button mSaveBtn;
    private Button mCancelBtn;

    private AccountModel selectedAccount = null;
    private String passedAccountName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_account);
        Log.d(TAG, "onCreate: started!!");

        DatabaseUtils.getAllAccount(MainActivity.mDBHelper, this);

        // todo: 이전 선택된 아이템은 선택되어졌다고 표시하기
        if (getIntent() != null) {
            passedAccountName = getIntent().getStringExtra("accountName");
        }

        initToolbar();
        initView();
        handleClickEvent();

    }

    private void handleClickEvent() {
        Log.d(TAG, "handleClickEvent: called!!");

        mCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(InputAccountActivity.this, "selectedAccount: " + selectedAccount.getAccount_name(),
                        Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.putExtra(Common.EXTRA_INPUT_ACCOUNT, selectedAccount);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    private void initView() {
        Log.d(TAG, "initView: called!!");

        mNoAccountMessage = findViewById(R.id.no_account_message);
        mSaveBtn = findViewById(R.id.input_save_btn);
        mSaveBtn.setEnabled(false);
        mCancelBtn = findViewById(R.id.input_cancel_btn);

        mRecyclerView = findViewById(R.id.input_account_recyclerview);
        mRecyclerView.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(layoutManager);
    }

    private void initToolbar() {
        Log.d(TAG, "initToolbar: called!!");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add Account");
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

    @Override
    public void onAccountLoadSuccess(List<AccountModel> accountList) {
        if (accountList == null) {
            mNoAccountMessage.setVisibility(View.VISIBLE);
            accountList = new ArrayList<>();
            InputAccountAdapter adapter = new InputAccountAdapter(accountList, this);
            mRecyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        } else {
            mNoAccountMessage.setVisibility(View.GONE);
            InputAccountAdapter adapter = new InputAccountAdapter(accountList, this);
            mRecyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onAccountDeleteSuccess(boolean isSuccess) {

    }

    @Override
    public void onUpdate(boolean status, AccountModel accountModel) {
        if (status) {
            mSaveBtn.setEnabled(true);
            selectedAccount = accountModel;
        }
    }
}
