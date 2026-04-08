package com.priyansh.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

public class TaskAdapter extends BaseAdapter {

    private Context context;
    private List<Task> tasks;
    private OnTaskActionListener actionListener;

    public interface OnTaskActionListener {
        void onEdit(Task task);
        void onDelete(Task task);
    }

    public TaskAdapter(Context context, List<Task> tasks, OnTaskActionListener actionListener) {
        this.context = context;
        this.tasks = tasks;
        this.actionListener = actionListener;
    }

    @Override
    public int getCount() {
        return tasks.size();
    }

    @Override
    public Object getItem(int position) {
        return tasks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return tasks.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_task, parent, false);
        }

        Task task = (Task) getItem(position);

        TextView tvTaskName = convertView.findViewById(R.id.tvTaskName);
        TextView tvTaskDetails = convertView.findViewById(R.id.tvTaskDetails);
        ImageButton btnEdit = convertView.findViewById(R.id.btnEdit);
        ImageButton btnDelete = convertView.findViewById(R.id.btnDelete);

        tvTaskName.setText(task.getName());
        tvTaskDetails.setText("Due: " + task.getDueDate() + " | Priority: " + task.getPriority());

        btnEdit.setOnClickListener(v -> actionListener.onEdit(task));
        btnDelete.setOnClickListener(v -> actionListener.onDelete(task));

        return convertView;
    }

    public void updateTasks(List<Task> newTasks) {
        this.tasks = newTasks;
        notifyDataSetChanged();
    }
}
