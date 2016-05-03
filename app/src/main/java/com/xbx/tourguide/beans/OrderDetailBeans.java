package com.xbx.tourguide.beans;

import java.io.Serializable;

/**
 * Created by shuzhen on 2016/4/11.
 */
public class OrderDetailBeans implements Serializable {

    private String nickname;
    private String head_image;
    private String mobile;
    private String server_status;
    private String pay_status;
    private String order_time;
    private String order_money;
    private String rebate_money;
    private String tip_money;
    private String pay_money;
    private String pay_type;
    private String server_start_time;
    private String server_end_time;
    private String numbers;
    private String end_addr;
    private String content;
    private String star;
    private String[] tag;
    private String order_number;
    private String server_type;//0-即使服务，1-预约服务
    private String order_status;
    private String server_date;

    public String getServer_date() {
        return server_date;
    }


    public void setServer_date(String server_date) {
        this.server_date = server_date;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public String getServer_type() {
        return server_type;
    }

    public void setServer_type(String server_type) {
        this.server_type = server_type;
    }

    public String getOrder_number() {
        return order_number;
    }

    public void setOrder_number(String order_number) {
        this.order_number = order_number;
    }

    public String getEnd_addr() {
        return end_addr;
    }

    public void setEnd_addr(String end_addr) {
        this.end_addr = end_addr;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }

    public String[] getTag() {
        return tag;
    }

    public void setTag(String[] tag) {
        this.tag = tag;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHead_image() {
        return head_image;
    }

    public void setHead_image(String head_image) {
        this.head_image = head_image;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getServer_status() {
        return server_status;
    }

    public void setServer_status(String server_status) {
        this.server_status = server_status;
    }

    public String getPay_status() {
        return pay_status;
    }

    public void setPay_status(String pay_status) {
        this.pay_status = pay_status;
    }

    public String getOrder_time() {
        return order_time;
    }

    public void setOrder_time(String order_time) {
        this.order_time = order_time;
    }

    public String getOrder_money() {
        return order_money;
    }

    public void setOrder_money(String order_money) {
        this.order_money = order_money;
    }

    public String getRebate_money() {
        return rebate_money;
    }

    public void setRebate_money(String rebate_money) {
        this.rebate_money = rebate_money;
    }

    public String getTip_money() {
        return tip_money;
    }

    public void setTip_money(String tip_money) {
        this.tip_money = tip_money;
    }

    public String getPay_money() {
        return pay_money;
    }

    public void setPay_money(String pay_money) {
        this.pay_money = pay_money;
    }

    public String getPay_type() {
        return pay_type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
    }

    public String getServer_start_time() {
        return server_start_time;
    }

    public void setServer_start_time(String server_start_time) {
        this.server_start_time = server_start_time;
    }

    public String getServer_end_time() {
        return server_end_time;
    }

    public void setServer_end_time(String server_end_time) {
        this.server_end_time = server_end_time;
    }

    public String getNumbers() {
        return numbers;
    }

    public void setNumbers(String numbers) {
        this.numbers = numbers;
    }

    @Override
    public String toString() {
        return "OrderDetailBeans{" +
                "nickname='" + nickname + '\'' +
                ", head_image='" + head_image + '\'' +
                ", mobile='" + mobile + '\'' +
                ", server_status='" + server_status + '\'' +
                ", pay_status='" + pay_status + '\'' +
                ", order_time='" + order_time + '\'' +
                ", order_money='" + order_money + '\'' +
                ", rebate_money='" + rebate_money + '\'' +
                ", tip_money='" + tip_money + '\'' +
                ", pay_money='" + pay_money + '\'' +
                ", pay_type='" + pay_type + '\'' +
                ", server_start_time='" + server_start_time + '\'' +
                ", server_end_time='" + server_end_time + '\'' +
                ", numbers='" + numbers + '\'' +
                ", end_addr='" + end_addr + '\'' +
                ", content='" + content + '\'' +
                ", star='" + star + '\'' +
                ", tag='" + tag + '\'' +
                ", order_number='" + order_number + '\'' +
                ", server_type='" + server_type + '\'' +
                ", order_status='" + order_status + '\'' +
                ", server_date='" + server_date + '\'' +
                '}';
    }
}
