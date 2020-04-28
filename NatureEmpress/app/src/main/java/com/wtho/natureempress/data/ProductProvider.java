package com.wtho.natureempress.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.wtho.natureempress.data.ProductContract.ProductEntry;

public class ProductProvider extends ContentProvider {
   private ProductDbHelper dbHelper;
   private static final int PRODUCTS = 100;
   private static final int PRODUCT_ID = 101;
   private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

   static {
      sUriMatcher.addURI(ProductContract.CONTENT_AUTHORITY,
              ProductContract.PRODUCT_PATH, PRODUCTS);

      sUriMatcher.addURI(ProductContract.CONTENT_AUTHORITY,
              ProductContract.PRODUCT_PATH + "#", PRODUCT_ID);
   }

   @Override
   public boolean onCreate() {
      dbHelper = new ProductDbHelper(getContext());
      return true;
   }

   @Nullable
   @Override
   public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String order) {
      Cursor cursor;
      SQLiteDatabase database = dbHelper.getWritableDatabase();
      int match = sUriMatcher.match(uri);
      switch (match) {
         case PRODUCTS:
            cursor = database.query(ProductEntry.TABLE_NAME,
                    projection, selection, selectionArgs, null, null, order);
            break;
         case PRODUCT_ID:
            selection = ProductEntry._ID + "+?";
            selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
            cursor = database.query(ProductEntry.TABLE_NAME,
                    projection, selection, selectionArgs, null, null, order);
            break;
         default:
            throw new IllegalArgumentException("Cannot query unknown URI " + uri);
      }
      return cursor;
   }

   @Nullable
   @Override
   public String getType(@NonNull Uri uri) {
      return null;
   }

   @Nullable
   @Override
   public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
      return null;
   }

   @Override
   public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
      return 0;
   }

   @Override
   public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
      return 0;
   }
}
