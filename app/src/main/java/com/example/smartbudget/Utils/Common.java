package com.example.smartbudget.Utils;

import java.text.NumberFormat;

public class Common {

    public static final String KEY_SELECTED_CATEGORY = "SELECTED_CATEGORY";

    // Intent
    public static final String EXTRA_PASS_INPUT_NOTE = "PASS_INPUT_NOTE";
    public static final String EXTRA_INPUT_NOTE = "INPUT_NOTE";

    public static final String EXTRA_PASS_INPUT_AMOUNT = "PASS_INPUT_AMOUNT";
    public static final String EXTRA_INPUT_AMOUNT = "INPUT_AMOUNT";

    public static final String EXTRA_PASS_BUDGET_CATEGORY = "PASS_BUDGET_CATEGORY";

    public static final String EXTRA_EDIT_TRANSACTION = "EDIT_TRANSACTION";

    public static String changeNumberToComma(int number) {
        return NumberFormat.getNumberInstance().format(number);
    }
}
