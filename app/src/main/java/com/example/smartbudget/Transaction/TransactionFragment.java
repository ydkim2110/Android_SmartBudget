package com.example.smartbudget.Transaction;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.smartbudget.R;

public class TransactionFragment extends Fragment{

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

    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transaction, container, false);

        mViewPager = view.findViewById(R.id.transaction_list_viewPager);
        mTabLayout = view.findViewById(R.id.transaction_list_tab);

        mViewPager.setOffscreenPageLimit(1);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        setDynamicFragmentToTabLayout();

        return view;
    }

    private void setDynamicFragmentToTabLayout() {
        Log.d(TAG, "setDynamicFragmentToTabLayout: called!!");

        for (int i=0; i<10; i++) {
            mTabLayout.addTab(mTabLayout.newTab().setText("Category: "+i));
        }

        DynamicFragmentAdapter dynamicFragmentAdapter = new DynamicFragmentAdapter(getActivity().getSupportFragmentManager(),
                mTabLayout.getTabCount());
        mViewPager.setAdapter(dynamicFragmentAdapter);
        mViewPager.setCurrentItem(5);
    }
}
