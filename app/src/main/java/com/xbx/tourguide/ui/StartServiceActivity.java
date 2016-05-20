package com.xbx.tourguide.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRoutePlanOption;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.makeramen.roundedimageview.RoundedImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xbx.tourguide.R;
import com.xbx.tourguide.api.ServiceApi;
import com.xbx.tourguide.api.TaskFlag;
import com.xbx.tourguide.base.BaseActivity;
import com.xbx.tourguide.beans.ProcessOrderDetailBeans;
import com.xbx.tourguide.http.HttpUrl;
import com.xbx.tourguide.http.IRequest;
import com.xbx.tourguide.http.RequestBackListener;
import com.xbx.tourguide.http.RequestParams;
import com.xbx.tourguide.util.Constant;
import com.xbx.tourguide.util.JsonUtils;
import com.xbx.tourguide.util.LogUtils;
import com.xbx.tourguide.util.SPUtils;
import com.xbx.tourguide.util.Utils;
import com.xbx.tourguide.util.baidumap.OverlayManager;
import com.xbx.tourguide.util.baidumap.WalkingRouteOverlay;
import com.xbx.tourguide.view.TitleBarView;

/**
 * Created by shuzhen on 2016/4/8.
 * <p/>
 * 开始/结束服务页
 */
public class StartServiceActivity extends BaseActivity implements View.OnClickListener {
    private String orderId = "";
    private RoundedImageView headImgRiv;
    private TextView nameTv, addressTv, timeTv;
    private ImageView phoneIv;
    private ImageLoader loader;
    private Button stopBtn;
    private String startTime = "";
    private MapView mapView;
    private BaiduMap baiduMap;
    private LocationClient locationClient;
    private boolean isFirst = true;
    private LocationMode mCurrentMode;
    private BitmapDescriptor bdMyself = null;
    private static final int accuracyCircleFillColor = 0x00000000;
    private static final int accuracyCircleStrokeColor = 0x00000000;
    private boolean isFirstInOrd = true;
    private Marker userMarkers = null;

    private OverlayManager routeOverlay = null;
    private RoutePlanSearch mSearch = null;

    private ServiceApi serviceApi = null;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case TaskFlag.REQUESTSUCCESS:
                    final ProcessOrderDetailBeans result = JsonUtils.object((String) msg.obj, ProcessOrderDetailBeans.class);

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

                    String addr = result.getEnd_addr();
                    if (addr.contains("市")) {
                        addr = addr.substring(addr.indexOf("市") + 1, addr.length());
                    }
                    addressTv.setText(addr);

                    loader.displayImage(result.getHead_image(), headImgRiv);

                    startTime = result.getServer_start_time();
                    if ("0".equals(startTime)) {
                        timeTv.setText("未开始");

                        if (!stopBtn.getText().toString().equals(getString(R.string.start_service))) {
                            stopBtn.setText(getResources().getString(R.string.start_service));
                        }
                    } else {
                        long time = Long.parseLong(result.getNow_time());
                        long serverTime = time - Long.parseLong(startTime);
                        String timeStr = Utils.formatTime(serverTime);
                        timeTv.setText(timeStr);

                        if (!stopBtn.getText().toString().equals(getString(R.string.end_service))) {
                            stopBtn.setText(getResources().getString(R.string.end_service));
                        }
                    }

