package com.example.a3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a3.Class.MD5;
import com.example.a3.Class.Person;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class Login extends AppCompatActivity {

    private EditText editText_UserName;
    private EditText editText_PassWord;
    public static int login_id;
    public static Person login_info = new Person();
//    SharedPreferences sp;

    public static final String BASE_URL ="http://10.0.0.3:42188/5046A1Final/webresources/";//http://10.0.0.3:42188/5046A1Final/webresources/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);
//        sp = getSharedPreferences("login_info", MODE_PRIVATE);
        Button button_Login = findViewById(R.id.button_Login);
        Button button_SignUp = findViewById(R.id.button_SignUp);
        editText_UserName = findViewById(R.id.editText_UserName);
        editText_PassWord = findViewById(R.id.editText_PassWord);


        button_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editText_UserName.getText().toString();
                String passwd = MD5.md5(editText_PassWord.getText().toString());
                if (username.trim().equals("") || passwd.trim().equals(""))
                    Toast.makeText(Login.this,"Username or password cannot be empty", Toast.LENGTH_SHORT).show();
                else
                    new AsyncLogin().execute(username,passwd);//match username and passwd in db
            }
        });

        button_SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Signup.class);
                startActivity(intent);
            }
        });
    }

    private class AsyncLogin extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            String result = "";
            String username = strings[0];
            String password = strings[1];
            URL url = null;
            HttpURLConnection connect = null;
            final String methodPath = "rest.credentials/findByUsernameAndPasswd/";
            try{
                url = new URL (BASE_URL + methodPath + username + "/" + password);
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
            try{
                String a = result.replace("[", "").replace("]", "");
                JSONObject jsonObject1 = new JSONObject(a);
                String loginid = jsonObject1.getString("pid");
                JSONObject jsonObject2 = new JSONObject(loginid);
                login_id = Integer.parseInt(jsonObject2.getString("pid"));
                login_info.setPid(jsonObject2.getString("pid"));
                login_info.setPaddress(jsonObject2.getString("paddress"));
                login_info.setPdob(jsonObject2.getString("pdob"));
                login_info.setPfname(jsonObject2.getString("pfname"));
                login_info.setPgender(jsonObject2.getString("pgender"));
                login_info.setPpostcode(jsonObject2.getString("ppostcode"));
                login_info.setPsname(jsonObject2.getString("psname"));
                login_info.setPstate(jsonObject2.getString("pstate"));
                String pid = jsonObject2.getString("pid");
                SharedPreferences sharedPreferences = Login.this.getSharedPreferences(pid,MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("fisrtname",jsonObject2.getString("pfname"));
                editor.putString("email",username);
                editor.commit();
            }catch (JSONException e){
                e.printStackTrace();
            }
            return result;
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if(result.equals("[]")){
                Toast.makeText(Login.this,"wrong username or password", Toast.LENGTH_SHORT).show();
            }
            else{
                SharedPreferences temp_sp = Login.this.getSharedPreferences(""+login_id ,MODE_PRIVATE);
                Toast.makeText(Login.this,"Welcome " + temp_sp.getString("fisrtname","") + "!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Login.this, MainActivity.class);
                startActivity(intent);
            }
        }
    }

}
