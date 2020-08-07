package com.example.a3;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a3.Class.Credentials;
import com.example.a3.Class.MD5;
import com.example.a3.Class.Person;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Signup extends AppCompatActivity {

    private ScrollView scrollView;
    private Map<String, EditText> editiext_Map = new HashMap<String, EditText>();
    private TextView textview_datepick;
    private Button button_submit;
    private Button button_datepick;
    private String gender = "M";
    private Spinner sp_state;
    private String state;
    private String DOB;
    private int uid;
    private int exitFlag = 0;
    private String signupDate;
    private List<String> username = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_signup);
        scrollView = findViewById(R.id.sv_scrollView);
        button_submit = findViewById(R.id.b_submit);
        button_datepick = findViewById(R.id.b_datepick);
        editiext_Map.put("et_newUserName",(EditText) findViewById(R.id.et_newUserName));
        editiext_Map.put("et_newPassword",(EditText) findViewById(R.id.et_newPassword));
        editiext_Map.put("et_confirmPassword",(EditText) findViewById(R.id.et_confirmPassword));
        editiext_Map.put("et_first_name",(EditText) findViewById(R.id.et_first_name));
        editiext_Map.put("et_last_name",(EditText) findViewById(R.id.et_last_name));
        editiext_Map.put("et_address",(EditText) findViewById(R.id.et_address));
        editiext_Map.put("et_postcode",(EditText) findViewById(R.id.et_postcode));
        textview_datepick = findViewById(R.id.tv_datepick);
        sp_state = findViewById(R.id.sp_state);
        new AsyncGetUsername().execute();//get all username
        //choose gender radiogroup
        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = findViewById(checkedId);
                if (radioButton.getText().equals("Male")) {
                    gender = "M";
                } else {
                    gender = "F";
                }
            }
        });
        //choose state spinner
        List<String> list = new ArrayList<String>();
        list.add("NSW");
        list.add("VIC");
        list.add("QLD");
        list.add("ACT");
        list.add("SA");
        list.add("WA");
        list.add("TAS");
        list.add("NT");
        final ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_state.setAdapter(spinnerAdapter);
        sp_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedState = parent.getItemAtPosition(position).toString();
                if (selectedState != null) {
                    state = selectedState;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        button_datepick.setOnClickListener(new View.OnClickListener() {
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
                signupDate = syear + "-" + mm + "-" + dd + "T00:00:00+10:00";
                DatePickerDialog datePicker = new DatePickerDialog(Signup.this, new DatePickerDialog.OnDateSetListener() {
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
                        textview_datepick.setText(dob);
                        DOB = dob + "T00:00:00+10:00";
                    }
                }, syear, smonthOfYear, sdayOfMonth);
                DatePicker dp = datePicker.getDatePicker();
                dp.setMaxDate(System.currentTimeMillis());
                datePicker.show();
            }
        });

        button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean flag = true;
                String newusername = editiext_Map.get("et_newUserName").getText().toString();
                for (int i = 0; i < username.size(); i++) {
                    if (newusername.equals(username.get(i))) {
                        flag = false;
                        editiext_Map.get("et_newUserName").setError("The username is exist");
                        Toast.makeText(Signup.this, "The username is exist, please enter again", Toast.LENGTH_SHORT).show();
                    }
                }
                if (editiext_Map.get("et_newPassword").getText().toString().equals(editiext_Map.get("et_confirmPassword").getText().toString())) {
                    if (flag) {
                        Person person = new Person();
                        int id = username.size() + 1;
                        person.setPid( id+"");
                        person.setPfname(editiext_Map.get("et_first_name").getText().toString());
                        person.setPsname(editiext_Map.get("et_last_name").getText().toString());
                        person.setPaddress(editiext_Map.get("et_address").getText().toString());
                        person.setPpostcode(editiext_Map.get("et_postcode").getText().toString());
                        person.setPdob(DOB);
                        person.setPgender(gender);
                        person.setPstate(state);
                        new AsyncPostUser().execute(person);
                        Toast.makeText(Signup.this, "Sign up finish, please login", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Signup.this, Login.class);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(Signup.this, "There are some mistakes, please check again", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    editiext_Map.get("et_confirmPassword").setError("two password are not same");
                    Toast.makeText(Signup.this, "Make sure you enter same password twice", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private class AsyncGetUsername extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            String result = "";
            URL url = null;
            HttpURLConnection connect = null;
            final String methodPath = "rest.credentials/getAllUsername/";
            try {
                url = new URL(Login.BASE_URL + methodPath);
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
                    String temp = jsonObject.getString("username");
                    username.add(temp);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

    }

    private class AsyncPostUser extends AsyncTask<Person, Void, Person> {
        @Override
        protected Person doInBackground(Person... person) {
            URL url = null;
            HttpURLConnection conn = null;
            final String methodPath = "rest.person/";
            Person newPerson = person[0];
            try {
                Gson gson = new Gson();
                String stringCourseJson = gson.toJson(newPerson);
                String a = stringCourseJson.replace("[", "").replace("]", "");
                url = new URL(Login.BASE_URL + methodPath);
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
            } finally {
                conn.disconnect();
            }
            return newPerson;
        }
        protected void onPostExecute(Person newPerson) {
            Credentials credential = new Credentials();
//            int id = username.size() + 1;
            credential.setPid(newPerson);
            credential.setPasswd(MD5.md5(editiext_Map.get("et_confirmPassword").getText().toString()));
            credential.setUsername(editiext_Map.get("et_newUserName").getText().toString());
            credential.setSigndate(signupDate);
            new AsyncPostCredential().execute(credential);
        }
    }


    private class AsyncPostCredential extends AsyncTask<Credentials, Void, Void> {
        @Override
        protected Void doInBackground(Credentials... credential) {
            URL url = null;
            HttpURLConnection conn = null;
            final String methodPath = "rest.credentials/";
            try {
                Gson gson = new Gson();
                String stringCourseJson = gson.toJson(credential);
                String a = stringCourseJson.replace("[", "").replace("]", "");
                url = new URL(Login.BASE_URL + methodPath);
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
            } finally {
                conn.disconnect();
            }
            return null;
        }
    }
}
