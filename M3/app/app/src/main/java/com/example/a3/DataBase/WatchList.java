package com.example.a3.DataBase;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class WatchList {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "watchID")
    public int watchID;

    @ColumnInfo(name = "movieName")
    public String movieName;

    @ColumnInfo(name = "releaseDate")
    public String releaseDate;

    @ColumnInfo(name = "addData")
    public String addData;

    @ColumnInfo(name = "addTime")
    public String addTime;

    @ColumnInfo(name = "login_id")
    public String login_id;

    @ColumnInfo(name = "movieID")
    public String movieID;

    public WatchList(String movieName, String releaseDate, String addData, String addTime, String login_id, String movieID) {

        this.movieName = movieName;
        this.releaseDate = releaseDate;
        this.addData = addData;
        this.addTime = addTime;
        this.login_id = login_id;
        this.movieID = movieID;
    }

    public int getWatchID() {
        return watchID;
    }

    public void setWatchID(int watchID) {
        this.watchID = watchID;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getAddData() {
        return addData;
    }

    public void setAddData(String addData) {
        this.addData = addData;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getLogin_id() {
        return login_id;
    }

    public void setLogin_id(String login_id) {
        this.login_id = login_id;
    }

    public String getMovieID() {
        return movieID;
    }

    public void setMovieID(String movieID) {
        this.movieID = movieID;
    }
}
