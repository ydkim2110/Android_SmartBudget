package com.example.smartbudget.Report;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.smartbudget.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReportFragment extends Fragment {

    private static final String TAG = ReportFragment.class.getSimpleName();

    private static ReportFragment instance;

    public static ReportFragment getInstance() {
        if (instance == null) {
            instance = new ReportFragment();
        }
        return instance;
    }

    public ReportFragment() {
    }

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private PagerAdapter mPagerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: started!!");

        View view =  inflater.inflate(R.layout.fragment_report, container, false);

        mViewPager = view.findViewById(R.id.viewPager);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());

        adapter.addFragment(WeeklyFragment.newInstance(), "Weekly");
        adapter.addFragment(MonthlyFragment.newInstance(), "Monthly");
        adapter.addFragment(YearlyFragment.newInstance(), "Yearly");

        mViewPager.setAdapter(adapter);

        mTabLayout = view.findViewById(R.id.tab);

        mTabLayout.setupWithViewPager(mViewPager);

        return view;
    }

}
