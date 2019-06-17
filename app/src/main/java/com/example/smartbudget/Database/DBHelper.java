package com.example.smartbudget.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.example.smartbudget.Database.Model.AccountModel;
import com.example.smartbudget.R;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.smartbudget.Database.DBContract.*;

public class DBHelper extends SQLiteOpenHelper {

    private static final String TAG = DBHelper.class.getSimpleName();

    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "smartbudget.db";

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
                    Transaction.COL_DESCRIPTION + " TEXT," +
                    Transaction.COL_AMOUNT + " INTEGER," +
                    Transaction.COL_TYPE + " TEXT," +
                    Transaction.COL_DATE + " DATE," +
                    Transaction.COL_CATEGORY_ID + " INTEGER," +
                    Transaction.COL_ACCOUNT_ID + " INTEGER ," +
                    Transaction.COL_TO_ACCOUNT + " INT REFERENCES "+Account.TABLE_NAME+ "(_id)," +
                    "FOREIGN KEY ("+Transaction.COL_CATEGORY_ID+") REFERENCES "+Category.TABLE_NAME+"(_id), " +
                    "FOREIGN KEY ("+Transaction.COL_ACCOUNT_ID+") REFERENCES "+Account.TABLE_NAME+"(_id))";

    private static final String SQL_DELETE_ACCOUNT_TABLE =
            "DROP TABLE IF EXISTS " + Account.TABLE_NAME;

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ACCOUNT_TABLE);
        db.execSQL(SQL_CREATE_CATEGORY_TABLE);
        db.execSQL(SQL_CREATE_SUBCATEGORY_TABLE);
        db.execSQL(SQL_CREATE_TRANSACTION_TABLE);

