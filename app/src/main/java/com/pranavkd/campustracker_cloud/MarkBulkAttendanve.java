package com.pranavkd.campustracker_cloud;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.pranavkd.campustracker_cloud.apicaller.getPerfomance;
import com.pranavkd.campustracker_cloud.data.BulkAttendance;
import com.pranavkd.campustracker_cloud.data.PerfomanceStudents;
import com.pranavkd.campustracker_cloud.interfaces.OnStudentsLoadedListener;
import com.pranavkd.campustracker_cloud.interfaces.OnSwitchChangedListener;

import java.util.ArrayList;
import java.util.List;

public class MarkBulkAttendanve extends AppCompatActivity {
    EditText subjectId;
    TextView dateView;
    String subjectIdString;
    int subjectIdInt;
    bulkAttendanceAdapter bulkAttendanceAdapter;
    List<PerfomanceStudents> perfomanceStudentsList;
    List<BulkAttendance> bulkAttendanceList;
    RecyclerView recyclerView;
    getPerfomance getPerfomanceInstance;
    Apihelper apihelper;
    Button submit;
    Dialog dialog;
    String absString ="";
    int[] absentStudents;
    String date;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_bulk_attendanve);
        subjectId = findViewById(R.id.subid);
        dateView = findViewById(R.id.date);
        subjectIdString = Integer.toString(getIntent().getIntExtra("subjectId",0));
        subjectId.setText(subjectIdString);
        subjectIdInt = getIntent().getIntExtra("subjectId",0);
        date = getIntent().getStringExtra("date");
        dateView.setText(date);
        String[] dateArray = date.split("-");
        dateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
        apihelper = new Apihelper(this);
        getPerfomanceInstance = new getPerfomance(this, this);
        recyclerView = findViewById(R.id.recyclerView_student_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getPerfomanceInstance.getStudentList(subjectIdInt, new OnStudentsLoadedListener() {
            @Override
            public void onStudentsLoaded(List<PerfomanceStudents> students) {
                perfomanceStudentsList = students;
                updateList(perfomanceStudentsList);
            }
        });
        submit = findViewById(R.id.btnMarkAttendance);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit(bulkAttendanceList);
            }
        });
    }
    public void updateList(List<PerfomanceStudents> perfomanceStudentsList) {
        this.perfomanceStudentsList = perfomanceStudentsList;
        if (perfomanceStudentsList != null) {
            if (bulkAttendanceList == null) {
                bulkAttendanceList = new ArrayList<>();
            }
            for (PerfomanceStudents student : perfomanceStudentsList) {
                boolean alreadyExists = false;
                for (BulkAttendance ba : bulkAttendanceList) {
                    if (ba.getStudentId() == student.getStudentId()) {
                        alreadyExists = true;
                        break;
                    }
                }
                if (!alreadyExists) {
                    bulkAttendanceList.add(new BulkAttendance(student.getStudentId(), true));
                    Log.e("student", student.getStudentName());
                }
            }
        }
        bulkAttendanceAdapter = new bulkAttendanceAdapter(perfomanceStudentsList, new OnSwitchChangedListener() {

            @Override
            public void onSwitchChange(int position, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(MarkBulkAttendanve.this, "Present", Toast.LENGTH_SHORT).show();
                } else {
                    bulkAttendanceList.get(position).setPresent(false);
                }
            }
        });
        recyclerView.setAdapter(bulkAttendanceAdapter);
        bulkAttendanceAdapter.notifyDataSetChanged();
    }
    public void submit(List<BulkAttendance> bulkAttendanceList)
    {
        absentStudents = new int[bulkAttendanceList.size()];
        for(int i = 0; i < bulkAttendanceList.size(); i++) {
            if (!bulkAttendanceList.get(i).getPresent()) {
                absentStudents[i] = i + 1;
            }
        }
        for(int i = 0; i < absentStudents.length; i++) {
            if(absentStudents[i] != 0) {
                absString = absString + absentStudents[i] + ",";
            }
        }
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.absdialog);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView tv = dialog.findViewById(R.id.student_name);
        Button btn = dialog.findViewById(R.id.btn_add);
        Button close = dialog.findViewById(R.id.closebtn);
        tv.setText(absString);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apihelper.addBulkAttantance(subjectIdInt, date, bulkAttendanceList);
                dialog.dismiss();
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                absString = "";
            }
        });
        dialog.show();

    }


    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        dateView.setText(String.format("%d/%02d/%02d", month+1, dayOfMonth, year));
                        date = String.format("%d/%02d/%02d", month+1, dayOfMonth, year);
                    }
                },
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }
}