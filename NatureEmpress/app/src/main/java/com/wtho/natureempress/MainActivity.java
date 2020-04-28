package com.wtho.natureempress;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.wtho.natureempress.data.ProductContract.ProductEntry;
import com.wtho.natureempress.data.ProductDbHelper;

public class MainActivity extends AppCompatActivity {

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);

      ListView productList = findViewById(R.id.list);
      FloatingActionButton fab = findViewById(R.id.fab);
      fab.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            Intent intent = new Intent(MainActivity.this, ProductEditor.class);
            startActivity(intent);
         }
      });
   }

   @Override
   protected void onStart() {
      super.onStart();
      displayInfo();
   }

   private void displayInfo() {
      ProductDbHelper dbHelper = new ProductDbHelper(this);
      SQLiteDatabase database = dbHelper.getReadableDatabase();

      String[] projection = {ProductEntry.COLUMN_PRODUCT_NAME,
              ProductEntry.COLUMN_PRODUCT_PRICE,
              ProductEntry.COLUMN_PRODUCT_SIZE};
      Cursor cursor = getContentResolver().query(
              ProductEntry.CONNTENT_URI, projection,
              null, null, null);
      
      Toast.makeText(this, "Number of row in Product table " + cursor.getCount(), Toast.LENGTH_LONG).show();
   }
}
