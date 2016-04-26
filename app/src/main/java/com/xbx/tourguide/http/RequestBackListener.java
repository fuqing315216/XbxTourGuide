package com.xbx.tourguide.http;

import android.content.Context;

import com.android.volley.NetworkError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.xbx.tourguide.util.LogUtils;
import com.xbx.tourguide.util.ToastUtils;

/**
 * Created by xbx on 2016/4/18.
 */
public class RequestBackListener implements RequestListener {

    private Context context;

    public RequestBackListener(Context context) {
        this.context = context;
    }

    @Override
    public void requestSuccess(String json) {

    }

    @Override
    public void requestError(VolleyError e) {
        LogUtils.i("VolleyError:" + e.getMessage());
        if (e instanceof NetworkError) {
            ToastUtils.showShort(context, "请检查网络");
        } else if (e instanceof TimeoutError) {
            ToastUtils.showShort(context, "连接超时");
        } else if (e instanceof ServerError) {
            ToastUtils.showShort(context, "服务器异常");
        } else {
            ToastUtils.showShort(context, "未知异常");
        }
    }
}
