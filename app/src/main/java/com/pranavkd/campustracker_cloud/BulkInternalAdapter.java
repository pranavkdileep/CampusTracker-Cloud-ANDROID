package com.pranavkd.campustracker_cloud;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pranavkd.campustracker_cloud.data.PerfomanceStudents;
import com.pranavkd.campustracker_cloud.interfaces.Onmarkchanged;

import java.util.List;

public class BulkInternalAdapter extends RecyclerView.Adapter<BulkInternalAdapter.ViewHolder> {

    private List<PerfomanceStudents> studentsList;
    private Onmarkchanged onmarkchanged;

    public BulkInternalAdapter(List<PerfomanceStudents> studentsList, Onmarkchanged onmarkchanged) {
        this.studentsList = studentsList;
        this.onmarkchanged = onmarkchanged;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_internals_marking, parent, false);
        return new ViewHolder(view, onmarkchanged);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PerfomanceStudents student = studentsList.get(position);
        holder.tvId.setText(String.valueOf(student.getStudentId()));
        holder.tvName.setText(student.getStudentName());
        holder.tvPosition.setText(String.valueOf(position+1));
        holder.etMark.setText("0");
    }

    @Override
    public int getItemCount() {
        return studentsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvId, tvName, tvPosition;
        EditText etMark;

        public ViewHolder(View itemView, Onmarkchanged onmarkchanged) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tvId);
            tvName = itemView.findViewById(R.id.tvName);
            etMark = itemView.findViewById(R.id.etMark);
            tvPosition = itemView.findViewById(R.id.tvPosition);

            etMark.setOnFocusChangeListener((v, hasFocus) -> {
                if (!hasFocus) {
                    int mark = Integer.parseInt(etMark.getText().toString());
                    onmarkchanged.onMarkChanged(getAdapterPosition(), mark);
                }
            });
        }
    }
}