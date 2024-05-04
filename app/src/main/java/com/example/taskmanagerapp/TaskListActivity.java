package com.example.taskmanagerapp; // Replace with your package name

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.List;

public class TaskListActivity extends AppCompatActivity {

    private ListView listView;
    private TaskDAO taskDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);

        listView = findViewById(R.id.listView);
        taskDAO = new TaskDAO(this);

        // Retrieve tasks from the database
        taskDAO.open();
        List<Task> tasks = taskDAO.getAllTasks();
        taskDAO.close();

        // Display tasks in a ListView
        ArrayAdapter<Task> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tasks);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        taskDAO.close();
    }
}
