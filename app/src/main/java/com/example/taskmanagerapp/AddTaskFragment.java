package com.example.taskmanagerapp;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class AddTaskFragment extends Fragment {

    private EditText etTitle, etDescription, etDueDate;
    private Button btnSaveTask;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_task, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize UI components
        etTitle = view.findViewById(R.id.etTitle);
        etDescription = view.findViewById(R.id.etDescription);
        etDueDate = view.findViewById(R.id.etDueDate);
        btnSaveTask = view.findViewById(R.id.btnSaveTask);

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
        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(description) || TextUtils.isEmpty(dueDate)) {
            Toast.makeText(requireActivity(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a new Task object
        Task task = new Task(0, title, description, dueDate);

        // Add the task to the database (implementation depends on your data storage approach)
        // You might need to modify this section based on how you manage tasks in your app
        // Here's a placeholder assuming you have a TaskDAO class for database operations:

        TaskDAO taskDAO = new TaskDAO(requireActivity()); // Assuming TaskDAO has a constructor with context
        taskDAO.open();
        long result = taskDAO.addTask(task);
        taskDAO.close();

        // Show a toast message based on the result
        if (result != -1) {
            Toast.makeText(requireActivity(), "Task added successfully", Toast.LENGTH_SHORT).show();
            // Clear input fields after saving task
            etTitle.setText("");
            etDescription.setText("");
            etDueDate.setText("");
        } else {
            Toast.makeText(requireActivity(), "Failed to add task", Toast.LENGTH_SHORT).show();
        }
    }
}
