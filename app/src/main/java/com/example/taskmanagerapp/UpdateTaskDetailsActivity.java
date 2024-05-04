package com.example.taskmanagerapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class UpdateTaskDetailsActivity extends AppCompatActivity {

    private EditText etTitle, etDescription, etDueDate;
    private Button btnSaveChanges;
    private long taskId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_task_details);

        etTitle = findViewById(R.id.etTitle);
        etDescription = findViewById(R.id.etDescription);
        etDueDate = findViewById(R.id.etDueDate);
        btnSaveChanges = findViewById(R.id.btnSaveChanges);

        taskId = getIntent().getLongExtra("taskId", -1);

        populateTaskDetails(taskId);

        btnSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveChanges();
            }
        });
    }

    private void populateTaskDetails(long taskId) {
        TaskDAO taskDAO = new TaskDAO(this);
        taskDAO.open();
        Task task = taskDAO.getTask(taskId);
        taskDAO.close();

        if (task != null) {
            etTitle.setText(task.getTitle());
            etDescription.setText(task.getDescription());
            etDueDate.setText(task.getDueDate());
        } else {
            Toast.makeText(this, "Task not found", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void saveChanges() {
        String title = etTitle.getText().toString().trim();
        String description = etDescription.getText().toString().trim();
        String dueDate = etDueDate.getText().toString().trim();

        if (!title.isEmpty() && !description.isEmpty() && !dueDate.isEmpty()) {
            Task updatedTask = new Task(taskId, title, description, dueDate);
            TaskDAO taskDAO = new TaskDAO(this);
            taskDAO.open();
            int rowsAffected = taskDAO.updateTask(updatedTask);
            taskDAO.close();

            if (rowsAffected > 0) {
                Toast.makeText(this, "Task updated successfully", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Failed to update task", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
        }
    }
}
