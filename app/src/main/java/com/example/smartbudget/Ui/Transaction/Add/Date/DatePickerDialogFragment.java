package com.example.smartbudget.Ui.Transaction.Add.Date;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.Toast;

import java.util.Calendar;

public class DatePickerDialogFragment extends DialogFragment {

    private static final String TAG = DatePickerDialogFragment.class.getSimpleName();

    private IDialogSendListener mIDialogSendListener;

    public static DatePickerDialogFragment newInstance(String passedData) {
        DatePickerDialogFragment fragment = new DatePickerDialogFragment();
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
            mIDialogSendListener.OnSendListener(showSetDate(view.getYear(), view.getMonth()+1, view.getDayOfMonth()));
        }
    };

    private String showSetDate(int year, int month, int dayOfMonth) {
        Log.d(TAG, "showSetDate: year"+year);
        Log.d(TAG, "showSetDate: month"+month);
        Log.d(TAG, "showSetDate: dayOfMonth"+dayOfMonth);
        String result = String.valueOf(new StringBuilder(""+year).append("-").append(""+String.format("%02d", month)).append("-").append(""+String.format("%02d", dayOfMonth)));
        Log.d(TAG, "showSetDate: result: "+ result);
        return result;
    }
}
