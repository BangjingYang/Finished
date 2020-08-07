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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.a3.API.MoiveSearchAPI;
import com.example.a3.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MovieSearch_Fragment extends Fragment {
    private View moviesearch_view;
    private Button button_search;
    private EditText et_search;
    private List<String> resultList = new ArrayList<>();
    private ListView lv_searchmovie;
    private List<HashMap<String, Object>> resultListArray;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        moviesearch_view = inflater.inflate(R.layout.fragment_moviesearch, container, false);
        button_search = moviesearch_view.findViewById(R.id.button_search);
        et_search = moviesearch_view.findViewById(R.id.et_search);
        lv_searchmovie = moviesearch_view.findViewById(R.id.lv_searchmovie);

        button_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultListArray = new ArrayList<>();
                new AsyncSearch().execute(et_search.getText().toString());
            }
        });
        lv_searchmovie.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String movieID = resultListArray.get(position).get("movieID").toString();
                String moviename = resultListArray.get(position).get("moviename").toString();
                String moviereleasedate = resultListArray.get(position).get("releasedate").toString();
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("message", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("movieID",movieID);
                editor.putString("moviename",moviename);
                editor.putString("releasedate",moviereleasedate);
                editor.putString("flag","true");
                editor.commit();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, new MovieView_Fragment()).commit();
            }
        });

        return moviesearch_view;
    }

    private class AsyncSearch extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            return MoiveSearchAPI.searchMoive(strings[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            new AsyncMoiveList().execute(result);
        }
    }

    private class AsyncMoiveList extends AsyncTask<String, Void, SimpleAdapter> {
        @Override
        protected SimpleAdapter doInBackground(String... strings) {

            resultList = MoiveSearchAPI.getSnippet(strings[0]);
            for( int i = 0; i <resultList.size();i += 4){
                HashMap<String, Object> map = new HashMap<>();
                map.put("moviename", resultList.get(i));
                map.put("releasedate", resultList.get(i+1));
                map.put("movieID", resultList.get(i+3));
                String imgURL = "https://image.tmdb.org/t/p/w600_and_h900_bestv2" + resultList.get(i+2);
                Bitmap bitmap;
                try{
                    URL url = new URL(imgURL);
                    InputStream inputStream = url.openStream();
                    bitmap = BitmapFactory.decodeStream(inputStream);
                    inputStream.close();
                    map.put("img", bitmap);
                }catch(IOException e){
                    e.printStackTrace();
                }
                resultListArray.add(map);
            }
            if(resultListArray.size() == 0) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("moviename", "No result, please try other keywords");
                resultListArray.add(map);
            }
            String[] colHead = new String[]{"img","moviename", "releasedate", "movieID"};
            int[] dataCell = new int[]{R.id.iv_smList, R.id.tv_smnList, R.id.tv_srdList, R.id.tv_smidList};
            SimpleAdapter simpleAdapter = new SimpleAdapter(MovieSearch_Fragment.this.getActivity(), resultListArray, R.layout.fragment_moviesearch_listview, colHead, dataCell);
            simpleAdapter.setViewBinder(new SimpleAdapter.ViewBinder(){
                public boolean setViewValue(View view, Object object, String textReperesentation){
                    if((view instanceof ImageView) & (object instanceof Bitmap)){
                        ImageView imageView = (ImageView)view;
                        Bitmap bitmap = (Bitmap) object;
                        imageView.setImageBitmap(bitmap);
                        return true;
                    }
                    return  false;
                }
            });
            return simpleAdapter;
        }
        @Override
        protected void onPostExecute(SimpleAdapter simpleAdapter){
            lv_searchmovie.setAdapter(simpleAdapter);
//            lv_searchmovie.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    String movieID = resultListArray.get(position).get("movieID").toString();
//                    String moviename = resultListArray.get(position).get("moviename").toString();
////                    Fragment nextFragment = new MovieView_Fragment();
////                    Bundle bundle = new Bundle();
////                    bundle.putString("movieID",movieID);
////                    bundle.putString("moviename",moviename);
////                    nextFragment.setArguments(bundle);
//                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("message", Context.MODE_PRIVATE);
//                    SharedPreferences.Editor editor = sharedPreferences.edit();
//                    editor.putString("movieID",movieID);
//                    editor.putString("emamovienameil",moviename);
//                    editor.commit();
//                    FragmentManager fragmentManager = getFragmentManager();
//                    fragmentManager.beginTransaction().replace(R.id.content_frame, new MovieView_Fragment()).commit();
//                }
//            });
        }
    }


}
