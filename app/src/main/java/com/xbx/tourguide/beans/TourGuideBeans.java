package com.xbx.tourguide.beans;

import java.io.Serializable;

/**
 * Created by shuzhen on 2016/4/6.
 */
public class TourGuideBeans implements Serializable {

    private String uid;
    private String login_token;
    private TourGuideInfoBeans user_info;

    private UnFinishedOrderBeans user_unfinished_order;

    public UnFinishedOrderBeans getUser_unfinished_order() {
        return user_unfinished_order;
    }

    public void setUser_unfinished_order(UnFinishedOrderBeans user_unfinished_order) {
        this.user_unfinished_order = user_unfinished_order;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getLogin_token() {
        return login_token;
    }

    public void setLogin_token(String login_token) {
        this.login_token = login_token;
    }

    public TourGuideInfoBeans getUser_info() {
        return user_info;
    }

    public void setUser_info(TourGuideInfoBeans user_info) {
        this.user_info = user_info;
    }

    @Override
    public String toString() {
        return "TourGuideBeans{" +
                "uid='" + uid + '\'' +
                ", login_token='" + login_token + '\'' +
                ", user_info=" + user_info +
                ", user_unfinished_order=" + user_unfinished_order +
                '}';
    }
}
