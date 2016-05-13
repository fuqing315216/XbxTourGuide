package com.xbx.tourguide.api;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.android.volley.VolleyError;
import com.xbx.tourguide.R;
import com.xbx.tourguide.http.HttpUrl;
import com.xbx.tourguide.http.IRequest;
import com.xbx.tourguide.http.RequestBackListener;
import com.xbx.tourguide.http.RequestParams;
import com.xbx.tourguide.jsonparse.UserInfoParse;
import com.xbx.tourguide.jsonparse.UtilParse;
import com.xbx.tourguide.util.LogUtils;

/**
 * Created by runde on 2016/4/23.
 */
public class LoginApi {

    private Context context;
    private SendShowMessage sendShowMessage = null;

    public LoginApi(Context context, Handler handler) {
        this.context = context;
        sendShowMessage = new SendShowMessage(context, handler);
    }

    /**
     * 获取验证码
     *
     * @param mobile
     * @param check_regester 是否验证手机号已注册（0：不验证；1：验证）
     */
    public void getVerifyCode(String mobile, String check_regester) {
        String url = HttpUrl.GET_VERIFYCODE + "?mobile=" + mobile + "&check_regester=" + check_regester;
        IRequest.get(context, url, "验证码發送中", new RequestBackListener(context) {
            @Override
            public void requestSuccess(String json) {
                LogUtils.i("---getVerifyCode:" + json);
                sendShowMessage.sendShowMsg(TaskFlag.PAGEREQUESTWO, json);
            }
        });
    }

    public void updatePw(String mobile, String verify_code, String password) {
        RequestParams params = new RequestParams();
        params.put("mobile", mobile);
        params.put("verify_code", verify_code);
        params.put("password", password);
        IRequest.post(context, HttpUrl.UPDATE_PW, params, new RequestBackListener(context) {
            @Override
            public void requestSuccess(String json) {
                sendShowMessage.sendShowMsg(TaskFlag.REQUESTSUCCESS, json);
            }
        });
    }

    /**
     * 忘记密码登录
     *
     * @param mobile
     * @param password
     * @param user_type
     */
    public void forgetPwLogin(String mobile, String password, String user_type) {
        RequestParams params = new RequestParams();
        params.put("mobile", mobile);
        params.put("password", password);
        params.put("user_type", user_type);
        IRequest.post(context, HttpUrl.LOGIN, params, context.getString(R.string.waitting), new RequestBackListener(context) {
            @Override
            public void requestSuccess(String json) {
                sendShowMessage.sendShowMsg(TaskFlag.PAGEREQUESTHREE, json);
            }
        });
    }

    /**
     * 注册
     *
     * @param params
     */
    public void register(RequestParams params) {
        IRequest.post(context, HttpUrl.REGISTER, params, context.getString(R.string.waitting), new RequestBackListener(context) {
            @Override
            public void requestSuccess(String json) {
                sendShowMessage.sendShowMsg(TaskFlag.REQUESTSUCCESS, json);
            }
        });
    }

    /**
     * 登出
     *
     * @param uid
     */
    public void loginOut(String uid) {
        String url = HttpUrl.LOGIN_OUT + "?uid=" + uid;
        IRequest.get(context, url, context.getString(R.string.waitting), new RequestBackListener(context) {
            @Override
            public void requestSuccess(String json) {
                sendShowMessage.sendShowMsg(TaskFlag.REQUESTSUCCESS, json);
            }
        });
    }
}
