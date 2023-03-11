package com.example.project2part2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ForgetActivity extends AppCompatActivity {

    EditText username,password,compassword;
    Button save;
    DBHelper MyDB = new DBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
        username = (EditText) findViewById(R.id.username_forget);
        password = (EditText) findViewById(R.id.password_forget);
        compassword = (EditText) findViewById(R.id.compassword_forget);
        save = (Button) findViewById(R.id.btn_save);

        if (getSupportActionBar()!=null) {
            getSupportActionBar().hide();
        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String getusername = username.getText().toString();
                    String getpassword = password.getText().toString();
                    String getcomPassword = compassword.getText().toString();
                    if (getusername.equals("")&&getpassword.equals("")&&getcomPassword.equals("")) {
                        Toast.makeText(ForgetActivity.this, "Silahkan Mengisi Data Diatas!", Toast.LENGTH_SHORT).show();
                    }
                    else if (!getpassword.equals(getcomPassword)) {
                        Toast.makeText(ForgetActivity.this, "Confrim New Password Salah", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Boolean checkUpdate = MyDB.updateLogin(getusername,getpassword);
                        if (checkUpdate==true) {
                            Toast.makeText(ForgetActivity.this, "Berhasil Mengganti Password", Toast.LENGTH_SHORT).show();
                            Intent MasukLogin = new Intent(ForgetActivity.this,MainActivity.class);
                            startActivity(MasukLogin);
                        }
                    }
                }
                catch (Exception ex) {
                    Toast.makeText(ForgetActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}