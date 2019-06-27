package com.example.smartbudget.Report;


import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
