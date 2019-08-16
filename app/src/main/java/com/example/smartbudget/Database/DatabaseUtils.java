package com.example.smartbudget.Database;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.smartbudget.Database.Model.ExpenseByCategory;
import com.example.smartbudget.Database.Model.SpendingByPattern;
import com.example.smartbudget.Database.Interface.IAccountDeleteListener;
import com.example.smartbudget.Database.Interface.IDBUpdateListener;
import com.example.smartbudget.Interface.ISumAccountsLoadListener;
import com.example.smartbudget.Database.Interface.IThisMonthTransactionsByCategoryLoadListener;
import com.example.smartbudget.Interface.IThisMonthTransactionByPatternLoadListener;
import com.example.smartbudget.Database.Interface.ITransactionUpdateListener;
import com.example.smartbudget.Interface.IUpdateAccountListener;
import com.example.smartbudget.Database.Interface.IBudgetInsertListener;
import com.example.smartbudget.Interface.IThisWeekTransactionLoadListener;
import com.example.smartbudget.Ui.Transaction.Add.ICategoryLoadListener;
import com.example.smartbudget.Interface.ITransactionInsertListener;
import com.example.smartbudget.Model.AccountModel;
import com.example.smartbudget.Model.CategoryModel;
import com.example.smartbudget.Model.TransactionModel;
import com.example.smartbudget.Interface.IThisMonthTransactionLoadListener;

import java.util.ArrayList;
import java.util.List;

public class DatabaseUtils {

    private static final String TAG = DatabaseUtils.class.getSimpleName();

    public static void deleteAccountAsync(DBHelper db, IAccountDeleteListener listener, AccountModel... accountModels) {
        DeleteAccountAsync task = new DeleteAccountAsync(db, listener);
        task.execute(accountModels);
    }

    public static void deleteTransactionAsync(DBHelper db, TransactionModel... transactionModels) {
        DeleteTransactionAsync task = new DeleteTransactionAsync(db);
        task.execute(transactionModels);
    }

//    public static void getAccount(DBHelper db, int accountId, IAccountLoadListener listener) {
//        GetAccountAsync task = new GetAccountAsync(db, accountId, listener);
//        task.execute();
//    }
//
//    public static void getAllAccounts(DBHelper db, IAccountsLoadListener listener) {
//        GetAllAccountsAsync task = new GetAllAccountsAsync(db, listener);
//        task.execute();
//    }
//
//    public static void getAccountsByType(DBHelper db, String type, IAccountsLoadListener listener) {
//        GetAccountsByTypeAsync task = new GetAccountsByTypeAsync(db, type, listener);
//        task.execute();
//    }

//    public static void getSumAccountsByType(DBHelper db, ISumAccountsLoadListener listener) {
//        GetSumAccountsByTypeAsync task = new GetSumAccountsByTypeAsync(db, listener);
//        task.execute();
//    }

    public static void updateAccountsByTransfer(DBHelper db, double amount, int fromId, int toId, IUpdateAccountListener listener) {
        UpdateAccountsByTransferAsync task = new UpdateAccountsByTransferAsync(db, amount, fromId, toId, listener);
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

    public static void getLastFewDaysTransactions(DBHelper db, String startDate, String endDate, IThisWeekTransactionLoadListener listener) {
        GetLastFewDaysTransactionsAsync task = new GetLastFewDaysTransactionsAsync(db, startDate, endDate, listener);
        task.execute();
    }

    public static void getThisMonthTransaction(DBHelper db, String date, IThisMonthTransactionLoadListener listener) {
        GetThisMonthTransactionAsync task = new GetThisMonthTransactionAsync(db, date, listener);
        task.execute();
    }

    public static void getThisMonthTransactionsByAccount(DBHelper db, String date, int id, IThisMonthTransactionLoadListener listener) {
        GetThisMonthTransactionsByAccountAsync task = new GetThisMonthTransactionsByAccountAsync(db, date, id, listener);
        task.execute();
    }

    public static void getThisMonthTransactionListPattern(DBHelper db, String date, String pattern, IThisMonthTransactionLoadListener listener) {
        getThisMonthTransactionListPattern task = new getThisMonthTransactionListPattern(db, date, pattern, listener);
        task.execute();
    }

    public static void getThisMonthTransactionByPattern(DBHelper db, String date, IThisMonthTransactionByPatternLoadListener listener) {
        GetThisMonthTransactionByPatternAsync task = new GetThisMonthTransactionByPatternAsync(db, date, listener);
        task.execute();
    }

    public static void getThisMonthTransactionByCategory(DBHelper db, String date, IThisMonthTransactionsByCategoryLoadListener listener) {
        GetThisMonthTransactionByCategoryAsync task = new GetThisMonthTransactionByCategoryAsync(db, date, listener);
        task.execute();
    }

    public static void insertTransactionAsync(DBHelper db, ITransactionInsertListener listener, TransactionModel... transactionModels) {
        InsertTransactionAsync task = new InsertTransactionAsync(db, listener);
        task.execute(transactionModels);
    }

    public static void insertAccountAsync(DBHelper db, IBudgetInsertListener listener, AccountModel... accountModels) {
        InsertAccountAsync task = new InsertAccountAsync(db, listener);
        task.execute(accountModels);
    }
    public static void updateAccountAsync(DBHelper db, IDBUpdateListener listener, AccountModel... accountModels) {
        UpdateAccountAsync task = new UpdateAccountAsync(db, listener);
        task.execute(accountModels);
    }

    public static void updateTransactionAsync(DBHelper db, ITransactionUpdateListener listener, TransactionModel... transactionModels) {
        UpdateTransactionAsync task = new UpdateTransactionAsync(db, listener);
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
            return db.insertTransaction(transactionModels[0]);
        }

        @Override
        protected void onPostExecute(Boolean isInserted) {
            super.onPostExecute(isInserted);
            mListener.onTransactionInsertSuccess(isInserted);
        }
    }

