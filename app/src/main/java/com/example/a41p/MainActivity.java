package com.example.a41p;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton add_button;
    MyDatabaseHelper myDB;
    ArrayList<String> task_id, task_title, task_description, task_duedate;
    CustomAdapter customAdapter;

    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        recyclerView = findViewById(R.id.recyclerView);
        add_button = findViewById(R.id.floatingActionButton);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(view -> {
            // Navigate back to MainActivity
            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish(); // Finish current activity
        });

        // Set onClickListener for the FloatingActionButton
        add_button.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddActivity.class);
            addActivityResultLauncher.launch(intent);
        });

        myDB = new MyDatabaseHelper(MainActivity.this);
        task_id = new ArrayList<>();
        task_title = new ArrayList<>();
        task_description = new ArrayList<>();
        task_duedate = new ArrayList<>();

        // Store data from the database into the array
        storeDataInArrays(false);

        // Set up the RecyclerView with the CustomAdapter
        customAdapter = new CustomAdapter(MainActivity.this, this,task_id, task_title, task_description, task_duedate);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }

    private final ActivityResultLauncher<Intent> addActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    // Append new data to the RecyclerView
                    storeDataInArrays(true);
                    customAdapter.notifyDataSetChanged();
                }
            }
    );

    void storeDataInArrays(boolean append) {
        Cursor cursor = myDB.readAllData();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        } else {
            if (!append) {
                task_title.clear();
                task_description.clear();
                task_duedate.clear();
            }
            while (cursor.moveToNext()) {
                task_id.add(cursor.getString(0));
                task_title.add(cursor.getString(1));
                task_description.add(cursor.getString(2));
                task_duedate.add(cursor.getString(3));
            }
        }
    }
}
