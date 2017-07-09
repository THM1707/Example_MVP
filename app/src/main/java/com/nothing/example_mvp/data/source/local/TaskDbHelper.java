package com.nothing.example_mvp.data.source.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by THM on 7/7/2017.
 */
public class TaskDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "tasks.db";
    private final static String COMMAND_CREATE_TASK_TABLE = "CREATE TABLE "
        + TaskContract.TaskEntry.TABLE_NAME
        + " ( "
        + TaskContract.TaskEntry._ID
        + " INTEGER PRIMARY KEY, "
        + TaskContract.TaskEntry.COLUMN_NAME_TITLE
        + " TEXT, "
        + TaskContract.TaskEntry.COLUMN_NAME_DESCRIPTION
        + " TEXT)";
    private static String COMMAND_DROP_TASK_TABLE = "DROP_TABLE " + TaskContract.TaskEntry
        .TABLE_NAME;

    public TaskDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(COMMAND_CREATE_TASK_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(COMMAND_CREATE_TASK_TABLE);
        sqLiteDatabase.execSQL(COMMAND_DROP_TASK_TABLE);
    }
}
