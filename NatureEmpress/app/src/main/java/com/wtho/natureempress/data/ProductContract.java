package com.wtho.natureempress.data;

import android.provider.BaseColumns;

public final class ProductContract {
   public ProductContract() {
   }

   public static class ProductEntry implements BaseColumns {
      public static final String TABLE_NAME = "products";
      public static final String _ID = BaseColumns._ID;
      public static final String COLUMN_PRODUCT_NAME = "name";
      public static final String COLUMN_PRODUCT_SIZE = "size";
      public static final String COLUMN_PRODUCT_PRICE = "price";

      public static final int PRODUCT_UNKNOWN=0;
      public static final int PRODUCT_SHAMPOO=1;
      public static final int PRODUCT_COFFEE_SOAP=2;
      public static final int PRODUCT_WHITE_RICE_SOAP=3;
      public static final int PRODUCT_BROWN_RICE_SOAP=4;
      public static final int PRODUCT_TURMERIC_SOAP=5;
      public static final int PRODUCT_ALOE_VERA_SOAP=6;
      public static final int PRODUCT_POTATO_SOAP=7;
      public static final int PRODUCT_ORANGE_SOAP=8;
      public static final int PRODUCT_TAMARIND_SOAP=9;

      public static final int SIZE_UNKNOWN=0;
      public static final int SIZE_BIG=1;
      public static final int SIZE_SMALL=2;
      public static final int SIZE_ML=3;
   }
}
