package com.example.taskmanagerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI components
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        // Set navigationItemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.navigation_add) {
                    openFragment(AddTaskFragment.class);
                    return true;
                }
                else if (itemId == R.id.navigation_view_tasks) {
                    openFragment(TaskListFragment.class);
                    return true;
                }
                else if (itemId == R.id.navigation_update_task) {
                    openFragment(UpdateTaskFragment.class);
                    return true;
                }
                else if (itemId == R.id.navigation_delete_task) {
                    openFragment(DeleteTaskFragment.class);
                    return true;
                }
                else return false;
            }
        });
    }

    // Helper method to open fragments based on navigation selection
    private void openFragment(Class<? extends Fragment> fragmentClass) {
        try {
            Fragment fragment = fragmentClass.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, fragment)
                    .addToBackStack(null)
                    .commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
