package com.xbx.tourguide.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.platform.comapi.map.C;
import com.xbx.tourguide.R;
import com.xbx.tourguide.adapter.ServiceTimeGridAdapter;
import com.xbx.tourguide.api.SettingApi;
import com.xbx.tourguide.api.TaskFlag;
import com.xbx.tourguide.base.BaseActivity;
import com.xbx.tourguide.beans.CityBeans;
import com.xbx.tourguide.beans.DateBeans;
import com.xbx.tourguide.beans.GetServiceTime;
import com.xbx.tourguide.beans.ServiceTimeBeans;
import com.xbx.tourguide.beans.TourGuideInfoBeans;
import com.xbx.tourguide.http.RequestParams;
import com.xbx.tourguide.jsonparse.UserInfoParse;
import com.xbx.tourguide.util.CalendarUtil;
import com.xbx.tourguide.util.Cookie;
import com.xbx.tourguide.util.JsonUtils;
import com.xbx.tourguide.util.LogUtils;
import com.xbx.tourguide.util.ToastUtils;
import com.xbx.tourguide.util.VerifyUtil;
import com.xbx.tourguide.view.TitleBarView;
import com.xbx.tourguide.view.UnScrollGridView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * Created by shuzhen on 2016/4/7.
 * <p/>
 * 服务时间
 */
