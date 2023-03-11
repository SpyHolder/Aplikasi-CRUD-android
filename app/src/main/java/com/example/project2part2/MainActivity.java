package com.example.project2part2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText username,password;
    Button login;
    TextView forgetpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.btn_login);
        forgetpass = (TextView) findViewById(R.id.forgetpassowrd);
        DBHelper MyDB = new DBHelper(this);


        if (getSupportActionBar()!=null) {
            getSupportActionBar().hide();
        }

            String Username = "admin";
            String Password = "admin";
            Boolean checkinsert = MyDB.insetLogin(Username,Password);
            if (checkinsert==true) {
                Toast.makeText(this, "Berhasil", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(this, "Gagal", Toast.LENGTH_SHORT).show();
            }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getusername = username.getText().toString();
                String getpassword = password.getText().toString();
                try {
                    Boolean checkusername = MyDB.checkusername(getusername);
                    Boolean checkpassword = MyDB.checkpassword(getpassword);
                    if (getusername.equals("")&&getpassword.equals("")) {
                        Toast.makeText(MainActivity.this, "Silahkan Mengisi Data!", Toast.LENGTH_SHORT).show();
                    }
                    else if (checkusername==true&&checkpassword==false) {
                        Toast.makeText(MainActivity.this, "Password Salah", Toast.LENGTH_SHORT).show();
                    }
                    else if (checkusername==true&&checkpassword==true) {
                        Intent masukHome = new Intent(MainActivity.this,HomeActivity.class);
                        startActivity(masukHome);
                    }
                }
                catch (Exception ex){
                    Toast.makeText(MainActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        forgetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent masukForget = new Intent(MainActivity.this,ForgetActivity.class);
                    startActivity(masukForget);
            }
        });
    }
}