package com.example.smartbudget.Utils;

import android.animation.ValueAnimator;
import android.widget.TextView;

import com.example.smartbudget.Database.AccountRoom.AccountItem;
import com.example.smartbudget.Database.TransactionRoom.TransactionItem;
import com.example.smartbudget.Model.AccountModel;
import com.example.smartbudget.Model.Category;
import com.example.smartbudget.Model.DefaultCategories;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Common {

    public static final String KEY_SELECTED_CATEGORY = "SELECTED_CATEGORY";
    public static final int LIST_TYPE = 0;
    public static final int CALENDAR_TYPE = 1;

    public static final int TYPE_CATEGORY = 0;
    public static final int TYPE_SUB_CATEGORY = 1;
    public static final int TYPE_EXPENSE_TRANSACTION = 0;
    public static final int TYPE_INCOME_TRANSACTION = 1;
    public static final int TYPE_TRANSFER_TRANSACTION = 2;
    public static final String LOGGED_KEY = "";

    public static AccountItem SELECTED_ACCOUNT = null;
    public static List<TransactionItem> TRANSACTION_LIST = null;

    // Intent
    public static final String EXTRA_INPUT_ACCOUNT = "INPUT_ACCOUNT";

    public static final String EXTRA_PASS_INPUT_NOTE = "PASS_INPUT_NOTE";
    public static final String EXTRA_INPUT_NOTE = "INPUT_NOTE";

    public static final String EXTRA_PASS_INPUT_AMOUNT = "PASS_INPUT_AMOUNT";
    public static final String EXTRA_INPUT_AMOUNT = "INPUT_AMOUNT";

    public static final String EXTRA_PASS_BUDGET_CATEGORY = "PASS_BUDGET_CATEGORY";

    public static final String EXTRA_EDIT_TRANSACTION = "EDIT_TRANSACTION";
    public static final String EXTRA_EDIT_BUDGET = "EDIT_BUDGET";

    public static final SimpleDateFormat yearmonthDateFormate = new SimpleDateFormat("yyyy MMMM", Locale.KOREA);
    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat monthdayFormat = new SimpleDateFormat("MM.dd");

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

    public static String calcPercentageDownToTwo(int currentValue, int maxValue) {
        return new DecimalFormat("0.00").format(
                (Double.parseDouble(String.valueOf(currentValue)) * 100 /
                        (Double.parseDouble(String.valueOf(maxValue)))));
    }

    public static String calcPercentageDownToOne(int currentValue, int maxValue) {
        return new DecimalFormat("0.0").format(
                (Double.parseDouble(String.valueOf(currentValue)) * 100 /
                        (Double.parseDouble(String.valueOf(maxValue)))));
    }

    public static String calcPercentage(int currentValue, int maxValue) {
        return new DecimalFormat("0").format(
                (Double.parseDouble(String.valueOf(currentValue)) * 100 /
                        (Double.parseDouble(String.valueOf(maxValue)))));
    }

    public static Date getWeekStartDate() {
        Calendar calendar = Calendar.getInstance();
        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
            calendar.add(Calendar.DATE, -1);
        }
        return calendar.getTime();
    }

    public static Date getWeekEndDate() {
        Calendar calendar = Calendar.getInstance();
        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
            calendar.add(Calendar.DATE, 1);
        }
        calendar.add(Calendar.DATE, -1);
        return calendar.getTime();
    }

    public static Date getMaximumDate(Date date) {
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));

        return calendar.getTime();
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
            diffDay = (end.getTime() - start.getTime()) / (24 * 60 * 60 * 1000);
        } catch (ParseException e) {
            e.printStackTrace();
            diffDay = 0;
        }
        return diffDay + 1;
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

        for (int i = 0; i < count; i++) {
            cal.add(Calendar.DATE, 1);
            list.add(sdf.format(cal.getTime()));
        }

        String[] result = new String[list.size()];

        list.toArray(result);

        return result;
    }

    public static String removeComma(String number) {
        return number.replace(",", "");
    }

    public static Category getExpenseCategory(String categoryId) {
        List<Category> categoryList = Arrays.asList(DefaultCategories.getDefaultExpenseCategories());
        for (Category category : categoryList) {
            if (category.getCategoryID().equals(categoryId)) {
                return category;
            }
        }
        return null;
    }

    public static Category getIncomeCategory(String categoryId) {
        List<Category> categoryList = Arrays.asList(DefaultCategories.getDefaultIncomeCategories());
        for (Category category : categoryList) {
            if (category.getCategoryID().equals(categoryId)) {
                return category;
            }
        }
        return null;
    }

    public static void animateTextView(int duration, int initialValue, int finalValue, final TextView textview) {

        ValueAnimator valueAnimator = ValueAnimator.ofInt(initialValue, finalValue);
        valueAnimator.setDuration(duration);

        valueAnimator.addUpdateListener(valueAnimator1 -> {
                textview.setText(new StringBuilder(changeNumberToComma(Integer.parseInt(valueAnimator1.getAnimatedValue().toString()))).append("원"));
        });
        valueAnimator.start();
    }
}
