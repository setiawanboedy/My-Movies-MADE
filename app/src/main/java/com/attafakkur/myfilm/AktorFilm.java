package com.attafakkur.myfilm;

import android.os.Parcel;
import android.os.Parcelable;

public class AktorFilm implements Parcelable {
    private int gambar;
    private int img1;
    private int img2;
    private int img3;
    private String nama;
    private String tanggal;
    private String deskripsi;
    private String durasi;
    private String bahasa;
    private String genre;

    public AktorFilm() {

    }

    protected AktorFilm(Parcel in) {
        gambar = in.readInt();
        img1 = in.readInt();
        img2 = in.readInt();
        img3 = in.readInt();
        nama = in.readString();
        tanggal = in.readString();
        deskripsi = in.readString();
        durasi = in.readString();
        bahasa = in.readString();
        genre = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(gambar);
        dest.writeInt(img1);
        dest.writeInt(img2);
        dest.writeInt(img3);
        dest.writeString(nama);
        dest.writeString(tanggal);
        dest.writeString(deskripsi);
        dest.writeString(durasi);
        dest.writeString(bahasa);
        dest.writeString(genre);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AktorFilm> CREATOR = new Creator<AktorFilm>() {
        @Override
        public AktorFilm createFromParcel(Parcel in) {
            return new AktorFilm(in);
        }

        @Override
        public AktorFilm[] newArray(int size) {
            return new AktorFilm[size];
        }
    };

    public int getGambar() {
        return gambar;
    }

    public void setGambar(int gambar) {
        this.gambar = gambar;
    }

    public int getImg1() {
        return img1;
    }

    public void setImg1(int img1) {
        this.img1 = img1;
    }

    public int getImg2() {
        return img2;
    }

    public void setImg2(int img2) {
        this.img2 = img2;
    }

    public int getImg3() {
        return img3;
    }

    public void setImg3(int img3) {
        this.img3 = img3;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getDurasi() {
        return durasi;
    }

    public void setDurasi(String durasi) {
        this.durasi = durasi;
    }

    public String getBahasa() {
        return bahasa;
    }

    public void setBahasa(String bahasa) {
        this.bahasa = bahasa;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
