package com.pranavkd.campustracker_cloud;

import static androidx.appcompat.content.res.AppCompatResources.getDrawable;

import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.pranavkd.campustracker_cloud.apicaller.getPerfomance;
import com.pranavkd.campustracker_cloud.data.PerfomanceStudents;

import java.util.ArrayList;
import java.util.List;


public class StudentFragment extends Fragment {
    private int subjectId;
    RecyclerView recyclerView;
    Button addStudent, deleteStudent,importStudent;
    Dialog dialog;
    Dialog deleteDialog;
    List<PerfomanceStudents> perfomanceStudentsList;


    public StudentFragment(int subjectId) {
        // Required empty public constructor
        this.subjectId = subjectId;
    }


    public static StudentFragment newInstance(String param1, String param2) {
        StudentFragment fragment = new StudentFragment(0);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPerfomance getPerfomance = new getPerfomance(getContext(), this);
        getPerfomance.getData(subjectId, getContext());
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.add_student);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(getDrawable(getContext(),R.drawable.dialogbackground));
        dialog.setCancelable(false);
        deleteDialog = new Dialog(getContext());
        deleteDialog.setContentView(R.layout.delete_student);
        deleteDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        deleteDialog.getWindow().setBackgroundDrawable(getDrawable(getContext(),R.drawable.dialogbackground));
        deleteDialog.setCancelable(false);




    }
    public void updateAdapter(List<PerfomanceStudents> perfomanceStudents)
    {
        this.perfomanceStudentsList = perfomanceStudents; // Store the perfomanceStudents list in the variable
        if (recyclerView != null) { // Check if recyclerView is not null
            PerformanceAdapter adapter = new PerformanceAdapter(perfomanceStudents);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_student, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new PerformanceAdapter(new ArrayList<>())); // Initialize with an empty list


        if (perfomanceStudentsList != null) { // Check if perfomanceStudentsList is not null
            updateAdapter(perfomanceStudentsList); // Update the adapter with the stored list
        }
        addStudent = view.findViewById(R.id.btnAdd);
        deleteStudent = view.findViewById(R.id.btnRemove);
        importStudent = view.findViewById(R.id.btnImport);
        addStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });
        Button cancel = dialog.findViewById(R.id.closebtn);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        Button add = dialog.findViewById(R.id.btn_add);
        EditText name = dialog.findViewById(R.id.student_name);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String studentName = name.getText().toString();
                if (studentName.isEmpty()) {
                    Toast.makeText(getContext(), "Please enter a name", Toast.LENGTH_SHORT).show();
                } else {
                    Apihelper apihelper = new Apihelper(getContext());
                    apihelper.add_student(studentName, subjectId, getContext());
                    getPerfomance getPerfomance = new getPerfomance(getContext(), StudentFragment.this);
                    getPerfomance.getData(subjectId, getContext());
                    dialog.dismiss();
                }
            }
        });
        deleteStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteDialog.show();
            }
        });
        Button cancelDelete = deleteDialog.findViewById(R.id.closebtn);
        cancelDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteDialog.dismiss();
            }
        });
        Button delete = deleteDialog.findViewById(R.id.btn_dlet);
        EditText studentId = deleteDialog.findViewById(R.id.student_id);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String studentIdString = studentId.getText().toString();
                if (studentIdString.isEmpty()) {
                    Toast.makeText(getContext(), "Please enter a student ID", Toast.LENGTH_SHORT).show();
                } else {
                    Apihelper apihelper = new Apihelper(getContext());
                    apihelper.delete_student(Integer.parseInt(studentIdString), getContext(),subjectId);
                    getPerfomance getPerfomance = new getPerfomance(getContext(), StudentFragment.this);
                    getPerfomance.getData(subjectId, getContext());
                    deleteDialog.dismiss();
                }
            }
        });

        return view;
    }
}