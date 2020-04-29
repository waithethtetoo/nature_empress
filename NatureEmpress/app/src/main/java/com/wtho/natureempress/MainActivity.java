package com.wtho.natureempress;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.wtho.natureempress.data.ProductContract.ProductEntry;
import com.wtho.natureempress.data.ProductDbHelper;

public class MainActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor> {
   private ListView productList;
   private ProductListAdapter adapter;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);

      FloatingActionButton fab = findViewById(R.id.fab);
      fab.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            Intent intent = new Intent(MainActivity.this, ProductEditor.class);
            startActivity(intent);
         }
      });

      productList = findViewById(R.id.list);
      adapter = new ProductListAdapter(this, null);
      productList.setAdapter(adapter);

      productList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
         @Override
         public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            Intent intent = new Intent(MainActivity.this, ProductEditor.class);
            Uri currentProductUri = ContentUris.withAppendedId(ProductEntry.CONTENT_URI, id);
            intent.setData(currentProductUri);
            startActivity(intent);
         }
      });
      getSupportLoaderManager().initLoader(0, null, MainActivity.this);
   }

   @NonNull
   @Override
   public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
      String[] projection = {
              ProductEntry._ID,
              ProductEntry.COLUMN_PRODUCT_NAME,
              ProductEntry.COLUMN_PRODUCT_PRICE,
              ProductEntry.COLUMN_PRODUCT_SIZE};

      return new CursorLoader(this,
              ProductEntry.CONTENT_URI,
              projection, null,
              null, null);
   }

   @Override
   public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
      adapter.swapCursor(data);
   }

   @Override
   public void onLoaderReset(@NonNull Loader<Cursor> loader) {
      adapter.swapCursor(null);
   }
}
