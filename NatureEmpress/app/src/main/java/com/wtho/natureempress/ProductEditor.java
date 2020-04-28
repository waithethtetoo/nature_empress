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

public class ProductEditor extends AppCompatActivity {

   private Spinner spinner_product;
   private EditText edtPrice;
   private Spinner spinner_size;
   private int mProduct = 0;
   private int mSize = 0;

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
                  mProduct = 1;
               } else if (selection.equals(getString(R.string.coffee_soap))) {
                  mProduct = 2;
               } else if (selection.equals(getString(R.string.white_rice_soap))) {
                  mProduct = 3;
               } else if (selection.equals(getString(R.string.brown_rice_soap))) {
                  mProduct = 4;
               } else if (selection.equals(getString(R.string.turmeric_soap))) {
                  mProduct = 5;
               } else if (selection.equals(getString(R.string.aloe_vera_soap))) {
                  mProduct = 6;
               } else if (selection.equals(getString(R.string.potato_soap))) {
                  mProduct = 7;
               } else if (selection.equals(getString(R.string.orange_soap))) {
                  mProduct = 8;
               } else if (selection.equals(getString(R.string.tamarind_soap))) {
                  mProduct = 9;
               }
            }
         }

         @Override
         public void onNothingSelected(AdapterView<?> parent) {
            mProduct = 0;
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
                  mSize = 1;
               } else if (selection.equals(getString(R.string.small_size))) {
                  mSize = 2;
               } else if (selection.equals(getString(R.string.ml))) {
                  mSize = 3;
               }
            }
         }

         @Override
         public void onNothingSelected(AdapterView<?> adapterView) {
            mSize = 0;
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
