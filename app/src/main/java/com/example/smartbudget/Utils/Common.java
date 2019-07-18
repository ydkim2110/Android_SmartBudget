package com.example.smartbudget.Utils;

import com.example.smartbudget.Model.AccountModel;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Common {

    public static final String KEY_SELECTED_CATEGORY = "SELECTED_CATEGORY";

    public static AccountModel SELECTED_ACCOUNT = null;

    // Intent
    public static final String EXTRA_INPUT_ACCOUNT = "INPUT_ACCOUNT";

    public static final String EXTRA_PASS_INPUT_NOTE = "PASS_INPUT_NOTE";
    public static final String EXTRA_INPUT_NOTE = "INPUT_NOTE";

    public static final String EXTRA_PASS_INPUT_AMOUNT = "PASS_INPUT_AMOUNT";
    public static final String EXTRA_INPUT_AMOUNT = "INPUT_AMOUNT";

    public static final String EXTRA_PASS_BUDGET_CATEGORY = "PASS_BUDGET_CATEGORY";

    public static final String EXTRA_EDIT_TRANSACTION = "EDIT_TRANSACTION";

    ////// Calculator
    public static final String ACTIVITY_MODE = "ActivityMode";
    public static final String CURRENCY = "Currency";
    public static final String MONEY = "Money";

    public static final int MODE_CALCULATOR = 0;
    public static final int MODE_KEYPAD = 1;

    private static final String OP_000 = "000";
    private static final String OP_0 = "0";
    private static final String OP_1 = "1";
    private static final String OP_2 = "2";
    private static final String OP_3 = "3";
    private static final String OP_4 = "4";
    private static final String OP_5 = "5";
    private static final String OP_6 = "6";
    private static final String OP_7 = "7";
    private static final String OP_8 = "8";
    private static final String OP_9 = "9";
    private static final String OP_POINT = ".";
    private static final String OP_CLEAR = "C";
    private static final String OP_CANCEL = "B";
    private static final String OP_ADDITION = "A";
    private static final String OP_SUBTRACTION = "S";
    private static final String OP_MULTIPLICATION = "M";
    private static final String OP_DIVISION = "D";
    private static final String OP_EXECUTE = "E";
    ////// Calculator

    public static String changeNumberToComma(int number) {
        return NumberFormat.getNumberInstance().format(number);
    }

    public static String calcPercentage (int currentValue, int maxValue) {
        return new DecimalFormat("0.00").format(
                (Double.parseDouble(String.valueOf(currentValue)) * 100/
                        (Double.parseDouble(String.valueOf(maxValue)))));
    }

    public static Date stringToDate(String date) {
        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date tempDate = null;
        try {
            tempDate = transFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            tempDate = new Date();
        }
        return tempDate;
    }

    public static long calDateBetweenStartandEnd(String startDate, String endDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        long diffDay = -1;
        try {
            Date start = sdf.parse(startDate);
            Date end = sdf.parse(endDate);
            diffDay = (end.getTime() - start.getTime()) / (24*60*60*1000);
        } catch (ParseException e) {
            e.printStackTrace();
            diffDay = 0;
        }
        return diffDay+1;
    }

    public static String[] getDiffDays(String fromDate, String toDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(sdf.parse(fromDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int count = (int) calDateBetweenStartandEnd(fromDate, toDate);
        cal.add(Calendar.DATE, -1);

        ArrayList<String> list = new ArrayList<>();

        for (int i=0; i<count; i++) {
            cal.add(Calendar.DATE, 1);
            list.add(sdf.format(cal.getTime()));
        }

        String[] result = new String[list.size()];

        list.toArray(result);

        return result;
    }

}
