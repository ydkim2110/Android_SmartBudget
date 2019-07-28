package com.example.smartbudget.Ui.Home.Spending;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.smartbudget.Ui.Transaction.Add.Category.ExpenseFragment;
import com.example.smartbudget.Ui.Transaction.Add.Category.IncomeFragment;
import com.example.smartbudget.Ui.Transaction.Add.Category.TransferFragment;

public class PagerAdapter extends FragmentPagerAdapter {

    private Context mContext;

    public PagerAdapter(FragmentManager fm, Context mContext) {
        super(fm);
        this.mContext = mContext;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return NormalFragment.getInstance();
        } else if (position == 1) {
            return WasteFragment.getInstance();
        } else if (position == 2) {
            return InvestFragment.getInstance();
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
                return "Normal";
            case 1:
                return "Waste";
            case 2:
                return "Invest";
            default:
                return null;
        }
    }
}
