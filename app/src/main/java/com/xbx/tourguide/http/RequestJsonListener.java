package com.xbx.tourguide.http;

import com.android.volley.VolleyError;


public interface RequestJsonListener<T> {
    /**
     * 成功
     *
     * @param <T>
     */
    public void requestSuccess(T result);

    /**
     * 错误
     */
    public void requestError(VolleyError e);
}
