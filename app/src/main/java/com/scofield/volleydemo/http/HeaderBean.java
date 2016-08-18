package com.scofield.volleydemo.http;

import java.io.Serializable;

public class HeaderBean implements Serializable{

    /**
     * status : 1000
     * message : success
     * result_count : 1
     */

    private int status;
    private String message;
    private int result_count;

    public int getStatus() { return status;}

    public void setStatus(int status) { this.status = status;}

    public String getMessage() { return message;}

    public void setMessage(String message) { this.message = message;}

    public int getResult_count() { return result_count;}

    public void setResult_count(int result_count) { this.result_count = result_count;}

    @Override
    public String toString() {
        return "HeaderBean:[status:" + status + ", message:" + message + ", count:" + result_count + "]";
    }
}
