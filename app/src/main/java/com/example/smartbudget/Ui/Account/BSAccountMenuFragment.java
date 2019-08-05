package com.example.smartbudget.Ui.Account;


import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartbudget.R;

public class BSAccountMenuFragment extends BottomSheetDialogFragment {

    private static final String TAG = BSAccountMenuFragment.class.getSimpleName();

    private static BSAccountMenuFragment instance;

    public static BSAccountMenuFragment getInstance() {
        if (instance == null) {
            instance = new BSAccountMenuFragment();
        }
        return instance;
    }

    public BSAccountMenuFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: started!!");
        View view = inflater.inflate(R.layout.fragment_bsaccount_menu, container, false);

        TextView editTv = view.findViewById(R.id.edit_tv);
        TextView deleteTv = view.findViewById(R.id.delete_tv);

        editTv.setOnClickListener(v -> {
            Toast.makeText(getContext(), "edit", Toast.LENGTH_SHORT).show();
            getContext().startActivity(new Intent(getContext(), AddAccountActivity.class));
            AccountAdapter.mBSAccountMenuFragment.dismiss();
        });

        deleteTv.setOnClickListener(v -> {
            Toast.makeText(getContext(), "delete", Toast.LENGTH_SHORT).show();
//            DatabaseUtils.deleteAccountAsync(MainActivity.mDBHelper, AccountAdapter.mListener, Common.SELECTED_ACCOUNT);
//            AccountAdapter.mBSAccountMenuFragment.dismiss();
        });

        return view;
    }

}
