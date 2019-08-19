package com.example.smartbudget.Ui.Home.Category;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.smartbudget.Database.ExpenseBudgetRoom.DBExpenseBudgetUtils;
import com.example.smartbudget.Database.ExpenseBudgetRoom.ExpenseBudgetItem;
import com.example.smartbudget.Database.Interface.IExpenseBudgetUpdateListener;
import com.example.smartbudget.Model.EventBus.UpdateExpenseBudgetEvent;
import com.example.smartbudget.R;
import com.example.smartbudget.Ui.Main.MainActivity;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SetExpenseBudgetActivity extends AppCompatActivity implements IExpenseBudgetUpdateListener {

    private static final String TAG = SetExpenseBudgetActivity.class.getSimpleName();

    public static final String EXTRA_EXPENSE_BUDGET_ITEM = "EXPENSE_BUDGET_ITEM";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.edt_expense_budget_amount)
    EditText edt_expense_budget_amount;
    @BindView(R.id.btn_save)
    Button btn_save;

    private String categoryId;
    private ExpenseBudgetItem mExpenseBudgetItem;
    private int passedAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_expense_budget);
        Log.d(TAG, "onCreate: started!!");
        ButterKnife.bind(this);

        if (getIntent() != null) {
            mExpenseBudgetItem = getIntent().getParcelableExtra(EXTRA_EXPENSE_BUDGET_ITEM);
            passedAmount = (int) mExpenseBudgetItem.getAmount();
            edt_expense_budget_amount.setText(String.valueOf(passedAmount));
        }

        initView();
    }

    private void initView() {
        Log.d(TAG, "initView: called!!");

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Update");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btn_save.setOnClickListener(v -> {
            mExpenseBudgetItem.setAmount(Double.parseDouble(edt_expense_budget_amount.getText().toString()));
            DBExpenseBudgetUtils.updateExpenseBudgetAmount(MainActivity.db, this, mExpenseBudgetItem);
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
    public void onExpenseBudgetUpdateSuccess(Boolean isInserted) {
        if (isInserted) {
            EventBus.getDefault().postSticky(new UpdateExpenseBudgetEvent(true));
            finish();
        }
    }
}
