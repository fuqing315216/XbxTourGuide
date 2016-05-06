package com.xbx.tourguide.beans;

public class Version {

    private String version_code;//版本
    private String version_title;//版本名称
    private String update_content;//版本更新内容
    private String version_url;

    public String getVersion_code() {
        return version_code;
    }

    public String getVersion_title() {
        return version_title;
    }

    public String getUpdate_content() {
        return update_content;
    }

    public String getVersion_url() {
        return version_url;
    }

    public void setVersion_code(String version_code) {
        this.version_code = version_code;
    }

    public void setVersion_title(String version_title) {
        this.version_title = version_title;
    }

    public void setUpdate_content(String update_content) {
        this.update_content = update_content;
    }

    public void setVersion_url(String version_url) {
        this.version_url = version_url;
    }
}
