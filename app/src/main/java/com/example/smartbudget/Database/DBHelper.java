package com.example.smartbudget.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.smartbudget.Model.AccountModel;
import com.example.smartbudget.Model.BudgetModel;
import com.example.smartbudget.Model.TransactionModel;
import com.example.smartbudget.Utils.Common;
import com.example.smartbudget.Utils.DateHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static com.example.smartbudget.Database.DBContract.*;

public class DBHelper extends SQLiteOpenHelper {

    private static final String TAG = DBHelper.class.getSimpleName();

    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "smart_budget.db";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    private static final String SQL_CREATE_ACCOUNT_TABLE =
            "CREATE TABLE " + Account.TABLE_NAME + " (" +
                    Account._ID + " INTEGER PRIMARY KEY, " +
                    Account.COL_NAME + " TEXT, " +
                    Account.COL_DESCRIPTION + " TEXT, " +
                    Account.COL_AMOUNT + " INTEGER, " +
                    Account.COL_HIGH_CATEGORY + " TEXT, " +
                    Account.COL_TYPE + " TEXT, " +
                    Account.COL_CREATE_AT + " DATE, " +
                    Account.COL_CURRENCY + " TEXT)";

    private static final String SQL_CREATE_TRANSACTION_TABLE =
            "CREATE TABLE " + Transaction.TABLE_NAME + " (" +
                    Transaction._ID + " INTEGER PRIMARY KEY," +
                    Transaction.COL_NOTE + " TEXT," +
                    Transaction.COL_AMOUNT + " INTEGER," +
                    Transaction.COL_TYPE + " TEXT," +
                    Transaction.COL_PATTERN + " TEXT," +
                    Transaction.COL_DATE + " TEXT," +
                    Transaction.COL_CATEGORY_ID + " TEXT," +
                    Transaction.COL_SUB_CATEGORY_ID + " TEXT," +
                    Transaction.COL_ACCOUNT_ID + " TEXT ," +
                    Transaction.COL_TO_ACCOUNT + " INT REFERENCES " + Account.TABLE_NAME + "(_id)," +
                    "FOREIGN KEY (" + Transaction.COL_ACCOUNT_ID + ") REFERENCES " + Account.TABLE_NAME + "(_id) ON DELETE CASCADE)";

    private static final String SQL_CREATE_BUDGET_TABLE =
            "CREATE TABLE " + Budget.TABLE_NAME + " (" +
                    Budget._ID + " INTEGER PRIMARY KEY, " +
                    Budget.COL_DESCRIPTION + " TEXT, " +
                    Budget.COL_AMOUNT + " INTEGER, " +
                    Budget.COL_START_DATE + " TEXT, " +
                    Budget.COL_END_DATE + " TEXT, " +
                    Budget.COL_ACCOUNT_ID + " INTEGER)";

    private static final String SQL_DELETE_ACCOUNT_TABLE =
            "DROP TABLE IF EXISTS " + Account.TABLE_NAME;
    private static final String SQL_DELETE_TRANSACTION_TABLE =
            "DROP TABLE IF EXISTS " + Transaction.TABLE_NAME;
    private static final String SQL_DELETE_BUDGET_TABLE =
            "DROP TABLE IF EXISTS " + Budget.TABLE_NAME;

