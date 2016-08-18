package com.scofield.volleydemo.http;

import java.io.Serializable;

/**
 * @author scofield@tronsis.com
 * @date 2016/8/15 17:39
 */
public class HttpResponse<T> implements Serializable {

    private HeaderBean header;

    private T data;

    public HeaderBean getHeader() { return header;}

    public void setHeader(HeaderBean header) { this.header = header;}

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
