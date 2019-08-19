package com.example.smartbudget.Ui.Report;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ReportSubPagerAdapter extends FragmentPagerAdapter {

    private Context mContext;

    public ReportSubPagerAdapter(FragmentManager fm, Context mContext) {
        super(fm);
        this.mContext = mContext;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return ExpRepFragment.getInstance();
        } else if (position == 1) {
            return IncRepFragment.getInstance();
        } else {
            return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Expense";
            case 1:
                return "Income";
            default:
                return null;
        }
    }
}
