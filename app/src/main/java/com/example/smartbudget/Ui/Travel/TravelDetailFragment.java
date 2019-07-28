package com.example.smartbudget.Ui.Travel;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartbudget.Database.DatabaseUtils;
import com.example.smartbudget.Model.TransactionModel;
import com.example.smartbudget.Ui.Main.MainActivity;
import com.example.smartbudget.R;
import com.example.smartbudget.Ui.Transaction.ITransactionLoadListener;
import com.example.smartbudget.Ui.Transaction.TransactionListAdapter;

import java.util.List;

public class TravelDetailFragment extends Fragment implements ITransactionLoadListener {

    private static final String TAG = "TravelDetailFragment";
    
    public static TravelDetailFragment newInstance() {
        return new TravelDetailFragment();
    }

    private RecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dynamic_fragment_layout, container, false);

        DatabaseUtils.getAllTransaction(MainActivity.mDBHelper, this);

        initView(view);
        return view;
    }

    @SuppressLint("WrongConstant")
    private void initView(View view) {
        mRecyclerView = view.findViewById(R.id.rv_transaction_list);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(layoutManager.getOrientation());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
    }

    @Override
    public void onTransactionLoadSuccess(List<TransactionModel> transactionList) {
        TransactionListAdapter adapter = new TransactionListAdapter(getContext(), transactionList);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onTransactionDeleteSuccess(boolean isSuccess) {

    }
}
