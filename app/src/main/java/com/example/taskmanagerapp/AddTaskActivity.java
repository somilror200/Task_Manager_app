package com.example.taskmanagerapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddTaskActivity extends AppCompatActivity {

    private EditText etTitle, etDescription, etDueDate;
    private Button btnSaveTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        // Initialize UI components
        etTitle = findViewById(R.id.etTitle);
        etDescription = findViewById(R.id.etDescription);
        etDueDate = findViewById(R.id.etDueDate);
        btnSaveTask = findViewById(R.id.btnSaveTask);

        // Set click listener for save button
        btnSaveTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTask();
            }
        });
    }

    private void saveTask() {
        // Get input values
        String title = etTitle.getText().toString().trim();
        String description = etDescription.getText().toString().trim();
        String dueDate = etDueDate.getText().toString().trim();

        // Validate input
        if (!title.isEmpty() && !description.isEmpty() && !dueDate.isEmpty()) {
            // Create a new Task object
            Task task = new Task(0, title, description, dueDate);

            // Add the task to the database
            TaskDAO taskDAO = new TaskDAO(this);
            taskDAO.open();
            long result = taskDAO.addTask(task);
            taskDAO.close();

            // Show a toast message based on the result
            if (result != -1) {
                Toast.makeText(this, "Task added successfully", Toast.LENGTH_SHORT).show();
                // Clear input fields after saving task
                etTitle.setText("");
                etDescription.setText("");
                etDueDate.setText("");
            } else {
                Toast.makeText(this, "Failed to add task", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
        }
    }
}
