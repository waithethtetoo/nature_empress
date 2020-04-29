package com.wtho.natureempress;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.wtho.natureempress.data.ProductContract.ProductEntry;

import org.w3c.dom.Text;

public class ProductListAdapter extends CursorAdapter {

   public ProductListAdapter(Context context, Cursor c) {
      super(context, c, 0);
   }

   @Override
   public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
      return LayoutInflater.from(context).inflate(R.layout.list_item, viewGroup, false);
   }

   @Override
   public void bindView(View view, Context context, Cursor cursor) {
      TextView tvName = view.findViewById(R.id.name);
      TextView tvPrice = view.findViewById(R.id.price);
      TextView tvSize = view.findViewById(R.id.size);

      int nameColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_NAME);
      int priceColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_PRICE);
      int sizeColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_SIZE);

      String nameText = cursor.getString(nameColumnIndex);
      String priceText = cursor.getString(priceColumnIndex);
      String sizeText = cursor.getString(sizeColumnIndex);

      tvName.setText(nameText);
      tvPrice.setText(priceText);
      tvSize.setText(sizeText);
   }
}
