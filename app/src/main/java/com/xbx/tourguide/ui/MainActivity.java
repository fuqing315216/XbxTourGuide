package com.xbx.tourguide.ui;

import android.os.Bundle;
import android.os.Handler;
import android.widget.RelativeLayout;

import com.android.volley.VolleyError;
import com.xbx.tourguide.R;
import com.xbx.tourguide.base.BaseActivity;
import com.xbx.tourguide.http.HttpUrl;
import com.xbx.tourguide.http.IRequest;
import com.xbx.tourguide.http.RequestBackListener;
import com.xbx.tourguide.http.RequestParams;
import com.xbx.tourguide.jsonparse.UserInfoParse;
import com.xbx.tourguide.jsonparse.UtilParse;
import com.xbx.tourguide.util.BitmapBgUtils;
import com.xbx.tourguide.util.Constant;
import com.xbx.tourguide.util.LogUtils;
import com.xbx.tourguide.util.SPUtils;
import com.xbx.tourguide.util.ToastUtils;
import com.xbx.tourguide.util.VerifyUtil;

import org.apache.http.cookie.Cookie;
import org.xml.sax.helpers.LocatorImpl;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by shuzhen on 2016/3/28.
 * <p>
 * loading页
 */
public class MainActivity extends BaseActivity {

    private RelativeLayout bgRlyt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        SPUtils.clear(this);
        SPUtils.put(this, Constant.APPOINT_ORDER, "");
        SPUtils.put(this, Constant.IS_DIALOG, false);
        SPUtils.put(this, Constant.IS_JPUSH, false);

        bgRlyt = (RelativeLayout) findViewById(R.id.rlyt_loading_bg);
        BitmapBgUtils.getInstance().setBitmapBackground(this,bgRlyt,R.drawable.bg_loading);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                isAutoLogin();
            }
        }, 2000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BitmapBgUtils.getInstance().destroyBackground(bgRlyt);
    }

    private void isAutoLogin() {
        final String userInfo = (String) SPUtils.get(this, Constant.USER_INFO, "");
        if (userInfo == null || VerifyUtil.isNullOrEmpty(userInfo)) {
            startIntent(LoginActivity.class, true);
            return;
        }

        String mobile = UserInfoParse.getMobile(userInfo);
        String token = UserInfoParse.getLogToken(userInfo);
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
                        SPUtils.put(MainActivity.this, Constant.USER_INFO, UtilParse.getRequestData(json));
                        if ("0".equals(UserInfoParse.getUserInfo(userInfo).getIs_auth())) {
                            startIntent(RegisterInfoOkActivity.class, true);
                        } else {
                            startIntent(HomeActivity.class, true);
                        }
                    } else if (UtilParse.getRequestCode(json) == 2) {
                        SPUtils.put(MainActivity.this, Constant.USER_INFO, UtilParse.getRequestData(json));
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
