package com.xbx.tourguide.ui;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.xbx.tourguide.R;
import com.xbx.tourguide.base.BaseStatusActivity;
import com.xbx.tourguide.beans.OnLineBeans;
import com.xbx.tourguide.beans.TourGuideBeans;
import com.xbx.tourguide.beans.TourGuideInfoBeans;
import com.xbx.tourguide.http.HttpUrl;
import com.xbx.tourguide.http.IRequest;
import com.xbx.tourguide.http.RequestJsonListener;
import com.xbx.tourguide.http.RequestParams;
import com.xbx.tourguide.util.Cookie;
import com.xbx.tourguide.util.JsonUtils;

import java.util.List;


/**
 * Created by shuzhen on 2016/3/31.
 * <p/>
 * 首页
 */
public class HomeActivity extends BaseStatusActivity implements View.OnClickListener {

    private static final double EARTH_RADIUS = 6378137.0;
    private RelativeLayout myOrderRlyt;
    private TextView startTv, serviceTimeTv, travelTv;
    private LocationClient locationClient;
    private String online = "";
    private String uid="";

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
        uid=Cookie.getUserInfo(this).getUid();

        initView();
    }

    private void initView() {
        myOrderRlyt = (RelativeLayout) findViewById(R.id.rlyt_myorder);
        startTv = (TextView) findViewById(R.id.tv_start_order);
        serviceTimeTv = (TextView) findViewById(R.id.tv_service_time);
        travelTv = (TextView) findViewById(R.id.tv_my_travel);

        myOrderRlyt.setOnClickListener(this);
        startTv.setOnClickListener(this);
        serviceTimeTv.setOnClickListener(this);
        travelTv.setOnClickListener(this);

        online=Cookie.getUserInfo(HomeActivity.this).getUser_info().getIs_online();
        if ("0".equals(online)) {//不在线
            startTv.setText(getResources().getString(R.string.start_order));
        } else if ("1".equals(online)) {
            startTv.setText(getResources().getString(R.string.stop_order));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

//        getLocation();
        new Handler().postDelayed(run, 6000);

        locationClient = new LocationClient(this);
        locationClient.start();
        locationClient.requestLocation();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rlyt_myorder:
                startIntent(MyOrderListActivity.class, false);
                break;

            case R.id.tv_start_order:
//               XbxTGApplication.getInstance().showNotification();
                setStartService();
                break;

            case R.id.tv_service_time:
                startIntent(ServiceTimeActivity.class, false);
                break;

            case R.id.tv_my_travel:
                startIntent(StartServiceActivity.class, false);
                break;
            default:
                break;
        }
    }

    /**
     * 开始服务
     */
    private void setStartService() {
        Log.i("log","==================="+uid);
        String url = HttpUrl.START_SERVICE + "?uid=" + uid;
        IRequest.get(this, url, OnLineBeans.class, "请稍候...", new RequestJsonListener<OnLineBeans>() {
            @Override
            public void requestSuccess(OnLineBeans result) {

                TourGuideInfoBeans infoBeans = new TourGuideInfoBeans();
                TourGuideBeans bean = new TourGuideBeans();

                if (result.getIs_online()==0) {//不在线
                    startTv.setText(getResources().getString(R.string.start_order));
                    infoBeans.setIs_online("0");
                } else if (result.getIs_online()==1) {
                    startTv.setText(getResources().getString(R.string.stop_order));
                    infoBeans.setIs_online("1");
                }
                bean.setUser_info(infoBeans);
                Cookie.putUserInfo(HomeActivity.this, JsonUtils.toJson(bean));
            }

            @Override
            public void requestSuccess(List<OnLineBeans> list) {

            }

            @Override
            public void requestError(VolleyError e) {

            }
        });
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
                String lonAndlat = Cookie.getLonAndLat(HomeActivity.this);

                if (lonAndlat != null && !"".equals(lonAndlat)) {
                    double lon = Double.parseDouble(lonAndlat.split(",")[0]);
                    double lat = Double.parseDouble(lonAndlat.split(",")[1]);
                    double instance = getDistance(lon, lat, location.getLongitude(), location.getLatitude());

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
        params.put("uid", Cookie.getUserInfo(HomeActivity.this).getUid());
        params.put("lon", location.getLongitude() + "");
        params.put("lat", location.getLatitude() + "");
        Cookie.putLonAndLat(HomeActivity.this, location.getLongitude() + "," + location.getLatitude());
        IRequest.post(HomeActivity.this, HttpUrl.POST_LON_LAT, String.class, params, new RequestJsonListener<String>() {
            @Override
            public void requestSuccess(String result) {

            }

            @Override
            public void requestSuccess(List<String> list) {

            }

            @Override
            public void requestError(VolleyError e) {

            }
        });
    }

    // 返回单位是米
    public static double getDistance(double longitude1, double latitude1,
                                     double longitude2, double latitude2) {
        double Lat1 = rad(latitude1);
        double Lat2 = rad(latitude2);
        double a = Lat1 - Lat2;
        double b = rad(longitude1) - rad(longitude2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(Lat1) * Math.cos(Lat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10000;
        return s;
    }

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

}
