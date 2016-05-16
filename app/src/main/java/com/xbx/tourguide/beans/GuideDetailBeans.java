package com.xbx.tourguide.beans;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xbx on 2016/4/25.
 */
public class GuideDetailBeans implements Serializable {
    private String realname;
    private String head_image;
    private String guide_card_number;
    private String guide_instant_price;
    private String guide_reserve_price;
    private String server_times;
    private String self_introduce;
    private String server_introduce;
    private String stars;
    private List<TagBeans> comment_tag_times;

    public String getGuide_card_number() {
        return guide_card_number;
    }

    public void setGuide_card_number(String guide_card_number) {
        this.guide_card_number = guide_card_number;
    }

    public String getRealname() {
        return realname;
    }

    public String getHead_image() {
        return head_image;
    }

    public String getGuide_instant_price() {
        return guide_instant_price;
    }

    public String getGuide_reserve_price() {
        return guide_reserve_price;
    }

    public String getServer_times() {
        return server_times;
    }

    public String getSelf_introduce() {
        return self_introduce;
    }

    public String getServer_introduce() {
        return server_introduce;
    }

    public String getStars() {
        return stars;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public void setHead_image(String head_image) {
        this.head_image = head_image;
    }

    public void setGuide_instant_price(String guide_instant_price) {
        this.guide_instant_price = guide_instant_price;
    }

    public void setGuide_reserve_price(String guide_reserve_price) {
        this.guide_reserve_price = guide_reserve_price;
    }

    public void setServer_times(String server_times) {
        this.server_times = server_times;
    }

    public void setSelf_introduce(String self_introduce) {
        this.self_introduce = self_introduce;
    }

    public void setServer_introduce(String server_introduce) {
        this.server_introduce = server_introduce;
    }

    public void setStars(String stars) {
        this.stars = stars;
    }

    public List<TagBeans> getComment_tag_times() {
        return comment_tag_times;
    }

    public void setComment_tag_times(List<TagBeans> comment_tag_times) {
        this.comment_tag_times = comment_tag_times;
    }

    @Override
    public String toString() {
        return "GuideDetailBeans{" +
                "realname='" + realname + '\'' +
                ", head_image='" + head_image + '\'' +
                ", guide_card_number='" + guide_card_number + '\'' +
                ", guide_instant_price='" + guide_instant_price + '\'' +
                ", guide_reserve_price='" + guide_reserve_price + '\'' +
                ", server_times='" + server_times + '\'' +
                ", self_introduce='" + self_introduce + '\'' +
                ", server_introduce='" + server_introduce + '\'' +
                ", star='" + stars + '\'' +
                ", comment_tag_times=" + comment_tag_times +
                '}';
    }
}
