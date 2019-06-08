package com.example.smartbudget.Transaction.Dialog;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.Toast;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment {

    private static final String TAG = DatePickerFragment.class.getSimpleName();

    private IDialogSendListener mIDialogSendListener;

    public static DatePickerFragment newInstance(String passedData) {
        DatePickerFragment fragment = new DatePickerFragment();
        Bundle args = new Bundle();
        args.putString("DATE", passedData);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mIDialogSendListener = (IDialogSendListener) getActivity();
        } catch (ClassCastException e) {
            Log.e(TAG, "onAttach: ClassCastException: " + e.getMessage());
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        String data = getArguments().getString("DATE");

        String[] splitData = data.split("-");

        int _year = Integer.parseInt(splitData[0]);
        int _month = Integer.parseInt(splitData[1])-1;
        int _day = Integer.parseInt(splitData[2]);

        Toast.makeText(getContext(), "splitData: "+_year+" "+_month+" "+_day, Toast.LENGTH_SHORT).show();
        
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), dateSetListener, _year, _month, _day);
    }

    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            mIDialogSendListener.OnSendListener(view.getYear() +
                    "-" + (view.getMonth()+1) +
                    "-" + view.getDayOfMonth());
        }
    };
}
