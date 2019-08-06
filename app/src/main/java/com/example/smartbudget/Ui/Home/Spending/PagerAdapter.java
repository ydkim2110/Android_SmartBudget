package com.example.smartbudget.Ui.Home.Spending;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.smartbudget.R;

public class PagerAdapter extends FragmentPagerAdapter {

    private Context mContext;
    private String currentDate;

    public PagerAdapter(FragmentManager fm, Context mContext, String currentDate) {
        super(fm);
        this.mContext = mContext;
        this.currentDate = currentDate;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return NormalFragment.getInstance(currentDate);
        } else if (position == 1) {
            return WasteFragment.getInstance(currentDate);
        } else if (position == 2) {
            return InvestFragment.getInstance(currentDate);
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
                return mContext.getResources().getString(R.string.normal);
            case 1:
                return mContext.getResources().getString(R.string.waste);
            case 2:
                return mContext.getResources().getString(R.string.invest);
            default:
                return null;
        }
    }
}
