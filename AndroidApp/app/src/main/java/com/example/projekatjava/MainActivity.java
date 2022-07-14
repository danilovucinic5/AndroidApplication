package com.example.projekatjava;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private  ArrayList<String> currencies ;
    private  ListView listView;
    private Button next,previous;
    private Pagination pagination;
    private int lastPage,currentPage=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        currencies =new ArrayList<>();

        listView= findViewById(R.id.listview);
        next=findViewById(R.id.next);
        previous=findViewById(R.id.prev);
        getData();

    }



    void getData() {

        String url = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest";
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray dataArray = response.getJSONArray("data");
                    for (int i = 0; i < 100; i++) {
                        JSONObject dataObj = dataArray.getJSONObject(i);
                        String name = dataObj.getString("name");
                        currencies.add(name);

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "Something went amiss. Please try again later", Toast.LENGTH_SHORT).show();
                }
                pagination=new Pagination(8,currencies);
                lastPage=pagination.getLastPage();
                updateData();

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Something went amiss. Please try again later", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("X-CMC_PRO_API_KEY", "20fc7e0c-1877-47df-85e4-8a3d0c620ce0");
                // at last returning headers
                return headers;
            }
        };
        queue.add(jsonObjectRequest);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentPage+=1;
                updateData();
            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentPage-=1;
                updateData();
            }
        });
    }

    private void updateData() {
        listView.setAdapter(new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1,pagination.generateData(currentPage)));
        updateButtons();
    }

    private void updateButtons() {
        if(currentPage==0)
        {
            next.setEnabled(true);
            previous.setEnabled(false);
        }
        else if(currentPage==lastPage)
        {
            next.setEnabled(false);
            previous.setEnabled(true);
        }
        else
        {
            next.setEnabled(true);
            previous.setEnabled(true);
        }

    }

}