package com.example.trackcovid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.leo.simplearcloader.SimpleArcLoader;

import org.eazegraph.lib.models.PieModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class effectedcountries extends AppCompatActivity {
    EditText edtsearch;
    ListView listView;
    SimpleArcLoader loader1;
    public static List<CountryModel> countryModelList = new ArrayList<>();
    public static List<CountryModel> fil = new ArrayList<>();

    CountryModel countryModel;
    CustomAdapter mycustomadapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_effectedcountries);
        edtsearch = findViewById(R.id.edtsearch);
        listView = findViewById(R.id.listView);
        loader1 = findViewById(R.id.loader1);
//        getSupportActionBar().setTitle("Affected countries");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
        fetchData();
//        edtsearch.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                mycustomadapter.getFilter().filter(s);
//                mycustomadapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
    }



    private void fetchData() {
        String url = "https://corona.lmao.ninja/v2/countries/";
        loader1.start();
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String countryname = jsonObject.getString("country");
                        String cases = jsonObject.getString("cases");
                        String todaycases = jsonObject.getString("todayCases");
                        String deaths = jsonObject.getString("deaths");
                        String todaydeaths = jsonObject.getString("todayDeaths");
                        String recovered = jsonObject.getString("recovered");
                        String active = jsonObject.getString("active");
                        String critical = jsonObject.getString("critical");

                        JSONObject jsonObjectinfo = jsonObject.getJSONObject("countryInfo");
                        String flagUri = jsonObjectinfo.getString("flag");
                        countryModel = new CountryModel(flagUri, countryname, cases, todaycases, deaths, todaydeaths, recovered, active, critical);
                        countryModelList.add(countryModel);
                    }
                    mycustomadapter =new CustomAdapter(effectedcountries.this, countryModelList);
                    listView.setAdapter(mycustomadapter);
                    loader1.stop();
                    loader1.setVisibility(View.GONE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loader1.stop();
                loader1.setVisibility(View.GONE);

                Toast.makeText(effectedcountries.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
}