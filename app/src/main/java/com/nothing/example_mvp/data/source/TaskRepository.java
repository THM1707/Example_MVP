package com.nothing.example_mvp.data.source;

import com.nothing.example_mvp.data.model.Task;

import java.util.List;

/**
 * Created by THM on 7/7/2017.
 */
public class TaskRepository implements TaskDataSource {
    private TaskDataSource mTaskLocalDataSource;
    private TaskDataSource mTaskRemoteDataSource;

    public TaskRepository(TaskDataSource taskLocalDataSource,
                          TaskDataSource taskRemoteDataSource) {
        mTaskLocalDataSource = taskLocalDataSource;
        mTaskRemoteDataSource = taskRemoteDataSource;
    }

    @Override
    public void addTask(Task task, TaskDataSource.Callback<Integer> callback) {
        mTaskLocalDataSource.addTask(task, callback);
        mTaskRemoteDataSource.addTask(task, callback);
    }

    @Override
    public void deleteTask(int id, TaskDataSource.Callback<Integer> callback) {
        mTaskRemoteDataSource.deleteTask(id, callback);
        mTaskLocalDataSource.deleteTask(id, callback);
    }

    @Override
    public void updateTask(int id, String title, String message, TaskDataSource.Callback<Integer>
        callback) {
        mTaskLocalDataSource.updateTask(id, title, message, callback);
        mTaskRemoteDataSource.updateTask(id, title, message, callback);
    }

    @Override
    public void getTaskById(final int id, final Callback<Task> callback) {
        mTaskLocalDataSource.getTaskById(id, new Callback<Task>() {
            @Override
            public void onSuccess(Task data) {
                callback.onSuccess(data);
            }

            @Override
            public void onFail() {
                mTaskRemoteDataSource.getTaskById(id, callback);
            }
        });
    }

    @Override
    public void getTasks(final Callbacks<Task> callbacks) {
        mTaskLocalDataSource.getTasks(new Callbacks<Task>() {
            @Override
            public void onExistedList(List<Task> data) {
                callbacks.onExistedList(data);
            }

            @Override
            public void onEmptyList() {
                mTaskRemoteDataSource.getTasks(callbacks);
            }
        });
    }
}