public class ServiceTimeActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private UnScrollGridView gridView;
    private EditText hourPriceEt, dayPriceEt;
    private RadioButton totalRbtn, partRbtn, leaderRbtn;
    private ServiceTimeGridAdapter adapter;
    private CalendarUtil calendar;
    private ArrayList<ServiceTimeBeans> gridList = new ArrayList<>();
    private String uid = "";
    private int type = 0;//0-全陪，1-地陪，2-领队
    private List<ServiceTimeBeans> selectTimeList = new ArrayList<>();

    private String locations = "";//多地区
    private String locationIds = "";//地区id

    private int userType;

    private SettingApi settingApi = null;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case TaskFlag.REQUESTSUCCESS:
                    GetServiceTime result = JsonUtils.object((String) msg.obj, GetServiceTime.class);

                    switch (userType) {//1：导游；2：随游；3：土著
                        case 1:
                            if (result.getServer_type() == 0) {
                                type = 0;
                                totalRbtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_gender_selected, 0, 0, 0);
                                partRbtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_gender_normal, 0, 0, 0);
                                leaderRbtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_gender_normal, 0, 0, 0);
                            } else if (result.getServer_type() == 1) {
                                type = 1;
                                totalRbtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_gender_normal, 0, 0, 0);
                                partRbtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_gender_selected, 0, 0, 0);
                                leaderRbtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_gender_normal, 0, 0, 0);
                            } else if (result.getServer_type() == 2) {
                                type = 2;
                                totalRbtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_gender_normal, 0, 0, 0);
                                partRbtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_gender_normal, 0, 0, 0);
                                leaderRbtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_gender_selected, 0, 0, 0);
                            }
                            break;
                        case 2:
                        case 3:
                            findViewById(R.id.rlyt_service_time_guide_type).setVisibility(View.GONE);
                            break;
                    }

                    hourPriceEt.setText(result.getGuide_instant_price());
                    hourPriceEt.setSelection(result.getGuide_instant_price().length());

                    dayPriceEt.setText(result.getGuide_reserve_price());
                    dayPriceEt.setSelection(result.getGuide_reserve_price().length());

                    ((EditText) findViewById(R.id.et_service_time_location)).setText(result.getServer_city_name());
                    locations = result.getServer_city_name();
                    locationIds = result.getServer_city();

                    String arrayTime = result.getFree_time();
                    if (!VerifyUtil.isNullOrEmpty(arrayTime)) {
                        List<String> timeList = Arrays.asList(arrayTime.split(","));
                        for (int i = 0; i < timeList.size(); i++) {
                            String y = CalendarUtil.getYear(timeList.get(i));
                            String m = CalendarUtil.getMonth(timeList.get(i));
                            String d = CalendarUtil.getDay(timeList.get(i));

                            for (int j = 0; j < gridList.size(); j++) {//年月日都相等显示选中色
                                if (gridList.get(j).isDay()) {
                                    if (gridList.get(j).getDate().getYear().equals(y)
                                            && gridList.get(j).getDate().getMonth().equals(m)
                                            && gridList.get(j).getDate().getDate().equals(d)) {
                                        gridList.get(j).setIsSelected(true);
                                    }
                                }
                            }
                        }
                        adapter.update(gridList);
                    }
                    break;
                case TaskFlag.PAGEREQUESTWO:
                    ToastUtils.showShort(ServiceTimeActivity.this, "设置成功");
                    finish();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_time);
        calendar = new CalendarUtil();
        settingApi = new SettingApi(this, handler);
        initView();
    }

    private void initView() {
        TitleBarView titleBarView = (TitleBarView) findViewById(R.id.titlebar);
        titleBarView.setTitle(getString(R.string.service_time));
        titleBarView.setLeftImageButtonOnClickListener(new TitleBarView.OnLeftImageButtonClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        titleBarView.setTextRightTextView(getString(R.string.update_ok));
        titleBarView.setRightTextViewOnClickListener(new TitleBarView.OnRightTextViewClickListener() {
            @Override
            public void onClick(View v) {
                setServiceTime();
            }
        });

        uid = Cookie.getUid(this);

        gridView = (UnScrollGridView) findViewById(R.id.gv_calendar);
        hourPriceEt = (EditText) findViewById(R.id.et_price_h);
        dayPriceEt = (EditText) findViewById(R.id.et_price_d);
        totalRbtn = (RadioButton) findViewById(R.id.rb_total);
        partRbtn = (RadioButton) findViewById(R.id.rb_part);
        leaderRbtn = (RadioButton) findViewById(R.id.rb_leader);

        findViewById(R.id.rlyt_service_time_location).setOnClickListener(this);
        gridView.setOnItemClickListener(this);
        totalRbtn.setOnClickListener(this);
        partRbtn.setOnClickListener(this);
        leaderRbtn.setOnClickListener(this);

        totalRbtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_gender_selected, 0, 0, 0);
        partRbtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_gender_normal, 0, 0, 0);
        leaderRbtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_gender_normal, 0, 0, 0);

        TourGuideInfoBeans tourGuideInfoBean = UserInfoParse.getUserInfo(Cookie.getUserInfo(this));
        userType = Integer.valueOf(tourGuideInfoBean.getGuide_type());
        switch (userType) {//1：导游；2：随游；3：土著
            case 1:
                findViewById(R.id.rlyt_service_time_guide_type).setVisibility(View.VISIBLE);
                findViewById(R.id.rlyt_service_time_priced).setVisibility(View.VISIBLE);
                findViewById(R.id.rlyt_service_time_priceh).setVisibility(View.GONE);
                break;
            case 2:
                findViewById(R.id.rlyt_service_time_guide_type).setVisibility(View.GONE);
                findViewById(R.id.rlyt_service_time_priced).setVisibility(View.VISIBLE);
                findViewById(R.id.rlyt_service_time_priceh).setVisibility(View.VISIBLE);
                break;
            case 3:
                findViewById(R.id.rlyt_service_time_priceh).setVisibility(View.VISIBLE);
                findViewById(R.id.rlyt_service_time_priced).setVisibility(View.GONE);
                findViewById(R.id.rlyt_service_time_guide_type).setVisibility(View.GONE);
                break;
        }
        setDateList();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rlyt_service_time_location:
                Intent cityIntent = new Intent(ServiceTimeActivity.this, SelectProvinceActivity.class);
                cityIntent.putExtra("isSingle", false);
                startActivityForResult(cityIntent, 200);
                break;
            case R.id.rb_total:
                type = 0;
                totalRbtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_gender_selected, 0, 0, 0);
                partRbtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_gender_normal, 0, 0, 0);
                leaderRbtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_gender_normal, 0, 0, 0);
                break;
            case R.id.rb_part:
                type = 1;
                totalRbtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_gender_normal, 0, 0, 0);
                partRbtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_gender_selected, 0, 0, 0);
                leaderRbtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_gender_normal, 0, 0, 0);
                break;
            case R.id.rb_leader:
                type = 2;
                totalRbtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_gender_normal, 0, 0, 0);
                partRbtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_gender_normal, 0, 0, 0);
                leaderRbtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_gender_selected, 0, 0, 0);
                break;

            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200 && resultCode == RESULT_OK) {
            ((EditText) findViewById(R.id.et_service_time_location)).setText("");
            List<CityBeans> cityBeans = (List<CityBeans>) data.getSerializableExtra("locations");
            locations = "";
            for (int i = 0; i < cityBeans.size(); i++) {
                locations += "," + cityBeans.get(i).getName();
                locationIds += "," + cityBeans.get(i).getId();
            }
            ((EditText) findViewById(R.id.et_service_time_location)).setText(locations.replaceFirst(",", ""));
        }
    }

    /**
     * 设置从明天起30天的时间
     */
    private void setDateList() {

        gridList = new ArrayList<>();

        long time = System.currentTimeMillis();
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.setTimeInMillis(time);
        //获取今天日期
        int year = mCalendar.get(Calendar.YEAR);
        int month = mCalendar.get(Calendar.MONTH) + 1;
        int day = mCalendar.get(Calendar.DATE);
        //明天周几
        int weekDay = CalendarUtil.StringData(year, month, day + 1);

        String today = "";
        for (int i = 0; i < (30 + weekDay); i++) {
            String inDay = "";
            ServiceTimeBeans serviceTimeBean = new ServiceTimeBeans();
            DateBeans dateBean = new DateBeans();
            if (i < weekDay) {//明天不是周日 前面显示空
                serviceTimeBean.setIsDay(false);
            } else if (i == weekDay) {
                if (month < 10 && day < 10) {
                    today = year + "-0" + month + "-0" + day;
                } else if (month < 10 && day > 10) {
                    today = year + "-0" + month + "-" + day;
                } else if (month > 10 && day < 10) {
                    today = year + "-" + month + "-0" + day;
                } else {
                    today = year + "-" + month + "-" + day;
                }
                inDay = CalendarUtil.addDay(today, 1);//明天
                dateBean.setYear(CalendarUtil.getYear(inDay));
                dateBean.setMonth(CalendarUtil.getMonth(inDay));
                dateBean.setDate(CalendarUtil.getDay(inDay));

                serviceTimeBean.setIsDay(true);
                serviceTimeBean.setIsSelected(false);
            } else {//循环一次加一天 加到第30天
                inDay = CalendarUtil.addDay(today, i - weekDay + 1);
                dateBean.setYear(CalendarUtil.getYear(inDay));
                dateBean.setMonth(CalendarUtil.getMonth(inDay));
                dateBean.setDate(CalendarUtil.getDay(inDay));

                serviceTimeBean.setIsDay(true);
                serviceTimeBean.setIsSelected(false);

            }
            serviceTimeBean.setDate(dateBean);
            gridList.add(serviceTimeBean);
        }

        adapter = new ServiceTimeGridAdapter(this, gridList);
        gridView.setAdapter(adapter);

        settingApi.getServiceTime(uid);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ServiceTimeBeans beans = (ServiceTimeBeans) parent.getItemAtPosition(position);

        if (beans.isDay()) {
            if (beans.isSelected()) {
                beans.setIsSelected(false);
            } else {
                beans.setIsSelected(true);
            }
        }

        gridList.set(position, beans);

        adapter.update(gridList);
    }

    /**
     * 筛选选中日期上传
     * @return
     */
    private String getFreeTime() {
        String service_time = "";
        for (int i = 0; i < gridList.size(); i++) {
            if (gridList.get(i).isDay()) {
                if (gridList.get(i).isSelected()) {
                    service_time += "," + gridList.get(i).getDate().getYear() + "-"
                            + gridList.get(i).getDate().getMonth() + "-" + gridList.get(i).getDate().getDate();
                }
            }
        }
        if (service_time.contains(",")) {
            service_time = service_time.replaceFirst(",", "");
        }
        return service_time;
    }

    /**
     * 设置服务时间
     */
    private void setServiceTime() {
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        params.put("free_time", getFreeTime());//2016-04-05,2016-04-09

        switch (userType) {//1：导游；2：随游；3：土著
            case 1:
                findViewById(R.id.rlyt_service_time_guide_type).setVisibility(View.VISIBLE);
                findViewById(R.id.rlyt_service_time_priced).setVisibility(View.VISIBLE);
                findViewById(R.id.rlyt_service_time_priceh).setVisibility(View.GONE);
                params.put("server_type", type + "");
                params.put("guide_instant_price", "");
                if (VerifyUtil.isNullOrEmpty(dayPriceEt.getText().toString())) {
                    Toast.makeText(this, "请填写每天的价格", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    params.put("guide_reserve_price", dayPriceEt.getText().toString());
                }

                break;
            case 2:
                findViewById(R.id.rlyt_service_time_guide_type).setVisibility(View.GONE);
                findViewById(R.id.rlyt_service_time_priced).setVisibility(View.VISIBLE);
                findViewById(R.id.rlyt_service_time_priceh).setVisibility(View.VISIBLE);
                params.put("server_type", "");
                if (VerifyUtil.isNullOrEmpty(hourPriceEt.getText().toString())) {
                    Toast.makeText(this, "请填写每小时的价格", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    params.put("guide_instant_price", hourPriceEt.getText().toString());
                }

                if (VerifyUtil.isNullOrEmpty(dayPriceEt.getText().toString())) {
                    Toast.makeText(this, "请填写每天的价格", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    params.put("guide_reserve_price", dayPriceEt.getText().toString());
                }
                break;
            case 3:
                findViewById(R.id.rlyt_service_time_priceh).setVisibility(View.VISIBLE);
                findViewById(R.id.rlyt_service_time_priced).setVisibility(View.GONE);
                findViewById(R.id.rlyt_service_time_guide_type).setVisibility(View.GONE);
                params.put("guide_reserve_price", "");
                params.put("server_type", "");

                if (VerifyUtil.isNullOrEmpty(hourPriceEt.getText().toString())) {
                    Toast.makeText(this, "请填写每小时的价格", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    params.put("guide_instant_price", hourPriceEt.getText().toString());
                }
                break;
        }

        if (VerifyUtil.isNullOrEmpty(locations)) {
            Toast.makeText(this, "请选择服务地区", Toast.LENGTH_SHORT).show();
            return;
        } else {
            params.put("server_city", locationIds.replaceFirst(",", ""));
        }

        settingApi.setServiceTime(params);
    }
}
