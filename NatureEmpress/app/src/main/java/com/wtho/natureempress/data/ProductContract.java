package com.wtho.natureempress.data;

import android.content.ContentResolver;
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
      public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PRODUCT_PATH);

      public static final String CONTENT_LIST_TYPE =
              ContentResolver.CURSOR_DIR_BASE_TYPE + "/" +
                      CONTENT_AUTHORITY + "/" + PRODUCT_PATH;
      public static final String CONTENT_ITEM_TYPE =
              ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" +
                      CONTENT_AUTHORITY + "/" + PRODUCT_PATH;

      public static final String TABLE_NAME = "products";
      public static final String _ID = BaseColumns._ID;
      public static final String COLUMN_PRODUCT_NAME = "name";
      public static final String COLUMN_PRODUCT_SIZE = "size";
      public static final String COLUMN_PRODUCT_PRICE = "price";

      public static final String PRODUCT_UNKNOWN = String.valueOf(R.string.unknown);
      ;
      public static final String PRODUCT_SHAMPOO = "Herbal Green Tea Shampoo";
      public static final String PRODUCT_COFFEE_SOAP = "Coffee Soap";
      public static final String PRODUCT_WHITE_RICE_SOAP = "White Rice Soap";
      public static final String PRODUCT_BROWN_RICE_SOAP = "Brown Rice Soap";
      public static final String PRODUCT_TURMERIC_SOAP = "Turmeric Soap";
      public static final String PRODUCT_ALOE_VERA_SOAP = "Aloe Vera Soap";
      public static final String PRODUCT_POTATO_SOAP = "Potato Soap";
      public static final String PRODUCT_ORANGE_SOAP = "Orange Soap";
      public static final String PRODUCT_TAMARIND_SOAP = "Tamarind Soap";

      public static final String SIZE_UNKNOWN = "Unknown";
      public static final String SIZE_BIG = "Big";
      public static final String SIZE_SMALL = "Small";
      public static final String SIZE_ML = "250ml";
   }
}
