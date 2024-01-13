package com.pranavkd.campustracker_cloud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class Login extends AppCompatActivity {
    Button btn_login;
    EditText et_url,et_key;
    constantsetup db = new constantsetup(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btn_login = findViewById(R.id.setconst);
        et_url = findViewById(R.id.ed_url);
        et_key = findViewById(R.id.ed_key);
        btn_login.setOnClickListener(view -> {
            String url = et_url.getText().toString();
            String key = et_key.getText().toString();
            db.addUser(url,key);
            if (db.isLoginDetailsExist()) {
                Intent intent = new Intent(Login.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                Intent intent = new Intent(Login.this, Login.class);
                startActivity(intent);
                finish();
            }
        });

    }
}