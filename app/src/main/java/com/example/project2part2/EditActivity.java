package com.example.project2part2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class EditActivity extends AppCompatActivity {

    EditText nama,keperluan,perusahaan,jammasuk,jamkeluar;
    ImageView fotoketerangan;
    String nm,kp,pr,jm,jk;
    Button save;
    static final int KUALITY = 100;
    DBHelper MyDB = new DBHelper(this);
    Uri ImageFilePath;
    Bitmap ImageToStore,Images;
    private ByteArrayOutputStream objectByteArrayOutputStream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        nama = (EditText) findViewById(R.id.nama_tamu_edit);
        keperluan = (EditText) findViewById(R.id.keperluan_edit);
        perusahaan = (EditText) findViewById(R.id.perusahaan_edit);
        jammasuk = (EditText) findViewById(R.id.jam_masuk_edit);
        jamkeluar = (EditText) findViewById(R.id.jam_keluar_edit);
        save = (Button) findViewById(R.id.btn_save);
        fotoketerangan = (ImageView) findViewById(R.id.keterangan);

        if (getSupportActionBar()!=null) {
            getSupportActionBar().hide();
        }

        String idEdit = getIntent().getStringExtra("id_edit");
        int id = Integer.parseInt(idEdit);

        Cursor tampilData = MyDB.KirimTamu(id);
        nm = tampilData.getString(1);
        kp = tampilData.getString(2);
        pr = tampilData.getString(3);
        jm = tampilData.getString(4);
        jk = tampilData.getString(5);

        nama.setText(nm);
        keperluan.setText(kp);
        perusahaan.setText(pr);
        jammasuk.setText(jm);
        jamkeluar.setText(jk);
        byte[] image = tampilData.getBlob(6);
        Images = BitmapFactory.decodeByteArray(image,0,image.length);
        fotoketerangan.setImageBitmap(Images);

    }

    public void pilihGambar(View view) {
        try {
            Intent pilih = new Intent();
            pilih.setType("image/*");

            pilih.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(pilih,KUALITY);
        }
        catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == KUALITY && resultCode == RESULT_OK && data != null && data.getData() != null) {
                ImageFilePath = data.getData();
                ImageToStore = MediaStore.Images.Media.getBitmap(getContentResolver(), ImageFilePath);
                fotoketerangan.setImageBitmap(ImageToStore);
            }
        } catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void save(View view) {
        try {
            String Nama = nama.getText().toString();
            String keper = keperluan.getText().toString();
            String perus = perusahaan.getText().toString();
            String masuk = jammasuk.getText().toString();
            String keluar = jamkeluar.getText().toString();

            String idEdit = getIntent().getStringExtra("id_edit");
            int id = Integer.parseInt(idEdit);

            if (!nama.getText().toString().isEmpty()&&!keperluan.getText().toString().isEmpty()&&!perusahaan.getText().toString().isEmpty()&&!jammasuk.getText().toString().isEmpty()&&!jamkeluar.getText().toString().isEmpty()) {

               Boolean check = MyDB.checkTamu(id,Nama,keper,perus,masuk,keluar);
               Boolean checknama = MyDB.checknama(Nama);
               Boolean checkkeper = MyDB.checkkeperluan(keper);
               Boolean checkperus = MyDB.checkperusahaan(perus);
               Boolean checkmasuk = MyDB.checkmasuk(masuk);
               Boolean checkkeluar = MyDB.checkkeluar(keluar);

               if (checknama==true&&checkkeper==true&&checkperus==true&&checkmasuk==true&&checkkeluar==true&&ImageToStore==null) {
                   Toast.makeText(this, "Nothing To Change", Toast.LENGTH_SHORT).show();
               }
               else {
                   if (ImageToStore!=null) {
                       Boolean checkupdate = MyDB.updateTamu(id,Nama,keper,perus,masuk,keluar,ImageToStore);
                       if (checkupdate==true) {
                           Toast.makeText(this, "Berhasil Diedit", Toast.LENGTH_SHORT).show();
                           Intent masukHome = new Intent(EditActivity.this,HomeActivity.class);
                           startActivity(masukHome);
                       }
                       else {
                           Toast.makeText(this, "Gagal Diedit", Toast.LENGTH_SHORT).show();
                       }
                   }
                   else {
                       ImageToStore = Images;
                       Boolean checkupdate = MyDB.updateTamu(id,Nama,keper,perus,masuk,keluar,ImageToStore);
                       if (checkupdate==true) {
                           Toast.makeText(this, "Berhasil Diedit", Toast.LENGTH_SHORT).show();
                           Intent masukHome = new Intent(EditActivity.this,HomeActivity.class);
                           startActivity(masukHome);
                       }
                       else {
                           Toast.makeText(this, "Gagal Diedit", Toast.LENGTH_SHORT).show();
                       }
                   }
               }
            }
            else {
                Toast.makeText(this, "Silahkan Lengkapi Data Diatas!", Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}