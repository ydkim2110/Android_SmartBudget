package com.example.smartbudget.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.smartbudget.Model.AccountModel;
import com.example.smartbudget.Model.TransactionModel;
import com.example.smartbudget.Utils.Common;
import com.example.smartbudget.Utils.DateHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static com.example.smartbudget.Database.DBContract.*;

public class DBHelper extends SQLiteOpenHelper {

    private static final String TAG = DBHelper.class.getSimpleName();

    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "smartbudget2.db";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    private static final String SQL_CREATE_ACCOUNT_TABLE =
            "CREATE TABLE " + Account.TABLE_NAME + " (" +
                    Account._ID + " INTEGER PRIMARY KEY, " +
                    Account.COL_NAME + " TEXT, " +
                    Account.COL_DESCRIPTION + " TEXT, " +
                    Account.COL_AMOUNT + " INTEGER, " +
                    Account.COL_TYPE + " TEXT, " +
                    Account.COL_CREATE_AT + " DATE, " +
                    Account.COL_CURRENCY + " TEXT)";

    private static final String SQL_CREATE_CATEGORY_TABLE =
            "CREATE TABLE " + Category.TABLE_NAME + " (" +
                    Category._ID + " INTEGER PRIMARY KEY," +
                    Category.COL_NAME + " TEXT," +
                    Category.COL_ICON + " INTEGER)";

    private static final String SQL_CREATE_SUBCATEGORY_TABLE =
            "CREATE TABLE " + SubCategory.TABLE_NAME + " (" +
                    SubCategory._ID + " INTEGER PRIMARY KEY, " +
                    SubCategory.COL_NAME + " TEXT, " +
                    SubCategory.COL_ICON + " INTEGER, " +
                    SubCategory.COL_CATEGORY_ID + " INTEGER)";

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
                    "FOREIGN KEY (" + Transaction.COL_CATEGORY_ID + ") REFERENCES " + Category.TABLE_NAME + "(_id), " +
                    "FOREIGN KEY (" + Transaction.COL_ACCOUNT_ID + ") REFERENCES " + Account.TABLE_NAME + "(_id))";

