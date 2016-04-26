package com.xbx.tourguide.beans;

import java.io.Serializable;

/**
 * Created by shuzhen on 2016/4/5.
 */
public class RegisterBeans implements Serializable {

    private String mobile;
    private String password;
    private String repassword;
    private String verify_code;
    private String realname;
    private int sex;//0：男；1：女
    private String idcard;
    private int guide_type;
    private String guide_number;
    private int user_type;//1：导游；2：伴游；3：土著
    private String head_image;
    private String idcard_front;
    private String idcard_back;
    private String guide_card;
    private String guide_idcard;
    private int language;
    private CityBeans city;

    public int getLanguage() {
        return language;
    }

    public void setLanguage(int language) {
        this.language = language;
    }

    public CityBeans getCity() {
        return city;
    }

    public void setCity(CityBeans city) {
        this.city = city;
    }

    public String getHead_image() {
        return head_image;
    }

    public void setHead_image(String head_image) {
        this.head_image = head_image;
    }

    public String getIdcard_front() {
        return idcard_front;
    }

    public void setIdcard_front(String idcard_front) {
        this.idcard_front = idcard_front;
    }

    public String getIdcard_back() {
        return idcard_back;
    }

    public void setIdcard_back(String idcard_back) {
        this.idcard_back = idcard_back;
    }

    public String getGuide_card() {
        return guide_card;
    }

    public void setGuide_card(String guide_card) {
        this.guide_card = guide_card;
    }

    public String getGuide_idcard() {
        return guide_idcard;
    }

    public void setGuide_idcard(String guide_idcard) {
        this.guide_idcard = guide_idcard;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepassword() {
        return repassword;
    }

    public void setRepassword(String repassword) {
        this.repassword = repassword;
    }

    public String getVerify_code() {
        return verify_code;
    }

    public void setVerify_code(String verify_code) {
        this.verify_code = verify_code;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public int getGuide_type() {
        return guide_type;
    }

    public void setGuide_type(int guide_type) {
        this.guide_type = guide_type;
    }

    public String getGuide_number() {
        return guide_number;
    }

    public void setGuide_number(String guide_number) {
        this.guide_number = guide_number;
    }

    public int getUser_type() {
        return user_type;
    }

    public void setUser_type(int user_type) {
        this.user_type = user_type;
    }

    @Override
    public String toString() {
        return "RegisterBeans{" +
                "mobile='" + mobile + '\'' +
                ", password='" + password + '\'' +
                ", repassword='" + repassword + '\'' +
                ", verify_code='" + verify_code + '\'' +
                ", realname='" + realname + '\'' +
                ", sex=" + sex +
                ", idcard='" + idcard + '\'' +
                ", guide_type=" + guide_type +
                ", guide_number='" + guide_number + '\'' +
                ", user_type=" + user_type +
                ", head_image='" + head_image + '\'' +
                ", idcard_front='" + idcard_front + '\'' +
                ", idcard_back='" + idcard_back + '\'' +
                ", guide_card='" + guide_card + '\'' +
                ", guide_idcard='" + guide_idcard + '\'' +
                ", language=" + language +
                ", city=" + city +
                '}';
    }
}
