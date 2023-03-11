package com.example.project2part2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    ListView listView;
    Button tambah;
    AlertDialog.Builder dialog;
    List<Model> list = new ArrayList<>();
    Adapter adapter = new Adapter();
    DBHelper MyDB = new DBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        listView = (ListView) findViewById(R.id.daftar_tamu);
        tambah = (Button) findViewById(R.id.tambahkan);

        listView.setAdapter(adapter);

        if (getSupportActionBar()!=null) {
            getSupportActionBar().hide();
        }

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    String id = list.get(i).getId();
                    final CharSequence[] dialogItem = {"Detail","Edit","Hapus"};
                    dialog =new AlertDialog.Builder(HomeActivity.this);
                    dialog.setItems(dialogItem, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            switch (i) {
                                case 0:
                                    Intent masukDetail = new Intent(HomeActivity.this,DetailActivity.class);
                                    masukDetail.putExtra("id_detail",id);
                                    startActivity(masukDetail);
                                    break;
                                case 1:
                                    Intent masukEdit = new Intent(HomeActivity.this,EditActivity.class);
                                    masukEdit.putExtra("id_edit",id);
                                    startActivity(masukEdit);
                                    break;
                                case 2:
                                    int id_tamu = Integer.parseInt(id);
                                    MyDB.deleteTamu(id_tamu);
                                    Toast.makeText(HomeActivity.this, "Berhasil Dihapus", Toast.LENGTH_SHORT).show();
                                    onResume();
                                    break;
                            }
                        }
                    }).show();
                }
                catch (Exception ex) {
                    Toast.makeText(HomeActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });

        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, InsertActivity.class);
                startActivity(intent);
            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();
    }
}