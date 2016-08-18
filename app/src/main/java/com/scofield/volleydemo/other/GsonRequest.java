package com.scofield.volleydemo.other;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

/**
 * @author scofield@tronsis.com
 * @date 2016/8/18 16:24
 */
public abstract class GsonRequest<T> extends Request<T> {

    private final Response.Listener<T> mListener;

    private Gson mGson;
    private Context mContext;
    private Class<T> mClass;

    private Type mResponseType;

    public GsonRequest(int method, String url, Class<T> clazz, Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        mGson = new Gson();
        mClass = clazz;
        mListener = listener;
    }

    public GsonRequest(String url, Class<T> clazz, Response.Listener<T> listener, Response.ErrorListener errorListener) {
        this(Method.GET, url, clazz, listener, errorListener);
    }


    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            return Response.success(mGson.fromJson(jsonString, mClass), HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(T response) {
        mListener.onResponse(response);
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