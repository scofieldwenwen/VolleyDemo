package com.scofield.volleydemo.bean;

import java.io.Serializable;

/**
 * UserBean
 *
 * @author allen@tronsis.com
 * @date 8/3/2016 11:37 AM
 */

public class UserBean implements Serializable {


    /**
     * id : 1
     * username : 18888888888
     * token : a1sd3asd3a4sd3
     * nickName : Moon
     * realName : 呵呵
     * address : 广东深圳
     * email : 123@123.com
     * phone : 1321456124
     * wechat : 1321456124
     * age : 12
     * gender : other
     * language : en,zn
     * profession : teacher
     * education : bachelor
     * hobbies : sleeping
     * avator : /USER/54654654654
     * status : vd
     * timestamp : 1469758271000
     */
    private int _id;
    private long id;
    private String username;
    private String token;
    private String nickName;
    private String realName;
    private String address;
    private String email;
    private String phone;
    private String wechat;
    private int age;
    private String gender;
    private String language;
    private String profession;
    private String education;
    private String hobbies;
    private String avator;
    private String status;
    private long timestamp;

    public UserBean() {
    }

    public UserBean(int _id, long id, String username, String token, String nickName, String realName, String address, String email, String phone, String
            wechat, int age, String gender, String language, String profession, String education, String hobbies, String avator, String status, long
            timestamp) {
        this._id = _id;
        this.id = id;
        this.username = username;
        this.token = token;
        this.nickName = nickName;
        this.realName = realName;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.wechat = wechat;
        this.age = age;
        this.gender = gender;
        this.language = language;
        this.profession = profession;
        this.education = education;
        this.hobbies = hobbies;
        this.avator = avator;
        this.status = status;
        this.timestamp = timestamp;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getHobbies() {
        return hobbies;
    }

    public void setHobbies(String hobbies) {
        this.hobbies = hobbies;
    }

    public String getAvator() {
        return avator;
    }

    public void setAvator(String avator) {
        this.avator = avator;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
