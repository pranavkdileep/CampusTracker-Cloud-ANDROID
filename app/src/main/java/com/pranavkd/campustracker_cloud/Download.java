package com.pranavkd.campustracker_cloud;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class Download extends AppCompatActivity {
    int subjectId;
    Button downloadInternal,downloadAssignment,downloadAttendance,downloadPerformance;
    EditText subjectIdEditText;
    String url;
    DownloadManager downloadManager;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        subjectIdEditText = findViewById(R.id.subjectId);
        subjectId = getIntent().getIntExtra("subjectId",0);
        subjectIdEditText.setText(String.valueOf(subjectId));
        downloadInternal = findViewById(R.id.downloadInternal);
        downloadAssignment = findViewById(R.id.downloadAssignment);
        downloadAttendance = findViewById(R.id.downloadAttendance);
        downloadPerformance = findViewById(R.id.downloadPerformance);
        constantsetup constantsetu = new constantsetup(this);
        url = constantsetu.getURL();
        progressBar = findViewById(R.id.progressBar);
        downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        downloadInternal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                Uri uri = Uri.parse(url+"downloadInternal/"+subjectId);
                Log.e("url",url+"downloadInternal/"+subjectId);
                DownloadManager.Request request = new DownloadManager.Request(uri);
                request.setMimeType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                Long reference = downloadManager.enqueue(request);
                progressBar.setVisibility(View.GONE);
                Toast.makeText(Download.this, "Download Started", Toast.LENGTH_SHORT).show();
            }
        });
        downloadAssignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                Uri uri = Uri.parse(url+"downloadAssignment/"+subjectId);
                Log.e("url",url+"downloadAssignment/"+subjectId);
                DownloadManager.Request request = new DownloadManager.Request(uri);
                request.setMimeType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                Long reference = downloadManager.enqueue(request);
                progressBar.setVisibility(View.GONE);
                Toast.makeText(Download.this, "Download Started", Toast.LENGTH_SHORT).show();
            }
        });
        downloadAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                Uri uri = Uri.parse(url+"downloadAttendance/"+subjectId);
                Log.e("url",url+"downloadAttendance/"+subjectId);
                DownloadManager.Request request = new DownloadManager.Request(uri);
                request.setMimeType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                Long reference = downloadManager.enqueue(request);
                progressBar.setVisibility(View.GONE);
                Toast.makeText(Download.this, "Download Started", Toast.LENGTH_SHORT).show();
            }
        });
        downloadPerformance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                Uri uri = Uri.parse(url+"downloadPerformance/"+subjectId);
                Log.e("url",url+"downloadPerformance/"+subjectId);
                DownloadManager.Request request = new DownloadManager.Request(uri);
                request.setMimeType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                Long reference = downloadManager.enqueue(request);
                progressBar.setVisibility(View.GONE);
                Toast.makeText(Download.this, "Download Started", Toast.LENGTH_SHORT).show();
            }
        });
    }
}