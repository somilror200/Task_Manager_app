package com.example.taskmanagerapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class UpdateTaskActivity extends AppCompatActivity {

    private ListView listViewTasks;
    private TaskDAO taskDAO;
    private List<Task> tasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_task);

        listViewTasks = findViewById(R.id.listViewTasks);
        taskDAO = new TaskDAO(this);

        // Populate the list view with tasks
        populateTaskList();

        // Set item click listener for the list view
        listViewTasks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Handle task selection
                Task selectedTask = tasks.get(position);
                updateTask(selectedTask.getId());
            }
        });
    }

    private void populateTaskList() {
        taskDAO.open();
        tasks = taskDAO.getAllTasks();
        taskDAO.close();

        if (tasks.isEmpty()) {
            // Handle case when no tasks are available
            Toast.makeText(this, "No tasks available", Toast.LENGTH_SHORT).show();
        } else {
            // Populate the list view with task titles
            ArrayAdapter<Task> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tasks);
            listViewTasks.setAdapter(adapter);
        }
    }

    private void updateTask(long taskId) {
        // Start UpdateTaskDetailsActivity and pass the task ID
        Intent intent = new Intent(UpdateTaskActivity.this, UpdateTaskDetailsActivity.class);
        intent.putExtra("taskId", taskId);
        startActivity(intent);
    }
}
