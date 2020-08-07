package com.example.a3.nv_fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Switch;

import androidx.fragment.app.Fragment;

import com.example.a3.API.MoiveSearchAPI;
import com.example.a3.Login;
import com.example.a3.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Memoir_Fragment extends Fragment {
    private View vmemoir;
    private List<HashMap<String,Object>> memoirList = new ArrayList<>();


    private List<HashMap<String,Object>> genreList = new ArrayList<>();

    private ListView lv_m_meoir;
    private HashMap<String, Object> map = new HashMap();
    private List<String> movieNameList = new ArrayList<>();;

    private int count = 0;
    private List<String> release = new ArrayList<>();
    private List<String> genreNameList = new ArrayList<>();;

    private boolean finish = false;
    private Spinner sp_m_sorting;
    private Spinner sp_m_genre;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        vmemoir = inflater.inflate(R.layout.fragment_memoir, container, false);
        genreNameList.add("All");
        lv_m_meoir = vmemoir.findViewById(R.id.lv_m_meoir);

        sp_m_genre = vmemoir.findViewById(R.id.sp_m_genre);
        sp_m_sorting = vmemoir.findViewById(R.id.sp_m_sorting);
        final ArrayAdapter<String> spinnerAdapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, genreNameList);
        spinnerAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_m_genre.setAdapter(spinnerAdapter1);
//        sp_m_genre.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String selectedGenre = parent.getItemAtPosition(position).toString();
//
//            }
////            @Override
////            public void onNothingSelected(AdapterView<?> parent) {
////
////            }
//        });

        List<String> sorting = new ArrayList<>();
        sorting.add("Watch Date");
        sorting.add("User Score");
        sorting.add("Release Date");


        final ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, sorting);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_m_sorting.setAdapter(spinnerAdapter);
        sp_m_sorting.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        new AsyncSortBy().execute("watchDate");
                        break;
                    case 1:
                        new AsyncSortBy().execute("userRating");
                        break;
                    case 2:
                        new AsyncSortBy().execute("releaseDate");
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        lv_m_meoir.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LinearLayout linearLayout =parent.getAdapter().getView(position,view,parent).findViewById(R.id.ll_ml);
                if(linearLayout.getVisibility() == View.GONE){
                    linearLayout.setVisibility(View.VISIBLE);
                }else{
                    linearLayout.setVisibility(View.GONE);
                }
            }
        });




        return vmemoir;
    }

    private class AsyncSortBy extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {

            String baseURL ="http://10.0.0.3:42188/5046A1Final/webresources/rest.memoir/";
            if(strings[0].equals("watchDate")){
                baseURL += "sortByMwatchdt/";
            }
            if(strings[0].equals("userRating")){
                baseURL += "sortByMscore/";
            }
            if(strings[0].equals("releaseDate")){
                baseURL += "sortByMrelease/";
            }
            baseURL += String.valueOf(Login.login_id);
            String result = "";
            URL url = null;
            HttpURLConnection connect = null;

            try {
                url = new URL(baseURL);
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

            return  result;
        }

        protected void onPostExecute(String result) {
            JSONArray jsonArray = null;
            count = 0;
            memoirList.clear();
            movieNameList.clear();
//            List<String> release = new ArrayList<>();
            try{
                jsonArray = new JSONArray(result);
                for(int i = 0; i < jsonArray.length(); i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    map = new HashMap<String, Object>();
                    map.put("MovieName", "Movie Name: " + jsonObject.getString("mname"));
                    map.put("WatchDate", "Watch Date: " + jsonObject.getString("mwatchdt").substring(0,10));
                    map.put("Release Date", "Release Date: " + jsonObject.getString("mrelease").substring(0,10));
                    release.add(jsonObject.getString("mrelease").substring(0,10));
                    map.put("Comment", "Comment: " + jsonObject.getString("mcomment"));
                    map.put("Score", jsonObject.getString("mscore"));
                    JSONObject cinemaJson = jsonObject.getJSONObject("cid");
                    map.put("Cinema", "Cinema: " + cinemaJson.getString("csurburb"));

                    movieNameList.add(jsonObject.getString("mname"));
                    memoirList.add(map);
                }

            }catch (Exception e){
                e.printStackTrace();
            }

            new AsyncGetMovieDetial().execute(movieNameList.get(count), release.get(count));


        }
    }

    private class AsyncGetMovieDetial extends AsyncTask<String, Void, HashMap<String, String>> {
        @Override
        protected HashMap<String, String> doInBackground(String... strings) {
            return MoiveSearchAPI.searchMovieDeteil(strings[0],strings[1]);
        }
        protected void onPostExecute(HashMap<String, String> result) {

            memoirList.get(count).put("overview", "Overview: " + result.get("overview"));
            memoirList.get(count).put("publicscore", result.get("publicscore"));
            memoirList.get(count).put("genre", "Genre: " + result.get("genre"));
            memoirList.get(count).put("country", "Country: " + result.get("country"));
            String imgURL = "https://image.tmdb.org/t/p/w600_and_h900_bestv2" + result.get("moviepost");

            for(int i = 0; i < genreNameList.size(); i++){
                if(result.get("genre").equals(genreNameList.get(i))){
                   break;
                }
                if(i == genreNameList.size()-1){
                    genreNameList.add(result.get("genre"));
                }
            }
            new AsyncLoadImg().execute(imgURL);

        }


    }

    private class AsyncLoadImg extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... strings) {
            Bitmap bitmap;
            try{
                URL url = new URL(strings[0]);
                InputStream inputStream = url.openStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
                inputStream.close();
                return bitmap;
            }catch(IOException e){
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Bitmap bitmap){

            memoirList.get(count).put("img",bitmap);
            count +=1;
            if( count == movieNameList.size()){
                String[] colHead = new String[]{"img","MovieName", "WatchDate","Release Date","Cinema",
                        "Score","Comment", "overview","genre","country","publicscore"};
                int[] dataCell = new int[]{R.id.iv_ml_iv, R.id.tv_ml_moviename, R.id.tv_ml_wtchdate,
                        R.id.tv_ml_releasedate, R.id.tv_ml_cinema, R.id.rb_ml_score, R.id.tv_ml_comment, R.id.tv_ml_overview,
                        R.id.tv_ml_genre, R.id.tv_ml_country, R.id.rb_ml_publicscore, };
                SimpleAdapter simpleAdapter = new SimpleAdapter(vmemoir.getContext(),memoirList,R.layout.fragment_memoir_listview,colHead,dataCell);
                simpleAdapter.setViewBinder(new SimpleAdapter.ViewBinder(){
                    public boolean setViewValue(View view, Object object, String textReperesentation){
                        if((view instanceof ImageView) & (object instanceof Bitmap)){
                            ImageView imageView = (ImageView)view;
                            Bitmap bitmap = (Bitmap) object;
                            imageView.setImageBitmap(bitmap);
                            return true;
                        }
                        if((view instanceof RatingBar) & (object instanceof String)){
                            RatingBar ratingBar = (RatingBar) view;
                            float rate = Float.parseFloat((String) object);
                            ratingBar.setRating(rate);
                            return true;
                        }
                        return  false;
                    }
                });
                lv_m_meoir.setAdapter(simpleAdapter);
            }else{
                new AsyncGetMovieDetial().execute(movieNameList.get(count), release.get(count));
            }
        }
    }


}
