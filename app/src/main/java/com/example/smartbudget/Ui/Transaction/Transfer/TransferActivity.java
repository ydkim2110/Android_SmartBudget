package com.example.smartbudget.Ui.Transaction.Transfer;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.smartbudget.Database.DatabaseUtils;
import com.example.smartbudget.Interface.IUpdateAccountListener;
import com.example.smartbudget.Model.AccountModel;
import com.example.smartbudget.R;
import com.example.smartbudget.Ui.Main.MainActivity;
import com.example.smartbudget.Ui.Transaction.Add.Account.InputAccountActivity;
import com.example.smartbudget.Ui.Transaction.Add.Amount.InputAmountActivity;
import com.example.smartbudget.Utils.Common;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TransferActivity extends AppCompatActivity implements IUpdateAccountListener {

    private static final String TAG = TransferActivity.class.getSimpleName();
    private static final int INPUT_AMOUNT_REQUEST = 100;
    private static final int INPUT_FROM_ACCOUNT_REQUEST = 200;
    private static final int INPUT_TO_ACCOUNT_REQUEST = 300;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.edt_transfer_amount)
    EditText edt_transfer_amount;
    @BindView(R.id.edt_transfer_from)
    EditText edt_transfer_from;
    @BindView(R.id.edt_transfer_to)
    EditText edt_transfer_to;
    @BindView(R.id.btn_cancel)
    Button btn_cancel;
    @BindView(R.id.btn_save)
    Button btn_save;

    private AccountModel mAccountModel;

    private int fromAccountId;
    private int toAccountId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);
        Log.d(TAG, "onCreate: started!!");

        initView();
    }

    private void initView() {
        Log.d(TAG, "initView: called!!");
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.toolbar_title_transfer));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btn_save.setEnabled(false);

        edt_transfer_amount.setOnClickListener(v -> {
            Intent intent = new Intent(TransferActivity.this, InputAmountActivity.class);
            startActivityForResult(intent, INPUT_AMOUNT_REQUEST);
        });
        edt_transfer_from.setOnClickListener(v -> {
            Intent intent = new Intent(TransferActivity.this, InputAccountActivity.class);
            startActivityForResult(intent, INPUT_FROM_ACCOUNT_REQUEST);
        });
        edt_transfer_to.setOnClickListener(v -> {
            Intent intent = new Intent(TransferActivity.this, InputAccountActivity.class);
            startActivityForResult(intent, INPUT_TO_ACCOUNT_REQUEST);
        });

        btn_cancel.setOnClickListener(v -> finish());

        btn_save.setOnClickListener(v -> {
            if (fromAccountId == toAccountId) {
                Toast.makeText(this, "[Same]", Toast.LENGTH_SHORT).show();
                return;
            }

            DatabaseUtils.updateAccountsByTransfer(MainActivity.mDBHelper, Double.parseDouble(edt_transfer_amount.getText().toString()),
                    fromAccountId, toAccountId, this);
        });

        watchTextChange();
    }

    private void watchTextChange() {
        Log.d(TAG, "watchTextChange: called!!");
        edt_transfer_amount.addTextChangedListener(new TextWatcher() {
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
        edt_transfer_from.addTextChangedListener(new TextWatcher() {
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
        edt_transfer_to.addTextChangedListener(new TextWatcher() {
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

        if (!TextUtils.isEmpty(edt_transfer_amount.getText())) {
            if (!TextUtils.isEmpty(edt_transfer_from.getText())) {
                if (!TextUtils.isEmpty(edt_transfer_to.getText())) {
                    btn_save.setEnabled(true);
                }
                else {
                    btn_save.setEnabled(false);
                }
            }
            else {
                btn_save.setEnabled(false);
            }
        }
        else {
            btn_save.setEnabled(false);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == INPUT_AMOUNT_REQUEST) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    edt_transfer_amount.setText(data.getStringExtra(Common.EXTRA_INPUT_AMOUNT));
                }
            }
        }
        else if (requestCode == INPUT_FROM_ACCOUNT_REQUEST) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    mAccountModel = data.getParcelableExtra(Common.EXTRA_INPUT_ACCOUNT);
                    edt_transfer_from.setText(mAccountModel.getName());
                    fromAccountId = mAccountModel.getId();
                }
            }
        }
        else if (requestCode == INPUT_TO_ACCOUNT_REQUEST) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    mAccountModel = data.getParcelableExtra(Common.EXTRA_INPUT_ACCOUNT);
                    edt_transfer_to.setText(mAccountModel.getName());
                    toAccountId = mAccountModel.getId();
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
    public void onUpdateAccountSuccess(boolean isUpdate) {
        if (isUpdate) {
            finish();
        }
    }

    @Override
    public void onUpdateAccountFailed(String message) {

    }
}
