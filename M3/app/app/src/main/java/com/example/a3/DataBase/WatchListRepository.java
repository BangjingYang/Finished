package com.example.a3.DataBase;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class WatchListRepository {
        private WatchListDAO dao;
        private LiveData<List<WatchList>> allWatchLists;
        private List<WatchList> watchLists;
        private WatchList watchList;
        public WatchListRepository(Application application){
            WatchListDatabase db = WatchListDatabase.getInstance(application);
            dao = db.watchListDAO();
        }
        public LiveData<List<WatchList>> getAllWatchLists(String login_id) {
            allWatchLists=dao.findByloginid(login_id);
            return allWatchLists;
        }
        public  List<WatchList> getAllWatchListByID(String login_id){
            watchLists = dao.findByloginidList(login_id);
            return watchLists;
        }
        public void insert(final WatchList watchList){
            WatchListDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.insert(watchList);
            } });
        }
        public void deleteAll(){
            WatchListDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.deleteAll(); }
        }); }
        public void delete(final WatchList watchList){
            WatchListDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.delete(watchList); }
        }); }
        public void deleteByWatchID(final int watchID){
            WatchListDatabase.databaseWriteExecutor.execute(new Runnable() {
                @Override
                public void run() { dao.deleteByWatchID(watchID); }
            });
        }
        public void insertAll(final WatchList... watchLists){
            WatchListDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() { dao.insertAll(watchLists); }
        }); }
        public void updateWatchLists(final WatchList... watchLists){
            WatchListDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() { dao.updateWatchLists(watchLists); }
        }); }

        public WatchList findByWatchID(final int watchID){
            WatchListDatabase.databaseWriteExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    WatchList runWatchList = dao.findByWatchID(watchID);
                    setWatchList(runWatchList);
                }
            });

            return watchList;
        }
        public void setWatchList(WatchList watchList){
            this.watchList = watchList;
        }
}
