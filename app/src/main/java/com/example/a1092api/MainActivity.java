package com.example.a1092api;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {
    static ArrayList<Map<String, String>> list = new ArrayList<>();
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        API api = new API(MainActivity.this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                api.getAllTopicApi();
            }
        });
    }
}

class API {
    Context context;
    API(Context context) {
        this.context = context;
    }

    //    String getAllTopic_url="https://opendata.cwb.gov.tw/api/v1/rest/datastore/F-C0032-001?Authorization=CWB-A4EC9953-6E71-4B5A-A748-852C52664EC3";
    String getAllTopic_url = "https://virtserver.swaggerhub.com/moontai0724/cmrdb-6th-hackathon/0.1.0/restaurants";

    public void getAllTopicApi() {
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, getAllTopic_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("BBB", "onResponse: " + response);
                try {
                    String str = new String(response.getBytes("ISO-8859-1"), "utf-8");
                    Log.d("BBB", "onResponse: " + str);
                    JSONObject object = new JSONObject(str);
                    String key = object.getString("success");
                    if (key.equals("true")) {
                        JSONArray jsonArray = object.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject data = jsonArray.getJSONObject(i);
                            String id = data.getString("id");
                            String description = data.getString("description");
                            String phone = data.getString("phone");
                            String address = data.getString("address");
                            String lowest_price = data.getString("lowest_price");
                            Log.d(TAG, "woeo: " + id);
                            Log.d(TAG, "woeo: " + description);
                            Log.d(TAG, "woeo: " + phone);
                            Log.d(TAG, "woeo: " + address);
                            Log.d(TAG, "woeo: " + lowest_price);
                        }
                    } else {
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("ABC", "onError" + e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ABC", "onErrorResponse: " + error);
            }
        }) {
        };
        queue.add(stringRequest);
    }
}
