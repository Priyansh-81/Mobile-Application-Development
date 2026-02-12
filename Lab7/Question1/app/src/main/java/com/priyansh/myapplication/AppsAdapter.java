
package com.priyansh.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class AppsAdapter extends RecyclerView.Adapter<AppsAdapter.ViewHolder> {

    private final List<AppInfo> apps;
    private final OnAppLongClickListener longClickListener;

    public interface OnAppLongClickListener {
        void onAppLongClick(AppInfo appInfo);
    }

    public AppsAdapter(List<AppInfo> apps, OnAppLongClickListener longClickListener) {
        this.apps = apps;
        this.longClickListener = longClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.app_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AppInfo app = apps.get(position);
        holder.appNameTextView.setText(app.getName());
        holder.appIconImageView.setImageDrawable(app.getIcon());

        holder.itemView.setOnLongClickListener(v -> {
            if (longClickListener != null) {
                longClickListener.onAppLongClick(app);
            }
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return apps.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView appIconImageView;
        TextView appNameTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            appIconImageView = itemView.findViewById(R.id.appIconImageView);
            appNameTextView = itemView.findViewById(R.id.appNameTextView);
        }
    }
}
