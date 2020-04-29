package com.wtho.natureempress.data;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.wtho.natureempress.data.ProductContract.ProductEntry;

public class ProductProvider extends ContentProvider {
   private ProductDbHelper dbHelper;
   private static final int PRODUCTS = 100;
   private static final int PRODUCT_ID = 101;
   private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
   public static final String LOG_TAG = ProductProvider.class.getSimpleName();

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
      SQLiteDatabase database = dbHelper.getReadableDatabase();
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
      cursor.setNotificationUri(getContext().getContentResolver(), uri);
      return cursor;
   }

   @Nullable
   @Override
   public String getType(@NonNull Uri uri) {
      int match = sUriMatcher.match(uri);
      switch (match) {
         case PRODUCTS:
            return ProductEntry.CONTENT_LIST_TYPE;
         case PRODUCT_ID:
            return ProductEntry.CONTENT_ITEM_TYPE;
         default:
            throw new IllegalStateException("Unknown URI " + uri + " with match " +
                    match);
      }
   }

   @Nullable
   @Override
   public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
      int match = sUriMatcher.match(uri);
      switch (match) {
         case PRODUCTS:
            return insertProduct(uri, contentValues);
         default:
            throw new IllegalArgumentException("Inserting is not supported URI " + uri);
      }
   }

   private Uri insertProduct(Uri uri, ContentValues values) {
      String price = values.getAsString(ProductEntry.COLUMN_PRODUCT_PRICE);
      if (price == null) {
         throw new IllegalArgumentException("Product requires price");
      }

      SQLiteDatabase database = dbHelper.getWritableDatabase();
      long id = database.insert(ProductEntry.TABLE_NAME, null, values);
      if (id == -1) {
         Log.e(LOG_TAG, "Fail to insert row " + uri);
         return null;
      }
      getContext().getContentResolver().notifyChange(uri, null);
      return ContentUris.withAppendedId(uri, id);
   }

   @Override
   public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String selection, @Nullable String[] selectionArgs) {
      int match = sUriMatcher.match(uri);
      switch (match) {
         case PRODUCTS:
            return updateProduct(uri, contentValues, selection, selectionArgs);
         case PRODUCT_ID:
            selection = ProductEntry._ID + "=?";
            selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
            return updateProduct(uri, contentValues, selection, selectionArgs);
         default:
            throw new IllegalArgumentException("Inserting is not supported URI " + uri);
      }
   }

   private int updateProduct(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
      if (values.containsKey(values.getAsString(ProductEntry.COLUMN_PRODUCT_NAME))) {
         String name = values.getAsString(ProductEntry.COLUMN_PRODUCT_NAME);
         if (name == null) {
            throw new IllegalArgumentException("Product requires a name");
         }
      }

      if (values.containsKey(ProductEntry.COLUMN_PRODUCT_PRICE)) {
         String price = values.getAsString(ProductEntry.COLUMN_PRODUCT_PRICE);
         if (price == null) {
            throw new IllegalArgumentException("Product requires price");
         }
      }

      if (values.containsKey(ProductEntry.COLUMN_PRODUCT_SIZE)) {
         Integer size = values.getAsInteger(ProductEntry.COLUMN_PRODUCT_SIZE);
         if (size == null) {
            throw new IllegalArgumentException("Product requires size");
         }
      }
      if (values.size() == 0) {
         return 0;
      }
      SQLiteDatabase database = dbHelper.getWritableDatabase();
      int rowUpdated = database.update(ProductEntry.TABLE_NAME, values, selection, selectionArgs);
      return rowUpdated;
   }

   @Override
   public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
      SQLiteDatabase database = dbHelper.getWritableDatabase();
      int match = sUriMatcher.match(uri);
      int rowDeleted;
      switch (match) {
         case PRODUCTS:
            rowDeleted = database.delete(ProductEntry.TABLE_NAME, selection, selectionArgs);
            break;
         case PRODUCT_ID:
            selection = ProductEntry._ID + "=?";
            selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
            rowDeleted = database.delete(ProductEntry.TABLE_NAME, selection, selectionArgs);
            break;
         default:
            throw new IllegalArgumentException("Deletion is not supported for " + uri);
      }
      if (rowDeleted != 0) {
         getContext().getContentResolver().notifyChange(uri, null);
      }
      return rowDeleted;
   }
}