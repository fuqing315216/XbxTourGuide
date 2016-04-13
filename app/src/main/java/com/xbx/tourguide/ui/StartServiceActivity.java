package com.xbx.tourguide.ui;

import android.app.AlertDialog;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xbx.tourguide.R;
import com.xbx.tourguide.app.XbxTGApplication;
import com.xbx.tourguide.base.BaseActivity;
import com.xbx.tourguide.beans.ProcessOrderDetailBeans;
import com.xbx.tourguide.http.HttpUrl;
import com.xbx.tourguide.http.IRequest;
import com.xbx.tourguide.http.RequestJsonListener;
import com.xbx.tourguide.util.Cookie;
import com.xbx.tourguide.view.CircleImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shuzhen on 2016/4/8.
 * <p>
 * 开始/结束服务页
 */
public class StartServiceActivity extends BaseActivity implements View.OnClickListener {

    private ImageButton returnIbtn;
    private String orderId = "";
    private CircleImageView headImgCiv;
    private TextView nameTv, addressTv, timeTv;
    private ImageView phoneIv;
    private ImageLoader loader;
    private Button stopBtn;
    private String startTime = "";
    private MapView mapView;
    private BaiduMap baiduMap;
    private LocationClient locationClient;

    Runnable run = new Runnable() {
        @Override
        public void run() {
            getOrderDetail();
            new Handler().postDelayed(this, 1000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startservice);
        orderId = getIntent().getStringExtra("orderId");
        loader = ImageLoader.getInstance();

        locationClient = new LocationClient(this);
        locationClient.start();
        locationClient.requestLocation();
        initView();
    }

    private void initView() {
        returnIbtn = (ImageButton) findViewById(R.id.ibtn_return);
        headImgCiv = (CircleImageView) findViewById(R.id.civ_headpic);
        nameTv = (TextView) findViewById(R.id.tv_name);
        addressTv = (TextView) findViewById(R.id.tv_add);
        timeTv = (TextView) findViewById(R.id.tv_service_time);
        phoneIv = (ImageView) findViewById(R.id.iv_phone);
        stopBtn = (Button) findViewById(R.id.btn_service);

        mapView = (MapView) findViewById(R.id.mapview);
        baiduMap = mapView.getMap();
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(14.0f);
        baiduMap.setMapStatus(msu);
        baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);

        stopBtn.setText(getResources().getString(R.string.end_service));

        returnIbtn.setOnClickListener(this);

        getLonAndLat();
        new Handler().postDelayed(run, 1000);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibtn_return:
                finish();
                break;

            default:
                break;
        }
    }

    /**
     * 获取订单详情
     */
    private void getOrderDetail() {
        String url = HttpUrl.ORDER_PROCESS + "?order_number=" + orderId;

        IRequest.get(this, url, ProcessOrderDetailBeans.class, "请稍候...", false, new RequestJsonListener<ProcessOrderDetailBeans>() {
            @Override
            public void requestSuccess(final ProcessOrderDetailBeans result) {

                phoneIv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //用intent启动拨打电话
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        Uri data = Uri.parse("tel:" + result.getMobile());
                        intent.setData(data);
                        startActivity(intent);
                    }
                });

                nameTv.setText(result.getNickname());
                addressTv.setText(result.getEnd_addr());
//                loader.displayImage(HttpUrl.IMAGE_URL + result.getHead_image(), headImgCiv);

                startTime = result.getServer_start_time();
                long time = Long.parseLong(result.getNow_time());
                long serverTime = time - Long.parseLong(startTime);
                String timeStr = XbxTGApplication.formatTime(serverTime);
                timeTv.setText(timeStr);

                if(result.getLon()!=null&&result.getLat()!=null){
                    initOverlay( Double.parseDouble(result.getLat()),Double.parseDouble(result.getLon()), R.drawable.ic_client);
                }

            }

            @Override
            public void requestSuccess(List<ProcessOrderDetailBeans> list) {

            }

            @Override
            public void requestError(VolleyError e) {

            }
        });
    }


    /**
     * 设置覆盖物
     * @param lon
     * @param lat
     * @param drawable
     */
    private void initOverlay(double lat,double lon,  int drawable) {

        LatLng llA = new LatLng(lon, lat);

        MarkerOptions options = new MarkerOptions()
                .position(llA) // 设置marker的位置
                .icon(BitmapDescriptorFactory
                        .fromResource(drawable)) // 设置marker图标
                .zIndex(9) // 设置marker所在层级
                .period(3) // 设置动画时间
                .draggable(true); // 设置手势拖拽
        Marker markers = (Marker) baiduMap.addOverlay(options);


    }

    /**
     * 获取当前位置的经纬度
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

                initOverlay( location.getLatitude(),location.getLongitude(), R.drawable.ic_guide);
                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatus mMapstatus = new MapStatus.Builder().target(ll).zoom(20f)
                        .build();
                MapStatusUpdate u = MapStatusUpdateFactory.newMapStatus(mMapstatus);
                baiduMap.setMapStatus(u);
            }

            public void onReceivePoi(BDLocation location) {

            }

        });


    }


}
