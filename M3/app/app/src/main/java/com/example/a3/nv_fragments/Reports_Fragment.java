package com.example.a3.nv_fragments;

import android.app.DatePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.a3.Login;
import com.example.a3.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Reports_Fragment extends Fragment {
    private View vreport;
    private Button b_re_begin;
    private Button b_re_end;
    private Button b_re_check;
    private Button b_re_Review;
    private PieChart pc_re;
    private BarChart bc_re;
    private TextView tv_re_begin;
    private TextView tv_re_end;
    private Spinner sp_re_year;
    private String selectYear = "";




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        vreport = inflater.inflate(R.layout.fragment_report, container, false);
        b_re_begin = vreport.findViewById(R.id.b_re_begin);
        b_re_end = vreport.findViewById(R.id.b_re_end);
        tv_re_begin = vreport.findViewById(R.id.tv_re_begin);
        tv_re_end = vreport.findViewById(R.id.tv_re_end);
        pc_re = vreport.findViewById(R.id.pc_re);
        sp_re_year = vreport.findViewById(R.id.sp_re_year);
        bc_re = vreport.findViewById(R.id.bc_re);
        b_re_begin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int syear = calendar.get(Calendar.YEAR);
                int smonthOfYear = calendar.get(Calendar.MONTH) + 1;
                final int sdayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
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

                DatePickerDialog datePicker = new DatePickerDialog(vreport.getContext(), new DatePickerDialog.OnDateSetListener() {
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
                        String dob = year + "-" + mon + "-" + day;
                        tv_re_begin.setText(dob);

                    }
                }, syear, smonthOfYear, sdayOfMonth);
                DatePicker dp = datePicker.getDatePicker();
                dp.setMaxDate(System.currentTimeMillis());
                datePicker.show();
            }
        });
        b_re_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int syear = calendar.get(Calendar.YEAR);
                int smonthOfYear = calendar.get(Calendar.MONTH) + 1;
                final int sdayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
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

                DatePickerDialog datePicker = new DatePickerDialog(vreport.getContext(), new DatePickerDialog.OnDateSetListener() {
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
                        String dob = year + "-" + mon + "-" + day;
                        tv_re_end.setText(dob);
                    }
                }, syear, smonthOfYear, sdayOfMonth);
                DatePicker dp = datePicker.getDatePicker();
                dp.setMaxDate(System.currentTimeMillis());
                datePicker.show();
            }

        });

        b_re_check = vreport.findViewById(R.id.b_re_check);
        b_re_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_re_begin.setError(null);
                tv_re_end.setError(null);
                if(tv_re_begin.getText().toString().isEmpty()) {
                    tv_re_begin.setError("Please Choose Begin Day");
                    Toast.makeText(vreport.getContext(),"Please Choose Begin Day",Toast.LENGTH_SHORT).show();
//                    return;
                }

                if(tv_re_end.getText().toString().isEmpty()) {
                    tv_re_end.setError("Please Choose End Day");
                    Toast.makeText(vreport.getContext(),"Please Choose End Day",Toast.LENGTH_SHORT).show();
//                    return;
                }

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Date beginDate = new Date();
                Date endDate = new Date();

                try{
                    beginDate = format.parse(tv_re_begin.getText().toString());
                } catch (Exception e){
                    tv_re_begin.setError("Wrong input date");
//                    return;
                }

                try{
                    endDate = format.parse(tv_re_end.getText().toString());
                } catch (Exception e){
                    tv_re_end.setError("Wrong input date");
//                    return;
                }

//                System.out.println(startDate.getTime() <= endDate.getTime());

                if(beginDate.getTime() <= endDate.getTime()) {
                    new AsyncGetMovieNum().execute(tv_re_begin.getText().toString(),tv_re_end.getText().toString());
                } else{
                    tv_re_begin.setError("Begin day should not greater than end day");
                    Toast.makeText(vreport.getContext(),"Begin day should not greater than end day",Toast.LENGTH_SHORT).show();
                }

            }
        });

        List<String> year = new ArrayList<>();
        year.add("2020");
        year.add("2019");
        year.add("2018");
        year.add("2017");
        year.add("2016");
        year.add("2015");
        final ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(Reports_Fragment.this.getActivity(), android.R.layout.simple_spinner_item, year);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_re_year.setAdapter(spinnerAdapter);
        sp_re_year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedState = parent.getItemAtPosition(position).toString();
                if (selectedState != null) {
                    selectYear = selectedState;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectYear = "2020";
            }
        });

        b_re_Review = vreport.findViewById(R.id.b_re_Review);
        b_re_Review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AsyncshowBarChart().execute(selectYear);
            }
        });

        return vreport;
    }
    private class AsyncshowBarChart extends AsyncTask<String, Void, BarDataSet> {
        @Override
        protected BarDataSet doInBackground(String... year) {
            List<BarEntry> entries = new ArrayList<>();
            String result = "";
            URL url = null;
            HttpURLConnection conn = null;
            String methodPath = "http://10.0.0.3:42188/5046A1Final/webresources/rest.memoir/findMnumByPidANDYear/" + String.valueOf(Login.login_id) + "/" + year[0];
            try {
                url = new URL(methodPath);
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Accept", "application/json");
                Scanner inStream = new Scanner(conn.getInputStream());
                while (inStream.hasNextLine()) {
                    result += inStream.nextLine();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conn.disconnect();
            }
            try {
                JSONArray jsonArray = new JSONArray(result);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                    entries.add(new BarEntry(Float.parseFloat(jsonObject.getString("month")), Float.parseFloat(jsonObject.getString("number of movie"))));//number of movie
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            BarDataSet set = new BarDataSet(entries, "BarDataSet");
            return set;
        }
        protected void onPostExecute(BarDataSet set) {
            BarData data = new BarData(set);
            data.setBarWidth(0.9f); // set custom bar width
            bc_re.setData(data);
            bc_re.setFitBars(true); // make the x-axis fit exactly all bars
            bc_re.invalidate(); // refresh
        }

    }


    private class AsyncGetMovieNum extends AsyncTask<String, Void, List<PieEntry>> {
        @Override
        protected List<PieEntry> doInBackground(String... strings) {
            String result = "";
            List<PieEntry> pieList = new ArrayList<>();
            URL url = null;
            HttpURLConnection connect = null;
            String methodPath = "http://10.0.0.3:42188/5046A1Final/webresources/rest.memoir/findMnumByPidANDTime/" +
                    String.valueOf(Login.login_id) + "/" + strings[0] + "/" + strings[1];
            try {
                url = new URL(methodPath);
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
            try {
                JSONArray jsonArray = new JSONArray(result);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                    PieEntry pie = new PieEntry(Integer.parseInt(jsonObject.getString("mnum")),jsonObject.getString("cposcode"));
                    pieList.add(pie);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return pieList;
        }

        protected void onPostExecute(List<PieEntry> result) {
            pc_re.setUsePercentValues(true);
            PieDataSet pieDataSet = new PieDataSet(result, "Cinema Postcode");
            PieData pie = new PieData();
            pie.setDataSet(pieDataSet);
            pc_re.setData(pie);
            pc_re.invalidate();
        }
    }


}
