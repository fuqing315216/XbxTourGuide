package com.xbx.tourguide.api;

import android.content.Context;
import android.os.Handler;

import com.xbx.tourguide.R;
import com.xbx.tourguide.http.HttpUrl;
import com.xbx.tourguide.http.IRequest;
import com.xbx.tourguide.http.RequestBackListener;
import com.xbx.tourguide.http.RequestParams;
import com.xbx.tourguide.jsonparse.UserInfoParse;
import com.xbx.tourguide.jsonparse.UtilParse;
import com.xbx.tourguide.util.LogUtils;
import com.xbx.tourguide.util.ToastUtils;

import java.io.File;

/**
 * Created by runde on 2016/4/25.
 */
public class SettingApi {

    private Context context;
    private SendShowMessage sendShowMessage = null;

    public SettingApi(Context context, Handler handler) {
        this.context = context;
        sendShowMessage = new SendShowMessage(context, handler);
    }

    /**
     * 意见反馈
     *
     * @param uid
     * @param content
     */
    public void feedBack(String uid, String content) {
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        params.put("content", content);
        IRequest.post(context, HttpUrl.FEED_BACK, params, context.getString(R.string.waitting), new RequestBackListener(context) {
            @Override
            public void requestSuccess(String json) {
                sendShowMessage.sendShowMsg(TaskFlag.REQUESTSUCCESS, json);
            }
        });
    }

    /**
     * 修改个人基本信息
     *
     * @param uid
     * @param head_image      头像File路径
     * @param now_address     服务地区
     * @param server_language 服务语言
     */
    public void updateInfo(String uid, File head_image, String birthday, String now_address, String server_language) {
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        params.put("head_image", head_image);
        params.put("birthday", birthday);
        params.put("now_address", now_address);
        params.put("server_language", server_language);
        IRequest.post(context, HttpUrl.UPDATE_INFO, params, context.getString(R.string.waitting), new RequestBackListener(context) {
            @Override
            public void requestSuccess(String json) {
                sendShowMessage.sendShowMsg(TaskFlag.REQUESTSUCCESS, json);
            }
        });
    }

    /**
     * 设置导游个人主页 获取导游基本信息
     *
     * @param uid
     */
    public void getGuideDetail(String uid) {
        String url = HttpUrl.GUIDE_DETAIL + "?uid=" + uid;
        IRequest.get(context, url, context.getString(R.string.waitting), new RequestBackListener(context) {
            @Override
            public void requestSuccess(String json) {
                sendShowMessage.sendShowMsg(TaskFlag.REQUESTSUCCESS, json);
            }
        });
    }

    /**
     * 更新导游个人主页
     *
     * @param uid
     * @param self_introduce   自我介绍
     * @param server_introduce 服务表述
     */
    public void updateGuideDetail(String uid, String self_introduce, String server_introduce) {
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        params.put("self_introduce", self_introduce);
        params.put("server_introduce", server_introduce);
        IRequest.post(context, HttpUrl.GUIDE_MAIN, params, context.getString(R.string.waitting), new RequestBackListener(context) {
            @Override
            public void requestSuccess(String json) {
                sendShowMessage.sendMsg(TaskFlag.PAGEREQUESTWO, json);
            }
        });
    }

    /**
     * 获取服务时间
     *
     * @param uid
     */
    public void getServiceTime(String uid) {
        String url = HttpUrl.GET_SERVICETIME + "?uid=" + uid;
        IRequest.get(context, url, context.getString(R.string.waitting), new RequestBackListener(context) {
            @Override
            public void requestSuccess(String json) {
                sendShowMessage.sendShowMsg(TaskFlag.REQUESTSUCCESS, json);
            }
        });
    }

    /**
     * 设置服务时间
     */
    public void setServiceTime(RequestParams params) {
        IRequest.post(context, HttpUrl.SETTING_SERVICETIME, params, context.getString(R.string.waitting), new RequestBackListener(context) {
            @Override
            public void requestSuccess(String json) {
                sendShowMessage.sendShowMsg(TaskFlag.PAGEREQUESTWO, json);
            }
        });
    }
}
