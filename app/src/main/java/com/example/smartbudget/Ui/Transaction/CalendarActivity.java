package com.example.smartbudget.Ui.Transaction;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.smartbudget.Database.DatabaseUtils;
import com.example.smartbudget.Database.Interface.IThisMonthTransactionsLoadListener;
import com.example.smartbudget.Database.TransactionRoom.DBTransactionUtils;
import com.example.smartbudget.Database.TransactionRoom.TransactionItem;
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

public class CalendarActivity extends AppCompatActivity implements IThisMonthTransactionsLoadListener {

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
    private Date displayDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        Log.d(TAG, "onCreate: started!!");


        if (getIntent() != null) {
            passedDate = getIntent().getStringExtra("date");

            try {
                displayDate = Common.dateFormat.parse(passedDate);;
            } catch (ParseException e) {
                e.printStackTrace();
                displayDate = new Date();
            }

            int year = Integer.parseInt(passedDate.split("-")[0]);
            int month = Integer.parseInt(passedDate.split("-")[1]);
            int dayOfMonth = Integer.parseInt(passedDate.split("-")[2]);
            mCalendar.set(Calendar.YEAR, year);
            mCalendar.set(Calendar.MONTH, month-1);
            mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            Log.d(TAG, "onCreate: passed Date: "+mCalendar.getTime());

        }

        initView();
    }

    private void initView() {
        Log.d(TAG, "initView: called!!");
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        setTitle(getResources().getString(R.string.toolbar_title_calendar));
        getSupportActionBar().setSubtitle(Common.yearmonthDateFormate.format(displayDate));
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

        DBTransactionUtils.getThisMonthTransactions(MainActivity.db, passedDate, this);

        while (mDateList.size() < MAX_CALENDAR_DAYS) {
            mDateList.add(monthCalendar.getTime());
            monthCalendar.add(Calendar.DAY_OF_MONTH, 1);
        }
    }

    @Override
    public void onThisMonthTransactionsLoadSuccess(List<TransactionItem> transactionItemList) {
        Log.d(TAG, "onThisMonthTransactionsLoadSuccess: called!!");
            mAdapter = new CalendarAdapter(this, mDateList, mCalendar, transactionItemList);
            rv_calendar.setAdapter(mAdapter);
    }

    @Override
    public void onThisMonthTransactionsLoadFailed(String message) {
        Log.d(TAG, "onThisMonthTransactionsLoadFailed: called!!");
        Toast.makeText(this, "[Failed!!]", Toast.LENGTH_SHORT).show();
    }
}
