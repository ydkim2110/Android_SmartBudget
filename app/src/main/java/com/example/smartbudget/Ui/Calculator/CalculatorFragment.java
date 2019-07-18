package com.example.smartbudget.Ui.Calculator;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.smartbudget.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CalculatorFragment extends Fragment
        implements View.OnClickListener {

    private static CalculatorFragment instance;

    public static CalculatorFragment getInstance() {
        if (instance == null) {
            instance = new CalculatorFragment();
        }
        return instance;
    }

    public CalculatorFragment() {
        // Required empty public constructor
    }

    private TextView keysPressedTv;
    private TextView keyOneTv;

    private TextView mDisplayTextView;
    private boolean mKeypadMode;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cal, container, false);
//
//        keysPressedTv = view.findViewById(R.id.keys_pressed_tv);
//        keyOneTv = view.findViewById(R.id.key_one);
//
//        keyOneTv.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.key_one:
//                if (containsLastLetter("%")) {
//                    keysPressedTv.setText(keysPressedTv.getText()+"x1");
//                }
//                else {
//                    keysPressedTv.setText(keysPressedTv.getText()+"1");
//                }
//
//        }
    }

    private void carryOutCalculation() {
        String expression = keysPressedTv.getText().toString();

        if (expression.contains("x")) {
            expression = expression.replace("x", "*");
        }

        if (expression.contains("÷")) {
            expression = expression.replace("÷", "/");
        }

        if (expression.contains("(") && !expression.substring(expression.lastIndexOf("("), expression.length()).contains(")")) {
            expression += ")";
        }


    }

    public boolean containsLastLetter(String letter) {
        String text = keysPressedTv.getText().toString();
        String last = "";
        if (text.length() != 0) {
            last = text.substring(text.length()-1);
        }
        return last.equals(letter);
    }
}
