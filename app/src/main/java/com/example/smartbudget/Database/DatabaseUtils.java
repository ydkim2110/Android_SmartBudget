package com.example.smartbudget.Database;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.smartbudget.Interface.IAccountLoadListener;
import com.example.smartbudget.Ui.Account.IAccountInsertListener;
import com.example.smartbudget.Interface.IAccountsLoadListener;
import com.example.smartbudget.Interface.IThisMonthTransactionPatternLoadListener;
import com.example.smartbudget.Database.Model.SpendingPattern;
import com.example.smartbudget.Interface.IThisWeekTransactionLoadListener;
import com.example.smartbudget.Ui.Transaction.Add.AddTransactionActivity;
import com.example.smartbudget.Ui.Transaction.Add.ICategoryLoadListener;
import com.example.smartbudget.Ui.Transaction.Add.ITransactionInsertListener;
import com.example.smartbudget.Model.AccountModel;
import com.example.smartbudget.Model.CategoryModel;
import com.example.smartbudget.Model.TransactionModel;
import com.example.smartbudget.Interface.IThisMonthTransactionLoadListener;

import java.util.ArrayList;
import java.util.List;

public class DatabaseUtils {

    private static final String TAG = DatabaseUtils.class.getSimpleName();

    public static void insertAccountAsync(DBHelper db, IAccountInsertListener listener, AccountModel... accountModels) {
        InsertAccountAsync task = new InsertAccountAsync(db, listener);
        task.execute(accountModels);
    }

    public static void updateAccountAsync(DBHelper db, IAccountInsertListener listener, AccountModel... accountModels) {
        UpdateAccountAsync task = new UpdateAccountAsync(db, listener);
        task.execute(accountModels);
    }

    public static void deleteAccountAsync(DBHelper db, IAccountsLoadListener listener, AccountModel... accountModels) {
        DeleteAccountAsync task = new DeleteAccountAsync(db, listener);
        task.execute(accountModels);
    }

    public static void deleteTransactionAsync(DBHelper db, TransactionModel... transactionModels) {
        DeleteTransactionAsync task = new DeleteTransactionAsync(db);
        task.execute(transactionModels);
    }

    public static void getAccount(DBHelper db, int accountId, IAccountLoadListener listener) {
        GetAccountAsync task = new GetAccountAsync(db, accountId, listener);
        task.execute();
    }

    public static void getAllAccount(DBHelper db, IAccountsLoadListener listener) {
        GetAllAccountAsync task = new GetAllAccountAsync(db, listener);
        task.execute();
    }

    public static void getAllCategory(DBHelper db, ICategoryLoadListener listener) {
        GetAllCategoryAsync task = new GetAllCategoryAsync(db, listener);
        task.execute();
    }

    public static void getAllTransaction(DBHelper db, IThisMonthTransactionLoadListener listener) {
        GetAllTransactionAsync task = new GetAllTransactionAsync(db, listener);
        task.execute();
    }

    public static void getThisWeekTransaction(DBHelper db, IThisWeekTransactionLoadListener listener) {
        GetThisWeekTransactionAsync task = new GetThisWeekTransactionAsync(db, listener);
        task.execute();
    }

    public static void getThisMonthTransaction(DBHelper db, String date, IThisMonthTransactionLoadListener listener) {
        GetThisMonthTransactionAsync task = new GetThisMonthTransactionAsync(db, date, listener);
        task.execute();
    }

    public static void getThisMonthTransactionListPattern(DBHelper db, String date, String pattern, IThisMonthTransactionLoadListener listener) {
        getThisMonthTransactionListPattern task = new getThisMonthTransactionListPattern(db, date, pattern, listener);
        task.execute();
    }

    public static void getThisMonthTransactionPattern(DBHelper db, String date, IThisMonthTransactionPatternLoadListener listener) {
        GetThisMonthTransactionPatternAsync task = new GetThisMonthTransactionPatternAsync(db, date, listener);
        task.execute();
    }

    public static void insertTransactionAsync(DBHelper db, ITransactionInsertListener listener, TransactionModel... transactionModels) {
        InsertTransactionAsync task = new InsertTransactionAsync(db, listener);
        task.execute(transactionModels);
    }

    /*
    ============================================================================
    ASYNC TASK DEFINE
    ============================================================================
     */
    private static class InsertTransactionAsync extends AsyncTask<TransactionModel, Void, Boolean> {

        DBHelper db;
        ITransactionInsertListener mListener;

