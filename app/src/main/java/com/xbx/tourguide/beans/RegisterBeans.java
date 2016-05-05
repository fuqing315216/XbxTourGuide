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
    private int guide_type;//1-导游；2-伴游；3-土著
    private String guide_card_number;
    private int guide_card_type;//0-普通导游证；1-领队导游证
    private String head_image;
    private String idcard_front;
    private String idcard_back;
    private String guide_card;
    private String guide_idcard;
    private int server_language;//0-中文，1-英文，2-双语
    private CityBeans city;

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRepassword(String repassword) {
        this.repassword = repassword;
    }

    public void setVerify_code(String verify_code) {
        this.verify_code = verify_code;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public void setGuide_type(int guide_type) {
        this.guide_type = guide_type;
    }

    public void setGuide_card_number(String guide_card_number) {
        this.guide_card_number = guide_card_number;
    }

    public void setGuide_card_type(int guide_card_type) {
        this.guide_card_type = guide_card_type;
    }

    public void setHead_image(String head_image) {
        this.head_image = head_image;
    }

    public void setIdcard_front(String idcard_front) {
        this.idcard_front = idcard_front;
    }

    public void setIdcard_back(String idcard_back) {
        this.idcard_back = idcard_back;
    }

    public void setGuide_card(String guide_card) {
        this.guide_card = guide_card;
    }

    public void setGuide_idcard(String guide_idcard) {
        this.guide_idcard = guide_idcard;
    }

    public void setServer_language(int server_language) {
        this.server_language = server_language;
    }

    public void setCity(CityBeans city) {
        this.city = city;
    }

    public String getMobile() {
        return mobile;
    }

    public String getPassword() {
        return password;
    }

    public String getRepassword() {
        return repassword;
    }

    public String getVerify_code() {
        return verify_code;
    }

    public String getRealname() {
        return realname;
    }

    public int getSex() {
        return sex;
    }

    public String getIdcard() {
        return idcard;
    }

    public int getGuide_type() {
        return guide_type;
    }

    public String getGuide_card_number() {
        return guide_card_number;
    }

    public int getGuide_card_type() {
        return guide_card_type;
    }

    public String getHead_image() {
        return head_image;
    }

    public String getIdcard_front() {
        return idcard_front;
    }

    public String getIdcard_back() {
        return idcard_back;
    }

    public String getGuide_card() {
        return guide_card;
    }

    public String getGuide_idcard() {
        return guide_idcard;
    }

    public int getServer_language() {
        return server_language;
    }

    public CityBeans getCity() {
        return city;
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
                ", guide_card_number='" + guide_card_number + '\'' +
                ", guide_card_type=" + guide_card_type +
                ", head_image='" + head_image + '\'' +
                ", idcard_front='" + idcard_front + '\'' +
                ", idcard_back='" + idcard_back + '\'' +
                ", guide_card='" + guide_card + '\'' +
                ", guide_idcard='" + guide_idcard + '\'' +
                ", server_language=" + server_language +
                ", city=" + city +
                '}';
    }
}