    private static final String SQL_DELETE_ACCOUNT_TABLE =
            "DROP TABLE IF EXISTS " + Account.TABLE_NAME;
    private static final String SQL_DELETE_TRANSACTION_TABLE =
            "DROP TABLE IF EXISTS " + Transaction.TABLE_NAME;

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ACCOUNT_TABLE);
        db.execSQL(SQL_CREATE_CATEGORY_TABLE);
        db.execSQL(SQL_CREATE_SUBCATEGORY_TABLE);
        db.execSQL(SQL_CREATE_TRANSACTION_TABLE);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        Calendar calendar1 = new GregorianCalendar(2019,05, 01);
        Calendar calendar2 = new GregorianCalendar(2019,06, 02);
        Calendar calendar3 = new GregorianCalendar(2019,06, 03);
        Calendar calendar4 = new GregorianCalendar(2019,06, 04);
        Calendar calendar5 = new GregorianCalendar(2019,06, 04);
        Calendar calendar6 = new GregorianCalendar(2019,06, 05);
        Calendar calendar7 = new GregorianCalendar(2019,06, 06);
        Calendar calendar8 = new GregorianCalendar(2019,06, 24);
        Calendar calendar9 = new GregorianCalendar(2019,06, 24);
        Calendar calendar10 = new GregorianCalendar(2019,06, 23);
        Calendar calendar11 = new GregorianCalendar(2019,06, 22);
        Calendar calendar12 = new GregorianCalendar(2019,06, 21);
        Calendar calendar13 = new GregorianCalendar(2019,06, 20);
        Calendar calendar14 = new GregorianCalendar(2019,06, 19);
        Calendar calendar15 = new GregorianCalendar(2019,06, 18);
        Calendar calendar16 = new GregorianCalendar(2019,06, 17);
        Calendar calendar17 = new GregorianCalendar(2019,06, 17);
        Calendar calendar18 = new GregorianCalendar(2019,06, 16);

        db.execSQL("INSERT INTO " + Transaction.TABLE_NAME + " (transaction_note, transaction_amount, transaction_type, transaction_pattern, transaction_date, category_id, sub_category_id, account_id, to_account) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)",
                new Object[]{"저녁(술)", 50000, "Expense", "Normal", dateFormat.format(calendar1.getTime()), ":food&drink", null, 1, null});
        db.execSQL("INSERT INTO " + Transaction.TABLE_NAME + " (transaction_note, transaction_amount, transaction_type, transaction_pattern, transaction_date, category_id, sub_category_id, account_id, to_account) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)",
                new Object[]{"피씨방", 3000, "Expense", "Waste", dateFormat.format(calendar2.getTime()), ":entertainment", null, 1, null});
        db.execSQL("INSERT INTO " + Transaction.TABLE_NAME + " (transaction_note, transaction_amount, transaction_type, transaction_pattern, transaction_date, category_id, sub_category_id, account_id, to_account) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)",
                new Object[]{"책", 29000, "Expense", "Invest", dateFormat.format(calendar3.getTime()), ":education", null, 1, null});
        db.execSQL("INSERT INTO " + Transaction.TABLE_NAME + " (transaction_note, transaction_amount, transaction_type, transaction_pattern, transaction_date, category_id, sub_category_id, account_id, to_account) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)",
                new Object[]{"김밥(아침)", 3500, "Expense", "Normal", dateFormat.format(calendar4.getTime()), ":food&drink", null, 1, null});
        db.execSQL("INSERT INTO " + Transaction.TABLE_NAME + " (transaction_note, transaction_amount, transaction_type, transaction_pattern, transaction_date, category_id, sub_category_id, account_id, to_account) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)",
                new Object[]{"버스", 1200, "Expense", "Normal", dateFormat.format(calendar5.getTime()), ":transportation", null, 1, null});
        db.execSQL("INSERT INTO " + Transaction.TABLE_NAME + " (transaction_note, transaction_amount, transaction_type, transaction_pattern, transaction_date, category_id, sub_category_id, account_id, to_account) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)",
                new Object[]{"햄버거(점심)", 5900, "Expense", "Waste", dateFormat.format(calendar6.getTime()), ":food&drink", null, 1, null});
        db.execSQL("INSERT INTO " + Transaction.TABLE_NAME + " (transaction_note, transaction_amount, transaction_type, transaction_pattern, transaction_date, category_id, sub_category_id, account_id, to_account) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)",
                new Object[]{"콜라", 1900, "Expense", "Normal", dateFormat.format(calendar7.getTime()), ":food&drink", null, 1, null});
        db.execSQL("INSERT INTO " + Transaction.TABLE_NAME + " (transaction_note, transaction_amount, transaction_type, transaction_pattern, transaction_date, category_id, sub_category_id, account_id, to_account) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)",
                new Object[]{"커피", 4100, "Expense", "Normal", dateFormat.format(calendar8.getTime()), ":food&drink", null, 1, null});
        db.execSQL("INSERT INTO " + Transaction.TABLE_NAME + " (transaction_note, transaction_amount, transaction_type, transaction_pattern, transaction_date, category_id, sub_category_id, account_id, to_account) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)",
                new Object[]{"노래방", 21000, "Expense", "Waste", dateFormat.format(calendar9.getTime()), ":entertainment", null, 1, null});
        db.execSQL("INSERT INTO " + Transaction.TABLE_NAME + " (transaction_note, transaction_amount, transaction_type, transaction_pattern, transaction_date, category_id, sub_category_id, account_id, to_account) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)",
                new Object[]{"택시비", 15000, "Expense", "Normal", dateFormat.format(calendar10.getTime()), ":food&drink", null, 1, null});
        db.execSQL("INSERT INTO " + Transaction.TABLE_NAME + " (transaction_note, transaction_amount, transaction_type, transaction_pattern, transaction_date, category_id, sub_category_id, account_id, to_account) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)",
                new Object[]{"쇼핑", 55000, "Expense", "Invest", dateFormat.format(calendar11.getTime()), ":clothing&beauty", null, 1, null});
        db.execSQL("INSERT INTO " + Transaction.TABLE_NAME + " (transaction_note, transaction_amount, transaction_type, transaction_pattern, transaction_date, category_id, sub_category_id, account_id, to_account) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)",
                new Object[]{"버스", 9500, "Expense", "Waste", dateFormat.format(calendar12.getTime()), ":transportation", null, 1, null});
        db.execSQL("INSERT INTO " + Transaction.TABLE_NAME + " (transaction_note, transaction_amount, transaction_type, transaction_pattern, transaction_date, category_id, sub_category_id, account_id, to_account) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)",
                new Object[]{"햄버거(점심)", 8900, "Expense", "Normal", dateFormat.format(calendar13.getTime()), ":food&drink", null, 1, null});
        db.execSQL("INSERT INTO " + Transaction.TABLE_NAME + " (transaction_note, transaction_amount, transaction_type, transaction_pattern, transaction_date, category_id, sub_category_id, account_id, to_account) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)",
                new Object[]{"콜라", 2100, "Expense", "Normal", dateFormat.format(calendar14.getTime()), ":food&drink", null, 1, null});
        db.execSQL("INSERT INTO " + Transaction.TABLE_NAME + " (transaction_note, transaction_amount, transaction_type, transaction_pattern, transaction_date, category_id, sub_category_id, account_id, to_account) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)",
                new Object[]{"커피", 2800, "Expense", "Invest", dateFormat.format(calendar15.getTime()), ":food&drink", null, 1, null});
        db.execSQL("INSERT INTO " + Transaction.TABLE_NAME + " (transaction_note, transaction_amount, transaction_type, transaction_pattern, transaction_date, category_id, sub_category_id, account_id, to_account) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)",
                new Object[]{"노래방", 32000, "Expense", "Normal", dateFormat.format(calendar16.getTime()), ":entertainment", null, 1, null});
        db.execSQL("INSERT INTO " + Transaction.TABLE_NAME + " (transaction_note, transaction_amount, transaction_type, transaction_pattern, transaction_date, category_id, sub_category_id, account_id, to_account) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)",
                new Object[]{"택시비", 7800, "Expense", "Normal", dateFormat.format(calendar17.getTime()), ":transportation", null, 1, null});
        db.execSQL("INSERT INTO " + Transaction.TABLE_NAME + " (transaction_note, transaction_amount, transaction_type, transaction_pattern, transaction_date, category_id, sub_category_id, account_id, to_account) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)",
                new Object[]{"쇼핑", 120000, "Expense", "Waste", dateFormat.format(calendar18.getTime()), ":clothing&beauty", null, 1, null});
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ACCOUNT_TABLE);
        db.execSQL(SQL_DELETE_TRANSACTION_TABLE);
        onCreate(db);
    }

    public boolean insertToAccount(AccountModel accountModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Account.COL_NAME, accountModel.getAccount_name());
        contentValues.put(Account.COL_DESCRIPTION, accountModel.getAccount_description());
        contentValues.put(Account.COL_AMOUNT, accountModel.getAccount_amount());
        contentValues.put(Account.COL_TYPE, accountModel.getAccount_type());
        contentValues.put(Account.COL_CREATE_AT, String.valueOf(accountModel.getAccount_create_at()));
        contentValues.put(Account.COL_CURRENCY, accountModel.getAccount_currency());

        long result = db.insert(Account.TABLE_NAME, null, contentValues);

        return result != -1;
    }

    public boolean insertToTransaction(TransactionModel transactionModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Transaction.COL_NOTE, transactionModel.getTransaction_note());
        contentValues.put(Transaction.COL_AMOUNT, transactionModel.getTransaction_amount());
        contentValues.put(Transaction.COL_TYPE, transactionModel.getTransaction_type());
        contentValues.put(Transaction.COL_PATTERN, transactionModel.getTransaction_pattern());
        contentValues.put(Transaction.COL_DATE, transactionModel.getTransaction_date());
        contentValues.put(Transaction.COL_CATEGORY_ID, transactionModel.getCategory_id());
        contentValues.put(Transaction.COL_SUB_CATEGORY_ID, transactionModel.getSub_category_id());
        contentValues.put(Transaction.COL_ACCOUNT_ID, transactionModel.getAccount_id());
        contentValues.put(Transaction.COL_TO_ACCOUNT, "");

        long result = db.insert(Transaction.TABLE_NAME, null, contentValues);

        return result != -1;
    }

    public int updateAccount(AccountModel accountModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        Log.d(TAG, "updateAccount:getId " + accountModel.getId());
        Log.d(TAG, "updateAccount:getAccount_name " + accountModel.getAccount_name());

        ContentValues contentValues = new ContentValues();
        contentValues.put(Account.COL_NAME, accountModel.getAccount_name());
        contentValues.put(Account.COL_DESCRIPTION, accountModel.getAccount_description());
        contentValues.put(Account.COL_AMOUNT, accountModel.getAccount_amount());
        contentValues.put(Account.COL_TYPE, accountModel.getAccount_type());
        contentValues.put(Account.COL_CREATE_AT, String.valueOf(accountModel.getAccount_create_at()));
        contentValues.put(Account.COL_CURRENCY, accountModel.getAccount_currency());

        int result = db.update(Account.TABLE_NAME, contentValues, "_id = " + accountModel.getId(), null);

        return result;
    }

    public int deleteAccount(AccountModel accountModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        return db.delete(Account.TABLE_NAME, "_id = " + accountModel.getId(), null);
    }

    public Cursor getAllAccounts() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Account.TABLE_NAME, null);
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
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String[] args = {dateFormat.format(DateHelper.getWeekStartDate()), dateFormat.format(DateHelper.getWeekEndDate())};
        Cursor cursor = db.rawQuery("SELECT * FROM " + Transaction.TABLE_NAME
                + " WHERE " + Transaction.COL_DATE + " BETWEEN DATE(?) AND DATE(?)" , args);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor getThisMonthTransactions(String date) {
        Log.d(TAG, "getThisMonthTransactions: "+date);
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + Transaction.TABLE_NAME
                + " WHERE " + Transaction.COL_DATE + " BETWEEN DATE(?, 'start of month') AND DATE(?, 'start of month', '+1 months', '-1 day')"
                + " ORDER BY "+Transaction.COL_DATE+" DESC";;
        String[] args = {date, date};
        Cursor cursor = db.rawQuery(query, args);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor getThisMonthTransactionsPatternList(String date, String pattern) {
        Log.d(TAG, "getThisMonthTransactions: "+date);
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * "
                + " FROM "+Transaction.TABLE_NAME
                + " WHERE "+Transaction.COL_DATE+" BETWEEN DATE(?, 'start of month') AND DATE(?, 'start of month', '+1 months', '-1 day')"
                + " AND "+Transaction.COL_PATTERN+" LIKE ?"
                + " ORDER BY "+Transaction.COL_DATE+" DESC";
        String[] args = {date, date, pattern};
        Cursor cursor = db.rawQuery(query, args);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor getThisMonthTransactionPattern(String date) {
        Log.d(TAG, "getThisMonthTransactionPattern: "+date);
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT "+Transaction.COL_PATTERN+", SUM("+Transaction.COL_AMOUNT+") AS pattern_sum, COUNT("+Transaction.COL_AMOUNT+") AS pattern_count"
                + " FROM " + Transaction.TABLE_NAME
                + " WHERE " + Transaction.COL_DATE + " BETWEEN DATE(?, 'start of month') AND DATE(?, 'start of month', '+1 months', '-1 day')"
                + " GROUP BY " + Transaction.COL_PATTERN;
        String[] args = {date, date};
        Cursor cursor = db.rawQuery(query, args);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

}