        public InsertTransactionAsync(DBHelper db, ITransactionInsertListener listener) {
            this.db = db;
            mListener = listener;
        }

        @Override
        protected Boolean doInBackground(TransactionModel... transactionModels) {
            return db.insertToTransaction(transactionModels[0]);
        }

        @Override
        protected void onPostExecute(Boolean isInserted) {
            super.onPostExecute(isInserted);
            mListener.onTransactionInsertSuccess(isInserted);
        }
    }

    private static class InsertAccountAsync extends AsyncTask<AccountModel, Void, Boolean> {

        DBHelper db;
        IAccountInsertListener mListener;

        public InsertAccountAsync(DBHelper db, IAccountInsertListener listener) {
            this.db = db;
            mListener = listener;
        }

        @Override
        protected Boolean doInBackground(AccountModel... accountModels) {
            return db.insertToAccount(accountModels[0]);
        }

        @Override
        protected void onPostExecute(Boolean isInserted) {
            super.onPostExecute(isInserted);
            mListener.onAccountInsertSuccess(isInserted);
        }
    }

    public static class UpdateAccountAsync extends AsyncTask<AccountModel, Void, Boolean> {

        DBHelper db;
        IAccountInsertListener mListener;

        public UpdateAccountAsync(DBHelper db, IAccountInsertListener listener) {
            this.db = db;
            this.mListener = listener;
        }

