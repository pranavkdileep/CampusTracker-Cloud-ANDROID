package com.pranavkd.campustracker_cloud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.pranavkd.campustracker_cloud.interfaces.Loging;

public class Login extends AppCompatActivity {
    Button btn_login;
    EditText et_url,et_key,ed_faculty_id,ed_password;
    constantsetup db;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        db = new constantsetup(this);
        progressBar = findViewById(R.id.progressBar);
        btn_login = findViewById(R.id.setconst);
        et_url = findViewById(R.id.ed_url);
        et_key = findViewById(R.id.ed_key);
        ed_faculty_id = findViewById(R.id.ed_faculty_id);
        ed_password = findViewById(R.id.ed_password);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = et_url.getText().toString();
                String key = et_key.getText().toString();
                int faculity = Integer.parseInt(ed_faculty_id.getText().toString());
                String password = ed_password.getText().toString();
                Apihelper apihelper = new Apihelper();
                progressBar.setVisibility(View.VISIBLE);
                if(faculity!=0)
                {
                    apihelper.login(url,key,faculity,password,new Loging(){

                        @Override
                        public void login(boolean success) {
                            if(success){
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        progressBar.setVisibility(View.GONE);
                                        Toast.makeText(Login.this,"Login Success",Toast.LENGTH_SHORT).show();
                                    }
                                });
                                Log.e("url",url+" "+key+" "+faculity);
                                db.addUser(url,key,faculity);
                                Intent intent = new Intent(Login.this, MainActivity.class);
                                startActivity(intent);
                            }
                            else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        progressBar.setVisibility(View.GONE);
                                        Toast.makeText(Login.this,"Login Failed",Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    });
                }
                else
                {
                    apihelper.adminauth(url,key,faculity,password,new Loging(){

                        @Override
                        public void login(boolean success) {
                            if(success){
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        progressBar.setVisibility(View.GONE);
                                        Toast.makeText(Login.this,"Login Success",Toast.LENGTH_SHORT).show();
                                    }
                                });
                                Log.e("url",url+" "+key+" "+faculity);
                                db.addUser(url,key,faculity);
                                Intent intent = new Intent(Login.this, MainActivity.class);
                                startActivity(intent);
                            }
                            else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        progressBar.setVisibility(View.GONE);
                                        Toast.makeText(Login.this,"Login Failed",Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    });
                }
            }
        });

    }
}