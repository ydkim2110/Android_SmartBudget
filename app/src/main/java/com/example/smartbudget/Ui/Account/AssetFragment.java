package com.example.smartbudget.Ui.Account;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.smartbudget.R;
import com.example.smartbudget.Ui.Home.Spending.InvestFragment;
import com.example.smartbudget.Utils.Common;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class AssetFragment extends Fragment {

    private static final String TAG = AssetFragment.class.getSimpleName();

    private static AssetFragment instance;

    public static AssetFragment getInstance() {
        Log.d(TAG, "getInstance: called!!");
        return instance == null ? new AssetFragment() : instance;
    }

    public AssetFragment() {
        // Required empty public constructor
    }

    @BindView(R.id.rv_asset)
    RecyclerView rv_asset;

    Unbinder mUnbinder;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_asset, container, false);
        mUnbinder = ButterKnife.bind(this, view);

        rv_asset.setLayoutManager(new LinearLayoutManager(getActivity()));
        CommonRecycleAdapter adapter = new CommonRecycleAdapter(createItemList());
        rv_asset.setAdapter(adapter);

        return view;
    }

    private List<String> createItemList() {
        List<String> itemList = new ArrayList<>();
        for(int i=0;i<30;i++) {
            itemList.add("Item "+i);
        }
        return itemList;
    }


    @Override
    public void onDestroy() {
        mUnbinder.unbind();
        super.onDestroy();
    }
}
