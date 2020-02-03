package com.attafakkur.myfilm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity{

    private String[] datanama,datatanggal,datadeskrip,datadurasi,databahasa,datagenre;
    private TypedArray imgFilm;
    private AdapterMyFilm adapterMyFilm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapterMyFilm = new AdapterMyFilm(this);
        ListView listfilm = findViewById(R.id.listFilm);
        listfilm.setAdapter(adapterMyFilm);

        imgFilm = getResources().obtainTypedArray(R.array.data_Photo);
        datanama = getResources().getStringArray(R.array.data_Nama);
        datatanggal = getResources().getStringArray(R.array.data_tgl);
        datadeskrip = getResources().getStringArray(R.array.data_Deskripsi);
        datadurasi = getResources().getStringArray(R.array.data_durasi);
        databahasa = getResources().getStringArray(R.array.data_bahasa);
        datagenre = getResources().getStringArray(R.array.data_genre);

        final ArrayList<AktorFilm> films = new ArrayList<>();
        for(int i = 0; i < datanama.length; i++){
            AktorFilm aktorFilm = new AktorFilm();
            aktorFilm.setGambar(imgFilm.getResourceId(i,-1));
            aktorFilm.setNama(datanama[i]);
            aktorFilm.setTanggal(datatanggal[i]);
            aktorFilm.setDeskripsi(datadeskrip[i]);
            aktorFilm.setDurasi(datadurasi[i]);
            aktorFilm.setBahasa(databahasa[i]);
            aktorFilm.setGenre(datagenre[i]);
            Random random = new Random();
            int j = random.nextInt((9-7)+7);
            int k = random.nextInt((3-0)+0);
            int l = random.nextInt((6-4)+4);
            aktorFilm.setImg1(imgFilm.getResourceId(j,-1));
            aktorFilm.setImg2(imgFilm.getResourceId(k,-1));
            aktorFilm.setImg3(imgFilm.getResourceId(l,-1));
            films.add(aktorFilm);
        }
        adapterMyFilm.setFilms(films);

        listfilm.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, DetailMyFilm.class);
                String key_film="myfilm";
                intent.putExtra(key_film,films.get(position));
                startActivity(intent);
            }
        });

    }
}
