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
import com.pranavkd.campustracker_cloud.data.BulkAttendance;
import com.pranavkd.campustracker_cloud.data.BulkInternals;
import com.pranavkd.campustracker_cloud.data.InternallistData;
import com.pranavkd.campustracker_cloud.data.PerfomanceStudents;
import com.pranavkd.campustracker_cloud.interfaces.ApiHelperLoaded;
import com.pranavkd.campustracker_cloud.interfaces.OnApiLoaded;
import com.pranavkd.campustracker_cloud.interfaces.OnStudentsLoadedListener;
import com.pranavkd.campustracker_cloud.interfaces.Onmarkchanged;

import java.util.ArrayList;
import java.util.List;

public class BulkInternalExamMark extends AppCompatActivity {
    EditText subjectId;
    EditText internalNo;
    EditText max_mark;
    int max_markint;
    int subjectIdInt;
    int internalNoInt;
    List<PerfomanceStudents> perfomanceStudentsList;
    List<BulkInternals> bulkInternalsList;
    getPerfomance getPerfomanceInstance;
    Apihelper apihelper;
    RecyclerView recyclerView;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bulk_internal_exam_mark);
        subjectId = findViewById(R.id.subid);
        internalNo = findViewById(R.id.internalno);
        Intent intent = getIntent();
        subjectIdInt = intent.getIntExtra("subjectId",0);
        subjectId.setText(Integer.toString(subjectIdInt));
        max_mark = findViewById(R.id.maxmarks);
        recyclerView = findViewById(R.id.recyclerView_student_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getPerfomanceInstance = new getPerfomance(this);
        getPerfomanceInstance.getStudentList(subjectIdInt, new OnStudentsLoadedListener() {
            @Override
            public void onStudentsLoaded(List<PerfomanceStudents> perfomanceStudentsList) {
                updatelist(perfomanceStudentsList);
            }
        });
        submit = findViewById(R.id.submit_internals);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                max_markint = Integer.parseInt(max_mark.getText().toString());
                internalNoInt = Integer.parseInt(internalNo.getText().toString());
                submitfunction(internalNoInt,subjectIdInt,max_markint,bulkInternalsList);
            }
        });
    }

    private void submitfunction(int internalNoInt, int subjectIdInt,int max_markint, List<BulkInternals> bulkInternalsList) {
        Apihelper apihelper = new Apihelper(this);
        apihelper.addBulkInternals(subjectIdInt, internalNoInt, max_markint, bulkInternalsList, new ApiHelperLoaded() {
            @Override
            public void onApiHelperLoaded() {
                finishfun();
            }
        });
    }

    private void finishfun() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(BulkInternalExamMark.this, "Internals Added", Toast.LENGTH_SHORT).show();
            }
        });
        finish();
    }

    private void updatelist(List<PerfomanceStudents> perfomanceStudentsList) {
        this.perfomanceStudentsList = perfomanceStudentsList;
        if (perfomanceStudentsList != null) {
            if (bulkInternalsList == null) {
                bulkInternalsList = new ArrayList<>();
            }
            for (PerfomanceStudents student : perfomanceStudentsList) {
                boolean alreadyExists = false;
                for (BulkInternals ba : bulkInternalsList) {
                    if (ba.getStudentId() == student.getStudentId()) {
                        alreadyExists = true;
                        break;
                    }
                }
                if (!alreadyExists) {
                    bulkInternalsList.add(new BulkInternals(student.getStudentId(), 0));
                    Log.e("student", student.getStudentName());
                }
            }
        }
        BulkInternalAdapter bulkInternalAdapter = new BulkInternalAdapter(perfomanceStudentsList, new Onmarkchanged() {
            @Override
            public void onMarkChanged(int position, int mark) {
                bulkInternalsList.get(position).setMarksObtained(mark);
            }
        });
        recyclerView.setAdapter(bulkInternalAdapter);
    }
}