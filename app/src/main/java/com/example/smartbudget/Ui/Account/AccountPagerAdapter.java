package com.example.smartbudget.Ui.Account;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.smartbudget.R;

public class AccountPagerAdapter extends FragmentPagerAdapter {

    private Context mContext;

    public AccountPagerAdapter(FragmentManager fm, Context mContext) {
        super(fm);
        this.mContext = mContext;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return AssetFragment.getInstance();
        } else if (position == 1) {
            return DebtFragment.getInstance();
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
                return mContext.getResources().getString(R.string.tab_title_asset);
            case 1:
                return mContext.getResources().getString(R.string.tab_title_debt);
            default:
                return null;
        }
    }
}
