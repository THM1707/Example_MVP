package com.nothing.example_mvp.main;

import com.nothing.example_mvp.data.model.Task;

import java.util.List;

public interface MainContract {
    interface View {
        void onAddSuccess(List<Task> taskList);
        void onAddFail();
        void onDeleteSuccess(List<Task> taskList);
        void onDeleteFail();
        void onFinishSuccess();
        void onFinishFail();
        void onUpdateSuccess();
        void onUpdateFail();
        void onListExist(List<Task> tasks);
        void onListEmpty();
        void onGetSuccess();
        void onGetFail();
    }

    interface Presenter {
        void addTask(List<Task> taskList, String taskName, String taskMsg);
        void deleteTask(List<Task> taskList, int id);
        void finishTask(String taskName);
        void updateTask(int id, String taskName, String taskMsg);
        void getTask(int id);
        void getTasks();
    }
}
