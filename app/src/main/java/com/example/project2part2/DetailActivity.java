package com.example.project2part2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentUris;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    TextView nama,keperluan,perusahaan,masuk,keluar;
    ImageView keterangan;
    DBHelper MyDB = new DBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        nama = (TextView) findViewById(R.id.nama_tamu_detail);
        keperluan = (TextView) findViewById(R.id.keperluan_tamu_detail);
        perusahaan = (TextView) findViewById(R.id.perusahaan_tamu_detail);
        masuk = (TextView) findViewById(R.id.jam_masuk_detail);
        keluar = (TextView) findViewById(R.id.jam_keluar_detail);
        keterangan = (ImageView) findViewById(R.id.foto_keterangan_detail);

        if (getSupportActionBar()!=null) {
            getSupportActionBar().hide();
        }

        String id_detail = getIntent().getStringExtra("id_detail");
        int id = Integer.parseInt(id_detail);

        Cursor tampilkan = MyDB.KirimTamu(id);

        nama.setText(tampilkan.getString(1));
        keperluan.setText(tampilkan.getString(2));
        perusahaan.setText(tampilkan.getString(3));
        masuk.setText(tampilkan.getString(4));
        keluar.setText(tampilkan.getString(5));
        byte[] image = tampilkan.getBlob(6);
        setImage(image);
    }

    public void setImage(byte[] byteImage) {
        keterangan.setImageBitmap(BitmapFactory.decodeByteArray(byteImage,0,byteImage.length));
    }

}