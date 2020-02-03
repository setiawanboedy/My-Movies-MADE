package com.attafakkur.myfilmstorage.entity;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import org.jetbrains.annotations.NotNull;

import static com.attafakkur.myfilmstorage.db.DatabaseContract.TvShowColumns.IMAGE_TV;
import static com.attafakkur.myfilmstorage.db.DatabaseContract.TvShowColumns.KEY_ID_TV;
import static com.attafakkur.myfilmstorage.db.DatabaseContract.TvShowColumns.LANGUAGE_TV;
import static com.attafakkur.myfilmstorage.db.DatabaseContract.TvShowColumns.POPULARITY_TV;
import static com.attafakkur.myfilmstorage.db.DatabaseContract.TvShowColumns.RELEASE_DATE_TV;
import static com.attafakkur.myfilmstorage.db.DatabaseContract.TvShowColumns.SYNOPSIS_TV;
import static com.attafakkur.myfilmstorage.db.DatabaseContract.TvShowColumns.TITLE_TV;
import static com.attafakkur.myfilmstorage.db.DatabaseContract.TvShowColumns.VOTE_TV;
import static com.attafakkur.myfilmstorage.db.DatabaseContract.getColumnInt;
import static com.attafakkur.myfilmstorage.db.DatabaseContract.getColumnString;

/**
 * @author geek-dev
 */
public class MyTvShow implements Parcelable {
    public static final Creator<MyTvShow> CREATOR = new Creator<MyTvShow>() {
        @Override
        public MyTvShow createFromParcel(Parcel in) {
            return new MyTvShow(in);
        }

        @Override
        public MyTvShow[] newArray(int size) {
            return new MyTvShow[size];
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

    public MyTvShow() {

    }

    private MyTvShow(Parcel in) {
        id = in.readInt();
        image = in.readString();
        title = in.readString();
        date = in.readString();
        description = in.readString();
        language = in.readString();
        popularity = in.readString();
        voteCount = in.readString();
    }

    public MyTvShow(Cursor cursor) {
        this.id = getColumnInt(cursor, KEY_ID_TV);
        this.image = getColumnString(cursor, IMAGE_TV);
        this.title = getColumnString(cursor, TITLE_TV);
        this.date = getColumnString(cursor, RELEASE_DATE_TV);
        this.voteCount = getColumnString(cursor, VOTE_TV);
        this.language = getColumnString(cursor, LANGUAGE_TV);
        this.popularity = getColumnString(cursor, POPULARITY_TV);
        this.description = getColumnString(cursor, SYNOPSIS_TV);
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

    @NotNull
    @Override
    public String toString() {
        return "MyTvShow{" +
                "id = '" + id + "" +
                ",poster_path = '" + image + "" +
                ",title = '" + title + "" +
                ",release_date = '" + date + "" +
                ",vote_average = '" + voteCount + "" +
                ",popularity = '" + popularity + "" +
                ",original_language = '" + language + "" +
                ",overview = '" + description + "" +
                "}";
    }
}
