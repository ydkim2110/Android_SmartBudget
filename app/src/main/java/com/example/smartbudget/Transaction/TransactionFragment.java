package com.example.smartbudget.Transaction;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.smartbudget.Database.DatabaseUtils;
import com.example.smartbudget.Database.Model.TransactionModel;
import com.example.smartbudget.Main.MainActivity;
import com.example.smartbudget.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

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

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transaction, container, false);

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

    private void setupViewPager(ViewPager viewPager) {
        DynamicFragmentAdapter adapter = new DynamicFragmentAdapter(getChildFragmentManager());
        for(int i=0 ;i<10; i++){
            adapter.addFragment(DynamicFragment.newInstance(), "6월 "+(i+1)+"일");
            viewPager.setAdapter(adapter);
        }
    }
}
