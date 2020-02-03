package com.attafakkur.myfilmapi.model;

import android.os.Parcel;
import android.os.Parcelable;

public class MyMovies implements Parcelable {

    private String image;
    private String title;
    private String date;
    private String description;
    private String language;
    private Double popularity;
    private int voteCount;

    public MyMovies() {

    }

    protected MyMovies(Parcel in) {
        image = in.readString();
        title = in.readString();
        date = in.readString();
        description = in.readString();
        language = in.readString();
        if (in.readByte() == 0) {
            popularity = null;
        } else {
            popularity = in.readDouble();
        }
        voteCount = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(image);
        dest.writeString(title);
        dest.writeString(date);
        dest.writeString(description);
        dest.writeString(language);
        if (popularity == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(popularity);
        }
        dest.writeInt(voteCount);
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

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }
}
