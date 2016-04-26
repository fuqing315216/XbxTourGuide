package com.xbx.tourguide.api;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.xbx.tourguide.jsonparse.UtilParse;
import com.xbx.tourguide.util.ToastUtils;
import com.xbx.tourguide.util.VerifyUtil;

/**
 * Created by runde on 2016/4/23.
 */
public class SendShowMessage {

    private Context context;
    private Handler mHandler;

    public SendShowMessage(Context context, Handler handler) {
        this.context = context;
        this.mHandler = handler;
    }

    /**
     * 无判断code=0的情况
     */
    public void sendMsg(int flag, String json) {
        Message msg = mHandler.obtainMessage();
        if (UtilParse.getRequestCode(json) == 1) {
            msg.obj = UtilParse.getRequestData(json);
            msg.what = flag;
            mHandler.sendMessage(msg);
        }
    }

    /**
     * 有判断code=0的情况
     */
    public void sendShowMsg(int flag, String json) {
        Message msg = mHandler.obtainMessage();
        if (UtilParse.getRequestCode(json) == 1) {
            msg.obj = UtilParse.getRequestData(json);
            msg.what = flag;
            mHandler.sendMessage(msg);
        } else {
            String showMsg = UtilParse.getRequestMsg(json);
            if (!VerifyUtil.isNullOrEmpty(showMsg))
                ToastUtils.showShort(context, showMsg);
        }
    }

    public Handler getmHandler() {
        return mHandler;
    }
}
