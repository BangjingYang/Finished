package com.example.a3.nv_fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;


import com.example.a3.DataBase.WatchList;
import com.example.a3.Login;
import com.example.a3.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WatchList_Fragment extends Fragment {
    private View vwatchlist;

    private List<HashMap<String,String>> watchListArray = new ArrayList<>();
    private ListView lv_watchlist;
//    private WatchList watchList;
    private SimpleAdapter simpleAdapter;
    private String movieID;
    private String movieName;
    private String watchID;
    private String releasedate;
    private TextView ml_tv_select;
    private Button ml_b_viewmovie;
    private Button ml_b_deletemovie;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        vwatchlist = inflater.inflate(R.layout.fragment_movielist, container, false);
        lv_watchlist = vwatchlist.findViewById(R.id.ml_lv);
        ml_tv_select = vwatchlist.findViewById(R.id.ml_tv_select);
        ml_tv_select.setVisibility(View.INVISIBLE);
        ml_b_viewmovie = vwatchlist.findViewById(R.id.ml_b_viewmovie);
        ml_b_viewmovie.setVisibility(View.INVISIBLE);
        ml_b_deletemovie = vwatchlist.findViewById(R.id.ml_b_deletemovie);
        ml_b_deletemovie.setVisibility(View.INVISIBLE);
        Home_Fragment.watchListViewModel.getAllWatchLists(String.valueOf(Login.login_id)).observe(getViewLifecycleOwner(), new Observer<List<WatchList>>() {
            @Override
            public void onChanged(List<WatchList> watchLists) {
                watchListArray.clear();
                for(WatchList w : watchLists){
                    HashMap<String,String> map = new HashMap<>();
                    map.put("moviename",w.getMovieName());

                    map.put("movieid",w.getMovieID());
                    map.put("releasedate",w.getReleaseDate());
                    map.put("adddate",w.getAddData());
                    map.put("watchid",String.valueOf(w.getWatchID()));
//                    map.put("addtime",w.getAddTime());
                    watchListArray.add(map);
                }
                String[] colHead = new String[]{"moviename", "releasedate", "adddate", "watchid", "movieid"};
                int[] dataCell1 = new int[]{R.id.mv_lv_tv_moviename, R.id.mv_lv_tv_releasedate, R.id.mv_lv_tv_adddate, R.id.mv_lv_tv_watchid, R.id.mv_lv_tv_movieid};
                simpleAdapter = new SimpleAdapter(WatchList_Fragment.this.getActivity(), watchListArray, R.layout.fragment_movielist_listview, colHead, dataCell1);
                lv_watchlist.setAdapter(simpleAdapter);
            }
        });
        lv_watchlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                movieID = watchListArray.get(position).get("movieid");
                watchID = watchListArray.get(position).get("watchid");
                movieName = watchListArray.get(position).get("moviename");
                releasedate = watchListArray.get(position).get("releasedate");
                ml_tv_select.setText(movieName + " is selected.");
                ml_tv_select.setVisibility(View.VISIBLE);
                ml_b_viewmovie.setVisibility(View.VISIBLE);
                ml_b_deletemovie.setVisibility(View.VISIBLE);
            }
        });
        ml_b_viewmovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("message", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("movieID",movieID);
                editor.putString("moviename",movieName);
                editor.putString("releasedate",releasedate);
                editor.putString("flag","false");
                editor.commit();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, new MovieView_Fragment()).commit();
            }
        });

        ml_b_deletemovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(WatchList_Fragment.this.getActivity());
                alert.setTitle("Make sure you want to delect this movie from your Watch List");
                alert.setPositiveButton("Comfirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Home_Fragment.watchListViewModel.deleteByWatchID(Integer.parseInt(watchID));
                        ml_tv_select.setVisibility(View.INVISIBLE);
                        ml_b_viewmovie.setVisibility(View.INVISIBLE);
                        ml_b_deletemovie.setVisibility(View.INVISIBLE);
                        Toast.makeText(vwatchlist.getContext(),"Moive has been delect from your Watch List", Toast.LENGTH_LONG).show();
                    }
                });
                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alert.show();
            }
        });










        return vwatchlist;
    }


}

