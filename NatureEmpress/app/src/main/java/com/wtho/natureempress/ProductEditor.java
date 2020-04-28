package com.wtho.natureempress;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.wtho.natureempress.data.ProductContract.ProductEntry;

public class ProductEditor extends AppCompatActivity {

   private Spinner spinner_product;
   private EditText edtPrice;
   private Spinner spinner_size;
   private int mProduct = ProductEntry.PRODUCT_UNKNOWN;
   private int mSize = ProductEntry.SIZE_UNKNOWN;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_product_editor);

      spinner_product = findViewById(R.id.spinner_product);
      edtPrice = findViewById(R.id.product_price);
      spinner_size = findViewById(R.id.spinner_size);

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

   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      getMenuInflater().inflate(R.menu.menu_product_editor, menu);
      return true;
   }

   @Override
   public boolean onOptionsItemSelected(@NonNull MenuItem item) {
      switch (item.getItemId()) {
         case R.id.action_save:
            return true;
         case R.id.action_delete:
            return true;
         case android.R.id.home:
            NavUtils.navigateUpFromSameTask(this);
            return true;
      }
      return super.onOptionsItemSelected(item);
   }
}
