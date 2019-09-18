package com.personal.weathercity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    public class weatherAPI extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... url) {
            URL apiURL;
            HttpURLConnection apiConnect = null;
            String result = "";
            try {
                apiURL = new URL(url[0]);
                apiConnect = (HttpURLConnection) apiURL.openConnection();

                InputStream in = apiConnect.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();
                while (data != -1) {
                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try{
                JSONObject jsonObject = new JSONObject(s);
                String weatherInfo = jsonObject.getString("weather");

                JSONArray jsonArray = new JSONArray(weatherInfo);

                JSONObject jsonObject1 = new JSONObject(jsonArray.getString(0));
                String main = jsonObject1.getString("main");
                String desc = jsonObject1.getString("description");
                TextView apiResult = findViewById(R.id.apiResult);
                apiResult.setText(main + ',' + desc);

            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }


    public void goButtonFunction(View view){
        EditText inputCity = findViewById(R.id.cityInput);
        String cityText = String.valueOf(inputCity.getText());

        if(cityText != ""){
            String url = "http://api.openweathermap.org/data/2.5/weather?q=" + cityText  + "&appid=ce8d34865e756e3ef622f5fb0fb66060";
            weatherAPI newTask = new weatherAPI();
            newTask.execute(url);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }
}
