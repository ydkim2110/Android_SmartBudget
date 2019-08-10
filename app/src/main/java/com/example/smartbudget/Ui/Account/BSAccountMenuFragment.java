package com.example.smartbudget.Ui.Account;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import com.example.smartbudget.Database.AccountRoom.DBAccountUtils;
import com.example.smartbudget.Database.Interface.IAccountDeleteListener;
import com.example.smartbudget.Ui.Main.MainActivity;
import com.example.smartbudget.Utils.Common;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartbudget.R;

public class BSAccountMenuFragment extends BottomSheetDialogFragment implements IAccountDeleteListener {

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
            Intent intent = new Intent(getContext(), AddAccountActivity.class);
            intent.putExtra(AddAccountActivity.EXTRA_TAG, "edit");
            getContext().startActivity(intent);
            AccountAdapter.mBSAccountMenuFragment.dismiss();
        });

        deleteTv.setOnClickListener(v -> {

            AlertDialog.Builder mDialog = new AlertDialog.Builder(getActivity());
            mDialog.setTitle(getContext().getResources().getString(R.string.alert_delete_title))
                    .setMessage(getContext().getResources().getString(R.string.alert_delete_message))
                    .setPositiveButton("OK", (dialog, which) -> {
                        dialog.dismiss();
                        DBAccountUtils.deleteAccountAsync(MainActivity.db, this, Common.SELECTED_ACCOUNT);
                    })
                    .setNegativeButton("Cancel", ((dialog, which) -> {
                        dialog.dismiss();
                    }));

            AlertDialog build = mDialog.create();
            build.show();

            AccountAdapter.mBSAccountMenuFragment.dismiss();
        });

        return view;
    }

    @Override
    public void onAccountDeleteSuccess(boolean isDeleted) {
        Log.d(TAG, "onAccountDeleteSuccess: success!!");
        if (isDeleted) {
        }
        Common.SELECTED_ACCOUNT = null;
    }

    @Override
    public void onAccountDeleteFailed(String message) {
        Log.d(TAG, "onAccountDeleteFailed: failed!!");
        Common.SELECTED_ACCOUNT = null;
    }
}
