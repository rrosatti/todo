package com.example.rodri.todo.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by rodri on 4/9/2016.
 */
public class MySQLiteHelper extends SQLiteOpenHelper {

    // Database name
    public static final String DATABASE_NAME = "todoManager.db";

    // Database version
    public static final int DATABASE_VERSION = 5;

    // Table names
    public static final String TABLE_TASK = "tasks";
    public static final String TABLE_CATEGORY = "categories";
    public static final String TABLE_CATEGORY_TASK = "category_tasks";
    public static final String TABLE_ALARM = "alarms";

    // Common column name
    public static final String KEY_ID = "id";

    // Task column names
    public static final String COLUMN_TASK_NAME = "task_name";
    public static final String COLUMN_PRIORITY = "priority";
    public static final String COLUMN_DUE_DATE = "due_date";
    public static final String COLUMN_SET_ALARM = "set_alarm";

    // Category column name
    public static final String COLUMN_CATEGORY_NAME = "category_name";

    // CategoryTasks column names
    public static final String COLUMN_TASK_ID = "task_id";
    public static final String COLUMN_CATEGORY_ID = "category_id";

    // Alarm column names
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_ALARM_TIME = "alarm_time";

    // Alarm table create
    public static final String CREATE_TABLE_ALARM = "CREATE TABLE " + TABLE_ALARM + "("
                                                    + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                                                    + COLUMN_DATE + " INTEGER NOT NULL, "
                                                    + COLUMN_ALARM_TIME + " INTEGER NOT NULL, "
                                                    + COLUMN_TASK_ID + " INTEGER NOT NULL);";

    // Task table create
    public static final String CREATE_TABLE_TASK = "CREATE TABLE " + TABLE_TASK + "("
                                                   + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                                                   + COLUMN_TASK_NAME + " TEXT NOT NULL, "
                                                   + COLUMN_PRIORITY + " INTEGER NOT NULL, "
                                                   + COLUMN_DUE_DATE + " TEXT NOT NULL);";

    // Category table create
    public static final String CREATE_TABLE_CATEGORY = "CREATE TABLE " + TABLE_CATEGORY + "("
                                                        + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                                                        + COLUMN_CATEGORY_NAME + " TEXT NOT NULL);";

    // CategoryTasks table create
    public static final String CREATE_TABLE_CATEGORY_TASKS = "CREATE TABLE " + TABLE_CATEGORY_TASK + "("
                                                                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                                                                + COLUMN_TASK_ID + " INTEGER NOT NULL, "
                                                                + COLUMN_CATEGORY_ID + " INTEGER NOT NULL);";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(CREATE_TABLE_TASK);
        database.execSQL(CREATE_TABLE_CATEGORY);
        database.execSQL(CREATE_TABLE_CATEGORY_TASKS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(), "Upgrading database from version " + oldVersion + " to " + newVersion
                + ", which will destroy all old data.");
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASK);
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY);
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY_TASK);
        //onCreate(db);
        if (oldVersion == 2 && newVersion == 3) {
            db.execSQL("ALTER TABLE " + TABLE_TASK + " ADD COLUMN set_alarm INTEGER DEFAULT 0");
        }

        if (oldVersion == 3 && newVersion == 4) {
            db.execSQL(CREATE_TABLE_ALARM);
        }

        if (oldVersion == 4 && newVersion == 5) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_ALARM);
            db.execSQL(CREATE_TABLE_ALARM);
        }
        System.out.println("I've been here");
    }

}
