package com.example.taskmanagerapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;
public class DeleteTaskActivity extends AppCompatActivity {

    private ListView listView;
    private TaskDAO taskDAO;
    private List<Task> tasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_task);

        listView = findViewById(R.id.listView);
        taskDAO = new TaskDAO(this);

        taskDAO.open();
        tasks = taskDAO.getAllTasks();
        taskDAO.close();

        ArrayAdapter<Task> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tasks);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showDeleteConfirmationDialog(tasks.get(position));
            }
        });
    }

    private void showDeleteConfirmationDialog(final Task task) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to delete this task?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        deleteTask(task);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        builder.create().show();
    }

    private void deleteTask(Task task) {
        taskDAO.open();
        taskDAO.deleteTask(task.getId());
        taskDAO.close();
        // Refresh the list
        tasks.remove(task);
        ((ArrayAdapter) listView.getAdapter()).notifyDataSetChanged();
        Toast.makeText(this, "Task deleted successfully", Toast.LENGTH_SHORT).show();
    }
}
