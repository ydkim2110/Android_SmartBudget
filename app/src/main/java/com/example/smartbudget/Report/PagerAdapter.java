package com.example.smartbudget.Report;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter {

    private int mPageCount;

    public PagerAdapter(FragmentManager fm, int pageCount) {
        super(fm);
        this.mPageCount = pageCount;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return WeeklyFragment.newInstance();
            case 1:
                return MonthlyFragment.newInstance();
            case 2:
                return YearlyFragment.newInstance();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mPageCount;
    }
}
