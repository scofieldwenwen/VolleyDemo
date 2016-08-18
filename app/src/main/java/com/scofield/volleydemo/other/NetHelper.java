package com.scofield.volleydemo.other;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * @author scofield@tronsis.com
 * @date 2016/8/17 11:29
 */
public class NetHelper {

    private static RequestQueue mRequestQueue;
    private Context mContext;

    public static RequestQueue getRequestQueue(Context context) {
        if (mRequestQueue == null) {
            synchronized (NetHelper.class){
                if (mRequestQueue != null) {
                    mRequestQueue = Volley.newRequestQueue(context);
                }
            }
        }
        return mRequestQueue;
    }

}
