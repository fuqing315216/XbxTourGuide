package com.xbx.tourguide.http;

import com.android.volley.VolleyError;

import java.util.List;


public interface RequestJsonListener<T> {
    /**
     * 成功
     *
     * @param <T>
     */
    public void requestSuccess(T result);
    public void requestSuccess(List<T> list);
    /**
     * 错误
     */
    public void requestError(VolleyError e);
}
