package com.pranavkd.campustracker_cloud;

import static androidx.appcompat.content.res.AppCompatResources.getDrawable;

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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.pranavkd.campustracker_cloud.apicaller.GetinternalsApi;
import com.pranavkd.campustracker_cloud.data.InternallistData;
import com.pranavkd.campustracker_cloud.data.faculti;
import com.pranavkd.campustracker_cloud.interfaces.OnApiLoaded;
import com.pranavkd.campustracker_cloud.interfaces.onEditAtt;

import java.util.List;


public class InternalFragment extends Fragment {
    int subject_id;
    RecyclerView recyclerView;
    EditText internalno;
    EditText studentid;
    Button getInternal;
    GetinternalsApi getinternalsApi;
    Dialog dialog;
    Apihelper apihelper;
    InternalAdapter internalAdapter;
    List<InternallistData> internallistDataList;
    ProgressBar progressBar;
    InternalAdapter internalAdapter1;
    public InternalFragment() {
        // Required empty public constructor
    }


    public static InternalFragment newInstance(String param1, String param2) {
        InternalFragment fragment = new InternalFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            subject_id = getArguments().getInt("subjectId");
        }
        apihelper = new Apihelper(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_internal, container, false);
    recyclerView = view.findViewById(R.id.recyclerView_internal_list);
    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    internalno = view.findViewById(R.id.student_id);
    getInternal = view.findViewById(R.id.btn_get_list);
    progressBar = view.findViewById(R.id.loadingProgressBar);
    Button bulkInternal = view.findViewById(R.id.mark_bulk_internals);
    bulkInternal.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getContext(),BulkInternalExamMark.class);
            intent.putExtra("subjectId",subject_id);
            startActivity(intent);
        }
    });
    getInternal.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(internalno.getText().toString().isEmpty())
            {
                Toast.makeText(getContext(),"Enter Internal No",Toast.LENGTH_SHORT).show();
            }
            else
            {
                progressBar.setVisibility(View.VISIBLE);
                getdata();
            }
        }
    });
    return view;
    }

    private void getdata() {
        getinternalsApi = new GetinternalsApi(subject_id,Integer.parseInt(internalno.getText().toString()),getContext(),InternalFragment.this);
        internallistDataList = getinternalsApi.getInternals(new OnApiLoaded() {
            @Override
            public void onApiLoaded(List<InternallistData> list) {
                internallistDataList = (List<InternallistData>) list;
                setInternallistDataList(internallistDataList);
            }

            @Override
            public void onFacultiLoaded(List<faculti> faculti) {

            }
        });
    }

    public void setInternallistDataList(List<InternallistData> internallistDataList) {
        this.internallistDataList = internallistDataList;
        internalAdapter = new InternalAdapter(internallistDataList,new onEditAtt() {
            @Override
            public void onEdit(int attId) {
                updateInternals(attId);
            }
        });
        recyclerView.setAdapter(internalAdapter);
        progressBar.setVisibility(View.GONE);
    }

    private void updateInternals(int attId) {
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.update_internal);
        dialog.getWindow().setBackgroundDrawable(getDrawable(getContext(),R.drawable.dialogbackground));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true);
        dialog.show();
        EditText newinternalmark = dialog.findViewById(R.id.new_internal_mark);
        Button update = dialog.findViewById(R.id.updatebtn);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (newinternalmark.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Enter Student Id", Toast.LENGTH_SHORT).show();
                } else {
                    apihelper.updateInternal(attId, Integer.parseInt(newinternalmark.getText().toString()),subject_id,Integer.parseInt(internalno.getText().toString()), new OnApiLoaded() {
                        @Override
                        public void onApiLoaded(List<InternallistData> list) {
                            internallistDataList = (List<InternallistData>) list;
                            setInternallistDataList(internallistDataList);
                            getdata();
                            dialog.dismiss();
                        }

                        @Override
                        public void onFacultiLoaded(List<faculti> faculti) {

                        }
                    });
                    progressBar.setVisibility(View.VISIBLE);
                    getdata();
                    dialog.dismiss();
                }
            }
        });
        Button close = dialog.findViewById(R.id.closebtn);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}