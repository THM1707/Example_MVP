package com.nothing.example_mvp.data.source.remote;

import com.nothing.example_mvp.data.model.Task;
import com.nothing.example_mvp.data.source.TaskDataSource;

/**
 * Created by THM on 7/7/2017.
 */
public class TaskRemoteDataSource implements TaskDataSource {
    @Override
    public void addTask(Task task, Callback<Integer> callback) {
    }

    @Override
    public void deleteTask(int id, Callback<Integer> callback) {
    }

    @Override
    public void updateTask(int id, String title, String message,
                           Callback<Integer> callback) {
    }

    @Override
    public void getTaskById(int id, Callback<Task> callback) {
    }

    @Override
    public void getTasks(Callbacks<Task> callbacks) {
        callbacks.onEmptyList();
    }
}
