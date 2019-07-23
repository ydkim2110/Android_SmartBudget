package com.example.smartbudget.Ui.Transaction;


import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.smartbudget.Model.EventBus.CalendarToggleEvent;
import com.example.smartbudget.R;
import com.example.smartbudget.Utils.Common;
import com.example.smartbudget.Utils.SpacesItemDecoration;
import com.google.android.material.tabs.TabLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TransactionFragment extends Fragment {

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

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private CalendarAdapter mAdapter;

    private static final int MAX_CALENDAR_DAYS = 35;
    private List<Date> mDateList = new ArrayList<>();
    private Calendar mCalendar = Calendar.getInstance(Locale.KOREA);

    private TextView mCurrentDate;
    private ImageButton previousBtn;
    private ImageButton nextBtn;

    private int height = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: called!!");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: called!!");

        EventBus.getDefault().register(this);

        View view = inflater.inflate(R.layout.fragment_transaction, container, false);

        list_container = view.findViewById(R.id.list_container);
        calendar_container = view.findViewById(R.id.calendar_container);

        view.post(() -> {

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

            loadCalendar(view);

        });

        mTabLayout = view.findViewById(R.id.transaction_list_tab);
        mViewPager = view.findViewById(R.id.transaction_viewPager);

        setupViewPager(mViewPager);

        mTabLayout.setupWithViewPager(mViewPager);

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

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
        if (calendar_container.getVisibility() == View.VISIBLE) {
            calendar_container.setVisibility(View.INVISIBLE);
            list_container.setVisibility(View.VISIBLE);
        } else {
            calendar_container.setVisibility(View.VISIBLE);
            list_container.setVisibility(View.INVISIBLE);
        }
    }
    
    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }

    public void loadCalendar(View view) {
        Log.d(TAG, "loadCalendar: called!!");
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        int statusBarHeight = getStatusBarHeight();
        int linearLayoutMonth = view.findViewById(R.id.linearLayout_month).getMeasuredHeight();
        int linearLayoutDay = view.findViewById(R.id.linearLayout).getMeasuredHeight();

        height = (size.y-statusBarHeight-linearLayoutMonth-linearLayoutDay)/5;

        mRecyclerView = view.findViewById(R.id.rv_calendar);
        mLayoutManager = new GridLayoutManager(getActivity(), 7);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(0));

        setUpCalendar();
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    private void setUpCalendar() {
        Log.d(TAG, "setUpCalendar: called!!");
        mCurrentDate.setText(Common.yearmonthDateFormate.format(mCalendar.getTime()));
        mDateList.clear();

        Calendar monthCalendar = (Calendar) mCalendar.clone();
        monthCalendar.set(Calendar.DAY_OF_MONTH, 1);
        int firstDayOfMonth = monthCalendar.get(Calendar.DAY_OF_WEEK)-1;
        Log.d(TAG, "setUpCalendar: firstDayOfMonth: "+firstDayOfMonth);
        monthCalendar.add(Calendar.DAY_OF_MONTH, -firstDayOfMonth);

        while (mDateList.size() < MAX_CALENDAR_DAYS) {
            mDateList.add(monthCalendar.getTime());
            monthCalendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        mAdapter = new CalendarAdapter(getActivity(), mDateList, mCalendar, height);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void setupViewPager(ViewPager viewPager) {
        DynamicFragmentAdapter adapter = new DynamicFragmentAdapter(getChildFragmentManager());
        for(int i=0; i<10; i++){
            adapter.addFragment(DynamicFragment.newInstance(), "6월 "+(i+1)+"일");
        }
        viewPager.setAdapter(adapter);
    }

}
