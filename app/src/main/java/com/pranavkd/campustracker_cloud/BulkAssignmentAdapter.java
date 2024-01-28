package com.pranavkd.campustracker_cloud;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pranavkd.campustracker_cloud.data.PerfomanceStudents;
import com.pranavkd.campustracker_cloud.interfaces.Onmarkchanged;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BulkAssignmentAdapter extends RecyclerView.Adapter<BulkAssignmentAdapter.ViewHolder> {

    private List<PerfomanceStudents> studentsList;
    private List<Integer> marksList;
    private Onmarkchanged onmarkchanged;

    public BulkAssignmentAdapter(List<PerfomanceStudents> studentsList, Onmarkchanged onmarkchanged) {
        this.studentsList = studentsList;
        this.onmarkchanged = onmarkchanged;
        this.marksList = new ArrayList<>(Collections.nCopies(studentsList.size(), 0)); // Initialize the ArrayList
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_bulk_assignemts, parent, false);
        return new ViewHolder(view, onmarkchanged);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PerfomanceStudents student = studentsList.get(position);
        holder.tvId.setText(String.valueOf(student.getStudentId()));
        holder.tvName.setText(student.getStudentName());
        holder.tvPosition.setText(String.valueOf(position+1));
        holder.etMark.setText(String.valueOf(marksList.get(position)));
        holder.saveButton.setText("Save");
        holder.saveButton.setBackgroundColor("#FFFFFF".hashCode()); // Reset the state of the save button
    }

    private void submitMarks(int position, int i, Button saveButton) {
        int mark = i;
        marksList.set(position, mark); // Update the mark at the specific position
        onmarkchanged.onMarkChanged(position, mark);
        saveButton.setText("Saved");
        saveButton.setBackgroundColor("#00ff00".hashCode());
    }

    @Override
    public int getItemCount() {
        return studentsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvId, tvName, tvPosition;
        EditText etMark;
        Button saveButton;

        public ViewHolder(View itemView, Onmarkchanged onmarkchanged) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tvId);
            tvName = itemView.findViewById(R.id.tvName);
            etMark = itemView.findViewById(R.id.etMark);
            tvPosition = itemView.findViewById(R.id.tvPosition);
            saveButton = itemView.findViewById(R.id.btnSave);
            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    submitMarks(getAdapterPosition(), Integer.parseInt(etMark.getText().toString()), saveButton);
                }
            });

            etMark.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        submitMarks(getAdapterPosition(), Integer.parseInt(etMark.getText().toString()), saveButton);
                    }
                }
            });
        }
    }
}