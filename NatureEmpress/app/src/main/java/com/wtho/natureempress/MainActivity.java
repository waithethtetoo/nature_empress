package com.wtho.natureempress;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.wtho.natureempress.data.ProductDbHelper;

public class MainActivity extends AppCompatActivity {
   private ProductDbHelper dbHelper;
   private SQLiteDatabase database;

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

      dbHelper = new ProductDbHelper(this);
      database = dbHelper.getReadableDatabase();
   }
}
