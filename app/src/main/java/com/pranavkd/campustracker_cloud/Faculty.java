package com.pranavkd.campustracker_cloud;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pranavkd.campustracker_cloud.data.InternallistData;
import com.pranavkd.campustracker_cloud.data.faculti;
import com.pranavkd.campustracker_cloud.interfaces.ApiHelperLoaded;
import com.pranavkd.campustracker_cloud.interfaces.OnApiLoaded;
import com.pranavkd.campustracker_cloud.interfaces.UpdateFacultie;

import java.util.List;

public class Faculty extends AppCompatActivity {
    RecyclerView recyclerView;
    FloatingActionButton fab;
    Apihelper apihelper;
    FacultiAdapter facultiAdapter;
    List<faculti> faculties;
    ProgressBar progressBar;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty);
        fab = findViewById(R.id.fab);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        apihelper = new Apihelper();
        progressBar = findViewById(R.id.progressBar);
        listfaculties();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addfacultie();
            }
        });
    }

    private void addfacultie() {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.add_faculti_dialog);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialogbackground));
        dialog.show();
        dialog.findViewById(R.id.addButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = ((android.widget.EditText) dialog.findViewById(R.id.facultyNameEditText)).getText().toString();
                String password = ((android.widget.EditText) dialog.findViewById(R.id.facultyPasswordEditText)).getText().toString();
                progressBar.setVisibility(ProgressBar.VISIBLE);
                apihelper.addfacultie(Faculty.this, name, password, new ApiHelperLoaded() {
                    @Override
                    public void onApiHelperLoaded() {
                        listfaculties();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(Faculty.this, "Added", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                dialog.dismiss();

            }
        });
        dialog.findViewById(R.id.closeButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private void listfaculties() {
        apihelper.listfaculties(this,new OnApiLoaded() {
            @Override
            public void onApiLoaded(List<InternallistData> internallistData) {
            }

            @Override
            public void onFacultiLoaded(List<faculti> faculties) {
                progressBar.setVisibility(ProgressBar.GONE);
                updatelist(faculties);
            }
        });
    }

    private void updatelist(List<faculti> faculties) {
        facultiAdapter = new FacultiAdapter(faculties, this, new UpdateFacultie() {
            @Override
            public void onDeleteFacultie(int id) {
                progressBar.setVisibility(ProgressBar.VISIBLE);
                apihelper.deletefacultie(Faculty.this, id, new ApiHelperLoaded() {
                    @Override
                    public void onApiHelperLoaded() {
                        listfaculties();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(Faculty.this, "Deleted", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }

            @Override
            public void onUpdateFacultie(int id, String password) {
                progressBar.setVisibility(ProgressBar.VISIBLE);
                apihelper.updatefacultie(Faculty.this, id, password, new ApiHelperLoaded() {
                    @Override
                    public void onApiHelperLoaded() {
                        listfaculties();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(Faculty.this, "Updated", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        });
        recyclerView.setAdapter(facultiAdapter);
    }

}