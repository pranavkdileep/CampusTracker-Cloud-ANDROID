package com.pranavkd.campustracker_cloud;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pranavkd.campustracker_cloud.data.AssignmentsDateList;
import com.pranavkd.campustracker_cloud.interfaces.onEditAtt;

import java.util.ArrayList;
import java.util.List;

public class AssignmentAdapter extends RecyclerView.Adapter<AssignmentAdapter.ViewHolder> {
    List<AssignmentsDateList> assignmentsDateList;
    onEditAtt onEditAssignment;

    public AssignmentAdapter(List<AssignmentsDateList> assignmentsDateList, onEditAtt onEditAssignment) {
        if (assignmentsDateList != null) {
            this.assignmentsDateList = assignmentsDateList;
        } else {
            this.assignmentsDateList = new ArrayList<>();
        }
        this.onEditAssignment = onEditAssignment;
    }

    @NonNull
    @Override
    public AssignmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_assignments, parent, false);
        return new ViewHolder(view, onEditAssignment, assignmentsDateList);
    }

    @Override
    public void onBindViewHolder(@NonNull AssignmentAdapter.ViewHolder holder, int position) {
holder.studentid.setText(String.valueOf(assignmentsDateList.get(position).getStudent_id()));
holder.studentName.setText(assignmentsDateList.get(position).getStudent_name());
holder.assignmentId.setText(String.valueOf(assignmentsDateList.get(position).getAssignment_id()));
holder.marksObtained.setText(String.valueOf(assignmentsDateList.get(position).getMarks_obtained()));
holder.maxMarks.setText(String.valueOf(assignmentsDateList.get(position).getMax_marks()));
    }

    @Override
    public int getItemCount() {
        return assignmentsDateList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView studentid, studentName, assignmentId, marksObtained, maxMarks;
        ImageView Edit;

        public ViewHolder(@NonNull View itemView, onEditAtt onEditAssignment, List<AssignmentsDateList> assignmentsDateList) {
            super(itemView);
            studentid = itemView.findViewById(R.id.tvId);
            studentName = itemView.findViewById(R.id.tvName);
            assignmentId = itemView.findViewById(R.id.aissignmentsid);
            marksObtained = itemView.findViewById(R.id.marks_obtained);
            maxMarks = itemView.findViewById(R.id.max_marks);
            Edit = itemView.findViewById(R.id.EditAssignment);
            Edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(position!=RecyclerView.NO_POSITION) {
                        onEditAssignment.onEdit(assignmentsDateList.get(position).getAssignment_id());
                    }
                }
            });
        }
    }
}