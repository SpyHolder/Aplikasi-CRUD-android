package com.example.project2part2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class InsertActivity extends AppCompatActivity {

    EditText namaTamu,Perusahaan,Keperluan,JamMasuk,JamKeluar;
    ImageView FotoKeterangan;
    Button simpan;
    DBHelper MyDB = new DBHelper(this);
    Uri ImageFilePath;
    Bitmap ImageToStore;
    private static final int KUALITAS = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
        namaTamu = (EditText) findViewById(R.id.nama_tamu_insert);
        Keperluan = (EditText) findViewById(R.id.keperluan_tamu_insert);
        Perusahaan = (EditText) findViewById(R.id.perusahaan_tamu_insert);
        JamMasuk = (EditText) findViewById(R.id.jam_masuk_insert);
        JamKeluar = (EditText) findViewById(R.id.jam_keluar_insert);
        FotoKeterangan = (ImageView) findViewById(R.id.foto_keterangan);
        simpan = (Button) findViewById(R.id.btn_tambah);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                Date date = new Date();
                DateFormat dateFormat = new SimpleDateFormat("HH.mm");
                JamMasuk.setText(dateFormat.format(date));
            }
        });

        JamMasuk.setEnabled(false);
    }

    public void chooseImage(View view) {
        try {
            Intent pilihGambar = new Intent();
            pilihGambar.setType("image/*");

            pilihGambar.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(pilihGambar,KUALITAS);
        }
        catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode==KUALITAS && resultCode==RESULT_OK && data != null && data.getData() != null) {
                ImageFilePath = data.getData();
                ImageToStore = MediaStore.Images.Media.getBitmap(getContentResolver(),ImageFilePath);
                FotoKeterangan.setImageBitmap(ImageToStore);
            }
        }
        catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void tambah(View view) {
        String nama = namaTamu.getText().toString();
        String keper = Keperluan.getText().toString();
        String perus = Perusahaan.getText().toString();
        String masuk = JamMasuk.getText().toString();
        String keluar = JamKeluar.getText().toString();
        if (!namaTamu.getText().toString().isEmpty()&&!Keperluan.getText().toString().isEmpty()&&!Perusahaan.getText().toString().isEmpty()&&!JamMasuk.getText().toString().isEmpty()&&!JamKeluar.getText().toString().isEmpty()&&FotoKeterangan.getDrawable()!=null&&ImageToStore!=null) {
            Boolean checkInsert = MyDB.insertTamu(nama,keper,perus,masuk,keluar,ImageToStore);
            if (checkInsert==true) {
                Toast.makeText(this, "Berhasil Menambahkan Data", Toast.LENGTH_SHORT).show();
                Intent kembaliHome = new Intent(InsertActivity.this,HomeActivity.class);
                startActivity(kembaliHome);
            }
            else {
                Toast.makeText(this, "Gagal Menambahkan Data", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(this, "Silahkan Lengkapi Data Diatas!", Toast.LENGTH_SHORT).show();
        }
    }

}