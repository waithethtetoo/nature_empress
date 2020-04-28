package com.wtho.natureempress.data;

import android.net.Uri;
import android.provider.BaseColumns;

import com.wtho.natureempress.R;

public final class ProductContract {
   public ProductContract() {
   }

   public static final String CONTENT_AUTHORITY = "com.wtho.natureempress";
   public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
   public static final String PRODUCT_PATH = "products";

   public static class ProductEntry implements BaseColumns {
      public static final Uri CONNTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PRODUCT_PATH);

      public static final String TABLE_NAME = "products";
      public static final String _ID = BaseColumns._ID;
      public static final String COLUMN_PRODUCT_NAME = "name";
      public static final String COLUMN_PRODUCT_SIZE = "size";
      public static final String COLUMN_PRODUCT_PRICE = "price";

      public static final String PRODUCT_UNKNOWN = String.valueOf(R.string.unknown);
      ;
      public static final String PRODUCT_SHAMPOO = String.valueOf(R.string.shampoo);
      public static final String PRODUCT_COFFEE_SOAP = String.valueOf(R.string.coffee_soap);
      public static final String PRODUCT_WHITE_RICE_SOAP = String.valueOf(R.string.white_rice_soap);
      public static final String PRODUCT_BROWN_RICE_SOAP = String.valueOf(R.string.brown_rice_soap);
      public static final String PRODUCT_TURMERIC_SOAP = String.valueOf(R.string.turmeric_soap);
      public static final String PRODUCT_ALOE_VERA_SOAP = String.valueOf(R.string.aloe_vera_soap);
      public static final String PRODUCT_POTATO_SOAP = String.valueOf(R.string.potato_soap);
      public static final String PRODUCT_ORANGE_SOAP = String.valueOf(R.string.orange_soap);
      public static final String PRODUCT_TAMARIND_SOAP = String.valueOf(R.string.tamarind_soap);

      public static final int SIZE_UNKNOWN = 0;
      public static final int SIZE_BIG = 1;
      public static final int SIZE_SMALL = 2;
      public static final int SIZE_ML = 3;
   }
}
