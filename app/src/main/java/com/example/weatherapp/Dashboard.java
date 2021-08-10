package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Dashboard extends AppCompatActivity {
    EditText city;
    Button temp;
    TextView centi,fern,longi,leti;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        city = findViewById(R.id.city);
        temp=findViewById(R.id.temp);
        centi = findViewById(R.id.centi);
        fern = findViewById(R.id.fern);
        longi = findViewById(R.id.longi);
        leti = findViewById(R.id.leti);

        temp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                temperature();
            }
        });


    }

    private void temperature() {
        String City = city.getText().toString();
        String url = "https://api.weatherapi.com/v1/current.json?key=35c9f92ac5bf4df0811144140212307&q=CityName-"+City+"&aqi=no";

        RequestQueue queue = Volley.newRequestQueue(Dashboard.this);

        JsonObjectRequest ObjectRequest = new JsonObjectRequest(Request.Method.GET,
                url,null,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("StringResponse", response.toString());
                try {
                    Double Celcius = response.getJSONObject("current").getDouble("temp_c");
                    Double Farenhite = response.getJSONObject("current").getDouble("temp_f");
                    Double latitude = response.getJSONObject("location").getDouble("lat");
                    Double longitude = response.getJSONObject("location").getDouble("lon");
                    centi.setText("Temperature in centigrade : " +Celcius);
                    fern.setText("Temperature in fahrenheit : " +Farenhite);
                    longi.setText("Longitude : "+longitude);
                    leti.setText("Latitude : "+latitude);
                } catch (JSONException jsonException) {
                    jsonException.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("pincodesError",error.toString());
                Toast.makeText(Dashboard.this, "city is not valid!", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(ObjectRequest);


    }
}