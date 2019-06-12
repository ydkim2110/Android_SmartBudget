package com.example.smartbudget.Database;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.AsyncTask;

import com.example.smartbudget.Account.Account;
import com.example.smartbudget.Account.IAccountLoadListener;
import com.example.smartbudget.AddTransaction.Dialog.Category;
import com.example.smartbudget.AddTransaction.ICategoryLoadListener;
import com.example.smartbudget.Database.Model.AccountModel;

import java.util.ArrayList;
import java.util.List;

public class DatabaseUtils {

    public static void insertAccountAsync(DBHelper db, AccountModel... accountModel) {
        insertAccountAsync task = new insertAccountAsync(db);
        task.execute(accountModel);
    }

    public static void getAllCategory(DBHelper db, ICategoryLoadListener listener) {
        GetAllCategoryAsync task = new GetAllCategoryAsync(db, listener);
        task.execute();
    }

    public static void getAllAccount(DBHelper db, IAccountLoadListener listener) {
        GetAllAccountAsync task = new GetAllAccountAsync(db, listener);
        task.execute();
    }

    /*
    ============================================================================
    ASYNC TASK DEFINE
    ============================================================================
     */

    private static class insertAccountAsync extends AsyncTask<AccountModel, Void, Void> {

        DBHelper db;

        public insertAccountAsync(DBHelper db) {
            this.db = db;
        }

        @Override
        protected Void doInBackground(AccountModel... accountModels) {
            db.insertToAccount(accountModels[0]);
            return null;
        }
    }

    private static class GetAllCategoryAsync extends AsyncTask<Void, Void, List<Category>> {

        DBHelper db;
        ICategoryLoadListener mListener;

        public GetAllCategoryAsync(DBHelper db, ICategoryLoadListener listener) {
            this.db = db;
            mListener = listener;
        }

        @Override
        protected List<Category> doInBackground(Void... voids) {
            Cursor cursor = db.getAllCategories();
            List<Category> categoryList = new ArrayList<>();
            try {
                do {
                    Category category = new Category();
                    String icon = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Category.COL_ICON));
                    String name = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Category.COL_NAME));
                    category.setIcon(Integer.parseInt(icon));
                    category.setName(name);
                    categoryList.add(category);
                }
                while (cursor.moveToNext());
            } finally {
                cursor.close();
            }
            return categoryList;
        }

        @Override
        protected void onPostExecute(List<Category> categoryList) {
            super.onPostExecute(categoryList);
            mListener.onCategoryLoadSuccess(categoryList);
        }
    }

    private static class GetAllAccountAsync extends AsyncTask<Void, Void, List<Account>> {

        DBHelper db;
        IAccountLoadListener mListener;

        public GetAllAccountAsync(DBHelper db, IAccountLoadListener listener) {
            this.db = db;
            mListener = listener;
        }

        @Override
        protected List<Account> doInBackground(Void... voids) {
            Cursor cursor = db.getAllAccounts();
            List<Account> accountList = new ArrayList<>();
            try {
                do {
                    Account account = new Account();
                    long id = cursor.getLong(cursor.getColumnIndexOrThrow(DBContract.Account._ID));
                    String name = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Account.COL_NAME));
                    String amount = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Account.COL_AMOUNT));
                    account.setName(name);
                    account.setAmount(Double.parseDouble(amount));
                    accountList.add(account);
                }
                while (cursor.moveToNext());
            } finally {
                cursor.close();
            }
            return accountList;
        }

        @Override
        protected void onPostExecute(List<Account> accountList) {
            super.onPostExecute(accountList);
            mListener.onAccountLoadSuccess(accountList);
        }
    }


}


