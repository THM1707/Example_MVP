package com.nothing.example_mvp.main;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.nothing.example_mvp.R;
import com.nothing.example_mvp.data.model.Task;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {
    private List<Task> mTaskList;
    private MainContract.Presenter mPresenter;
    private RecyclerView mRecyclerView;

    public TaskAdapter(List<Task> taskList, MainContract.Presenter presenter,
                       RecyclerView recyclerView) {
        mPresenter = presenter;
        mTaskList = taskList;
        mRecyclerView = recyclerView;
    }

    public void setTaskList(List<Task> taskList) {
        mTaskList = taskList;
    }

    public void replaceData(List<Task> newData) {
        setTaskList(newData);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent,
            false);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int position = mRecyclerView.getChildAdapterPosition(view);
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                View dialogView =
                    LayoutInflater.from(view.getContext()).inflate(R.layout.dialog_add_edit, null);
                final EditText editTitle = dialogView.findViewById(R.id.edit_title);
                final EditText editMessage = dialogView.findViewById(R.id.edit_message);
                editTitle.setText(mTaskList.get(position).getName());
                editMessage.setText(mTaskList.get(position).getMessage());
                builder.setView(dialogView)
                    .setTitle("Update Task")
                    .setPositiveButton(R.string.action_update, new DialogInterface.OnClickListener
                        () {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            mPresenter
                                .updateTask(mTaskList, mTaskList.get(position).getId(), editTitle
                                    .getText()
                                    .toString(), editMessage.getText().toString());
                        }
                    }).setNegativeButton(R.string.action_cancel, null);
                builder.create().show();
            }
        });
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindData(mTaskList.get(position));
    }

    @Override
    public int getItemCount() {
        return mTaskList.isEmpty() ? 0 : mTaskList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private CheckBox mCheckFinish;
        private TextView mTextTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            mCheckFinish = itemView.findViewById(R.id.check_finish);
            mTextTitle = itemView.findViewById(R.id.text_title);
            itemView.findViewById(R.id.button_delete).setOnClickListener(this);
            mCheckFinish.setOnClickListener(this);
        }

        public void bindData(Task task) {
            if (task != null) {
                mTextTitle.setText(task.getName());
                mCheckFinish.setChecked(task.isFinish());
            }
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.button_delete:
                    mPresenter.deleteTask(mTaskList, mTaskList.get(getAdapterPosition()).getId());
                    break;
                case R.id.check_finish:
                    mPresenter.finishTask(mTaskList, mTaskList.get(getAdapterPosition()).getId(),
                        mCheckFinish.isChecked());
                    break;
                default:
                    break;
            }
        }
    }
}
