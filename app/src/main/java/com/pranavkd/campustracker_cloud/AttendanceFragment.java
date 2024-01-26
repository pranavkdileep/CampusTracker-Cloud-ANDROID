package com.pranavkd.campustracker_cloud;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.pranavkd.campustracker_cloud.data.BulkAttendance;
import com.pranavkd.campustracker_cloud.data.studentAttendance;
import com.pranavkd.campustracker_cloud.interfaces.onEditAtt;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import com.pranavkd.campustracker_cloud.apicaller.getAttendanceApi;


public class AttendanceFragment extends Fragment {
    int subjectId;
    onEditAtt onEditAtt;
    RecyclerView recyclerView;
    AttendanceAdapter attendanceAdapter;
    EditText studentId;
    Button getAttendance;
    getAttendanceApi apihelper;
    List<studentAttendance> studentAttendanceList;
    Dialog dialog;
    Apihelper apihelper1;

    public AttendanceFragment() {
        // Required empty public constructor
    }
    public static AttendanceFragment newInstance(String param1, String param2) {
        AttendanceFragment fragment = new AttendanceFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null)
        {
            subjectId=getArguments().getInt("subjectId");
        }
         apihelper = new getAttendanceApi(subjectId,getContext(),this);
        apihelper1 = new Apihelper(getContext());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_attendance, container, false);
        Button markBulkAttendance = view.findViewById(R.id.mark_bulk_attendance);
        recyclerView = view.findViewById(R.id.recyclerView_attendance_list);
        studentId = view.findViewById(R.id.student_id);
        getAttendance = view.findViewById(R.id.btn_get_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(attendanceAdapter);
        getAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(studentId.getText().toString().isEmpty())
                {
                    Toast.makeText(getContext(),"Please enter student id",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    int id = Integer.parseInt(studentId.getText().toString());
                    studentAttendanceList = apihelper.getAttendance(id,getContext());
                    updateList(studentAttendanceList);
                    recyclerView.setAdapter(attendanceAdapter);
                }
            }
        });
        markBulkAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int subjectId = getArguments().getInt("subjectId");
                SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
                String date = formatter.format(new java.util.Date());
                Intent intent = new Intent(getContext(),MarkBulkAttendanve.class);
                intent.putExtra("subjectId",subjectId);
                intent.putExtra("date",date);
                startActivity(intent);
            }
        });
        return view;
    }
    public void updateList(List<studentAttendance> studentAttendances)
    {
        this.studentAttendanceList = studentAttendances;
        attendanceAdapter = new AttendanceAdapter(studentAttendanceList, new onEditAtt() {
            @Override
            public void onEdit(int attId) {
                updateDialog(attId);
        }
        });
        recyclerView.setAdapter(attendanceAdapter);
    }
    public void updateDialog(int attendanceId)
    {
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.updateattendancedialog);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        Button updateAttendance = dialog.findViewById(R.id.edit_button);
        Spinner spinner = dialog.findViewById(R.id.attendance_spinner);
        Button close = dialog.findViewById(R.id.close_button);
        dialog.setCancelable(false);
        dialog.show();
        updateAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String attendance = spinner.getSelectedItem().toString();
                boolean flag = false;
                if(attendance.equals("True"))
                {
                    flag = true;
                }
                else if(attendance.equals("False"))
                {
                    flag = false;
                }
                apihelper1.updateAttendance(attendanceId,flag);
                dialog.dismiss();
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }


}