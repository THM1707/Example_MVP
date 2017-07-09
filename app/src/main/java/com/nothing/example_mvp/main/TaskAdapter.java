package com.nothing.example_mvp.main;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.nothing.example_mvp.R;
import com.nothing.example_mvp.data.model.Task;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {
    private List<Task> mTaskList;
    private MainContract.Presenter mPresenter;

    public TaskAdapter(List<Task> taskList, MainContract.Presenter presenter) {
        mPresenter = presenter;
        mTaskList = taskList;
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
        }

        public void bindData(Task task) {
            if (task != null) {
                mTextTitle.setText(task.getName());
            }
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.button_delete:
                    mPresenter.deleteTask(mTaskList, mTaskList.get(getAdapterPosition()).getId());
                    break;
                default:
                    break;
            }
        }
    }
}
