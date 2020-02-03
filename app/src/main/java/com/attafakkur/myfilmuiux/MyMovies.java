package com.attafakkur.myfilmuiux;

import android.os.Parcel;
import android.os.Parcelable;

public class MyMovies implements Parcelable {

    private String image;
    private String title;
    private String date;
    private String description;
    private String language;
    private String runtime;
    private String genre;
    private String season;

    public MyMovies() {

    }

    protected MyMovies(Parcel in) {
        image = in.readString();
        title = in.readString();
        date = in.readString();
        description = in.readString();
        language = in.readString();
        runtime = in.readString();
        genre = in.readString();
        season = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(image);
        dest.writeString(title);
        dest.writeString(date);
        dest.writeString(description);
        dest.writeString(language);
        dest.writeString(runtime);
        dest.writeString(genre);
        dest.writeString(season);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MyMovies> CREATOR = new Creator<MyMovies>() {
        @Override
        public MyMovies createFromParcel(Parcel in) {
            return new MyMovies(in);
        }

        @Override
        public MyMovies[] newArray(int size) {
            return new MyMovies[size];
        }
    };

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }
}
