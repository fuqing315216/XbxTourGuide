package com.xbx.tourguide.beans;

import java.io.Serializable;

/**
 * Created by shuzhen on 2016/4/6.
 */
public class TourGuideInfoBeans implements Serializable {

    private String mobile;
    private String email;
    private String realname;
    private String nickname;
    private String birthday;
    private String sex;
    private String idcard;
    private String head_image;
    private String guide_type;
    private String guide_number;
    private String star;
    private String user_type;
    private String is_online;
    private String unread_order;
    private String unread_message;
    private String now_address_name;
    private String now_address;
    private String server_language;
    private String is_auth;

    public String getIs_auth() {
        return is_auth;
    }

    public void setIs_auth(String is_auth) {
        this.is_auth = is_auth;
    }

    public String getIs_online() {
        return is_online;
    }

    public void setIs_online(String is_online) {
        this.is_online = is_online;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getHead_image() {
        return head_image;
    }

    public void setHead_image(String head_image) {
        this.head_image = head_image;
    }

    public String getGuide_type() {
        return guide_type;
    }

    public void setGuide_type(String guide_type) {
        this.guide_type = guide_type;
    }

    public String getGuide_number() {
        return guide_number;
    }

    public void setGuide_number(String guide_number) {
        this.guide_number = guide_number;
    }

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }

    public String getUnread_order() {
        return unread_order;
    }

    public void setUnread_order(String unread_order) {
        this.unread_order = unread_order;
    }

    public String getUnread_message() {
        return unread_message;
    }

    public void setUnread_message(String unread_message) {
        this.unread_message = unread_message;
    }

    public void setNow_address_name(String now_address_name) {
        this.now_address_name = now_address_name;
    }

    public void setNow_address(String now_address) {
        this.now_address = now_address;
    }

    public void setServer_language(String server_language) {
        this.server_language = server_language;
    }

    public String getNow_address_name() {

        return now_address_name;
    }

    public String getNow_address() {
        return now_address;
    }

    public String getServer_language() {
        return server_language;
    }

    @Override
    public String toString() {
        return "TourGuideInfoBeans{" +
                "mobile='" + mobile + '\'' +
                ", email='" + email + '\'' +
                ", realname='" + realname + '\'' +
                ", nickname='" + nickname + '\'' +
                ", birthday='" + birthday + '\'' +
                ", sex='" + sex + '\'' +
                ", idcard='" + idcard + '\'' +
                ", head_image='" + head_image + '\'' +
                ", guide_type='" + guide_type + '\'' +
                ", guide_number='" + guide_number + '\'' +
                ", star='" + star + '\'' +
                ", user_type='" + user_type + '\'' +
                ", is_online='" + is_online + '\'' +
                ", unread_order='" + unread_order + '\'' +
                ", unread_message='" + unread_message + '\'' +
                ", now_address_name='" + now_address_name + '\'' +
                ", now_address='" + now_address + '\'' +
                ", server_language='" + server_language + '\'' +
                ", is_auth='" + is_auth + '\'' +
                '}';
    }
}
