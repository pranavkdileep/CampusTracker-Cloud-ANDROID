package com.pranavkd.campustracker_cloud;

import static androidx.appcompat.content.res.AppCompatResources.getDrawable;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.OpenableColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.pranavkd.campustracker_cloud.apicaller.getPerfomance;
import com.pranavkd.campustracker_cloud.data.PerfomanceStudents;
import com.pranavkd.campustracker_cloud.interfaces.ApiHelperLoaded;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;


public class StudentFragment extends Fragment {
    private int subjectId;
    RecyclerView recyclerView;
    Button addStudent, deleteStudent,importStudent;
    Dialog dialog;
    Dialog importDialog;
    ActivityResultLauncher<String> mGetContent;
    ProgressBar loadingProgressBar;
    ProgressBar progressBarDialog;
    Button selectFile;
    Dialog deleteDialog;
    ProgressBar progressBar2;
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

        mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri o) {
                String filename = getFileName(o);
                Log.e("TAG", "onActivityResult: "+filename );
                if(o != null)
                {
                    try {
                        InputStream inputStream = getActivity().getContentResolver().openInputStream(o);
                        File file = new File(getActivity().getCacheDir(), filename);
                        writeFile(inputStream, file);
                        Log.e("TAG", "onActivityResult: "+file.getName() );
                        selectFile.setVisibility(View.GONE);
                        progressBarDialog.setVisibility(View.VISIBLE);
                        Apihelper apihelper = new Apihelper(getContext());
                        apihelper.uploadStudentsListXls(subjectId, file, new ApiHelperLoaded() {
                            @Override
                            public void onApiHelperLoaded() {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        progressBarDialog.setVisibility(View.GONE);
                                        selectFile.setVisibility(View.VISIBLE);
                                        importDialog.dismiss();
                                        getPerfomance getPerfomance = new getPerfomance(getContext(), StudentFragment.this);
                                        getPerfomance.getData(subjectId, getContext());
                                        loadingProgressBar.setVisibility(View.VISIBLE);
                                    }
                                });
                            }
                        });

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                else
                {
                    Log.e("TAG", "onActivityResult: "+o.getPath() );
                }
            }
        });

    }
    private void writeFile(InputStream in, File file) {
        try (OutputStream out = new FileOutputStream(file)) {
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
//                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }
    public void updateAdapter(List<PerfomanceStudents> perfomanceStudents)
    {
        this.perfomanceStudentsList = perfomanceStudents; // Store the perfomanceStudents list in the variable
        if (recyclerView != null) { // Check if recyclerView is not null
            PerformanceAdapter adapter = new PerformanceAdapter(perfomanceStudents);
            recyclerView.setAdapter(adapter);
            loadingProgressBar.setVisibility(View.GONE);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_student, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        loadingProgressBar = view.findViewById(R.id.loadingProgressBar);
        loadingProgressBar.setVisibility(View.VISIBLE);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new PerformanceAdapter(new ArrayList<>())); // Initialize with an empty list



        if (perfomanceStudentsList != null) { // Check if perfomanceStudentsList is not null
            updateAdapter(perfomanceStudentsList); // Update the adapter with the stored list
        }
        addStudent = view.findViewById(R.id.btnAdd);
        deleteStudent = view.findViewById(R.id.btnRemove);
        importStudent = view.findViewById(R.id.btnImport);
        importStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                importstudent(subjectId,getContext());
            }
        });
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
                    add.setVisibility(View.GONE);
                    progressBar2 = dialog.findViewById(R.id.progressBar);
                    progressBar2.setVisibility(View.VISIBLE);
                    // desable the add button
                    Apihelper apihelper = new Apihelper(getContext());
                    apihelper.add_student(studentName, subjectId, getContext(), new ApiHelperLoaded() {
                        @Override
                        public void onApiHelperLoaded() {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    dialog.dismiss();
                                    getPerfomance getPerfomance = new getPerfomance(getContext(), StudentFragment.this);
                                    getPerfomance.getData(subjectId, getContext());
                                    loadingProgressBar.setVisibility(View.VISIBLE);
                                }
                            });
                        }
                    });
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
                    loadingProgressBar.setVisibility(View.VISIBLE);
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

    private void importstudent(int subjectId, Context context) {
        importDialog = new Dialog(context);
        importDialog.setContentView(R.layout.uploadlist);
        importDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        importDialog.getWindow().setBackgroundDrawable(getDrawable(context,R.drawable.dialogbackground));
        importDialog.show();
        showfilepremission();
        selectFile = importDialog.findViewById(R.id.select_file_button);
        progressBarDialog = importDialog.findViewById(R.id.progress_bar);
        selectFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGetContent.launch("*/*");
            }
        });
    }





    private void showfilepremission() {

    }
}