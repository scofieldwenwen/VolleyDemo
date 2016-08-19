package com.scofield.volleydemo.other;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scofield.volleydemo.http.HttpResponse;
import com.scofield.volleydemo.http.UICallBack;

import java.lang.reflect.Constructor;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * @author scofield@tronsis.com
 * @date 2016/8/17 11:29
 */
public abstract class RequestClient {

    private static RequestQueue mRequestQueue;
    private Context mContext;
    private Map<String, String> paramsMap = new HashMap<>();


    public static RequestQueue getRequestQueue(Context context) {
        if (mRequestQueue == null) {
            synchronized (RequestClient.class) {
                if (mRequestQueue != null) {
                    mRequestQueue = Volley.newRequestQueue(context);
                }
            }
        }
        return mRequestQueue;
    }


    /**
     * json 网络请求(使用Gson)
     *
     * @param context
     * @param url
     * @param params
     * @param dataType
     * @param callback
     * @author scofield@tronsis.com
     */
    public void jsonRequest(Context context, String url, Map<String, String> params, Type dataType, UICallBack callback) {
        mContext = context;
        makeJsonRequest(context, StringRequestImpl.class, url, params, dataType, callback);
    }


    /**
     * json网络请求(使用Gson)
     *
     * @param context
     * @param clazz
     * @param url
     * @param params
     * @param dataType
     * @param callback
     * @author scofield@tronsis.com
     */
    protected void makeJsonRequest(final Context context, Class<? extends Request> clazz, String url, final Map<String, String> params, final Type dataType,
                                   final UICallBack callback) {
        //网络请求
        Request request = null;
        //失败回调
        Response.ErrorListener errorListener = null;
        //成功回调
        Response.Listener listener = null;
        //判空
        if (callback != null) {

            //请求成功
            listener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (context instanceof Activity && (((Activity) (context)).isFinishing())) {
                        Log.i("http", "activity finish, not callback");
                        return;
                    }

                    HttpResponse httpResponse = new Gson().fromJson(response, new TypeToken<HttpResponse>() {
                    }.getType());
                    Object result = null;
                    if (httpResponse != null) {
                        result = new Gson().fromJson(new Gson().toJson(httpResponse.getData()), dataType);
                    }
                    Log.e("http", response);
                    callback.onSuccess(result);
                }
            };

            //请求失败
            errorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (context instanceof Activity && (((Activity) (context)).isFinishing())) {
                        Log.i("http", "activity finish, not callback");
                        return;
                    }
                    int errorCode = onRequestFailure(error);
                    callback.onFail(errorCode, error.toString());
                }
            };

        }


        //启动网络请求
        if (clazz == ImageRequest.class) {
            throw new IllegalArgumentException("please use imageloader");
        } else {
            try {
                Constructor constructor = clazz.getConstructor(Integer.class, String.class, Response.Listener.class, Response.ErrorListener.class, Map.class);
                int method = Request.Method.GET;
                if (params != null)
                    method = Request.Method.POST;
                request = (Request) constructor.newInstance(method, url, listener, errorListener, params);
            } catch (Exception e) {
                Log.e("http", "error reflect", e);
                return;
            }
        }

        //自定义超时时间，重试次数
        //        request.setRetryPolicy(new DefaultRetryPolicy());
        getRequestQueue(mContext).add(request);
    }


    /**
     * 请求失败
     *
     * @param exception
     */
    private int onRequestFailure(VolleyError exception) {
        exception.printStackTrace();
        if (exception != null && exception instanceof NetworkError) {
            //网络错误
            Toast.makeText(mContext, "网络错误", Toast.LENGTH_SHORT).show();
            return 5000;
        } else if (exception != null && exception instanceof TimeoutError) {
            //连接超时
            Toast.makeText(mContext, "连接超时", Toast.LENGTH_SHORT).show();
            return 5001;
        } else if (exception != null && exception instanceof NoConnectionError) {
            //没有网络
            Toast.makeText(mContext, "没有网络", Toast.LENGTH_SHORT).show();
            return 5002;
        } else {
            //服务器出现错误
            Toast.makeText(mContext, "服务器出现错误", Toast.LENGTH_SHORT).show();
            return 5002;
        }
    }


    /**
     * 添加需提交的参数对
     *
     * @param params 参数对
     * @return 当前对象
     * @throws Exception
     */
    public RequestClient addParameter(String... params) throws Exception {
        if (params != null) {
            if (params.length % 2 != 0) {
                throw new Exception("The length of the variable parameter is not even.");
            }

            for (int i = 0; i < params.length; i++) {
                if (TextUtils.isEmpty(params[i])) {
                    throw new Exception("The variable parameter is empty.");
                }
                String params2 = params[i + 1];
                if (params2 != null) {
                    paramsMap.put(params[i], params2);
                }
                i++;
            }
        }

        return this;
    }

    /**
     * 对{@linkplain StringRequest}的封装类
     */
    private static class StringRequestImpl extends StringRequest {
        private Map<String, String> params;

        public StringRequestImpl(Integer method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener, Map<String, String>
                params) {
            super(method, url, listener, errorListener);
            this.params = params;
        }

        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            return params;


        }
    }




//    /**
//     * 执行http请求
//     *
//     * @param method http请求方式
//     * @param url    请求URL全路径
//     */
//    public void excuteFullUrl( method, String url) {
//        switch (method) {
//            case POST: // post请求
//                post(url);
//                break;
//
//            case GET: // Get请求
//                get(url);
//                break;
//        }
//    }
//
//    /**
//     * GET请求
//     *
//     * @param url
//     */
//    private void get(String url) {
//        // 创建一个Request
//        final Request request = new Request.Builder().url(url).build();
//        Call call = mOkHttpClient.newCall(request);
//        // 请求加入调度
//        call.enqueue(mCallBack);
//        mCallBack.onStart();
//    }
//
//    /**
//     * POST请求
//     *
//     * @param url
//     */
//    private void post(String url) {
//        Request request = new Request.Builder().url(url).post(mRequestBody).build();
//        mOkHttpClient.newCall(request).enqueue(mCallBack);
//        mCallBack.onStart();
//    }
//
//    /**
//     * 获得绝对路径
//     *
//     * @param actionUrl
//     * @return
//     */
//    private String getAbsoluteUrl(String actionUrl) {
//        return HttpConfig.BASE_SERVER_PATH + actionUrl;
//    }


}