                    if (result.getLon() != null && result.getLat() != null
                            && timeTv.getText().toString().equals("未开始")) {
                        //设置用户地图覆盖物
                        initOverlay(Double.parseDouble(result.getLat()), Double.parseDouble(result.getLon()), R.drawable.ic_client);
                        //步行路径规划
                        String lonAndlat = (String) SPUtils.get(StartServiceActivity.this, Constant.LON_LAT, "");
                        if (lonAndlat != null && !"".equals(lonAndlat)) {
                            setWalkingSearch(Double.parseDouble(lonAndlat.split(",")[0]), Double.parseDouble(lonAndlat.split(",")[1]),
                                    Double.valueOf(result.getLon()), Double.valueOf(result.getLat()));
                        }
                    }
                    break;
                case TaskFlag.PAGEREQUESTWO://开始服务
                    stopBtn.setText(getResources().getString(R.string.end_service));
                    userMarkers.remove();
                    if (routeOverlay != null) {
                        routeOverlay.removeFromMap();
                    }
                    startActivity(new Intent(StartServiceActivity.this, ConfirmActivity.class)
                            .putExtra("title", "服务开始")
                            .putExtra("content", "您的即时导游服务已经开始计时"));
                    break;
                case TaskFlag.PAGEREQUESTHREE://结束服务
                    finish();
                    break;
                case 1:
                    serviceApi.getOrderDetail(orderId);
                    handler.sendEmptyMessageDelayed(1, 1000);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startservice);
        orderId = getIntent().getStringExtra("orderId");
        loader = ImageLoader.getInstance();
        bdMyself = BitmapDescriptorFactory.fromResource(R.drawable.ic_guide);
        serviceApi = new ServiceApi(this, handler);
        //
        mSearch = RoutePlanSearch.newInstance();
        mSearch.setOnGetRoutePlanResultListener(listener);
        initView();
    }

    private void initView() {
        TitleBarView titleBarView = (TitleBarView) findViewById(R.id.titlebar);
        titleBarView.setTitle(getString(R.string.app_name));
        titleBarView.setLeftImageButtonOnClickListener(new TitleBarView.OnLeftImageButtonClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        headImgRiv = (RoundedImageView) findViewById(R.id.riv_start_service);
        nameTv = (TextView) findViewById(R.id.tv_name);
        addressTv = (TextView) findViewById(R.id.tv_add);
        timeTv = (TextView) findViewById(R.id.tv_service_time);
        phoneIv = (ImageView) findViewById(R.id.iv_phone);
        stopBtn = (Button) findViewById(R.id.btn_service);

        mapView = (MapView) findViewById(R.id.mapview);
        mapView.showZoomControls(false);//去掉缩放按钮
        baiduMap = mapView.getMap();
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(14.0f);
        baiduMap.setMapStatus(msu);
        baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);

        stopBtn.setOnClickListener(this);

        locationClient = new LocationClient(this);
        locationClient.start();
        locationClient.requestLocation();

        getLonAndLat();
        handler.sendEmptyMessage(1);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_service:
                if (stopBtn.getText().toString().equals(getString(R.string.end_service))) {//进行中，点击按钮结束服务
                    startActivityForResult(new Intent(StartServiceActivity.this, ConfirmActivity.class)
                            .putExtra("title", "提醒")
                            .putExtra("content", "是否结束服务并计算费用"), 102);
                } else {//未开始，点击按钮开始服务
                    startActivityForResult(new Intent(StartServiceActivity.this, ConfirmActivity.class)
                            .putExtra("title", "提醒")
                            .putExtra("content", "是否开始服务并计时"), 101);
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 101) {
                serviceApi.startServer(orderId);
            } else if (requestCode == 102) {
                serviceApi.endServer(orderId);
            }
        }
    }

    @Override
    protected void onDestroy() {
        handler.removeMessages(1);
        mSearch.destroy();
        super.onDestroy();
    }

    /**
     * 设置覆盖物
     *
     * @param lon
     * @param lat
     * @param drawable
     */
    private void initOverlay(double lat, double lon, int drawable) {
        LatLng llA = new LatLng(lat, lon);
        if (isFirstInOrd) {
            isFirstInOrd = false;
            MarkerOptions options = new MarkerOptions()
                    .position(llA) // 设置marker的位置
                    .icon(BitmapDescriptorFactory.fromResource(drawable)) // 设置marker图标
                    .zIndex(9) // 设置marker所在层级
                    .period(3) // 设置动画时间
                    .draggable(true); // 设置手势拖拽
            userMarkers = (Marker) baiduMap.addOverlay(options);
        } else {
            if (userMarkers != null)
                userMarkers.setPosition(llA);
        }
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
        option.setScanSpan(5000);    //设置定时定位的时间间隔。单位毫秒
        locationClient.setLocOption(option);
        //注册位置监听器
        locationClient.registerLocationListener(new BDLocationListener() {

            @Override
            public void onReceiveLocation(BDLocation location) {
                if (location == null) {
                    return;
                }
                String lonAndlat = (String) SPUtils.get(StartServiceActivity.this, Constant.LON_LAT, "");

                if (lonAndlat != null && !"".equals(lonAndlat)) {
                    double myLon = Double.parseDouble(lonAndlat.split(",")[0]);
                    double myLat = Double.parseDouble(lonAndlat.split(",")[1]);

                    double instance = Utils.getDistance(myLon, myLat, location.getLongitude(), location.getLatitude());
                    if (instance >= 10) {
                        setLonLat(location);
                    }
                } else {
                    setLonLat(location);
                }
                MyLocationData locData = new MyLocationData.Builder()
                        .accuracy(location.getRadius())
                        // 此处设置开发者获取到的方向信息，顺时针0-360
                        .direction(100).latitude(location.getLatitude())
                        .longitude(location.getLongitude()).build();
//                initOverlay(location.getLatitude(), location.getLongitude(), R.drawable.ic_guide);
                baiduMap.setMyLocationData(locData);

                if (isFirst) {
                    isFirst = false;
                    mCurrentMode = LocationMode.NORMAL;
                    baiduMap.setMyLocationConfigeration(new MyLocationConfiguration(
                            mCurrentMode, true, bdMyself, accuracyCircleFillColor, accuracyCircleStrokeColor));
                    LatLng ll = new LatLng(location.getLatitude(),
                            location.getLongitude());
                    MapStatus mMapstatus = new MapStatus.Builder().target(ll).zoom(20f).build();
                    MapStatusUpdate u = MapStatusUpdateFactory.newMapStatus(mMapstatus);
                    baiduMap.animateMapStatus(u);
                    baiduMap.setMapStatus(u);
                    baiduMap.setMyLocationEnabled(true);//开启定位图层
                }
            }
        });
    }

    /**
     * 上传经纬度
     *
     * @param location
     */
    private void setLonLat(final BDLocation location) {
        RequestParams params = new RequestParams();
        params.put("uid", (String) SPUtils.get(this, Constant.UID, ""));
        params.put("lon", location.getLongitude() + "");
        params.put("lat", location.getLatitude() + "");
        SPUtils.put(this, Constant.LON_LAT, location.getLongitude() + "," + location.getLatitude());
        IRequest.post(this, HttpUrl.POST_LON_LAT, params, new RequestBackListener(this) {
            @Override
            public void requestSuccess(String json) {
                super.requestSuccess(json);
            }
        });
    }

    /**
     * 步行路线规划
     *
     * @param myLon
     * @param myLat
     * @param userLon
     * @param userLat
     */
    private void setWalkingSearch(double myLon, double myLat, double userLon, double userLat) {
        PlanNode stNode = PlanNode.withLocation(new LatLng(myLat, myLon));
        PlanNode enNode = PlanNode.withLocation(new LatLng(userLat, userLon));
        mSearch.walkingSearch((new WalkingRoutePlanOption())
                .from(stNode)
                .to(enNode));
    }

    OnGetRoutePlanResultListener listener = new OnGetRoutePlanResultListener() {
        @Override
        public void onGetWalkingRouteResult(WalkingRouteResult result) {
            //获取步行线路规划结果
            if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                Toast.makeText(StartServiceActivity.this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
            }
            if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
                // 起终点或途经点地址有岐义，通过以下接口获取建议查询信息
                // result.getSuggestAddrInfo()
                return;
            }
            if (result.error == SearchResult.ERRORNO.NO_ERROR) {
                if (routeOverlay != null) {
                    routeOverlay.removeFromMap();
                }
                WalkingRouteOverlay overlay = new MyWalkingRouteOverlay(baiduMap);
                baiduMap.setOnMarkerClickListener(overlay);
                routeOverlay = overlay;
                overlay.setData(result.getRouteLines().get(0));
                overlay.addToMap();
//                overlay.zoomToSpan();
            }
        }

        @Override
        public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {
            //获取公交换乘路径规划结果
        }

        @Override
        public void onGetDrivingRouteResult(DrivingRouteResult drivingRouteResult) {
            //获取驾车线路规划结果
        }

        @Override
        public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {
            //获取骑行线路规划结果
        }
    };

    private class MyWalkingRouteOverlay extends WalkingRouteOverlay {

        public MyWalkingRouteOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public BitmapDescriptor getStartMarker() {
//            if (useDefaultIcon) {
//                return BitmapDescriptorFactory.fromResource(R.drawable.ic_guide);
//            }
            return null;
        }

        @Override
        public BitmapDescriptor getTerminalMarker() {
//            if (useDefaultIcon) {
//                return BitmapDescriptorFactory.fromResource(R.drawable.ic_client);
//            }
            return null;
        }
    }
}
