package com.example.smartbudget.Ui.Budget;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.smartbudget.R;
import com.example.smartbudget.Ui.Transaction.Add.Date.DatePickerDialogFragment;
import com.example.smartbudget.Ui.Transaction.Add.Date.IDialogSendListener;
import com.example.smartbudget.Utils.Common;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddBudgetActivity extends AppCompatActivity implements IDialogSendListener {

    private static final String TAG = AddBudgetActivity.class.getSimpleName();

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.edt_start_date)
    EditText edt_start_date;
    @BindView(R.id.edt_end_date)
    EditText edt_end_date;

    private Calendar mCalendar = Calendar.getInstance();

    private boolean isStartDate = false;

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
        setTitle(getResources().getString(R.string.toolbar_title_add_budget));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        edt_start_date.setText(Common.dateFormat.format(mCalendar.getTime()));
        mCalendar.add(Calendar.MONTH, 1);
        edt_end_date.setText(Common.dateFormat.format(mCalendar.getTime()));

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
}
