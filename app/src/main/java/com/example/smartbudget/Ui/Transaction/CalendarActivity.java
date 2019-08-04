package com.example.smartbudget.Ui.Transaction;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.smartbudget.Database.DatabaseUtils;
import com.example.smartbudget.Interface.IThisMonthTransactionLoadListener;
import com.example.smartbudget.Model.TransactionModel;
import com.example.smartbudget.R;
import com.example.smartbudget.Ui.Main.MainActivity;
import com.example.smartbudget.Utils.Common;
import com.example.smartbudget.Utils.SpacesItemDecoration;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CalendarActivity extends AppCompatActivity implements IThisMonthTransactionLoadListener {

    private static final String TAG = CalendarActivity.class.getSimpleName();

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_calendar)
    RecyclerView rv_calendar;
    private CalendarAdapter mAdapter;

    private int MAX_CALENDAR_DAYS = 35;
    private List<Date> mDateList = new ArrayList<>();
    private Calendar mCalendar = Calendar.getInstance(Locale.KOREA);
    private String passedDate = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        Log.d(TAG, "onCreate: started!!");


        if (getIntent() != null) {
            passedDate = getIntent().getStringExtra("date");
            int year = Integer.parseInt(passedDate.split("-")[0]);
            int month = Integer.parseInt(passedDate.split("-")[1]);
            int dayOfMonth = Integer.parseInt(passedDate.split("-")[2]);
            mCalendar.set(Calendar.YEAR, year);
            mCalendar.set(Calendar.MONTH, month-1);
            mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            Log.d(TAG, "onCreate: passed Date: "+mCalendar.getTime());
        }

        Log.d(TAG, "onCreate: date: "+passedDate);


        initView();
    }

    private void initView() {
        Log.d(TAG, "initView: called!!");
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        setTitle(getResources().getString(R.string.toolbar_title_calendar));
        getSupportActionBar().setSubtitle(passedDate);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_close_black_24dp);
        toolbar.setTitleMarginTop(0);

        loadCalendar();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void loadCalendar() {
        Log.d(TAG, "loadCalendar: called!!");
        rv_calendar.setLayoutManager(new GridLayoutManager(this, 7));
        rv_calendar.addItemDecoration(new SpacesItemDecoration(0));

        setUpCalendar();
    }

    private void setUpCalendar() {
        Log.d(TAG, "setUpCalendar: called!!");
        mDateList.clear();

        Calendar monthCalendar = (Calendar) mCalendar.clone();
        monthCalendar.set(Calendar.DAY_OF_MONTH, 1);
        int firstDayOfMonth = monthCalendar.get(Calendar.DAY_OF_WEEK) - 1;
        Log.d(TAG, "setUpCalendar: firstDayOfMonth: " + firstDayOfMonth);
        monthCalendar.add(Calendar.DAY_OF_MONTH, -firstDayOfMonth);

        Log.d(TAG, "setUpCalendar: monthCalendar.getTime(): "+monthCalendar.getTime());

        DatabaseUtils.getThisMonthTransaction(MainActivity.mDBHelper, passedDate, this);

        while (mDateList.size() < MAX_CALENDAR_DAYS) {
            mDateList.add(monthCalendar.getTime());
            monthCalendar.add(Calendar.DAY_OF_MONTH, 1);
        }
    }

    @Override
    public void onTransactionLoadSuccess(List<TransactionModel> transactionList) {
        Log.d(TAG, "onTransactionLoadSuccess: "+transactionList.size());
        mAdapter = new CalendarAdapter(this, mDateList, mCalendar, transactionList);
        rv_calendar.setAdapter(mAdapter);
    }

    @Override
    public void onTransactionDeleteSuccess(boolean isSuccess) {

    }
}
