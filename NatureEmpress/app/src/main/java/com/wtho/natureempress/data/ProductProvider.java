package com.wtho.natureempress.data;

import android.content.ContentProvider;
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
      int match = sUriMatcher.match(uri);
      switch (match) {
         case PRODUCTS:
            return insertProduct(uri, contentValues);
         default:
            throw new IllegalArgumentException("Inserting is not supported URI " + uri);
      }
   }

   private Uri insertProduct(Uri uri, ContentValues values) {
      SQLiteDatabase database = dbHelper.getWritableDatabase();
      long id = database.insert(ProductEntry.TABLE_NAME, null, values);
      String price = values.getAsString(ProductEntry.COLUMN_PRODUCT_PRICE);
      if (price == null) {
         throw new IllegalArgumentException("Product requires price");
      }
      if (id == -1) {
         Log.e(LOG_TAG, "Fail to insert row " + uri);
         return null;
      }
      return ContentUris.withAppendedId(uri, id);
   }

   @Override
   public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
      return 0;
   }

   @Override
   public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String selection, @Nullable String[] selectionArgs) {
      int match=sUriMatcher.match(uri);
      switch (match){
         case PRODUCTS:
            break;
         case PRODUCT_ID:
            break;
         default:
            throw new IllegalArgumentException("Inserting is not supported URI " + uri);
      }
      return 0;
   }
}
