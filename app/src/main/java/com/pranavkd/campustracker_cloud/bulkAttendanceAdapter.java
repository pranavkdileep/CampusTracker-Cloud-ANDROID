package com.pranavkd.campustracker_cloud;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pranavkd.campustracker_cloud.data.PerfomanceStudents;
import com.pranavkd.campustracker_cloud.interfaces.OnSwitchChangedListener;

import java.util.List;

public class bulkAttendanceAdapter extends RecyclerView.Adapter<bulkAttendanceAdapter.ViewHolder>{
    List<PerfomanceStudents> perfomanceStudentsList;
    OnSwitchChangedListener onSwitchChangedListener;

    public bulkAttendanceAdapter(List<PerfomanceStudents> perfomanceStudentsList, OnSwitchChangedListener onSwitchChangedListener) {
        this.perfomanceStudentsList = perfomanceStudentsList;
        this.onSwitchChangedListener = onSwitchChangedListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listforattendance, parent, false);
        return new ViewHolder(view, onSwitchChangedListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PerfomanceStudents student = perfomanceStudentsList.get(position);
        holder.tvId.setText(String.valueOf(student.getStudentId()));
        holder.tvName.setText(student.getStudentName());
        holder.tvPosition.setText(String.valueOf(position+1));
        holder.switch1.setChecked(true);
    }

    @Override
    public int getItemCount() {
        return perfomanceStudentsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvId, tvName, tvPosition;
        Switch switch1;

        public ViewHolder(@NonNull View itemView, OnSwitchChangedListener onSwitchChangedListener) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tvId);
            tvName = itemView.findViewById(R.id.tvName);
            tvPosition = itemView.findViewById(R.id.tvPosition);
            switch1 = itemView.findViewById(R.id.switch1);
            switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    onSwitchChangedListener.onSwitchChange(getAdapterPosition(), isChecked);
                }
            });
        }
    }
}