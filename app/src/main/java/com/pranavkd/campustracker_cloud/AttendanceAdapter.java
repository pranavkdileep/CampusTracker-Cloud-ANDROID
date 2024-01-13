package com.pranavkd.campustracker_cloud;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pranavkd.campustracker_cloud.data.studentAttendance;
import com.pranavkd.campustracker_cloud.interfaces.onEditAtt;

import java.util.ArrayList;
import java.util.List;

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.ViewHolder> {
    List<studentAttendance> studentAttendanceList;
    onEditAtt onEditAtt;

    public AttendanceAdapter(List<studentAttendance> studentAttendanceList,onEditAtt onEditAtt) {
        if (studentAttendanceList != null) {
            this.studentAttendanceList = studentAttendanceList;
        } else {
            this.studentAttendanceList = new ArrayList<>();
        }
        this.onEditAtt = onEditAtt;
    }

    @NonNull
    @Override
    public AttendanceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_attendace, parent, false);
        return new ViewHolder(view,onEditAtt,studentAttendanceList);
    }

    @Override
    public void onBindViewHolder(@NonNull AttendanceAdapter.ViewHolder holder, int position) {
        holder.studentId.setText(String.valueOf(studentAttendanceList.get(position).getStudentId()));
        holder.studentName.setText(studentAttendanceList.get(position).getStudentName());
        holder.attendenceId.setText(String.valueOf(studentAttendanceList.get(position).getAttandanceId()));
        holder.date.setText(studentAttendanceList.get(position).getDate());
        holder.IsPresent.setText(String.valueOf(studentAttendanceList.get(position).getPresent()));

    }

     

    @Override
    public int getItemCount() {
        return studentAttendanceList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView studentId, studentName, attendenceId,date,IsPresent;
        ImageView Edit;

        public ViewHolder(@NonNull View itemView,onEditAtt onEditAtt,List<studentAttendance> studentAttendanceList) {
            super(itemView);
            studentId = itemView.findViewById(R.id.tvId);
            studentName = itemView.findViewById(R.id.tvName);
            attendenceId = itemView.findViewById(R.id.tvAttendanceId);
            date = itemView.findViewById(R.id.tvDate);
            IsPresent = itemView.findViewById(R.id.tvIsPresent);
            Edit = itemView.findViewById(R.id.EditAttendance);
            Edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(position!=RecyclerView.NO_POSITION) {
                        onEditAtt.onEdit(studentAttendanceList.get(position).getAttandanceId());
                    }
                }
            });
        }
    }

}
