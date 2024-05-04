package com.example.taskmanagerapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;

public class TaskListFragment extends Fragment {

    private ListView listView;
    private TaskDAO taskDAO;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_task_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize UI components
        listView = view.findViewById(R.id.listView);
        taskDAO = new TaskDAO(this.requireActivity()); // Assuming TaskDAO has a constructor with context

        // Retrieve tasks from the database
        taskDAO.open();
        List<Task> tasks = taskDAO.getAllTasks();
        taskDAO.close();

        // Display tasks in a ListView
        ArrayAdapter<Task> adapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_list_item_1, tasks);
        listView.setAdapter(adapter);
    }
}
