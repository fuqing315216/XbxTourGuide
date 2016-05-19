package com.xbx.tourguide.ui;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.makeramen.roundedimageview.RoundedImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xbx.tourguide.R;
import com.xbx.tourguide.api.ServiceApi;
import com.xbx.tourguide.api.TaskFlag;
import com.xbx.tourguide.base.BaseActivity;
import com.xbx.tourguide.beans.GoingBeans;
import com.xbx.tourguide.beans.OnLineBeans;
import com.xbx.tourguide.beans.TourGuideInfoBeans;
import com.xbx.tourguide.beans.Version;
import com.xbx.tourguide.http.HttpUrl;
import com.xbx.tourguide.http.IRequest;
import com.xbx.tourguide.http.RequestBackListener;
import com.xbx.tourguide.http.RequestParams;
import com.xbx.tourguide.jsonparse.UserInfoParse;
import com.xbx.tourguide.util.ActivityManager;
import com.xbx.tourguide.util.Constant;
import com.xbx.tourguide.util.JsonUtils;
import com.xbx.tourguide.util.SPUtils;
import com.xbx.tourguide.util.ToastUtils;
import com.xbx.tourguide.util.Util;
import com.xbx.tourguide.util.VerifyUtil;
import com.xbx.tourguide.util.updateversion.UpdateUtil;


/**
 * Created by shuzhen on 2016/3/31.
 * <p>
 * 首页
 */
public class HomeActivity extends BaseActivity implements View.OnClickListener {

    private RelativeLayout myOrderRlyt;
    private RatingBar starRab;
    private TextView startTv, serviceTimeTv, travelTv, orderNumTv, msgNumTv, nameTv, scoreTv;
    private LocationClient locationClient;
    private String online = "";
    private String uid = "";
    private int unreadNum = 0, unreadMsg = 0;
    private RoundedImageView headPicRiv;
    private ImageLoader loader;
    private TourGuideInfoBeans beans;
    private ServiceApi serviceApi = null;
    private String userInfo = "";

    private OrderReceiver orderReceiver = null;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case TaskFlag.REQUESTSUCCESS://设置是否开始接单
                    OnLineBeans onLineBean = JsonUtils.object((String) msg.obj, OnLineBeans.class);
                    startTv.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);

                    SPUtils.put(HomeActivity.this, Constant.ONLINE, onLineBean.getIs_online() + "");

                    if (onLineBean.getIs_online() == 0) {//不在线
                        startTv.setText(getResources().getString(R.string.start_order));
                        startTv.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_start_order, 0, 0);
                    } else if (onLineBean.getIs_online() == 1) {
                        startTv.setText(getResources().getString(R.string.stop_order));
                        startTv.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_stop_order, 0, 0);
                    }
                    break;
                case TaskFlag.PAGEREQUESTWO:
                    Version version = JsonUtils.object((String) msg.obj, Version.class);
                    if (version == null)
                        return;
                    if (VerifyUtil.isNullOrEmpty(version.getVersion_code()))
                        return;
                    checkUpdate(version);
                    break;
            }
        }
    };

    private Runnable run = new Runnable() {
        @Override
        public void run() {
            getLonAndLat();
            new Handler().postDelayed(this, 6000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if (orderReceiver == null) {
            orderReceiver = new OrderReceiver();
            IntentFilter intentFilter = new IntentFilter(Constant.BROADCAST);
            registerReceiver(orderReceiver, intentFilter);
        }

        uid = (String) SPUtils.get(this, Constant.UID, "");
        loader = ImageLoader.getInstance();

        serviceApi = new ServiceApi(this, handler);
//        serviceApi.checkUpdate();
        SPUtils.put(this, Constant.IS_JPUSH, false);
        SPUtils.put(this, Constant.IS_DIALOG, false);
        SPUtils.put(this, Constant.LOGIN_OUT, false);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        getLocation();
        if ((Boolean) SPUtils.get(this, Constant.LOGIN_OUT, false)) {
            initData();
        }

        new Handler().postDelayed(run, 6000);

        locationClient = new LocationClient(this);
        locationClient.start();
        locationClient.requestLocation();
    }

    private void initView() {
        myOrderRlyt = (RelativeLayout) findViewById(R.id.rlyt_myorder);
        starRab = (RatingBar) findViewById(R.id.rab_home);
        scoreTv = (TextView) findViewById(R.id.tv_score);
        startTv = (TextView) findViewById(R.id.tv_start_order);
        serviceTimeTv = (TextView) findViewById(R.id.tv_home_service_time);
        travelTv = (TextView) findViewById(R.id.tv_my_travel);
        orderNumTv = (TextView) findViewById(R.id.tv_order_sum);
        msgNumTv = (TextView) findViewById(R.id.tv_information_sum);
        headPicRiv = (RoundedImageView) findViewById(R.id.riv_home_headpic);
        nameTv = (TextView) findViewById(R.id.tv_username);

        myOrderRlyt.setOnClickListener(this);
        startTv.setOnClickListener(this);
        serviceTimeTv.setOnClickListener(this);
        travelTv.setOnClickListener(this);
        findViewById(R.id.rlyt_head).setOnClickListener(this);
        findViewById(R.id.tv_my_wallet).setOnClickListener(this);
        findViewById(R.id.tv_setting).setOnClickListener(this);

        initData();
    }

    private void initData() {
        userInfo = (String) SPUtils.get(this, Constant.USER_INFO, "");
        beans = UserInfoParse.getUserInfo(userInfo);

        if ("going".equals(UserInfoParse.getDataType(userInfo))) {
            GoingBeans goingBeans = UserInfoParse.getGoing(userInfo);
            startActivity(new Intent(HomeActivity.this, StartServiceActivity.class)
                    .putExtra("orderId", goingBeans.getOrder_number()));
        }

        online = beans.getIs_online();
        startTv.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);

        SPUtils.put(this, Constant.ONLINE, online);

        if ("0".equals(online)) {//不在线
            startTv.setText(getResources().getString(R.string.start_order));
            startTv.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_start_order, 0, 0);
        } else if ("1".equals(online)) {
            startTv.setText(getResources().getString(R.string.stop_order));
            startTv.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_stop_order, 0, 0);
        }

