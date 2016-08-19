package com.scofield.volleydemo.other;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scofield.volleydemo.http.HttpResponse;

import java.lang.reflect.Type;

/**
 * @author scofield@tronsis.com
 * @date 2016/8/18 16:58
 */
public abstract class HttpCallBack {


    private Gson mGson;
    private Context mContext;

    private Type mResponseType;
    private int method;
    private String url;


    public HttpCallBack(Context context, Type mResponseType, int method, String url) {
        this.mContext = context;
        this.mResponseType = mResponseType;
        this.method = method;
        this.url = url;
    }

    private void requestPost(){
        onStart();
        StringRequest stringRequest = new StringRequest(method, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                HttpResponse httpResponse = new Gson().fromJson(response, new TypeToken<HttpResponse>(){}.getType());
                Object result = new Gson().fromJson(new Gson().toJson(httpResponse.getData()), mResponseType);
                onCallBack(httpResponse.getHeader().getStatus(), result);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               onCallBack(error.networkResponse.statusCode,null);
            }
        });
    }


    /**
     * 请求开始
     */
    public abstract void onStart();

    /**
     * 请求成功回调
     *
     * @param status 请求状态码
     * @param result 请求成功的返回值
     */
    public abstract void onCallBack(int status, Object result);


}
