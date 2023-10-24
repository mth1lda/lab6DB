package com.example.lab6db;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private DBHelper helper;
    private SQLiteDatabase database;
    private Button refresh_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listView1);
        refresh_btn = findViewById(R.id.button2);

        helper = new DBHelper(getApplicationContext());
        try {
            database = helper.getWritableDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }

        refresh_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<HashMap<String,String>> animals = new ArrayList<>();
                HashMap<String, String> animal;

                // запрос к бд
                Cursor cursor = database.rawQuery("SELECT id, name, age FROM animals", null);
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    animal = new HashMap<>();
                    animal.put("name", cursor.getString(1));
                    animal.put("age", cursor.getString(2));
                    animals.add(animal);
                    cursor.moveToFirst();
                } cursor.close();

                SimpleAdapter adapter = new SimpleAdapter(
                        getApplicationContext(),
                        animals,
                        android.R.layout.simple_list_item_2,
                        new String [] {"name", "age"},
                        new int [] {android.R.id.text1,android.R.id.text2}
                );
                listView.setAdapter(adapter);
            }
        });
    }
}