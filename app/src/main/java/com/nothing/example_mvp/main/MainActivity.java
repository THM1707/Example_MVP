package com.nothing.example_mvp.main;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.nothing.example_mvp.R;
import com.nothing.example_mvp.data.model.Task;
import com.nothing.example_mvp.data.source.TaskDataSource;
import com.nothing.example_mvp.data.source.TaskRepository;
import com.nothing.example_mvp.data.source.local.TaskLocalDataSource;
import com.nothing.example_mvp.data.source.remote.TaskRemoteDataSource;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainContract.View,
    View.OnClickListener {
    private MainContract.Presenter mPresenter;
    private RecyclerView mRecyclerTask;
    private TaskAdapter mTaskAdapter;
    private List<Task> mTaskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPresenter = new MainPresenter(new TaskRepository(new TaskLocalDataSource(this),
            new TaskRemoteDataSource()), this);
        mTaskList = new ArrayList<>();
        mPresenter.getTasks();
        mRecyclerTask = (RecyclerView) findViewById(R.id.recycler_task);
        mRecyclerTask.setLayoutManager(new LinearLayoutManager(this));
        mTaskAdapter = new TaskAdapter(mTaskList, mPresenter);
        mRecyclerTask.setAdapter(mTaskAdapter);
        findViewById(R.id.button_add).setOnClickListener(this);
    }

    @Override
    public void onAddSuccess(List<Task> taskList) {
        mTaskList = taskList;
        mTaskAdapter.replaceData(taskList);
    }

    @Override
    public void onAddFail() {
        Toast.makeText(this, R.string.msg_cant_add, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteSuccess(List<Task> taskList) {
        mTaskList = taskList;
        mTaskAdapter.replaceData(taskList);
    }

    @Override
    public void onDeleteFail() {
        Toast.makeText(this, R.string.msg_cant_delete, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFinishSuccess() {
        // TODO: 7/9/2017
    }

    @Override
    public void onFinishFail() {
        // TODO: 7/9/2017
    }

    @Override
    public void onUpdateSuccess() {
        // TODO: 7/9/2017
    }

    @Override
    public void onUpdateFail() {
        // TODO: 7/9/2017
    }

    @Override
    public void onListExist(List<Task> taskList) {
        mTaskList = taskList;
        mTaskAdapter.replaceData(taskList);
    }

    @Override
    public void onListEmpty() {
    }

    @Override
    public void onGetSuccess() {
        // TODO: 7/9/2017
    }

    @Override
    public void onGetFail() {
        // TODO: 7/9/2017
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_add:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_edit, null);
                final EditText editTitle = dialogView.findViewById(R.id.edit_title);
                final EditText editMessage = dialogView.findViewById(R.id.edit_message);
                builder.setView(dialogView)
                    .setTitle("Add Task")
                    .setPositiveButton(R.string.action_add, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            mPresenter.addTask(mTaskList, editTitle.getText().toString(),
                                editMessage.getText().toString());
                        }
                    }).setNegativeButton(R.string.action_cancel, null);
                builder.create().show();
                break;
            default:
                break;
        }
    }
}
