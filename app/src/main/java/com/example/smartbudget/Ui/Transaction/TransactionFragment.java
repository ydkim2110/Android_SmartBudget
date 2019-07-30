package com.example.smartbudget.Ui.Transaction;


import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.smartbudget.Database.DatabaseUtils;
import com.example.smartbudget.Interface.IThisMonthTransactionLoadListener;
import com.example.smartbudget.Model.EventBus.CalendarToggleEvent;
import com.example.smartbudget.Model.TransactionModel;
import com.example.smartbudget.R;
import com.example.smartbudget.Ui.Main.MainActivity;
import com.example.smartbudget.Utils.Common;
import com.example.smartbudget.Utils.SpacesItemDecoration;
import com.google.android.material.tabs.TabLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TransactionFragment extends Fragment implements IThisMonthTransactionLoadListener {

    private static final String TAG = TransactionFragment.class.getSimpleName();

    private static TransactionFragment instance;

    public static TransactionFragment getInstance() {
        if (instance == null) {
            instance = new TransactionFragment();
        }
        return instance;
    }

    public TransactionFragment() {
    }

    private LinearLayout list_container;
    private LinearLayout calendar_container;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private RecyclerView rv_calendar;
    private RecyclerView.LayoutManager mLayoutManager;
    private CalendarAdapter mAdapter;

    private static final int MAX_CALENDAR_DAYS = 35;
    private List<Date> mDateList = new ArrayList<>();
    private Calendar mCalendar = Calendar.getInstance(Locale.KOREA);

    private TextView mCurrentDate;
    private ImageButton previousBtn;
    private ImageButton nextBtn;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: called!!");
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: called!!");

        EventBus.getDefault().register(this);

        View view = inflater.inflate(R.layout.fragment_transaction, container, false);

        rv_calendar = view.findViewById(R.id.rv_calendar);
        list_container = view.findViewById(R.id.list_container);
        calendar_container = view.findViewById(R.id.calendar_container);

        mCurrentDate = view.findViewById(R.id.current_date);
        previousBtn = view.findViewById(R.id.previousBtn);
        nextBtn = view.findViewById(R.id.nextBtn);

        previousBtn.setOnClickListener(v -> {
            mCalendar.add(Calendar.MONTH, -1);
            setUpCalendar();
        });
        nextBtn.setOnClickListener(v -> {
            mCalendar.add(Calendar.MONTH, 1);
            setUpCalendar();
        });

        loadCalendar();

        mTabLayout = view.findViewById(R.id.transaction_list_tab);
        mViewPager = view.findViewById(R.id.transaction_viewPager);

        setupViewPager(mViewPager);

        mTabLayout.setupWithViewPager(mViewPager);

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.d(TAG, "onTabSelected: "+tab.getText());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return view;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void toggleEvent(CalendarToggleEvent event) {
        if (event.getType() == Common.LIST_TYPE) {
            list_container.setVisibility(View.VISIBLE);
            calendar_container.setVisibility(View.INVISIBLE);
        } else if (event.getType() == Common.CALENDAR_TYPE) {
            list_container.setVisibility(View.INVISIBLE);
            calendar_container.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }

    public void loadCalendar() {
        Log.d(TAG, "loadCalendar: called!!");
        mLayoutManager = new GridLayoutManager(getActivity(), 7);
        rv_calendar.setLayoutManager(mLayoutManager);
        rv_calendar.addItemDecoration(new SpacesItemDecoration(0));

        setUpCalendar();
    }

    private void setUpCalendar() {
        Log.d(TAG, "setUpCalendar: called!!");
        mCurrentDate.setText(Common.yearmonthDateFormate.format(mCalendar.getTime()));
        mDateList.clear();

        Calendar monthCalendar = (Calendar) mCalendar.clone();
        monthCalendar.set(Calendar.DAY_OF_MONTH, 1);
        int firstDayOfMonth = monthCalendar.get(Calendar.DAY_OF_WEEK) - 1;
        Log.d(TAG, "setUpCalendar: firstDayOfMonth: " + firstDayOfMonth);
        monthCalendar.add(Calendar.DAY_OF_MONTH, -firstDayOfMonth);

        Log.d(TAG, "setUpCalendar: monthCalendar.getTime(): "+monthCalendar.getTime());

        DatabaseUtils.getThisMonthTransaction(MainActivity.mDBHelper, Common.dateFormat.format(mCalendar.getTime()), this);

        while (mDateList.size() < MAX_CALENDAR_DAYS) {
            mDateList.add(monthCalendar.getTime());
            monthCalendar.add(Calendar.DAY_OF_MONTH, 1);
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM yyyy");
        Calendar calendar = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        List<Date> calendarList = new ArrayList<>();

        for (int i = 0; i < 12; i++) {
            calendarList.add(calendar.getTime());
            if (i==0) {
                for (int j = 0; j < 2; j++) {
                    calendar2.add(Calendar.MONTH, 1);
                    calendarList.add(calendar2.getTime());
                }
            }
            calendar.add(Calendar.MONTH, -1);
        }

        Collections.sort(calendarList);

        TransactionListFragmentAdapter adapter = new TransactionListFragmentAdapter(getChildFragmentManager());
        for (int i = 0; i < calendarList.size(); i++) {
            adapter.addFragment(TransactionListFragment.newInstance(calendarList.get(i).getTime()), dateFormat.format(calendarList.get(i)));
        }
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(calendarList.size()-3);
    }

    @Override
    public void onTransactionLoadSuccess(List<TransactionModel> transactionList) {
        Log.d(TAG, "onTransactionLoadSuccess: called!!");

        mAdapter = new CalendarAdapter(getActivity(), mDateList, mCalendar, transactionList);
        rv_calendar.setAdapter(mAdapter);
    }

    @Override
    public void onTransactionDeleteSuccess(boolean isSuccess) {

    }
}