//        db.execSQL("INSERT INTO " + Account.TABLE_NAME + " (account_name, account_amount, account_type, account_create_at, account_currency) VALUES(?, ?, ?, ?, ?)",
//                new Object[]{"국민은행", 4000000, "수시입출금", new Date(), "KRW"});
//        db.execSQL("INSERT INTO " + Account.TABLE_NAME + " (account_name, account_amount, account_type, account_create_at, account_currency) VALUES(?, ?, ?, ?, ?)",
//                new Object[]{"신한은행", 3000000, "예금", new Date(), "KRW"});
//        db.execSQL("INSERT INTO " + Account.TABLE_NAME + " (account_name, account_amount, account_type, account_create_at, account_currency) VALUES(?, ?, ?, ?, ?)",
//                new Object[]{"한국투자증권", 13000000, "주식", new Date(), "KRW"});

        db.execSQL("INSERT INTO "+ Category.TABLE_NAME+" (category_name, category_icon) VALUES(?, ?)", new Object[]{"Food", R.drawable.ic_directions_bus_black_24dp});
        db.execSQL("INSERT INTO "+ Category.TABLE_NAME+" (category_name, category_icon) VALUES(?, ?)", new Object[]{"Transport", R.drawable.ic_directions_bus_black_24dp});
        db.execSQL("INSERT INTO "+ Category.TABLE_NAME+" (category_name, category_icon) VALUES(?, ?)", new Object[]{"Clothing", R.drawable.ic_directions_bus_black_24dp});
        db.execSQL("INSERT INTO "+ Category.TABLE_NAME+" (category_name, category_icon) VALUES(?, ?)", new Object[]{"Entertainment", R.drawable.ic_directions_bus_black_24dp});
        db.execSQL("INSERT INTO "+ Category.TABLE_NAME+" (category_name, category_icon) VALUES(?, ?)", new Object[]{"Household", R.drawable.ic_directions_bus_black_24dp});
        db.execSQL("INSERT INTO "+ Category.TABLE_NAME+" (category_name, category_icon) VALUES(?, ?)", new Object[]{"Bills", R.drawable.ic_directions_bus_black_24dp});
        db.execSQL("INSERT INTO "+ Category.TABLE_NAME+" (category_name, category_icon) VALUES(?, ?)", new Object[]{"Healthcare", R.drawable.ic_directions_bus_black_24dp});
        db.execSQL("INSERT INTO "+ Category.TABLE_NAME+" (category_name, category_icon) VALUES(?, ?)", new Object[]{"Other Expenses", R.drawable.ic_directions_bus_black_24dp});
        db.execSQL("INSERT INTO "+ Category.TABLE_NAME+" (category_name, category_icon) VALUES(?, ?)", new Object[]{"Income", R.drawable.ic_directions_bus_black_24dp});
        db.execSQL("INSERT INTO "+ Category.TABLE_NAME+" (category_name, category_icon) VALUES(?, ?)", new Object[]{"Transfer", R.drawable.ic_directions_bus_black_24dp});


        db.execSQL("INSERT INTO "+Transaction.TABLE_NAME+" (transaction_description, transaction_amount, transaction_type, transaction_date, category_id, account_id, to_account) VALUES(?, ?, ?, ?, ?, ?, ?)",
                new Object[]{"택시비", 40000, "Expense", new Date(), 2, 1, null});
        db.execSQL("INSERT INTO "+Transaction.TABLE_NAME+" (transaction_description, transaction_amount, transaction_type, transaction_date, category_id, account_id, to_account) VALUES(?, ?, ?, ?, ?, ?, ?)",
                new Object[]{"택시비", 40000, "Expense", new Date(), 2, 1, null});
        db.execSQL("INSERT INTO "+Transaction.TABLE_NAME+" (transaction_description, transaction_amount, transaction_type, transaction_date, category_id, account_id, to_account) VALUES(?, ?, ?, ?, ?, ?, ?)",
                new Object[]{"택시비", 40000, "Expense", new Date(), 2, 1, null});
        db.execSQL("INSERT INTO "+Transaction.TABLE_NAME+" (transaction_description, transaction_amount, transaction_type, transaction_date, category_id, account_id, to_account) VALUES(?, ?, ?, ?, ?, ?, ?)",
                new Object[]{"택시비", 40000, "Expense", new Date(), 2, 1, null});
        db.execSQL("INSERT INTO "+Transaction.TABLE_NAME+" (transaction_description, transaction_amount, transaction_type, transaction_date, category_id, account_id, to_account) VALUES(?, ?, ?, ?, ?, ?, ?)",
                new Object[]{"택시비", 40000, "Expense", new Date(), 2, 1, null});
        db.execSQL("INSERT INTO "+Transaction.TABLE_NAME+" (transaction_description, transaction_amount, transaction_type, transaction_date, category_id, account_id, to_account) VALUES(?, ?, ?, ?, ?, ?, ?)",
                new Object[]{"택시비", 40000, "Expense", new Date(), 2, 1, null});
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ACCOUNT_TABLE);
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

    public int updateAccount(AccountModel accountModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        Log.d(TAG, "updateAccount:getId "+accountModel.getId());
        Log.d(TAG, "updateAccount:getAccount_name "+accountModel.getAccount_name());

        ContentValues contentValues = new ContentValues();
        contentValues.put(Account.COL_NAME, accountModel.getAccount_name());
        contentValues.put(Account.COL_DESCRIPTION, accountModel.getAccount_description());
        contentValues.put(Account.COL_AMOUNT, accountModel.getAccount_amount());
        contentValues.put(Account.COL_TYPE, accountModel.getAccount_type());
        contentValues.put(Account.COL_CREATE_AT, String.valueOf(accountModel.getAccount_create_at()));
        contentValues.put(Account.COL_CURRENCY, accountModel.getAccount_currency());

        int result = db.update(Account.TABLE_NAME, contentValues, "_id = "+accountModel.getId(), null);

        return result;
    }

    public int deleteAccount(AccountModel accountModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        return db.delete(Account.TABLE_NAME, "_id = "+accountModel.getId(), null);
    }

    public Cursor getAllAccounts() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+Account.TABLE_NAME, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor getAllCategories() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+Category.TABLE_NAME, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor getAllTransactions() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+Transaction.TABLE_NAME, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }
}
