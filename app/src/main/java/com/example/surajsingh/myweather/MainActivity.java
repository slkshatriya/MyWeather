package com.example.surajsingh.myweather;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    TextView resultTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText= findViewById(R.id.editText);
        Log.e("onCreate","onCreate created");
        resultTextView = findViewById(R.id.textView2);
    }
    //https://openweathermap.org/data/2.5/forecast?q="
    public  void getWeather(View view){
        DownloadTsk downloadTsk = new DownloadTsk();
        downloadTsk.execute("https://api.openweathermap.org/data/2.5/weather?"+editText.getText().toString()+"uk&appid=8dac782f27f1e839f0d19e875b0ac13a" );
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(),0);
        Log.e("getWeather","getWeather created");
        

    }
    public  class DownloadTsk extends AsyncTask<String,Void,String>{
        String result =null;
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            try{
                JSONObject jsonObject = new JSONObject(result);
                String weatherInfo = jsonObject.getString("weather");
                Log.i("JsonObject","JsonObject called");
                JSONArray jsonArray = new JSONArray(weatherInfo);
                String message = "";
                for (int i =0;i< jsonArray.length();i++){
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                    String main = jsonObject1.getString("main");
                    Toast.makeText(getApplicationContext(),main,Toast.LENGTH_LONG).show();
                    String description = jsonObject1.getString("description");
                    if (!(main.equals("")&& !description.equals("") )){
                        message = message + main +": "+description+"\r\n";
                        resultTextView.setText(message);
                        Toast.makeText(getApplicationContext(),"It's working",Toast.LENGTH_LONG).show();

                    }

                }

            }catch (Exception e){

                e.printStackTrace();
                Toast.makeText(getApplicationContext(),"It's not working",Toast.LENGTH_LONG).show();


            }
        }

        @Override
        protected String doInBackground(String... urls) {

            URL url;
            HttpURLConnection httpURLConnection =null;
            try{
                url = new URL(urls[0]);
                httpURLConnection =(HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                int data = inputStreamReader.read();
                while (data != 1){
                    char word = (char)data;
                    result += word;
                    data = inputStreamReader.read();

//                    Toast.makeText(getApplicationContext(),"Do in backGround",Toast.LENGTH_LONG).show();


                }
                Log.i("result","result called");
                return result;
            }catch (IOException e){
                e.printStackTrace();

                return  null;

            }




        }
    }
//    public class DownloadTask extends  AsyncTask<String, Void,String>{
//        String result = null;
//    @Override
//    protected void onPostExecute(String s) {
//        super.onPostExecute(s);
//    }
//
//    @Override
//    protected String doInBackground(String... strings) {
//        URL url;
//        HttpURLConnection httpURLConnection;
//        try {
//            url = new URL(strings[0]);
//            httpURLConnection = (HttpURLConnection) url.openConnection();
//            InputStream inputStream = httpURLConnection.getInputStream();
//            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//            int  data= bufferedReader.read();
//            while (data != 1){
//
//            }
//
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
//}


}
