package com.pranavkd.campustracker_cloud;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pranavkd.campustracker_cloud.data.Subject;
import com.pranavkd.campustracker_cloud.interfaces.OnDeleteClickListener;
import com.pranavkd.campustracker_cloud.interfaces.OnSubClickListener;

import java.util.List;


public class SubjectsAdapter extends RecyclerView.Adapter<SubjectsAdapter.SubjectViewHolder> {

    private List<Subject> subjects;
    private OnDeleteClickListener onDeleteClickListener;
    private OnSubClickListener onSubClickListener;

    public SubjectsAdapter(List<Subject> subjects, OnDeleteClickListener onDeleteClickListener, OnSubClickListener onSubClickListener) {
        this.subjects = subjects;
        this.onDeleteClickListener = onDeleteClickListener;
        this.onSubClickListener = onSubClickListener;
    }

    @NonNull
    @Override
    public SubjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.subject_item, parent, false);
        return new SubjectViewHolder(view,onDeleteClickListener,subjects,onSubClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectViewHolder holder, int position) {
        Subject subject = subjects.get(position);
        holder.bind(subject);
    }

    @Override
    public int getItemCount() {
        return subjects.size();
    }

    static class SubjectViewHolder extends RecyclerView.ViewHolder {
        TextView subjectName;
        TextView subjectId;
        ImageView deleteButton;

        public SubjectViewHolder(@NonNull View itemView,OnDeleteClickListener onDeleteClickListener,List<Subject> subjects,OnSubClickListener onSubClickListener) {
            super(itemView);
            subjectName = itemView.findViewById(R.id.class_name_text_view);
            subjectId = itemView.findViewById(R.id.class_id_text_view);
            deleteButton = itemView.findViewById(R.id.delete_sub);
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        onDeleteClickListener.onDeleteClick(subjects.get(position).getSubjectId());
                    }
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(position!=RecyclerView.NO_POSITION)
                    {
                        onSubClickListener.onSubClick(subjects.get(position).getSubjectId(),subjects.get(position).getSubjectName());
                    }
                }
            });
        }

        public void bind(Subject subject) {
            subjectName.setText(subject.getSubjectName());
            subjectId.setText(String.valueOf(subject.getSubjectId()));
        }

    }
}
