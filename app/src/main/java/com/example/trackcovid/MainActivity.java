package com.example.trackcovid;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.leo.simplearcloader.SimpleArcLoader;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONException;
import org.json.JSONObject;

import static com.android.volley.Request.Method.GET;

public class MainActivity extends AppCompatActivity {
    TextView tvcasses, tvrecover, tvcritical, tvactive, tvtodaycases, tvtotaldeaths, tvtodaydeaths, tveffectedcountries;
    ScrollView scrollstats;
    PieChart piechart;
    SimpleArcLoader loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvcasses = findViewById(R.id.tvcasses);
        tvrecover = findViewById(R.id.tvrecovered);
        tvcritical = findViewById(R.id.tvcritical);
        tvactive = findViewById(R.id.tvactive);
        tvtodaycases = findViewById(R.id.tvtodaycases);
        tvtotaldeaths = findViewById(R.id.tvtotaldeaths);
        tvtodaydeaths = findViewById(R.id.tvtodaydeaths);
        tveffectedcountries = findViewById(R.id.tveffectedcountries);
        scrollstats = findViewById(R.id.scrollstats);
        piechart = findViewById(R.id.piechart);
        loader = findViewById(R.id.loader);
        fetchData();
    }

    private void fetchData() {
        String url = "https://corona.lmao.ninja/v2/all/";
        loader.start();
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response.toString());
                    tvcasses.setText(jsonObject.getString("cases"));
                    tvrecover.setText(jsonObject.getString("recovered"));
                    tvcritical.setText(jsonObject.getString("critical"));
                    tvactive.setText(jsonObject.getString("active"));
                    tvtodaycases.setText(jsonObject.getString("todayCases"));
                    tvtotaldeaths.setText(jsonObject.getString("deaths"));
                    tvtodaydeaths.setText(jsonObject.getString("todayDeaths"));
                    tveffectedcountries.setText(jsonObject.getString("affectedCountries"));
                    piechart.addPieSlice(new PieModel("Cases", Integer.parseInt(tvcasses.getText().toString()), Color.parseColor("#E1972A")));
                    piechart.addPieSlice(new PieModel("Recovered", Integer.parseInt(tvrecover.getText().toString()), Color.parseColor("#4CAF50")));
                    piechart.addPieSlice(new PieModel("Total deaths", Integer.parseInt(tvtotaldeaths.getText().toString()), Color.parseColor("#CF2317")));
                    piechart.addPieSlice(new PieModel("Active", Integer.parseInt(tvactive.getText().toString()), Color.parseColor("#03A9F4")));
                    piechart.startAnimation();
                    loader.stop();
                    loader.setVisibility(View.GONE);
                    scrollstats.setVisibility(View.VISIBLE);
                } catch (JSONException e) {
                    loader.stop();
                    loader.setVisibility(View.GONE);
                    scrollstats.setVisibility(View.VISIBLE);
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loader.stop();
                loader.setVisibility(View.GONE);
                scrollstats.setVisibility(View.VISIBLE);
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    public void goTrackCountries(View view) {
        Intent intent = new Intent(MainActivity.this, effectedcountries.class);
        startActivity(intent);

    }
}