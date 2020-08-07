package com.example.a3.DataBase;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class WatchListViewModel extends ViewModel {
    private  WatchListRepository watchListRepository;
    private MutableLiveData<List<WatchList>> allWatchList;

    public WatchListViewModel(){
        allWatchList = new MutableLiveData<List<WatchList>>();
    }
    public void setWatchLists(List<WatchList> watchLists) {
        allWatchList.setValue(watchLists);
    }
    public LiveData<List<WatchList>> getAllWatchLists(String login_id){
        return watchListRepository.getAllWatchLists(login_id);
    }
    public List<WatchList> getAllWatchListByID(String login_id){
        return watchListRepository.getAllWatchListByID(login_id);
    }
    public void initalizeVars(Application application){
        watchListRepository = new WatchListRepository(application);
    }
    public void insert(WatchList watchList) {
        watchListRepository.insert(watchList);
    }
    public void insertAll(WatchList... watchLists) {
        watchListRepository.insertAll(watchLists);
    }
    public void deleteAll() {
        watchListRepository.deleteAll();
    }
    public void delete(WatchList watchList) {
        watchListRepository.delete(watchList);
    }
    public void update(WatchList... watchLists) {
        watchListRepository.updateWatchLists(watchLists);
    }
    public void deleteByWatchID(int watchID){
        watchListRepository.deleteByWatchID(watchID);
    }
    public WatchList findByWatchID(int watchID){
        return watchListRepository.findByWatchID(watchID);
    }

}
