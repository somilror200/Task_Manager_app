package com.example.taskmanagerapp;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class TaskDAO {

    private SQLiteDatabase database;
    private TaskDBHelper dbHelper;

    public TaskDAO(Context context) {
        dbHelper = new TaskDBHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    // Add a new task
    public long addTask(Task task) {
        ContentValues values = new ContentValues();
        values.put("title", task.getTitle());
        values.put("description", task.getDescription());
        values.put("due_date", task.getDueDate());
        return database.insert("tasks", null, values);
    }

    // Update an existing task
    public int updateTask(Task task) {
        ContentValues values = new ContentValues();
        values.put("title", task.getTitle());
        values.put("description", task.getDescription());
        values.put("due_date", task.getDueDate());
        return database.update("tasks", values, "_id = ?", new String[]{String.valueOf(task.getId())});
    }

    // Delete a task
    public void deleteTask(long taskId) {
        database.delete("tasks", "_id = ?", new String[]{String.valueOf(taskId)});
    }

    // Get all tasks
    public List<Task> getAllTasks() {
        List<Task> tasks = new ArrayList<>();
        Cursor cursor = database.query("tasks", null, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Task task = cursorToTask(cursor);
            tasks.add(task);
            cursor.moveToNext();
        }
        cursor.close();
        return tasks;
    }

    // Helper method to convert cursor to Task object
    private Task cursorToTask(Cursor cursor) {
        long id = cursor.getLong(cursor.getColumnIndex("_id"));
        String title = cursor.getString(cursor.getColumnIndex("title"));
        String description = cursor.getString(cursor.getColumnIndex("description"));
        String dueDate = cursor.getString(cursor.getColumnIndex("due_date"));
        return new Task(id, title, description, dueDate);
    }

    public Task getTask(long taskId) {
        Cursor cursor = database.query("tasks", null, "_id = ?", new String[]{String.valueOf(taskId)}, null, null, null);
        Task task = null;
        if (cursor != null && cursor.moveToFirst()) {
            task = cursorToTask(cursor);
            cursor.close();
        }
        return task;
    }


}
