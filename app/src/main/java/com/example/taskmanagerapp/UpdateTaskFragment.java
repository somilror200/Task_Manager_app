package com.example.taskmanagerapp;

import android.content.Intent;
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
import androidx.fragment.app.Fragment;

import java.util.List;

public class UpdateTaskFragment extends Fragment {

    private ListView listViewTasks;
    private TaskDAO taskDAO;
    private List<Task> tasks;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_update_task, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize UI components
        listViewTasks = view.findViewById(R.id.listViewTasks);
        taskDAO = new TaskDAO(this.requireActivity()); // Assuming TaskDAO has a constructor with context

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
            Toast.makeText(requireActivity(), "No tasks available", Toast.LENGTH_SHORT).show();
        } else {
            // Populate the list view with task titles
            ArrayAdapter<Task> adapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_list_item_1, tasks);
            listViewTasks.setAdapter(adapter);
        }
    }

    private void updateTask(long taskId) {
        // Start UpdateTaskDetailsActivity and pass the task ID
        Intent intent = new Intent(requireActivity(), UpdateTaskDetailsActivity.class);
        intent.putExtra("taskId", taskId);
        startActivity(intent);
    }
}
