package com.example.smartbudget.Database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.smartbudget.Database.AccountRoom.AccountDAO;
import com.example.smartbudget.Database.AccountRoom.AccountItem;
import com.example.smartbudget.Database.BudgetRoom.BudgetDAO;
import com.example.smartbudget.Database.BudgetRoom.BudgetItem;
import com.example.smartbudget.Database.TransactionRoom.TransactionDAO;
import com.example.smartbudget.Database.TransactionRoom.TransactionItem;

@Database(version = 1, entities = {AccountItem.class, TransactionItem.class, BudgetItem.class}, exportSchema = false)
public abstract class BudgetDatabase extends RoomDatabase {

    public abstract AccountDAO accountDAO();
    public abstract TransactionDAO transactionDAO();
    public abstract BudgetDAO budgetDAO();

    private static BudgetDatabase instance;

    public static BudgetDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, BudgetDatabase.class, "mySmartBudget")
                    .addCallback(mCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback mCallback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            db.execSQL("INSERT into account_table (name, description, amount, high_category, type, create_at, currency)"
                    + " VALUES ('국민은행', '예금통장', 1200000, 'checking_account', 'debt', '2019-07-12', 'KRW')");
            db.execSQL("INSERT into account_table (name, description, amount, high_category, type, create_at, currency)"
                    + " VALUES ('현금', '내 지갑', 250000, 'cash', 'asset', '2019-08-11', 'KRW')");
            db.execSQL("INSERT into account_table (name, description, amount, high_category, type, create_at, currency)"
                    + " VALUES ('현금2', '내 지갑2', 240000, 'cash', 'asset', '2019-08-12', 'KRW')");

            db.execSQL("INSERT INTO transaction_table (description, amount, type, pattern, date, category_id, sub_category_id, account_id, to_account)"
                    + " VALUES ('술값', 50000, 'Expense', 'Invest', '2019-08-07', ':food&drink', ':breakfast', 1, '')");
            db.execSQL("INSERT INTO transaction_table (description, amount, type, pattern, date, category_id, sub_category_id, account_id, to_account)"
                    + " VALUES ('방값', 150000, 'Expense', 'Invest', '2019-08-11', ':food&drink', ':breakfast', 1, '')");
            db.execSQL("INSERT INTO transaction_table (description, amount, type, pattern, date, category_id, sub_category_id, account_id, to_account)"
                    + " VALUES ('핸드폰비', 80000, 'Expense', 'Normal', '2019-08-12', ':food&drink', ':breakfast', 2, '')");
            db.execSQL("INSERT INTO transaction_table (description, amount, type, pattern, date, category_id, sub_category_id, account_id, to_account)"
                    + " VALUES ('월급', 4000000, 'Income', '', '2019-08-21', ':salary', '', 2, '')");
            db.execSQL("INSERT INTO transaction_table (description, amount, type, pattern, date, category_id, sub_category_id, account_id, to_account)"
                    + " VALUES ('월급', 2000000, 'Income', '', '2019-04-22', ':salary', '', 1, '')");
            db.execSQL("INSERT INTO transaction_table (description, amount, type, pattern, date, category_id, sub_category_id, account_id, to_account)"
                    + " VALUES ('월급', 1000000, 'Income', '', '2019-10-21', ':salary', '', 1, '')");
            db.execSQL("INSERT INTO transaction_table (description, amount, type, pattern, date, category_id, sub_category_id, account_id, to_account)"
                    + " VALUES ('술값', 50000, 'Expense', 'Waste', '2019-08-02', ':food&drink', ':breakfast', 1, '')");
            db.execSQL("INSERT INTO transaction_table (description, amount, type, pattern, date, category_id, sub_category_id, account_id, to_account)"
                    + " VALUES ('술값', 50000, 'Expense', 'Waste', '2019-08-04', ':food&drink', ':breakfast', 2, '')");
            db.execSQL("INSERT INTO transaction_table (description, amount, type, pattern, date, category_id, sub_category_id, account_id, to_account)"
                    + " VALUES ('술값', 50000, 'Expense', 'Waste', '2019-08-05', ':food&drink', ':breakfast', 1, '')");
            db.execSQL("INSERT INTO transaction_table (description, amount, type, pattern, date, category_id, sub_category_id, account_id, to_account)"
                    + " VALUES ('술값', 50000, 'Expense', 'Waste', '2019-08-07', ':food&drink', ':breakfast', 1, '')");
            db.execSQL("INSERT INTO transaction_table (description, amount, type, pattern, date, category_id, sub_category_id, account_id, to_account)"
                    + " VALUES ('이자', 210000, 'Expense', 'Normal', '2019-07-07', ':homeneeds', ':breakfast', 1, '')");
            db.execSQL("INSERT INTO transaction_table (description, amount, type, pattern, date, category_id, sub_category_id, account_id, to_account)"
                    + " VALUES ('숙박', 320000, 'Expense', 'Normal', '2019-06-05', ':travel', ':breakfast', 1, '')");
            db.execSQL("INSERT INTO transaction_table (description, amount, type, pattern, date, category_id, sub_category_id, account_id, to_account)"
                    + " VALUES ('배당', 444444, 'Income', 'Normal', '2019-05-02', ':salary', '', 1, '')");

            db.execSQL("INSERT INTO budget_table (description, amount, start_date, end_date, type, account_id) VALUES(?, ?, ?, ?, ?, ?)",
                    new Object[]{"유럽여행", 5000000, "2019-08-01", "2019-12-31", "Income", 2});
            db.execSQL("INSERT INTO budget_table (description, amount, start_date, end_date, type, account_id) VALUES(?, ?, ?, ?, ?, ?)",
                    new Object[]{"현금모으기", 200000, "2019-08-01", "2019-8-31", "Income", 1});
            db.execSQL("INSERT INTO budget_table (description, amount, start_date, end_date, type, account_id) VALUES(?, ?, ?, ?, ?, ?)",
                    new Object[]{"차바꾸기", 400000, "2019-05-01", "2019-07-31", "Income", 1});
            db.execSQL("INSERT INTO budget_table (description, amount, start_date, end_date, type, account_id) VALUES(?, ?, ?, ?, ?, ?)",
                    new Object[]{"밥값줄여보기", 900000, "2019-08-01", "2019-09-30", "Expense", 1});

        }
    };

}
