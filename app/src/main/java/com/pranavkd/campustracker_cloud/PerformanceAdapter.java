package com.pranavkd.campustracker_cloud;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pranavkd.campustracker_cloud.data.PerfomanceStudents;

import java.util.List;

public class PerformanceAdapter extends RecyclerView.Adapter<PerformanceAdapter.PerformanceViewHolder> {
    private List<PerfomanceStudents> perfomanceStudents;
    public PerformanceAdapter(List<PerfomanceStudents> perfomanceStudents)
    {
        this.perfomanceStudents=perfomanceStudents;
    }
    @NonNull
    @Override
    public PerformanceAdapter.PerformanceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.studentperfomance, parent, false);
        return new PerformanceAdapter.PerformanceViewHolder((ViewGroup) view);
    }

    @Override
    public void onBindViewHolder(@NonNull PerformanceAdapter.PerformanceViewHolder holder, int position) {
        perfomanceStudents.get(position);
        holder.bind(perfomanceStudents.get(position));
    }

    @Override
    public int getItemCount() {
        return perfomanceStudents != null ? perfomanceStudents.size() : 0;
    }
    class PerformanceViewHolder extends RecyclerView.ViewHolder{
        TextView rollno;
        TextView name;
        TextView TotalLectures;
        TextView present;
        TextView percentage;
        TextView internal;

        public PerformanceViewHolder(@NonNull ViewGroup parent) {
            super(parent);
            rollno=parent.findViewById(R.id.tvRollNo);
            name=parent.findViewById(R.id.tvName);
            TotalLectures=parent.findViewById(R.id.tvTotalLectures);
            present=parent.findViewById(R.id.tvPresent);
            percentage=parent.findViewById(R.id.tvAttendance);
            internal=parent.findViewById(R.id.tvInternalMark);

        }
        public void bind(PerfomanceStudents perfomanceStudents)
        {
            rollno.setText(String.valueOf(perfomanceStudents.getStudentId()));
            name.setText(perfomanceStudents.getStudentName());
            TotalLectures.setText(String.valueOf(perfomanceStudents.getTotalLectures()));
            present.setText(String.valueOf(perfomanceStudents.getLecturesPresent()));
            percentage.setText(String.valueOf(perfomanceStudents.getAttendancePercentage()));
            internal.setText(String.valueOf(perfomanceStudents.getAverageInternalMarks()));
        }
    }
}
