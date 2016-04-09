package com.example.rodri.todo.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.rodri.todo.category.Category;
import com.example.rodri.todo.task.Task;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rodri on 4/9/2016.
 */
public class CategoryTaskDataSource {

    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] taskColumns = { MySQLiteHelper.KEY_ID,
                                        MySQLiteHelper.COLUMN_TASK_NAME,
                                        MySQLiteHelper.COLUMN_PRIORITY,
                                        MySQLiteHelper.COLUMN_DUE_DATE };

    private String[] categoryColumns = { MySQLiteHelper.KEY_ID,
                                            MySQLiteHelper.COLUMN_CATEGORY_NAME };

    private String[] categoryTaskColumns = { MySQLiteHelper.KEY_ID,
                                                MySQLiteHelper.COLUMN_TASK_ID,
                                                MySQLiteHelper.COLUMN_CATEGORY_ID };

    public CategoryTaskDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Category createCategory(String categoryName) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_CATEGORY_NAME, categoryName);

        long insertId = database.insert(MySQLiteHelper.TABLE_CATEGORY, null, values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_CATEGORY, categoryColumns,
                MySQLiteHelper.KEY_ID + " = " + insertId,
                null, null, null, null);
        cursor.moveToFirst();
        Category newCategory = cursorToCategory(cursor);
        cursor.close();

        return newCategory;
    }

    public Category cursorToCategory(Cursor cursor) {
        Category category = new Category();
        category.setId(cursor.getLong(0));
        category.setCategoryName(cursor.getString(1));
        return category;
    }

    public Task createTask(String taskName, int priority, String dueDate, long categoryId) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_TASK_NAME, taskName);
        values.put(MySQLiteHelper.COLUMN_PRIORITY, priority);
        values.put(MySQLiteHelper.COLUMN_DUE_DATE, dueDate);

        long insertId = database.insert(MySQLiteHelper.TABLE_TASK, null, values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_TASK, taskColumns,
                MySQLiteHelper.KEY_ID + " = " + insertId,
                null, null, null, null);
        cursor.moveToFirst();
        Task newTask = cursorToTask(cursor);
        cursor.close();

        createCategoryTask(insertId, categoryId);


        return newTask;
    }

    public Task cursorToTask(Cursor cursor) {
        Task task = new Task();
        task.setId(cursor.getLong(0));
        task.setTaskName(cursor.getString(1));
        task.setPriority(cursor.getInt(2));
        task.setDueDate(cursor.getString(3));
        return task;
    }

    public void createCategoryTask(long taskId, long categoryId) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_TASK_ID, taskId);
        values.put(MySQLiteHelper.COLUMN_CATEGORY_ID, categoryId);

        long insertId = database.insert(MySQLiteHelper.TABLE_CATEGORY_TASK, null, values);
        /** database.query(MySQLiteHelper.TABLE_CATEGORY_TASK, categoryTaskColumns,
                                        MySQLiteHelper.KEY_ID + " = " + insertId,
                                        null, null, null, null); */

    }

    public List<Task> getAllTasks() {
        List<Task> tasks = new ArrayList<>();
        Cursor cursor = database.query(MySQLiteHelper.TABLE_TASK, taskColumns, null, null, null, null, null);
        cursor.moveToFirst();

        while(!cursor.isAfterLast()) {
            Task task = cursorToTask(cursor);
            tasks.add(task);
            cursor.moveToNext();
        }

        cursor.close();

        return tasks;
    }

    public List<Task> getTasksByCategory(long categoryId) {
        List<Task> tasks = new ArrayList<>();
        Cursor cursor = database.query(MySQLiteHelper.TABLE_CATEGORY_TASK, categoryTaskColumns,
                                        MySQLiteHelper.KEY_ID + " = " + categoryId, null, null, null, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Task task = cursorToTask(cursor);
            tasks.add(task);
            cursor.moveToNext();
        }

        cursor.close();

        return tasks;
    }

    public void deleteTask(Task task) {
        long id = task.getId();
        System.out.println("The task with id " + id + " was removed.");
        database.delete(MySQLiteHelper.TABLE_TASK, MySQLiteHelper.KEY_ID + " = " + id, null);
        database.delete(MySQLiteHelper.TABLE_CATEGORY_TASK, MySQLiteHelper.COLUMN_TASK_ID + " = " + id, null);
    }

    public void deleteCategory(Category category) {
        long id = category.getId();
        System.out.println("The category with id " + id + " and all its Tasks were removed.");
        List<Task> tasks = getTasksByCategory(id);
        for (Task task : tasks) {
            deleteTask(task);
        }
        database.delete(MySQLiteHelper.TABLE_CATEGORY, MySQLiteHelper.KEY_ID + " = " + id, null);
    }


}
