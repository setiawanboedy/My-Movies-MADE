package com.attafakkur.myfavoritemovie.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author geek-dev
 */
public class MyMovies implements Parcelable {

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
    private int id;
    private String image;
    private String title;
    private String date;
    private String description;
    private String language;
    private String popularity;
    private String voteCount;

    public MyMovies() {

    }

    private MyMovies(Parcel in) {
        id = in.readInt();
        image = in.readString();
        title = in.readString();
        date = in.readString();
        description = in.readString();
        language = in.readString();
        popularity = in.readString();
        voteCount = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(image);
        dest.writeString(title);
        dest.writeString(date);
        dest.writeString(description);
        dest.writeString(language);
        dest.writeString(popularity);
        dest.writeString(voteCount);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(String voteCount) {
        this.voteCount = voteCount;
    }

    @Override
    public String toString() {
        return "MyMovies{" +
                "id = '" + id + "" +
                ",poster_path = '" + image + "" +
                ",title = '" + title + "" +
                ",release_date = '" + date + "" +
                ",vote_average = '" + voteCount + "" +
                ",original_language = '" + language + "" +
                ",popularity = '" + popularity + "" +
                ",overview = '" + description + "" +
                "}";
    }
}
