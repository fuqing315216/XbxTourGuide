package com.xbx.tourguide.http;

import com.android.volley.VolleyError;


public interface RequestListener  {

    /** 成功 */
     void requestSuccess(String json);

    /** 错误 */
     void requestError(VolleyError e);
}
