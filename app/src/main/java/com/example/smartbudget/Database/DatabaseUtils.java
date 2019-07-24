package com.example.smartbudget.Database;

import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;

import com.example.smartbudget.Ui.Account.IAccountInsertListener;
import com.example.smartbudget.Ui.Account.IAccountLoadListener;
import com.example.smartbudget.Ui.Transaction.Add.ICategoryLoadListener;
import com.example.smartbudget.Ui.Transaction.Add.ITransactionInsertListener;
import com.example.smartbudget.Model.AccountModel;
import com.example.smartbudget.Model.CategoryModel;
import com.example.smartbudget.Model.TransactionModel;
import com.example.smartbudget.Ui.Transaction.ITransactionLoadListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

    public static void deleteAccountAsync(DBHelper db, IAccountLoadListener listener, AccountModel... accountModels) {
        DeleteAccountAsync task = new DeleteAccountAsync(db, listener);
        task.execute(accountModels);
    }

    public static void getAllAccount(DBHelper db, IAccountLoadListener listener) {
        GetAllAccountAsync task = new GetAllAccountAsync(db, listener);
        task.execute();
    }

    public static void getAllCategory(DBHelper db, ICategoryLoadListener listener) {
        GetAllCategoryAsync task = new GetAllCategoryAsync(db, listener);
        task.execute();
    }

    public static void getAllTransaction(DBHelper db, ITransactionLoadListener listener) {
        GetAllTransactionAsync task = new GetAllTransactionAsync(db, listener);
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
        IAccountLoadListener mListener;

        public DeleteAccountAsync(DBHelper db, IAccountLoadListener listener) {
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


    private static class GetAllAccountAsync extends AsyncTask<Void, Void, List<AccountModel>> {

        DBHelper db;
        IAccountLoadListener mListener;

        public GetAllAccountAsync(DBHelper db, IAccountLoadListener listener) {
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
            mListener.onAccountLoadSuccess(accountList);
        }
    }

    private static class GetAllTransactionAsync extends AsyncTask<Void, Void, List<TransactionModel>> {

        DBHelper db;
        ITransactionLoadListener mListener;

        public GetAllTransactionAsync(DBHelper db, ITransactionLoadListener listener) {
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
                        String description = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Transaction.COL_DESCRIPTION));
                        String amount = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Transaction.COL_AMOUNT));
                        String type = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Transaction.COL_TYPE));
                        String date = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Transaction.COL_DATE));
                        String categoryId = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Transaction.COL_CATEGORY_ID));
                        String accountId = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Transaction.COL_ACCOUNT_ID));
                        String toAccount = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Transaction.COL_TO_ACCOUNT));

                        transaction.setId((int) id);
                        transaction.setTransaction_description(description);
                        transaction.setTransaction_amount(Double.parseDouble(amount));
                        transaction.setTransaction_type(type);
                        transaction.setTransaction_date(date);
                        transaction.setCategory_id(Integer.parseInt(categoryId));
                        transaction.setAccount_id(Integer.parseInt(accountId));
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
            mListener.onTransactionLoadSuccess(transactionList);
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


