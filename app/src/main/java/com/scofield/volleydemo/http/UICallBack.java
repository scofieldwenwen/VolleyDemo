package com.scofield.volleydemo.http;

/**
 * 网络范围回调
 * @author scofield@tronsis.com
 * @date 2016/8/18 15:57
 */
public interface UICallBack {

    void onStart();

    void onSuccess(Object data);

    void onFailure(int code);
}
