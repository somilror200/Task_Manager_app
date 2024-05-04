package com.example.taskmanagerapp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import java.util.List;

public class DeleteTaskFragment extends Fragment {

    private ListView listView;
    private TaskDAO taskDAO;
    private List<Task> tasks;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_delete_task, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize UI components
        listView = view.findViewById(R.id.listView);
        taskDAO = new TaskDAO(this.requireActivity()); // Assuming TaskDAO has a constructor with context

        // Populate the list view with tasks
        populateTaskList();

        // Set item click listener for the list view
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showDeleteConfirmationDialog(tasks.get(position));
            }
        });
    }

    private void populateTaskList() {
        taskDAO.open();
        tasks = taskDAO.getAllTasks();
        taskDAO.close();

        if (tasks.isEmpty()) {
            // Handle case when no tasks are available
            Toast.makeText(requireActivity(), "No tasks available", Toast.LENGTH_SHORT).show();
        } else {
            // Populate the list view with task titles
            ArrayAdapter<Task> adapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_list_item_1, tasks);
            listView.setAdapter(adapter);
        }
    }

    private void showDeleteConfirmationDialog(final Task task) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity()); // Use requireActivity() for context within fragment
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
        Toast.makeText(requireActivity(), "Task deleted successfully", Toast.LENGTH_SHORT).show();
    }
}
