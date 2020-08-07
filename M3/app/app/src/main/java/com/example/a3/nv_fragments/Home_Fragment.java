package com.example.a3.nv_fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.a3.DataBase.WatchListDatabase;
import com.example.a3.DataBase.WatchListViewModel;
import com.example.a3.Login;
import com.example.a3.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

//home screen
public class Home_Fragment extends Fragment {
    View menu_view;
    private ListView movieList;
    private List<HashMap<String, String>> movieListArray = new ArrayList<>();
    public static WatchListDatabase watchListDatabase;
    public static WatchListViewModel watchListViewModel;


    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        menu_view = inflater.inflate(R.layout.fragment_home, container, false);
        movieList = menu_view.findViewById(R.id.lv_movie);
        watchListViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(WatchListViewModel.class);//new ViewModelProvider.NewInstanceFactory()
        watchListViewModel.initalizeVars(getActivity().getApplication());
        SharedPreferences sp = getActivity().getSharedPreferences(String.valueOf(Login.login_id), Context.MODE_PRIVATE);
        String fname = sp.getString("fisrtname", "");
        TextView tv_welcome = menu_view.findViewById(R.id.tv_welcome);
        TextView tv_date = menu_view.findViewById(R.id.tv_date);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date(System.currentTimeMillis());
        tv_welcome.setText("Welcome " + fname);
        tv_date.setText(simpleDateFormat.format(date));
        new AsynGetTop5().execute();

        return menu_view;
    }


    private class AsynGetTop5 extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            String result = "";
            URL url = null;
            HttpURLConnection connect = null;
            final String methodPath = "rest.memoir/findTopFiveInRecentByPid/";
            try {
                url = new URL(Login.BASE_URL + methodPath + Login.login_id);
                connect = (HttpURLConnection) url.openConnection();
                connect.setReadTimeout(10000);
                connect.setConnectTimeout(15000);
                connect.setRequestMethod("GET");
                connect.setRequestProperty("Content-Type", "application/json");
                connect.setRequestProperty("Accept", "application/json");
                Scanner scanner = new Scanner(connect.getInputStream());
                while (scanner.hasNextLine()) {
                    result += scanner.nextLine();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                connect.disconnect();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            JSONArray jsonArray = null;
            try {
                jsonArray = new JSONArray(result);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                    HashMap<String, String> map = new HashMap<>();
                    map.put("Movie Name", jsonObject.getString("Movie name"));
                    map.put("Release Date", jsonObject.getString("Release Date"));
                    map.put("Movie Score", jsonObject.getString("Movie Score"));
                    movieListArray.add(map);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String[] colHead = new String[]{"Movie Name", "Release Date", "Movie Score"};
            int[] dataCell = new int[]{R.id.tv_mnList, R.id.tv_rdList, R.id.tv_msList};
            SimpleAdapter simpleAdapter = new SimpleAdapter(Home_Fragment.this.getActivity(), movieListArray, R.layout.fragment_home_mlistview, colHead, dataCell);
            movieList.setAdapter(simpleAdapter);
        }

    }

}

