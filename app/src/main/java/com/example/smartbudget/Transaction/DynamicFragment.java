package com.example.smartbudget.Transaction;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.smartbudget.Database.DatabaseUtils;
import com.example.smartbudget.Database.Model.TransactionModel;
import com.example.smartbudget.Main.MainActivity;
import com.example.smartbudget.R;
import com.example.smartbudget.Travel.TravelListAdapter;

import java.util.List;

public class DynamicFragment extends Fragment implements ITransactionLoadListener {

    private static final String TAG = "TravelDetailFragment";
    
    public static DynamicFragment newInstance() {
        return new DynamicFragment();
    }

    private RecyclerView mRecyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DatabaseUtils.getAllTransaction(MainActivity.mDBHelper, this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dynamic_fragment_layout, container, false);
        initView(view);
        return view;
    }

    @SuppressLint("WrongConstant")
    private void initView(View view) {
        mRecyclerView = view.findViewById(R.id.transaction_list_recyclerview);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void onTransactionLoadSuccess(List<TransactionModel> transactionList) {
        TransactionListAdapter adapter = new TransactionListAdapter(transactionList);
        mRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onTransactionDeleteSuccess(boolean isSuccess) {

    }
}
