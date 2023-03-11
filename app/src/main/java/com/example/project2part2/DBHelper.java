package com.example.project2part2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.view.Display;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    Context context;
    private byte[] imageByte;
    private ByteArrayOutputStream objectByteArrayOutputStream;

    public DBHelper(Context context) {
        super(context, "tamu.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        MyDB.execSQL("CREATE TABLE user (id INTEGER PRIMARY KEY AUTOINCREMENT,username TEXT,password TEXT)");
        MyDB.execSQL("CREATE TABLE tamu (id INTEGER PRIMARY KEY AUTOINCREMENT,namatamu TEXT,keperluan TEXT,perusahaan TEXT,jammasuk TEXT,jamkeluar TEXT,fotoketerangan BLOB)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
        MyDB.execSQL("DROP TABLE IF EXISTS user");
        MyDB.execSQL("DROP TABLE IF EXISTS tamu");
        onCreate(MyDB);
    }

    public Boolean checkusername(String username) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("SELECT * FROM user WHERE username = ?",new String[] {username});
        if (cursor.getCount()>0) return true;
        else return false;
    }
    public Boolean checkpassword(String password) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("SELECT * FROM user WHERE password = ?",new String[] {password});
        if (cursor.getCount()>0) return true;
        else return false;
    }
    public Boolean insetLogin(String username,String passowrd) {
        long check;
        try {
            SQLiteDatabase MyDB = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("username",username);
            values.put("password",passowrd);
            check = MyDB.insert("user",null,values);
            if (check!=-1) return true;
            else return false;
        }
        catch (Exception ex) {
            Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public Boolean updateLogin(String username,String password) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("password",password);
        long result = MyDB.update("user",values,"username = ?",new String[] {username});
        if (result == -1) return false;
        else return true;
    }
//<---------------------------------------------------------------------------------------------------------------------------------->
    public Boolean insertTamu(String nama, String keperluan, String perusahaan, String masuk, String keluar,Bitmap foto) {
        try {
            SQLiteDatabase MyDB = this.getWritableDatabase();

            Bitmap imageToStore = foto;
            objectByteArrayOutputStream = new ByteArrayOutputStream();
            imageToStore.compress(Bitmap.CompressFormat.JPEG,100,objectByteArrayOutputStream);
            imageByte = objectByteArrayOutputStream.toByteArray();

            ContentValues values = new ContentValues();
            values.put("namatamu",nama);
            values.put("keperluan",keperluan);
            values.put("perusahaan",perusahaan);
            values.put("jammasuk",masuk);
            values.put("jamkeluar",keluar);
            values.put("fotoketerangan",imageByte);
            long check = MyDB.insert("tamu",null,values);
            if (check != -1) {
                return true;
            }
            else {
                return false;
            }
        }
        catch (Exception ex) {
            Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    public void getAll() {
        List<Model> list = new ArrayList<>();
        try {
            SQLiteDatabase MyDB = this.getWritableDatabase();
            Cursor cursor = MyDB.rawQuery("SELECT * FROM tamu",null);
            if (cursor.moveToFirst()) {
                do {
                    String id = cursor.getString(0);
                    String nama = cursor.getString(1);
                    String keperluan = cursor.getString(2);
                    String perusahaan = cursor.getString(3);

                    Model model = new Model();
                    model.setId(id);
                    model.setNamaTamu(nama);
                    model.setPerusahaan(perusahaan);
                    model.setKeperluan(keperluan);
                    list.add(model);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        catch (Exception ex) {
            Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteTamu(int id) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        String QUERY = "DELETE FROM tamu WHERE id =" +id;
        MyDB.execSQL(QUERY);
    }

    public Cursor KirimTamu(int id) {
        try {
            SQLiteDatabase MyDB = this.getWritableDatabase();
            String QUERY = "SELECT * FROM tamu WHERE id =" + id;
            Cursor cursor = MyDB.rawQuery(QUERY,null);
            cursor.moveToFirst();
            return cursor;
        }
        catch (Exception ex) {
            Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
            return  null;
        }
    }

    public Boolean updateTamu(int id,String nama,String keperluan,String perusahaan,String masuk,String keluar,Bitmap foto) {
        try {
            SQLiteDatabase MyDB = this.getWritableDatabase();

            Bitmap imageToStore = foto;
            objectByteArrayOutputStream = new ByteArrayOutputStream();
            imageToStore.compress(Bitmap.CompressFormat.JPEG,100,objectByteArrayOutputStream);
            imageByte = objectByteArrayOutputStream.toByteArray();

            String Id = Integer.toString(id);

           ContentValues values = new ContentValues();
           values.put("namatamu",nama);
           values.put("keperluan",keperluan);
           values.put("perusahaan",perusahaan);
           values.put("jammasuk",masuk);
           values.put("jamkeluar",keluar);
           values.put("fotoketerangan",imageByte);

           String selection = "id = ?";
           String[] selectionArgs = {Id};

           MyDB.update("tamu",values,selection,selectionArgs);
            return true;
        }
        catch (Exception ex) {
            Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public Boolean selectAdmin(String username) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("SELECT * FROM tamu WHERE username = ?",new String[] {username});
        cursor.moveToFirst();
        if (cursor.getCount()>0){
            return true;
        }
        else {
            return false;
        }
    }

    public Boolean checkTamu(int id,String nama,String keperluan,String perusahaan,String masuk,String keluar) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("SELECT * FROM tamu WHERE namatamu = ? AND keperluan = ? AND perusahaan = ? AND jammasuk = ? AND jamkeluar = ?",new String[]{nama,keperluan,perusahaan,masuk,keluar});
        if (cursor.getCount()>0) {
            return true;
        }
        else {
            return false;
        }
    }

//    =============================================================

    public Boolean checknama (String nama) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("SELECT * FROM tamu WHERE namatamu = ?",new String[] {nama});
        if (cursor.getCount()>0) {
            return true;
        }
        else {
            return false;
        }
    }
    public Boolean checkkeperluan (String nama) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("SELECT * FROM tamu WHERE keperluan = ?",new String[] {nama});
        if (cursor.getCount()>0) {
            return true;
        }
        else {
            return false;
        }
    }
    public Boolean checkperusahaan (String nama) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("SELECT * FROM tamu WHERE perusahaan = ?",new String[] {nama});
        if (cursor.getCount()>0) {
            return true;
        }
        else {
            return false;
        }
    }
    public Boolean checkmasuk (String nama) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("SELECT * FROM tamu WHERE jammasuk = ?",new String[] {nama});
        if (cursor.getCount()>0) {
            return true;
        }
        else {
            return false;
        }
    }
    public Boolean checkkeluar (String nama) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("SELECT * FROM tamu WHERE jamkeluar = ?",new String[] {nama});
        if (cursor.getCount()>0) {
            return true;
        }
        else {
            return false;
        }
    }

//    =============================================================

}