        @Override
        protected Boolean doInBackground(AccountModel... accountModels) {
            try {
                long result = db.updateAccount(accountModels[0]);
                return result == 1;
            } catch (Exception e) {
                Log.d(TAG, "doInBackground: Error-" + e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            mListener.onAccountUpdateSuccess(aBoolean);
        }
    }

    private static class DeleteAccountAsync extends AsyncTask<AccountModel, Void, Boolean> {

        DBHelper db;
        IAccountsLoadListener mListener;

        public DeleteAccountAsync(DBHelper db, IAccountsLoadListener listener) {
            this.db = db;
            mListener = listener;
        }

        @Override
        protected Boolean doInBackground(AccountModel... accountModels) {
            try {
                long result = db.deleteAccount(accountModels[0]);
                return result == 1;
            } catch (Exception e) {
                Log.d(TAG, "doInBackground: Error-" + e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            mListener.onAccountDeleteSuccess(aBoolean);
        }
    }

    private static class DeleteTransactionAsync extends AsyncTask<TransactionModel, Void, Void> {

        DBHelper db;

        public DeleteTransactionAsync(DBHelper db) {
            this.db = db;
        }

        @Override
        protected Void doInBackground(TransactionModel... transactionModels) {
            try {
                long result = db.deleteTransaction(transactionModels[0]);
            } catch (Exception e) {
                Log.d(TAG, "doInBackground: Error-" + e.getMessage());
            }
            return null;
        }
    }

    private static class GetAccountAsync extends AsyncTask<Void, Void, AccountModel> {

        DBHelper db;
        IAccountLoadListener mListener;
        int accountId = 0;

        public GetAccountAsync(DBHelper db, int accountId, IAccountLoadListener listener) {
            this.db = db;
            mListener = listener;
            this.accountId = accountId;
        }

        @Override
        protected AccountModel doInBackground(Void... voids) {
            Cursor cursor = db.getAccount(accountId);
            AccountModel accountModel = new AccountModel();
            Log.d(TAG, "doInBackground: cursor.getCount(): "+cursor.getCount());
            if (cursor != null && cursor.getCount() > 0) {
                try {
                    long id = cursor.getLong(cursor.getColumnIndexOrThrow(DBContract.Account._ID));
                    String name = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Account.COL_NAME));
                    String description = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Account.COL_DESCRIPTION));
                    String amount = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Account.COL_AMOUNT));
                    String type = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Account.COL_TYPE));
                    String createAt = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Account.COL_CREATE_AT));
                    String currency = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Account.COL_CURRENCY));

                    accountModel.setId((int) id);
                    accountModel.setAccount_name(name);
                    accountModel.setAccount_description(description);
                    accountModel.setAccount_amount(Double.parseDouble(amount));
                    accountModel.setAccount_type(type);
                    accountModel.setAccount_currency(currency);
                } finally {
                    cursor.close();
                }
                return accountModel;
            }
            return accountModel;
        }

        @Override
        protected void onPostExecute(AccountModel accountModel) {
            super.onPostExecute(accountModel);
            mListener.onAccountLoadSuccess(accountModel);
        }
    }

    private static class GetAllAccountAsync extends AsyncTask<Void, Void, List<AccountModel>> {

        DBHelper db;
        IAccountsLoadListener mListener;

        public GetAllAccountAsync(DBHelper db, IAccountsLoadListener listener) {
            this.db = db;
            mListener = listener;
        }

        @Override
        protected List<AccountModel> doInBackground(Void... voids) {
            Cursor cursor = db.getAllAccounts();
            List<AccountModel> accountList = new ArrayList<>();
            if (cursor != null && cursor.getCount() > 0) {
                try {
                    do {
                        AccountModel account = new AccountModel();
                        long id = cursor.getLong(cursor.getColumnIndexOrThrow(DBContract.Account._ID));
                        String name = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Account.COL_NAME));
                        String description = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Account.COL_DESCRIPTION));
                        String amount = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Account.COL_AMOUNT));
                        String type = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Account.COL_TYPE));
                        String createAt = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Account.COL_CREATE_AT));
                        String currency = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Account.COL_CURRENCY));

                        account.setId((int) id);
                        account.setAccount_name(name);
                        account.setAccount_description(description);
                        account.setAccount_amount(Double.parseDouble(amount));
                        account.setAccount_type(type);
                        account.setAccount_currency(currency);
                        accountList.add(account);
                    }
                    while (cursor.moveToNext());
                } finally {
                    cursor.close();
                }
                return accountList;
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<AccountModel> accountList) {
            super.onPostExecute(accountList);
            mListener.onAccountsLoadSuccess(accountList);
        }
    }

    private static class GetAllTransactionAsync extends AsyncTask<Void, Void, List<TransactionModel>> {

        DBHelper db;
        IThisMonthTransactionLoadListener mListener;

        public GetAllTransactionAsync(DBHelper db, IThisMonthTransactionLoadListener listener) {
            this.db = db;
            mListener = listener;
        }

        @Override
        protected List<TransactionModel> doInBackground(Void... voids) {
            Cursor cursor = db.getAllTransactions();

            List<TransactionModel> transactionList = new ArrayList<>();
            if (cursor != null && cursor.getCount() > 0) {
                try {
                    do {
                        TransactionModel transaction = new TransactionModel();
                        long id = cursor.getLong(cursor.getColumnIndexOrThrow(DBContract.Transaction._ID));
                        String description = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Transaction.COL_NOTE));
                        String amount = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Transaction.COL_AMOUNT));
                        String type = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Transaction.COL_TYPE));
                        String date = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Transaction.COL_DATE));
                        String pattern = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Transaction.COL_PATTERN));
                        String categoryId = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Transaction.COL_CATEGORY_ID));
                        String accountId = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Transaction.COL_ACCOUNT_ID));
                        String toAccount = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Transaction.COL_TO_ACCOUNT));

                        transaction.setId((int) id);
                        transaction.setTransaction_note(description);
                        transaction.setTransaction_amount(Double.parseDouble(amount));
                        transaction.setTransaction_type(type);
                        transaction.setTransaction_date(date);
                        transaction.setTransaction_pattern(pattern);
                        transaction.setCategory_id(categoryId);
                        transaction.setAccount_id(Integer.parseInt(accountId));
                        transactionList.add(transaction);
                    }
                    while (cursor.moveToNext());
                } finally {
                    cursor.close();
                }
                return transactionList;
            }
            return transactionList;
        }

        @Override
        protected void onPostExecute(List<TransactionModel> transactionList) {
            super.onPostExecute(transactionList);
            mListener.onTransactionLoadSuccess(transactionList);
        }
    }

    private static class GetThisWeekTransactionAsync extends AsyncTask<Void, Void, List<TransactionModel>> {

        DBHelper db;
        IThisWeekTransactionLoadListener mListener;

        public GetThisWeekTransactionAsync(DBHelper db, IThisWeekTransactionLoadListener listener) {
            this.db = db;
            mListener = listener;
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected List<TransactionModel> doInBackground(Void... voids) {
            Cursor cursor = db.getThisWeekTransactions();

            Log.d(TAG, "doInBackground: cursor: " + cursor.getCount());

            List<TransactionModel> transactionList = new ArrayList<>();
            if (cursor != null && cursor.getCount() > 0) {
                try {
                    do {
                        TransactionModel transaction = new TransactionModel();
                        long id = cursor.getLong(cursor.getColumnIndexOrThrow(DBContract.Transaction._ID));
                        String description = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Transaction.COL_NOTE));
                        String amount = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Transaction.COL_AMOUNT));
                        String type = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Transaction.COL_TYPE));
                        String date = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Transaction.COL_DATE));
                        String pattern = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Transaction.COL_PATTERN));
                        String categoryId = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Transaction.COL_CATEGORY_ID));
                        String accountId = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Transaction.COL_ACCOUNT_ID));
                        String toAccount = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Transaction.COL_TO_ACCOUNT));

                        transaction.setId((int) id);
                        transaction.setTransaction_note(description);
                        transaction.setTransaction_amount(Double.parseDouble(amount));
                        transaction.setTransaction_type(type);
                        transaction.setTransaction_date(date);
                        transaction.setTransaction_pattern(pattern);
                        transaction.setCategory_id(categoryId);
                        transaction.setAccount_id(Integer.parseInt(accountId));
                        transactionList.add(transaction);
                    }
                    while (cursor.moveToNext());
                } finally {
                    cursor.close();
                }
                return transactionList;
            }
            return transactionList;
        }

        @Override
        protected void onPostExecute(List<TransactionModel> transactionList) {
            super.onPostExecute(transactionList);
            mListener.onThisWeekTransactionLoadSuccess(transactionList);
        }
    }

    private static class GetThisMonthTransactionAsync extends AsyncTask<Void, Void, List<TransactionModel>> {

        DBHelper db;
        IThisMonthTransactionLoadListener mListener;
        String date = "";

        public GetThisMonthTransactionAsync(DBHelper db, String date, IThisMonthTransactionLoadListener listener) {
            this.db = db;
            mListener = listener;
            this.date = date;
        }

        @Override
        protected List<TransactionModel> doInBackground(Void... voids) {
            Cursor cursor = db.getThisMonthTransactions(date);
            Log.d(TAG, "cursor.getCount(): " + cursor.getCount());
            List<TransactionModel> transactionList = new ArrayList<>();
            if (cursor != null && cursor.getCount() > 0) {
                try {
                    do {
                        TransactionModel transaction = new TransactionModel();
                        long id = cursor.getLong(cursor.getColumnIndexOrThrow(DBContract.Transaction._ID));
                        String description = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Transaction.COL_NOTE));
                        String amount = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Transaction.COL_AMOUNT));
                        String type = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Transaction.COL_TYPE));
                        String date = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Transaction.COL_DATE));
                        String pattern = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Transaction.COL_PATTERN));
                        String categoryId = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Transaction.COL_CATEGORY_ID));
                        String subCategoryId = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Transaction.COL_SUB_CATEGORY_ID));
                        String accountId = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Transaction.COL_ACCOUNT_ID));
                        String toAccount = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Transaction.COL_TO_ACCOUNT));

                        transaction.setId((int) id);
                        transaction.setTransaction_note(description);
                        transaction.setTransaction_amount(Double.parseDouble(amount));
                        transaction.setTransaction_type(type);
                        transaction.setTransaction_date(date);
                        transaction.setTransaction_pattern(pattern);
                        transaction.setCategory_id(categoryId);
                        transaction.setSub_category_id(subCategoryId);
                        transaction.setAccount_id(accountId != null ? Integer.parseInt(accountId) : 0);
                        transactionList.add(transaction);
                    }
                    while (cursor.moveToNext());
                } finally {
                    cursor.close();
                }
                return transactionList;
            }
            return transactionList;
        }

        @Override
        protected void onPostExecute(List<TransactionModel> transactionList) {
            super.onPostExecute(transactionList);
            if (transactionList != null)
                mListener.onTransactionLoadSuccess(transactionList);
        }
    }

    private static class getThisMonthTransactionListPattern extends AsyncTask<Void, Void, List<TransactionModel>> {

        DBHelper db;
        IThisMonthTransactionLoadListener mListener;
        String date = "";
        String pattern = "";

        public getThisMonthTransactionListPattern(DBHelper db, String date, String pattern, IThisMonthTransactionLoadListener listener) {
            this.db = db;
            mListener = listener;
            this.date = date;
            this.pattern = pattern;
        }

        @Override
        protected List<TransactionModel> doInBackground(Void... voids) {
            Cursor cursor = db.getThisMonthTransactionsPatternList(date, pattern);
            Log.d(TAG, "cursor.getCount(): " + cursor.getCount());
            List<TransactionModel> transactionList = new ArrayList<>();
            if (cursor != null && cursor.getCount() > 0) {
                try {
                    do {
                        TransactionModel transaction = new TransactionModel();
                        long id = cursor.getLong(cursor.getColumnIndexOrThrow(DBContract.Transaction._ID));
                        String description = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Transaction.COL_NOTE));
                        String amount = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Transaction.COL_AMOUNT));
                        String type = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Transaction.COL_TYPE));
                        String date = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Transaction.COL_DATE));
                        String categoryId = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Transaction.COL_CATEGORY_ID));
                        String accountId = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Transaction.COL_ACCOUNT_ID));
                        String toAccount = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Transaction.COL_TO_ACCOUNT));

                        transaction.setId((int) id);
                        transaction.setTransaction_note(description);
                        transaction.setTransaction_amount(Double.parseDouble(amount));
                        transaction.setTransaction_type(type);
                        transaction.setTransaction_date(date);
                        transaction.setCategory_id(categoryId);
                        transaction.setAccount_id(Integer.parseInt(accountId));
                        transactionList.add(transaction);
                    }
                    while (cursor.moveToNext());
                } finally {
                    cursor.close();
                }
                return transactionList;
            }
            return transactionList;
        }

        @Override
        protected void onPostExecute(List<TransactionModel> transactionList) {
            super.onPostExecute(transactionList);
            if (transactionList != null)
                mListener.onTransactionLoadSuccess(transactionList);
        }
    }

    private static class GetThisMonthTransactionPatternAsync extends AsyncTask<Void, Void, List<SpendingPattern>> {

        DBHelper db;
        IThisMonthTransactionPatternLoadListener mListener;
        String date = "";

        public GetThisMonthTransactionPatternAsync(DBHelper db, String date, IThisMonthTransactionPatternLoadListener listener) {
            this.db = db;
            mListener = listener;
            this.date = date;
        }

        @Override
        protected List<SpendingPattern> doInBackground(Void... voids) {
            Cursor cursor = db.getThisMonthTransactionPattern(date);

            List<SpendingPattern> spendingPatternList = new ArrayList<>();
            if (cursor != null && cursor.getCount() > 0) {
                try {
                    do {
                        SpendingPattern spendingPattern = new SpendingPattern();
                        String pattern = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Transaction.COL_PATTERN));
                        String patternSum = cursor.getString(cursor.getColumnIndexOrThrow("pattern_sum"));
                        String patternCount = cursor.getString(cursor.getColumnIndexOrThrow("pattern_count"));

                        spendingPattern.setPattern(pattern);
                        spendingPattern.setSum(Double.parseDouble(patternSum));
                        spendingPattern.setCount(Integer.parseInt(patternCount));
                        spendingPatternList.add(spendingPattern);
                    }
                    while (cursor.moveToNext());
                } finally {
                    cursor.close();
                }
                return spendingPatternList;
            }
            return spendingPatternList;
        }

        @Override
        protected void onPostExecute(List<SpendingPattern> spendingPatternList) {
            super.onPostExecute(spendingPatternList);
            if (spendingPatternList != null)
                mListener.onThisMonthTransactionPatternLoadSuccess(spendingPatternList);
        }
    }

    private static class GetAllCategoryAsync extends AsyncTask<Void, Void, List<CategoryModel>> {

        DBHelper db;
        ICategoryLoadListener mListener;

        public GetAllCategoryAsync(DBHelper db, ICategoryLoadListener listener) {
            this.db = db;
            mListener = listener;
        }

        @Override
        protected List<CategoryModel> doInBackground(Void... voids) {
            Cursor cursor = db.getAllCategories();
            List<CategoryModel> categoryList = new ArrayList<>();
            try {
                do {
                    CategoryModel category = new CategoryModel();

                    long id = cursor.getLong(cursor.getColumnIndexOrThrow(DBContract.Category._ID));
                    String icon = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Category.COL_ICON));
                    String name = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Category.COL_NAME));
                    category.setId((int) id);
                    category.setCategory_icon(Integer.parseInt(icon));
                    category.setCategory_name(name);
                    categoryList.add(category);
                }
                while (cursor.moveToNext());
            } finally {
                cursor.close();
            }
            return categoryList;
        }

        @Override
        protected void onPostExecute(List<CategoryModel> categoryList) {
            super.onPostExecute(categoryList);
            mListener.onCategoryLoadSuccess(categoryList);
        }
    }


}


