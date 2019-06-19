package com.example.smartbudget.Transaction;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class DynamicFragmentAdapter extends FragmentPagerAdapter {

    private int mNumberOfTabs;

    public DynamicFragmentAdapter(FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.mNumberOfTabs = numberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle args = new Bundle();
        args.putInt("position", position);
        Fragment fragment = DynamicFragment.newInstance();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getCount() {
        return mNumberOfTabs;
    }
}
