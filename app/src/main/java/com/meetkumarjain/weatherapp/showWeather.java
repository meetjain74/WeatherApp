package com.meetkumarjain.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class showWeather extends AppCompatActivity {
    private String temperature;
    private TextView textView;
    private double kelvin;
    private double celsius;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_weather);

        Intent intent=getIntent();
        String city = intent.getStringExtra("CityName");
        textView=findViewById(R.id.temperature);
        //Toast.makeText(this, city, Toast.LENGTH_SHORT).show();
        temperature="273.15";

        String url = "http://api.openweathermap.org/data/2.5/weather?q=+"+city+"&appid=e7686e25286c6ea2658372dca5029358";
        RequestQueue requestQueue=Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    temperature=response.getJSONObject("main").getString("temp");
                    kelvin=Double.parseDouble(temperature);
                    celsius=kelvin-273.15;
                    textView.setText(String.format("%.1f",celsius)+" °C");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Mytag","Error occured "+error.toString());
                Toast.makeText(showWeather.this, "Invalid city name", Toast.LENGTH_SHORT).show();
                textView.setText("Error");
            }
        });

        requestQueue.add(jsonObjectRequest);
    }

}