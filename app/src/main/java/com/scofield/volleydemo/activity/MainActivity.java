package com.scofield.volleydemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.scofield.volleydemo.R;
import com.scofield.volleydemo.bean.CityBean;
import com.scofield.volleydemo.bean.UserBean;
import com.scofield.volleydemo.http.UICallBack;
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
                ImageLoader.getInstance(MainActivity.this).loadImage(url, iv);
            }
        });

    }


    private void getCityInfoPost() {

        String url = "http://192.168.31.185:8080/deplay/api/common/list_cities";
        Map<String, String> map = new HashMap<>();
        map.put("country", "japan");
        new VolleyHelper().jsonRequest(this, url, map, new TypeToken<List<CityBean>>() {
        }.getType(), new UICallBack() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(Object data) {
                List<CityBean> cityBeanList = (List<CityBean>) data;
                for (CityBean cityBean : cityBeanList) {
                    Log.e(TAG, "cityBean: " + cityBean.getCountry());
                }
            }

            @Override
            public void onFail(int code, String message) {

            }
        });

    }


    private void getProfilePost() {
        String url = "http://192.168.31.185:8080/deplay/api/common/get_user_info";
        Map<String, String> params = new HashMap<>();
        params.put("uid", "12");

        VolleyHelper volleyHelper = new VolleyHelper();
        //        volleyHelper.stringRequest(this, url, params, new VolleyHelper.OnNetCallback<String>() {
        //            @Override
        //            public void onSuccess(String result) {
        //                String string = result.toString();
        //                HttpResponse<UserBean> httpResponse = new Gson().fromJson(string, new TypeToken<HttpResponse<UserBean>>() {
        //                }.getType());
        //
        //                tv.setText(result.toString());
        //
        ////                Toast.makeText(MainActivity.this, "success: " + result.toString(), Toast.LENGTH_SHORT).show();
        //            }
        //
        //            @Override
        //            public void onFail(NetError error) {
        //                Toast.makeText(MainActivity.this, "error: " + error.toString(), Toast.LENGTH_SHORT).show();
        //            }
        //        });


        volleyHelper.jsonRequest(this, url, params, UserBean.class, new UICallBack() {
            @Override
            public void onStart() {
            }

            @Override
            public void onSuccess(Object result) {
                UserBean userBean = (UserBean) result;
                Toast.makeText(MainActivity.this, userBean.getUsername(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFail(int code, String message) {
                Toast.makeText(MainActivity.this, "code: " + code + "\nmessage: " + message, Toast.LENGTH_SHORT).show();
            }

        });

    }

}
