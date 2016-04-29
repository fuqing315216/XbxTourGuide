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
import com.xbx.tourguide.util.ToastUtils;
import com.xbx.tourguide.util.VerifyUtil;
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

    private ImageButton returnIbtn;
    private TextView updateTv;
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

                    String arrayTime = result.getFree_time();
                    if (!VerifyUtil.isNullOrEmpty(arrayTime)) {
                        List<String> timeList = Arrays.asList(arrayTime.split(","));
                        List<String> dateList = new ArrayList<>();
                        for (int i = 0; i < timeList.size(); i++) {
                            String str = timeList.get(i);
                            String date = str.substring(str.indexOf("-") + 1, str.length());
                            dateList.add(date.substring(date.indexOf("-") + 1, date.length()));
                        }
                        for (int i = 0; i < dateList.size(); i++) {
                            for (int j = 0; j < gridList.size(); j++) {
                                if (gridList.get(j).getDate() != null) {
                                    String date = gridList.get(j).getDate().getDate();
                                    if (date.equals(dateList.get(i))) {
                                        gridList.get(j).setIsSelected(true);
                                        selectTimeList.add(gridList.get(j));
                                    }
                                    adapter.update(gridList);
                                }
                            }
                        }
                    }
                    break;
                case TaskFlag.PAGEREQUESTWO:
                    ToastUtils.showShort(ServiceTimeActivity.this, "修改成功");
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
        uid = UserInfoParse.getUid(Cookie.getUserInfo(this));
        returnIbtn = (ImageButton) findViewById(R.id.ibtn_return);
        updateTv = (TextView) findViewById(R.id.tv_update);
        gridView = (UnScrollGridView) findViewById(R.id.gv_calendar);
        hourPriceEt = (EditText) findViewById(R.id.et_price_h);
        dayPriceEt = (EditText) findViewById(R.id.et_price_d);
        totalRbtn = (RadioButton) findViewById(R.id.rb_total);
        partRbtn = (RadioButton) findViewById(R.id.rb_part);
        leaderRbtn = (RadioButton) findViewById(R.id.rb_leader);

        findViewById(R.id.rlyt_service_time_location).setOnClickListener(this);
        returnIbtn.setOnClickListener(this);
        updateTv.setOnClickListener(this);
        gridView.setOnItemClickListener(this);
        totalRbtn.setOnClickListener(this);
        partRbtn.setOnClickListener(this);
        leaderRbtn.setOnClickListener(this);

        totalRbtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_gender_selected, 0, 0, 0);
        partRbtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_gender_normal, 0, 0, 0);
        leaderRbtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_gender_normal, 0, 0, 0);

        TourGuideInfoBeans tourGuideInfoBean = UserInfoParse.getUserInfo(Cookie.getUserInfo(this));
        userType = Integer.valueOf(tourGuideInfoBean.getUser_type());
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
            case R.id.ibtn_return:
                finish();
                break;
            case R.id.tv_update:
                setServiceTime();
                break;
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
     * 服务时间设置
     */
    private void setDateList() {

        ArrayList<String> dateList = new ArrayList<>();

        long time = System.currentTimeMillis();
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.setTimeInMillis(time);

        int year = mCalendar.get(Calendar.YEAR);
        int month = mCalendar.get(Calendar.MONTH) + 1;
        int day = mCalendar.get(Calendar.DATE);

        int firstWeek = calendar.StringData(year, month, day);

        int monthLastDay = calendar.getLastDayofMonth(year, month);
        int daySum = calendar.getDaysByYearMonth(year, 2);
        if (day == monthLastDay) {//如果当前时间为本月最后一天
            if (month == 1) {//若本月为2月
                for (int i = 1; i <= daySum; i++) {
                    dateList.add(i + "");
                }
                int feSize = dateList.size();
                if (feSize < 30) {
                    for (int i = 1; i <= 30 - feSize; i++) {
                        dateList.add(i + "");
                    }
                }
            } else {//不为2月
                for (int i = 1; i <= 30; i++) {
                    dateList.add(i + "");
                }
            }

        } else {//如果当前时间不为本月最后一天
            for (int i = day + 1; i <= monthLastDay; i++) {
                dateList.add(i + "");
            }

            int size = dateList.size();

            if (size < 30) {
                for (int i = 1; i <= 30 - size; i++) {
                    dateList.add(i + "");
                }
            }
        }

        gridList = new ArrayList<>();

        if (firstWeek == 6) {
            for (int i = 0; i < dateList.size(); i++) {
                gridList.add(new ServiceTimeBeans(false, new DateBeans(year + "", month + "", dateList.get(i))));
            }

        } else {
            for (int i = 0; i <= dateList.size() + firstWeek; i++) {
                if (i <= firstWeek) {
                    for (int j = 0; j <= firstWeek; j++) {
                        if (i == j) {
                            gridList.add(j, new ServiceTimeBeans());
                        }
                    }
                } else {
                    if (i - firstWeek <= dateList.size()) {
//                        gridList.add(i, dateList.get(i - firstWeek - 1));
                        gridList.add(new ServiceTimeBeans(false, new DateBeans(year + "", month + "", dateList.get(i - firstWeek - 1))));

                    } else {
                        gridList.remove(i);
                    }

                }
            }
        }

        adapter = new ServiceTimeGridAdapter(this, gridList);
        gridView.setAdapter(adapter);

        settingApi.getServiceTime(uid);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ServiceTimeBeans beans = (ServiceTimeBeans) parent.getItemAtPosition(position);
        if (beans.getDate() != null) {
            if (!beans.isSelected()) {
                beans.setIsSelected(true);
            } else {
                beans.setIsSelected(false);
            }
        }

        if (beans.isSelected()) {
            selectTimeList.add(beans);
        } else {
            selectTimeList.remove(beans);
        }

        adapter.update(gridList);
    }

    /**
     * 设置服务时间
     */
    private void setServiceTime() {

        String service_time = "";
        if (selectTimeList != null && selectTimeList.size() > 0) {
            for (int i = 0; i < selectTimeList.size(); i++) {
                String date = "";
                if (Integer.parseInt(selectTimeList.get(i).getDate().getMonth()) < 10) {
                    date = selectTimeList.get(i).getDate().getYear() + "-0" + selectTimeList.get(i).getDate().getMonth() + "-" + selectTimeList.get(i).getDate().getDate();
                } else if (Integer.parseInt(selectTimeList.get(i).getDate().getDate()) < 10) {
                    date = selectTimeList.get(i).getDate().getYear() + "-" + selectTimeList.get(i).getDate().getMonth() + "-0" + selectTimeList.get(i).getDate().getDate();
                } else if (Integer.parseInt(selectTimeList.get(i).getDate().getMonth()) < 10 && Integer.parseInt(selectTimeList.get(i).getDate().getDate()) < 10) {
                    date = selectTimeList.get(i).getDate().getYear() + "-0" + selectTimeList.get(i).getDate().getMonth() + "-0" + selectTimeList.get(i).getDate().getDate();
                } else {
                    date = selectTimeList.get(i).getDate().getYear() + "-" + selectTimeList.get(i).getDate().getMonth() + "-" + selectTimeList.get(i).getDate().getDate();
                }
                if (i != selectTimeList.size() - 1) {
                    service_time = service_time + date + ",";
                } else {
                    service_time = service_time + date;
                }
            }
        }
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        params.put("free_time", service_time);//2016-04-05,2016-04-09

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
            params.put("server_city", locationIds);
        }

        settingApi.setServiceTime(params);
    }
}
