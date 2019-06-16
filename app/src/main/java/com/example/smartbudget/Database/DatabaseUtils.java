package com.example.smartbudget.Database;

import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;

import com.example.smartbudget.Account.IAccountInsertListener;
import com.example.smartbudget.Account.IAccountLoadListener;
import com.example.smartbudget.AddTransaction.Category.Category;
import com.example.smartbudget.AddTransaction.ICategoryLoadListener;
import com.example.smartbudget.Database.Model.AccountModel;

import java.util.ArrayList;
import java.util.List;

public class DatabaseUtils {

    private static final String TAG = DatabaseUtils.class.getSimpleName();

    public static void insertAccountAsync(DBHelper db, IAccountInsertListener listener, AccountModel... accountModel) {
        InsertAccountAsync task = new InsertAccountAsync(db, listener);
        task.execute(accountModel);
    }

    public static void updateAccountAsync(DBHelper db, IAccountInsertListener listener, AccountModel... accountModel) {
        UpdateAccountAsync task = new UpdateAccountAsync(db, listener);
        task.execute(accountModel);
    }

    public static void deleteAccountAsync(DBHelper db, IAccountLoadListener listener, AccountModel... accountModel) {
        DeleteAccountAsync task = new DeleteAccountAsync(db, listener);
        task.execute(accountModel);
    }

    public static void getAllAccount(DBHelper db, IAccountLoadListener listener) {
        GetAllAccountAsync task = new GetAllAccountAsync(db, listener);
        task.execute();
    }

    public static void getAllCategory(DBHelper db, ICategoryLoadListener listener) {
        GetAllCategoryAsync task = new GetAllCategoryAsync(db, listener);
        task.execute();
    }

    /*
    ============================================================================
    ASYNC TASK DEFINE
    ============================================================================
     */
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
            if (cursor != null && cursor.getCount() >0) {
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



}


