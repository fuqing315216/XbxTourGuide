package com.xbx.tourguide.ui;

import android.os.Bundle;
import android.os.Handler;

import com.android.volley.VolleyError;
import com.xbx.tourguide.R;
import com.xbx.tourguide.base.BaseActivity;
import com.xbx.tourguide.http.HttpUrl;
import com.xbx.tourguide.http.IRequest;
import com.xbx.tourguide.http.RequestBackListener;
import com.xbx.tourguide.http.RequestParams;
import com.xbx.tourguide.jsonparse.UserInfoParse;
import com.xbx.tourguide.jsonparse.UtilParse;
import com.xbx.tourguide.util.Cookie;
import com.xbx.tourguide.util.ToastUtils;
import com.xbx.tourguide.util.VerifyUtil;
import cn.jpush.android.api.JPushInterface;

/**
 * Created by shuzhen on 2016/3/28.
 * <p/>
 * loading页
 */
public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Cookie.putAppointmentOrder(this, "");
        Cookie.putIsDialog(this, false);
        Cookie.putIsJPush(this, false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                isAutoLogin();
            }
        }, 2000);
    }

    //    @Override
//    protected void onResume() {
//        // TODO Auto-generated method stub
//        super.onResume();
//        JPushInterface.onResume(this);
//    }
//
//    @Override
//    protected void onPause() {
//        // TODO Auto-generated method stub
//        super.onPause();
//        JPushInterface.onPause(this);
//    }

    private void isAutoLogin() {
        if (Cookie.getUserInfo(this) == null) {
            startIntent(LoginActivity.class, true);
            return;
        }

        String mobile = UserInfoParse.getMobile(Cookie.getUserInfo(this));
        String token = UserInfoParse.getLogToken(Cookie.getUserInfo(this));
        if (!VerifyUtil.isNullOrEmpty(mobile) && !VerifyUtil.isNullOrEmpty(token)) {
            RequestParams params = new RequestParams();
            params.put("mobile", mobile);
            params.put("password", token);
            params.put("push_id", JPushInterface.getRegistrationID(this));
            IRequest.post(this, HttpUrl.LOGIN, params, new RequestBackListener(this) {
                @Override
                public void requestSuccess(String json) {
                    if (UtilParse.getRequestCode(json) == 0) {
                        ToastUtils.showShort(MainActivity.this, "自动登录已过期，请重新登录");
                        startIntent(LoginActivity.class, true);
                    } else if (UtilParse.getRequestCode(json) == 1) {
//                        ToastUtils.showShort(MainActivity.this, "自动登录成功");
                        Cookie.putUserInfo(MainActivity.this, UtilParse.getRequestData(json));
                        if ("0".equals(UserInfoParse.getUserInfo(Cookie.getUserInfo(MainActivity.this)).getIs_auth())) {
                            startIntent(RegisterInfoOkActivity.class, true);
                        } else {
                            startIntent(HomeActivity.class, true);
                        }
                    } else if (UtilParse.getRequestCode(json) == 2) {
                        Cookie.putUserInfo(MainActivity.this, UtilParse.getRequestData(json));
                        startIntent(RegisterGuideTypeActivity.class, true);
                    }
                }

                /**
                 * 处理网络问题 连接超时
                 */
                @Override
                public void requestError(VolleyError e) {
                    super.requestError(e);
                    ToastUtils.showShort(MainActivity.this, "请检查您的网络");
                    startIntent(LoginActivity.class, true);
                }

            });
        } else {
            startIntent(LoginActivity.class, true);
        }
    }
}
