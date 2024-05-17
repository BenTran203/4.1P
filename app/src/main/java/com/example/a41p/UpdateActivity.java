package com.example.a41p;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class UpdateActivity extends AppCompatActivity {

    EditText Title_Input, Description_Input, DueDate_Input;
    Button Update_Button, Delete_Button;

    String id, title, description, duedate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        Title_Input = findViewById(R.id.Title_Input2);
        Description_Input = findViewById(R.id.Description_Input2);
        DueDate_Input = findViewById(R.id.DueDate_Input2);
        Update_Button = findViewById(R.id.Update_Button);
        Delete_Button = findViewById(R.id.Delete_Button);

        // Set actionbar title after getAndSetIntentData method
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(title);
        }

        DueDate_Input.setOnClickListener(view -> showDatePickerDialog());

        Update_Button.setOnClickListener(v -> {
            MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
            title = Title_Input.getText().toString().trim();
            description = Description_Input.getText().toString().trim();
            duedate = DueDate_Input.getText().toString().trim();
            myDB.updateData(id, title, description, duedate);

            Intent resultIntent = new Intent();
            setResult(RESULT_OK, resultIntent);
            Toast.makeText(UpdateActivity.this, "Task Updated Successfully", Toast.LENGTH_SHORT).show();
            finish();
        });

        getAndSetIntentData();
        Delete_Button.setOnClickListener(view -> {
            confirmDialog();
            Intent resultIntent = new Intent();
            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }

    void getAndSetIntentData() {
        if (getIntent().hasExtra("id") && getIntent().hasExtra("title") && getIntent().hasExtra("description") && getIntent().hasExtra("due date")) {
            id = getIntent().getStringExtra("id");
            title = getIntent().getStringExtra("title");
            description = getIntent().getStringExtra("description");
            duedate = getIntent().getStringExtra("due date");

            // Setting Intent Data
            Title_Input.setText(title);
            Description_Input.setText(description);
            DueDate_Input.setText(duedate);
        } else {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
    }


    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        @SuppressLint("SetTextI18n") DatePickerDialog datePickerDialog = new DatePickerDialog(
                UpdateActivity.this,
                (view, year1, month1, dayOfMonth) -> DueDate_Input.setText(dayOfMonth + "/" + (month1 + 1) + "/" + year1),
                year, month, day);
        datePickerDialog.show();
    }

    void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + title + " ?");
        builder.setMessage("Are you sure you want to delete " + title + " ?");
        builder.setPositiveButton("Yes", (dialogInterface, i) -> {
            MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
            myDB.deleteOneRow(id);
            finish();
        });
        builder.setNegativeButton("No", (dialogInterface, i) -> { });
        builder.create().show();
    }


}
