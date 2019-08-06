package com.example.smartbudget.Ui.Transaction.Add.Category;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.smartbudget.R;

public class CategoryPagerAdapter extends FragmentPagerAdapter {

    private Context mContext;

    public CategoryPagerAdapter(FragmentManager fm, Context mContext) {
        super(fm);
        this.mContext = mContext;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return ExpenseFragment.getInstance();
        } else if (position == 1) {
            return IncomeFragment.getInstance();
        }else {
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
                return mContext.getResources().getString(R.string.expense);
            case 1:
                return mContext.getResources().getString(R.string.income);
            default:
                return null;
        }
    }
}
