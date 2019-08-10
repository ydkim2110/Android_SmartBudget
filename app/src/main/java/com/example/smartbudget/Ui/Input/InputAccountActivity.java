package com.example.smartbudget.Ui.Input;

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

import com.example.smartbudget.Interface.IAccountsLoadListener;
import com.example.smartbudget.Database.DatabaseUtils;
import com.example.smartbudget.Model.AccountModel;
import com.example.smartbudget.Ui.Main.MainActivity;
import com.example.smartbudget.R;
import com.example.smartbudget.Utils.Common;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InputAccountActivity extends AppCompatActivity implements IAccountsLoadListener, InputAccountAdapter.SaveButtonListener {

    private static final String TAG = InputAccountActivity.class.getSimpleName();

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_input_account)
    RecyclerView rv_input_account;
    @BindView(R.id.btn_cancel)
    Button btn_cancel;
    @BindView(R.id.btn_save)
    Button btn_save;
    @BindView(R.id.tv_no_account_message)
    TextView tv_no_account_message;

    private AccountModel selectedAccount = null;
    private String passedAccountName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_account);
        Log.d(TAG, "onCreate: started!!");

        DatabaseUtils.getAllAccounts(MainActivity.mDBHelper, this);

        // todo: 이전 선택된 아이템은 선택되어졌다고 표시하기
        if (getIntent() != null) {
            passedAccountName = getIntent().getStringExtra("accountName");
        }

        initView();

    }

    private void initView() {
        Log.d(TAG, "initView: called!!");
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add Account");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rv_input_account.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        rv_input_account.setLayoutManager(layoutManager);

        btn_cancel.setOnClickListener(v -> finish());

        btn_save.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.putExtra(Common.EXTRA_INPUT_ACCOUNT, selectedAccount);
            setResult(RESULT_OK, intent);
            finish();
        });
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
    public void onAccountsLoadSuccess(List<AccountModel> accountList) {
        if (accountList == null) {
            tv_no_account_message.setVisibility(View.VISIBLE);
            accountList = new ArrayList<>();
            InputAccountAdapter adapter = new InputAccountAdapter(accountList, this);
            rv_input_account.setAdapter(adapter);
        }
        else {
            tv_no_account_message.setVisibility(View.GONE);
            InputAccountAdapter adapter = new InputAccountAdapter(accountList, this);
            rv_input_account.setAdapter(adapter);
        }
    }

    @Override
    public void onAccountDeleteSuccess(boolean isSuccess) {

    }

    @Override
    public void onUpdate(boolean status, AccountModel accountModel) {
        if (status) {
            rv_input_account.setEnabled(true);
            selectedAccount = accountModel;
        }
    }
}