//        unreadNum = Integer.parseInt(beans.getUnread_order());
//        unreadMsg = Integer.parseInt(beans.getUnread_message());
//        if (unreadNum == 0) {
//            orderNumTv.setVisibility(View.GONE);
//        } else {
//            orderNumTv.setText(unreadNum + "");
//        }
//
//        if (unreadMsg == 0) {
//            msgNumTv.setVisibility(View.GONE);
//        } else {
//            msgNumTv.setText(unreadMsg + "");
//        }

        loader.displayImage(beans.getHead_image(), headPicRiv);
        if (!VerifyUtil.isNullOrEmpty(beans.getGuide_card_number())) {
            ((TextView) findViewById(R.id.tv_userno)).setText(beans.getGuide_card_number());//导游证号
        } else {
            findViewById(R.id.tv_userno).setVisibility(View.GONE);
        }

        nameTv.setText(beans.getRealname());

        scoreTv.setText(Util.getStar(beans.getStars()) + "分");
        if ("0.0".equals(Util.getStar(beans.getStars()))) {
            starRab.setVisibility(View.GONE);
        } else {
            starRab.setRating(Util.getStar(beans.getStars()) / 2);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rlyt_myorder:
                startIntent(MyOrderListActivity.class, false);
                break;

            case R.id.tv_start_order:
//               XbxTGApplication.getInstance().showNotification();
                serviceApi.setIsOnline(uid);
                break;

            case R.id.tv_home_service_time:
                startIntent(ServiceTimeActivity.class, false);
                break;

            case R.id.tv_my_travel:
//                Intent intent = new Intent(this, OrderRemainActivity.class);
//                intent.putExtra("orderNumber", "");//541654646468484
//                startActivity(intent);
                break;

            case R.id.rlyt_head:
                startActivityForResult(new Intent(HomeActivity.this, PersonalInfoActivity.class), 102);
                break;
            case R.id.tv_my_wallet:
                startIntent(MyWalletActivity.class, false);
                break;
            case R.id.tv_setting:
                startIntent(SettingActivity.class, false);
                break;
            default:
                break;
        }
    }

    /**
     * 上传经纬度
     */
    private void getLonAndLat() {
        //设置定位条件
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);        //是否打开GPS
        option.setCoorType("bd09ll");       //设置返回值的坐标类型。
        option.setPriority(LocationClientOption.NetWorkFirst);  //设置定位优先级
        option.setProdName("LocationDemo"); //设置产品线名称。强烈建议您使用自定义的产品线名称，方便我们以后为您提供更高效准确的定位服务。
        option.setScanSpan(6000);    //设置定时定位的时间间隔。单位毫秒
        locationClient.setLocOption(option);
        //注册位置监听器
        locationClient.registerLocationListener(new BDLocationListener() {

            @Override
            public void onReceiveLocation(BDLocation location) {
                // TODO Auto-generated method stub
                if (location == null) {
                    return;
                }
                String lonAndlat = (String) SPUtils.get(HomeActivity.this, Constant.LON_LAT, "");

                if (lonAndlat != null && !"".equals(lonAndlat)) {
                    double lon = Double.parseDouble(lonAndlat.split(",")[0]);
                    double lat = Double.parseDouble(lonAndlat.split(",")[1]);
                    double instance = Util.getDistance(lon, lat, location.getLongitude(), location.getLatitude());

                    if (instance >= 10) {
                        setLonLat(location);
                    }
                } else {
                    setLonLat(location);
                }
            }

            public void onReceivePoi(BDLocation location) {

            }

        });
    }

    private void setLonLat(final BDLocation location) {

        RequestParams params = new RequestParams();
        params.put("uid", (String) SPUtils.get(this, Constant.UID, ""));
        params.put("lon", location.getLongitude() + "");
        params.put("lat", location.getLatitude() + "");
        SPUtils.put(HomeActivity.this, Constant.LON_LAT, location.getLongitude() + "," + location.getLatitude());
        IRequest.post(HomeActivity.this, HttpUrl.POST_LON_LAT, params, new RequestBackListener(this) {
            @Override
            public void requestSuccess(String json) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 102) {//修改个人信息
            loader.displayImage(UserInfoParse.getUserInfo((String) SPUtils.get(this, Constant.USER_INFO, "")).getHead_image(), headPicRiv);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (orderReceiver != null) {
            unregisterReceiver(orderReceiver);
        }
    }

    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                ToastUtils.showShort(this, "再按一次退出程序");
                exitTime = System.currentTimeMillis();
            } else {
                ActivityManager.getInstance().finishAllActivity();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 更新版本
     *
     * @param version
     */
    private void checkUpdate(Version version) {
        new UpdateUtil(HomeActivity.this, version);
    }
}
