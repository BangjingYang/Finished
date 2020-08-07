package com.example.a3.nv_fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.a3.Class.Cinema;
import com.example.a3.Login;
import com.example.a3.R;
import com.google.gson.Gson;

import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AddCinema_Fragment extends Fragment {

    private View vaddCinema;
    private ImageView iv_ac_img;
    private TextView tv_ac_cinemaname;
    private EditText et_ac_cname;
    private TextView tv_ac_cinemasubburb;
    private EditText et_ac_csuburb;
    private TextView tv_ac_cinemapostcode;
    private EditText et_ac_cpostcode;
    private Button b_ac_addcinema;

    private Cinema cinema;
    private  String cid;

    private List<String> movieDetial = new ArrayList<>();



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        vaddCinema = inflater.inflate(R.layout.fragment_addcinema, container, false);

        et_ac_cname = vaddCinema.findViewById(R.id.et_ac_cname);
        et_ac_csuburb = vaddCinema.findViewById(R.id.et_ac_csuburb);
        et_ac_cpostcode = vaddCinema.findViewById(R.id.et_ac_cpostcode);
        if(getArguments() != null){
            movieDetial.add(getArguments().getString("moviename"));
            movieDetial.add(getArguments().getString("releasedate"));
            movieDetial.add(getArguments().getString("imgURL"));
        }
        b_ac_addcinema = vaddCinema.findViewById(R.id.b_ac_addcinema);
        new AsyncGetCid().execute();
        b_ac_addcinema.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et_ac_cname.getText().toString().trim().isEmpty() ||
                        et_ac_csuburb.getText().toString().trim().isEmpty() ||
                        et_ac_cpostcode.getText().toString().trim().isEmpty() ){
                    Toast.makeText(vaddCinema.getContext(),"Make sure you enter all information", Toast.LENGTH_SHORT).show();
                }else{
                    cinema = new Cinema(cid,et_ac_cname.getText().toString().trim(),
                            et_ac_csuburb.getText().toString().trim(),
                            et_ac_cpostcode.getText().toString().trim());
                    new AsyncPostCinema().execute(cinema);

                }
            }
        });






        return vaddCinema;
    }

    private class AsyncGetCid extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            URL url = null;
            HttpURLConnection connect = null;
            String result = "";
            try{
                url = new URL ("http://10.0.0.3:42188/5046A1Final/webresources/rest.cinema/count");
                connect = (HttpURLConnection) url.openConnection();
                connect.setReadTimeout(10000);
                connect.setConnectTimeout(15000);
                connect.setRequestMethod("GET");
                connect.setRequestProperty("Content-Type","text/plain");
                connect.setRequestProperty("Accept","text/plain");
                Scanner scanner = new Scanner(connect.getInputStream());
                while (scanner.hasNextLine()){
                    result += scanner.nextLine();
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
            finally {
                connect.disconnect();
            }
            return result;
        }
        @Override
        protected void onPostExecute(String result){
            cid = String.valueOf(Integer.parseInt(result) + 1);
        }

    }

    private class AsyncPostCinema extends AsyncTask<Cinema, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Cinema... cinemas) {
            URL url = null;
            HttpURLConnection conn = null;
            Cinema newMemoir = cinemas[0];
            try {
                Gson gson = new Gson();
                String stringCourseJson = gson.toJson(newMemoir);
                String a = stringCourseJson.replace("[", "").replace("]", "");
                url = new URL ("http://10.0.0.3:42188/5046A1Final/webresources/rest.cinema");
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.setFixedLengthStreamingMode(a.getBytes().length);
                conn.setRequestProperty("Content-Type", "application/json");
                PrintWriter out = new PrintWriter(conn.getOutputStream());
                out.print(a);
                out.flush();
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            } finally {
                conn.disconnect();
            }
            return true;
        }
        protected void onPostExecute(Boolean result) {
            if(result){
                Toast.makeText(vaddCinema.getContext(), "Add new cinema", Toast.LENGTH_SHORT).show();
                Fragment nextFragment = new AddMemoir_Fragment();
                Bundle bundle = new Bundle();
                bundle.putString("releasedate",movieDetial.get(1));
                bundle.putString("moviename",movieDetial.get(0));
                bundle.putString("imgURL",movieDetial.get(2));
                nextFragment.setArguments(bundle);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, nextFragment).commit();
            }else {
                Toast.makeText(vaddCinema.getContext(), "Add Fail", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
