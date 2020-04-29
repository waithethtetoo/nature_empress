package com.wtho.natureempress;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.wtho.natureempress.data.ProductContract.ProductEntry;
import com.wtho.natureempress.data.ProductDbHelper;

public class ProductEditor extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor> {

   private Spinner spinner_product;
   private EditText edtPrice;
   private Spinner spinner_size;
   private String mProduct = ProductEntry.PRODUCT_UNKNOWN;
   private String mSize = ProductEntry.SIZE_UNKNOWN;
   private boolean hasChanged = false;
   private Uri currentProductUri;

   private View.OnTouchListener touchListener = new View.OnTouchListener() {
      @Override
      public boolean onTouch(View view, MotionEvent motionEvent) {
         hasChanged = true;
         return false;
      }
   };

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_product_editor);

      Intent intent = getIntent();
      currentProductUri = intent.getData();

      spinner_product = findViewById(R.id.spinner_product);
      edtPrice = findViewById(R.id.product_price);
      spinner_size = findViewById(R.id.spinner_size);
      spinner_product.setOnTouchListener(touchListener);
      edtPrice.setOnTouchListener(touchListener);
      spinner_size.setOnTouchListener(touchListener);

      setSpinner_product();
      setSpinner_size();

   }

   private void setSpinner_product() {
      ArrayAdapter productSpinnerAdapter = ArrayAdapter.createFromResource(this,
              R.array.array_product_options, android.R.layout.simple_spinner_item);
      productSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
      spinner_product.setAdapter(productSpinnerAdapter);
      spinner_product.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
         @Override
         public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
            String selection = (String) adapterView.getItemAtPosition(position);
            if (!TextUtils.isEmpty(selection)) {
               if (selection.equals(getString(R.string.shampoo))) {
                  mProduct = ProductEntry.PRODUCT_SHAMPOO;
               } else if (selection.equals(getString(R.string.coffee_soap))) {
                  mProduct = ProductEntry.PRODUCT_COFFEE_SOAP;
               } else if (selection.equals(getString(R.string.white_rice_soap))) {
                  mProduct = ProductEntry.PRODUCT_WHITE_RICE_SOAP;
               } else if (selection.equals(getString(R.string.brown_rice_soap))) {
                  mProduct = ProductEntry.PRODUCT_BROWN_RICE_SOAP;
               } else if (selection.equals(getString(R.string.turmeric_soap))) {
                  mProduct = ProductEntry.PRODUCT_TURMERIC_SOAP;
               } else if (selection.equals(getString(R.string.aloe_vera_soap))) {
                  mProduct = ProductEntry.PRODUCT_ALOE_VERA_SOAP;
               } else if (selection.equals(getString(R.string.potato_soap))) {
                  mProduct = ProductEntry.PRODUCT_POTATO_SOAP;
               } else if (selection.equals(getString(R.string.orange_soap))) {
                  mProduct = ProductEntry.PRODUCT_ORANGE_SOAP;
               } else if (selection.equals(getString(R.string.tamarind_soap))) {
                  mProduct = ProductEntry.PRODUCT_TAMARIND_SOAP;
               }
            }
         }

         @Override
         public void onNothingSelected(AdapterView<?> parent) {
            mProduct = ProductEntry.PRODUCT_UNKNOWN;
         }
      });
   }

   private void setSpinner_size() {
      ArrayAdapter sizeSpinnerAdapter = ArrayAdapter.createFromResource(this,
              R.array.array_product_size, android.R.layout.simple_spinner_item);
      sizeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
      spinner_size.setAdapter(sizeSpinnerAdapter);
      spinner_size.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
         @Override
         public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
            String selection = (String) adapterView.getItemAtPosition(position);
            if (!TextUtils.isEmpty(selection)) {
               if (selection.equals(getString(R.string.big_size))) {
                  mSize = ProductEntry.SIZE_BIG;
               } else if (selection.equals(getString(R.string.small_size))) {
                  mSize = ProductEntry.SIZE_SMALL;
               } else if (selection.equals(getString(R.string.ml))) {
                  mSize = ProductEntry.SIZE_ML;
               }
            }
         }

         @Override
         public void onNothingSelected(AdapterView<?> adapterView) {
            mSize = ProductEntry.SIZE_UNKNOWN;
         }
      });

   }

   private void saveProduct() {
      ProductDbHelper dbHelper = new ProductDbHelper(this);
      SQLiteDatabase database = dbHelper.getWritableDatabase();

      String priceString = edtPrice.getText().toString().trim();

      if (currentProductUri == null && mProduct == ProductEntry.PRODUCT_UNKNOWN
              && mSize == ProductEntry.SIZE_UNKNOWN) {
         return;
      }

      ContentValues values = new ContentValues();
      values.put(ProductEntry.COLUMN_PRODUCT_NAME, mProduct);
      values.put(ProductEntry.COLUMN_PRODUCT_PRICE, priceString);
      values.put(ProductEntry.COLUMN_PRODUCT_SIZE, mSize);
      if (currentProductUri == null) {
         Uri newUri = getContentResolver().insert(ProductEntry.CONTENT_URI, values);
         if (newUri == null) {
            Toast.makeText(this, "Insert Product Failed", Toast.LENGTH_SHORT).show();
         }
      } else {
         int rowAffected = getContentResolver().update(currentProductUri, values, null,
                 null);
         if (rowAffected == 0) {
            Toast.makeText(this, "Update Product Failed", Toast.LENGTH_SHORT).show();
         } else {
            Toast.makeText(this, "Update Product Successful", Toast.LENGTH_SHORT).show();
         }
      }
   }

   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      getMenuInflater().inflate(R.menu.menu_product_editor, menu);
      return true;
   }

   @Override
   public boolean onPrepareOptionsMenu(Menu menu) {
      super.onPrepareOptionsMenu(menu);
      if (currentProductUri == null) {
         MenuItem item = menu.findItem(R.id.action_delete);
         item.setVisible(false);
      }
      return true;
   }

   @Override
   public boolean onOptionsItemSelected(@NonNull MenuItem item) {
      switch (item.getItemId()) {
         case R.id.action_save:
            saveProduct();
            finish();
            return true;
         case R.id.action_delete:
            return true;
         case android.R.id.home:
            if (!hasChanged) {
               NavUtils.navigateUpFromSameTask(this);
               return true;
            }
      }
      return super.onOptionsItemSelected(item);
   }

   @NonNull
   @Override
   public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
      String[] projection = {
              ProductEntry._ID,
              ProductEntry.COLUMN_PRODUCT_NAME,
              ProductEntry.COLUMN_PRODUCT_PRICE,
              ProductEntry.COLUMN_PRODUCT_SIZE};

      return new CursorLoader(this, currentProductUri, projection,
              null, null, null);
   }

   @Override
   public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
      if (data == null || data.getCount() < 1) {
         return;
      }
      if (data.moveToFirst()) {
         int nameColumnIndex = data.getColumnIndex(ProductEntry.COLUMN_PRODUCT_NAME);
         int priceColumnIndex = data.getColumnIndex(ProductEntry.COLUMN_PRODUCT_PRICE);
         int sizeColumnIndex = data.getColumnIndex(ProductEntry.COLUMN_PRODUCT_SIZE);

         String name = data.getString(nameColumnIndex);
         String price = data.getString(priceColumnIndex);
         String size = data.getString(sizeColumnIndex);

         switch (name) {
            case ProductEntry.PRODUCT_SHAMPOO:
               spinner_product.setSelection(1);
               break;
            case ProductEntry.PRODUCT_COFFEE_SOAP:
               spinner_product.setSelection(2);
               break;
            case ProductEntry.PRODUCT_WHITE_RICE_SOAP:
               spinner_product.setSelection(3);
               break;
            case ProductEntry.PRODUCT_BROWN_RICE_SOAP:
               spinner_product.setSelection(4);
               break;
            case ProductEntry.PRODUCT_TURMERIC_SOAP:
               spinner_product.setSelection(5);
               break;
            case ProductEntry.PRODUCT_ALOE_VERA_SOAP:
               spinner_product.setSelection(6);
               break;
            case ProductEntry.PRODUCT_POTATO_SOAP:
               spinner_product.setSelection(7);
               break;
            case ProductEntry.PRODUCT_ORANGE_SOAP:
               spinner_product.setSelection(8);
               break;
            case ProductEntry.PRODUCT_TAMARIND_SOAP:
               spinner_product.setSelection(9);
               break;
         }

         switch (size) {
            case ProductEntry.SIZE_BIG:
               spinner_size.setSelection(1);
               break;
            case ProductEntry.SIZE_SMALL:
               spinner_size.setSelection(2);
               break;
            case ProductEntry.SIZE_ML:
               spinner_size.setSelection(3);
               break;
         }
      }
   }

   @Override
   public void onLoaderReset(@NonNull Loader<Cursor> loader) {
      spinner_product.setSelection(0);
      edtPrice.setText("");
      spinner_size.setSelection(0);
   }
}
