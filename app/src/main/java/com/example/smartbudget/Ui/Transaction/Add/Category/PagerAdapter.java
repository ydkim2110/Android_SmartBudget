package com.example.smartbudget.Ui.Transaction.Add.Category;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PagerAdapter extends FragmentPagerAdapter {

    private Context mContext;

    public PagerAdapter(FragmentManager fm, Context mContext) {
        super(fm);
        this.mContext = mContext;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return ExpenseFragment.getInstance();
        } else if (position == 1) {
            return IncomeFragment.getInstance();
        } else if (position == 2) {
            return TransferFragment.getInstance();
        } else {
            return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Expense";
            case 1:
                return "Income";
            case 2:
                return "Transfer";
            default:
                return null;
        }
    }
}
