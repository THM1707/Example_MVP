package com.nothing.example_mvp.data.source.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nothing.example_mvp.data.model.Task;
import com.nothing.example_mvp.data.source.TaskDataSource;

import java.util.ArrayList;
import java.util.List;

public class TaskLocalDataSource extends TaskDbHelper implements TaskDataSource {
    public TaskLocalDataSource(Context context) {
        super(context);
    }

    @Override
    public void addTask(Task task, Callback<Integer> callback) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TaskContract.TaskEntry.COLUMN_NAME_TITLE, task.getName());
        values.put(TaskContract.TaskEntry.COLUMN_NAME_DESCRIPTION, task.getMessage());
        long result = db.insert(TaskContract.TaskEntry.TABLE_NAME, null, values);
        if (result != -1) {
            callback.onSuccess((int) result);
        } else
            callback.onFail();
        db.close();
    }

    @Override
    public void deleteTask(int id, Callback<Integer> callback) {
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = TaskContract.TaskEntry._ID + "=?";
        String[] whereClauseArgs = {String.valueOf(id)};
        long result = db.delete(TaskContract.TaskEntry.TABLE_NAME, whereClause, whereClauseArgs);
        db.close();
        if (result > 0) {
            callback.onSuccess((int) result);
        } else callback.onFail();
    }

    @Override
    public void updateTask(int id, String title, String message, Callback<Integer> callback) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TaskContract.TaskEntry.COLUMN_NAME_TITLE, title);
        values.put(TaskContract.TaskEntry.COLUMN_NAME_DESCRIPTION, message);
        String whereClause = TaskContract.TaskEntry._ID + "=?";
        String[] whereClauseArgs = {String.valueOf(id)};
        int result = db.update(TaskContract.TaskEntry.TABLE_NAME, values, whereClause,
            whereClauseArgs);
        db.close();
        if (result > 0) {
            callback.onSuccess(result);
        } else callback.onFail();
    }

    @Override
    public void getTaskById(int id, Callback<Task> callback) {
        SQLiteDatabase db = getWritableDatabase();
        String[] columns = {
            TaskContract.TaskEntry._ID,
            TaskContract.TaskEntry.COLUMN_NAME_TITLE,
            TaskContract.TaskEntry.COLUMN_NAME_DESCRIPTION
        };
        String selection = TaskContract.TaskEntry._ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};
        Cursor c =
            db.query(TaskContract.TaskEntry.TABLE_NAME, columns, selection, selectionArgs, null,
                null, null);
        Task task = null;
        if (c != null && c.getCount() > 0) {
            c.moveToFirst();
            int taskId = c.getInt(c.getColumnIndex(TaskContract.TaskEntry._ID));
            String title =
                c.getString(c.getColumnIndex(TaskContract.TaskEntry.COLUMN_NAME_TITLE));
            String description =
                c.getString(c.getColumnIndex(TaskContract.TaskEntry.COLUMN_NAME_DESCRIPTION));
            task = new Task(taskId, title, description);
        }
        if (c != null) {
            c.close();
        }
        db.close();
        if (task != null) {
            callback.onSuccess(task);
        } else {
            callback.onFail();
        }
    }

    @Override
    public void getTasks(Callbacks<Task> callback) {
        List<Task> tasks = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        String[] columns = {
            TaskContract.TaskEntry._ID,
            TaskContract.TaskEntry.COLUMN_NAME_TITLE,
            TaskContract.TaskEntry.COLUMN_NAME_DESCRIPTION
        };
        Cursor c =
            db.query(TaskContract.TaskEntry.TABLE_NAME, columns, null, null, null, null, null);
        if (c != null && c.getCount() > 0) {
            c.moveToFirst();
            int taskId = c.getInt(c.getColumnIndex(TaskContract.TaskEntry._ID));
            String title =
                c.getString(c.getColumnIndex(TaskContract.TaskEntry.COLUMN_NAME_TITLE));
            String description =
                c.getString(c.getColumnIndex(TaskContract.TaskEntry.COLUMN_NAME_DESCRIPTION));
            Task task = new Task(taskId, title, description);
            tasks.add(task);
        }
        if (c != null) {
            c.close();
        }
        db.close();
        if (tasks.isEmpty()) {
            callback.onEmptyList();
        } else {
            callback.onExistedList(tasks);
        }
    }
}
