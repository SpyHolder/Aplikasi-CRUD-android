package com.example.project2part2;

import android.app.Activity;
import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class Adapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<Model> lists;

    public Adapter() {
    }

    public Adapter(Activity activity, List<Model> lists) {
        this.activity = activity;
        this.lists = lists;
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int i) {
        return lists.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        try {
            if (inflater==null) {
                inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }
            if (view==null&&inflater!=null) {
                view = inflater.inflate(R.layout.list_user,null);
            }
            if (view!=null) {
                TextView namaTamu = view.findViewById(R.id.nama_tamu);
                TextView keperluanTamu = view.findViewById(R.id.keperluan_tamu);
                TextView perusahaanTamu = view.findViewById(R.id.perusahaan_tamu);

                Model data = lists.get(i);
                namaTamu.setText("Nama          : "+data.getNamaTamu());
                keperluanTamu.setText("Keperluan    : "+data.getKeperluan());
                perusahaanTamu.setText("Perusahaan  : "+data.getPerusahaan());
            }
        }
        catch (Exception ex) {
            Toast.makeText(activity, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return view;
    }
}
