package com.pranavkd.campustracker_cloud;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.pranavkd.campustracker_cloud.apicaller.getPerfomance;
import com.pranavkd.campustracker_cloud.data.BulkAssignment;
import com.pranavkd.campustracker_cloud.data.BulkInternals;
import com.pranavkd.campustracker_cloud.data.PerfomanceStudents;
import com.pranavkd.campustracker_cloud.interfaces.ApiHelperLoaded;
import com.pranavkd.campustracker_cloud.interfaces.OnStudentsLoadedListener;
import com.pranavkd.campustracker_cloud.interfaces.Onmarkchanged;

import java.util.ArrayList;
import java.util.List;

public class BulkAssignmentsMark extends AppCompatActivity {
    EditText subject, assignmentNo, maxmark;
    RecyclerView recyclerView;
    Button submit;
    String subjectidstring;
    int subjectidIntInt, assignemtIntInt, maxMarkIntInt;
    List<PerfomanceStudents> perfomanceStudentsList;
    List<BulkAssignment> bulkAssignmentList;
    getPerfomance getPerfomanceInstance;
    Apihelper apihelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bulk_assignments_mark);
        subject = findViewById(R.id.subid);
        assignmentNo = findViewById(R.id.assignementno);
        maxmark = findViewById(R.id.maxmarks);
        recyclerView = findViewById(R.id.recyclerView_student_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Intent intent = getIntent();
        subjectidstring = intent.getStringExtra("subjectid");
        subject.setText(subjectidstring);
        subjectidIntInt = Integer.parseInt(subjectidstring);
        getPerfomanceInstance = new getPerfomance(this);
        getPerfomanceInstance.getStudentList(subjectidIntInt, new OnStudentsLoadedListener() {
            @Override
            public void onStudentsLoaded(List<PerfomanceStudents> perfomanceStudentsList) {
                updatelist(perfomanceStudentsList);
            }
        });
        submit = findViewById(R.id.submit_internals);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                maxMarkIntInt = Integer.parseInt(maxmark.getText().toString());
                assignemtIntInt = Integer.parseInt(assignmentNo.getText().toString());
                submitfunction(assignemtIntInt,subjectidIntInt,maxMarkIntInt,bulkAssignmentList);
            }
        });
    }

    private void submitfunction(int assignemtIntInt, int subjectidIntInt, int maxMarkIntInt, List<BulkAssignment> bulkAssignmentList) {
        Apihelper apihelper = new Apihelper(this);
        apihelper.addBulkAssignment(subjectidIntInt, assignemtIntInt, maxMarkIntInt, bulkAssignmentList, new ApiHelperLoaded() {
            @Override
            public void onApiHelperLoaded() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(BulkAssignmentsMark.this, "Bulk Assignment Added", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
            }
        });
    }

    private void updatelist(List<PerfomanceStudents> perfomanceStudentsList) {
        this.perfomanceStudentsList = perfomanceStudentsList;
        if (perfomanceStudentsList != null) {
            if (bulkAssignmentList == null) {
                bulkAssignmentList = new ArrayList<>();
            }
            for (PerfomanceStudents student : perfomanceStudentsList) {
                boolean alreadyExists = false;
                for (BulkAssignment ba : bulkAssignmentList) {
                    if (ba.getStudent_id() == student.getStudentId()) {
                        alreadyExists = true;
                        break;
                    }
                }
                if (!alreadyExists) {
                    bulkAssignmentList.add(new BulkAssignment(student.getStudentId(), 0));
                    Log.e("student", student.getStudentName());
                }
            }
        }
        BulkAssignmentAdapter bulkAssignmentAdapter = new BulkAssignmentAdapter(perfomanceStudentsList,new Onmarkchanged(){

            @Override
            public void onMarkChanged(int position, int mark) {
                bulkAssignmentList.get(position).setMark_obtained(mark);
            }
        });
        recyclerView.setAdapter(bulkAssignmentAdapter);
    }
}