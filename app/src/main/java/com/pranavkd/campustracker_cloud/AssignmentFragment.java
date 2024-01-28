package com.pranavkd.campustracker_cloud;

import static androidx.appcompat.content.res.AppCompatResources.getDrawable;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.pranavkd.campustracker_cloud.apicaller.GetAssigmentMaarkListApi;
import com.pranavkd.campustracker_cloud.apicaller.getAttendanceApi;
import com.pranavkd.campustracker_cloud.data.AssignmentsDateList;
import com.pranavkd.campustracker_cloud.data.studentAttendance;
import com.pranavkd.campustracker_cloud.interfaces.ApiHelperLoaded;
import com.pranavkd.campustracker_cloud.interfaces.AssignmentData;
import com.pranavkd.campustracker_cloud.interfaces.onEditAtt;

import java.util.List;

public class AssignmentFragment extends Fragment {
    public int subjectId;
    RecyclerView recyclerView;


    EditText assignmentNo;
    Button getassignment;
    GetAssigmentMaarkListApi apihelper;
    List<AssignmentsDateList> assignmentsDateList;
    AssignmentAdapter assignmentAdapter;
    Dialog dialog;
    Apihelper apihelper1;
    ProgressBar progressBar;

    public AssignmentFragment() {
        // Required empty public constructor
    }


    public static AssignmentFragment newInstance(String param1, String param2) {
        AssignmentFragment fragment = new AssignmentFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null)
        {
            subjectId=getArguments().getInt("subjectId");
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_assignment, container, false);
        recyclerView = view.findViewById(R.id.recyclerView_attendance_list);
        assignmentNo = view.findViewById(R.id.tvAiisgnmentsNo);
        getassignment = view.findViewById(R.id.btn_get_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        subjectId = getArguments().getInt("subjectId");
        progressBar = view.findViewById(R.id.loadingProgressBar);
        Button bulk = view.findViewById(R.id.mark_bulk_assignments);
        bulk.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),BulkAssignmentsMark.class);
                intent.putExtra("subjectid", String.valueOf(subjectId));
                startActivity(intent);
            }
        });
        getassignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                apihelper = new GetAssigmentMaarkListApi(subjectId,Integer.parseInt(assignmentNo.getText().toString()),getContext());
                apihelper.getAssignmentMarkList(new AssignmentData() {
                    @Override
                    public void onAssignmentDataLoaded(List<AssignmentsDateList> assignmentsDateList) {
                        updateUi(assignmentsDateList);
                    }
                });
            }
        });
        return view;
    }

    private void updateUi(List<AssignmentsDateList> assignmentsDateList) {
        assignmentAdapter = new AssignmentAdapter(assignmentsDateList, new onEditAtt() {

            @Override
            public void onEdit(int attId) {
                updateassignment(attId);
            }
        });
        recyclerView.setAdapter(assignmentAdapter);
        progressBar.setVisibility(View.GONE);
    }

    private void updateassignment(int attId) {
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.update_assignment);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(getDrawable(getContext(),R.drawable.dialogbackground));
        EditText marks = dialog.findViewById(R.id.new_assignments_mark);
        Button update = dialog.findViewById(R.id.updatebtn);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                apihelper1 = new Apihelper(getContext());
                apihelper1.updateAssignment(attId, Integer.parseInt(marks.getText().toString()), new ApiHelperLoaded() {
                    @Override
                    public void onApiHelperLoaded() {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getContext(),"Updated",Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                apihelper = new GetAssigmentMaarkListApi(subjectId,Integer.parseInt(assignmentNo.getText().toString()),getContext());
                                apihelper.getAssignmentMarkList(new AssignmentData() {
                                    @Override
                                    public void onAssignmentDataLoaded(List<AssignmentsDateList> assignmentsDateList) {
                                        updateUi(assignmentsDateList);
                                    }
                                });
                            }
                        });
                    }
                });
            }
        });
        Button close = dialog.findViewById(R.id.closebtn);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }
}