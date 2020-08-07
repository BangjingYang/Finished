package com.example.a3.nv_fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.a3.API.MoiveSearchAPI;
import com.example.a3.DataBase.WatchList;
import com.example.a3.Login;
import com.example.a3.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class MovieView_Fragment extends Fragment {
    private View movieview;
    private String movieID;
    private String movieName;
    private String releaseDate;

    private String imgURL = "https://image.tmdb.org/t/p/w600_and_h900_bestv2";
    private ImageView mv_image;
    private TextView tv_mv_moviename;
    private TextView tv_mv_releasedate;
    private TextView tv_mv_genre;
    private TextView tv_mv_country;
    private TextView tv_mv_overviews;
    private TextView tv_mv_ratingscore;

    private List<String> castList;
    private ListView lv_mv_cast;
    private List<HashMap<String, String>> castListArray;

    private List<String> directorList;
    private ListView lv_mv_director;
    private List<HashMap<String, String>> directorListArray;

    private String score;
    private RatingBar rb_mv_rating;

    private Button button_watchList;
    private Button button_memoir;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        movieview = inflater.inflate(R.layout.fragment_movieview, container, false);
        mv_image = movieview.findViewById(R.id.mv_image);
        tv_mv_moviename = movieview.findViewById(R.id.tv_mv_moviename);
        tv_mv_releasedate = movieview.findViewById(R.id.tv_mv_releasedate);
        button_watchList = movieview.findViewById(R.id.button_watchList);
        button_memoir = movieview.findViewById(R.id.button_memoir);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("message", Context.MODE_PRIVATE);
        movieID = sharedPreferences.getString("movieID", "");
        movieName = sharedPreferences.getString("moviename", "");
        releaseDate = sharedPreferences.getString("releasedate", "");
        tv_mv_moviename.setText(movieName);
        tv_mv_releasedate.setText(releaseDate);
        if(sharedPreferences.getString("flag", "").equals("false")){
            button_watchList.setVisibility(View.INVISIBLE);
        }
        new AsyncGetMovieDetial().execute(movieID);
        button_watchList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AsyncCheckMovieList().execute(String.valueOf(Login.login_id));
            }
        });
        button_memoir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment nextFragment = new AddMemoir_Fragment();
                Bundle bundle = new Bundle();
                bundle.putString("releasedate",releaseDate);
                bundle.putString("moviename",movieName);
                bundle.putString("imgURL",imgURL);
                nextFragment.setArguments(bundle);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, nextFragment).commit();
            }
        });
        return movieview;
    }

    private class AsyncGetMovieDetial extends AsyncTask<String, Void, HashMap<String, Object>> {
        @Override
        protected HashMap<String, Object> doInBackground(String... strings) {
            return MoiveSearchAPI.getMovieDetail(strings[0]);
        }

        @Override
        protected void onPostExecute(HashMap<String, Object> result) {
            tv_mv_genre = movieview.findViewById(R.id.tv_mv_genre);
            tv_mv_genre.setText((String) result.get("genre"));
            tv_mv_country = movieview.findViewById(R.id.tv_mv_country);
            tv_mv_country.setText((String) result.get("country"));
            tv_mv_overviews = movieview.findViewById(R.id.tv_mv_overviews);
            tv_mv_overviews.setText((String) result.get("overview"));

            imgURL += (String) result.get("imgURL");

            if(result.containsKey("castList")) {
                List<String> castList = (List<String>) result.get("castList");
                List<HashMap<String, String>> castListArray = new ArrayList<>();
                for (int i = 0; i < castList.size(); i++) {
                    HashMap<String, String> map = new HashMap<>();
                    map.put("castname", castList.get(i));
                    castListArray.add(map);
                }
                String[] colHead1 = new String[]{"castname"};
                int[] dataCell1 = new int[]{R.id.tv_mv_castlist};
                SimpleAdapter simpleAdapter1 = new SimpleAdapter(MovieView_Fragment.this.getActivity(), castListArray, R.layout.fragment_movieview_cast_listview, colHead1, dataCell1);
                ListView lv_mv_cast = movieview.findViewById(R.id.lv_mv_cast);
                lv_mv_cast.setAdapter(simpleAdapter1);
            }else{
                TextView tv_mv_casts = movieview.findViewById(R.id.tv_mv_casts);
                tv_mv_casts.setText("No Cast");
            }
            if(result.containsKey("directorList")){
                List<String> directorList = (List<String>) result.get("directorList");
                List<HashMap<String, String>> directorListArray = new ArrayList<>();
                for (int i = 0; i < directorList.size(); i++) {
                    HashMap<String, String> map = new HashMap<>();
                    map.put("Director", directorList.get(i));
                    directorListArray.add(map);
                }
                String[] colHead2 = new String[]{"Director"};
                int[] dataCell2 = new int[]{R.id.tv_mv_directorlist};
                SimpleAdapter simpleAdapter2 = new SimpleAdapter(MovieView_Fragment.this.getActivity(), directorListArray, R.layout.fragment_movieview_director_listview, colHead2, dataCell2);
                ListView lv_mv_director = movieview.findViewById(R.id.lv_mv_director);
                lv_mv_director.setAdapter(simpleAdapter2);
            }else{
                TextView tv_mv_director = movieview.findViewById(R.id.tv_mv_director);
                tv_mv_director.setText("No director");
            }
            Float f1 = Float.parseFloat((String) result.get("rate score"));
            tv_mv_ratingscore = movieview.findViewById(R.id.tv_mv_ratingscore);
            tv_mv_ratingscore.setText((String) result.get("rate score"));
            rb_mv_rating =  movieview.findViewById(R.id.rb_mv_rating);
            rb_mv_rating.setRating(f1);

            new AsyncLoadImg().execute(imgURL);

        }
    }

    private class AsyncCheckMovieList extends AsyncTask<String, Void, List<WatchList>>{

        @Override
        protected List<WatchList> doInBackground(String... strings) {
            List<WatchList> watchLists = Home_Fragment.watchListViewModel.getAllWatchListByID(strings[0]);
            return watchLists;
        }
        @Override
        protected void onPostExecute(List<WatchList> watchLists){
            boolean ifExist = false;
            for (WatchList watchList : watchLists){
                if(watchList.getMovieID().equals(movieID)){
                    Toast.makeText(getActivity(),"Movie has been in your Watch List.", Toast.LENGTH_LONG).show();
                    ifExist = true;
                    break;
                }
            }
            if(!ifExist){
                Date nowDate = new Date();
                SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss");
                String addDate = date.format(nowDate.getTime());
                String addTime = time.format(nowDate.getTime());
                WatchList watchList = new WatchList(movieName, releaseDate,addDate, addTime, String.valueOf(Login.login_id),movieID);
                Home_Fragment.watchListViewModel.insert(watchList);
                Toast.makeText(getActivity(),"Add this movie to your Watch List.", Toast.LENGTH_LONG).show();

            }

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

            mv_image.setImageBitmap(bitmap);
        }
    }


}
