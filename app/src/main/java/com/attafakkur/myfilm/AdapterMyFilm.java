package com.attafakkur.myfilm;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterMyFilm extends BaseAdapter {

    private Context context;
    private ArrayList<AktorFilm> films;

    public void setFilms(ArrayList<AktorFilm> films) {
        this.films = films;
    }

    public AdapterMyFilm(Context context) {
        this.context = context;
        films = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return films.size();
    }

    @Override
    public Object getItem(int position) {
        return films.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

            convertView = LayoutInflater.from(context).inflate(R.layout.aktorfilm, parent, false);

            TextView judul,tanggal,desk,durasi;
            ImageView gambarfilm;


            judul = convertView.findViewById(R.id.namafilm);
            tanggal = convertView.findViewById(R.id.tanggalrilis);
            desk = convertView.findViewById(R.id.deskripsi);
            durasi = convertView.findViewById(R.id.durasi);
            gambarfilm = convertView.findViewById(R.id.gambarfilm);

            AktorFilm aktorFilm = (AktorFilm) getItem(position);
            judul.setText(aktorFilm.getNama());
            tanggal.setText(aktorFilm.getTanggal());
            desk.setText(aktorFilm.getDeskripsi());
            durasi.setText(aktorFilm.getDurasi());
            gambarfilm.setImageResource(aktorFilm.getGambar());

        return convertView;
    }
}
