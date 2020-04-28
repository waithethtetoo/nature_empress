package com.wtho.natureempress.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.wtho.natureempress.data.ProductContract.ProductEntry;

public class ProductDbHelper extends SQLiteOpenHelper {
   public static final int DATABASE_VESION = 1;
   public static final String DATABASE_NAME = "nature_empress.db";

   public ProductDbHelper(@Nullable Context context) {
      super(context, DATABASE_NAME, null, DATABASE_VESION);
   }

   @Override
   public void onCreate(SQLiteDatabase sqLiteDatabase) {
      String SQL_CREATE_ENTRIES = "CREATE TABLE " + ProductEntry.TABLE_NAME + "(" +
              ProductEntry._ID + "INTEGER PRIMARY KEY AUTOINCREMENT, " +
              ProductEntry.COLUMN_PRODUCT_NAME + "TEXT NOT NULL, " +
              ProductEntry.COLUMN_PRODUCT_PRICE + "INTEGER NOT NULL," +
              ProductEntry.COLUMN_PRODUCT_SIZE + "INTEGER NOT NULL DEFAULT 0);";
      sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
   }

   @Override
   public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
      String SQL_DELETE_ENTRIES ="";
      sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
      onCreate(sqLiteDatabase);
   }

   @Override
   public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
      super.onDowngrade(db, oldVersion, newVersion);
   }
}
