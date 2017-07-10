package com.nothing.example_mvp.data.source;

import com.nothing.example_mvp.data.model.Task;

import java.util.List;

public interface TaskDataSource {
    void addTask(Task task, Callback<Integer> callback);
    void deleteTask(int id, Callback<Integer> callback);
    void updateTask(int id, String title, String message, Callback<Integer> callback);
    void getTaskById(int id, Callback<Task> callback);
    void getTasks(Callbacks<Task> getTasksCallback);
    void finishTask(int id, boolean isFinish, Callback<Integer> callback);
    interface Callback<T> {
        void onSuccess(T data);
        void onFail();
    }

    interface Callbacks<T> {
        void onExistedList(List<T> data);
        void onEmptyList();
    }
}