    @Override
    public void onConfigure(SQLiteDatabase db) {
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {

        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ACCOUNT_TABLE);
        db.execSQL(SQL_CREATE_TRANSACTION_TABLE);
        db.execSQL(SQL_CREATE_BUDGET_TABLE);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        Calendar calendar1 = new GregorianCalendar(2019, 05, 01);
        Calendar calendar2 = new GregorianCalendar(2019, 06, 02);
        Calendar calendar3 = new GregorianCalendar(2019, 06, 03);
        Calendar calendar4 = new GregorianCalendar(2019, 06, 04);
        Calendar calendar5 = new GregorianCalendar(2019, 06, 04);
        Calendar calendar6 = new GregorianCalendar(2019, 06, 05);
        Calendar calendar7 = new GregorianCalendar(2019, 06, 06);
        Calendar calendar8 = new GregorianCalendar(2019, 06, 24);
        Calendar calendar9 = new GregorianCalendar(2019, 06, 24);
        Calendar calendar10 = new GregorianCalendar(2019, 06, 23);
        Calendar calendar11 = new GregorianCalendar(2019, 06, 22);
        Calendar calendar12 = new GregorianCalendar(2019, 06, 21);
        Calendar calendar13 = new GregorianCalendar(2019, 06, 20);
        Calendar calendar14 = new GregorianCalendar(2019, 06, 19);
        Calendar calendar15 = new GregorianCalendar(2019, 06, 18);
        Calendar calendar16 = new GregorianCalendar(2019, 06, 17);
        Calendar calendar17 = new GregorianCalendar(2019, 06, 17);
        Calendar calendar18 = new GregorianCalendar(2019, 06, 16);
        Calendar calendar19 = new GregorianCalendar(2019, 06, 01);
        Calendar calendar20 = new GregorianCalendar(2019, 06, 04);

        db.execSQL("INSERT INTO " + Account.TABLE_NAME + "(name, description, amount, high_category, type, create_at, currency) VALUES(?, ?, ?, ?, ?, ?, ?)",
                new Object[]{"현금", "내돈", 150000, "cash", "asset", new Date(), "KRW"});
        db.execSQL("INSERT INTO " + Account.TABLE_NAME + "(name, description, amount, high_category, type, create_at, currency) VALUES(?, ?, ?, ?, ?, ?, ?)",
                new Object[]{"월급통장", "월급", 1250000, "checking_account", "asset", new Date(), "KRW"});
        db.execSQL("INSERT INTO " + Account.TABLE_NAME + "(name, description, amount, high_category, type, create_at, currency) VALUES(?, ?, ?, ?, ?, ?, ?)",
                new Object[]{"국민은행", "비상금통장", 2150000, "checking_account", "asset", new Date(), "KRW"});
        db.execSQL("INSERT INTO " + Account.TABLE_NAME + "(name, description, amount, high_category, type, create_at, currency) VALUES(?, ?, ?, ?, ?, ?, ?)",
                new Object[]{"대출통장", "전세자금대출", 2150000, "checking_account", "debt", new Date(), "KRW"});


        db.execSQL("INSERT INTO " + Transaction.TABLE_NAME + " (note, amount, type, pattern, date, category_id, sub_category_id, account_id, to_account) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)",
                new Object[]{"저녁(술)", 50000, "Expense", "Normal", dateFormat.format(calendar1.getTime()), ":food&drink", null, 1, null});
        db.execSQL("INSERT INTO " + Transaction.TABLE_NAME + " (note, amount, type, pattern, date, category_id, sub_category_id, account_id, to_account) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)",
                new Object[]{"피씨방", 3000, "Expense", "Waste", dateFormat.format(calendar2.getTime()), ":entertainment", null, 1, null});
        db.execSQL("INSERT INTO " + Transaction.TABLE_NAME + " (note, amount, type, pattern, date, category_id, sub_category_id, account_id, to_account) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)",
                new Object[]{"책", 29000, "Expense", "Invest", dateFormat.format(calendar3.getTime()), ":education", null, 1, null});
        db.execSQL("INSERT INTO " + Transaction.TABLE_NAME + " (note, amount, type, pattern, date, category_id, sub_category_id, account_id, to_account) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)",
                new Object[]{"김밥(아침)", 3500, "Expense", "Normal", dateFormat.format(calendar4.getTime()), ":food&drink", null, 1, null});
        db.execSQL("INSERT INTO " + Transaction.TABLE_NAME + " (note, amount, type, pattern, date, category_id, sub_category_id, account_id, to_account) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)",
                new Object[]{"버스", 1200, "Expense", "Normal", dateFormat.format(calendar5.getTime()), ":transportation", null, 1, null});
        db.execSQL("INSERT INTO " + Transaction.TABLE_NAME + " (note, amount, type, pattern, date, category_id, sub_category_id, account_id, to_account) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)",
                new Object[]{"햄버거(점심)", 5900, "Expense", "Waste", dateFormat.format(calendar6.getTime()), ":food&drink", null, 1, null});
        db.execSQL("INSERT INTO " + Transaction.TABLE_NAME + " (note, amount, type, pattern, date, category_id, sub_category_id, account_id, to_account) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)",
                new Object[]{"콜라", 1900, "Expense", "Normal", dateFormat.format(calendar7.getTime()), ":food&drink", null, 1, null});
        db.execSQL("INSERT INTO " + Transaction.TABLE_NAME + " (note, amount, type, pattern, date, category_id, sub_category_id, account_id, to_account) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)",
                new Object[]{"커피", 4100, "Expense", "Normal", dateFormat.format(calendar8.getTime()), ":food&drink", null, 1, null});
        db.execSQL("INSERT INTO " + Transaction.TABLE_NAME + " (note, amount, type, pattern, date, category_id, sub_category_id, account_id, to_account) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)",
                new Object[]{"노래방", 21000, "Expense", "Waste", dateFormat.format(calendar9.getTime()), ":entertainment", null, 1, null});
        db.execSQL("INSERT INTO " + Transaction.TABLE_NAME + " (note, amount, type, pattern, date, category_id, sub_category_id, account_id, to_account) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)",
                new Object[]{"택시비", 15000, "Expense", "Normal", dateFormat.format(calendar10.getTime()), ":food&drink", null, 1, null});
        db.execSQL("INSERT INTO " + Transaction.TABLE_NAME + " (note, amount, type, pattern, date, category_id, sub_category_id, account_id, to_account) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)",
                new Object[]{"쇼핑", 55000, "Expense", "Invest", dateFormat.format(calendar11.getTime()), ":clothing&beauty", null, 1, null});
        db.execSQL("INSERT INTO " + Transaction.TABLE_NAME + " (note, amount, type, pattern, date, category_id, sub_category_id, account_id, to_account) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)",
                new Object[]{"버스", 9500, "Expense", "Waste", dateFormat.format(calendar12.getTime()), ":transportation", null, 1, null});
        db.execSQL("INSERT INTO " + Transaction.TABLE_NAME + " (note, amount, type, pattern, date, category_id, sub_category_id, account_id, to_account) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)",
                new Object[]{"햄버거(점심)", 8900, "Expense", "Normal", dateFormat.format(calendar13.getTime()), ":food&drink", null, 1, null});
        db.execSQL("INSERT INTO " + Transaction.TABLE_NAME + " (note, amount, type, pattern, date, category_id, sub_category_id, account_id, to_account) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)",
                new Object[]{"콜라", 2100, "Expense", "Normal", dateFormat.format(calendar14.getTime()), ":food&drink", null, 1, null});
        db.execSQL("INSERT INTO " + Transaction.TABLE_NAME + " (note, amount, type, pattern, date, category_id, sub_category_id, account_id, to_account) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)",
                new Object[]{"커피", 2800, "Expense", "Invest", dateFormat.format(calendar15.getTime()), ":food&drink", null, 1, null});
        db.execSQL("INSERT INTO " + Transaction.TABLE_NAME + " (note, amount, type, pattern, date, category_id, sub_category_id, account_id, to_account) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)",
                new Object[]{"노래방", 32000, "Expense", "Normal", dateFormat.format(calendar16.getTime()), ":entertainment", null, 1, null});
        db.execSQL("INSERT INTO " + Transaction.TABLE_NAME + " (note, amount, type, pattern, date, category_id, sub_category_id, account_id, to_account) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)",
                new Object[]{"택시비", 7800, "Expense", "Normal", dateFormat.format(calendar17.getTime()), ":transportation", null, 1, null});
        db.execSQL("INSERT INTO " + Transaction.TABLE_NAME + " (note, amount, type, pattern, date, category_id, sub_category_id, account_id, to_account) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)",
                new Object[]{"쇼핑", 120000, "Expense", "Waste", dateFormat.format(calendar18.getTime()), ":clothing&beauty", null, 1, null});
        db.execSQL("INSERT INTO " + Transaction.TABLE_NAME + " (note, amount, type, pattern, date, category_id, sub_category_id, account_id, to_account) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)",
                new Object[]{"월급", 3000000, "Income", "", dateFormat.format(calendar19.getTime()), ":salary", null, 1, null});
        db.execSQL("INSERT INTO " + Transaction.TABLE_NAME + " (note, amount, type, pattern, date, category_id, sub_category_id, account_id, to_account) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)",
                new Object[]{"이자", 150000, "Income", "", dateFormat.format(calendar20.getTime()), ":interest&dividend", null, 1, null});

        db.execSQL("INSERT INTO " + Budget.TABLE_NAME + "(description, amount, start_date, end_date, account_id) VALUES(?, ?, ?, ?, ?)",
                new Object[]{"유럽여행", 5000000, "2019-08-01", "2019-12-31", 3});
        db.execSQL("INSERT INTO " + Budget.TABLE_NAME + "(description, amount, start_date, end_date, account_id) VALUES(?, ?, ?, ?, ?)",
                new Object[]{"현금모으기", 200000, "2019-08-01", "2019-10-31", 1});
        db.execSQL("INSERT INTO " + Budget.TABLE_NAME + "(description, amount, start_date, end_date, account_id) VALUES(?, ?, ?, ?, ?)",
                new Object[]{"차바꾸기", 400000, "2019-05-01", "2019-07-31", 1});

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ACCOUNT_TABLE);
        db.execSQL(SQL_DELETE_TRANSACTION_TABLE);
        db.execSQL(SQL_DELETE_BUDGET_TABLE);
        onCreate(db);
    }

    /*
    ============================================================================
    ACCOUNT
    ============================================================================
     */
    public boolean insertAccount(AccountModel accountModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Account.COL_NAME, accountModel.getName());
        contentValues.put(Account.COL_DESCRIPTION, accountModel.getDescription());
        contentValues.put(Account.COL_AMOUNT, accountModel.getAmount());
        contentValues.put(Account.COL_TYPE, accountModel.getType());
        contentValues.put(Account.COL_CREATE_AT, String.valueOf(accountModel.getCreateAt()));
        contentValues.put(Account.COL_CURRENCY, accountModel.getCurrency());

        long result = db.insert(Account.TABLE_NAME, null, contentValues);

        return result != -1;
    }

    /*
    ============================================================================
    BUDGET
    ============================================================================
     */
    public boolean insertBudget(BudgetModel budgetModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Budget.COL_DESCRIPTION, budgetModel.getDescription());
        contentValues.put(Budget.COL_AMOUNT, budgetModel.getAmount());
        contentValues.put(Budget.COL_START_DATE, budgetModel.getStartDate());
        contentValues.put(Budget.COL_END_DATE, String.valueOf(budgetModel.getEndDate()));
        contentValues.put(Budget.COL_ACCOUNT_ID, budgetModel.getAccountId());

        long result = db.insert(Budget.TABLE_NAME, null, contentValues);

        return result != -1;
    }

    public Cursor getRunningBudgets() {
        SQLiteDatabase db = this.getWritableDatabase();

        String today = Common.dateFormat.format(new Date());
        String[] args = {today};

        Cursor cursor = db.rawQuery("SELECT * FROM " + Budget.TABLE_NAME + " WHERE " + Budget.COL_END_DATE + " >= DATE(?)", args);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor getExpiredBudgets() {
        SQLiteDatabase db = this.getWritableDatabase();

        String today = Common.dateFormat.format(new Date());
        String[] args = {today};

        Cursor cursor = db.rawQuery("SELECT * FROM " + Budget.TABLE_NAME + " WHERE " + Budget.COL_END_DATE + " < DATE(?)", args);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }


    public boolean updateAccountsByTransfer(double amount, int fromId, int toId) {
        SQLiteDatabase db = this.getWritableDatabase();

        String fromQuery = "UPDATE " + Account.TABLE_NAME + " SET " + Account.COL_AMOUNT + " = " + Account.COL_AMOUNT + " - "
                + amount + " WHERE _id = " + fromId;

        String toQuery = "UPDATE " + Account.TABLE_NAME + " SET " + Account.COL_AMOUNT + " = " + Account.COL_AMOUNT + " + "
                + amount + " WHERE _id = " + toId;

        boolean result = false;

        try {
            db.beginTransaction();
            db.execSQL(fromQuery);
            db.execSQL(toQuery);
            db.setTransactionSuccessful();
            result = true;
        } catch (SQLException e) {

        } finally {
            db.endTransaction();
        }
        return result;
    }

    public boolean insertTransaction(TransactionModel transactionModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Transaction.COL_NOTE, transactionModel.getNote());
        contentValues.put(Transaction.COL_AMOUNT, transactionModel.getAmount());
        contentValues.put(Transaction.COL_TYPE, transactionModel.getType());
        contentValues.put(Transaction.COL_PATTERN, transactionModel.getPattern());
        contentValues.put(Transaction.COL_DATE, transactionModel.getDate());
        contentValues.put(Transaction.COL_CATEGORY_ID, transactionModel.getCategoryId());
        contentValues.put(Transaction.COL_SUB_CATEGORY_ID, transactionModel.getSubCategoryId());
        contentValues.put(Transaction.COL_ACCOUNT_ID, transactionModel.getAccountId());
        contentValues.put(Transaction.COL_TO_ACCOUNT, "");

        String query = "";
        if (transactionModel.getType().equals("Expense")) {
            query = "UPDATE " + Account.TABLE_NAME + " SET " + Account.COL_AMOUNT + " = " + Account.COL_AMOUNT + " - "
                    + transactionModel.getAmount() + " WHERE _id = " + transactionModel.getAccountId();
        } else if (transactionModel.getType().equals("Income")) {
            query = "UPDATE " + Account.TABLE_NAME + " SET " + Account.COL_AMOUNT + " = " + Account.COL_AMOUNT + " + "
                    + transactionModel.getAmount() + " WHERE _id = " + transactionModel.getAccountId();
        }

        long result = -1;
        try {
            db.beginTransaction();
            result = db.insert(Transaction.TABLE_NAME, null, contentValues);
            db.execSQL(query);
            db.setTransactionSuccessful(); // This commits the transaction if there were no exceptions
        } catch (SQLException e) {

        } finally {
            db.endTransaction();
        }


        return result != -1;
    }

    public int updateTransaction(TransactionModel transactionModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(Transaction.COL_NOTE, transactionModel.getNote());
        cv.put(Transaction.COL_AMOUNT, transactionModel.getAmount());
        cv.put(Transaction.COL_TYPE, transactionModel.getType());
        cv.put(Transaction.COL_PATTERN, transactionModel.getPattern());
        cv.put(Transaction.COL_DATE, transactionModel.getDate());
        cv.put(Transaction.COL_CATEGORY_ID, transactionModel.getCategoryId());
        cv.put(Transaction.COL_SUB_CATEGORY_ID, transactionModel.getSubCategoryId());
        cv.put(Transaction.COL_ACCOUNT_ID, transactionModel.getAccountId());
        cv.put(Transaction.COL_TO_ACCOUNT, "");

        int result = db.update(Transaction.TABLE_NAME, cv, "_id = " + transactionModel.getId(), null);

        String updateTransaction =  "UPDATE " + Transaction.TABLE_NAME + " SET "
                + Transaction.COL_NOTE + " = " + transactionModel.getNote() + ", "
                + Transaction.COL_AMOUNT + " = " + Transaction.COL_AMOUNT + " - " + transactionModel.getAmount() + ", "
                + Transaction.COL_TYPE + " = " + transactionModel.getType() + ", "
                + Transaction.COL_PATTERN + " = " + transactionModel.getType() + ", "
                + Transaction.COL_DATE + " = " + transactionModel.getDate() + ", "
                + Transaction.COL_CATEGORY_ID + " = " + transactionModel.getCategoryId() + ", "
                + Transaction.COL_SUB_CATEGORY_ID + " = " + transactionModel.getSubCategoryId() + ", "
                + Transaction.COL_ACCOUNT_ID + " = " + transactionModel.getAccountId() + ", "
                + Transaction.COL_TO_ACCOUNT + " = " + transactionModel.getToAccount()
                + " WHERE _id = " + transactionModel.getId();

        String query = "";
        if (transactionModel.getType().equals("Expense")) {
            query = "UPDATE " + Account.TABLE_NAME + " SET " + Account.COL_AMOUNT + " = " + Account.COL_AMOUNT + " - "
                    + transactionModel.getAmount() + " WHERE _id = " + transactionModel.getAccountId();
        }
        else if (transactionModel.getType().equals("Income")) {
            query = "UPDATE " + Account.TABLE_NAME + " SET " + Account.COL_AMOUNT + " = " + Account.COL_AMOUNT + " + "
                    + transactionModel.getAmount() + " WHERE _id = " + transactionModel.getAccountId();
        }

        db.execSQL(updateTransaction);
        db.execSQL(query);

        return result;
    }

    public int updateAccount(AccountModel accountModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Account.COL_NAME, accountModel.getName());
        contentValues.put(Account.COL_DESCRIPTION, accountModel.getDescription());
        contentValues.put(Account.COL_AMOUNT, accountModel.getAmount());
        contentValues.put(Account.COL_TYPE, accountModel.getType());
        contentValues.put(Account.COL_CREATE_AT, String.valueOf(accountModel.getCreateAt()));
        contentValues.put(Account.COL_CURRENCY, accountModel.getCurrency());

        int result = db.update(Account.TABLE_NAME, contentValues, "_id = " + accountModel.getId(), null);

        return result;
    }

    public int deleteAccount(AccountModel accountModel) {
        Log.d(TAG, "deleteAccount: id: "+accountModel.getId());
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(Account.TABLE_NAME, "_id = " + accountModel.getId(), null);
    }

    public int deleteTransaction(TransactionModel transactionModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d(TAG, "deleteTransaction: getId(): " + transactionModel.getId());
        return db.delete(Transaction.TABLE_NAME, "_id = " + transactionModel.getId(), null);
    }


    public Cursor getAccount(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Account.TABLE_NAME + " WHERE _id = " + id, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor getSumAccountsByType() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + Account.COL_TYPE + ", SUM(" + Account.COL_AMOUNT + ") AS sumByType FROM " + Account.TABLE_NAME
                + " GROUP BY " + Account.COL_TYPE;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor getAllAccounts() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Account.TABLE_NAME, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor getAccountsByType(String type) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + Account.TABLE_NAME
                + " WHERE " + Account.COL_TYPE + " = ?";
        String[] args = {type};
        Cursor cursor = db.rawQuery(query, args);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }


    public Cursor getAllCategories() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Category.TABLE_NAME, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor getAllTransactions() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Transaction.TABLE_NAME, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Cursor getThisWeekTransactions() {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] args = {Common.dateFormat.format(DateHelper.getWeekStartDate()), Common.dateFormat.format(DateHelper.getWeekEndDate())};
        Cursor cursor = db.rawQuery("SELECT * FROM " + Transaction.TABLE_NAME
                + " WHERE " + Transaction.COL_DATE + " BETWEEN DATE(?) AND DATE(?)", args);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor getLastFewDaysTransactions(String startDate, String endDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] args = {startDate, endDate};
        Cursor cursor = db.rawQuery("SELECT * FROM " + Transaction.TABLE_NAME
                + " WHERE " + Transaction.COL_DATE + " BETWEEN DATE(?) AND DATE(?)", args);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor getThisMonthTransactions(String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + Transaction.TABLE_NAME
                + " WHERE " + Transaction.COL_DATE + " BETWEEN DATE(?, 'start of month') AND DATE(?, 'start of month', '+1 months', '-1 day')"
                + " ORDER BY " + Transaction.COL_DATE + " DESC";
        ;
        String[] args = {date, date};
        Cursor cursor = db.rawQuery(query, args);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor getThisMonthTransactionsByAccount(String date, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + Transaction.TABLE_NAME
                + " WHERE " + Transaction.COL_DATE + " BETWEEN DATE(?, 'start of month') AND DATE(?, 'start of month', '+1 months', '-1 day')"
                + " AND " + Transaction.COL_ACCOUNT_ID + " = ?"
                + " ORDER BY " + Transaction.COL_DATE + " DESC";
        String[] args = {date, date, String.valueOf(id)};
        Cursor cursor = db.rawQuery(query, args);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor getThisMonthTransactionsPatternList(String date, String pattern) {
        Log.d(TAG, "getThisMonthTransactions: " + date);
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * "
                + " FROM " + Transaction.TABLE_NAME
                + " WHERE " + Transaction.COL_DATE + " BETWEEN DATE(?, 'start of month') AND DATE(?, 'start of month', '+1 months', '-1 day')"
                + " AND " + Transaction.COL_PATTERN + " LIKE ?"
                + " AND " + Transaction.COL_TYPE + " LIKE 'Expense'"
                + " ORDER BY " + Transaction.COL_DATE + " DESC";
        String[] args = {date, date, pattern};
        Cursor cursor = db.rawQuery(query, args);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor getThisMonthTransactionsByPattern(String date) {
        Log.d(TAG, "getThisMonthTransactionsByPattern: " + date);
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + Transaction.COL_PATTERN + ", SUM(" + Transaction.COL_AMOUNT + ") AS pattern_sum, COUNT(" + Transaction.COL_AMOUNT + ") AS pattern_count"
                + " FROM " + Transaction.TABLE_NAME
                + " WHERE " + Transaction.COL_DATE + " BETWEEN DATE(?, 'start of month') AND DATE(?, 'start of month', '+1 months', '-1 day')"
                + " AND " + Transaction.COL_TYPE + " LIKE 'Expense'"
                + " GROUP BY " + Transaction.COL_PATTERN;
        String[] args = {date, date};
        Cursor cursor = db.rawQuery(query, args);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor getThisMonthTransactionsByCategory(String date) {
        Log.d(TAG, "getThisMonthTransactionsByPattern: " + date);
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + Transaction.COL_CATEGORY_ID + ", SUM(" + Transaction.COL_AMOUNT + ") AS sum_by_category, COUNT(" + Transaction.COL_AMOUNT + ") AS count_by_category"
                + " FROM " + Transaction.TABLE_NAME
                + " WHERE " + Transaction.COL_DATE + " BETWEEN DATE(?, 'start of month') AND DATE(?, 'start of month', '+1 months', '-1 day')"
                + " AND " + Transaction.COL_TYPE + " LIKE 'Expense'"
                + " GROUP BY " + Transaction.COL_CATEGORY_ID;
        String[] args = {date, date};
        Cursor cursor = db.rawQuery(query, args);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

}
