package com.example.smartbudget.Utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.text.DecimalFormat;
import java.text.ParseException;

public class NumberTextWatcher implements TextWatcher {

    private static final String TAG = NumberTextWatcher.class.getSimpleName();

    private DecimalFormat df;
    private DecimalFormat dfnd;
    private boolean hasFractionalPart;

    private EditText edt;

    public NumberTextWatcher(EditText edt) {
        df = new DecimalFormat("#,###.##");
        df.setDecimalSeparatorAlwaysShown(true);
        dfnd = new DecimalFormat("#,###");
        this.edt = edt;
        hasFractionalPart = false;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.toString().contains(String.valueOf(df.getDecimalFormatSymbols().getDecimalSeparator())))
        {
            hasFractionalPart = true;
        } else {
            hasFractionalPart = false;
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        edt.removeTextChangedListener(this);

        try {
            int inilen, endlen;
            inilen = edt.getText().length();

            String v = s.toString().replace(String.valueOf(df.getDecimalFormatSymbols().getGroupingSeparator()), "");
            Number n = df.parse(v);
            int cp = edt.getSelectionStart();
            if (hasFractionalPart) {
                edt.setText(df.format(n));
            } else {
                edt.setText(dfnd.format(n));
            }
            endlen = edt.getText().length();
            int sel = (cp + (endlen - inilen));
            if (sel > 0 && sel <= edt.getText().length()) {
                edt.setSelection(sel);
            } else {
                // place cursor at the end?
                edt.setSelection(edt.getText().length() - 1);
            }
        } catch (NumberFormatException nfe) {
            // do nothing?
        } catch (ParseException e) {
            // do nothing?
        }

        edt.addTextChangedListener(this);
    }
}
