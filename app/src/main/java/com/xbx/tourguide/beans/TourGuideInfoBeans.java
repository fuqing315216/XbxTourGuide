package com.xbx.tourguide.beans;

import java.io.Serializable;

/**
 * Created by shuzhen on 2016/4/6.
 */
public class TourGuideInfoBeans implements Serializable {

    private String mobile;
    private String realname;
    private String idcard;
    private String head_image;
    private String birthday;
    private String sex;
    private String guide_type;//导游类型：1-导游；2-伴游；3-土著
    private String guide_card_number;
    private String stars;
    private String is_auth;//认证状态：0-未认证；1认证通过；2-认证失败
    private String server_language;
    private String now_address_name;
    private String unread_order;
    private String unread_message;
    private String is_online;

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public void setHead_image(String head_image) {
        this.head_image = head_image;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setGuide_type(String guide_type) {
        this.guide_type = guide_type;
    }

    public void setGuide_card_number(String guide_card_number) {
        this.guide_card_number = guide_card_number;
    }

    public void setStars(String stars) {
        this.stars = stars;
    }

    public void setIs_auth(String is_auth) {
        this.is_auth = is_auth;
    }

    public void setServer_language(String server_language) {
        this.server_language = server_language;
    }

    public void setNow_address_name(String now_address_name) {
        this.now_address_name = now_address_name;
    }

    public void setUnread_order(String unread_order) {
        this.unread_order = unread_order;
    }

    public void setUnread_message(String unread_message) {
        this.unread_message = unread_message;
    }

    public void setIs_online(String is_online) {
        this.is_online = is_online;
    }

    public String getMobile() {
        return mobile;
    }

    public String getRealname() {
        return realname;
    }

    public String getIdcard() {
        return idcard;
    }

    public String getHead_image() {
        return head_image;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getSex() {
        return sex;
    }

    public String getGuide_type() {
        return guide_type;
    }

    public String getGuide_card_number() {
        return guide_card_number;
    }

    public String getStars() {
        return stars;
    }

    public String getIs_auth() {
        return is_auth;
    }

    public String getServer_language() {
        return server_language;
    }

    public String getNow_address_name() {
        return now_address_name;
    }

    public String getUnread_order() {
        return unread_order;
    }

    public String getUnread_message() {
        return unread_message;
    }

    public String getIs_online() {
        return is_online;
    }

    @Override
    public String toString() {
        return "TourGuideInfoBeans{" +
                "mobile='" + mobile + '\'' +
                ", realname='" + realname + '\'' +
                ", idcard='" + idcard + '\'' +
                ", head_image='" + head_image + '\'' +
                ", birthday='" + birthday + '\'' +
                ", sex='" + sex + '\'' +
                ", guide_type='" + guide_type + '\'' +
                ", guide_card_number='" + guide_card_number + '\'' +
                ", stars='" + stars + '\'' +
                ", is_auth='" + is_auth + '\'' +
                ", server_language='" + server_language + '\'' +
                ", now_address_name='" + now_address_name + '\'' +
                ", unread_order='" + unread_order + '\'' +
                ", unread_message='" + unread_message + '\'' +
                ", is_online='" + is_online + '\'' +
                '}';
    }
}
