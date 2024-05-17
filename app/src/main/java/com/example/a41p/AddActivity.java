package com.example.a41p;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Calendar;

public class AddActivity extends AppCompatActivity {

    EditText Title_Input, Description_Input, DueDate_Input;
    Button Add_Button;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        // Set up the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Initialize views
        Title_Input = findViewById(R.id.Title_Input);
        Description_Input = findViewById(R.id.Description_Input);
        DueDate_Input = findViewById(R.id.DueDate_Input);
        Add_Button = findViewById(R.id.Add_Button);

        // Set onClickListener for the DueDate EditText
        DueDate_Input.setOnClickListener(view -> showDatePickerDialog());

        // Set onClickListener for the add button
        Add_Button.setOnClickListener(view -> {
            // Get input values
            String title = Title_Input.getText().toString().trim();
            String description = Description_Input.getText().toString().trim();
            String dueDate = DueDate_Input.getText().toString().trim();

            // Check if inputs are valid
            if (title.isEmpty() || description.isEmpty() || dueDate.isEmpty()) {
                Toast.makeText(AddActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            } else {
                // Insert the task into the database
                MyDatabaseHelper myDB = new MyDatabaseHelper(AddActivity.this);
                myDB.addTask(title, description, dueDate);
                Toast.makeText(AddActivity.this, "Task added successfully", Toast.LENGTH_SHORT).show();
                // Clear inputs
                Title_Input.setText("");
                Description_Input.setText("");
                DueDate_Input.setText("");
                setResult(RESULT_OK);  // Indicate successful addition
                finish();  // Close AddActivity and return to MainActivity
            }
        });
    }

    private void showDatePickerDialog() {
        if (isFinishing() || isDestroyed()) {
            return;
        }
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        @SuppressLint("SetTextI18n") DatePickerDialog datePickerDialog = new DatePickerDialog(
                AddActivity.this,
                (view, year1, month1, dayOfMonth) -> DueDate_Input.setText(dayOfMonth + "/" + (month1 + 1) + "/" + year1),
                year, month, day);
        datePickerDialog.show();
    }
}
