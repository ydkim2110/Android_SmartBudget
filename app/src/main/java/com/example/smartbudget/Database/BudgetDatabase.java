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
                    + " VALUES ('현금', '내 현금', 120000, 'cash', 'asset', '2019-04-12', 'KRW')");
            db.execSQL("INSERT into account_table (name, description, amount, high_category, type, create_at, currency)"
                    + " VALUES ('하나은행', '월급통장', 2500000, 'checking_account', 'asset', '2017-08-11', 'KRW')");
            db.execSQL("INSERT into account_table (name, description, amount, high_category, type, create_at, currency)"
                    + " VALUES ('카카오뱅크', '비상금 통장', 4400000, 'checking_account', 'asset', '2018-04-21', 'KRW')");
            db.execSQL("INSERT into account_table (name, description, amount, high_category, type, create_at, currency)"
                    + " VALUES ('하나은행', '전세자금대출', 120000000, 'debt', 'debt', '2019-04-01', 'KRW')");

            db.execSQL("INSERT INTO transaction_table (description, amount, type, pattern, date, category_id, sub_category_id, account_id, to_account)"
                    + " VALUES ('샌드위치', 3000, 'Expense', 'Normal', '2019-08-07', ':food&drink', ':breakfast', 1, '')");
            db.execSQL("INSERT INTO transaction_table (description, amount, type, pattern, date, category_id, sub_category_id, account_id, to_account)"
                    + " VALUES ('감자탕', 15000, 'Expense', 'Normal', '2019-08-11', ':food&drink', ':dinner', 2, '')");
            db.execSQL("INSERT INTO transaction_table (description, amount, type, pattern, date, category_id, sub_category_id, account_id, to_account)"
                    + " VALUES ('술값', 50000, 'Expense', 'Waste', '2019-08-02', ':food&drink', ':dinner', 1, '')");
            db.execSQL("INSERT INTO transaction_table (description, amount, type, pattern, date, category_id, sub_category_id, account_id, to_account)"
                    + " VALUES ('커피', 5000, 'Expense', 'Normal', '2019-08-14', ':food&drink', ':coffee&beberage', 2, '')");
            db.execSQL("INSERT INTO transaction_table (description, amount, type, pattern, date, category_id, sub_category_id, account_id, to_account)"
                    + " VALUES ('냉면', 8000, 'Expense', 'Normal', '2019-08-05', ':food&drink', ':lunch', 1, '')");
            db.execSQL("INSERT INTO transaction_table (description, amount, type, pattern, date, category_id, sub_category_id, account_id, to_account)"
                    + " VALUES ('커피', 4100, 'Expense', 'Normal', '2019-08-12', ':food&drink', ':coffee&beberage', 2, '')");

            db.execSQL("INSERT INTO transaction_table (description, amount, type, pattern, date, category_id, sub_category_id, account_id, to_account)"
                    + " VALUES ('핸드폰비', 80000, 'Expense', 'Normal', '2019-08-12', ':communication', ':mobile', 2, '')");

            db.execSQL("INSERT INTO transaction_table (description, amount, type, pattern, date, category_id, sub_category_id, account_id, to_account)"
                    + " VALUES ('신발구매', 150000, 'Expense', 'Normal', '2019-08-07', ':clothing&beauty', ':shoes', 1, '')");

            db.execSQL("INSERT INTO transaction_table (description, amount, type, pattern, date, category_id, sub_category_id, account_id, to_account)"
                    + " VALUES ('이자', 210000, 'Expense', 'Normal', '2019-08-07', ':homeneeds', ':other', 1, '')");
            db.execSQL("INSERT INTO transaction_table (description, amount, type, pattern, date, category_id, sub_category_id, account_id, to_account)"
                    + " VALUES ('관리비', 50000, 'Expense', 'Normal', '2019-08-31', ':homeneeds', ':maintenance_charge', 1, '')");
            db.execSQL("INSERT INTO transaction_table (description, amount, type, pattern, date, category_id, sub_category_id, account_id, to_account)"
                    + " VALUES ('전기요금', 50000, 'Expense', 'Normal', '2019-08-31', ':homeneeds', ':electricity_bill', 1, '')");
            db.execSQL("INSERT INTO transaction_table (description, amount, type, pattern, date, category_id, sub_category_id, account_id, to_account)"
                    + " VALUES ('수도요금', 50000, 'Expense', 'Normal', '2019-08-31', ':homeneeds', ':water_bill', 1, '')");
            db.execSQL("INSERT INTO transaction_table (description, amount, type, pattern, date, category_id, sub_category_id, account_id, to_account)"
                    + " VALUES ('가스비', 50000, 'Expense', 'Normal', '2019-08-31', ':homeneeds', ':gas_bill', 1, '')");

            db.execSQL("INSERT INTO transaction_table (description, amount, type, pattern, date, category_id, sub_category_id, account_id, to_account)"
                    + " VALUES ('숙박', 320000, 'Expense', 'Normal', '2019-08-05', ':travel', ':other', 2, '')");
            db.execSQL("INSERT INTO transaction_table (description, amount, type, pattern, date, category_id, sub_category_id, account_id, to_account)"
                    + " VALUES ('숙박', 320000, 'Expense', 'Normal', '2019-08-05', ':travel', ':other', 2, '')");

            db.execSQL("INSERT INTO transaction_table (description, amount, type, pattern, date, category_id, sub_category_id, account_id, to_account)"
                    + " VALUES ('영화', 18000, 'Expense', 'Normal', '2019-08-11', ':entertainment', ':movie', 2, '')");
            db.execSQL("INSERT INTO transaction_table (description, amount, type, pattern, date, category_id, sub_category_id, account_id, to_account)"
                    + " VALUES ('피씨방', 8000, 'Expense', 'Normal', '2019-08-11', ':entertainment', ':game', 2, '')");
            db.execSQL("INSERT INTO transaction_table (description, amount, type, pattern, date, category_id, sub_category_id, account_id, to_account)"
                    + " VALUES ('인터넷 강의', 140000, 'Expense', 'Normal', '2019-08-24', ':education', ':lecture', 2, '')");

            db.execSQL("INSERT INTO transaction_table (description, amount, type, pattern, date, category_id, sub_category_id, account_id, to_account)"
                    + " VALUES ('버스', 12000, 'Expense', 'Normal', '2019-08-24', ':transportation', ':bus', 2, '')");
            db.execSQL("INSERT INTO transaction_table (description, amount, type, pattern, date, category_id, sub_category_id, account_id, to_account)"
                    + " VALUES ('택시', 15400, 'Expense', 'Normal', '2019-08-04', ':transportation', ':taxi', 2, '')");
            db.execSQL("INSERT INTO transaction_table (description, amount, type, pattern, date, category_id, sub_category_id, account_id, to_account)"
                    + " VALUES ('택시', 4500, 'Expense', 'Normal', '2019-08-25', ':transportation', ':taxi', 2, '')");

            db.execSQL("INSERT INTO transaction_table (description, amount, type, pattern, date, category_id, sub_category_id, account_id, to_account)"
                    + " VALUES ('월급', 4000000, 'Income', '', '2019-08-21', ':salary', ':other', 2, '')");
            db.execSQL("INSERT INTO transaction_table (description, amount, type, pattern, date, category_id, sub_category_id, account_id, to_account)"
                    + " VALUES ('월급', 2000000, 'Income', '', '2019-04-22', ':salary', ':other', 1, '')");
            db.execSQL("INSERT INTO transaction_table (description, amount, type, pattern, date, category_id, sub_category_id, account_id, to_account)"
                    + " VALUES ('월급', 1000000, 'Income', '', '2019-10-21', ':salary', ':other', 1, '')");
            db.execSQL("INSERT INTO transaction_table (description, amount, type, pattern, date, category_id, sub_category_id, account_id, to_account)"
                    + " VALUES ('배당', 444444, 'Income', 'Normal', '2019-05-02', ':interest&dividend', ':other', 1, '')");

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
