package com.scofield.volleydemo.other;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scofield.volleydemo.http.HttpResponse;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

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

    /**
     * 请求失败
     *
     * @param exception
     */
    private void onRequestFailure(IOException exception) {
        exception.printStackTrace();
        if (exception != null && exception instanceof ConnectException) {
            onNetError();
        } else if (exception != null && exception instanceof SocketTimeoutException) {
            onConnectTimeOut();
        } else {
            onServerError();
        }
    }


    /**
     * 服务器出现错误
     */
    public void onServerError() {
        Toast.makeText(mContext, "服务器出错", Toast.LENGTH_SHORT).show();
        onCallBack(5000, null);
    }

    ;

    /**
     * 连接超时
     */
    public void onConnectTimeOut() {
        Toast.makeText(mContext, "连接超时", Toast.LENGTH_SHORT).show();
        onCallBack(5001, null);
    }

    /**
     * 网络错误
     */
    public void onNetError() {
        Toast.makeText(mContext, "网络错误", Toast.LENGTH_SHORT).show();
        onCallBack(5002, null);
    }
}
