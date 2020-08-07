package com.example.a3.nv_fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.a3.Class.Cinema;
import com.example.a3.Class.Memoir;
import com.example.a3.Login;
import com.example.a3.R;
import com.google.gson.Gson;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

public class AddMemoir_Fragment extends Fragment {
    private View vaddmemoir;
    private EditText et_ad_comments;
    private ImageView iv_am;
    private TextView tv_ad_moviename;
    private TextView tv_ad_releasedate;
    private TextView tv_ad_pickeddatetime;
    private Button b_ad_date;
    private Button b_ad_time;
    private Button b_ad_addcinema;
    private String addDate;
    private String watchDate;
    private String watchDateDB;
    private Button b_ad_addmemoir;
    private String mid;


    private Spinner sp_ad_pickcinema;
    private List<String> cinemaList = new ArrayList<>();
    private List<Cinema> cinemas = new ArrayList<>();
    private Cinema cinema;
    private String imgURL;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        vaddmemoir = inflater.inflate(R.layout.fragment_addmemoir, container, false);
        et_ad_comments = vaddmemoir.findViewById(R.id.et_ad_comments);
        iv_am = vaddmemoir.findViewById(R.id.iv_am);
        tv_ad_moviename = vaddmemoir.findViewById(R.id.tv_ad_moviename);
        et_ad_comments = vaddmemoir.findViewById(R.id.et_ad_comments);
        tv_ad_releasedate = vaddmemoir.findViewById(R.id.tv_ad_releasedate);
        b_ad_date = vaddmemoir.findViewById(R.id.b_ad_date);
        b_ad_time = vaddmemoir.findViewById(R.id.b_ad_time);
        b_ad_addcinema = vaddmemoir.findViewById(R.id.b_ad_addcinema);
        tv_ad_pickeddatetime = vaddmemoir.findViewById(R.id.tv_ad_pickeddatetime);
        sp_ad_pickcinema = vaddmemoir.findViewById(R.id.sp_ad_pickcinema);
        b_ad_addmemoir = vaddmemoir.findViewById(R.id.b_ad_addmemoir);
        final TextView tv_ad_ratingnum = vaddmemoir.findViewById(R.id.tv_ad_ratingnum);
        SimpleRatingBar simpleRatingBar = vaddmemoir.findViewById(R.id.rb_ad_rating);
        if(getArguments() != null){
            tv_ad_moviename.setText(getArguments().getString("moviename"));
            tv_ad_releasedate.setText(getArguments().getString("releasedate"));
            imgURL = getArguments().getString("imgURL");
            new AsyncLoadImg().execute(imgURL);
        }
        new AsyncLoadCinema().execute();
        b_ad_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar = Calendar.getInstance();
                int syear = calendar.get(Calendar.YEAR);
                int smonthOfYear = calendar.get(Calendar.MONTH) + 1;
//                final
                int sdayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                String mm;
                if (smonthOfYear < 10) {
                    mm = "0" + smonthOfYear;
                } else {
                    mm = "" + smonthOfYear;
                }
                String dd = "";
                if (sdayOfMonth < 10) {
                    dd = "0" + sdayOfMonth;
                } else {
                    dd = sdayOfMonth + "";
                }
                addDate = syear + "-" + mm + "-" + dd + "T00:00:00+10:00";
                DatePickerDialog datePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        String mon = "";
                        int month = monthOfYear + 1;
                        if (month < 10) {
                            mon = "0" + month;
                        } else {
                            mon = "" + month;
                        }
                        String day = "";
                        if (dayOfMonth < 10) {
                            day = "0" + dayOfMonth;
                        } else {
                            day = "" + dayOfMonth;
                        }
                        watchDate = year + "-" + mon + "-" + day;
                        tv_ad_pickeddatetime.setText(watchDate);
                    }
                }, syear, smonthOfYear, sdayOfMonth);
                DatePicker dp = datePicker.getDatePicker();
                dp.setMaxDate(System.currentTimeMillis());
                datePicker.show();
            }
        });
        b_ad_time.setOnClickListener(new View.OnClickListener() {
            Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(vaddmemoir.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String ahour = "";
                        if (hour < 10) {
                            ahour = "0" + hour;
                        } else {
                            ahour = "" + hour;
                        }
                        String aminute = "";
                        if (minute < 10) {
                            aminute = "0" + minute;
                        } else {
                            aminute = minute + "";
                        }
                        watchDateDB = watchDate + "T" + (ahour + ":" + aminute + ":00+10:00");//2020-05-30T00:00:00+10:00
                        watchDate += (ahour + ":" + aminute + ":00");

                    }
                }, hour,minute,true);
                timePickerDialog.show();
            }
        });

        simpleRatingBar.setOnRatingBarChangeListener(new SimpleRatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(SimpleRatingBar simpleRatingBar, float rating, boolean fromUser) {
                tv_ad_ratingnum.setText(String.valueOf(rating));
            }
        });




        new AsyncGetmid().execute();
        b_ad_addmemoir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rDate = tv_ad_releasedate.getText().toString() + "T00:00:00+10:00";
                Memoir memoir = new Memoir(mid,rDate,addDate,
                        et_ad_comments.getText().toString(),tv_ad_ratingnum.getText().toString(),
                        watchDateDB,tv_ad_moviename.getText().toString(),cinema, Login.login_info);

                new AsyncPostMemoir().execute(memoir);
            }
        });

        b_ad_addcinema.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment nextFragment = new AddCinema_Fragment();
                Bundle bundle = new Bundle();
                bundle.putString("releasedate",tv_ad_releasedate.getText().toString());
                bundle.putString("moviename",tv_ad_moviename.getText().toString());
                bundle.putString("imgURL",imgURL);
                nextFragment.setArguments(bundle);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, nextFragment).commit();
            }
        });


        return vaddmemoir;
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
            iv_am.setImageBitmap(bitmap);
        }
    }

    private class AsyncGetmid extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            URL url = null;
            HttpURLConnection connect = null;
            String result = "";
            try{
                url = new URL ("http://10.0.0.3:42188/5046A1Final/webresources/rest.memoir/count");
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
            mid = String.valueOf(Integer.parseInt(result) + 1);
        }

    }

    private class AsyncLoadCinema extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            URL url = null;
            HttpURLConnection connect = null;
            String result = "";
            try{
                url = new URL ("http://10.0.0.3:42188/5046A1Final/webresources/rest.cinema");
                connect = (HttpURLConnection) url.openConnection();
                connect.setReadTimeout(10000);
                connect.setConnectTimeout(15000);
                connect.setRequestMethod("GET");
                connect.setRequestProperty("Content-Type","application/json");
                connect.setRequestProperty("Accept","application/json");
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
            try{
                JSONArray jsonArray = new JSONArray(result);
                for(int i = 0; i < jsonArray.length(); i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String cinemaDetail = jsonObject.getString("cname") + " " + jsonObject.getString("cpostcode");
                    Cinema cinema1 = new Cinema(jsonObject.getString("cid"),jsonObject.getString("cname"),jsonObject.getString("csurburb"),jsonObject.getString("cpostcode"));
                    cinemaList.add(cinemaDetail);
                    cinemas.add(cinema1);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            final ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, cinemaList);
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sp_ad_pickcinema.setAdapter(spinnerAdapter);
            sp_ad_pickcinema.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String selectedCinema = parent.getItemAtPosition(position).toString();
                    if (selectedCinema != null) {
                        cinema = cinemas.get(position);
                    }
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }

    }

    private class AsyncPostMemoir extends AsyncTask<Memoir, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Memoir... memoirs) {
            URL url = null;
            HttpURLConnection conn = null;
            Memoir newMemoir = memoirs[0];
            try {
                Gson gson = new Gson();
                String stringCourseJson = gson.toJson(newMemoir);
                String a = stringCourseJson.replace("[", "").replace("]", "");
                url = new URL ("http://10.0.0.3:42188/5046A1Final/webresources/rest.memoir");
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
                Toast.makeText(vaddmemoir.getContext(), "Add new Memoir~", Toast.LENGTH_SHORT).show();
                Fragment nextFragment = new WatchList_Fragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, nextFragment).commit();
            }else {
                Toast.makeText(vaddmemoir.getContext(), "Add Fail", Toast.LENGTH_SHORT).show();
            }
        }
    }


}
