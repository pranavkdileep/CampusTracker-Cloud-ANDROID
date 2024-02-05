package com.pranavkd.campustracker_cloud;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pranavkd.campustracker_cloud.data.faculti;
import com.pranavkd.campustracker_cloud.interfaces.UpdateFacultie;

import java.util.List;

public class FacultiAdapter extends RecyclerView.Adapter<FacultiAdapter.FacultiViewHolder>{

    private List<faculti> faculties;
    private Context context;
    private UpdateFacultie updateFacultie;

    public FacultiAdapter(List<faculti> faculties, Context context, UpdateFacultie updateFacultie) {
        this.faculties = faculties;
        this.context = context;
        this.updateFacultie = updateFacultie;
    }

    @NonNull
    @Override
    public FacultiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.faculti_list, parent, false);
        return new FacultiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FacultiViewHolder holder, int position) {
        faculti faculty = faculties.get(position);
        holder.facultyId.setText(String.valueOf(faculty.getFaculti_id()));
        holder.facultyName.setText(faculty.getName());
    }

    @Override
    public int getItemCount() {
        return faculties.size();
    }

    public class FacultiViewHolder extends RecyclerView.ViewHolder {
        TextView facultyId;
        TextView facultyName;
        TextView facultyPassword;
        Button deleteButton;
        Button updateButton;

        public FacultiViewHolder(@NonNull View itemView) {
            super(itemView);
            facultyId = itemView.findViewById(R.id.faculty_id);
            facultyName = itemView.findViewById(R.id.faculty_name);
            facultyPassword = itemView.findViewById(R.id.faculty_password);
            deleteButton = itemView.findViewById(R.id.delete_button);
            updateButton = itemView.findViewById(R.id.update_button);
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateFacultie.onDeleteFacultie(faculties.get(getAdapterPosition()).getFaculti_id());
                }
            });
            updateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateFacultie.onUpdateFacultie(faculties.get(getAdapterPosition()).getFaculti_id(),facultyPassword.getText().toString());
                }
            });
        }
    }
}