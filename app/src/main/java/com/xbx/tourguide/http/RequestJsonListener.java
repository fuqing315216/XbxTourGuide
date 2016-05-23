package com.xbx.tourguide.http;

import com.android.volley.VolleyError;

import java.util.List;


public interface RequestJsonListener<T> {
    /**
     * 成功
     *
     * @param <T>
     */
     void requestSuccess(T result);
     void requestSuccess(List<T> list);
    /**
     * 错误
     */
     void requestError(VolleyError e);
}
