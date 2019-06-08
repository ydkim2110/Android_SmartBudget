package com.example.smartbudget.Database;

import android.database.Cursor;
import android.os.AsyncTask;

import com.example.smartbudget.Transaction.Dialog.Category;
import com.example.smartbudget.Transaction.ICategoryLoadListener;

import java.util.ArrayList;
import java.util.List;

public class DatabaseUtils {

    public static void getAllCategory(DBHelper db, ICategoryLoadListener listener) {
        GetAllCategoryAsync task = new GetAllCategoryAsync(db, listener);
        task.execute();
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


