package com.example.smartbudget.Statstics;


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
public class StatsticsFragment extends Fragment {

    private static final String TAG = StatsticsFragment.class.getSimpleName();

    public StatsticsFragment() {
    }

    public static StatsticsFragment newInstance() {
        StatsticsFragment fragment = new StatsticsFragment();
        return fragment;
    }

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private PagerAdapter mPagerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: started!!");

        View view =  inflater.inflate(R.layout.fragment_statstics, container, false);

        mTabLayout = view.findViewById(R.id.tab);
        mTabLayout.addTab(mTabLayout.newTab().setCustomView(createTabView("Weekly")));
        mTabLayout.addTab(mTabLayout.newTab().setCustomView(createTabView("Monthly")));
        mTabLayout.addTab(mTabLayout.newTab().setCustomView(createTabView("Yearly")));

        mViewPager = view.findViewById(R.id.viewPager);

        mPagerAdapter = new PagerAdapter(getActivity().getSupportFragmentManager(), mTabLayout.getTabCount());

        mViewPager.setAdapter(mPagerAdapter);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
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

    private View createTabView(String tabName) {
        View tabView = LayoutInflater.from(getContext()).inflate(R.layout.custom_tab, null);
        TextView txt_name = tabView.findViewById(R.id.txt_name);
        txt_name.setText(tabName);
        return tabView;
    }

}
