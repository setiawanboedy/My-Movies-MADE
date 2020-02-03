package com.attafakkur.myfilm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Parcelable;
import android.text.method.TextKeyListener;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class DetailMyFilm extends AppCompatActivity {

    private String nama,tgl,desk,durasi,bahasa,genre;
    private int img,img1,img2,img3;
    TextView judul,reles,deskrip,lama,langua,genere;
    ImageView imgFilm,imageView1,imageView2,imageView3;
    public static final String key_film = "myfilm";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_my_film);

        judul = (TextView)findViewById(R.id.namafilm);
        reles = (TextView)findViewById(R.id.tanggalrilis);
        deskrip = (TextView)findViewById(R.id.deskripsi);
        lama = (TextView)findViewById(R.id.durasi);
        langua = (TextView)findViewById(R.id.bahasa);
        genere = (TextView)findViewById(R.id.genre);
        imgFilm = (ImageView)findViewById(R.id.gambarfilm);
        imageView1 = (ImageView)findViewById(R.id.imageView);
        imageView2 = (ImageView)findViewById(R.id.imageView2);
        imageView3 = (ImageView)findViewById(R.id.imageView3);

        AktorFilm aktor = this.getIntent().getParcelableExtra(key_film);
        nama = aktor.getNama();
        judul.setText(nama);
        tgl = aktor.getTanggal();
        reles.setText(tgl);
        desk = aktor.getDeskripsi();
        deskrip.setText(desk);
        durasi = aktor.getDurasi();
        lama.setText(durasi);
        bahasa = aktor.getBahasa();
        langua.setText(bahasa);
        genre = aktor.getGenre();
        genere.setText(genre);
        img = aktor.getGambar();
        imgFilm.setImageResource(img);
        img1 = aktor.getImg1();
        imageView1.setImageResource(img1);
        img2 = aktor.getImg2();
        imageView2.setImageResource(img2);
        img3 = aktor.getImg3();
        imageView3.setImageResource(img3);

    }
}
