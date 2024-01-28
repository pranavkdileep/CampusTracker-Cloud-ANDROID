package com.pranavkd.campustracker_cloud;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pranavkd.campustracker_cloud.data.Subject;
import com.pranavkd.campustracker_cloud.interfaces.OnDeleteClickListener;
import com.pranavkd.campustracker_cloud.interfaces.OnSubClickListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Dialog dialog;
    private RecyclerView subjectsRecyclerView;
    private SubjectsAdapter adapter;
    Button logout_button;
    List<Subject> subjects;
    ProgressBar progressBar;

    private Apihelper apihelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progressBar);
        subjectsRecyclerView = findViewById(R.id.classes_recycler_view);
        subjectsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        apihelper = new Apihelper(this,this);
        apihelper.loadSubjects(this);
        subjectsRecyclerView.setAdapter(adapter);
        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(view -> {
            addSubjects();
        });
        logout_button = findViewById(R.id.logout_button);
        logout_button.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, Login.class);
            startActivity(intent);
        });
    }







    @SuppressLint("NotifyDataSetChanged")
    private void addSubjects() {
        dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.add_subject);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialogbackground));
        Button btn_add = dialog.findViewById(R.id.btn_add);
        //text input in dialog box
        EditText subjectname = dialog.findViewById(R.id.st_subname);
        Button btnclose = dialog.findViewById(R.id.closebtn);
        dialog.show();
        btn_add.setOnClickListener(view -> {
            String subjectNameText = subjectname.getText().toString();
            btn_add.setVisibility(View.GONE);
            if (subjectNameText.isEmpty()) {
                // Check if the subject name is empty
                Toast.makeText(MainActivity.this, "Please enter a subject name", Toast.LENGTH_SHORT).show();
            } else {
                progressBar.setVisibility(View.VISIBLE);
                apihelper.addSubject(subjectNameText,this);
                dialog.dismiss();
            }
        });
        btnclose.setOnClickListener(view -> {
            dialog.dismiss();
        });

    }

    public void updateSubjects(List<Subject> subjects) {
        this.subjects = subjects;
        progressBar.setVisibility(View.GONE);
        adapter = new SubjectsAdapter(subjects, new OnDeleteClickListener() {
            @Override
            public void onDeleteClick(int subjectId) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.VISIBLE);
                        apihelper.deleteSubject(subjectId, MainActivity.this);
                    }
                });
            }
        }, new OnSubClickListener() {
            @Override
            public void onSubClick(int subject_id, String subject_name) {
                String subjectId = String.valueOf(subject_id);
                Intent intent = new Intent(MainActivity.this, SubjectActivity.class);
                intent.putExtra("subjectId", subject_id);
                intent.putExtra("subjectName", subject_name);
                startActivity(intent);
            }
        });
        subjectsRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}