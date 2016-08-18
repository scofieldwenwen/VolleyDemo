package com.scofield.volleydemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scofield.volleydemo.R;
import com.scofield.volleydemo.bean.CityBean;
import com.scofield.volleydemo.bean.UserBean;
import com.scofield.volleydemo.http.HeaderBean;
import com.scofield.volleydemo.http.HttpResponse;
import com.scofield.volleydemo.http.NetError;
import com.scofield.volleydemo.http.VolleyHelper;
import com.scofield.volleydemo.imageloader.ImageLoader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.tv);
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCityInfoPost();
            }
        });


        findViewById(R.id.btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getProfilePost();
            }
        });

        findViewById(R.id.btn3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://tronsis.com/web/cms/images/website/icon/qr_code.png";
                ImageView iv = (ImageView) findViewById(R.id.iv);
                ImageLoader.getInstance(MainActivity.this).loadImage(url,iv);
            }
        });

    }


    private void getCityInfoPost() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "http://192.168.31.185:8080/deplay/api/common/list_cities";

        final Response.Listener listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                HttpResponse httpResponse = new Gson().fromJson(response, new TypeToken<HttpResponse>(){}.getType());
                Object  result = new Gson().fromJson(new Gson().toJson(httpResponse.getData()), new TypeToken<List<CityBean>>(){}.getType());

                List<CityBean> cityBeanList = (List<CityBean>) result;
                HeaderBean headerBean = httpResponse.getHeader();
                Log.e(TAG, "headerBean: " + headerBean.toString());

                tv.setText(response);

                for (CityBean cityBean : cityBeanList) {
                    Log.e(TAG, "cityBean: " + cityBean.getCountry());
                }

            }
        };


        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        };

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, listener, errorListener) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("country", "japan");
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }


    private void getProfilePost() {
        String url = "http://192.168.31.185:8080/deplay/api/common/get_user_info";
        Map<String, String> params = new HashMap<>();
        params.put("uid", "12");

        VolleyHelper volleyHelper = new VolleyHelper();
        volleyHelper.stringRequest(this, url, params, new VolleyHelper.OnNetCallback<String>() {
            @Override
            public void onSuccess(String result) {
                String string = result.toString();
                HttpResponse<UserBean> httpResponse = new Gson().fromJson(string, new TypeToken<HttpResponse<UserBean>>() {
                }.getType());

                tv.setText(result.toString());

//                Toast.makeText(MainActivity.this, "success: " + result.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFail(NetError error) {
                Toast.makeText(MainActivity.this, "error: " + error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}
