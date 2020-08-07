package com.example.a3.DataBase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface WatchListDAO {

    @Query("SELECT * FROM watchlist")
    List<WatchList> getAll();

    @Query("SELECT * FROM watchlist WHERE watchID = :watchID LIMIT 1")
    WatchList findByWatchID(int watchID);

    @Insert
    void insertAll(WatchList... watchList);

    @Insert
    long insert(WatchList watchLists);

    @Delete
    void delete(WatchList watchList);

    @Update(onConflict = REPLACE)
    void updateWatchLists(WatchList... watchLists);

    @Query("DELETE FROM watchlist")
    void deleteAll();

    @Query("SELECT * FROM watchlist WHERE login_id = :login_id")
    LiveData<List<WatchList>> findByloginid(String login_id);

    @Query("SELECT * FROM watchlist WHERE login_id = :login_id")
    List<WatchList> findByloginidList(String login_id);

    @Query("DELETE FROM WatchList WHERE watchID = :watchID")
    void deleteByWatchID(int watchID);



}