    private static class InsertAccountAsync extends AsyncTask<AccountModel, Void, Boolean> {

        DBHelper db;
        IBudgetInsertListener mListener;

        public InsertAccountAsync(DBHelper db, IBudgetInsertListener listener) {
            this.db = db;
            mListener = listener;
        }

        @Override
        protected Boolean doInBackground(AccountModel... accountModels) {
            return db.insertAccount(accountModels[0]);
        }

        @Override
        protected void onPostExecute(Boolean isInserted) {
            super.onPostExecute(isInserted);
            mListener.onBudgetInsertSuccess(isInserted);
        }
    }

    public static class UpdateAccountAsync extends AsyncTask<AccountModel, Void, Boolean> {

        DBHelper db;
        IDBUpdateListener mListener;

        public UpdateAccountAsync(DBHelper db, IDBUpdateListener listener) {
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
            mListener.onDBUpdateSuccess(aBoolean);
        }
    }

    public static class UpdateTransactionAsync extends AsyncTask<TransactionModel, Void, Boolean> {

        DBHelper db;
        ITransactionUpdateListener mListener;

        public UpdateTransactionAsync(DBHelper db, ITransactionUpdateListener listener) {
            this.db = db;
            this.mListener = listener;
        }

        @Override
        protected Boolean doInBackground(TransactionModel... transactionModels) {
            try {
                long result = db.updateTransaction(transactionModels[0]);
                return result == 1;
            } catch (Exception e) {
                Log.d(TAG, "doInBackground: Error-" + e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            mListener.onTransactionUpdateSuccess(aBoolean);
        }
    }

    private static class DeleteAccountAsync extends AsyncTask<AccountModel, Void, Boolean> {

        DBHelper db;
        IAccountDeleteListener mListener;

        public DeleteAccountAsync(DBHelper db, IAccountDeleteListener listener) {
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
            return true;
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

//    private static class GetAccountAsync extends AsyncTask<Void, Void, AccountModel> {
//
//        DBHelper db;
//        IAccountLoadListener mListener;
//        int accountId = 0;
//
//        public GetAccountAsync(DBHelper db, int accountId, IAccountLoadListener listener) {
//            this.db = db;
//            mListener = listener;
//            this.accountId = accountId;
//        }
//
//        @Override
//        protected AccountModel doInBackground(Void... voids) {
//            Cursor cursor = db.getAccount(accountId);
//            AccountModel account = new AccountModel();
//            Log.d(TAG, "doInBackground: cursor.getCount(): "+cursor.getCount());
//            if (cursor != null && cursor.getCount() > 0) {
//                try {
//                    long id = cursor.getLong(cursor.getColumnIndexOrThrow(DBContract.Account._ID));
//                    String name = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Account.COL_NAME));
//                    String description = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Account.COL_DESCRIPTION));
//                    String amount = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Account.COL_AMOUNT));
//                    String highCategory = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Account.COL_HIGH_CATEGORY));
//                    String type = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Account.COL_TYPE));
//                    String createAt = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Account.COL_CREATE_AT));
//                    String currency = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Account.COL_CURRENCY));
//
//                    account.setId((int) id);
//                    account.setName(name);
//                    account.setDescription(description);
//                    account.setHighCategory(highCategory);
//                    account.setAmount(Double.parseDouble(amount));
//                    account.setType(type);
//                    Date dt = null;
//                    try {
//                        dt = Common.dateFormat.parse(createAt);
//                        account.setCreateAt(dt);
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
//                    account.setCurrency(currency);
//
//                } finally {
//                    cursor.close();
//                }
//                return account;
//            }
//            return account;
//        }
//
//        @Override
//        protected void onPostExecute(AccountModel accountModel) {
//            super.onPostExecute(accountModel);
//            mListener.onExpenseBudgetLoadSuccess(accountModel);
//        }
//    }

//    private static class GetAllAccountsAsync extends AsyncTask<Void, Void, List<AccountModel>> {
//
//        DBHelper db;
//        IAccountsLoadListener mListener;
//
//        public GetAllAccountsAsync(DBHelper db, IAccountsLoadListener listener) {
//            this.db = db;
//            mListener = listener;
//        }
//
//        @Override
//        protected List<AccountModel> doInBackground(Void... voids) {
//            Cursor cursor = db.getAllAccounts();
//            List<AccountModel> accountList = new ArrayList<>();
//            if (cursor != null && cursor.getCount() > 0) {
//                try {
//                    do {
//                        AccountModel account = new AccountModel();
//                        long id = cursor.getLong(cursor.getColumnIndexOrThrow(DBContract.Account._ID));
//                        String name = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Account.COL_NAME));
//                        String description = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Account.COL_DESCRIPTION));
//                        String amount = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Account.COL_AMOUNT));
//                        String highCategory = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Account.COL_HIGH_CATEGORY));
//                        String type = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Account.COL_TYPE));
//                        String createAt = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Account.COL_CREATE_AT));
//                        String currency = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Account.COL_CURRENCY));
//
//                        account.setId((int) id);
//                        account.setName(name);
//                        account.setDescription(description);
//                        account.setAmount(Double.parseDouble(amount));
//                        account.setHighCategory(highCategory);
//                        account.setType(type);
//
//                        Date dt = null;
//                        try {
//                            dt = Common.dateFormat.parse(createAt);
//                            account.setCreateAt(dt);
//                        } catch (ParseException e) {
//                            e.printStackTrace();
//                        }
//                        account.setCurrency(currency);
//                        accountList.add(account);
//                    }
//                    while (cursor.moveToNext());
//                } finally {
//                    cursor.close();
//                }
//                return accountList;
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(List<AccountModel> accountList) {
//            super.onPostExecute(accountList);
//            mListener.onAccountsLoadSuccess(accountList);
//        }
//    }

//    private static class GetAccountsByTypeAsync extends AsyncTask<Void, Void, List<AccountModel>> {
//
//        DBHelper db;
//        String type = "";
//        IAccountsLoadListener mListener;
//
//        public GetAccountsByTypeAsync(DBHelper db, String type, IAccountsLoadListener listener) {
//            this.db = db;
//            this.type = type;
//            mListener = listener;
//        }
//
//        @Override
//        protected List<AccountModel> doInBackground(Void... voids) {
//            Cursor cursor = db.getAccountsByType(type);
//            List<AccountModel> accountList = new ArrayList<>();
//            if (cursor != null && cursor.getCount() > 0) {
//                try {
//                    do {
//                        AccountModel account = new AccountModel();
//                        long id = cursor.getLong(cursor.getColumnIndexOrThrow(DBContract.Account._ID));
//                        String name = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Account.COL_NAME));
//                        String description = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Account.COL_DESCRIPTION));
//                        String amount = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Account.COL_AMOUNT));
//                        String highCategory = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Account.COL_HIGH_CATEGORY));
//                        String type = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Account.COL_TYPE));
//                        String createAt = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Account.COL_CREATE_AT));
//                        String currency = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Account.COL_CURRENCY));
//
//                        account.setId((int) id);
//                        account.setName(name);
//                        account.setDescription(description);
//                        account.setAmount(Double.parseDouble(amount));
//                        account.setHighCategory(highCategory);
//                        account.setType(type);
//                        Date dt = null;
//                        try {
//                            dt = Common.dateFormat.parse(createAt);
//                            account.setCreateAt(dt);
//                        } catch (ParseException e) {
//                            e.printStackTrace();
//                        }
//                        account.setCurrency(currency);
//                        accountList.add(account);
//                    }
//                    while (cursor.moveToNext());
//                } finally {
//                    cursor.close();
//                }
//                return accountList;
//            }
//            return accountList;
//        }
//
//        @Override
//        protected void onPostExecute(List<AccountModel> accountList) {
//            super.onPostExecute(accountList);
//            mListener.onAccountsLoadSuccess(accountList);
//        }
//    }

    private static class GetSumAccountsByTypeAsync extends AsyncTask<Void, Void, List<AccountModel>> {

        DBHelper db;
        ISumAccountsLoadListener mListener;

        public GetSumAccountsByTypeAsync(DBHelper db, ISumAccountsLoadListener listener) {
            this.db = db;
            mListener = listener;
        }

        @Override
        protected List<AccountModel> doInBackground(Void... voids) {
            Cursor cursor = db.getSumAccountsByType();
            List<AccountModel> accountList = new ArrayList<>();
            if (cursor != null && cursor.getCount() > 0) {
                try {
                    do {
                        AccountModel account = new AccountModel();
                        String type = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Account.COL_TYPE));
                        String amount = cursor.getString(cursor.getColumnIndexOrThrow("sumByType"));

                        account.setAmount(Double.parseDouble(amount));
                        account.setType(type);
                        accountList.add(account);
                    }
                    while (cursor.moveToNext());
                } finally {
                    cursor.close();
                }
                return accountList;
            }
            return accountList;
        }

        @Override
        protected void onPostExecute(List<AccountModel> accountList) {
            super.onPostExecute(accountList);
            mListener.onSumAccountsLoadSuccess(accountList);
        }
    }

    private static class UpdateAccountsByTransferAsync extends AsyncTask<Void, Void, Boolean> {

        DBHelper db;
        IUpdateAccountListener mListener;
        double amount;
        int fromId;
        int toId;

        public UpdateAccountsByTransferAsync(DBHelper db, double amount, int fromId, int toId, IUpdateAccountListener listener) {
            this.db = db;
            this.amount = amount;
            this.fromId = fromId;
            this.toId = toId;
            mListener = listener;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            return db.updateAccountsByTransfer(amount, fromId, toId);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            mListener.onUpdateAccountSuccess(aBoolean);
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
                        transaction.setNote(description);
                        transaction.setAmount(Double.parseDouble(amount));
                        transaction.setType(type);
                        transaction.setDate(date);
                        transaction.setPattern(pattern);
                        transaction.setCategoryId(categoryId);
                        transaction.setAccountId(Integer.parseInt(accountId));
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
                        transaction.setNote(description);
                        transaction.setAmount(Double.parseDouble(amount));
                        transaction.setType(type);
                        transaction.setDate(date);
                        transaction.setPattern(pattern);
                        transaction.setCategoryId(categoryId);
                        transaction.setAccountId(Integer.parseInt(accountId));
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

    private static class GetLastFewDaysTransactionsAsync extends AsyncTask<Void, Void, List<TransactionModel>> {

        DBHelper db;
        String startDate = "";
        String endDate = "";
        IThisWeekTransactionLoadListener mListener;

        public GetLastFewDaysTransactionsAsync(DBHelper db, String startDate, String endDate, IThisWeekTransactionLoadListener listener) {
            this.db = db;
            this.startDate = startDate;
            this.endDate = endDate;
            mListener = listener;
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected List<TransactionModel> doInBackground(Void... voids) {
            Cursor cursor = db.getLastFewDaysTransactions(startDate, endDate);
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
                        transaction.setNote(description);
                        transaction.setAmount(Double.parseDouble(amount));
                        transaction.setType(type);
                        transaction.setDate(date);
                        transaction.setPattern(pattern);
                        transaction.setCategoryId(categoryId);
                        transaction.setAccountId(Integer.parseInt(accountId));
                        transactionList.add(transaction);
                    }
                    while (cursor.moveToNext());
                } finally {
                    cursor.close();
                }
                return transactionList;
            }
            return null;
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
                        transaction.setNote(description);
                        transaction.setAmount(Double.parseDouble(amount));
                        transaction.setType(type);
                        transaction.setDate(date);
                        transaction.setPattern(pattern);
                        transaction.setCategoryId(categoryId);
                        transaction.setSubCategoryId(subCategoryId);
                        transaction.setAccountId(accountId != null ? Integer.parseInt(accountId) : 0);
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

    private static class GetThisMonthTransactionsByAccountAsync extends AsyncTask<Void, Void, List<TransactionModel>> {

        DBHelper db;
        IThisMonthTransactionLoadListener mListener;
        String date;
        int id;

        public GetThisMonthTransactionsByAccountAsync(DBHelper db, String date, int id, IThisMonthTransactionLoadListener listener) {
            this.db = db;
            this.date = date;
            this.id = id;
            mListener = listener;
        }

        @Override
        protected List<TransactionModel> doInBackground(Void... voids) {
            Cursor cursor = db.getThisMonthTransactionsByAccount(date, id);
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
                        transaction.setNote(description);
                        transaction.setAmount(Double.parseDouble(amount));
                        transaction.setType(type);
                        transaction.setDate(date);
                        transaction.setPattern(pattern);
                        transaction.setCategoryId(categoryId);
                        transaction.setSubCategoryId(subCategoryId);
                        transaction.setAccountId(accountId != null ? Integer.parseInt(accountId) : 0);
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
                        transaction.setNote(description);
                        transaction.setAmount(Double.parseDouble(amount));
                        transaction.setType(type);
                        transaction.setDate(date);
                        transaction.setCategoryId(categoryId);
                        transaction.setAccountId(Integer.parseInt(accountId));
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

    private static class GetThisMonthTransactionByPatternAsync extends AsyncTask<Void, Void, List<SpendingByPattern>> {

        DBHelper db;
        IThisMonthTransactionByPatternLoadListener mListener;
        String date = "";

        public GetThisMonthTransactionByPatternAsync(DBHelper db, String date, IThisMonthTransactionByPatternLoadListener listener) {
            this.db = db;
            mListener = listener;
            this.date = date;
        }

        @Override
        protected List<SpendingByPattern> doInBackground(Void... voids) {
            Cursor cursor = db.getThisMonthTransactionsByPattern(date);

            List<SpendingByPattern> spendingPatternList = new ArrayList<>();
            if (cursor != null && cursor.getCount() > 0) {
                try {
                    do {
                        SpendingByPattern spendingPattern = new SpendingByPattern();
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
        protected void onPostExecute(List<SpendingByPattern> spendingPatternList) {
            super.onPostExecute(spendingPatternList);
            if (spendingPatternList != null)
                mListener.onThisMonthTransactionByPatternLoadSuccess(spendingPatternList);
        }
    }

    private static class GetThisMonthTransactionByCategoryAsync extends AsyncTask<Void, Void, List<ExpenseByCategory>> {

        DBHelper db;
        IThisMonthTransactionsByCategoryLoadListener mListener;
        String date = "";

        public GetThisMonthTransactionByCategoryAsync(DBHelper db, String date, IThisMonthTransactionsByCategoryLoadListener listener) {
            this.db = db;
            mListener = listener;
            this.date = date;
        }

        @Override
        protected List<ExpenseByCategory> doInBackground(Void... voids) {
            Cursor cursor = db.getThisMonthTransactionsByCategory(date);

            List<ExpenseByCategory> expenseByCategoryList = new ArrayList<>();
            if (cursor != null && cursor.getCount() > 0) {
                try {
                    do {
                        ExpenseByCategory expenseByCategory = new ExpenseByCategory();
                        String categoryId = cursor.getString(cursor.getColumnIndexOrThrow(DBContract
                        .Transaction.COL_CATEGORY_ID));
                        int sumByCategory = cursor.getInt(cursor.getColumnIndexOrThrow("sum_by_category"));
                        int countByCategory = cursor.getInt(cursor.getColumnIndexOrThrow("count_by_category"));

                        expenseByCategory.setCategoryId(categoryId);
                        expenseByCategory.setSumByCategory(sumByCategory);
                        expenseByCategory.setCountByCategory(countByCategory);

                        expenseByCategoryList.add(expenseByCategory);
                    }
                    while (cursor.moveToNext());
                } finally {
                    cursor.close();
                }
                return expenseByCategoryList;
            }
            return expenseByCategoryList;
        }

        @Override
        protected void onPostExecute(List<ExpenseByCategory> expenseByCategoryList) {
            super.onPostExecute(expenseByCategoryList);
            if (expenseByCategoryList != null)
                mListener.onThisMonthTransactionByCategoryLoadSuccess(expenseByCategoryList);
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


